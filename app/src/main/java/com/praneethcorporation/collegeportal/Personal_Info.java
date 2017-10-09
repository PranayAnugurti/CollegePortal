package com.praneethcorporation.collegeportal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
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
import java.util.ArrayList;
import java.util.List;

public class Personal_Info extends AppCompatActivity {
    Button button;
    NumberPicker class10, class12;
    EditText school10, school12, board10, board12, per10, per12;
    Button btn;
    acad_info infoItem;
    Context context;
    String reg_no, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        Intent intent = getIntent();
        reg_no = intent.getStringExtra("reg_no");
        name = intent.getStringExtra("name");
        setContentView(R.layout.activity_personal__info);
        button = (Button) findViewById(R.id.saveform2);
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

        button.setOnClickListener(new View.OnClickListener() {

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
                            infoItem = new acad_info(
                                    reg_no,
                                    name,
                                    school10.getText().toString(),
                                    board10.getText().toString(),
                                    String.valueOf(class10.getValue()),
                                    per10.getText().toString(),
                                    school12.getText().toString(),
                                    board12.getText().toString(),
                                    String.valueOf(class12.getValue()),
                                    per12.getText().toString(),
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    ""
                            );
                            BackgroundTask task = new BackgroundTask(context);
                            task.execute(infoItem);
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


        HorizontalStepView stepView = (HorizontalStepView) findViewById(R.id.step_view);
        List<StepBean> stepBeanList = new ArrayList<>();
        StepBean stepBean = new StepBean("Personal", 1);
        StepBean stepBean1 = new StepBean("Academic", 0);
        StepBean stepBean2 = new StepBean("Done", -1);
        stepBeanList.add(stepBean);
        stepBeanList.add(stepBean1);
        stepBeanList.add(stepBean2);
        stepView
                .setStepViewTexts(stepBeanList)//
                .setTextSize(8)//set textSize
                .setStepsViewIndicatorCompletedLineColor(Color.parseColor("#00796B"))//StepsViewIndicator
                .setStepsViewIndicatorUnCompletedLineColor(Color.parseColor("#4DB6AC"))//StepsViewIndicator
                .setStepViewComplectedTextColor(Color.parseColor("#00796B"))//StepsView text
                .setStepViewUnComplectedTextColor(Color.parseColor("#4DB6AC"))//StepsView text
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.success))//StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.unvisited))//StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.current));//StepsViewIndicator AttentionIcon
    }

    public class BackgroundTask extends AsyncTask<acad_info, Void, String> {
        Context ctx;

        BackgroundTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected String doInBackground(acad_info... params) {
            String update_userInfo_url = "http://139.59.5.186/php/register_acad_info.php";
            infoItem = params[0];
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
                        URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(reg_no, "UTF-8")
                                + "&" +
                                URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")
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
                                URLEncoder.encode("per_12", "UTF-8") + "=" + URLEncoder.encode(infoItem.per_12, "UTF-8")
                                + "&" +
                                URLEncoder.encode("spi1", "UTF-8") + "=" + URLEncoder.encode(infoItem.spi1, "UTF-8")
                                + "&" +
                                URLEncoder.encode("spi2", "UTF-8") + "=" + URLEncoder.encode(infoItem.spi2, "UTF-8")
                                + "&" +
                                URLEncoder.encode("spi3", "UTF-8") + "=" + URLEncoder.encode(infoItem.spi3, "UTF-8")
                                + "&" +
                                URLEncoder.encode("spi4", "UTF-8") + "=" + URLEncoder.encode(infoItem.spi4, "UTF-8")
                                + "&" +
                                URLEncoder.encode("spi5", "UTF-8") + "=" + URLEncoder.encode(infoItem.spi5, "UTF-8")
                                + "&" +
                                URLEncoder.encode("spi6", "UTF-8") + "=" + URLEncoder.encode(infoItem.spi6, "UTF-8")
                                + "&" +
                                URLEncoder.encode("spi7", "UTF-8") + "=" + URLEncoder.encode(infoItem.spi7, "UTF-8")
                                + "&" +
                                URLEncoder.encode("spi8", "UTF-8") + "=" + URLEncoder.encode(infoItem.spi8, "UTF-8")
                                + "&" +
                                URLEncoder.encode("cpi", "UTF-8") + "=" + URLEncoder.encode(infoItem.cpi, "UTF-8");
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
            if (s.contains("Academic Details saved successfully!!")) {
                Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Personal_Info.this, FinishedActivty.class);
                startActivity(intent);
            } else {
                Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

