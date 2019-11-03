import com.r4v3zn.fofa.core.DO.FofaData;
import com.r4v3zn.fofa.core.client.FofaClient;
import org.junit.Test;

import java.util.List;

/**
 * Fofa使用测试代码
 */
public class TestFofa {
    @Test
    public void testSearch() throws Exception{
        //测试本地ssr代理
        String proxyHost="127.0.0.1";
        String proxyPort="1080";
        System.getProperties().put("proxySet","true");
        System.getProperties().put("socksProxyHost",proxyHost);
        System.getProperties().put("socksProxyPort",proxyPort);
        //测试获取账号信息
        String email="";
        String key="";
        String query="";
        FofaClient client = new FofaClient(email, key);
        System.out.println(client.getUser());

        //测试获取查询结果，并只筛选出结果
        FofaData fofaData=client.getData(query,1,4);
        List results=fofaData.getResults();
        System.out.println(results+"\n");

        //测试发现，可以循环迭代
        for (Object str:results) {
            System.out.println(str);
        }
    }

}
