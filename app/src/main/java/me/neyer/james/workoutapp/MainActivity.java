package me.neyer.james.workoutapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button yes = (Button) findViewById(R.id.yesButton);
        yes.setOnClickListener(this);
        Button no = (Button) findViewById(R.id.noButton);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        boolean workedOut = false;
        switch (v.getId()){
            case R.id.yesButton:
                workedOut = true;
                break;
            case R.id.noButton:
                workedOut = false;
                break;
            default:
                break;
        }
        goToCalendarActivity(workedOut);
    }

    private void goToCalendarActivity(boolean workedOut) {

        Intent intent = new Intent(this, CalendarActivity.class);
        intent.putExtra("workedOut", workedOut);
        startActivity(intent);

    }
}
