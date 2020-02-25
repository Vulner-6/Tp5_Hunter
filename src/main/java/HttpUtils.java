import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 异步发送请求
 */
public class HttpUtils
{
    /**
     * 异步传输
     *
     * @param address
     * @param callback
     */
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback)
    {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * HTTP GET
     *
     * @param url
     * @return response.body().string()
     * @throws Exception
     */
    public static String get(String url) throws Exception
    {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }
}
