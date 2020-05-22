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

import java.util.ArrayList;

public class FragmentWeather extends Fragment{
    public static final int THREAD_HANDLER_SUCCESS_INFO = 1;
    TextView weatherInfo;
    ForeCastManager mForeCast;
    Context mContext;
    Activity mActivity;

    String lon = "127.219540";
    String lat = "37.540417";

    ArrayList<ContentValues> mWeatherData;
    ArrayList<WeatherInfo> mWeatherInfomation;

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
        //weatherInfo = (TextView)view.findViewById(R.id.weather_info);
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

                Log.d("hihi", mWeatherInfomation.get(i).getWeather_Name());
                if(mWeatherInfomation.get(i).getWeather_Name().equals("clear") || mWeatherInfomation.get(i).getWeather_Name().equals("few clouds"))
                {
                    WeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.sunny, mContext.getApplicationContext().getTheme()));
                }

                else
                {
                    WeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.rainny, mContext.getApplicationContext().getTheme()));
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

                if(mWeatherInfomation.get(i).getWeather_Name().equals("clear") || mWeatherInfomation.get(i).getWeather_Name().equals("few clouds"))
                {
                    SecondWeatherIcon.setImageResource(R.drawable.sunny);
                }

                else
                {
                    SecondWeatherIcon.setImageResource(R.drawable.rainny);
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

                if(mWeatherInfomation.get(i).getWeather_Name().equals("clear") || mWeatherInfomation.get(i).getWeather_Name().equals("few clouds"))
                {
                    ThirdWeatherIcon.setImageResource(R.drawable.sunny);
                }

                else
                {
                    ThirdWeatherIcon.setImageResource(R.drawable.rainny);
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

                if(mWeatherInfomation.get(i).getWeather_Name().equals("clear") || mWeatherInfomation.get(i).getWeather_Name().equals("few clouds"))
                {
                    FourthWeatherIcon.setImageResource(R.drawable.sunny);
                }

                else
                {
                    FourthWeatherIcon.setImageResource(R.drawable.rainny);
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

                if(mWeatherInfomation.get(i).getWeather_Name().equals("clear") || mWeatherInfomation.get(i).getWeather_Name().equals("few clouds"))
                {
                    FifthWeatherIcon.setImageResource(R.drawable.sunny);
                }

                else
                {
                    FifthWeatherIcon.setImageResource(R.drawable.rainny);
                }
            }

            /*mData = mData + mWeatherInfomation.get(i).getWeather_Day() + "\r\n"
                    +  mWeatherInfomation.get(i).getWeather_Name() + "\r\n"
                    +  mWeatherInfomation.get(i).getClouds_Sort()
                    +  " /Cloud amount: " + mWeatherInfomation.get(i).getClouds_Value()
                    +  mWeatherInfomation.get(i).getClouds_Per() +"\r\n"
                    +  mWeatherInfomation.get(i).getWind_Name()
                    +  " /WindSpeed: " + mWeatherInfomation.get(i).getWind_Speed() + " mps" + "\r\n"
                    +  "Max: " + mWeatherInfomation.get(i).getTemp_Max() + "℃"
                    +  " /Min: " + mWeatherInfomation.get(i).getTemp_Min() + "℃" +"\r\n"
                    +  "Humidity: " + mWeatherInfomation.get(i).getHumidity() + "%";*/


            //mData = mData + "\r\n" + "----------------------------------------------" + "\r\n";
        }
        //return mData;
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
        for(int i = 0; i < 5; i++)
        {
            mWeatherInfomation.add(new WeatherInfo(
                    String.valueOf(mWeatherData.get(i).get("weather_Name")),  String.valueOf(mWeatherData.get(i).get("weather_Number")), String.valueOf(mWeatherData.get(i).get("weather_Much")),
                    String.valueOf(mWeatherData.get(i).get("weather_Type")),  String.valueOf(mWeatherData.get(i).get("wind_Direction")),  String.valueOf(mWeatherData.get(i).get("wind_SortNumber")),
                    String.valueOf(mWeatherData.get(i).get("wind_SortCode")),  String.valueOf(mWeatherData.get(i).get("wind_Speed")),  String.valueOf(mWeatherData.get(i).get("wind_Name")),
                    String.valueOf(mWeatherData.get(i).get("temp_Min")),  String.valueOf(mWeatherData.get(i).get("temp_Max")),  String.valueOf(mWeatherData.get(i).get("humidity")),
                    String.valueOf(mWeatherData.get(i).get("Clouds_Value")),  String.valueOf(mWeatherData.get(i).get("Clouds_Sort")), String.valueOf(mWeatherData.get(i).get("Clouds_Per")),String.valueOf(mWeatherData.get(i).get("day"))
            ));

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
                    if(mWeatherData.size() ==0)
                        weatherInfo.setText("데이터가 없습니다");

                    DataToInformation(); // 자료 클래스로 저장,

                    String data = "";
                    PrintValue();
                    //data = PrintValue();
                    //DataChangedToHangeul();
                    //data = data + PrintValue();

                    //weatherInfo.setText(data);
                    break;
                default:
                    break;
            }
        }
    };
}
