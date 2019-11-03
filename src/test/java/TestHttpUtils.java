import org.junit.Test;

public class TestHttpUtils {
    @Test
    public void testGet(){
        String url="http://www.baidu.com";
        String responseBody="";
        try{
            responseBody=HttpUtils.get(url);
            System.out.println(responseBody);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
