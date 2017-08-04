package me.neyer.james.workoutapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.squareup.timessquare.CalendarPickerView;

import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileInputStream;
import android.content.Context;
import android.content.Intent;


public class CalendarActivity extends AppCompatActivity {
    final Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
    CalendarPickerView calendar;
    private String filename = "savedDates";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        Date today = new Date();
        calendar.init(today, nextYear.getTime())
                .withSelectedDate(today);
        Intent i = getIntent();
        Boolean workedOut = i.getBooleanExtra("workedOut", false);
        writeFile(workedOut);
        colorCalendar(readFile());
    }
    protected void writeFile(Boolean answer) {
        Date todaysDate = new Date();
        String workoutToday = sdf.format(todaysDate);
        workoutToday = workoutToday+"x"+Boolean.toString(answer)+"y";
        FileOutputStream outputStream;
        try {
            if(getBaseContext().getFileStreamPath(filename).exists()) {
                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                outputStream.write(workoutToday.getBytes());
                outputStream.close();
            }
            else{
                File file = new File(this.getFilesDir(), filename);
                FileWriter writer = new FileWriter(file);
                writer.write(workoutToday);
                writer.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected ArrayList<Date> readFile(){
        ArrayList<Date> coloredList = new ArrayList<>();
        String temp = "";
        Date d;
        FileInputStream inputStream;
        try {
            inputStream = openFileInput(filename);
            int c;
            while((c = inputStream.read())!= -1){
                temp += Character.toString((char)c);
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] coloredArray = temp.split("y");
        if (coloredArray != null){
            for(String s: coloredArray){
                String[] coloredArray2 = s.split("x");
                if(Boolean.getBoolean(coloredArray2[1])) {
                    try {
                        d = sdf.parse(coloredArray2[0]);
                        coloredList.add(d);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else{
            String[] coloredArray2 = coloredArray[0].split("x");
            if(Boolean.getBoolean(coloredArray2[1])) {
                try {
                    d = sdf.parse(coloredArray2[0]);
                    coloredList.add(d);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return coloredList;
    }
    protected void colorCalendar(ArrayList<Date> coloredList){

        calendar.highlightDates(coloredList);
    }
}
