/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package browser;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author Mavis Beacon
 */
public class ScreenshotInfo implements Serializable{

    private Map<String,String> map=new HashMap<String,String>();

    public ScreenshotInfo(){

    }

    public void addScreenshot(String name,String url){
        map.put(name, url);
    }
    public String getScreenshot(String name){
        return map.get(name);
    }
}
