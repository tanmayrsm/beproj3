package com.example.beproj3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;


import java.io.IOException;
import java.util.List;

public class eng_ocr_vid extends AppCompatActivity {
    SurfaceView s;
    TextView tv;
    Button nat;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
    GraphicOverlay mGraphicOverlay;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case RequestCameraPermissionID:{
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                        return;
                    }
                    try {
                        cameraSource.start(s.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eng_ocr_vid);

        s = findViewById(R.id.surf_view);
        tv = findViewById(R.id.display2);
        nat = findViewById(R.id.see_native_text2);
        mGraphicOverlay = findViewById(R.id.graphic_overlay);

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.e("Exception", "Not operational re");
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();

            s.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {
                        if(ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

                            ActivityCompat.requestPermissions(eng_ocr_vid.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionID);
                            return;}

                        cameraSource.start(s.getHolder());

                    }catch (Exception e){

                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if(items.size() != 0){

                        tv.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();
                                for(int i = 0; i< items.size();i++){
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("\n");
                                }
                                tv.setText(stringBuilder.toString());
                            }
                        });
                    }
                    ///new code
                    mGraphicOverlay.clear();

                    final StringBuilder strBuilder = new StringBuilder();
                    for (int i = 0; i < items.size(); i++)
                    {
                        TextBlock item = items.valueAt(i);
                        strBuilder.append(item.getValue());
                        strBuilder.append("/");
                        // The following Process is used to show how to use lines & elements as well
                        for (int j = 0; j < items.size(); j++) {
                            TextBlock textBlock = items.valueAt(j);
                            strBuilder.append(textBlock.getValue());
                            strBuilder.append("/");
                            for (Text line : textBlock.getComponents()) {
                                //extract scanned text lines here
                                Log.v("lines", line.getValue());
                                strBuilder.append(line.getValue());
                                strBuilder.append("/");
                                for (Text element : line.getComponents()) {
                                    //extract scanned text words here
                                    Log.v("element", element.getValue());
                                    strBuilder.append(element.getValue());
                                    GraphicOverlay.Graphic textGraphic = new TextGraphic2(mGraphicOverlay, element);
                                    mGraphicOverlay.add(textGraphic);
                                }
                            }
                        }
                    }
                    Log.v("strBuilder.toString()", strBuilder.toString());



                }
            });
        }

    }
}
