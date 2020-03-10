package com.example.fitflex;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class ProfielActivity extends AppCompatActivity {

    private FirebaseUser fuser;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;

    private EditText email;
    private EditText wachtwoord;
    private EditText telefoon;
    private EditText locatie;
    private EditText naam;
    private ImageView profiel_foto;
    private ProgressBar progressBar;

    String validatieEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiel);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Gebruiker");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        validatieEmail = user.getEmail();

        email = findViewById(R.id.profiel_email);
        wachtwoord = findViewById(R.id.profiel_wachtwoord);
        telefoon = findViewById(R.id.profiel_telefoonnummer);
        locatie = findViewById(R.id.profiel_locatie);
        naam = findViewById(R.id.profiel_naam);

        progressBar = findViewById(R.id.profiel_progressBar);
        profiel_foto = findViewById(R.id.profiel_foto);

        progressBar.setVisibility(View.GONE);

        getGebruikersInformatie();

        /*profiel_foto.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               openAfbeelding();
            }
        });*/

    }

    /*private void openAfbeelding() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);

    }

    private String getBestandsextentie(Uri uri) {

        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private void uploadAfbeelding() {

        Toast.makeText(getApplicationContext(), "Uploading...", Toast.LENGTH_SHORT).show();

        if (imageUri != null) {

            final StorageReference bestandsreferentie = storageReference.child(System.currentTimeMillis() +
                    "." + getBestandsextentie(imageUri));
            uploadTask = bestandsreferentie.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {

                        throw task.getException();

                    }
                    return bestandsreferentie.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if (task.isSuccessful()) {

                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        reference = FirebaseDatabase.getInstance().getReference("Gebruiker").child(fuser.getUid());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imageURL", mUri);
                        reference.updateChildren(map);

                    } else {

                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        } else {

            Toast.makeText(getApplicationContext(), "Geen afbeelding geselecteerd", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            imageUri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()) {

                Toast.makeText(getApplicationContext(), "Upload is bezig", Toast.LENGTH_SHORT).show();


            } else {

                uploadAfbeelding();

            }

        }
    }*/

    public void getGebruikersInformatie() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    if (ds.child("emailID").getValue().equals(validatieEmail)) {

                        email.setText(validatieEmail);
                        wachtwoord.setText(ds.child("wachtwoord").getValue(String.class));
                        telefoon.setText(ds.child("telefoonnummer").getValue(String.class));
                        locatie.setText(ds.child("locatie").getValue(String.class));
                        naam.setText(ds.child("naam").getValue(String.class));

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
