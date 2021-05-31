package org.xinc.code;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Admin
 */
public class CodeProperty {

    String workspace;


    public CodeProperty(String s) throws IOException {
        this.loadProperty(s);
    }

    public void loadProperty(InputStream stream) throws IOException {
        Properties properties = new Properties();
        properties.load(stream);
        this.workspace=properties.getProperty("app.code.workspace");
    }

    public void loadProperty(String path) throws IOException {
        loadProperty( this.getClass().getResourceAsStream(path));
    }
}
