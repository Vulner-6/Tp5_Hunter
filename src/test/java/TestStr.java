import org.junit.Test;

import java.util.Scanner;

public class TestStr {
    @Test
    public void testIndexOf(){
        String str="'\"ThinkPHP\"&&apache";
        int start=str.indexOf("'");
        System.out.println(start);
    }

    @Test
    public void testCommandInput(){
        Scanner scanner=new Scanner(System.in);
        String str=scanner.next();
        System.out.println(str);

    }
}
