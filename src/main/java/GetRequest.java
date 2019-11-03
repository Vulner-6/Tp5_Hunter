import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetRequest {
    OkHttpClient client = new OkHttpClient();

    String run(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try{
            Response response = client.newCall(request).execute();
            return response.body().string();
        }catch (Exception e){
            return "出现异常";
        }
    }
}
