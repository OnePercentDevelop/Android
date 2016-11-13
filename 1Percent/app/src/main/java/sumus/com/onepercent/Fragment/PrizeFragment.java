package sumus.com.onepercent.Fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.Base64;
import com.loopj.android.http.RequestParams;

import java.text.SimpleDateFormat;

import cz.msebera.android.httpclient.Header;
import sumus.com.onepercent.FontBaseActvity;
import sumus.com.onepercent.Object.MySharedPreference;
import sumus.com.onepercent.R;

import static android.content.Context.SENSOR_SERVICE;


public class PrizeFragment extends Fragment implements SensorEventListener {
    /*
    (f) InitWidget : 위젯 초기 설정
    (f) byteArrayToBitmap :  byte 를 bitmap으로 변환
    (f) StartAccelerSensor :  가속도 센서 측정 가능 상태
    (f) StopAccelerSensor : 가속도 센서 측정 중지 상태
    (f) InitAccelerSensor : 가속소 센서 초기 설정
    */


    // frgment init data
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    // 가속도 센서
    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;
    private float x, y, z;

    private static final int SHAKE_THRESHOLD = 800;
    private static final int DATA_X = SensorManager.DATA_X;
    private static final int DATA_Y = SensorManager.DATA_Y;
    private static final int DATA_Z = SensorManager.DATA_Z;

    private SensorManager sensorManager;
    private Sensor accelerormeterSensor;

    // Fragment
    Activity mActivity;
    View views;

    // widet
    ImageView prize_animaitonImg, prize_giftImg;
    TextView prize_shakeTv;

    // 변수
    AnimationDrawable frameAnimation; // 애니메이션
    MySharedPreference pref;

  public PrizeFragment() {  }

    public static PrizeFragment newInstance(String param1, String param2) {
        PrizeFragment fragment = new PrizeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        views = inflater.inflate(R.layout.fragment_prize, container, false);
        mActivity = getActivity();
        pref = new MySharedPreference(mActivity);

        InitAccelerSensor();
        InitWidget();

        FontBaseActvity fontBaseActvity = new FontBaseActvity();
        fontBaseActvity.setGlobalFont(views);

        return views;
    }

    void InitAccelerSensor(){
        sensorManager = (SensorManager) mActivity.getSystemService(SENSOR_SERVICE);
        accelerormeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    void  InitWidget(){
        prize_giftImg =  (ImageView)views.findViewById(R.id.prize_giftImg);


        if(pref.getPreferences("oneday","giftImg").equals("") != true)
            prize_giftImg.setImageBitmap(byteArrayToBitmap(Base64.decode(pref.getPreferences("oneday","giftImg"), Base64.DEFAULT)));

        prize_animaitonImg =  (ImageView)views.findViewById(R.id.prize_animaitonImg);
        prize_animaitonImg.setBackgroundResource(R.drawable.animaiton_list);
        frameAnimation = (AnimationDrawable) prize_animaitonImg.getBackground();  // 이미지를 동작시키기위해  AnimationDrawable 객체를 가져온다.
       // prize_shakeTv = (TextView) views.findViewById(R.id.prize_shakeTv);
    }

    public Bitmap byteArrayToBitmap(byte[] byteArray ) {  // byte -> bitmap 변환 및 반환
        Bitmap bitmap = BitmapFactory.decodeByteArray( byteArray, 0, byteArray.length ) ;
        return bitmap ;
    }


    // 가속도 센서
    int ShakeCount = 0;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            long gabOfTime = (currentTime - lastTime);
            if (gabOfTime > 100) {
                lastTime = currentTime;
                x = event.values[SensorManager.DATA_X];
                y = event.values[SensorManager.DATA_Y];
                z = event.values[SensorManager.DATA_Z];

                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;

                if (speed > SHAKE_THRESHOLD) {  // 이벤트발생!!
                    ShakeCount++;
                   // prize_shakeTv.setText(ShakeCount+"번");
                    frameAnimation.start();

                    if(ShakeCount>30){ // 할당량 채우면 애니메이션 및 센서 중지
                        Toast.makeText(mActivity, "shake it !!!", Toast.LENGTH_SHORT).show();
                        frameAnimation.stop();
                        StopAccelerSensor();
                    }
                }else{
                    frameAnimation.stop();
                }

                lastX = event.values[DATA_X];
                lastY = event.values[DATA_Y];
                lastZ = event.values[DATA_Z];
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}


    void StartAccelerSensor(){
        if (accelerormeterSensor != null)
            sensorManager.registerListener(this, accelerormeterSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    void StopAccelerSensor(){
        if (sensorManager != null)
            sensorManager.unregisterListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        StartAccelerSensor();
    }

    @Override
    public void onStop() {
        super.onStop();
        StopAccelerSensor();
    }

}
