import org.junit.Test;

import java.util.List;

public class TestFileOperation
{
    @Test
    public void testReadTxt()
    {
        FileOperation fileOperation = new FileOperation();
        List<String> list = fileOperation.readTxt("F:\\example\\out\\artifacts\\example_jar\\text.txt");
        for (String i : list)
        {
            System.out.println(i);
        }
    }

    @Test
    public void testTime()
    {
        long start = System.currentTimeMillis();
        try
        {
            Thread.sleep(3201);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        long stop = System.currentTimeMillis();
        long result = stop - start;
        System.out.println(result / 1000 + "秒");
    }

    @Test
    public void testSSR()
    {

    }
}
