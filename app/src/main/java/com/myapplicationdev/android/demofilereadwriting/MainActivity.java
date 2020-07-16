package com.myapplicationdev.android.demofilereadwriting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permissionCheck_storage = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE
        );
        if (permissionCheck_storage != PermissionChecker.PERMISSION_GRANTED){
            Toast.makeText(this,"permission not granted ",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(
                    MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
            finish();
        }

        String FolderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyFolder";
        File folder = new File(FolderLocation);
        if (folder.exists() == false) {
            boolean result = folder.mkdir();
            if (result == true){
                Log.d("File Read/Write", "Folder created");
            }else {
                Log.e("File Read/write", "folder creation failed");
            }
        }

    }
}