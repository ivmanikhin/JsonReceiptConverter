package com.manikhin.jsonreceiptconverter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import android.os.Bundle;
import android.view.View;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView textView_human_text;
    EditText editText_file_path;
    private static final int READ_CODE = 41;
    private static final int STORAGE_PERMISSION_CODE = 1;
    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("Need permission to read from storage")
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }
    private void handleJsonData(Intent intent) {
        Uri json_uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (json_uri != null) {
            String data, buff = "";
            try {
                final String[] split = json_uri.getPath().split(":");
                File file = new File(split[1]);
//                File file = new File(json_uri.getPath());
                FileInputStream fin = new FileInputStream(file);
                BufferedReader br = new BufferedReader( new InputStreamReader(fin));
                while((data = br.readLine()) != null){
                    buff += data;
                }

                JSONObject receipt = new JSONObject(buff);

                JSONArray items = receipt.getJSONArray("items");
                String[] item_names = new String[items.length()];
                for (int i=0; i<items.length(); i++) {
                    item_names[i] = items.getString(i);
                }


                textView_human_text.setText(item_names[1]);
                br.close();
                fin.close();
                Toast.makeText(getBaseContext(), "Data has been read", Toast.LENGTH_LONG).show();
            }
            catch (Exception e){
                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView_human_text = findViewById(R.id.humanText);
        editText_file_path = findViewById(R.id.filePath);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "You've already granted permission", Toast.LENGTH_SHORT).show();
        }
        else {
            requestStoragePermission();
        }

        Intent intent = getIntent();
        if (intent != null) {
            String action = intent.getAction();
            String type = intent.getType();
            if (Intent.ACTION_SEND.equals(action) && type != null) {
                handleJsonData(intent);
            }
        }

    }



    public void read_JSON_file(View view) {


//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("text/plain");
//        startActivityForResult(intent, READ_CODE);
        String data, buff = "", file_path = editText_file_path.getText().toString();


        try {

            File file = new File(file_path);
            FileInputStream fin = new FileInputStream(file);
            BufferedReader br = new BufferedReader( new InputStreamReader(fin));
            while((data = br.readLine()) != null){
                buff += data;
            }
            textView_human_text.setText(buff);
            br.close();
            fin.close();
            Toast.makeText(getBaseContext(), "Data has been read", Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == STORAGE_PERMISSION_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Permission NOT granted", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}