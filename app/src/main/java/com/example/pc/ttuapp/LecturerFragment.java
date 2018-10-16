package com.example.pc.ttuapp;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class LecturerFragment extends Fragment {

    Button selectFile, upload;
    TextView notification;
    Uri pdfUri;


    FirebaseStorage storage;
    FirebaseDatabase database;
    public LecturerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_lecturer, container, false);


        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        selectFile = (Button) rootView.findViewById(R.id.select_file);
        upload = (Button) rootView.findViewById(R.id.upload_file);
        notification = (TextView) rootView.findViewById(R.id.no_file);

        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    selectPdf();
                }else
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pdfUri !=null){ // user has successfully selected file
                    uploadFile(pdfUri);
                }else
                    Toast.makeText(getActivity(), "Select file", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    private void uploadFile(Uri pdfUri) {
        StorageReference storageReference = storage.getReference().child("notes");
        final String filename = System.currentTimeMillis() + "";

        storageReference.child(filename).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getUploadSessionUri().toString();

                        // store the uri in database

                        DatabaseReference reference = database.getReference();
                        //reference.child(filename).setValue()
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            selectPdf();
        }else
            Toast.makeText(getActivity(), "Please provide permission", Toast.LENGTH_SHORT).show();
    }

    private void selectPdf(){
        // to offer user tp select a file manager
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT); // to fetch file
        startActivityForResult(intent, 86);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.lecturers);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check whether user has selected file or not
        if (requestCode == 86 && resultCode == RESULT_OK && data != null){
            pdfUri = data.getData(); //return uri of selected file
        }else {
            Toast.makeText(getActivity(), "Please select a file", Toast.LENGTH_SHORT).show();
        }
    }
}
