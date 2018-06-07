package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class  PropertyReader{
    
    	Properties prop = new Properties();
	InputStream input = null;
        public PropertyReader(String fileName) throws FileNotFoundException, IOException{
            	input = new FileInputStream(fileName);
		// load a properties file
		prop.load(input);
        }
        public String getProperty(String name){
            return prop.getProperty(name);
        }
}