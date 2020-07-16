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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {
Button btnWrite, btnRead;
TextView tvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnWrite = findViewById(R.id.buttonWrite);
        btnRead = findViewById(R.id.buttonRead);
        tvContent = findViewById(R.id.textView);

        int permissionCheck_storage = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE
        );
        if (permissionCheck_storage != PermissionChecker.PERMISSION_GRANTED){
            Toast.makeText(this,"permission not granted ",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(
                    MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
            finish();
        }

        final String FolderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyFolder";
        File folder = new File(FolderLocation);
        if (folder.exists() == false) {
            boolean result = folder.mkdir();
            if (result == true){
                Log.d("File Read/Write", "Folder created");
            }else {
                Log.e("File Read/write", "folder creation failed");
            }
        }
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File targetfile = new File(FolderLocation,"data.txt");
                try{
                    FileWriter writer = new FileWriter(targetfile,true);
                    writer.write("Hello world" +"\n");
                    writer.flush();
                    writer.close();

                }catch (Exception e){
                    Toast.makeText(MainActivity.this,"failed to write! ",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File targetFile = new File(FolderLocation,"data.txt");
                if(targetFile.exists()==true){
                    String data = "";
                    try {
                        FileReader reader = new FileReader(targetFile);
                        BufferedReader br = new BufferedReader(reader);
                        String line = br.readLine();
                        while (line != null){
                            data += line + "\n";
                            line = br.readLine();

                        }

                        br.close();
                        reader.close();



                    }catch (Exception e){
                        Toast.makeText(MainActivity.this,"failed to read! ",Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                     tvContent.setText(data);
                }
            }
        });


    }
}