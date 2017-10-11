package com.praneethcorporation.collegeportal;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;

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
import java.util.Calendar;
import java.util.List;

public class Registration extends AppCompatActivity {

  private Button next;
  EditText dateofbirth, name, reg_no, email, skype, guardian, present_address, permanent_address, password, reEnterPassword;
  private DatePicker datePicker;
  private Calendar calendar;
  private int year, month, day;
  LinearLayout linearLayout;

  RadioGroup radiophGroup, radioresedentialgroup, radiomartialgroup;
  RadioButton ph, res, martial;
  UserInfo infoItem;
  Spinner genderSpinner, branchSpinner, courseSpinner, countrySpinner, stateSpinner, categorySpinner;
  ArrayAdapter<CharSequence> genderAdapter, branchAdapter, courseAdapter, countryAdapter, stateAdapter, categoryAdapter;
  Context context;

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    context = this;
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_registration);
    next = (Button) findViewById(R.id.saveform1);
    linearLayout = (LinearLayout)findViewById(R.id.RegistartionLayout);
    name = (EditText) findViewById(R.id.et_name);
    password = (EditText) findViewById(R.id.et_password);
    reEnterPassword = (EditText) findViewById(R.id.et_retypepassword);
    email = (EditText) findViewById(R.id.etemialId);
    skype = (EditText) findViewById(R.id.etSkypeId);
    guardian = (EditText) findViewById(R.id.etguardian);
    present_address = (EditText) findViewById(R.id.presentAddress);
    permanent_address = (EditText) findViewById(R.id.permanentAddress);
    reg_no = (EditText) findViewById(R.id.et_registration);
    dateofbirth = (EditText) findViewById(R.id.et_datapicker);
    radiophGroup = (RadioGroup) findViewById(R.id.radiophGroup);

    radioresedentialgroup = (RadioGroup) findViewById(R.id.radioresedentialGroup);

    radiomartialgroup = (RadioGroup) findViewById(R.id.radiomartialGroup);
    genderSpinner = (Spinner) findViewById(R.id.genderspinner);
    genderAdapter = ArrayAdapter
        .createFromResource(this, R.array.gender, R.layout.support_simple_spinner_dropdown_item);
    genderAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
    genderSpinner.setAdapter(genderAdapter);
    branchSpinner = (Spinner) findViewById(R.id.branchspinner);

    branchAdapter = ArrayAdapter
        .createFromResource(this, R.array.branch, R.layout.support_simple_spinner_dropdown_item);
    branchAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
    branchSpinner.setAdapter(branchAdapter);

    courseSpinner = (Spinner) findViewById(R.id.coursespinner);
    courseAdapter = ArrayAdapter
        .createFromResource(this, R.array.course, R.layout.support_simple_spinner_dropdown_item);
    courseAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
    courseSpinner.setAdapter(courseAdapter);

    countrySpinner = (Spinner) findViewById(R.id.countryspinner);
    countryAdapter = ArrayAdapter
        .createFromResource(this, R.array.country, R.layout.support_simple_spinner_dropdown_item);
    countryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
    countrySpinner.setAdapter(countryAdapter);
    categorySpinner = (Spinner) findViewById(R.id.categoryspinner);
    categoryAdapter = ArrayAdapter
        .createFromResource(this, R.array.category, R.layout.support_simple_spinner_dropdown_item);
    categoryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
    categorySpinner.setAdapter(categoryAdapter);
    stateSpinner = (Spinner) findViewById(R.id.statespinner);
    stateAdapter = ArrayAdapter
        .createFromResource(this, R.array.states, R.layout.support_simple_spinner_dropdown_item);
    stateAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
    stateSpinner.setAdapter(stateAdapter);

    next.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int isValid = 1;

        int phselectedId = radiophGroup.getCheckedRadioButtonId();
        int resselectedId = (radioresedentialgroup).getCheckedRadioButtonId();
        int martialId = (radiomartialgroup).getCheckedRadioButtonId();
        ph = (RadioButton) findViewById(phselectedId);
        res = (RadioButton) findViewById(resselectedId);
        martial = (RadioButton) findViewById(martialId);


        if (password.getText().toString().isEmpty()) {
          password.setError(getString(R.string.PasswordCheck));
          password.requestFocus();
          isValid = 0;
        }
        if (reEnterPassword.getText().toString().isEmpty()) {
          reEnterPassword.setError(getString(R.string.ReEnterPasswordCheck));
          reEnterPassword.requestFocus();
          isValid = 0;
        }

        String Password = password.getText().toString().trim();
        String RePassword = reEnterPassword.getText().toString().trim();


        if (!Password.equals(RePassword)){
          Snackbar snackbar = Snackbar.make(linearLayout,R.string.PasswordFieldsDidntMatchCheck, BaseTransientBottomBar.LENGTH_LONG);
          snackbar.show();
          isValid=0;
          password.setError(getString(R.string.PasswordFieldsDidntMatchCheck));
          reEnterPassword.setError(getString(R.string.PasswordFieldsDidntMatchCheck));
          password.requestFocus();
          reEnterPassword.requestFocus();
        }

        if (name.getText().toString().isEmpty()) {
          name.setError(getString(R.string.EnterNameCheck));
          name.requestFocus();
          isValid = 0;
        }
        if (reg_no.getText().toString().isEmpty()) {
          reg_no.setError(getString(R.string.RegistartionNoCheck));
          reg_no.requestFocus();
          isValid = 0;
        }


        if (!isValidEmail(email.getText().toString().trim())) {
          email.setError("Please enter valid email adress");
          email.requestFocus();
          isValid = 0;
        }

        if (skype.getText().toString().isEmpty()) {
          skype.setError(getString(R.string.SkypeIdCheck));
          skype.requestFocus();
          isValid = 0;
        }

        if (present_address.getText().toString().isEmpty()) {
          present_address.setError(getString(R.string.PresentAddressCheck));
          present_address.requestFocus();
          isValid = 0;
        }

        if (permanent_address.getText().toString().isEmpty()) {
          permanent_address.setError(getString(R.string.PermanentAddressCheck));
          permanent_address.requestFocus();
          isValid = 0;
        }


        if (isValid == 1) {
          AlertDialog.Builder builder = new AlertDialog.Builder(context);
// Add the buttons
          builder.setPositiveButton("Yes! Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
              // User clicked OK button
              UserInfo userInfoItem = new UserInfo(reg_no.getText().toString(),
                  name.getText().toString(),
                  null,
                  null,
                  null,
                  null,
                  courseSpinner.getSelectedItem().toString(),
                  branchSpinner.getSelectedItem().toString(),
                  dateofbirth.getText().toString(),
                  email.getText().toString(),
                  skype.getText().toString(),
                  null,
                  genderSpinner.getSelectedItem().toString(),
                  categorySpinner.getSelectedItem().toString(),
                  ph.getText().toString(),
                  res.getText().toString(),
                  guardian.getText().toString(),
                  present_address.getText().toString(),
                  permanent_address.getText().toString(),
                  martial.getText().toString(),
                  stateSpinner.getSelectedItem().toString(),
                  countrySpinner.getSelectedItem().toString()
              );

              BackgroundTask task = new BackgroundTask(context);
              task.execute(userInfoItem);
            }
          });
          builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
              // User cancelled the dialog

            }
          });

