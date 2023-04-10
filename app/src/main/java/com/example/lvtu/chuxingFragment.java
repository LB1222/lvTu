package com.example.lvtu;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import weather.AppBean;
import weather.AppService;
import weather.HttpCallBack;
import weather.HttpUtil;
import weather.TqBean;


public class chuxingFragment extends Fragment {

    Button btLocation,  mbtn_parse, mBtn_weather ,mBtn_URL;
    TextView tvLatitude,tvLongitude, mtv_result ;
    TextView tv_ct,tv_wd,tv_tq, tv_tm, tv_wk;
    FusedLocationProviderClient client;
    public static List<AppBean> getParseDataWithGSON(String jsonStr) {
        Gson gson = new Gson();
        List<AppBean> applist = gson.fromJson(jsonStr, new TypeToken<List<AppBean>>() {
        }.getType());
        return applist;
    }
    Handler mHandler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what)
            {
                case 1:
                    TqBean tqBean=(TqBean) msg.obj;

                    String   CITY = tqBean.getCity();
                    tv_ct.setText("城市："+CITY);
                    String TMP = tqBean.getTem();
                    tv_wd.setText("温度："+TMP+"℃");
                    String WEA = tqBean.getWea();
                    tv_tq.setText("状况："+WEA);
                    String TM = tqBean.getUpdate_time();
                    tv_tm.setText("时间"+TM);
                    String WK = tqBean.getWeek();
                    tv_wk.setText(WK);

                    break;

            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_chuxing, container, false);

       btLocation = view.findViewById(R.id.bt_location);
       tvLatitude = view.findViewById(R.id.tv_latitute);
       tvLongitude = view.findViewById(R.id.tv_longitude);
        mbtn_parse = view.findViewById(R.id.btn_parse);
        mtv_result= view.findViewById(R.id.tv_result);
        mBtn_weather = view.findViewById(R.id.btn_weather);
        tv_ct = view.findViewById(R.id.tv_city);
        tv_tq = view.findViewById(R.id.tv_tianqi);
        tv_wd = view.findViewById(R.id.tv_wendu);
        tv_tm = view.findViewById(R.id.tv_time);
        tv_wk = view.findViewById(R.id.tv_week);



        mBtn_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://www.yiketianqi.com/free/day?appid=12577937&appsecret=udCY8s4p&unescape=1";
                HttpUtil.sendRequestWithHttpURLConnection(url, new HttpCallBack() {
                    @Override
                    public void onFinish(String response) {
                        String res=response.toString();
                        Log.d("Test", res);
                        TqBean tqBean = AppService.getParseDataWithGSONTQ(res);
                        Message message=new Message();
                        message.what=1;
                        message.obj=tqBean;
                        mHandler.sendMessage(message);
                    }

                    @Override
                    public void onError(Exception e) {

                    }



                });
            }

        });

        mbtn_parse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    InputStream is = getResources().openRawResource(R.raw.getdata);
                    byte[] buffer = new byte[is.available()];
                    is.read(buffer);
                    String jsonStr = new String(buffer,"utf-8");
                    JSONArray jsonArray= new JSONArray(jsonStr);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String version = jsonObject.getString("version");

                        mtv_result.append("\n id="+id+",\n name="+name+",\n version="+version);
                    }
                }catch (IOException e){
                    throw new RuntimeException(e);
                }catch (JSONException e){
                    throw new RuntimeException(e);
                }

            }
        });
       client = LocationServices.getFusedLocationProviderClient(getActivity());

       btLocation.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //条件检查
               if (ContextCompat.checkSelfPermission(getActivity()
               , Manifest.permission.ACCESS_FINE_LOCATION)
               == PackageManager.PERMISSION_GRANTED &&
               ContextCompat.checkSelfPermission(getActivity()
              ,Manifest.permission.ACCESS_FINE_LOCATION)
               == PackageManager.PERMISSION_GRANTED){
                   //CALL METHOD
                   getCurrentLacation();
               }else {
                  requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                  ,Manifest.permission.ACCESS_COARSE_LOCATION},100);

               }
           }
       });
//       mBtn_URL.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               new Thread(new Runnable() {
//                   @Override
//                   public void run() {
//                       try {
//                           //shift+f6直接修改文件名
////                           URL url = new URL("http://10.0.2.2:9102/get/text");
//                    URL url = new URL("http://www.sunofbeaches.com/shop/api/discovery/categories");
//
//                           HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                           connection.setConnectTimeout(10000);
//                           connection.setRequestMethod("GET");
//                           connection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9");
//
//                           connection.setRequestProperty("Accept","*/*");
//                           connection.connect();
//                           int responseCode = connection.getResponseCode();
//                           if (responseCode == 200){
//                               Map<String, List<String>> headerFields = connection.getHeaderFields();
//                               Set<Map.Entry<String, List<String>>> entries = headerFields.entrySet();
//                               for (Map.Entry<String, List<String>> entry : entries) {
//                                   Log.d(TAG, entry.getKey() + " == " + entry.getValue());
//                               }
//                               InputStream inputStream = connection.getInputStream();
//                               BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                               String line = bufferedReader.readLine();
//
//                               Log.d(TAG, "line --> " + line);
//                           }
//                       } catch (IOException e){
//                           throw new RuntimeException(e);
//                       }
//
//                   }
//               }).start();
//
//           }
      // });
       return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && (grantResults.length > 0) &&
                (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)){
            getCurrentLacation();
        }else {
            Toast.makeText(getActivity()
            ,"Permission denied",Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("MissingPermission")//getLastLocation()显红处理
    private void getCurrentLacation() {
        LocationManager locationManager = (LocationManager) getActivity()
                .getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            //当成功获取位置时，获取用户当前需要的位置
            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        //如果当前位置不为空，则读取信息至tv
                        tvLatitude.setText(String.valueOf(location.getLatitude()));
                        tvLongitude.setText(String.valueOf(location.getLongitude()));


                    } else {
                        //如果获取位置失败，重新请求获取

                        com.google.android.gms.location.LocationRequest locationRequest = new com.google.android.gms.location.LocationRequest()
                                .setPriority(LocationRequest.QUALITY_HIGH_ACCURACY)
                                  .setInterval(10000)
                                  .setFastestInterval(1000)
                                  .setNumUpdates(1);

                        LocationCallback locationCallback = new LocationCallback(){
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();
                                tvLatitude.setText(String.valueOf(location1.getLatitude()));
                                tvLongitude.setText(String.valueOf(location1.getLongitude()));

                            }
                        };
                        //请求位置更新
                        client.requestLocationUpdates(locationRequest
                        ,locationCallback, Looper.myLooper());
                    }



                }
            });
        }else {
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
    private void okHttpMethod()
    {
        OkHttpClient okHttpClient=new OkHttpClient();
        String url="https://www.yiketianqi.com/free/day?appid=12577937&appsecret=udCY8s4p&unescape=1"+"&city=海口";
        Request request=new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res=response.body().string();
               getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();


                    }
                });
                TqBean tqBean = AppService.getParseDataWithGSONTQ(res);
                Message message=new Message();
                message.what=1;
                message.obj=tqBean;
                mHandler.sendMessage(message);




            }

        });


    }
}