package com.example.ams;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;


public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> implements Filterable {
    private StudentList[] listdata;
    Context mcontext = null;
    StudentDBHelper dbHelper;
    SQLiteDatabase db, db1;
    Cursor getDetails;
    String Subject1;
    String SEM;
    int Attendance_value_subject;
    int Attendance_value_practical;



    int attendence = 0, total_attendence = 0;
    public MyListAdapter(StudentList[] listdata, String Subject1, String SEM, int Attendance_value_subject,int Attendance_value_practical) {
        this.listdata = listdata;
        this.Subject1 = Subject1;
        this.SEM = SEM;
        this.Attendance_value_subject=Attendance_value_subject;
        this.Attendance_value_practical=Attendance_value_practical;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mcontext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.student_data, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final StudentList myListData = listdata[position];
        holder.textView.setText(listdata[position].getName());
        holder.textView1.setText(listdata[position].getRollno());
        dbHelper = new StudentDBHelper(mcontext);
        db = dbHelper.getWritableDatabase();
        /* db1 = dbHelper.getReadableDatabase();*/
        holder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listdata = ArrayUtils.remove(listdata, position);
                notifyDataSetChanged();
                ContentValues cv = new ContentValues();
                int Rollno = Integer.parseInt(myListData.getRollno());
                String Subject_Table = "ATTENDENCE_" + SEM + "_SUBJECT";
                String[] column = {Subject1, "TOTAL_ATTENDENCE"};
                try {
                    Cursor res = db.query(Subject_Table, column, "Student_Rollno=? and Student_Name=?", new String[]{Integer.toString(Rollno), myListData.getName()}, null, null, null);
                    if (res.moveToNext()) {
                        attendence = res.getInt(0) + Attendance_value_subject;
                        total_attendence = res.getInt(1) + Attendance_value_subject;
                        String Attendance = "" + attendence;
                        cv.put(Subject1, attendence);
                        cv.put("TOTAL_ATTENDENCE", total_attendence);
                        db.update(Subject_Table, cv, "Student_Rollno=? and Student_Name=?", new String[]{Integer.toString(Rollno), myListData.getName()});
                        Toast.makeText(view.getContext(), "Attendence marked" + " " + Attendance, Toast.LENGTH_SHORT).show();
                                            }
                } catch (Exception e) {

                    String Subject_Tablle1 = "ATTENDENCE_" + SEM + "_PRACTICAL";
                    Cursor res = db.query(Subject_Tablle1, column, "Student_Rollno=? and Student_Name=?", new String[]{Integer.toString(Rollno), myListData.getName()}, null, null, null);
                    if (res.moveToNext()) {
                        attendence = res.getInt(0) + Attendance_value_practical;
                        total_attendence = res.getInt(1) + Attendance_value_practical;
                        String Attendance = "" + attendence;
                        cv.put(Subject1, attendence);
                        cv.put("TOTAL_ATTENDENCE", total_attendence);
                        db.update(Subject_Tablle1, cv, "Student_Rollno=? and Student_Name=?", new String[]{Integer.toString(Rollno), myListData.getName()});
                        Toast.makeText(view.getContext(), "Attendence marked" + " " + Attendance, Toast.LENGTH_SHORT).show();

                    }
                }


            }
        });

    }

    @Override
    public int getItemCount() {

        return listdata.length;
    }

    @Override
    public Filter getFilter() {

        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView, textView1;
        public final RelativeLayout relative;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.Name);
            this.textView1 = (TextView) itemView.findViewById(R.id.Rollno);
            this.relative = (RelativeLayout) itemView.findViewById(R.id.relative);
        }
    }
}
