import org.junit.Test;

public class TestCiphertext {
    String[] pwd={"V","u","l","n","e","r"};
    @Test
    public void testEncode(){
        Ciphertext ciphertext=new Ciphertext();
        String result=ciphertext.encode("assert",pwd);
        System.out.println(result);

        String decodeStr=ciphertext.decode(result,pwd);
        System.out.println(decodeStr);
    }
}
