package utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertiesReader {

    private Properties props;

    public PropertiesReader(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream input = classLoader.getResourceAsStream(fileName)) {
            this.props = new Properties();
            this.props.load(input);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String get(String property) {
        return this.props.getProperty(property);
    }
}
