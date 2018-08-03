/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package browser;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Mavis Beacon
 */
public class PageHistory {

    private ArrayList<String> visitedPages=new ArrayList<String>();
    int currentIndex=-1;

    public PageHistory(){
    }

    public String back(){
        if(!isBackEnabled())
            return null;
        currentIndex=currentIndex-1;
        return visitedPages.get(currentIndex);

    }
    public String forward(){
        if(!isForwardEnabled())
            return null;
        currentIndex=currentIndex+1;
        return visitedPages.get(currentIndex);
    }

    public void addToVisitedPages(URL url){
        visitedPages.add(++currentIndex,url.toString());
        System.out.println("Page History SP: "+(currentIndex)+" "+url.toString());
    }
    public ArrayList<String> getVisitedPages(){
        return visitedPages;
    }

    public boolean isBackEnabled(){
        if(currentIndex<=0)
            return false;
        return true;
    }
    public boolean isForwardEnabled(){
        int size=visitedPages.size();
        if(currentIndex+1>=size)
            return false;
        return true;
    }

}
