package com.if41.firebasemahasiswa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddActivity extends AppCompatActivity {
    private EditText etNama, etMatkul;
    private MaterialButton btSimpan;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etNama = findViewById(R.id.etNama);
        etMatkul = findViewById(R.id.etMatKul);
        btSimpan = findViewById(R.id.btSimpan);

        btSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = etNama.getText().toString();
                String matkul = etMatkul.getText().toString();

                if (nama.trim().isEmpty()) {
                    etNama.setError("Nama Wajib Di-isi");
                } else if (matkul.trim().isEmpty()) {
                    etMatkul.setError("Mata Kulaih Wajib Di-isi");
                } else {
                    databaseReference.child("Mahasiswa").push().setValue(new ModelMahasiswa(nama, matkul)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(AddActivity.this, "Data Berhasil Ditambah", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddActivity.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}