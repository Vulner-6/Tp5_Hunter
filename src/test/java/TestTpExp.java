import okhttp3.Call;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;

public class TestTpExp {
    Tp5_0_22 tp5_0_22=new Tp5_0_22();
    String targetUrl="http://104.160.27.198";
    @Test
    public void testTp5_0_22() throws Exception{
        //还没改，默认发送http请求，获得漏洞是否存在的结果
        Boolean result=tp5_0_22.check(targetUrl);
        System.out.println(result);
    }

    @Test
    public void testYiBu(){
        HttpUtils.sendOkHttpRequest("http://www.baidu.com",new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response)  throws IOException{
                //得到服务器返回的具体内容
                String responseData=response.body().toString();
                System.out.println("发送成功");
            }
            @Override
            public void onFailure(Call call,IOException e){
                //在这里对异常情况进行处理
                System.out.println("出现异常");
            }
        });
    }

    @Test
    public void testException(){
        GetRequest getRequest=new GetRequest();
        try{
            String result=getRequest.run("http://www.baidu.com");
            System.out.println(result);
        }
        catch (Exception e){
            System.out.println("出现异常");
        }
    }
}

