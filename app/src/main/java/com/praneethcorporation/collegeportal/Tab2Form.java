package com.praneethcorporation.collegeportal;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.praneethcorporation.collegeportal.InfoClasses.acad_info;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Tab2Form extends AppCompatActivity {
    NumberPicker class10, class12;
    EditText school10, school12, board10, board12, per10, per12;
    Button btn;
    acad_info info;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab2form);

        //Getting data from intent
        final Intent intent = getIntent();
        Bundle b = intent.getExtras();
        context = this;
        info = b.getParcelable("academic_details");
        btn = (Button) findViewById(R.id.saveform2);
        school10 = (EditText) findViewById(R.id.et_school10);
        school12 = (EditText) findViewById(R.id.et_12schoolname);
        board10 = (EditText) findViewById(R.id.et_10thboard);
        board12 = (EditText) findViewById(R.id.et_12thboard);
        per10 = (EditText) findViewById(R.id.et_10percentage);
        per12 = (EditText) findViewById(R.id.et_datapicker);
        class10 = (NumberPicker) findViewById(R.id.clas10yearPicker);
        class10.setMaxValue(2050);
        class10.setMinValue(2010);
        class10.setValue(2013);
        class12 = (NumberPicker) findViewById(R.id.clas12yearPicker);
        class12.setMaxValue(2050);
        class12.setMinValue(2010);
        class12.setValue(2015);
        class10.setValue(parseInt(acad_info.year_10));
        class12.setValue(parseInt(acad_info.year_12));

        school10.setText(info.school_10);
        school12.setText(info.school_12);
        board12.setText(info.board_12);
        board10.setText(info.board_10);
        per10.setText(info.per_10);
        per12.setText(info.per_12);

        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int isValid = 1;
                if (school10.getText().toString().isEmpty()) {
                    school10.setError(getString(R.string.SchoolNameCheck));
                    school10.requestFocus();
                    isValid = 0;
                }
                if (school12.getText().toString().isEmpty()) {
                    school12.setError(getString(R.string.IntermediateNameCheck));
                    isValid = 0;
                    school12.requestFocus();
                }

                if (board10.getText().toString().isEmpty()) {
                    board10.setError(getString(R.string.SchoolBoardNameCheck));
                    board10.requestFocus();
                    isValid = 0;
                }

                if (board12.getText().toString().isEmpty()) {
                    board12.setError(getString(R.string.IntermediateBoardNameCheck));
                    isValid = 0;
                    board12.requestFocus();
                }

                if (per10.getText().toString().isEmpty()) {
                    per10.setError(getString(R.string.SchoolPercentageCheck));
                    isValid = 0;
                    per10.requestFocus();
                }

                if (per12.getText().toString().isEmpty()) {
                    per12.setError(getString(R.string.IntermediatePercentageCheck));
                    isValid = 0;
                    per12.requestFocus();
                }

                if (isValid == 1) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
// Add the buttons
                    builder.setPositiveButton("Yes! Save", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                            acad_info acad_infoItem = new acad_info(
                                    info.reg_no,
                                    info.name,
                                    school10.getText().toString(),
                                    board10.getText().toString(),
                                    String.valueOf(class10.getValue()),
                                    per10.getText().toString(),
                                    school12.getText().toString(),
                                    board12.getText().toString(),
                                    String.valueOf(class12.getValue()),
                                    per12.getText().toString(),
                                    info.spi1,
                                    info.spi2,
                                    info.spi3,
                                    info.spi4,
                                    info.spi5,
                                    info.spi6,
                                    info.spi7,
                                    info.spi8,
                                    info.cpi
                            );
                            BackgroundTask task = new BackgroundTask(context);
                            task.execute(acad_infoItem);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog

                        }
                    });

// Create the AlertDialog
                    builder.setMessage("Do you really want to change the details?");

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    public static class BackgroundTask extends AsyncTask<acad_info, Void, String> {

        Context ctx;

        BackgroundTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(acad_info... params) {
            String update_userInfo_url = "http://139.59.5.186/php/update_acad_info.php";
            acad_info infoItem = params[0];
            URL url = null;
            try {
                url = new URL(update_userInfo_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(outputStream, "UTF-8"));
                String data =
                        URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(infoItem.reg_no, "UTF-8")
                                + "&" +
                                URLEncoder.encode("school_10", "UTF-8") + "=" + URLEncoder.encode(infoItem.school_10, "UTF-8")
                                + "&" +
                                URLEncoder.encode("board_10", "UTF-8") + "=" + URLEncoder.encode(infoItem.board_10, "UTF-8")
                                + "&" +
                                URLEncoder.encode("year_10", "UTF-8") + "=" + URLEncoder.encode(infoItem.year_10, "UTF-8")
                                + "&" +
                                URLEncoder.encode("per_10", "UTF-8") + "=" + URLEncoder.encode(infoItem.per_10, "UTF-8")
                                + "&" +
                                URLEncoder.encode("school_12", "UTF-8") + "=" + URLEncoder.encode(infoItem.school_12, "UTF-8")
                                + "&" +
                                URLEncoder.encode("board_12", "UTF-8") + "=" + URLEncoder.encode(infoItem.board_12, "UTF-8")
                                + "&" +
                                URLEncoder.encode("year_12", "UTF-8") + "=" + URLEncoder.encode(infoItem.year_12, "UTF-8")
                                + "&" +
                                URLEncoder.encode("per_12", "UTF-8") + "=" + URLEncoder.encode(infoItem.per_12, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
        }
    }
}

