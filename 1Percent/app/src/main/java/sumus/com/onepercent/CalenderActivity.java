package sumus.com.onepercent;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class CalenderActivity extends AppCompatActivity implements View.OnClickListener{

    View actionView;
    Context mContext;

    TextView action_titleTv;
    ImageButton action_settingBtn;
    CalendarView calendarView;

    String today_YYYYMMDD;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_calender);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mContext = this;
        InitActionBar();
        InitWidget();
    }

    public void InitWidget() {
        calendarView = (CalendarView)findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                today_YYYYMMDD = year+"/"+(month+1)+"/"+dayOfMonth;

                Intent intent = getIntent();
                intent.putExtra("select_date", today_YYYYMMDD);
                setResult(RESULT_OK, intent);
                finish();
            }
        });



        long now = calendarView.getDate();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        today_YYYYMMDD = df.format(now);

    }

    void InitActionBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setPadding(0,0,0,0);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0.0F);
        getSupportActionBar().setCustomView(R.layout.actionbar_back);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        actionView = getSupportActionBar().getCustomView();
        action_titleTv = (TextView) actionView.findViewById(R.id.action_titleTv);
        action_titleTv.setText("질문 캘린더");
    }

    @Override
    public void onClick(View v) {

    }
}
