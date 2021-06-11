package com.example.ams;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class student_Dashboard extends AppCompatActivity {
    SQLiteDatabase db;
    PieChart pieChart;
    Context c;
    RecyclerView recyclerView = null;
    String SEMESTER = null;
    subjectAttendenceData[] listdata;
    String TableName = null, TableName1 = null;
    Intent get_Standard = null;
    Bundle Standard = null;
    int total_Score = 0, present_Score = 0, absent_Score = 0;
    TextView Student_Name;
    SpacingItemDecorater spacingItemDecorater;
    int posistion = 0, count = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = openOrCreateDatabase("Attendence", Context.MODE_PRIVATE, null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__dashboard);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        recyclerView = (RecyclerView) findViewById(R.id.attendence_graph);
        spacingItemDecorater = new SpacingItemDecorater(15);
        recyclerView.addItemDecoration(spacingItemDecorater);
        Student_Name = (TextView) findViewById(R.id.student_Name);
        c = getApplicationContext();
        get_Standard = getIntent();
        Standard = get_Standard.getExtras();
        Student_Name.setText(Standard.getString("Student_Name"));
        getSupportActionBar().setTitle("Dashboard");
        first_Sem();

    }

    public void first_Sem() {

        int Present = Math.round(calculateAttendence1(Standard.getString("Class"), Standard.getString("Student_Id")));
        int Absent = 100 - Present;
        grphPercentage(Present, Absent);

        Cursor getSubjectName = db.rawQuery("select " + SEMESTER + " from Students_subject where " + SEMESTER + " is not null;", null);
        listdata = new subjectAttendenceData[getSubjectName.getCount()];
        int i = 0;
        TableName = "ATTENDENCE_" + SEMESTER + "_SUBJECT";
        TableName1 = "ATTENDENCE_" + SEMESTER + "_PRACTICAL";

        while (getSubjectName.moveToNext()) {
            perSubjectPercentage(getSubjectName.getString(0), TableName, TableName1, Standard.getString("Student_Id"), i);
            i++;
        }
        student_Attendence_Adapter adapter = new student_Attendence_Adapter(listdata);
        recyclerView.setAdapter(adapter);
        posistion = adapter.getItemCount() - 1;

        dashBoard_Score();
        slowdown(1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.update_details) {
            Toast.makeText(this, "Update Details", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void next_Sem() {
        int Present = Math.round(calculateAttendence2(Standard.getString("Class"), Standard.getString("Student_Id")));
        int Absent = 100 - Present;
        grphPercentage(Present, Absent);
        Cursor getSubjectName = db.rawQuery("select " + SEMESTER + " from Students_subject where " + SEMESTER + " is not null;", null);
        listdata = new subjectAttendenceData[getSubjectName.getCount()];
        int i = 0;
        TableName = "ATTENDENCE_" + SEMESTER + "_SUBJECT";
        TableName1 = "ATTENDENCE_" + SEMESTER + "_PRACTICAL";

        while (getSubjectName.moveToNext()) {
            perSubjectPercentage(getSubjectName.getString(0), TableName, TableName1, Standard.getString("Student_Id"), i);
            i++;
        }
        final student_Attendence_Adapter adapter = new student_Attendence_Adapter(listdata);
        recyclerView.setAdapter(adapter);
        posistion = adapter.getItemCount() - 1;

        dashBoard_Score();
        slowdown(1);


    }

    public Float calculateAttendence1(String Class, String Student_Id) {
        float overallAttendance = 0, studentSubject = 0, studentPractical = 0, Result = 0, studentAttendence = 0;
        if (Class.equals("Student_FY")) {
            Cursor getAttendance = db.rawQuery("select sum(SEM1_ATTENDANCE) from Students_subject", null);
            if (getAttendance.moveToNext()) {
                overallAttendance = getAttendance.getFloat(0);
            }

            Cursor getStudentSubject = db.rawQuery("select TOTAL_ATTENDENCE from ATTENDENCE_SEM1_SUBJECT where Student_Id='" + Student_Id + "';", null);
            if (getStudentSubject.moveToNext()) {
                studentSubject = getStudentSubject.getFloat(0);
            }
            Cursor getPracticalAttendence = db.rawQuery("select TOTAL_ATTENDENCE from ATTENDENCE_SEM1_PRACTICAL where Student_Id='" + Student_Id + "'", null);
            if (getPracticalAttendence.moveToNext()) {
                studentPractical = getPracticalAttendence.getFloat(0);
            }
            studentAttendence = studentSubject + studentPractical;
            Result = ((studentAttendence / overallAttendance) * 100);
            SEMESTER = "SEM1";

        }
        if (Class.equals("Student_SY")) {
            Cursor getAttendance = db.rawQuery("select sum(SEM3_ATTENDANCE) from Students_subject", null);
            if (getAttendance.moveToNext()) {
                overallAttendance = getAttendance.getFloat(0);
            }

            Cursor getStudentSubject = db.rawQuery("select TOTAL_ATTENDENCE from ATTENDENCE_SEM3_SUBJECT where Student_Id='" + Student_Id + "';", null);
            if (getStudentSubject.moveToNext()) {
                studentSubject = getStudentSubject.getFloat(0);
            }
            Cursor getPracticalAttendence = db.rawQuery("select TOTAL_ATTENDENCE from ATTENDENCE_SEM3_PRACTICAL where Student_Id='" + Student_Id + "'", null);
            if (getPracticalAttendence.moveToNext()) {
                studentPractical = getPracticalAttendence.getFloat(0);
            }
            studentAttendence = studentSubject + studentPractical;
            Result = ((studentAttendence / overallAttendance) * 100);
            SEMESTER = "SEM3";
        }

        if (Class.equals("Student_TY")) {
            Cursor getAttendance = db.rawQuery("select sum(SEM5_ATTENDANCE) from Students_subject", null);
            if (getAttendance.moveToNext()) {
                overallAttendance = getAttendance.getFloat(0);
            }

            Cursor getStudentSubject = db.rawQuery("select TOTAL_ATTENDENCE from ATTENDENCE_SEM5_SUBJECT where Student_Id='" + Student_Id + "';", null);
            if (getStudentSubject.moveToNext()) {
                studentSubject = getStudentSubject.getFloat(0);
            }
            Cursor getPracticalAttendence = db.rawQuery("select TOTAL_ATTENDENCE from ATTENDENCE_SEM5_PRACTICAL where Student_Id='" + Student_Id + "'", null);
            if (getPracticalAttendence.moveToNext()) {
                studentPractical = getPracticalAttendence.getFloat(0);
            }
            studentAttendence = studentSubject + studentPractical;
            Result = ((studentAttendence / overallAttendance) * 100);
            SEMESTER = "SEM5";

        }
        present_Score = (int) studentAttendence;
        total_Score = (int) overallAttendance;
        absent_Score = (int) (overallAttendance - studentAttendence);
        return Result;
    }

    public Float calculateAttendence2(String Class, String Student_Id) {
        float overallAttendance = 0, studentSubject = 0, studentPractical = 0, Result = 0, studentAttendence = 0;
        if (Class.equals("Student_FY")) {
            Cursor getAttendance = db.rawQuery("select sum(SEM2_ATTENDANCE) from Students_subject", null);
            if (getAttendance.moveToNext()) {
                overallAttendance = getAttendance.getFloat(0);
            }

            Cursor getStudentSubject = db.rawQuery("select TOTAL_ATTENDENCE from ATTENDENCE_SEM2_SUBJECT where Student_Id='" + Student_Id + "';", null);
            if (getStudentSubject.moveToNext()) {
                studentSubject = getStudentSubject.getFloat(0);
            }
            Cursor getPracticalAttendence = db.rawQuery("select TOTAL_ATTENDENCE from ATTENDENCE_SEM2_PRACTICAL where Student_Id='" + Student_Id + "'", null);
            if (getPracticalAttendence.moveToNext()) {
                studentPractical = getPracticalAttendence.getFloat(0);
            }
            studentAttendence = studentSubject + studentPractical;
            Result = ((studentAttendence / overallAttendance) * 100);
            SEMESTER = "SEM2";
        }
        if (Class.equals("Student_SY")) {
            Cursor getAttendance = db.rawQuery("select sum(SEM4_ATTENDANCE) from Students_subject", null);
            if (getAttendance.moveToNext()) {
                overallAttendance = getAttendance.getFloat(0);
            }

            Cursor getStudentSubject = db.rawQuery("select TOTAL_ATTENDENCE from ATTENDENCE_SEM4_SUBJECT where Student_Id='" + Student_Id + "';", null);
            if (getStudentSubject.moveToNext()) {
                studentSubject = getStudentSubject.getFloat(0);
            }
            Cursor getPracticalAttendence = db.rawQuery("select TOTAL_ATTENDENCE from ATTENDENCE_SEM4_PRACTICAL where Student_Id='" + Student_Id + "'", null);
            if (getPracticalAttendence.moveToNext()) {
                studentPractical = getPracticalAttendence.getFloat(0);
            }
            studentAttendence = studentSubject + studentPractical;
            Result = ((studentAttendence / overallAttendance) * 100);
            SEMESTER = "SEM4";
        }

        if (Class.equals("Student_TY")) {
            Cursor getAttendance = db.rawQuery("select sum(SEM6_ATTENDANCE) from Students_subject", null);
            if (getAttendance.moveToNext()) {
                overallAttendance = getAttendance.getFloat(0);
            }
            Cursor getStudentSubject = db.rawQuery("select TOTAL_ATTENDENCE from ATTENDENCE_SEM6_SUBJECT where Student_Id='" + Student_Id + "';", null);
            if (getStudentSubject.moveToNext()) {
                studentSubject = getStudentSubject.getFloat(0);
            }
            Cursor getPracticalAttendence = db.rawQuery("select TOTAL_ATTENDENCE from ATTENDENCE_SEM6_PRACTICAL where Student_Id='" + Student_Id + "'", null);
            if (getPracticalAttendence.moveToNext()) {
                studentPractical = getPracticalAttendence.getFloat(0);
            }
            studentAttendence = studentSubject + studentPractical;
            Result = ((studentAttendence / overallAttendance) * 100);
            SEMESTER = "SEM6";

        }
        present_Score = (int) studentAttendence;
        total_Score = (int) overallAttendance;
        absent_Score = (int) (overallAttendance - studentAttendence);
        return Result;
    }

    public void perSubjectPercentage(String Subject_Name, String tableName, String tableName1, String Student_ID, int i) {
        int setFlag = 0, SubjectPresentPercentage = 0, SubjectAbsentPercentage = 0;
        float teacherAttendance;
        float teacherTotal = 0, StudentTotal = 0;
        float OverAllPercentage = 0;
        Cursor getTeacherAttendence = db.rawQuery("select " + SEMESTER + "_ATTENDANCE from Students_subject where " + SEMESTER + "='" + Subject_Name + "'", null);
        if (getTeacherAttendence.moveToNext()) {
            teacherAttendance = getTeacherAttendence.getFloat(0);
            if (teacherAttendance == 0) {
                setFlag = 0;

            } else {

                setFlag = 1;
                teacherTotal = teacherAttendance;
            }

        }
        try {
            Cursor getStudentAttendence = db.rawQuery("select " + Subject_Name + " from " + TableName + " where Student_Id=" + Student_ID + "", null);
            if (getStudentAttendence.moveToNext()) {

                StudentTotal = getStudentAttendence.getFloat(0);
            }
        } catch (Exception ex) {
            Cursor getStudentAttendence = db.rawQuery("select " + Subject_Name + " from " + TableName1 + " where Student_Id=" + Student_ID + "", null);
            if (getStudentAttendence.moveToNext()) {
                StudentTotal = getStudentAttendence.getFloat(0);

            }

        }
        if (setFlag == 0) {

            listdata[i] = new subjectAttendenceData(Subject_Name, 0, 100);
        } else {
            OverAllPercentage = (StudentTotal / teacherTotal) * 100;
            SubjectPresentPercentage = Math.round(OverAllPercentage);
            SubjectAbsentPercentage = 100 - SubjectPresentPercentage;
            listdata[i] = new subjectAttendenceData(Subject_Name, SubjectPresentPercentage, SubjectAbsentPercentage);
        }
        i++;
    }

    public void grphPercentage(int Present, int Absent) {
        String present = "" + Present;
        pieChart = (PieChart) findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);
        pieChart.setExtraOffsets(0, 0, 0, 0);
        pieChart.setDragDecelerationFrictionCoef(0.99f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setCenterText(present + "%" + "\nAttendence");
        pieChart.setCenterTextSize(14);
        pieChart.setCenterTextColor(Color.WHITE);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setHoleRadius(75);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutQuad);
        pieChart.getLegend().setEnabled(false);
        // pieChart.setDrawSlicesUnderHole(true);
        pieChart.invalidate();
        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(Present, ""));
        yValues.add(new PieEntry(Absent, ""));
        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(3f);
        dataSet.setColors(new int[]{R.color.green, R.color.red}, c);
        PieData data = new PieData(dataSet);
        data.setValueTextSize(0f);
        pieChart.setData(data);
    }


    public void showFirstSem(View view) {

        first_Sem();
    }

    public void showSecondSEM(View view) {

        next_Sem();
    }

    public void dashBoard_Score() {
        TextView Total, Present, Absent;
        String Total_score, Present_score, Absent_score;
        Total_score = "" + total_Score;
        Present_score = "" + present_Score;
        Absent_score = "" + absent_Score;
        Total = (TextView) findViewById(R.id.Total_score);
        Present = (TextView) findViewById(R.id.Attended_score);
        Absent = (TextView) findViewById(R.id.Absent_score);
        Total.setText(Total_score);
        Present.setText(Present_score);
        Absent.setText(Absent_score);

    }

    public void slowdown(final int value) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                recyclerView.smoothScrollToPosition(value);
            }
        }, 2000);

    }

}