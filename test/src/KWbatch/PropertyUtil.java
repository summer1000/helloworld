//Security Classification: GE Confidential
package KWbatch;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PropertyUtil {

	//Get String in Properties;
	public static String getPropertyString(String propertyName,String propertyString) {
	       String stResult = "";
	       ResourceBundle prpt = null;
	       try {
	    	   prpt = ResourceBundle.getBundle(propertyName);
	       }
	       catch (MissingResourceException e) {
	          return stResult;
	       }

	       if (propertyString == null) {
	          return stResult;
	       }

	       String msg = null;
	       try {
	    	   msg = prpt.getString(propertyString);
	       }
	       catch (MissingResourceException e) {
	          return stResult;
	       }

	       if (msg == null) {
	          return stResult;
	       }

	       //System.out.println("msg="+msg);
	       stResult = msg;
	       return stResult;
	    }
}
