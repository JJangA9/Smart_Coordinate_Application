package com.example.smart_coordinator;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FragmentWeather extends Fragment{
    public static final int THREAD_HANDLER_SUCCESS_INFO = 1;
    ForeCastManager mForeCast;
    Context mContext;
    Activity mActivity;

    String lon = "127.219540";
    String lat = "37.540417";

    ArrayList<ContentValues> mWeatherData;
    ArrayList<WeatherInfo> mWeatherInfomation;
    ArrayList<WeatherInfo> mDayInformation;

    ImageView WeatherIcon, SecondWeatherIcon, ThirdWeatherIcon, FourthWeatherIcon, FifthWeatherIcon;
    TextView Temperature, SecondTemperature, ThirdTemperature, FourthTemperature, FifthTemperature;
    TextView SecondTime, ThirdTime, FourthTime, FifthTime;
    TextView SecondHumidity, ThirdHumidity, FourthHumidity, FifthHumidity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = (Activity)context;

        //mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_weather, container, false);

        WeatherIcon = (ImageView)view.findViewById(R.id.weatherIcon);
        SecondWeatherIcon = (ImageView)view.findViewById(R.id.secondWeatherIcon);
        ThirdWeatherIcon = (ImageView)view.findViewById(R.id.thirdWeatherIcon);
        FourthWeatherIcon = (ImageView)view.findViewById(R.id.fourthWeatherIcon);
        FifthWeatherIcon = (ImageView)view.findViewById(R.id.fifthWeatherIcon);

        Temperature = (TextView)view.findViewById(R.id.temperature);
        SecondTemperature = (TextView)view.findViewById(R.id.secondTemper);
        ThirdTemperature = (TextView)view.findViewById(R.id.thirdTemper);
        FourthTemperature = (TextView)view.findViewById(R.id.fourthTemper);
        FifthTemperature = (TextView)view.findViewById(R.id.fifthTemper);

        SecondTime = (TextView)view.findViewById(R.id.secondTime);
        ThirdTime = (TextView)view.findViewById(R.id.thirdTime);
        FourthTime = (TextView)view.findViewById(R.id.fourthTime);
        FifthTime = (TextView)view.findViewById(R.id.fifthTime);

        SecondHumidity = (TextView)view.findViewById(R.id.secondHumidity);
        ThirdHumidity = (TextView)view.findViewById(R.id.thirdHumidity);
        FourthHumidity = (TextView)view.findViewById(R.id.fourthHumidity);
        FifthHumidity = (TextView)view.findViewById(R.id.fifthHumidity);

        Initialize();

        return view;
    }

    public void Initialize()
    {
        //weatherInfo = (TextView)getView().findViewById(R.id.weather_info);
        mWeatherInfomation = new ArrayList<>();
        mDayInformation = new ArrayList<>();
        mForeCast = new ForeCastManager(lon,lat,this);
        mForeCast.run();
    }

    public void PrintValue()
    {
        String mData = "";
        for(int i = 0; i < mWeatherInfomation.size(); i ++)
        {
            if(i == 0)
            {
                String temper = mWeatherInfomation.get(i).getTemp_Max();
                temper = temper.substring(0,2);
                temper = temper + "\u00B0";
                Temperature.setText(temper);

                Log.d("hihi", mWeatherInfomation.get(i).getWeather_Day());
                if(mWeatherInfomation.get(i).getWeather_Name().equals("clear")) {
                    WeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.sunny, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("some clouds")) {
                    WeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.somecloud, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("many clouds")) {
                    WeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.cloudy, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("rain")) {
                    WeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.rainny, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("snow")) {
                    WeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.snow, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("foggy")) {
                    WeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.foggy, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("thunderStorm")) {
                    WeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.thunderstorm, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("thunderStormAndRain")) {
                    WeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.thunderandrain, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("wind")) {
                    WeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.wind, mContext.getApplicationContext().getTheme()));
                }
                else {
                    WeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.sunny, mContext.getApplicationContext().getTheme()));
                }
            }

            else if(i == 1)
            {
                String time = mWeatherInfomation.get(i).getWeather_Day();
                time = time.substring(11, 16);
                SecondTime.setText(time);

                String temper = mWeatherInfomation.get(i).getTemp_Max();
                temper = temper.substring(0,2);
                SecondTemperature.setText(temper);
                SecondHumidity.setText(mWeatherInfomation.get(i).getHumidity());

                if(mWeatherInfomation.get(i).getWeather_Name().equals("clear")) {
                    SecondWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.sunny, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("some clouds")) {
                    SecondWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.somecloud, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("many clouds")) {
                    SecondWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.cloudy, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("rain")) {
                    SecondWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.rainny, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("snow")) {
                    SecondWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.snow, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("foggy")) {
                    SecondWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.foggy, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("thunderStorm")) {
                    SecondWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.thunderstorm, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("thunderStormAndRain")) {
                    SecondWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.thunderandrain, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("wind")) {
                    SecondWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.wind, mContext.getApplicationContext().getTheme()));
                }

                else {
                    WeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.sunny, mContext.getApplicationContext().getTheme()));
                }
            }

            else if(i == 2)
            {
                String time = mWeatherInfomation.get(i).getWeather_Day();
                time = time.substring(11, 16);
                ThirdTime.setText(time);

                String temper = mWeatherInfomation.get(i).getTemp_Max();
                temper = temper.substring(0,2);
                ThirdTemperature.setText(temper);
                ThirdHumidity.setText(mWeatherInfomation.get(i).getHumidity());

                if(mWeatherInfomation.get(i).getWeather_Name().equals("clear")) {
                    ThirdWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.sunny, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("some clouds")) {
                    ThirdWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.somecloud, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("many clouds")) {
                    ThirdWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.cloudy, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("rain")) {
                    ThirdWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.rainny, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("snow")) {
                    ThirdWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.snow, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("foggy")) {
                    ThirdWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.foggy, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("thunderStorm")) {
                    ThirdWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.thunderstorm, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("thunderStormAndRain")) {
                    ThirdWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.thunderandrain, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("wind")) {
                    ThirdWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.wind, mContext.getApplicationContext().getTheme()));
                }
                else {
                    ThirdWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.sunny, mContext.getApplicationContext().getTheme()));
                }
            }

            else if(i == 3)
            {
                String time = mWeatherInfomation.get(i).getWeather_Day();
                time = time.substring(11, 16);
                FourthTime.setText(time);

                String temper = mWeatherInfomation.get(i).getTemp_Max();
                temper = temper.substring(0,2);
                FourthTemperature.setText(temper);
                FourthHumidity.setText(mWeatherInfomation.get(i).getHumidity());

                if(mWeatherInfomation.get(i).getWeather_Name().equals("clear")) {
                    FourthWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.sunny, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("some clouds")) {
                    FourthWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.somecloud, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("many clouds")) {
                    FourthWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.cloudy, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("rain")) {
                    FourthWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.rainny, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("snow")) {
                    FourthWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.snow, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("foggy")) {
                    FourthWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.foggy, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("thunderStorm")) {
                    FourthWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.thunderstorm, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("thunderStormAndRain")) {
                    FourthWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.thunderandrain, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("wind")) {
                    FourthWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.wind, mContext.getApplicationContext().getTheme()));
                }
                else {
                    FourthWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.sunny, mContext.getApplicationContext().getTheme()));
                }
            }

            else if(i == 4)
            {
                String time = mWeatherInfomation.get(i).getWeather_Day();
                time = time.substring(11, 16);
                FifthTime.setText(time);

                String temper = mWeatherInfomation.get(i).getTemp_Max();
                temper = temper.substring(0,2);
                FifthTemperature.setText(temper);
                FifthHumidity.setText(mWeatherInfomation.get(i).getHumidity());

                if(mWeatherInfomation.get(i).getWeather_Name().equals("clear")) {
                    FifthWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.sunny, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("some clouds")) {
                    FifthWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.somecloud, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("many clouds")) {
                    FifthWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.cloudy, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("rain")) {
                    FifthWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.rainny, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("snow")) {
                    FifthWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.snow, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("foggy")) {
                    FifthWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.foggy, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("thunderStorm")) {
                    FifthWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.thunderstorm, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("thunderStormAndRain")) {
                    FifthWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.thunderandrain, mContext.getApplicationContext().getTheme()));
                }

                else if(mWeatherInfomation.get(i).getWeather_Name().equals("wind")) {
                    FifthWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.wind, mContext.getApplicationContext().getTheme()));
                }
                else {
                    FifthWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.sunny, mContext.getApplicationContext().getTheme()));
                }
            }
        }
    }

    public void DataChangedToHangeul()
    {
        for(int i = 0 ; i <mWeatherInfomation.size(); i ++)
        {
            WeatherToHangeul mHangeul = new WeatherToHangeul(mWeatherInfomation.get(i));
            mWeatherInfomation.set(i,mHangeul.getHangeulWeather());
        }
    }

    public void DataToInformation()
    {
        //get current time
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("MM-dd HH:mm:ss");
        String getTime = simpleDate.format(mDate);
        String getDay = getTime.substring(3, 5);
        getTime = getTime.substring(6,8);

        getDay = getDay + getTime;
        int t = 0;

        for(int i = 0; i < mWeatherData.size(); i++)
        {
            mDayInformation.add(new WeatherInfo(
                    String.valueOf(mWeatherData.get(i).get("weather_Name")),  String.valueOf(mWeatherData.get(i).get("weather_Number")), String.valueOf(mWeatherData.get(i).get("weather_Much")),
                    String.valueOf(mWeatherData.get(i).get("weather_Type")),  String.valueOf(mWeatherData.get(i).get("wind_Direction")),  String.valueOf(mWeatherData.get(i).get("wind_SortNumber")),
                    String.valueOf(mWeatherData.get(i).get("wind_SortCode")),  String.valueOf(mWeatherData.get(i).get("wind_Speed")),  String.valueOf(mWeatherData.get(i).get("wind_Name")),
                    String.valueOf(mWeatherData.get(i).get("temp_Min")),  String.valueOf(mWeatherData.get(i).get("temp_Max")),  String.valueOf(mWeatherData.get(i).get("humidity")),
                    String.valueOf(mWeatherData.get(i).get("Clouds_Value")),  String.valueOf(mWeatherData.get(i).get("Clouds_Sort")), String.valueOf(mWeatherData.get(i).get("Clouds_Per")),String.valueOf(mWeatherData.get(i).get("day"))
            ));

            String time = mDayInformation.get(i).getWeather_Day();
            String day = time.substring(8, 10);
            time = time.substring(11, 13);

            day = day + time;

            //If getweather data's time is later than current time, get that data
            if((day.compareTo(getDay) > 0) && (t < 5))
            {
                mWeatherInfomation.add(new WeatherInfo(
                        String.valueOf(mWeatherData.get(i).get("weather_Name")),  String.valueOf(mWeatherData.get(i).get("weather_Number")), String.valueOf(mWeatherData.get(i).get("weather_Much")),
                        String.valueOf(mWeatherData.get(i).get("weather_Type")),  String.valueOf(mWeatherData.get(i).get("wind_Direction")),  String.valueOf(mWeatherData.get(i).get("wind_SortNumber")),
                        String.valueOf(mWeatherData.get(i).get("wind_SortCode")),  String.valueOf(mWeatherData.get(i).get("wind_Speed")),  String.valueOf(mWeatherData.get(i).get("wind_Name")),
                        String.valueOf(mWeatherData.get(i).get("temp_Min")),  String.valueOf(mWeatherData.get(i).get("temp_Max")),  String.valueOf(mWeatherData.get(i).get("humidity")),
                        String.valueOf(mWeatherData.get(i).get("Clouds_Value")),  String.valueOf(mWeatherData.get(i).get("Clouds_Sort")), String.valueOf(mWeatherData.get(i).get("Clouds_Per")),String.valueOf(mWeatherData.get(i).get("day"))
                ));
                t++;

            }

            else if(t == 5)
                break;
        }

    }

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case THREAD_HANDLER_SUCCESS_INFO :
                    mForeCast.getmWeather();
                    mWeatherData = mForeCast.getmWeather();

                    DataToInformation(); // 자료 클래스로 저장,

                    String data = "";
                    DataChangedToHangeul();
                    PrintValue();

                    break;
                default:
                    break;
            }
        }
    };
}
