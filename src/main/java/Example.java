import com.r4v3zn.fofa.core.DO.FofaData;
import com.r4v3zn.fofa.core.client.FofaClient;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@CommandLine.Command(
        sortOptions = false,name="Example",
        mixinStandardHelpOptions = true,
        version = "Tp5_Hunter v1.0",
        synopsisHeading = "",
        customSynopsis = {
                "\n",
                "================================================================================",
                "工具名称：Tp5_Hunter v1.0",
                "开发者：Vulner-6",
                "Github地址：https://github.com/Vulner-6",
                "================================================================================",
                "命令语法：",
                "java -jar tool.jar <url来源> <利用方式> <是否添加本地代理地址>",
                "java -jar tool.jar <-option> [value]...[-option] [value]\n"
        },
        footer={
                "\n用法示例————(真实使用时请以导出的jar包名为准)",
                "java -jar tool.jar -u http://www.example.com --poc",
                "java -jar tool.jar -r list.txt -p",
                "java -jar tool.jar -fofa email_address,api_key --poc",
                "java -jar tool.jar -s 127.0.0.1:1080 -r list.txt -p",
                "--------------------------------------------------------------------------------",
                "注意:",
                "1.表示目标范围的选项，只能选择一个，比如-u和-r就互相冲突，只能选其一。",
                "2.每次批量扫描后，生成的results.txt会被覆盖，如果需要，请备份。",
                "3.可能还存在未知的BUG，初次开发，请多包涵。",
                "--------------------------------------------------------------------------------"
        }
)
public class Example implements Runnable{

    //自定义选项
    @CommandLine.Option(order = 1,names = {"-u","--url"},description = "输入目标域名，例如：http://1.1.1.1\n或https://www.test.com")
    String url;
    @CommandLine.Option(order = 2,names = {"-r","--readFile"},description = "输入要读取的文件路径")
    String readFilePath;
    @CommandLine.Option(order = 4,names = {"-p","--poc"},description = "使用poc验证漏洞")
    Boolean poc;
    //全网扫描命令
    @CommandLine.Option(order = 6,names = {"-f","--fofa"},split = ",",description = "使用fofa")
    String[] fofa;
    //联动本地ssr代理
    @CommandLine.Option(order = 7,names = {"-s","--ssr"},split = ":",description="代理设置")
    String[] ssr;

    Tp5_0_22 tp5_0_22=new Tp5_0_22();
    FileOperation fileOperation=new FileOperation();
    List<String> listStr=new ArrayList<String>();
    int fofaNum=0;
    int vulNum=0;
    int noVulNum=0;
    int exceptionNum=0;

    int txtVulNum=0;

