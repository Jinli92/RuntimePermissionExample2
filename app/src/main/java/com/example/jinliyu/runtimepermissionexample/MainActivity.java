package com.example.jinliyu.runtimepermissionexample;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS = 1;
    private final int CAMERA_REQUESTCODE = 0X11;//与回调有关
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button fab = (Button) findViewById(R.id.btn);
        textView = findViewById(R.id.showRes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    takePhoto();
            }


        });

    }

    private void takePhoto(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
//                new AlertDialog.Builder(this)
//                        .setMessage("camera permission")
//                        .setPositiveButton("grant", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                ActivityCompat.requestPermissions(MainActivity.this,
//                                        new String[]{Manifest.permission.CAMERA},CAMERA_REQUESTCODE);
//                            }
//                        })
//                        .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                textView.setText("permission denied");
//                            }
//                        })
//                        .show();
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA},CAMERA_REQUESTCODE);
            }
            else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        CAMERA_REQUESTCODE);
            }
        }
        else {
            openCamera();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == CAMERA_REQUESTCODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                textView.setText("Permission Granted");
                openCamera();
            }
            else{
                textView.setText("Permission Denied");
                   Toast.makeText(this,"permission denied",Toast.LENGTH_LONG).show();

            }
        }



    }

    private void openCamera(){
        Intent pIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(pIntent, 1);//requestcode
    }
}
