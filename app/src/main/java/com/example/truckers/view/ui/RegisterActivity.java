package com.example.truckers.view.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.truckers.MainActivity;
import com.example.truckers.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int TAKE_IMAGE = 2;

    private ImageView profilePicture;

    private FirebaseAuth mAuth;
    private FirebaseStorage mStorage;

    private EditText emailField, passwordField, checkPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Storage
        mStorage = FirebaseStorage.getInstance();

        // Camera permission
        if(ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(RegisterActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    }, TAKE_IMAGE);
        }

        emailField = findViewById(R.id.register_email_field);
        passwordField = findViewById(R.id.register_password_field);
        checkPasswordField = findViewById(R.id.check_password_field);

        profilePicture = (ImageView) findViewById(R.id.register_profile_pic);
        ImageButton backBtn = (ImageButton) findViewById(R.id.register_back_button);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageButton galleryBtn = (ImageButton) findViewById(R.id.select_media_btn);
        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(android.content.Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");

                startActivityForResult(Intent.createChooser(galleryIntent,"Seleccione una imagen"), PICK_IMAGE);
            }
        });

        ImageButton cameraBtn = (ImageButton) findViewById(R.id.use_camera_btn);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_IMAGE);
            }
        });

        Button registerBtn = (Button) findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();
                String passwordCheck = checkPasswordField.getText().toString();

                Bitmap bitmap = ((BitmapDrawable)profilePicture.getDrawable()).getBitmap();

                if(password.equals(passwordCheck) && bitmap != null){
                    createUserInFirebase(email, password);
                }else{
                    Toast.makeText(RegisterActivity.this,"Verifique que los campos esten completos y bien",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void createUserInFirebase(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            saveProfileImage(user);
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Error creando usuario",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveProfileImage(FirebaseUser user){
        String userID = user.getUid();
        StorageReference storageReference = mStorage.getReference();
        StorageReference imagesReference = storageReference.child("images");

        StorageReference profileImageReference = imagesReference.child(userID + "/profilePic.jpg");

        BitmapDrawable bitmapDrawable = (BitmapDrawable) profilePicture.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = profileImageReference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profilePicture.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }

        }

        if(requestCode == TAKE_IMAGE && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            profilePicture.setImageBitmap(bitmap);
        }
    }
}
