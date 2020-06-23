package com.example.fileorganizer;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mButton;
            ProgressBar prg;
            TextView tv3;
            RadioButton r1;
            RadioButton r2;
            RadioButton r3;
             RadioButton r4;
            RadioButton r5;
            RadioButton r6;
            String Path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.button_send);
        mButton.setOnClickListener(this);
        prg=findViewById(R.id.determinateBar);
        tv3=findViewById(R.id.tv3);
        r1=findViewById(R.id.R1);
        r1.setOnClickListener(this);
        r2=findViewById(R.id.R2);
        r2.setOnClickListener(this);
        r3=findViewById(R.id.R3);
        r3.setOnClickListener(this);
        r4=findViewById(R.id.R4);
        r4.setOnClickListener(this);
        r5=findViewById(R.id.R5);
        r6=findViewById(R.id.R6);

    }

private  void listoffiles(String path){

    Log.d("Files", "Path: " + path);
    File directory = new File(path);
    File[] files = directory.listFiles();
    int length=files.length;
    Log.d("Files", "Size: "+ files.length);
    if(length==0){
        Toast. makeText(this,"Brak plików w wybranej lokalizacji", Toast. LENGTH_SHORT).show();
    }
    else {
        for (File file : files) {
            Log.d("Files", "FileName:" + file.getName());
            String filename = file.getName();
            String filetype = filename.substring(filename.indexOf(".") + 1);
            filename.trim();
            Log.d("Files", "Filetype: " + filetype);
            moveFile(path, file.getName(), filetype);

        }
    }
}

private String removablepath(){

        String removableStoragePath = null;
        File fileList[] = new File("/storage/").listFiles();
        for (File file : fileList)
        { if(!file.getAbsolutePath().equalsIgnoreCase(Environment.getExternalStorageDirectory().getAbsolutePath()) && file.isDirectory() && file.canRead())
            removableStoragePath = file.getAbsolutePath(); }
    return removableStoragePath;
}
    private void moveFile(String inputPath, String inputFile,String filetype) {

        String outputPath = Environment.getExternalStorageDirectory().toString() ;
        InputStream in = null;
        OutputStream out = null;
        try {
            tv3.setText(R.string.Above_progress_bar_1);

            switch (filetype) {

                case "jpg":
                case "jpeg":
                case "png":
                case "tif":
                case "tiff":{

                    tv3.setText(R.string.Above_progress_bar_2);
                    in = new FileInputStream(inputPath + inputFile);
                    out = new FileOutputStream(outputPath+ "/Pictures/" + inputFile);
                    prg.setProgress(25);
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = in.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                    }
                    in.close();
                    in = null;

                    // write the output file
                    out.flush();
                    out.close();
                    out = null;

                    // delete the original file
                    new File(inputPath + inputFile).delete();

              tv3.setText(R.string.Above_progress_bar_2);
                }
                break;

                default:{
                    String defaultpath=outputPath+ "/Default_Folder_File_Organizer/";
                    tv3.setText(R.string.Above_progress_bar_2);
//                    create output directory if it doesn't exist
                    File dir = new File (defaultpath);
                    if (!dir.exists())
                        {
                            dir.mkdirs();
                                }
                    in = new FileInputStream(inputPath + inputFile);
                    out = new FileOutputStream(defaultpath + inputFile);
                    prg.setProgress(25);
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = in.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                    }
                    in.close();
                    in = null;

                    // write the output file
                    out.flush();
                    out.close();
                    out = null;

                    // delete the original file
                    new File(inputPath + inputFile).delete();
                }
                break;


            }
            prg.setProgress(100);
            tv3.setText(R.string.Above_progress_bar_3);
        }

        catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
            Toast. makeText(this,"File not found", Toast. LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }



    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.R1:
            {
                if (r1.isChecked()) {
                    Toast.makeText(this, R.string.choosen1, Toast.LENGTH_SHORT).show();
                }

            }
            break;
            case R.id.R2:
            {
                if (r2.isChecked()) {

                    Toast.makeText(this, R.string.choosen2, Toast.LENGTH_SHORT).show();
                }

            }
            break;
            case R.id.R3:
                {
                if (r1.isChecked()) {
                    Path = removablepath() + "/Download/";
                    Toast.makeText(this, Path, Toast.LENGTH_SHORT).show();
                }else if(r2.isChecked()){
                    Path = Environment.getExternalStorageDirectory().toString() + "/Download/";
                    Toast.makeText(this, Path, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, R.string.warning_radio_first, Toast.LENGTH_SHORT).show();
                    r3.setChecked(false);
                    r5.setChecked(true);
                }

            }
                break;

            case R.id.R4: {

                    if (r1.isChecked()) {
                        Path = removablepath();
                        Toast.makeText(this, Path, Toast.LENGTH_SHORT).show();
                    }else if(r2.isChecked()){
                        Path = Environment.getExternalStorageDirectory().toString();
                        Toast.makeText(this, Path, Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, R.string.warning_radio_first, Toast.LENGTH_SHORT).show();
                        r4.setChecked(false);
                        r6.setChecked(true);
                    }


                }

                break;

            case R.id.button_send:{
                if (Path != null){
                    r5.setChecked(true);
                    r6.setChecked(true);
//                    r1.setChecked(false);
//                    r2.setChecked(false);
//                    r3.setChecked(false);
//                    r4.setChecked(false);
                    removablepath();
                    listoffiles(Path);
//
                } else {

                    Toast.makeText(this, "Directory not chosen", Toast.LENGTH_SHORT).show();
                }}
                break;


        }
    }

}

