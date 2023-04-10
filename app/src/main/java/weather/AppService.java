package weather;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AppService {
    //该方法使用较少，一般用于数据格式较为简单，层级较少的数据类型
    public static List<AppBean> getParseDataWithJsonObject(String jsonStr) {
        List<AppBean> appBeans = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                AppBean appBean = new AppBean();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                //Log.d("test",id);
                appBean.setId(id);
                String name = jsonObject.getString("name");
                //Log.d("test",name);
                appBean.setName(name);
                String version = jsonObject.getString("version");
                appBean.setVersion(version);
                appBeans.add(appBean);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return appBeans;
    }

    //解析JSON数据的常用方法
    public static List<AppBean> getParseDataWithGSON(String jsonStr) {
        Gson gson = new Gson();
        List<AppBean> applist = gson.fromJson(jsonStr, new TypeToken<List<AppBean>>() {
        }.getType());
        return applist;
    }

    //解析JSON数据的常用方法
    public static TqBean getParseDataWithGSONTQ(String jsonStr) {
        Gson gson = new Gson();
        TqBean tqBean = gson.fromJson(jsonStr, new TypeToken<TqBean>() {
        }.getType());
        return tqBean;
    }

    public static List<AppBean> getXMLParseDataWithPull(InputStream is) {
        List<AppBean> applist =null;
        AppBean app=null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(is,"utf-8");
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                //AppBean app=new AppBean();
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if("apps".equals(nodeName))
                        {
                            applist=new ArrayList<AppBean>();
                        }
                        else if ("id".equals(nodeName)) {
                            app=new AppBean();
                            app.setId(xmlPullParser.nextText());
                        } else if ("name".equals(nodeName)) {
                            app.setName(xmlPullParser.nextText());
                        } else if ("version".equals(nodeName)) {
                            app.setVersion(xmlPullParser.nextText());
                        }

                        break;
                    case XmlPullParser.END_TAG: {
                        if("app".equals(nodeName))
                        {
                            applist.add(app);
                        }
                    }
                    break;
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }


        } catch (XmlPullParserException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return applist;
    }
}
