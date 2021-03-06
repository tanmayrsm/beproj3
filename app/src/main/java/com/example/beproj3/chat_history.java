package com.example.beproj3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beproj3.Adapters.AllChatsAdapter;
import com.example.beproj3.Adapters.AllNativeChatsAdapter;
import com.example.beproj3.Models.Chats;
import com.example.beproj3.Models.PDF;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class chat_history extends AppCompatActivity {
    //private static final String LOG_TAG = "chat_history" ;
    RecyclerView recyclerViewr ,native_recycler;
    TextView ch_with;
    Switch s2;
    FirebaseUser firebaseUser;
    StorageReference storageReference;
    Button send_docu, view_docu, view_ocr ,lang_ocro;

    private String checker = "";
    public String TAG = "permission";
    private final List<Chats> chatlist = new ArrayList<>();
    private final List<Chats> nativechatlist = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager, linearLayoutManager2;
    private AllChatsAdapter chatsAdapter;
    private AllNativeChatsAdapter nativeChatsAdapter;
    public String uskaId,uska_name,mera;
    public DatabaseReference refi;
    private static final int PICKFILE_RESULT_CODE = 8778;
    FirebaseAuth auth;
    //PDFView p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_history);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        ch_with = findViewById(R.id.uska_naam);
        recyclerViewr = findViewById(R.id.recyclerview2);
        native_recycler = findViewById(R.id.native_recyclerview);
        view_ocr = findViewById(R.id.real_time_ocr);
        lang_ocro = findViewById(R.id.lang_ocr);

        lang_ocro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(chat_history.this , other_lang_ocr.class));
            }
        });

        //p = findViewById(R.id.pdfView);

        s2 = findViewById(R.id.switch2);

        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    recyclerViewr.setVisibility(View.GONE);
                    native_recycler.setVisibility(View.VISIBLE);
                }
                else{
                        recyclerViewr.setVisibility(View.VISIBLE);
                        native_recycler.setVisibility(View.GONE);
                }
            }
        });

        view_docu = findViewById(R.id.view_doc);
        view_docu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
            }
        });
        view_ocr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(chat_history.this,ocr.class));
            }
        });

        //ch_with = findViewById(R.id.chat_with2);
        //Toast.makeText(this, "uska naam:"+uskaId, Toast.LENGTH_SHORT).show();

        chatsAdapter = new AllChatsAdapter(chatlist);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewr.setLayoutManager(linearLayoutManager);

        recyclerViewr.setAdapter(chatsAdapter);

        ///set native recycler view
        nativeChatsAdapter = new AllNativeChatsAdapter(nativechatlist);
        linearLayoutManager2 = new LinearLayoutManager(this);
        native_recycler.setLayoutManager(linearLayoutManager2);

        native_recycler.setAdapter(nativeChatsAdapter);

    }
    @Override
    protected void onStart() {
        super.onStart();

        if(isReadStoragePermissionGranted()){
            Log.e("READ","READ HAI");
        }
        if(isWriteStoragePermissionGranted()){
            Log.e("WRITE","WRITE HAI");
        }





        ////////
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e("Status:","1 - "+isExternalStorageAvailable() + " 2- "+isExternalStorageReadOnly());
            //saveButton.setEnabled(false);
        }
        else{
            Log.e("Huha:","1 - "+isExternalStorageAvailable() + " 2- "+isExternalStorageReadOnly());

        }

        Intent intent = getIntent();
        uskaId = intent.getStringExtra("UskaId");
        uska_name = intent.getStringExtra("UskaNaam");
        mera = intent.getStringExtra("My");     //my id

        ch_with = findViewById(R.id.uska_naam);
        ch_with.setText(uska_name);

        send_docu = findViewById(R.id.send_doc);

        send_docu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                storageReference = FirebaseStorage.getInstance().getReference();
                DatabaseReference data_ref = FirebaseDatabase.getInstance().getReference().child("Call_history")
                                        .child(firebaseUser.getUid()).child(uskaId);

                dumas2();

                //Toast.makeText(chat_history.this, "Hi", Toast.LENGTH_SHORT).show();
//                CharSequence options[] = new CharSequence[]{
//                        "Images",
//                        "PDF Files",
//                        "MS Word Files"
//                };

