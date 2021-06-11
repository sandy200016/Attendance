package com.example.ams;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    private TabLayout tablayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private EditText Login_id, Login_password,
            student_id, student_name, student_rollno, student_pass, student_Password,
            teacher_id, teacher_name, teacher_pass, teacher_password;
    private Button Login;
    private RadioGroup Class;
    private RadioButton Class1;


    String user_password, user_id,
            Student_id, Student_pass, Student_name, Student_rollno, Student_password,
            Teacher_id, Teacher_name, Teacher_pass, Teacher_password;
    Cursor getData;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = openOrCreateDatabase("Attendence", Context.MODE_PRIVATE, null);








        db.execSQL("CREATE TABLE IF NOT EXISTS Student_FY(Student_Name VARCHAR,Student_Id VARCHAR,Student_class VARCHAR,Student_Rollno INTEGER,Student_Password);");
        db.execSQL("CREATE TABLE IF NOT EXISTS Student_SY(Student_Name VARCHAR,Student_Id VARCHAR,Student_class VARCHAR,Student_Rollno INTEGER,Student_Password);");
        db.execSQL("CREATE TABLE IF NOT EXISTS Student_TY(Student_Name VARCHAR,Student_Id VARCHAR,Student_class VARCHAR,Student_Rollno INTEGER,Student_Password);");
        db.execSQL("CREATE TABLE IF NOT EXISTS ATTENDENCE_SEM1_SUBJECT(Student_Name VARCHAR,Student_Id VARCHAR,Student_Rollno INTEGER,COD INTEGER,PYTHON INTEGER,DBMS INTEGER,FOSS INTEGER,DISCRETE_MATHEMATICS INTEGER,STATISTICS INTEGER,SOFT_SKILLS INTEGER,TOTAL_ATTENDENCE INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS ATTENDENCE_SEM1_PRACTICAL(Student_Name VARCHAR,Student_Id VARCHAR,Student_Rollno INTEGER,PRACTICAL_COD INTEGER,PRACTICAL_PYTHON INTEGER,PRACTICAL_DBMS INTEGER,PRACTICAL_FOSS INTEGER,TUTORIAL_DISCRETE_MATHEMATICS INTEGER,TUTORIAL_STATISTICS INTEGER,TOTAL_ATTENDENCE INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS ATTENDENCE_SEM2_SUBJECT(Student_Name VARCHAR,Student_Id VARCHAR,Student_Rollno INTEGER,C INTEGER,PYTHON INTEGER,LINUX INTEGER,DATA_STRUCTURES INTEGER,CALCULUS INTEGER,STATISTICS INTEGER,GREEN_TECHNOLOGIES INTEGER,TOTAL_ATTENDENCE INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS ATTENDENCE_SEM2_PRACTICAL(Student_Name VARCHAR,Student_Id VARCHAR,Student_Rollno INTEGER,PRACTICAL_C INTEGER,PRACTICAL_PYTHON INTEGER,PRACTICAL_LINUX INTEGER,PRACTICAL_DATA_STRUCTURES INTEGER,TUTORIAL_CALCULUS INTEGER,TUTORIAL_STATISTICS INTEGER,TOTAL_ATTENDENCE INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS ATTENDENCE_SEM3_SUBJECT(Student_Name VARCHAR,Student_Id VARCHAR,Student_Rollno INTEGER,CORE_JAVA INTEGER,WEB_PROGRAMMING INTEGER,OPERATING_SYSTEM INTEGER,DBMS INTEGER,CGT INTEGER,IOT INTEGER,TOC INTEGER,TOTAL_ATTENDENCE INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS ATTENDENCE_SEM3_PRACTICAL(Student_Name VARCHAR,Student_Id VARCHAR,Student_Rollno INTEGER,PRACTICAL_CORE_JAVA INTEGER,PRACTICAL_OPERATING_SYSTEM INTEGER,PRACTICAL_WEB_PROGRAMMING INTEGER,PRACTICAL_DBMS INTEGER,TUTORIAL_CGT INTEGER,PRACTICAL_IOT INTEGER,TOC INTEGER,TOTAL_ATTENDENCE INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS ATTENDENCE_SEM4_SUBJECT(Student_Name VARCHAR,Student_Id VARCHAR,Student_Rollno INTEGER,ALGORITHM INTEGER,ADVANCE_JAVA INTEGER,COMPUTER_NETWORK INTEGER,ANDROID INTEGER,LINEAR_ALGEBRA INTEGER,DOT_NET INTEGER,SOFTWARE_ENGINEERING INTEGER,TOTAL_ATTENDENCE INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS ATTENDENCE_SEM4_PRACTICAL(Student_Name VARCHAR,Student_Id VARCHAR,Student_Rollno INTEGER,PRACTICAL_ALGORITHM INTEGER,PRACTICAL_ADVANCE_JAVA INTEGER,PRACTICAL_COMPUTER_NETWORK INTEGER,PRACTICAL_ANDROID INTEGER,PRACTICAL_LINEAR_ALGEBRA INTEGER,PRACTICAL_DOT_NET INTEGER,TOTAL_ATTENDENCE INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS ATTENDENCE_SEM5_SUBJECT(Student_Name VARCHAR,Student_Id VARCHAR,Student_Rollno INTEGER,ARTIFICIAL_INTELLIGENCE INTEGER,LINUX_SERVER INTEGER,SOFTWARE_TESTING INTEGER,NETWORK_SECURITY INTEGER,IOT INTEGER,WEB_SERVICES INTEGER,GAME_PROGRAMING INTEGER,TOTAL_ATTENDENCE INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS ATTENDENCE_SEM5_PRACTICAL(Student_Name VARCHAR,Student_Id VARCHAR,Student_Rollno INTEGER,PRACTICAL_ARTIFICIAL_INTELLIGENCE INTEGER,PRACTICAL_LINUX_SERVER INTEGER,PRACTICAL_SOFTWARE_TESTING INTEGER,PRACTICAL_NETWORK_SECURITY INTEGER,PRACTICAL_IOT INTEGER,PRACTICAL_WEB_SERVICES INTEGER,PRACTICAL_GAME_PROGRAMING INTEGER,TOTAL_ATTENDENCE INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS ATTENDENCE_SEM6_SUBJECT(Student_Name VARCHAR,Student_Id VARCHAR,Student_Rollno INTEGER,WSN INTEGER,CLOUD_COMPUTING INTEGER,CYBER_FORENSIC INTEGER,INFORMATION_RETRIEVAL INTEGER,DIGITAL_IMAGE_PROCESSING INTEGER,DATA_SCIENCE INTEGER,ETHICAL_HACKING INTEGER,TOTAL_ATTENDENCE INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS ATTENDENCE_SEM6_PRACTICAL(Student_Name VARCHAR,Student_Id VARCHAR,Student_Rollno INTEGER,PRACTICAL_WSN INTEGER,PRACTICAL_CLOUD_COMPUTING INTEGER,PRACTICAL_CYBER_FORENSIC INTEGER,PRACTICAL_INFORMATION_RETRIEVAL INTEGER,PRACTICAL_DIGITAL_IMAGE_PROCESSING INTEGER,PRACTICAL_DATA_SCIENCE INTEGER,PRACTICAL_ETHICAL_HACKING INTEGER,TOTAL_ATTENDENCE INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS Students_subject(SEM1 VARCHAR,SEM2 VARCHAR,SEM3 VARCHAR,SEM4 VARCHAR,SEM5 VARCHAR,SEM6 VARCHAR,SEM1_ATTENDANCE INTEGER,SEM2_ATTENDANCE INTEGER,SEM3_ATTENDANCE INTEGER,SEM4_ATTENDANCE INTEGER,SEM5_ATTENDANCE INTEGER,SEM6_ATTENDANCE INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS Teacher_id(Teacher_id INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Teacher_Details(Teacher_Name VARCHAR,Teacher_Id INTEGER,Teacher_Password VARCHAR)");
        Cursor getSubjectName = db.rawQuery("select * from Students_subject", null);
        if (getSubjectName.moveToNext()) {

        } else {
            db.execSQL("insert into Students_subject(SEM1,SEM2,SEM3,SEM4,SEM5,SEM6) values('COD','C','CORE_JAVA','ALGORITHM','ARTIFICIAL_INTELLIGENCE','WSN')");
            db.execSQL("insert into Students_subject(SEM1,SEM2,SEM3,SEM4,SEM5,SEM6) values('PYTHON','PYTHON','WEB_PROGRAMMING','ADVANCE_JAVA','LINUX_SERVER','CLOUD_COMPUTING')");
            db.execSQL("insert into Students_subject(SEM1,SEM2,SEM3,SEM4,SEM5,SEM6) values('DBMS','LINUX','OPERATING_SYSTEM','COMPUTER_NETWORK','SOFTWARE_TESTING','CYBER_FORENSIC')");
            db.execSQL("insert into Students_subject(SEM1,SEM2,SEM3,SEM4,SEM5,SEM6) values('FOSS','DATA_STRUCTURES','DBMS','ANDROID','NETWORK_SECURITY','INFORMATION_RETRIEVAL')");
            db.execSQL("insert into Students_subject(SEM1,SEM2,SEM3,SEM4,SEM5,SEM6) values('DISCRETE_MATHEMATICS','CALCULUS','CGT','LINEAR_ALGEBRA','IOT','DIGITAL_IMAGE_PROCESSING')");
            db.execSQL("insert into Students_subject(SEM1,SEM2,SEM3,SEM4,SEM5,SEM6) values('STATISTICS','STATISTICS','IOT','DOT_NET','WEB_SERVICES','DATA_SCIENCE')");
            db.execSQL("insert into Students_subject(SEM1,SEM2,SEM3,SEM4,SEM5,SEM6) values('SOFT_SKILLS','GREEN_TECHNOLOGIES','TOC','SOFTWARE_ENGINEERING','GAME_PROGRAMING','ETHICAL_HACKING')");
            db.execSQL("insert into Students_subject(SEM1,SEM2,SEM3,SEM4,SEM5,SEM6) values('PRACTICAL_COD','PRACTICAL_C','PRACTICAL_CORE_JAVA','PRACTICAL_ALGORITHM','PRACTICAL_ARTIFICIAL_INTELLIGENCE','PRACTICAL_WSN')");
            db.execSQL("insert into Students_subject(SEM1,SEM2,SEM3,SEM4,SEM5,SEM6) values('PRACTICAL_PYTHON','PRACTICAL_PYTHON','PRACTICAL_WEB_PROGRAMMING','PRACTICAL_ADVANCE_JAVA','PRACTICAL_LINUX_SERVER','PRACTICAL_CLOUD_COMPUTING')");
            db.execSQL("insert into Students_subject(SEM1,SEM2,SEM3,SEM4,SEM5,SEM6) values('PRACTICAL_DBMS','PRACTICAL_LINUX','PRACTICAL_OPERATING_SYSTEM','PRACTICAL_COMPUTER_NETWORK','PRACTICAL_SOFTWARE_TESTING','PRACTICAL_CYBER_FORENSIC')");
            db.execSQL("insert into Students_subject(SEM1,SEM2,SEM3,SEM4,SEM5,SEM6) values('PRACTICAL_FOSS','PRACTICAL_DATA_STRUCTURES','PRACTICAL_DBMS','PRACTICAL_ANDROID','PRACTICAL_NETWORK_SECURITY','PRACTICAL_INFORMATION_RETRIEVAL')");
            db.execSQL("insert into Students_subject(SEM1,SEM2,SEM3,SEM4,SEM5,SEM6) values('TUTORIAL_DISCRETE_MATHEMATICS','TUTORIAL_CALCULUS','TUTORIAL_CGT','PRACTICAL_LINEAR_ALGEBRA','PRACTICAL_IOT','PRACTICAL_DIGITAL_IMAGE_PROCESSING')");
            db.execSQL("insert into Students_subject(SEM1,SEM2,SEM3,SEM4,SEM5,SEM6) values('TUTORIAL_STATISTICS','TUTORIAL_STATISTICS','PRACTICAL_IOT','PRACTICAL_DOT_NET','PRACTICAL_WEB_SERVICES','PRACTICAL_DATA_SCIENCE')");
            db.execSQL("insert into Students_subject(SEM5,SEM6) values('PRACTICAL_GAME_PROGRAMING','PRACTICAL_ETHICAL_HACKING')");
        }

        Cursor getTeacherId = db.rawQuery("Select * from Teacher_id", null);
        if (getTeacherId.moveToNext()) {

        }
        else {
            db.execSQL("insert into Teacher_Id(Teacher_id) values(1338);");
            db.execSQL("insert into Teacher_Id(Teacher_id) values(8331);");
        }
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        tablayout = (TabLayout) findViewById(R.id.tabs);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPagerAdapter adapter = new viewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new Login(), "Login");
        adapter.AddFragment(new Student_reg(), "Student");
        adapter.AddFragment(new Teacher_reg(), "Teacher");
        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);
       // db.execSQL("update Teacher_Details set Teacher_Name='Madhvi' where Teacher_Id=1338;");
      /*  pa*/


    }

    public void Msg(String msg) {
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    public void Submit(View view) {
        Login_id = (EditText) findViewById(R.id.login_id);
        Login_password = (EditText) findViewById(R.id.login_password);
        user_id = Login_id.getText().toString();
        user_password = Login_password.getText().toString();
        if (user_id.isEmpty() & user_password.isEmpty()) {
            Msg("Please Enter Your Full Details");
        } else {
            Cursor FY_Student = db.rawQuery("select * from Student_FY where Student_Id='" + Login_id.getText() + "'", null);
            Cursor SY_Student = db.rawQuery("select * from Student_SY where Student_Id='" + Login_id.getText() + "'", null);
            Cursor TY_Student = db.rawQuery("select * from Student_TY where Student_Id='" + Login_id.getText() + "'", null);
            Cursor Teacher = db.rawQuery("select * from Teacher_Details where Teacher_Id='" + Login_id.getText() + "'", null);
            Intent student_DashBoard = new Intent(getApplicationContext(), student_Dashboard.class);
            Bundle getStudentClass = new Bundle();
            if (FY_Student.moveToFirst()) {
                if (user_password.equals(FY_Student.getString(4)) & user_id.equals(FY_Student.getString(1))) {

                    getStudentClass.putString("Class", "Student_FY");
                    getStudentClass.putString("Student_Id", FY_Student.getString(1));
                    getStudentClass.putString("Student_Rollno", FY_Student.getString(3));
                    getStudentClass.putString("Student_Password", FY_Student.getString(4));
                    getStudentClass.putString("Student_Name", FY_Student.getString(0));
                    student_DashBoard.putExtras(getStudentClass);
                    startActivity(student_DashBoard);
                    Login_id.getText().clear();
                    Login_password.getText().clear();

                } else {
                    Msg("Incorrect Password");
                }
            } else if (SY_Student.moveToFirst()) {
                if (user_password.equals(SY_Student.getString(4)) & user_id.equals(SY_Student.getString(1))) {

                    getStudentClass.putString("Class", "Student_SY");
                    getStudentClass.putString("Student_Id", SY_Student.getString(1));
                    getStudentClass.putString("Student_Rollno", SY_Student.getString(3));
                    getStudentClass.putString("Student_Password", SY_Student.getString(4));
                    getStudentClass.putString("Student_Name", SY_Student.getString(0));
                    student_DashBoard.putExtras(getStudentClass);
                    startActivity(student_DashBoard);
                    Login_id.getText().clear();
                    Login_password.getText().clear();

                } else {
                    Msg("Incorrect Password");
                }

            } else if (TY_Student.moveToFirst()) {
                if (user_password.equals(TY_Student.getString(4)) & user_id.equals(TY_Student.getString(1))) {

                    getStudentClass.putString("Class", "Student_TY");
                    getStudentClass.putString("Student_Id", TY_Student.getString(1));
                    getStudentClass.putString("Student_Rollno", TY_Student.getString(3));
                    getStudentClass.putString("Student_Password", TY_Student.getString(4));
                    getStudentClass.putString("Student_Name", TY_Student.getString(0));
                    student_DashBoard.putExtras(getStudentClass);
                    startActivity(student_DashBoard);
                    Login_id.getText().clear();
                    Login_password.getText().clear();


                } else {
                    Msg("Incorrect Password");
                }

            } else if (Teacher.moveToFirst()) {
                if (user_password.equals(Teacher.getString(2)) & user_id.equals(Teacher.getString(1))) {
                    Bundle put_Teacherdata=new Bundle();
                    put_Teacherdata.putString("Name",Teacher.getString(0));
                    put_Teacherdata.putString("ID",Teacher.getString(1));
                    Intent i = new Intent(getApplicationContext(), Teacher_view.class);
                    i.putExtras(put_Teacherdata);
                    startActivity(i);
                    Login_id.getText().clear();
                    Login_password.getText().clear();
                } else {
                    Msg("Incorrect Password");
                }

            } else {
                Msg("Invalid User");
            }

        }

    }

    public static boolean isNumeric(final String str) {
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean specialCharacter(final String str) {
        String Specialchar = "!@#$%^&*()-=+/*?|][}{><.,;'':_";
        for (int i = 0; i < str.length(); i++) {
            if (Specialchar.contains(Character.toString(str.charAt(i)))) {
                return true;
            }

        }
        return false;
    }

    public void reg_Student(View view) {
        student_id = (EditText) findViewById(R.id.student_id);
        student_name = (EditText) findViewById(R.id.student_name);
        student_pass = (EditText) findViewById(R.id.student_pass);
        student_Password = (EditText) findViewById(R.id.student_cpass);
        student_rollno = (EditText) findViewById(R.id.student_rollno);
        Class = (RadioGroup) findViewById(R.id.class_group);
        Student_name = student_name.getText().toString();
        Student_id = student_id.getText().toString();
        Student_rollno = student_rollno.getText().toString();
        Student_pass = student_pass.getText().toString();
        Student_password = student_Password.getText().toString();

        if (Student_name.isEmpty()) {
            Msg("Please Enter Your Name");
        } else if (isNumeric(Student_name)) {
            Msg("Number is not Allowed");

        } else if (specialCharacter(Student_name)) {
            Msg("Special Character is not Allowed");

        } else if (Student_rollno.isEmpty()) {
            Msg("Please Enter Your Rollno");

        } else if (Student_id.isEmpty()) {
            Msg("Please Enter Your Id No");
        } else if (Student_pass.isEmpty()) {
            Msg("Please Enter Your Password");
        } else if (Student_password.isEmpty()) {
            Msg("Please Enter Your Confirm Password");
        } else if (!Student_pass.equals(Student_password)) {
            Msg("Incorrect Password Please Try Again");
        } else {
            int Rollno_student = Integer.parseInt(Student_rollno);
            int class_selected_id = Class.getCheckedRadioButtonId();
            Class1 = (RadioButton) findViewById(class_selected_id);
            String Class_Name = "Student_" + Class1.getText();
            getData = db.rawQuery("select * from " + Class_Name + " where Student_Id='" + Student_id + "' or Student_Rollno='" + Rollno_student + "' ", null);
            Cursor get_FY_id= db.rawQuery("Select * from Student_FY where Student_Id='"+Student_id+"'",null);
            Cursor get_SY_id= db.rawQuery("Select * from Student_SY where Student_Id='"+Student_id+"'",null);
            Cursor get_TY_id= db.rawQuery("Select * from Student_TY where Student_Id='"+Student_id+"'",null);
            if (getData.moveToNext()) {
                Msg("User Has Been Already Registered Please Try Another Id or Rollno");
            }
            else if(get_FY_id.moveToNext()){
                Msg("User Id Has Been Already Registered In Fy");
            }
            else if(get_SY_id.moveToNext()){
                Msg("User Id Has Been Already Registered In Sy");
            }
            else if(get_TY_id.moveToNext()){
                Msg("User Id Has Been Already Registered In Ty");
            }

            else {
                db.execSQL("INSERT INTO " + Class_Name + "(Student_Name,Student_Id,Student_class,Student_Rollno,Student_Password) VALUES ('" + Student_name + "','" + Student_id + "','" + Class1.getText() + "','" + Rollno_student + "','" + Student_pass + "');");
                if (Class1.getText().equals("FY")) {
                    db.execSQL("INSERT INTO ATTENDENCE_SEM1_SUBJECT(Student_Name,Student_Id,Student_Rollno) VALUES ('" + Student_name + "','" + Student_id + "','" + Rollno_student + "');");
                    db.execSQL("INSERT INTO ATTENDENCE_SEM1_PRACTICAL(Student_Name,Student_Id,Student_Rollno) VALUES ('" + Student_name + "','" + Student_id + "','" + Rollno_student + "');");
                    db.execSQL("INSERT INTO ATTENDENCE_SEM2_SUBJECT(Student_Name,Student_Id,Student_Rollno) VALUES ('" + Student_name + "','" + Student_id + "','" + Rollno_student + "');");
                    db.execSQL("INSERT INTO ATTENDENCE_SEM2_PRACTICAL(Student_Name,Student_Id,Student_Rollno) VALUES ('" + Student_name + "','" + Student_id + "','" + Rollno_student + "');");

                } else if (Class1.getText().equals("SY")) {
                    db.execSQL("INSERT INTO ATTENDENCE_SEM3_SUBJECT(Student_Name,Student_Id,Student_Rollno) VALUES ('" + Student_name + "','" + Student_id + "','" + Rollno_student + "');");
                    db.execSQL("INSERT INTO ATTENDENCE_SEM3_PRACTICAL(Student_Name,Student_Id,Student_Rollno) VALUES ('" + Student_name + "','" + Student_id + "','" + Rollno_student + "');");
                    db.execSQL("INSERT INTO ATTENDENCE_SEM4_SUBJECT(Student_Name,Student_Id,Student_Rollno) VALUES ('" + Student_name + "','" + Student_id + "','" + Rollno_student + "');");
                    db.execSQL("INSERT INTO ATTENDENCE_SEM4_PRACTICAL(Student_Name,Student_Id,Student_Rollno) VALUES ('" + Student_name + "','" + Student_id + "','" + Rollno_student + "');");
                } else if (Class1.getText().equals("TY")) {
                    db.execSQL("INSERT INTO ATTENDENCE_SEM5_SUBJECT(Student_Name,Student_Id,Student_Rollno) VALUES ('" + Student_name + "','" + Student_id + "','" + Rollno_student + "');");
                    db.execSQL("INSERT INTO ATTENDENCE_SEM5_PRACTICAL(Student_Name,Student_Id,Student_Rollno) VALUES ('" + Student_name + "','" + Student_id + "','" + Rollno_student + "');");
                    db.execSQL("INSERT INTO ATTENDENCE_SEM6_SUBJECT(Student_Name,Student_Id,Student_Rollno) VALUES ('" + Student_name + "','" + Student_id + "','" + Rollno_student + "');");
                    db.execSQL("INSERT INTO ATTENDENCE_SEM6_PRACTICAL(Student_Name,Student_Id,Student_Rollno) VALUES ('" + Student_name + "','" + Student_id + "','" + Rollno_student + "');");
                }
                Msg("Registration Sucessfull Swip Left And Login");
                student_id.getText().clear();
                student_name.getText().clear();
                student_pass.getText().clear();
                student_Password.getText().clear();
                student_rollno.getText().clear();
            }
        }
    }

    public void reg_Teacher(View view) {
        teacher_id = findViewById(R.id.teacher_id);
        teacher_name = findViewById(R.id.teacher_name);
        teacher_pass = findViewById(R.id.teacher_pass);
        teacher_password = findViewById(R.id.teacher_password);
        Teacher_id = teacher_id.getText().toString();
        Teacher_name = teacher_name.getText().toString();
        Teacher_pass = teacher_pass.getText().toString();
        Teacher_password = teacher_password.getText().toString();
        if (Teacher_name.isEmpty()) {
            Msg("Please Enter Your Name");
        } else if (isNumeric(Teacher_name)) {
            Msg("Please Enter Your Valid Name");
        } else if (specialCharacter(Teacher_name)) {
            Msg("Special Character is not Allowed");
        } else if (Teacher_id.isEmpty()) {
            Msg("Please Enter Your Id No");
        } else if (Teacher_pass.isEmpty()) {
            Msg("Please Enter Your Password");
        } else if (Teacher_password.isEmpty()) {
            Msg("Please Enter Your Confirm Password");
        } else if (!Teacher_pass.equals(Teacher_password)) {

            Msg("Incorrect Password Please Try Again");
        } else {
            int Teacher_Id_No = Integer.parseInt(teacher_id.getText().toString());

            Cursor c = db.rawQuery("select * from Teacher_Id where Teacher_id='" + teacher_id.getText() + "'", null);
            Cursor c1 = db.rawQuery("select * from Teacher_Details where Teacher_Id='" + Teacher_Id_No + "'", null);

            if (c.moveToFirst()) {

                if (Teacher_id.equals(c.getString(0))) {
                    if (c1.moveToFirst()) {
                        Msg("Id Is Already Registered");
                    } else {
                        db.execSQL("insert into Teacher_Details(Teacher_Name,Teacher_Id,Teacher_Password) values ('" + Teacher_name + "','" + Teacher_Id_No + "','" + Teacher_password + "')");
                        teacher_name.getText().clear();
                        teacher_id.getText().clear();
                        teacher_password.getText().clear();
                        teacher_pass.getText().clear();
                        Msg("Registration Sucessfull Swip Left And Login");
                    }
                } else {
                    Msg("Invalid Id");
                }
            } else {

                Msg("Invalid Id");
            }

        }

    }

}
