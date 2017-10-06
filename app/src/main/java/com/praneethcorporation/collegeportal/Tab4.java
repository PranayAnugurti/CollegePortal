package com.praneethcorporation.collegeportal;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;
import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;
import static android.app.Activity.RESULT_OK;
import static com.praneethcorporation.collegeportal.R.id.imageView;

/**
 * Created by user on 8/29/2017.
 */

public class Tab4 extends Fragment implements SingleUploadBroadcastReceiver.Delegate{
    private final SingleUploadBroadcastReceiver uploadReceiver =
            new SingleUploadBroadcastReceiver();

    Button UploadBn, ChooseImg,BrowseBtn,UploadPdfBtn,DownloadPdfBtn;
    ImageView imgView;
  TextView pdfPathTextView;
  EditText pdfPathEditTxt;
    String ImagePath = "image_path" ;
    private int PICK_IMAGE_REQUEST = 1;
    private int PICK_PDF_REQUEST = 2;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    String reg_no,name,imageServerLink;
    //Uri to store the image uri
    private Uri filePath;
    private Uri pdfPath ;
    String path,pathForPdf,pdfServerLink;
    boolean check = true;
    String ImageUploadServerPath ="http://139.59.5.186/php/photo_upload.php" ;
    String PdfUploadServerPath ="http://139.59.5.186/php/pdf_upload.php" ;
    ProgressDialog dialog;
    private final int IMG_REQUEST = 1;
    private Bitmap bitmap;
  private String tag="O_MY";


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
        imageServerLink=getActivity().getIntent().getStringExtra("image");
        pdfServerLink=getActivity().getIntent().getStringExtra("pdf");
        name=UserInfo.name;
        requestStoragePermission();
        ChooseImg = (Button)view.findViewById(R.id.chooseImage);
        UploadBn = (Button) view.findViewById(R.id.uploadBn);
        BrowseBtn = (Button) view.findViewById(R.id.browseBtn);
        UploadPdfBtn = (Button) view.findViewById(R.id.uploadPdfBtn);
        DownloadPdfBtn = (Button) view.findViewById(R.id.downloadBtn);
        pdfPathEditTxt=(EditText)view.findViewById(R.id.pdfNameFeild);
        pdfPathTextView=(TextView)view.findViewById(R.id.pdfName);
        imgView = (ImageView)view.findViewById(imageView);
      Picasso.with(getContext()).load(imageServerLink).into(imgView);
        ChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageFromGallery();
            }
        });
        UploadBn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = VERSION_CODES.KITKAT)
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
      pdfPathTextView.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          viewPDF();
        }
      });

      BrowseBtn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          showFileChooser();
        }
      });
      UploadPdfBtn.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = VERSION_CODES.KITKAT)
        @Override
        public void onClick(View v) {
          //dialog = ProgressDialog.show(getContext(), "", "Uploading file...", true);
          uploadPdfToServer();

          dialog = new ProgressDialog(getContext());
          dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
          dialog.setMessage("Uploading pdf, please wait.");
          dialog.setMax(100);
          dialog.setCancelable(true);
          dialog.show();
        }
      });
      DownloadPdfBtn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          downloadPDF();
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
        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            pdfPath = data.getData();
            pdfPathEditTxt.setText(pdfPath.toString());
            UploadPdfBtn.setVisibility(View.VISIBLE);
            pdfPathTextView.setClickable(true);


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

    @RequiresApi(api = VERSION_CODES.KITKAT)
    public void uploadImageToServer() {
        //getting the actual path of the image
        //String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();
            uploadReceiver.setDelegate(this);
            uploadReceiver.setUploadID(uploadId);
            //Creating a multi part request
            new MultipartUploadRequest(getContext(), uploadId,ImageUploadServerPath)
                    .addFileToUpload(FilePath.getPath(getContext(),filePath),"image") //Adding file
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
  @RequiresApi(api = VERSION_CODES.KITKAT)
  public void uploadPdfToServer() {
    //getting name for the image
    //String name = editText.getText().toString().trim();
    pathForPdf=FilePath.getPath(getContext(),pdfPath);

    if (pathForPdf == null) {

      Toast.makeText(getContext(), "Please move your .pdf file to internal storage and retry", Toast.LENGTH_LONG).show();
    } else {
      //Uploading code
      try {
        String uploadId = UUID.randomUUID().toString();
        uploadReceiver.setDelegate(this);
        uploadReceiver.setUploadID(uploadId);
        //Creating a multi part request
        Log.d("O_MY","Reg.no="+reg_no);
        new MultipartUploadRequest(getContext(), uploadId, PdfUploadServerPath)
            .addFileToUpload(pathForPdf, "pdf") //Adding file
            .addParameter("name",name)
            .addParameter("reg_no",reg_no)
            //Adding text parameter to the request
            .setNotificationConfig(new UploadNotificationConfig())
            .setMaxRetries(2)
            .startUpload(); //Starting the upload

      } catch (Exception exc) {
        Toast.makeText(getContext(), exc.getMessage(), Toast.LENGTH_SHORT).show();
      }
    }
  }


  //method to show file chooser
  private void showFileChooser() {
    Intent intent = new Intent();
    intent.setType("application/pdf");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
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
      Log.d("O_MY",serverResponseBody+"ResponseCode"+serverResponseBody);
        Snackbar.make(getActivity().findViewById(R.id.linearLayout),"Whoila!!! File Uploaded Successfully!",Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void onCancelled() {

    }
  public void downloadPDF()
  {
    new DownloadFile().execute(pdfServerLink,pdfServerLink.substring(pdfServerLink.lastIndexOf("/")+1));
  }

  public void viewPDF()
  {
    File pdfFile = new File(Environment.getExternalStorageDirectory() + "/PDF DOWNLOAD/" +pdfServerLink.substring(pdfServerLink.lastIndexOf("/")+1));  // -> filename = maven.pdf
    Uri path = Uri.fromFile(pdfFile);
    Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
    pdfIntent.setDataAndType(path, "application/pdf");
    pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    try{
      startActivity(pdfIntent);
    }catch(ActivityNotFoundException e){
      Toast.makeText(getContext(), "No Application available to view PDF", Toast.LENGTH_SHORT).show();
    }
  }

  private class DownloadFile extends AsyncTask<String, Void, Void> {

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      showpDialog();
    }

    @Override
    protected Void doInBackground(String... strings) {

      String fileUrl = strings[0];
      String fileName = strings[1];

      String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
      File folder = new File(extStorageDirectory, "PDF DOWNLOAD");
      folder.mkdir();
      File pdfFile = new File(folder, fileName);
      try{
        pdfFile.createNewFile();
      }catch (IOException e){
        e.printStackTrace();
      }
      FileDownloader.downloadFile(fileUrl, pdfFile);
      return null;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);
      hidepDialog();
      Toast.makeText(getContext(), "Download PDf successfully", Toast.LENGTH_SHORT).show();
      DownloadPdfBtn.setText("Pdf Downloaded!!");
      Log.d("Download complete", "----------");
    }
  }


  private void showpDialog() {
    dialog = new ProgressDialog(getContext());
    dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    dialog.setMessage("Downloading pdf, please wait.");
    dialog.setMax(100);
    dialog.setCancelable(true);
    dialog.show();
  }

  private void hidepDialog() {
    if (dialog.isShowing())
      dialog.dismiss();
  }

}

