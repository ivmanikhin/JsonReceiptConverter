package com.manikhin.jsonreceiptconverter;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.FileObserver;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    TextView textView_human_text;
    EditText editText_file_path;
    private static final int READ_CODE = 41;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView_human_text = findViewById(R.id.humanText);
        editText_file_path = findViewById(R.id.filePath);
    }

    public void read_JSON_file(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        registerForActivityResult(ActivityResultContracts.RequestPermission, READ_CODE);
//        String data, buff = "", file_path = editText_file_path.getText().toString();
//
//
//        try {
//
//            File file = new File(file_path);
//            FileInputStream fin = new FileInputStream(file);
//            BufferedReader br = new BufferedReader( new InputStreamReader(fin));
//            while((data = br.readLine()) != null){
//                buff += data;
//            }
//            textView_human_text.setText(buff);
//            br.close();
//            fin.close();
//            Toast.makeText(getBaseContext(), "Data has been read", Toast.LENGTH_LONG).show();
//        }
//        catch (Exception e){
//            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//        }
    }
}