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

public class subject_Attendance_Adapter extends RecyclerView.Adapter<subject_Attendance_Adapter.ViewHolder>{
    public subject_Attendance_list[] listData;
    int Present=0,Absent=0;
    String absent=null,Subject_score=null;
    Context c=null;

    public subject_Attendance_Adapter(subject_Attendance_list[] listData) {
        this.listData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        c=parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.subject_attendance_list, parent, false);
        ViewHolder viewHolder=new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        final subject_Attendance_list myListData=listData[position];
        Present=myListData.getPresent();
        Absent=myListData.getAbsent();
        Subject_score =""+myListData.getScore_subject();
        String present=""+Present;
        holder.textView.setText(listData[position].getName());
        holder.textView1.setText(Subject_score);
        holder.pieChart.setUsePercentValues(true);
        holder.pieChart.setExtraOffsets(0,0,0,0);
        holder.pieChart.setDragDecelerationFrictionCoef(0.99f);
        holder.pieChart.setDrawHoleEnabled(true);
        holder.pieChart.setCenterText(present+"%");
        holder.pieChart.setCenterTextSize(10);
        holder.pieChart.setCenterTextColor(Color.WHITE);
        holder.pieChart.setHoleColor(Color.TRANSPARENT);
        holder.pieChart.setHoleRadius(70);
        holder.pieChart.setTransparentCircleRadius(61f);
        holder.pieChart.getDescription().setEnabled(false);
        holder.pieChart.animateY(1000, Easing.EasingOption.EaseInOutQuad);
        holder.pieChart.getLegend().setEnabled(false);
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
        public final TextView textView,textView1;
        public final PieChart pieChart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView=(TextView)itemView.findViewById(R.id.subject_Attendance_Student_Name);
            this.pieChart=(PieChart)itemView.findViewById(R.id.subject_Attendance_graph);
            this.textView1=(TextView)itemView.findViewById(R.id.subject_Score);

        }
    }
}