// Create the AlertDialog
          builder.setMessage("Do you really want to save the details?");
          AlertDialog dialog = builder.create();
          dialog.show();

        }
      }
    });

    dateofbirth = (EditText) findViewById(R.id.et_datapicker);
    calendar = Calendar.getInstance();
    year = calendar.get(Calendar.YEAR);

    month = calendar.get(Calendar.MONTH);
    day = calendar.get(Calendar.DAY_OF_MONTH);
    showDate(year, month + 1, day);
    dateofbirth.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
          showDialog(999);
        }
        return false;
      }
    });
    HorizontalStepView stepView = (HorizontalStepView) findViewById(R.id.step_view);
    List<StepBean> stepBeanList = new ArrayList<>();
    StepBean stepBean = new StepBean("Personal", 0);
    StepBean stepBean1 = new StepBean("Academic", -1);
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
        .setStepsViewIndicatorCompleteIcon(
            ContextCompat.getDrawable(getApplicationContext(), R.drawable.success))//StepsViewIndicator CompleteIcon
        .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.unvisited))//StepsViewIndicator DefaultIcon
        .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.current));//StepsViewIndicator AttentionIcon

  }


  public final static boolean isValidEmail(CharSequence target) {
    if (TextUtils.isEmpty(target)) {
      return false;
    } else {
      return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
  }

  private DatePickerDialog.OnDateSetListener myDateListener = new
      DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0,
            int arg1, int arg2, int arg3) {
          // TODO Auto-generated method stub
          // arg1 = year
          // arg2 = month
          // arg3 = day
          showDate(arg1, arg2 + 1, arg3);
        }
      };

  private void showDate(int year, int month, int day) {
    dateofbirth.setText(new StringBuilder().append(day).append("/")
        .append(month).append("/").append(year));
  }

  @Override
  protected Dialog onCreateDialog(int id) {
    // TODO Auto-generated method stub
    if (id == 999) {
      return new DatePickerDialog(this,
          myDateListener, year, month, day);
    }
    return null;
  }

  public class BackgroundTask extends AsyncTask<UserInfo, Void, String> {

    Context ctx;

    BackgroundTask(Context ctx) {
      this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
    }

    @Override
    protected String doInBackground(UserInfo... params) {
      String update_userInfo_url = "http://139.59.5.186/php/register_user_info.php";
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

            URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(infoItem.reg_no, "UTF-8")
                + "&" +
                URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(infoItem.pass, "UTF-8")
                + "&" +
                URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(infoItem.name, "UTF-8")
                + "&" +
                URLEncoder.encode("course", "UTF-8") + "=" + URLEncoder.encode(infoItem.course, "UTF-8")
                + "&" +
                URLEncoder.encode("branch", "UTF-8") + "=" + URLEncoder.encode(infoItem.branch, "UTF-8")
                + "&" +
                URLEncoder.encode("dob", "UTF-8") + "=" + URLEncoder.encode(infoItem.dob, "UTF-8")
                + "&" +
                URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(infoItem.email, "UTF-8")
                + "&" +
                URLEncoder.encode("skype", "UTF-8") + "=" + URLEncoder.encode(infoItem.skype, "UTF-8")
                + "&" +
                URLEncoder.encode("linkedin", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")
                + "&" +
                URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(infoItem.gender, "UTF-8")
                + "&" +
                URLEncoder.encode("category", "UTF-8") + "=" + URLEncoder.encode(infoItem.category, "UTF-8")
                + "&" +
                URLEncoder.encode("phd", "UTF-8") + "=" + URLEncoder.encode(infoItem.phd, "UTF-8")
                + "&" +
                URLEncoder.encode("residential_status", "UTF-8") + "=" + URLEncoder.encode(infoItem.residential_status, "UTF-8")
                + "&" +
                URLEncoder.encode("guardian", "UTF-8") + "=" + URLEncoder.encode(infoItem.guardian, "UTF-8")
                + "&" +
                URLEncoder.encode("present_address", "UTF-8") + "=" + URLEncoder.encode(infoItem.present_address, "UTF-8")
                + "&" +
                URLEncoder.encode("permanent_address", "UTF-8") + "=" + URLEncoder.encode(infoItem.permanent_address, "UTF-8")
                + "&" +
                URLEncoder.encode("marital_status", "UTF-8") + "=" + URLEncoder.encode(infoItem.marital_status, "UTF-8")
                + "&" +
                URLEncoder.encode("state", "UTF-8") + "=" + URLEncoder.encode(infoItem.state, "UTF-8")
                + "&" +
                URLEncoder.encode("country", "UTF-8") + "=" + URLEncoder.encode(infoItem.country, "UTF-8");
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
      if (s.contains("Personal Details saved successfully!!")) {
        Toast.makeText(ctx, "Personal Details saved successfully!!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Registration.this, Personal_Info.class);
        intent.putExtra("reg_no", infoItem.reg_no);
        intent.putExtra("name", infoItem.name);
        startActivity(intent);
      } else {
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
      }
    }
  }

  @Override
  public void onBackPressed() {
    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
    startActivity(intent);

    super.onBackPressed();
  }
}