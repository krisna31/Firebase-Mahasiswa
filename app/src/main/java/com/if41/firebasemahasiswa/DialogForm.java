package com.if41.firebasemahasiswa;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogForm extends DialogFragment {

    String nama, matkul, key, pilih;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    EditText etNama, etMatkul;
    MaterialButton btSimpan;

    public DialogForm(String nama, String matkul, String key, String pilih) {
        this.nama = nama;
        this.matkul = matkul;
        this.key = key;
        this.pilih = pilih;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_add, container, false);
        etNama = view.findViewById(R.id.etNama);
        etMatkul = view.findViewById(R.id.etMatKul);
        btSimpan = view.findViewById(R.id.btSimpan);

        etNama.setText(nama);
        etMatkul.setText(matkul);

        btSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = etNama.getText().toString();
                String matkul = etMatkul.getText().toString();
                if (pilih.equalsIgnoreCase("Ubah")) {
                    databaseReference.child("Mahasiswa").child(key).setValue(new ModelMahasiswa(nama, matkul)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(view.getContext(), "Berhasil update Daata", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(view.getContext(), "Gagal mengupadte Data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return view;


    }
}
