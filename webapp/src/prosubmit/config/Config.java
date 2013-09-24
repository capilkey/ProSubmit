/**
 * 
 */
package prosubmit.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author ramone
 *
 */
public class Config {
	static Properties properties;
	static{
		try{
			properties.load(new FileInputStream("database.properties"));
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	static public String getProperty(String key){
		return properties.getProperty(key);
	}
}
