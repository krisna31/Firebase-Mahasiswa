package com.if41.firebasemahasiswa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fabTambah;
    private RecyclerView rvMhs;
    private AdapterMahasiswa adapterMahasiswa;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private ArrayList<ModelMahasiswa> listMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabTambah = findViewById(R.id.fab_tambah);
        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });

        rvMhs = findViewById(R.id.rv_mahasiswa);
        RecyclerView.LayoutManager mLayout = new LinearLayoutManager(this);
        rvMhs.setLayoutManager(mLayout);
        rvMhs.setItemAnimator(new DefaultItemAnimator());
        tampilData();
    }

    private void tampilData() {
        databaseReference.child("Mahasiswa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listMahasiswa = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()) {
                    ModelMahasiswa mhs = item.getValue(ModelMahasiswa.class);
                    mhs.setKey(item.getKey());
                    listMahasiswa.add(mhs);
                }

                adapterMahasiswa = new AdapterMahasiswa(listMahasiswa, MainActivity.this);
                rvMhs.setAdapter(adapterMahasiswa);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}