package KeyReaderPackage;

import java.io.FileInputStream;
import java.util.Properties;

public class KeyReader {

    Properties prop;
    public KeyReader fromFile(String fileKey) {
        prop = new Properties();
        String userHome = System.getProperty("user.home");
        try {
            FileInputStream input = new FileInputStream(userHome + "/OneDrive/Dokument/APIkeys/" + fileKey + ".txt");
            prop.load(input);
        } catch (Exception e) {
        }

    }

    public String getAPIKey(){
        return prop.getProperty("apiKey");
    }

    public String getKey(String key){
        return prop.getProperty(key);
    }
}