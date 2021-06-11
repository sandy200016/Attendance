package com.example.ams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieAnimationView;

public class Attendance_subject extends AppCompatActivity {
    Intent get_Details=null;
    Bundle Get_Data=null;
    SQLiteDatabase db=null;
    subject_Attendance_list[] listData;
    String Total=null;
    SearchView searchView;
    boolean  solo_subject=false;
    LottieAnimationView animationView;
    TextView Message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_subject);
        db = openOrCreateDatabase("Attendence", Context.MODE_PRIVATE, null);
        get_Details = getIntent();
        Get_Data = get_Details.getExtras();
        subject_Attendance();
        getSupportActionBar().setTitle("Total: "+Total);

        animationView=(LottieAnimationView)findViewById(R.id.animate_msg);
        Message=(TextView) findViewById(R.id.message);

    }

    public void subject_Attendance() {
        String Column_name = Get_Data.getString("Semester") + "_ATTENDANCE";
        String Table_Name = "ATTENDENCE_" + Get_Data.getString("Semester") + "_SUBJECT";
        String Table_Name1 = "ATTENDENCE_" + Get_Data.getString("Semester") + "_PRACTICAL";
        float Percentage=0;
        float total_Present=0, Absent=0;
        int i = 0;

        Cursor get_Total_Present = db.rawQuery("select " + Column_name + " from Students_subject where " + Get_Data.getString("Semester") + " = '" + Get_Data.getString("Subject") + "'", null);
        if(get_Total_Present.moveToNext()){
            total_Present = get_Total_Present.getFloat(0);
            Total = "" + total_Present;
        }



        try {
            Cursor get_Present_Value = db.rawQuery("select Student_Name , " + Get_Data.getString("Subject") + " from " + Table_Name + " order by Student_Rollno", null);
            listData = new subject_Attendance_list[get_Present_Value.getCount()];
            while (get_Present_Value.moveToNext()) {
                Percentage = ((get_Present_Value.getFloat(1)) / total_Present) * 100;
                Absent = 100 - Percentage;
                listData[i] = new subject_Attendance_list(get_Present_Value.getString(0), Math.round(Percentage), Math.round(Absent),get_Present_Value.getInt(1));
                i++;
            }

        } catch (Exception e) {
            Cursor get_Present_Value = db.rawQuery("select Student_Name , " + Get_Data.getString("Subject") + " from " + Table_Name1 + " order by Student_Rollno", null);
            listData = new subject_Attendance_list[get_Present_Value.getCount()];
            while (get_Present_Value.moveToNext()) {
                Percentage = ((get_Present_Value.getFloat(1)) / total_Present) * 100;
                Absent = 100 - Percentage;
                listData[i] = new subject_Attendance_list(get_Present_Value.getString(0), Math.round(Percentage), Math.round(Absent),get_Present_Value.getInt(1));
                i++;
            }

        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.subject_Attendance_recycler);
        subject_Attendance_Adapter adapter = new subject_Attendance_Adapter(listData);
        recyclerView.setAdapter(adapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem item=menu.findItem(R.id.search_recycler);
        searchView= (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                int rollno;
                try{
                    rollno=Integer.parseInt(query);
                    subject_Attendance(rollno);
                    animationView.playAnimation();

                }
                catch (Exception ex){

                    Toast.makeText(Attendance_subject.this,"Please Enter Student Rollno",Toast.LENGTH_SHORT).show();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    public void subject_Attendance(int Rollno) {
        solo_subject=true;
        String Column_name = Get_Data.getString("Semester") + "_ATTENDANCE";
        String Table_Name = "ATTENDENCE_" + Get_Data.getString("Semester") + "_SUBJECT";
        String Table_Name1 = "ATTENDENCE_" + Get_Data.getString("Semester") + "_PRACTICAL";
        float Percentage=0;
        float total_Present=0, Absent=0;
        int i = 0;

        Cursor get_Total_Present = db.rawQuery("select " + Column_name + " from Students_subject where " + Get_Data.getString("Semester") + " = '" + Get_Data.getString("Subject") + "'", null);
        if(get_Total_Present.moveToNext()){
            total_Present = get_Total_Present.getFloat(0);
            Total = "" + total_Present;
        }


        try {
            Cursor get_Present_Value = db.rawQuery("select Student_Name , " + Get_Data.getString("Subject") + " from " + Table_Name + " where Student_Rollno= '"+Rollno+"'", null);
            listData = new subject_Attendance_list[get_Present_Value.getCount()];
            if(get_Present_Value.getCount()==0){
                animationView.setVisibility(View.VISIBLE);
                animationView.playAnimation();
                animationView.setAnimation(R.raw.user_not_found1);
                Message.setText("User not found");
                //Toast.makeText(this,"Data Not Found",Toast.LENGTH_LONG).show();
            }
            else{

                while (get_Present_Value.moveToNext()) {

                    animationView.setVisibility(View.INVISIBLE);
                    Message.setText("");
                    Percentage = ((get_Present_Value.getFloat(1)) / total_Present) * 100;
                    Absent = 100 - Percentage;
                    listData[i] = new subject_Attendance_list(get_Present_Value.getString(0), Math.round(Percentage), Math.round(Absent),get_Present_Value.getInt(1));
                    i++;
                }
            }


        } catch (Exception e) {

            Cursor get_Present_Value = db.rawQuery("select Student_Name , " + Get_Data.getString("Subject") + " from " + Table_Name1 + " where Student_Rollno= '"+Rollno+"'", null);
            listData = new subject_Attendance_list[get_Present_Value.getCount()];
            if(get_Present_Value.getCount()==0){
                animationView.setVisibility(View.VISIBLE);
                animationView.playAnimation();
                animationView.setAnimation(R.raw.user_not_found1);
                Message.setText("User not found");
               // Toast.makeText(this,"Data Not Found",Toast.LENGTH_LONG).show();
            }
            else {

                while (get_Present_Value.moveToNext()) {
                    animationView.setVisibility(View.INVISIBLE);
                    Message.setText("");
                    Percentage = ((get_Present_Value.getFloat(1)) / total_Present) * 100;
                    Absent = 100 - Percentage;
                    listData[i] = new subject_Attendance_list(get_Present_Value.getString(0), Math.round(Percentage), Math.round(Absent),get_Present_Value.getInt(1));
                    i++;
                }

            }

        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.subject_Attendance_recycler);
        subject_Attendance_Adapter adapter = new subject_Attendance_Adapter(listData);
        recyclerView.setAdapter(adapter);

    }
    public void onBackPressed() {

        if(!searchView.isIconified()){
            searchView.setIconified(true);
        }
             else {

            super.onBackPressed();

        }

    }

}