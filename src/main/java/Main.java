import org.jasypt.util.text.BasicTextEncryptor;
import starter.Starter;

public class Main {
    public static void main(String[] args) {
        String key="store123";
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(key);
        String pw = basicTextEncryptor.encrypt("admin123");
        System.out.println(pw);
        System.out.println(basicTextEncryptor.decrypt(pw));
        Starter.main(args);

    }
}