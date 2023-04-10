package weather;

public interface HttpCallBack {
    void onFinish(String response);
    void onError(Exception e);
}
