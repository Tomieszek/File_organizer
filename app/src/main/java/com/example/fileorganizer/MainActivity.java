package com.example.fileorganizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.ArrayList;

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
            RadioButton r7;
            String Path;
            EditText PathText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mButton = findViewById(R.id.button_send);
        mButton.setOnClickListener(this);
        PathText = findViewById(R.id.path_enter);
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
        r7=findViewById(R.id.R7);
        r7.setOnClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            
            case R.id.item1:
                Intent aboutintent = new Intent(this, About_app.class);
                startActivity(aboutintent);
                return true;
            case R.id.item2:
                Intent favIntent = new Intent(this, Help.class);
                startActivity(favIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

private  void listoffiles(String path){

    Log.d("Files", "Path: " + path);
    File directory = new File(path);
    File[] files = directory.listFiles();
        ArrayList<File> newFiles= new ArrayList<File>();
        if (files!=null) {
            for (File file : files) {

                if (!file.isDirectory()) {
                    newFiles.add(file);

                }
            }
            int length = newFiles.size();
            Log.d("Files", "Size: " + length);
            if (length == 0) {
                Toast.makeText(this, "Brak plików w wybranej lokalizacji", Toast.LENGTH_SHORT).show();
            } else {

                for (File newfile : newFiles) {

                    Log.d("Files", "FileName:" + newfile.getName());
                    String filename = newfile.getName();
                    String filetype = filename.substring(filename.indexOf(".") + 1);
                    filename.trim();
                    Log.d("Files", "Filetype: " + filetype);
                    moveFile(path, newfile.getName(), filetype);

                }
            }
        }else {
            Toast.makeText(this, "Wybrano nie istniejącą lokalizację", Toast.LENGTH_SHORT).show();
        }
}
private String GetCustompath( String Path){
        String Custompath=Path+PathText.getText().toString();
    Toast.makeText(this,Custompath, Toast. LENGTH_SHORT).show();
        return Custompath;

}
private String removablepath(){

        String removableStoragePath = null;
        File fileList[] = new File("/storage/").listFiles();
        for (File file : fileList)
        { if(!file.getAbsolutePath().equalsIgnoreCase(Environment.getExternalStorageDirectory().getAbsolutePath())&&file.isDirectory()&& file.canRead())
            removableStoragePath = file.getAbsolutePath(); }
    return removableStoragePath;
}
    private void moveFile(String inputPath, String inputFile,String filetype) {
        String outputPath = "";

        if (r1.isChecked()) {
            outputPath = removablepath() ;
        }else if(r2.isChecked()){
             outputPath = Environment.getExternalStorageDirectory().toString() ;}


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
                    String intest=inputPath + inputFile;
                    String outtest= outputPath+ "/Pictures/" + inputFile;
                    prg.setProgress(25);
                    if(intest.equals(outtest)) {
                        Toast. makeText(this,"Folder który próbujesz posprzątać jest jedną z domyślnych lokalizacji przechowywania plików przez program, nie możesz jej posprzątać", Toast. LENGTH_SHORT).show();
                    }
                    else{
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
              tv3.setText(R.string.Above_progress_bar_2);
                }
                break;


                    case "zip":
                    case "rar":
                    case "7z":
                    case "tar":
                    case "cab":
                    case "bzip2":{
                        String archivesPath=outputPath+"/Archives/";
                        tv3.setText(R.string.Above_progress_bar_2);
                        //                    create output directory if it doesn't exist
                        File dir = new File (archivesPath);
                        if (!dir.exists())
                        {
                            dir.mkdirs();
                        }
                        in = new FileInputStream(inputPath + inputFile);
                        out = new FileOutputStream( archivesPath + inputFile);
                        String intest=inputPath + inputFile;
                        String outtest= archivesPath + inputFile;
                        prg.setProgress(25);
                        if(intest.equals(outtest)) {
                            Toast. makeText(this,"Folder który próbujesz posprzątać jest jedną z domyślnych lokalizacji przechowywania plików przez program, nie możesz jej posprzątać", Toast. LENGTH_SHORT).show();
                        }
                        else {
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
                        tv3.setText(R.string.Above_progress_bar_2);
                    }break;

                case "mp3":
                case "flac":
                case "wv":
                case "m4a":
                case "wav":
                            {
                    String musicPath=outputPath+"/Music/";
                    tv3.setText(R.string.Above_progress_bar_2);
                    //                    create output directory if it doesn't exist
                    File dir = new File (musicPath);
                    if (!dir.exists())
                    {
                        dir.mkdirs();
                    }
                    in = new FileInputStream(inputPath + inputFile);
                    out = new FileOutputStream( musicPath + inputFile);
                                String intest=inputPath + inputFile;
                                String outtest= musicPath + inputFile;
                                prg.setProgress(25);
                                if(intest.equals(outtest)) {
                                    Toast. makeText(this,"Folder który próbujesz posprzątać jest jedną z domyślnych lokalizacji przechowywania plików przez program, nie możesz jej posprzątać", Toast. LENGTH_SHORT).show();
                                }
                                else {
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
                }}break;

                case "doc":
                case "docx":
                case "xlsx":
                case "pdf":
                case "txt":
                {
                    String documentsPath=outputPath+"/Documents/";
                    tv3.setText(R.string.Above_progress_bar_2);
                    //                    create output directory if it doesn't exist
                    File dir = new File (documentsPath);
                    if (!dir.exists())
                    {
                        dir.mkdirs();
                    }
                    in = new FileInputStream(inputPath + inputFile);
                    out = new FileOutputStream(documentsPath + inputFile);
                    String intest=inputPath + inputFile;
                    String outtest= documentsPath + inputFile;
                    prg.setProgress(25);
                    if(intest.equals(outtest)) {
                        Toast. makeText(this,"Folder który próbujesz posprzątać jest jedną z domyślnych lokalizacji przechowywania plików przez program, nie możesz jej posprzątać", Toast. LENGTH_SHORT).show();
                    }
                    else {
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
                }}break;

                case "mp4":
                case "wmv":
                case "mkv":

                {
                    String moviesPath=outputPath+"/Movies/";
                    tv3.setText(R.string.Above_progress_bar_2);
                    //                    create output directory if it doesn't exist
                    File dir = new File (moviesPath);
                    if (!dir.exists())
                    {
                        dir.mkdirs();
                    }
                    in = new FileInputStream(inputPath + inputFile);
                    out = new FileOutputStream(moviesPath + inputFile);
                    String intest=inputPath + inputFile;
                    String outtest= moviesPath + inputFile;
                    prg.setProgress(25);
                    if(intest.equals(outtest)) {
                        Toast. makeText(this,"Folder który próbujesz posprzątać jest jedną z domyślnych lokalizacji przechowywania plików przez program", Toast. LENGTH_SHORT).show();
                    }
                    else {
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
                    }}break;

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
                    String intest=inputPath + inputFile;
                    String outtest= defaultpath + inputFile;
                    prg.setProgress(25);
                    if(intest.equals(outtest)) {
                        Toast. makeText(this,"Folder który próbujesz posprzątać jest jedną z domyślnych lokalizacji przechowywania plików przez program, nie możesz jej posprzątać", Toast. LENGTH_SHORT).show();
                    }
                    else {
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
                        Toast. makeText(this,"Nieobsługiwane pliki zostały przeniesione do folderu'/Default_Folder_File_Organizer/'", Toast. LENGTH_SHORT).show();
                }}
                break;


            }
            prg.setProgress(100);
            tv3.setText(R.string.Above_progress_bar_3);
        }

        catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
            tv3.setText(R.string.Above_progress_bar_1);
            Toast. makeText(this,"Nie znaleziono obsługiwanych plików lub folder jest pusty", Toast. LENGTH_SHORT).show();
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
                    Toast.makeText(this, removablepath(), Toast.LENGTH_SHORT).show();
                    tv3.setText(R.string.Above_progress_bar_1);
                    prg.setProgress(0);
                }

            }
            break;
            case R.id.R2:
            {
                if (r2.isChecked()) {

                    Toast.makeText(this, R.string.choosen2, Toast.LENGTH_SHORT).show();
                    Toast.makeText(this,Environment.getExternalStorageDirectory().toString() , Toast.LENGTH_SHORT).show();
                    tv3.setText(R.string.Above_progress_bar_1);
                    prg.setProgress(0);
                }

            }
            break;
            //Download
            case R.id.R3:
                {
                if (r1.isChecked()) {
                    Path = removablepath() + "/Download/";
                    PathText.setVisibility(View.GONE);
                    Toast.makeText(this, Path, Toast.LENGTH_SHORT).show();
                }else if(r2.isChecked()){
                    Path = Environment.getExternalStorageDirectory().toString() + "/Download/";
                    PathText.setVisibility(View.GONE);
                    Toast.makeText(this, Path, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, R.string.warning_radio_first, Toast.LENGTH_SHORT).show();
                    r3.setChecked(false);
                    r5.setChecked(true);
                }

            }
                break;
   //Bez folderu
            case R.id.R4: {

                    if (r1.isChecked()) {
                        Path = removablepath()+"/";
                        PathText.setVisibility(View.GONE);
                        Toast.makeText(this, Path, Toast.LENGTH_SHORT).show();
                    }else if(r2.isChecked()){
                        PathText.setVisibility(View.GONE);
                        Path = Environment.getExternalStorageDirectory().toString()+"/";
                        Toast.makeText(this, Path, Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, R.string.warning_radio_first, Toast.LENGTH_SHORT).show();
                        r4.setChecked(false);
                        r6.setChecked(true);
                    }


                }

                break;
//Custom
            case R.id.R7:
            {
                if (r1.isChecked()) {
                    PathText.setVisibility(View.VISIBLE);
                    Path = removablepath() ;
                    Toast.makeText(this, Path, Toast.LENGTH_SHORT).show();
                }
                else if(r2.isChecked()){
                    PathText.setVisibility(View.VISIBLE);
                    Path = Environment.getExternalStorageDirectory().toString();
                    Toast.makeText(this, Path, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, R.string.warning_radio_first, Toast.LENGTH_SHORT).show();
                    r7.setChecked(false);
                    r5.setChecked(true);
                }

            }
            break;

            case R.id.button_send:{
                if (Path != null){


                if(r7.isChecked()) {


                    listoffiles(GetCustompath(Path));
                        }else {
                            listoffiles(Path);
                    }
                }else {

                    Toast.makeText(this, "Nie wybrano folderu", Toast.LENGTH_SHORT).show();
                }
                break;


        }
    }

}}

