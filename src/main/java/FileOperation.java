import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileOperation
{
    /**
     * 按行读取txt中的内容
     *
     * @param filePath
     * @return List<String>
     */
    public int readTxtNum = 0;
    public int writeTxtNum = 0;

    public List<String> readTxt(String filePath)
    {
        List<String> results = new ArrayList<String>();
        String temp = "";
        try
        {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(filePath), "gbk");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((temp = bufferedReader.readLine()) != null)
            {
                results.add(temp);
                this.readTxtNum++;
            }
        }
        catch (Exception e)
        {
            System.out.println("读取" + filePath + "文件时，发生异常");
        }
        return results;
    }

    public boolean writeTxt(List<String> listStr, String outputName)
    {
        File resultTxt = new File(outputName);
        try
        {
            FileWriter fileWriter = new FileWriter(resultTxt);    //创建写入流，会覆盖
            for (String str : listStr)
            {
                fileWriter.write(str + "\r\n");
                this.writeTxtNum++;
            }
            fileWriter.close();
            return true;
        }
        catch (Exception e)
        {
            System.out.println("创建写入流的时候发生异常");
            return false;
        }
    }

    /**
     * 后期再优化吧,暂时不写了
     *
     * @param listStr
     * @return
     */
    public List<String> restructuring(List<String> listStr)
    {
        List<String> results = new ArrayList<String>();
        for (String str : listStr)
        {
            //有http或https的前缀
            if (str.indexOf("http") != -1)
            {

            }
            else
            {
                //没有协议前缀
            }
        }
        return results;
    }
}
