package weather;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
    public static void sendRequestWithHttpURLConnection(String urladdress, final HttpCallBack listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(urladdress);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                   


                    InputStream is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder data = new StringBuilder();//接收到的服务器返回数据
                    String linedata;
                    while ((linedata = reader.readLine()) != null) {
                        data.append(linedata);
                    }
                    if (listener != null) {
                        listener.onFinish(data.toString());//回调MainActivity中的方法
                    }


                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                } finally {

                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();//启动线程


    }
}
