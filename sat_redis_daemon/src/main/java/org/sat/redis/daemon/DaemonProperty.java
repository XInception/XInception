package org.sat.redis.daemon;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Admin
 */
public class DaemonProperty {

    String server;
    Integer port;

    public DaemonProperty(String s) throws IOException {
        this.loadProperty(s);
    }

    public void loadProperty(InputStream stream) throws IOException {
        Properties properties = new Properties();
        properties.load(stream);
        this.server=properties.getProperty("app.redis.server");
        this.port=Integer.parseInt(properties.getProperty("app.redis.port"));
    }

    public void loadProperty(String path) throws IOException {
        loadProperty( this.getClass().getResourceAsStream(path));
    }
}
