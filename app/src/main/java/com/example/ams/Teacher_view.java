package com.example.ams;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Teacher_view extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    SQLiteDatabase db;
    Intent in = null;
    Bundle b = null;
    Intent get_Teacher_Data=null;
    Bundle fetch_teachet_data=null;
    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mtoggle;
    NavigationView navigationView;

    View v;
    AlertDialog.Builder builder;
    TextView Teacher_Name,Teacher_Wish;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = openOrCreateDatabase("Attendence", Context.MODE_PRIVATE, null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mdrawerlayout = (DrawerLayout) findViewById(R.id.drawer);
        mtoggle = new ActionBarDrawerToggle(this, mdrawerlayout, R.string.open, R.string.close);
        mdrawerlayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        View hView=navigationView.getHeaderView(0);
        get_Teacher_Data=getIntent();
        fetch_teachet_data=get_Teacher_Data.getExtras(); //fetch teachers data eg(Name,Id)
        Teacher_Name=(TextView)hView.findViewById(R.id.header_teacher_name);
        Teacher_Name.setText(fetch_teachet_data.getString("Name"));
        Teacher_Wish=(TextView)hView.findViewById(R.id.wish_teacher);

        in = new Intent(Teacher_view.this, student_Subjects.class);
        b = new Bundle();
        v=new View(this);
        getTime();

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (mtoggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void SEM1(View view) {
        b.putString("SEM", "SEM1");
        b.putString("Name",fetch_teachet_data.getString("Name"));
        b.putString("ID",fetch_teachet_data.getString("ID"));
        in.putExtras(b);
        startActivity(in);
    }

    public void SEM2(View view) {
        b.putString("SEM", "SEM2");
        b.putString("Name",fetch_teachet_data.getString("Name"));
        b.putString("ID",fetch_teachet_data.getString("ID"));
        in.putExtras(b);
        startActivity(in);
    }

    public void SEM3(View view) {
        b.putString("SEM", "SEM3");
        b.putString("Name",fetch_teachet_data.getString("Name"));
        b.putString("ID",fetch_teachet_data.getString("ID"));
        in.putExtras(b);
        startActivity(in);
    }

    public void SEM4(View view) {
        b.putString("SEM", "SEM4");
        b.putString("Name",fetch_teachet_data.getString("Name"));
        b.putString("ID",fetch_teachet_data.getString("ID"));
        in.putExtras(b);
        startActivity(in);
    }

    public void SEM5(View view) {
        b.putString("SEM", "SEM5");
        b.putString("Name",fetch_teachet_data.getString("Name"));
        b.putString("ID",fetch_teachet_data.getString("ID"));
        in.putExtras(b);
        startActivity(in);

    }

    public void SEM6(View view) {
        b.putString("SEM", "SEM6");
        b.putString("Name",fetch_teachet_data.getString("Name"));
        b.putString("ID",fetch_teachet_data.getString("ID"));
        in.putExtras(b);
        startActivity(in);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.Student_Details) {
            Toast.makeText(this, "Student Details", Toast.LENGTH_LONG).show();
        }

        if (id == R.id.print_sem) {

          Intent print_semester=new Intent(Teacher_view.this,Print_Attendance_Semester.class);
          startActivity(print_semester);
        }
        if (id == R.id.Reset) {
            deleteSemData("FY");
        }
        if(id == R.id.conducted_attendance){
            Intent conducted_Attendance=new Intent(Intent.ACTION_VIEW);
            conducted_Attendance.setData(Uri.parse("https://docs.google.com/spreadsheets/d/1UpnH9XOMIFCPZB35Q5b49_QYi7P4B5dOmwC904nzv6k/edit#gid=0"));
            startActivity(conducted_Attendance);
        }
        if(id == R.id.add_teacher_id){
            Teacher_Id();
        }

        mdrawerlayout.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onBackPressed() {
        if (mdrawerlayout.isDrawerOpen(GravityCompat.START)) {
            mdrawerlayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }



    public void deleteSemData(String SEM) {
        builder = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);

        builder.setMessage("Do you want to restart academic  attendance data")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        db.execSQL("delete from Student_FY");
                        db.execSQL("delete from Student_SY");
                        db.execSQL("delete from Student_TY");
                        db.execSQL("delete from ATTENDENCE_SEM1_SUBJECT");
                        db.execSQL("delete from ATTENDENCE_SEM1_PRACTICAL");
                        db.execSQL("delete from ATTENDENCE_SEM2_SUBJECT");
                        db.execSQL("delete from ATTENDENCE_SEM2_PRACTICAL");
                        db.execSQL("delete from ATTENDENCE_SEM3_SUBJECT");
                        db.execSQL("delete from ATTENDENCE_SEM3_PRACTICAL");
                        db.execSQL("delete from ATTENDENCE_SEM4_SUBJECT");
                        db.execSQL("delete from ATTENDENCE_SEM4_PRACTICAL");
                        db.execSQL("delete from ATTENDENCE_SEM5_SUBJECT");
                        db.execSQL("delete from ATTENDENCE_SEM5_PRACTICAL");
                        db.execSQL("delete from ATTENDENCE_SEM6_SUBJECT");
                        db.execSQL("delete from ATTENDENCE_SEM6_PRACTICAL");
                        db.execSQL("update Students_subject set SEM1_ATTENDANCE=0,SEM2_ATTENDANCE=0,SEM3_ATTENDANCE=0,SEM4_ATTENDANCE=0,SEM5_ATTENDANCE=0,SEM6_ATTENDANCE=0");
                        Toast.makeText(getApplicationContext(), "Academic attendance data has restarted",
                                Toast.LENGTH_SHORT).show();




                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        

                    }
                });

        AlertDialog alert = builder.create();
        alert.setTitle("AlertDialogExample");
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.YELLOW);
        alert.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.YELLOW);


    }
    public void Teacher_Id(){

         AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        ViewGroup viewGroup =findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.popup_add_teacher_id,viewGroup,false);
        final EditText get_ID=dialogView.findViewById(R.id.Add_Teacher_Id);
        Button ADD_Id=dialogView.findViewById(R.id.ADD_ID);

        ADD_Id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor getData=db.rawQuery("select * from Teacher_Id where Teacher_id="+get_ID.getText().toString()+"",null);
                if(get_ID.getText().toString().isEmpty()){
                    Toast.makeText(Teacher_view.this,"Please Enter Id",Toast.LENGTH_SHORT).show();
                }
                else {

                    if(getData.moveToNext()){
                        Toast.makeText(Teacher_view.this,"Id is Already there",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        db.execSQL("insert into Teacher_Id(Teacher_id) values('"+get_ID.getText().toString()+"');");
                        Toast.makeText(Teacher_view.this, get_ID.getText().toString()+" "+"Added",Toast.LENGTH_SHORT).show();
                        get_ID.getText().clear();

                    }
                }


            }
        });

        builder1.setView(dialogView);

        final AlertDialog alertDialog = builder1.create();
        alertDialog.show();

    }
    public void getTime(){
        String get_Time;
        int Get_Time;
        SimpleDateFormat Time=new SimpleDateFormat("HH");
        Date Wish_Time=new Date();
        get_Time =  Time.format(Wish_Time);
        Get_Time=Integer.parseInt(get_Time);
        if(Get_Time>=0 && Get_Time<=04){
            Teacher_Wish.setText("Good Night");
        }
        else if(Get_Time>=05 && Get_Time<=11){
            Teacher_Wish.setText("Good Morning");
        }
        else if(Get_Time>=12 && Get_Time<=16){
            Teacher_Wish.setText("Good Afternoon");
        }
        else {
            Teacher_Wish.setText("Good Evening");
        }


    }


}
