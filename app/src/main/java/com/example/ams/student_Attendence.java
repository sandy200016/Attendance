package com.example.ams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;


public class student_Attendence extends AppCompatActivity {
    SQLiteDatabase db;
    Intent get_Sem;
    Bundle b;
    ImageButton fab_Button,fab_Present,fab_Absent,fab_Cancel;
    Animation fab_Open,fab_Close,fab_forward,fab_Backward;
    Boolean isOpen=false,isMarkedAbsent=false;
    SearchView searchView;
    int present_Count=0;
    int absent_Count=0;
    int Attendance_value_subject=1,Attendance_value_practical=3;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_student__attendence);
        fab_Button=(ImageButton)findViewById(R.id.fab_button);
        fab_Absent=(ImageButton)findViewById(R.id.fab_absent);
        fab_Present=(ImageButton)findViewById(R.id.fab_present);
        fab_Cancel=(ImageButton)findViewById(R.id.fab_cancel);


        fab_Open= AnimationUtils.loadAnimation(this,R.anim.fab_open);
        fab_Close= AnimationUtils.loadAnimation(this,R.anim.fab_close);
        fab_forward= AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
        fab_Backward= AnimationUtils.loadAnimation(this,R.anim.rotate_backward);

        db = openOrCreateDatabase("Attendence", Context.MODE_PRIVATE, null);
        get_Sem = getIntent();
        b = get_Sem.getExtras();


        Student_attendance(Attendance_value_subject,Attendance_value_practical);
        Teacher_Attendance(Attendance_value_subject,Attendance_value_practical);



    }
    public void Teacher_Attendance(int Subject_value,int Practical_value){
        String semesterName = b.getString("SEMESTER") + "_ATTENDANCE";
        String tableName = "ATTENDENCE_" + b.getString("SEMESTER") + "_SUBJECT";
        Cursor getAttendance = db.rawQuery("select " + semesterName + " from Students_subject where " + b.getString("SEMESTER") + " ='" + b.getString("SUBJECT") + "' ", null);
        if (getAttendance.moveToNext()) {
            int Attendance = 0;
            try {

                Cursor getData = db.rawQuery("select " + b.getString("SUBJECT") + " from " + tableName + " ", null); // Gives Error when user select Practical Subjects Catch  code executes
                Attendance = getAttendance.getInt(0) + Subject_value;
            }
            catch (Exception e) {
                Attendance = getAttendance.getInt(0) + Practical_value;

            }

            db.execSQL("update Students_subject set " + semesterName + " = '" + Attendance + "' where " + b.getString("SEMESTER") + "='" + b.getString("SUBJECT") + "'");

        }

    }
    public void Student_attendance(int Subject_value,int Practical_value){
        Cursor c1 = db.rawQuery("SELECT Student_Name,Student_Rollno From ATTENDENCE_" + b.getString("SEMESTER") + "_SUBJECT ORDER BY Student_Rollno;", null);
        int count = 0;
        String Idno;
        StudentList[] listData;
        listData = new StudentList[c1.getCount()];
        int i = 0;
        while (c1.moveToNext()) {
            Idno = "" + c1.getInt(1);
            listData[i] = new StudentList(c1.getString(0), Idno);
            i++;
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        MyListAdapter adapter = new MyListAdapter(listData, b.getString("SUBJECT"), b.getString("SEMESTER"),Subject_value,Practical_value);
        recyclerView.setAdapter(adapter);

    }
    private void animateFab(){
        if(isOpen){
            fab_Button.startAnimation(fab_Backward);
            fab_Present.startAnimation(fab_Close);
            fab_Absent.startAnimation(fab_Close);
            fab_Cancel.startAnimation(fab_Close);

            fab_Present.setClickable(false);
            fab_Absent.setClickable(false);
            fab_Cancel.setClickable(false);


            isOpen=false;
        }
        else{
            fab_Button.startAnimation(fab_forward);
            fab_Present.startAnimation(fab_Open);
            fab_Absent.startAnimation(fab_Open);
            fab_Cancel.startAnimation(fab_Open);

            fab_Present.setClickable(true);
            fab_Absent.setClickable(true);
            fab_Cancel.setClickable(true);

            isOpen=true;

        }

    }

    public void fab_Cancel(View view) {

        getSupportActionBar().setTitle("AMS");
        Teacher_Attendance(-1,-3);
        if(isMarkedAbsent && absent_Count==2){
            Teacher_Attendance(-1,-3);
            MarkedAbsent(-2,-6);
            isMarkedAbsent=false;
        }
        else if(isMarkedAbsent){
            MarkedAbsent(-1,-3);
            isMarkedAbsent=false;
        }

        Toast.makeText(this,"Attendance has been canceled",Toast.LENGTH_LONG).show();
        finish();

    }

    public void fab_Absent(View view) {

        animateFab();
        absent_Count++;
        if(absent_Count>2){

            Toast.makeText(this,"You have already added +1 Subject",Toast.LENGTH_LONG).show();
            getSupportActionBar().setTitle("Absent +1");
        }
        else if(absent_Count==2){
            Teacher_Attendance(1,3);
            MarkedAbsent(1,3);
            Student_attendance(-2,-6);
            getSupportActionBar().setTitle("Absent +1");

        }
        else {
            MarkedAbsent(1,3);
            Student_attendance(-1,-3);
            Toast.makeText(this,"Absent",Toast.LENGTH_LONG).show();
            getSupportActionBar().setTitle("Absent");
        }

        isMarkedAbsent=true;
    }

    public void fab_Present(View view) {
        animateFab();
        present_Count++;
        getSupportActionBar().setTitle("Present");
        if(isMarkedAbsent && absent_Count==2){
            MarkedAbsent(-2,-6);
            Student_attendance(2,6);
            isMarkedAbsent=false;
        }
        else if(isMarkedAbsent){
            MarkedAbsent(-1,-3);
            Student_attendance(1,3);
            isMarkedAbsent=false;
        }
        else if(present_Count>2){
            Toast.makeText(this,"You have already added +1 Subject",Toast.LENGTH_LONG).show();
            getSupportActionBar().setTitle("Present +1");
        }
        else if(present_Count==2){
            Teacher_Attendance(1,3);
            Student_attendance(2,6);
            getSupportActionBar().setTitle("Present +1");
        }

        else {
            Student_attendance(1,3);
        }
        Toast.makeText(this,"Present",Toast.LENGTH_LONG).show();
    }

    public void fab_Button(View view) {
        animateFab();
        getSupportActionBar().setTitle("AMS");

    }


    public void MarkedAbsent(int Subject,int Practical){
        String Subject_table="ATTENDENCE_" + b.getString("SEMESTER") + "_SUBJECT";
        String Subject_Practical="ATTENDENCE_" + b.getString("SEMESTER") + "_PRACTICAL";
        int Attendance_value=0;
        Cursor getStudentAttendance;

        try{

            getStudentAttendance=db.rawQuery("select " +b.getString("SUBJECT")+",Student_Rollno from "+Subject_table+"",null);
            while (getStudentAttendance.moveToNext()){
                Attendance_value=getStudentAttendance.getInt(0)+Subject;
                db.execSQL("update "+Subject_table +" set "+b.getString("SUBJECT")+"='"+Attendance_value+"' where Student_Rollno='"+getStudentAttendance.getString(1)+"'");
                db.execSQL("update "+Subject_table +" set TOTAL_ATTENDENCE='"+Attendance_value+"' where Student_Rollno='"+getStudentAttendance.getString(1)+"'");
            }
        }catch (Exception ex){

            getStudentAttendance=db.rawQuery("select " +b.getString("SUBJECT")+",Student_Rollno from "+Subject_Practical+"",null);
            while (getStudentAttendance.moveToNext()){
                Attendance_value=getStudentAttendance.getInt(0)+Practical;
                db.execSQL("update "+Subject_Practical +" set "+b.getString("SUBJECT")+"='"+Attendance_value+"' where Student_Rollno='"+getStudentAttendance.getString(1)+"'");
                db.execSQL("update "+Subject_Practical +" set TOTAL_ATTENDENCE='"+Attendance_value+"' where Student_Rollno='"+getStudentAttendance.getString(1)+"'");
            }

        }
    }


/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem item=menu.findItem(R.id.search_recycler);
        searchView= (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                int rollno;
                rollno=Integer.parseInt(query);

                 if(isMarkedAbsent){

                    Student_attendance_search(-1,-3,rollno);
                    isMarkedAbsent=false;

                }

                else {

                    Student_attendance_search(1,3,rollno);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }*/
    /*public void Student_attendance_search(int Subject_value,int Practical_value,int Rollno){
        Cursor c1 = db.rawQuery("SELECT Student_Name,Student_Rollno From ATTENDENCE_" + b.getString("SEMESTER") + "_SUBJECT where Student_Rollno='"+Rollno+"';", null);
        int count = 0;
        String Idno;
        StudentList[] listData;
        listData = new StudentList[c1.getCount()];
        int i = 0;
        while (c1.moveToNext()) {
            Idno = "" + c1.getInt(1);
            listData[i] = new StudentList(c1.getString(0), Idno);
            i++;
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        MyListAdapter adapter = new MyListAdapter(listData, b.getString("SUBJECT"), b.getString("SEMESTER"),Subject_value,Practical_value);
        recyclerView.setAdapter(adapter);

    }*/

    /*@Override
    public void onBackPressed() {
        if(!searchView.isIconified()){
            searchView.setIconified(true);
        }
        else {
            super.onBackPressed();
        }

    }*/
}
