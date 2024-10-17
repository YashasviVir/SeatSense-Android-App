package com.example.seatsense;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AttendanceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AttendanceAdapter attendanceAdapter;
    private List<Attendance> allAttendanceList;
    private List<Attendance> filteredAttendanceList;
    private Button btnPickDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance); // The XML layout containing RecyclerView

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnPickDate = findViewById(R.id.btnPickDate);

        allAttendanceList = new ArrayList<>();
        filteredAttendanceList = new ArrayList<>();

        // Add dummy data for two dates
        loadDummyData();

        // Set adapter
        attendanceAdapter = new AttendanceAdapter(filteredAttendanceList);
        recyclerView.setAdapter(attendanceAdapter);

        // Set button click listener for the date picker
        btnPickDate.setOnClickListener(v -> showDatePickerDialog());
    }

    private void loadDummyData() {
        try {
            // Dummy JSON data for two dates
            String jsonStr = "[{\"name\":\"Yashasvi Virani\",\"enrollment\":\"21BT04136\",\"timestamp\":\"10:00 AM\",\"date\":\"2024-10-16\"},"
                    + "{\"name\":\"Nayan Radadiya\",\"enrollment\":\"21BT04099\",\"timestamp\":\"10:05 AM\",\"date\":\"2024-10-16\"},"
                    + "{\"name\":\"Khushi Panchal\",\"enrollment\":\"21BT04038\",\"timestamp\":\"09:00 AM\",\"date\":\"2024-10-17\"}]";

            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String enrollment = jsonObject.getString("enrollment");
                String timestamp = jsonObject.getString("timestamp");
                String date = jsonObject.getString("date");

                allAttendanceList.add(new Attendance(name, enrollment, timestamp, date));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Show date picker dialog
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            // Date selected, now filter data
            String selectedDate = String.format("%d-%02d-%02d", year1, month1 + 1, dayOfMonth);
            filterAttendanceByDate(selectedDate);
        }, year, month, day);
        datePickerDialog.show();
    }

    // Filter the attendance list by selected date
    private void filterAttendanceByDate(String selectedDate) {
        filteredAttendanceList.clear();
        for (Attendance attendance : allAttendanceList) {
            if (attendance.getDate().equals(selectedDate)) {
                filteredAttendanceList.add(attendance);
            }
        }
        attendanceAdapter.notifyDataSetChanged();
    }

    // Inner class: Attendance model
    private class Attendance {
        private String name;
        private String enrollment;
        private String timestamp;
        private String date;

        public Attendance(String name, String enrollment, String timestamp, String date) {
            this.name = name;
            this.enrollment = enrollment;
            this.timestamp = timestamp;
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public String getEnrollment() {
            return enrollment;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public String getDate() {
            return date;
        }
    }

    // Inner class: AttendanceAdapter for RecyclerView
    private class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {

        private List<Attendance> attendanceList;

        public AttendanceAdapter(List<Attendance> attendanceList) {
            this.attendanceList = attendanceList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_row, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Attendance attendance = attendanceList.get(position);
            holder.tvName.setText(attendance.getName());
            holder.tvEnrollment.setText(attendance.getEnrollment());
            holder.tvTimestamp.setText(attendance.getTimestamp());
        }

        @Override
        public int getItemCount() {
            return attendanceList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvName, tvEnrollment, tvTimestamp;

            public ViewHolder(View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvName);
                tvEnrollment = itemView.findViewById(R.id.tvEnrollment);
                tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            }
        }
    }
}
