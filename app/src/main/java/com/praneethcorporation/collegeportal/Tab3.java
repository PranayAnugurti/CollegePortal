package com.praneethcorporation.collegeportal;


import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import android.widget.Toast;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;

import com.praneethcorporation.collegeportal.Adapters.InternshipAdapter;
import com.praneethcorporation.collegeportal.Adapters.ProjectAdapter;
import com.praneethcorporation.collegeportal.InfoClasses.Internship;
import com.praneethcorporation.collegeportal.InfoClasses.Project;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 8/29/2017.
 */

public class Tab3 extends Fragment {

    InternshipAdapter adapter;
    ProjectAdapter projectAdapter;
    FloatingActionButton addIntern;
    FloatingActionButton addproject;
    ArrayList<Project> projects;
    ArrayList<Internship> internships;
    String reg_no;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3, container, false);
        Context c = getActivity().getApplicationContext();

        addproject = (FloatingActionButton) view.findViewById(R.id.addProjectbutton);
        addIntern = (FloatingActionButton) view.findViewById(R.id.addInternbutton);


        addproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.projectdialogueedit);
                dialog.setCancelable(true);
                dialog.setTitle("Add Project");
                dialog.show();

                final EditText projectEditTxt = (EditText) dialog.findViewById(R.id.et_Dproject);
                final EditText durationEditTxt = (EditText) dialog.findViewById(R.id.et_DdurationView);
                final EditText descriptionEditTxt = (EditText) dialog
                        .findViewById(R.id.et_DdescriptionView);
                final EditText projectLinkEditTxt = (EditText) dialog.findViewById(R.id.et_DviewProject);

                Button save = (Button) dialog.findViewById(R.id.DupdateProjectBtn);
                save.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int isValid = 1;

                        if (projectEditTxt.getText().toString().isEmpty()) {
                            projectEditTxt.setError(getString(R.string.ProjectNameCheck));
                            isValid = 0;
                        }
                        if (durationEditTxt.getText().toString().isEmpty()) {
                            durationEditTxt.setError(getString(R.string.DurationCheck));
                            isValid = 0;

                        }
                        if (descriptionEditTxt.getText().toString().isEmpty()) {
                            descriptionEditTxt.setError(getString(R.string.DescriptionCheck));
                            isValid = 0;
                        }

                        if (isValid == 1) {
                            Project project = new Project(
                                    "", reg_no,
                                    projectEditTxt.getText().toString(),
                                    durationEditTxt.getText().toString(),
                                    descriptionEditTxt.getText().toString(),
                                    projectLinkEditTxt.getText().toString()
                            );
                            dialog.dismiss();
                            AddProjectAsyncTask task = new AddProjectAsyncTask(getContext());
                            task.execute(project);
                        }
                    }
                });
            }
        });
        addIntern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.internshipdialogedit);
                dialog.setCancelable(true);
                dialog.setTitle("Add Internship");
                dialog.show();

                final EditText intern = (EditText) dialog.findViewById(R.id.DinternshipeditView);
                final EditText company = (EditText) dialog.findViewById(R.id.DcompanyeditView);
                final EditText description = (EditText) dialog.findViewById(R.id.DdescriptioneditView);
                final EditText duration = (EditText) dialog.findViewById(R.id.DdurationeditView);

                Button save = (Button) dialog.findViewById(R.id
                        .DupdateInternBtn);

                save.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int isValid = 1;

                        if (intern.getText().toString().isEmpty()) {
                            intern.setError(getString(R.string.InternProfileCheck));
                            isValid = 0;
                        }
                        if (company.getText().toString().isEmpty()) {
                            company.setError(getString(R.string.CompanyNameCheck));
                            isValid = 0;
                        }
                        if (description.getText().toString().isEmpty()) {
                            description.setError(getString(R.string.DescriptionCheck));
                        }
                        if (duration.getText().toString().isEmpty()) {
                            duration.setError(getString(R.string.DurationCheck));
                        }

                        if (isValid == 1) {
                            Internship internship = new Internship("",
                                    reg_no,
                                    intern.getText().toString(),
                                    company.getText().toString(),
                                    duration.getText().toString(),
                                    description.getText().toString()
                            );
                            dialog.dismiss();
                            AddInternAsyncTask task = new AddInternAsyncTask(getContext());
                            task.execute(internship);

                        }
                    }
                });

            }
        });

        // Construct the data source
        ArrayList<Internship> arrayOfInternships = new ArrayList<Internship>();
        ArrayList<Project> arrayOfProjects = new ArrayList<Project>();
        Intent i = getActivity().getIntent();
        reg_no = i.getStringExtra("reg_no");

        adapter = new InternshipAdapter(view.getContext(), arrayOfInternships);
        projectAdapter = new ProjectAdapter(view.getContext(), arrayOfProjects);
        new InternBackgroundTask(c).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new ProjectBackgroundTask(c).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        ExpandableHeightListView listView = (ExpandableHeightListView) view
                .findViewById(R.id.intenshipsList);
        listView.setAdapter(adapter);
        listView.setExpanded(true);

        ExpandableHeightListView projectListView = (ExpandableHeightListView) view
                .findViewById(R.id.projectsList);
        projectListView.setAdapter(projectAdapter);
        projectListView.setExpanded(true);

        projectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                TextView title = (TextView) view.findViewById(R.id.project);
                TextView duration = (TextView) view.findViewById(R.id.durationView);
                TextView descriptionView = (TextView) view.findViewById(R.id.descriptionView);


                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.projectdialog);

                TextView Dtitle = (TextView) dialog.findViewById(R.id.Dproject);
                TextView Dduration = (TextView) dialog.findViewById(R.id.DdurationView);
                TextView DdescriptionView = (TextView) dialog.findViewById(R.id.DdescriptionView);

                Dtitle.setText(title.getText().toString().trim());
                Dduration.setText(duration.getText().toString().trim());
                DdescriptionView.setText(descriptionView.getText().toString().trim());

                Button OpenProject = (Button) dialog.findViewById(R.id.DviewProject);


                final Project projectItem = projects.get(position);


                OpenProject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = projectItem.getProject_link();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });


                dialog.setTitle("Project");
                dialog.setCancelable(true);
                dialog.show();
            }
        });

        projectListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final Project projectItem = projects.get(position);
                String url = projectItem.getProject_link();


                TextView title = (TextView) view.findViewById(R.id.project);
                TextView duration = (TextView) view.findViewById(R.id.durationView);
                TextView descriptionView = (TextView) view.findViewById(R.id.descriptionView);

                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.projectdialogueedit);

                final EditText Etitle = (EditText) dialog.findViewById(R.id.et_Dproject);
                final EditText Eduration = (EditText) dialog.findViewById(R.id.et_DdurationView);
                final EditText Edescription = (EditText) dialog.findViewById(R.id.et_DdescriptionView);
                final EditText EviewProject = (EditText) dialog.findViewById(R.id.et_DviewProject);

                Button updateBtn = (Button) dialog.findViewById(R.id.DupdateProjectBtn);


                Etitle.setText(title.getText().toString().trim());
                Edescription.setText(descriptionView.getText().toString().trim());
                Eduration.setText(duration.getText().toString().trim());
                EviewProject.setText(projectItem.getProject_link());

                updateBtn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Project project1 = new Project(
                                projectItem.getId(),
                                reg_no,
                                Etitle.getText().toString(),
                                Eduration.getText().toString(),
                                Edescription.getText().toString(),
                                EviewProject.getText().toString()
                        );

                        UpdateProjectAsyncTask updateProjectAsyncTask = new UpdateProjectAsyncTask(getContext());
                        updateProjectAsyncTask.execute(project1);
                    }
                });
                dialog.setTitle("Edit Project");
                dialog.show();
                dialog.setCancelable(true);
                return true;
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {

                TextView internship = (TextView) view.findViewById(R.id.internshipTextView);
                TextView comapny = (TextView) view.findViewById(R.id.companyTextView);
                TextView descriptionTextView = (TextView) view.findViewById(R.id.descriptionTextView);
                TextView durationTextView = (TextView) view.findViewById(R.id.durationTextView);


                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.internshipdialog);

                final Internship internItem = internships.get(position);
                TextView Dinternship = (TextView) dialog.findViewById(R.id.DinternshipTextView);
                TextView Dcomapny = (TextView) dialog.findViewById(R.id.DcompanyTextView);
                TextView DdescriptionTextView = (TextView) dialog.findViewById(R.id.DdescriptionTextView);
                TextView DdurationTextView = (TextView) dialog.findViewById(R.id.DdurationTextView);


                Dinternship.setText(internship.getText().toString().trim());
                Dcomapny.setText(comapny.getText().toString().trim());
                DdurationTextView.setText(durationTextView.getText().toString().trim());
                DdescriptionTextView.setText(descriptionTextView.getText().toString().toString());


                dialog.setTitle("Intern");
                dialog.setCancelable(true);
                dialog.show();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final TextView internship = (TextView) view.findViewById(R.id.internshipTextView);
                TextView comapny = (TextView) view.findViewById(R.id.companyTextView);
                TextView descriptionTextView = (TextView) view.findViewById(R.id.descriptionTextView);
                TextView durationTextView = (TextView) view.findViewById(R.id.durationTextView);

                final Internship internItem = internships.get(position);
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.internshipdialogedit);

                final EditText Einternship = (EditText) dialog.findViewById(R.id.DinternshipeditView);
                final EditText Eduration = (EditText) dialog.findViewById(R.id.DdurationeditView);
                final EditText Edescription = (EditText) dialog.findViewById(R.id.DdescriptioneditView);
                final EditText ECompany = (EditText) dialog.findViewById(R.id.DcompanyeditView);

                Einternship.setText(internship.getText().toString().trim());
                Eduration.setText(durationTextView.getText().toString().trim());
                ECompany.setText(comapny.getText().toString().trim());
                Edescription.setText(descriptionTextView.getText().toString().trim());

                Button button = (Button) dialog.findViewById(R.id.DupdateInternBtn);
                button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Internship internship1 = new Internship(
                                internItem.getId(),
                                reg_no,
                                Einternship.getText().toString(),
                                Eduration.getText().toString(),
                                Edescription.getText().toString(),
                                ECompany.getText().toString()
                        );

                        UpdateInternAsyncTask updateInternAsyncTask = new UpdateInternAsyncTask(getContext());
                        updateInternAsyncTask.execute(internship1);
                    }
                });
                dialog.setTitle("Edit Intern");
                dialog.setCancelable(true);
                dialog.show();
                return true;
            }
        });

         animate();

        return view;
    }

    private void animate() {

        addIntern.setScaleX(0);
        addIntern.setScaleY(0);
        addproject.setScaleX(0);
        addproject.setScaleY(0);
        addproject.animate().scaleX(1).scaleY(1).setDuration(1000).start();
        addIntern.animate().scaleX(1).scaleY(1).setDuration(1000).start();
    }


    public class InternBackgroundTask extends AsyncTask<String, Void, String> {

        String json_url;
        Context ctx;

        InternBackgroundTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            json_url = "http://139.59.5.186/php/internship_info.php";
            Log.d("O_MY", reg_no);
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                //Log.d("O_MY", String.valueOf(httpURLConnection.getResponseCode()));
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(outputStream, "UTF-8"));
                String data =
                        URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(reg_no, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream, "iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();

                String JSON_STRING;
                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            internships = new ArrayList<>();
            try {

                Log.d("O_MY", "Result=" + result);

                JSONObject base_json = new JSONObject(result);
                Log.d("O_MY", base_json.toString());

                JSONArray jsonArray = base_json.getJSONArray("response");
                int i = 0;
                Log.d("O_MY", "length=" + jsonArray.length());
                while (i < jsonArray.length()) {
                    Log.d("O_MY", "pos=" + i);
                    JSONObject intern = jsonArray.getJSONObject(i);
                    Log.d("O_MY", "intern=" + intern.getString("internship"));

                    Internship internship = new Internship(
                            intern.getString("id"),
                            intern.getString("reg_no"),
                            intern.getString("internship"),
                            intern.getString("company"),
                            intern.getString("duration"),
                            intern.getString("description")
                    );
                    Log.d("O_MY", intern.getString("internship") + intern.getString("company"));
                    internships.add(i, internship);

                    i++;
                }
                adapter.addAll(internships);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    public class ProjectBackgroundTask extends AsyncTask<String, Void, String> {

        String json_url;
        Context ctx;

        ProjectBackgroundTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            json_url = "http://139.59.5.186/php/project_info.php";
            Log.d("O_MY", reg_no);
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                //Log.d("O_MY", "Projects httpResponseCode="+String.valueOf(httpURLConnection.getResponseCode()));
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(outputStream, "UTF-8"));
                String data =
                        URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(reg_no, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream, "iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();

                String JSON_STRING;
                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            projects = new ArrayList<>();
            try {

                Log.d("O_MY", "Result=" + result);

                JSONObject base_json = new JSONObject(result);
                Log.d("O_MY", "projects=" + base_json.toString());

                JSONArray jsonArray = base_json.getJSONArray("response");
                int i = 0;
                Log.d("O_MY", "length=" + jsonArray.length());
                while (i < jsonArray.length()) {

                    JSONObject projectItem = jsonArray.getJSONObject(i);


                    Project Project = new Project(
                            projectItem.getString("id"),
                            projectItem.getString("reg_no"),
                            projectItem.getString("project"),
                            projectItem.getString("duration"),
                            projectItem.getString("description"),
                            projectItem.getString("project_link")
                    );
                    projects.add(i, Project);

                    i++;
                }
                projectAdapter.addAll(projects);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    public class UpdateProjectAsyncTask extends AsyncTask<Project, Void, String> {
        String update_url;
        Context ctx;

        UpdateProjectAsyncTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            update_url = "http://139.59.5.186/php/update_project_info.php";
            Log.d("O_MY", reg_no);
        }

        @Override
        protected String doInBackground(Project... params) {


            Project projectItem = params[0];
            URL url = null;
            try {
                url = new URL(update_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                Log.d("O_MY", projectItem.getId() + "ID");
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(outputStream, "UTF-8"));

                String data =
                        URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(projectItem.getId(), "UTF-8")
                                + "&" +
                                URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(projectItem.getReg_no(), "UTF-8")
                                + "&" +
                                URLEncoder.encode("project", "UTF-8") + "=" + URLEncoder.encode(projectItem.getProject(), "UTF-8")
                                + "&" +
                                URLEncoder.encode("duration", "UTF-8") + "=" + URLEncoder.encode(projectItem.getDuration(), "UTF-8")
                                + "&" +
                                URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(projectItem.getDescription(), "UTF-8")
                                + "&" +
                                URLEncoder.encode("project_link", "UTF-8") + "=" + URLEncoder.encode(projectItem.getProject_link(), "UTF-8");
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
            if (s.contains("Project updated successfully")) {
                Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class UpdateInternAsyncTask extends AsyncTask<Internship, Void, String> {
        String update_url;
        Context ctx;

        UpdateInternAsyncTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            update_url = "http://139.59.5.186/php/update_internship_info.php";
            Log.d("O_MY", reg_no);
        }

        @Override
        protected String doInBackground(Internship... params) {


            Internship internshipItem = params[0];
            URL url = null;
            try {
                url = new URL(update_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(internshipItem.getId()
                        , "UTF-8")
                        + "&" +
                        URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(internshipItem.getReg_no(), "UTF-8")
                        + "&" +
                        URLEncoder.encode("internship", "UTF-8") + "=" + URLEncoder.encode(internshipItem.getInternship(), "UTF-8")
                        + "&" +
                        URLEncoder.encode("company", "UTF-8") + "=" + URLEncoder.encode(internshipItem.getCompany(), "UTF-8")
                        + "&" +
                        URLEncoder.encode("duration", "UTF-8") + "=" + URLEncoder.encode(internshipItem.getDuration(), "UTF-8")
                        + "&" +
                        URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(internshipItem.getDescription(), "UTF-8");
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
            if (s.contains("Intern updated successfully")) {
                Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class AddInternAsyncTask extends AsyncTask<Internship, Void, String> {
        String update_url;
        Context ctx;

        AddInternAsyncTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            update_url = "http://139.59.5.186/php/add_internship.php";
            Log.d("O_MY", reg_no);
        }

        @Override
        protected String doInBackground(Internship... params) {


            Internship internshipItem = params[0];
            URL url = null;
            try {
                url = new URL(update_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(internshipItem.getId(), "UTF-8")
                        + "&" +
                        URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(internshipItem.getReg_no(), "UTF-8")
                        + "&" +
                        URLEncoder.encode("internship", "UTF-8") + "=" + URLEncoder.encode(internshipItem.getInternship(), "UTF-8")
                        + "&" +
                        URLEncoder.encode("company", "UTF-8") + "=" + URLEncoder.encode(internshipItem.getCompany(), "UTF-8")
                        + "&" +
                        URLEncoder.encode("duration", "UTF-8") + "=" + URLEncoder.encode(internshipItem.getDuration(), "UTF-8")
                        + "&" +
                        URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(internshipItem.getDescription(), "UTF-8");
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
            if (s.contains("Internship details saved successfully!!")) {
                Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class AddProjectAsyncTask extends AsyncTask<Project, Void, String> {
        String update_url;
        Context ctx;

        AddProjectAsyncTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            update_url = "http://139.59.5.186/php/add_project.php";
            Log.d("O_MY", reg_no);
        }

        @Override
        protected String doInBackground(Project... params) {


            Project projectItem = params[0];
            URL url = null;
            try {
                url = new URL(update_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(projectItem.getId(), "UTF-8")
                        + "&" +
                        URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(projectItem.getReg_no(), "UTF-8")
                        + "&" +
                        URLEncoder.encode("project", "UTF-8") + "=" + URLEncoder.encode(projectItem.getProject(), "UTF-8")
                        + "&" +
                        URLEncoder.encode("duration", "UTF-8") + "=" + URLEncoder.encode(projectItem.getDuration(), "UTF-8")
                        + "&" +
                        URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(projectItem.getDescription(), "UTF-8")
                        + "&" +
                        URLEncoder.encode("project_link", "UTF-8") + "=" + URLEncoder.encode(projectItem.getProject_link(), "UTF-8");
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
            if (s.contains("Project details saved successfully!!")) {
                Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

