package com.example.ams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class student_Subjects extends AppCompatActivity {
    SQLiteDatabase db;
    String semester = null;
    String Teacher_Name = null;
    String Teacher_ID = null;
    SimpleDateFormat attendance_date_formater = null;
    SimpleDateFormat getAttendance_time_formater = null;
    Date attendance_date = null;
    String Attendance_Date = null;
    String Attendance_Time = null;
    StringRequest stringRequest = null;
    int Rename_count = 0;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = openOrCreateDatabase("Attendence", Context.MODE_PRIVATE, null);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__subjects);
        Intent get_Sem = getIntent();
        Bundle b = get_Sem.getExtras();
        semester = b.getString("SEM");
        Teacher_Name = b.getString("Name");
        Teacher_ID = b.getString("ID");
        getSupportActionBar().setTitle(b.getString("SEM"));
        addSubject(b.getString("SEM"));
     /*   getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_appbar);*/
    }

    public void addSubject(String SEM) {
        Cursor c = null;
        final ListView subject = (ListView) findViewById(R.id.subjects);
        final ArrayList<String> Subjects_list = new ArrayList<>();
        if (SEM.equals("SEM1")) {
            c = db.rawQuery("select SEM1 from Students_subject where SEM1 is not null", null);
        } else if (SEM.equals("SEM2")) {
            c = db.rawQuery("select SEM2 from Students_subject where SEM2 is not null", null);
        } else if (SEM.equals("SEM3")) {
            c = db.rawQuery("select SEM3 from Students_subject where SEM3 is not null", null);
        } else if (SEM.equals("SEM4")) {
            c = db.rawQuery("select SEM4 from Students_subject where SEM4 is not null", null);
        } else if (SEM.equals("SEM5")) {
            c = db.rawQuery("select SEM5 from Students_subject where SEM5 is not null", null);
        } else if (SEM.equals("SEM6")) {
            c = db.rawQuery("select SEM6 from Students_subject where SEM6 is not null", null);
        }
        while (c.moveToNext()) {
            Subjects_list.add(c.getString(0));
        }
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Subjects_list);
        subject.setAdapter(arrayAdapter);
        subject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = subject.getItemAtPosition(position).toString();
                if (Rename_count == 1) {
                    Toast.makeText(student_Subjects.this, "Hello", Toast.LENGTH_LONG).show();
                    rename_Subject(clickedItem);
                    Rename_count = 0;
                }
                else if(Rename_count==2){
                    show_Subject_Attendance(clickedItem);
                    Rename_count=0;

                }
                else {
                    attendance_date = new Date();
                    attendance_date_formater = new SimpleDateFormat("dd-MM-yyyy");
                    Attendance_Date = attendance_date_formater.format(attendance_date);
                    getAttendance_time_formater = new SimpleDateFormat("HH:mm");
                    Attendance_Time = getAttendance_time_formater.format(attendance_date);
                    teacher_Attendance(clickedItem);
                    Bundle b = new Bundle();
                    b.putString("SUBJECT", clickedItem);
                    b.putString("SEMESTER", semester);
                    Intent in = new Intent(student_Subjects.this, student_Attendence.class);
                    in.putExtras(b);
                    startActivity(in);
                }

            }

        });

    }

    public void teacher_Attendance(final String Subject_Name) {


        stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbxTsjnzHJwB3IUT3zp0Vwyjzq3wpbzXmEvEZq_zDgZArG-JnFk/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();
                parmas.put("action", "Teacher_Attendance");
                parmas.put("Date", Attendance_Date);
                parmas.put("Time", Attendance_Time);
                parmas.put("Name", Teacher_Name);
                parmas.put("ID", Teacher_ID);
                parmas.put("SEM", semester);
                parmas.put("Subject", Subject_Name);
                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.subjects, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.rename_subjects) {
            Rename_count = 1;
            Toast.makeText(this, "Rename Subjects", Toast.LENGTH_LONG).show();
        }
        if (id == R.id.show_Attendance) {
            Rename_count=2;
            Toast.makeText(this, "Show Attendance", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void rename_Subject(final String subjectName) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.popup_rename_subject, viewGroup, false);
        final EditText getSubjectName = (EditText) dialogView.findViewById(R.id.et_rename_subject);
        Button rename = (Button) dialogView.findViewById(R.id.bt_rename_subject);
       // getSubjectName.setHint(subjectName);
        rename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getSubjectName.getText().toString().isEmpty()) {
                    Toast.makeText(student_Subjects.this, "Enter Subject Name", Toast.LENGTH_LONG).show();
                } else if (isNumeric(getSubjectName.getText().toString())) {
                    Toast.makeText(student_Subjects.this, "No is Not Allowed", Toast.LENGTH_LONG).show();
                } else if (specialCharacter(getSubjectName.getText().toString())) {
                    Toast.makeText(student_Subjects.this, "Special character not Allowed", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(student_Subjects.this,semester+" "+getSubjectName.getText().toString()+" "+subjectName,Toast.LENGTH_LONG).show();

                    String SubjectName = "ATTENDENCE_" + semester + "_SUBJECT";
                    String PracticalName = "ATTENDENCE_" + semester + "_PRACTICAL";
                    String semesterAttendance=semester+"_ATTENDANCE";
                    Cursor getSubject =db.rawQuery("select "+semester+" from Students_subject where "+semester+"='"+getSubjectName.getText().toString()+"'",null);
                    if(getSubject.moveToNext()){
                        Toast.makeText(student_Subjects.this, "Subject Name Already Exists", Toast.LENGTH_LONG).show();
                    }else {

                        db.execSQL("update Students_subject set "+semesterAttendance+"=0 where "+semester+"='"+subjectName+"'");
                        db.execSQL("update Students_subject set " + semester + " = '" + getSubjectName.getText().toString() + "' where " + semester + " = '" + subjectName + "'");


                       try {
                           Cursor getData = db.rawQuery("select " + subjectName + " from " + SubjectName + " ", null);
                           db.execSQL("ALTER TABLE "+SubjectName+" ADD COLUMN "+getSubjectName.getText().toString()+" INTEGER DEFAULT 0");
                           getSubjectName.getText().clear();
                            Toast.makeText(student_Subjects.this, "Subject Name Successfully Renamed", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {

                            db.execSQL("ALTER TABLE "+PracticalName+" ADD COLUMN "+getSubjectName.getText().toString()+" INTEGER DEFAULT 0");
                            Toast.makeText(student_Subjects.this, "Practical Name Successfully Renamed", Toast.LENGTH_LONG).show();
                            getSubjectName.getText().clear();
                        }
                    }


                }
                addSubject(semester);

            }
        });
        builder1.setView(dialogView);
        final AlertDialog alertDialog = builder1.create();
        alertDialog.show();

    }

    public static boolean specialCharacter(final String str) {
        String Specialchar = "!@#$%^&*()-=+/*?|][}{><.,;'':";
        for (int i = 0; i < str.length(); i++) {
            if (Specialchar.contains(Character.toString(str.charAt(i)))) {
                return true;
            }

        }
        return false;
    }

    public static boolean isNumeric(final String str) {
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
    public void show_Subject_Attendance(String Subject_Name){
        Intent in = new Intent(student_Subjects.this, Attendance_subject.class);
        Bundle putSemester=new Bundle();
        putSemester.putString("Semester",semester);
        putSemester.putString("Subject",Subject_Name);
        in.putExtras(putSemester);
        startActivity(in);

    }
}