//                AlertDialog.Builder builder = new AlertDialog.Builder(chat_history.this);
//                builder.setTitle("Select any File");
//
//                builder.setItems(options, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if(which == 0){
//                            checker = "image";
//                        }
//                        else if(which == 1){
//                            checker = "pdf";
//
//                            //dumas();
//
//
//                        }
//                        else if (which == 2){
//                            checker = "word";
//                        }
//                    }
//
//                    private void dumas() {
//                        Intent pdf_intent = new Intent();
//                        pdf_intent.setType("application/pdf");
//                        pdf_intent.setAction(Intent.ACTION_GET_CONTENT);
//                        startActivityForResult(Intent.createChooser(pdf_intent,"Select pdf file"),1);
//
//                    }
//
//                });

            }
        });

        refi = FirebaseDatabase.getInstance().getReference("Call_history").child(mera).child(uskaId);
        refi.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Chats chat = dataSnapshot.getValue(Chats.class);
                chatlist.add(chat);
                chatsAdapter.notifyDataSetChanged();

                //add in native recycler view also
                nativechatlist.add(chat);
                nativeChatsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void dumas2() {
        Intent pdf_intent = new Intent();
        pdf_intent.setType("application/pdf");
        pdf_intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(pdf_intent,"Select pdf file"),2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == RESULT_OK && data!=null &&data.getData()!=null){
            uploadPDFFile(data.getData());
        }
        else{

            switch (requestCode) {
                case PICKFILE_RESULT_CODE:
                    if (resultCode == -1) {
                        Uri fileUri = data.getData();

                        String filePath = fileUri.getPath();
                        Log.e("FIle path","File path:"+filePath);
                        Toast.makeText(this, "File path:" + filePath, Toast.LENGTH_SHORT).show();

                        try {
                            String parsedText="";

                            //filePath tha previously
                            //PdfReader reader = new PdfReader(String.valueOf(Uri.parse("https://firebasestorage.googleapis.com/v0/b/zampa-89864.appspot.com/o/uploads%2F1577281041241.pdf?alt=media&token=4a03ba92-333d-4c95-aefb-06b6561df1a3")));
                            PdfReader reader = new PdfReader(filePath);
                            //PdfReader reader2 = new PdfReader(real_path);

                            int n = reader.getNumberOfPages();

                            for (int i = 0; i <n ; i++) {
                                parsedText   = parsedText+ PdfTextExtractor.getTextFromPage(reader, i+1).trim()+"\n"; //Extracting the content from the different pages
                            }
                            //System.out.println(parsedText);
                            Log.e("Text obt from huha","Haa");
                            Intent i = new Intent(chat_history.this ,ViewText.class);
                            i.putExtra("Text",parsedText);
                            startActivity(i);
                            reader.close();
                        } catch (Exception e) {

                            try{
                                String real_path = getRealPathFromURI(this,fileUri);
                                Log.e("Real path",real_path);

                                String parsedText="";

                                //filePath tha previously
                                //PdfReader reader = new PdfReader(String.valueOf(Uri.parse("https://firebasestorage.googleapis.com/v0/b/zampa-89864.appspot.com/o/uploads%2F1577281041241.pdf?alt=media&token=4a03ba92-333d-4c95-aefb-06b6561df1a3")));
                                PdfReader reader2 = new PdfReader(real_path);
                                //PdfReader reader2 = new PdfReader(real_path);

                                int n = reader2.getNumberOfPages();

                                for (int i = 0; i <n ; i++) {
                                    parsedText   = parsedText+ PdfTextExtractor.getTextFromPage(reader2, i+1).trim()+"\n"; //Extracting the content from the different pages
                                }
                                //System.out.println(parsedText);
                                Log.e("Text obt from huha","Haa");
                                Intent j = new Intent(chat_history.this ,ViewText.class);
                                j.putExtra("Text",parsedText);
                                startActivity(j);
                                reader2.close();

                            }
                            catch(Exception e2){

                                Log.e("Cant open","Cant open");
                                Toast.makeText(this, "Exception:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("Exception2",e2.getMessage());
                                Log.e("Path requested:",filePath);

                            }



                            //Toast.makeText(this, "Na ho payega", Toast.LENGTH_SHORT).show();
                        }
                    }

                    break;
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                Log.d(TAG, "External storage2");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
                    //downloadPdfFile();
                }else{
                    //progress.dismiss();
                }
                break;

            case 3:
                Log.d(TAG, "External storage1");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
                    //SharePdfFile();
                }else{
                    //progress.dismiss();
                }
                break;
        }
    }

    private void uploadPDFFile(Uri data) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading..");
        progressDialog.show();

        Log.e("chat_history","from and to:" +firebaseUser.getUid() + " "+uskaId);
        StorageReference ref = storageReference.child("uploads/"+System.currentTimeMillis()+".pdf");
        ref.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uri.isComplete()){
                            // progressDialog.setMessage("Sending from "+firebaseUser.getUid()+ " to "+uskaId);
                        }
                            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                            String res = currentDate + " " + currentTime;

                            Toast.makeText(chat_history.this, "Uska: "+uskaId, Toast.LENGTH_SHORT).show();

                            Uri url = uri.getResult();

                            DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Call_history")
                                    .child(firebaseUser.getUid()).child(uskaId);

                            DatabaseReference df2 = FirebaseDatabase.getInstance().getReference().child("Call_history")
                                    .child(uskaId).child(firebaseUser.getUid());



                        HashMap<String, Object> result = new HashMap<>();

                        result.put("chat","");
                        result.put("time", res);
                        result.put("type", "pdf");
                        result.put("who_tells", firebaseUser.getUid());
                        result.put("url", url.toString());

                        df.push().setValue(result);
                        df2.push().setValue(result);

                            Toast.makeText(chat_history.this, "Done upload", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();


                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("Uploading..."+(int)progress);
            }
        });
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG,"Permission is granted1");
                return true;
            } else {

                Log.e(TAG,"Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.e(TAG,"Permission is granted1");
            return true;
        }
    }

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG,"Permission is granted2");
                return true;
            } else {

                Log.e(TAG,"Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.e(TAG,"Permission is granted2");
            return true;
        }
    }

    private String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            Log.e(TAG, "getRealPathFromURI Exception : " + e.toString());
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
