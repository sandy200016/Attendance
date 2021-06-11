package com.example.ams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Print_Attendance_Semester extends AppCompatActivity {
    SQLiteDatabase db;
    ArrayList<String> Student_name;
    ArrayList<Integer> Student_Rollno;
    ArrayList<Integer> Student_Id;
    ArrayList<Integer> Student_Attendance;
    Workbook wb = null;
    CellStyle cellStyle = null;
    Sheet sheet = null;
    Row row = null;
    File file = null;
    FileOutputStream outputStream = null;
    int Count_sem1=0,Count_sem2=0,Count_sem3=0,Count_sem4=0,Count_sem5=0,Count_sem6=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print__attendance__semester);
        db = openOrCreateDatabase("Attendence", Context.MODE_PRIVATE, null);
        Student_name = new ArrayList<String>();
        Student_Id = new ArrayList<Integer>();
        Student_Rollno = new ArrayList<Integer>();
        Student_Attendance = new ArrayList<Integer>();

    }

    public void print_SEM(String SEM) {
        Student_Attendance.clear();
        Student_Id.clear();
        Student_name.clear();
        Student_Rollno.clear();

        float temp_Present = 0, Subject = 0, Practical = 0, Total = 0, Attendance = 0;
        int Present = 0, i = 1;

        Cursor student_Details = db.rawQuery("select * from ATTENDENCE_" + SEM + "_SUBJECT order by Student_Rollno", null);

        Cursor getAttendance = db.rawQuery("select sum(" + SEM + "_ATTENDANCE) from Students_subject", null);
        Cursor getSubjectData = db.rawQuery("select TOTAL_ATTENDENCE from ATTENDENCE_" + SEM + "_SUBJECT order by Student_Rollno", null);
        Cursor getPracticalData = db.rawQuery("select TOTAL_ATTENDENCE from ATTENDENCE_" + SEM + "_PRACTICAL order by Student_Rollno", null);
        if (getAttendance.moveToNext()) {

            Attendance = getAttendance.getFloat(0);
        }

        while (student_Details.moveToNext()) {

            if (getPracticalData.moveToNext()) {
                Practical = getPracticalData.getFloat(0);
            }
            if (getSubjectData.moveToNext()) {
                Subject = getSubjectData.getFloat(0);
            }

            Total = +Subject + Practical;
            temp_Present = ((Total / Attendance) * 100);
            Present = Math.round(temp_Present);
            Student_name.add(student_Details.getString(0));
            Student_Id.add(student_Details.getInt(1));
            Student_Rollno.add(student_Details.getInt(2));
            Student_Attendance.add(Present);




        }

        wb = new XSSFWorkbook();
        Cell cell = null;
        cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.BLUE.index);
        cellStyle.setFillBackgroundColor(HSSFColor.BLUE_GREY.index);
        sheet = wb.createSheet(SEM + " " + "Attendance");
        row = sheet.createRow(0);
        cell = row.createCell(0);
        cell.setCellValue("Student Name");
        cell.setCellStyle(cellStyle);
        cell = row.createCell(1);
        cell.setCellValue("Student Id");
        cell.setCellStyle(cellStyle);
        cell = row.createCell(2);
        cell.setCellValue("Roll No");
        cell.setCellStyle(cellStyle);
        cell = row.createCell(3);
        cell.setCellValue("Attendance");
        cell.setCellStyle(cellStyle);
        for (int j = 0; j < Student_name.size(); j++) {
            Row data = sheet.createRow(i);
            cell = data.createCell(0);
            cell.setCellValue(Student_name.get(j));
            cell = data.createCell(1);
            cell.setCellValue(Student_Id.get(j));
            cell = data.createCell(2);
            cell.setCellValue(Student_Rollno.get(j));
            cell = data.createCell(3);
            cell.setCellValue(Student_Attendance.get(j));
            i++;
        }

        sheet.setColumnWidth(0, 10 * 400);
        sheet.setColumnWidth(1, 10 * 400);
        sheet.setColumnWidth(2, 10 * 400);
        sheet.setColumnWidth(3, 10 * 400);


        file = new File(getExternalFilesDir(null), SEM + " " + "Attendance.xls");
        try {
            outputStream = new FileOutputStream(file);
            wb.write(outputStream);

            Toast.makeText(this, SEM + " " + "File created", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(this, " NOT Ok", Toast.LENGTH_SHORT).show();
            try {
                outputStream.close();
            } catch (Exception ex1) {
                ex1.printStackTrace();

            }
        }
      /*  File file1 = new File( getExternalFilesDir(null)+"/" + SEM + " " + "Attendance.xls");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file1),"application/vnd.ms-excel");
       /* intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);*/
       // startActivity(intent);
        Intent intent=new Intent(Intent.ACTION_VIEW);
        Uri apkURI = FileProvider.getUriForFile(getApplicationContext(),
                getApplicationContext().getPackageName(),file);

        MimeTypeMap myMime = MimeTypeMap.getSingleton();
        String mimeType=myMime.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(apkURI.toString()));//It will return the mimetype
        intent.setDataAndType(apkURI, mimeType);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }


    public void sem1_print_attendance(View view) {


        if (Count_sem1 == 0) {
            print_SEM("SEM1");
        }
        else{
            Toast.makeText(this, "SEM1 File created", Toast.LENGTH_SHORT).show();
        }
        Count_sem1++;

    }

    public void sem2_print_attendance(View view) {

        if (Count_sem2 == 0) {
            print_SEM("SEM2");
        }
        else{
            Toast.makeText(this, "SEM2 File created", Toast.LENGTH_SHORT).show();
        }
        Count_sem2++;
    }

    public void sem3_print_attendance(View view) {

        if (Count_sem3== 0) {
            print_SEM("SEM3");
        }
        else{
            Toast.makeText(this, "SEM3 File created", Toast.LENGTH_SHORT).show();
        }
        Count_sem3++;
    }

    public void sem4_print_attendance(View view) {

        if (Count_sem4== 0) {
            print_SEM("SEM4");
        }
        else{
            Toast.makeText(this, "SEM4 File created", Toast.LENGTH_SHORT).show();
        }
        Count_sem4++;
    }

    public void sem5_print_attendance(View view) {

        if (Count_sem5== 0) {
            print_SEM("SEM5");
        }
        else{
            Toast.makeText(this, "SEM5 File created", Toast.LENGTH_SHORT).show();
        }
        Count_sem5++;
    }

    public void sem6_print_attendance(View view) {

        if (Count_sem6== 0) {
            print_SEM("SEM6");
        }
        else{
            Toast.makeText(this, "SEM6 File created", Toast.LENGTH_SHORT).show();
        }
        Count_sem6++;
    }
}