    //重写Runnable接口中的run方法,也就是调用这个线程时执行的逻辑
    @Override
    public void run(){
        long startTime = System.currentTimeMillis();
        //判断是否设置了代理
        if(this.ssr!=null&&this.ssr.length>0)
        {
            //设置socks代理
            String proxyHost=this.ssr[0];
            String proxyPort=this.ssr[1];
            System.getProperties().put("proxySet","true");
            System.getProperties().put("socksProxyHost",proxyHost);
            System.getProperties().put("socksProxyPort",proxyPort);
        }
        //如果对单个url进行poc验证
        if(this.url!=null&&this.url!=""&&this.poc){
            try{
                tp5_0_22.check(this.url);
            }catch (Exception e){
                System.out.println(this.url+"    该网站访问超时，默认算作无法访问，请检查网络状态");
            }
        }

        //对fofa搜索结果进行poc验证
        if(this.fofa!=null&&this.fofa.length>0&&this.poc){
            Scanner scanner=new Scanner(System.in);
            System.out.print("请输入fofa查询语法：");
            scanner.useDelimiter("\n");    //这是一个坑点，不然如果fofa查询语句中有空格，程序就会报错！
            String query=scanner.next();
            String email=this.fofa[0];
            String key=this.fofa[1];
            System.out.print("请输入你希望查询到的数据个数（当真实数据超过指定条数，只取指定条数返回；" +
                    "当真实数据不足指定条数，就返回全部真实数据；" +
                    "当真实数据为0，就什么都不返回）：");
            String num=scanner.next();
            System.out.println("请勿操作，正常查询，稍等。。。");
            int test=Integer.parseInt(num.trim());    ///这里转换报错！！！被我改正了！！！
            if(test>0){
                //开始用fofa进行搜索，并返回筛选的结果
                FofaClient client = new FofaClient(email, key);
                FofaData fofaData;
                List results=null;     //fofa查询结果中的results部分
                List<String> pocResults=new ArrayList<String>();
                try{
                    fofaData=client.getData(query,1,test);
                    results=fofaData.getResults();
                    this.fofaNum=results.size();
                }catch (Exception e){
                    System.out.println("与fofa建立连接，向fofa发送查询请求时发生异常，请检查账号和api_key是否正确"+
                            "程序退出（也可能是fofa服务器出现问题，导致无法查询）");
                }
                //遍历fofa查询返回的数组
                for(Object domain:results){
                    try{
                        boolean vulboolean=tp5_0_22.check(domain.toString());
                        if(vulboolean){
                            this.vulNum++;
                            pocResults.add(domain.toString());
                        }
                        else{
                            this.noVulNum++;
                        }
                    }catch (Exception e){
                        System.out.println("访问该网站出现异常,错误url:"+domain.toString());
                        this.exceptionNum++;
                    }
                }
                //将fofa扫描出，存在漏洞的结果，写入fofaResults.txt中
                if(pocResults.size()>0){
                    fileOperation.writeTxt(pocResults,"fofaResults.txt");
                }

                System.out.println("==========================扫描结束==========================="
                        + "\nfofa查询总数据："+this.fofaNum+"条"
                        +"\n存在TP5远程代码执行漏洞网站数量："+this.vulNum+"条"
                        +"\n未扫描到漏洞的网站数量:"+this.noVulNum+"条"
                        +"\n访问异常的网站数量："+this.exceptionNum+"条");
            }else{
                System.out.println("请输入大于0的整数！");
            }

        }

        //对txt读取的内容进行poc验证
        if(this.readFilePath!=null&&this.readFilePath!=""&&this.poc){
            this.listStr=this.fileOperation.readTxt(this.readFilePath);
            boolean checkResult=false;
            List<String> tempResult=new ArrayList<String>();
            for(String str:this.listStr){
                try{
                    checkResult=this.tp5_0_22.check(str);
                    if(checkResult){
                        tempResult.add(str);
                        this.txtVulNum++;
                    }
                }catch (Exception e){
                    System.out.println("访问该网站出现异常,错误url:"+str);
                    this.exceptionNum++;
                }
            }
            //将存在漏洞的结果写入results.txt文件中
            if(tempResult.size()>0){
                boolean writeResult=fileOperation.writeTxt(tempResult,"results.txt");
                if(writeResult){
                    System.out.println("\n存在漏洞的扫描结果已经写入当前路径的results.txt文件");
                }
            }
            else {
                System.out.println("没有发现漏洞，因此没有生成results.txt文件");
            }
            //扫描结果打印输出
            System.out.println("==========================扫描结束==========================="+
                    "\n读取url数量："+fileOperation.readTxtNum+
                    "\n存在TP5远程代码执行漏洞网站数量："+fileOperation.writeTxtNum+
                    "\n未扫描到漏洞的网站数量:"+(fileOperation.readTxtNum-fileOperation.writeTxtNum-this.exceptionNum)+
                    "\n访问异常的网站数量："+this.exceptionNum);
        }

        long stopTime=System.currentTimeMillis();
        long result=(stopTime-startTime)/1000;
        System.out.println("共耗时："+result+"秒");
    }

    public static void main(String[] args) {
        // By implementing Runnable or Callable, parsing, error handling and handling user
        // requests for usage help or version help can be done with one line of code.
        int exitCode = new CommandLine(new Example()).execute(args);
        System.exit(exitCode);
    }
}
