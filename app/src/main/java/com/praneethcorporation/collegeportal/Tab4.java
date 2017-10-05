package com.praneethcorporation.collegeportal;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.SyncStateContract.Constants;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.net.ssl.HttpsURLConnection;
import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import static android.app.Activity.RESULT_OK;
import static com.praneethcorporation.collegeportal.R.id.imageView;
import static com.praneethcorporation.collegeportal.R.id.uploadBn;

/**
 * Created by user on 8/29/2017.
 */

public class Tab4 extends Fragment implements SingleUploadBroadcastReceiver.Delegate{
    private final SingleUploadBroadcastReceiver uploadReceiver =
            new SingleUploadBroadcastReceiver();

    Button UploadBn, ChooseImg;
    ImageView imgView;
    String ImagePath = "image_path" ;
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    String reg_no,name;
    //Uri to store the image uri
    private Uri filePath;
    String path;
    boolean check = true;
    String ServerUploadPath ="http://139.59.5.186/php/photo_upload.php" ;
    ProgressDialog dialog;
    private final int IMG_REQUEST = 1;
    private Bitmap bitmap;

    @Override
    public void onResume() {
        super.onResume();
        uploadReceiver.register(getContext());
    }

    @Override
    public void onPause() {
        super.onPause();
        uploadReceiver.unregister(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab4, container, false);
        Context c = getActivity().getApplicationContext();
        reg_no=getActivity().getIntent().getStringExtra("reg_no");
        name=UserInfo.name;
        requestStoragePermission();
        ChooseImg = (Button)view.findViewById(R.id.chooseImage);
        UploadBn = (Button) view.findViewById(R.id.uploadBn);
        imgView = (ImageView)view.findViewById(imageView);
        ChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageFromGallery();
            }
        });
        UploadBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog = ProgressDialog.show(getContext(), "", "Uploading file...", true);
                uploadImageToServer();

                dialog = new ProgressDialog(getContext());
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.setMessage("Uploading photo, please wait.");
                dialog.setMax(100);

                dialog.setCancelable(true);
                dialog.show();
            }
        });
        return view;
    }
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    public void selectImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            filePath= data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, "Title", null);
                imgView.setImageBitmap(bitmap);
                imgView.setVisibility(View.VISIBLE);
                UploadBn.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }
    public void uploadImageToServer() {
        //getting the actual path of the image
        //String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();
            uploadReceiver.setDelegate(this);
            uploadReceiver.setUploadID(uploadId);
            //Creating a multi part request
            new MultipartUploadRequest(getContext(), uploadId,ServerUploadPath)
                    .addFileToUpload(getPath(filePath),"image") //Adding file
                    .addParameter("name",name)
                    .addParameter("reg_no",reg_no)//Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload
        }catch (MalformedURLException exc) {
            Toast.makeText(getContext(),exc.getMessage(),Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onProgress(int progress) {
        Log.d("PROGRESS", "progress = " + progress);
        dialog.setProgress(progress);
    }

    @Override
    public void onProgress(long uploadedBytes, long totalBytes) {
        //dialog.incrementSecondaryProgressBy((int) (totalBytes-uploadedBytes));

    }

    @Override
    public void onError(Exception exception) {
        Toast.makeText(getContext(),""+exception,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCompleted(int serverResponseCode, byte[] serverResponseBody) {
        dialog.dismiss();
        Snackbar.make(getActivity().findViewById(R.id.linearLayout),"Whoila!!! Image Uploaded Successfully!",Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void onCancelled() {

    }
}
