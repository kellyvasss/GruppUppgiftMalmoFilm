import java.io.FileInputStream;
import java.util.Properties;

public class KeyReader {

    Properties prop;

    private KeyReader(Properties properties) {
        this.prop = properties;
    }

    public static KeyReader fromFile(String fileKey) {
        Properties properties = new Properties();
        String userHome = System.getProperty("user.home");
        try {
            FileInputStream input = new FileInputStream(userHome + "/OneDrive/Dokument/APIkeys/" + fileKey + ".txt");
            properties.load(input);
        } catch (Exception e) {
        }
        return new KeyReader(properties);
    }

    public String getAPIKey(){
        return prop.getProperty("apiKey");
    }

    public String getKey(String key){
        return prop.getProperty(key);
    }
}