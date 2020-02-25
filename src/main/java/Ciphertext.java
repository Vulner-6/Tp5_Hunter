import java.util.Base64;

public class Ciphertext
{
    /**
     * 加密字符串
     *
     * @param str
     * @param pwdArray
     * @return
     */
    public String encode(String str, String[] pwdArray)
    {
        String result = "";
        String tempStr = "";
        int intIndex = 1;

        for (String ch : pwdArray)
        {
            if (intIndex == 1)
            {
                try
                {
                    result = Base64.getEncoder().encodeToString(str.getBytes("utf-8"));
                    result = ch + result;
                    tempStr = result;
                    intIndex++;
                }
                catch (Exception e)
                {

                }
            }
            else
            {
                try
                {
                    result = Base64.getEncoder().encodeToString(tempStr.getBytes("utf-8"));
                    result = ch + result;
                    tempStr = result;
                }
                catch (Exception e)
                {
                    System.out.println("对字符串进行base64加密时出现异常");
                }
            }
        }
        //最后再加个随机数
        char randomCh = (char) (int) (Math.random() * 26 + 97);
        try
        {
            result = randomCh + Base64.getEncoder().encodeToString(result.getBytes("utf-8"));
        }
        catch (Exception e)
        {
            System.out.println("对字符串进行base64加密时出现异常");
        }
        return result;
    }

    public String decode(String str, String[] pwdArray)
    {
        byte[] tempByte;
        String decodeStr = str.substring(1);
        tempByte = Base64.getDecoder().decode(decodeStr);
        decodeStr = new String(tempByte);
        for (String ch : pwdArray)
        {
            decodeStr = decodeStr.substring(1);
            tempByte = Base64.getDecoder().decode(decodeStr);
            decodeStr = new String(tempByte);
        }
        return decodeStr;
    }
}
