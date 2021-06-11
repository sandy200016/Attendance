package com.example.ams;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class student_Attendence_Adapter extends RecyclerView.Adapter<student_Attendence_Adapter.ViewHolder>{
    public subjectAttendenceData[] listData;
    Context c;
    int Present=0,Absent=0;
    String absent=null;

    public student_Attendence_Adapter(subjectAttendenceData[] listData){

        this.listData=listData;
    }
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        c=parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.subject_attendance_data, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        final subjectAttendenceData myListData = listData[position];
        Present=myListData.getPresentValue();
        Absent=myListData.getAbsentValue();
        String present=""+Present;
        holder.textView.setText(listData[position].getSubject());
        holder.pieChart.setUsePercentValues(true);
        holder.pieChart.setExtraOffsets(0,0,0,0);
        holder.pieChart.setDragDecelerationFrictionCoef(0.99f);
        holder.pieChart.setDrawHoleEnabled(true);
        holder.pieChart.setCenterText(present+"%");
        holder.pieChart.setCenterTextSize(10);
        holder.pieChart.setCenterTextColor(Color.WHITE);
        holder.pieChart.setHoleColor(Color.TRANSPARENT);
        holder.pieChart.setHoleRadius(75);
        holder.pieChart.setTransparentCircleRadius(61f);
        holder.pieChart.getDescription().setEnabled(false);
        holder.pieChart.animateY(1000, Easing.EasingOption.EaseInOutQuad);
        holder.pieChart.getLegend().setEnabled(false);
     //   holder.pieChart.setDrawSlicesUnderHole(true);
        holder.pieChart.invalidate();
        ArrayList<PieEntry> yValues=new ArrayList<>();
        yValues.add(new PieEntry(Present,""));
        yValues.add(new PieEntry(Absent,""));
        PieDataSet dataSet=new PieDataSet(yValues,"");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(3f);
        dataSet.setColors(new int[]{R.color.green,R.color.red},c );
        PieData data=new PieData(dataSet);
        data.setValueTextSize(0f);
        holder.pieChart.setData(data);
    }
    @Override
    public int getItemCount() {
        return listData.length;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;
        public final PieChart pieChart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView=(TextView)itemView.findViewById(R.id.subject_name);
            this.pieChart=(PieChart)itemView.findViewById(R.id.student_Attendence_grapg);
        }
    }
}
