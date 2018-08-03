/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package browser;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Mavis Beacon
 */
public class Settings implements Serializable {

    private String homePage;
    private ArrayList<String> favorites=new ArrayList<String>();
    private ArrayList<String> history=new ArrayList<String>();

    public Settings(){

    }

    public void setHomePage(String homePage){
        this.homePage=homePage;
    }
    public String getHomePage(){
        if(homePage==null)
            return "http://www.google.com";
        return homePage;
    }
    public void addFavorite(String s){
        if(s.equals("http://"))
            return;
        favorites.add(s);
    }
    public ArrayList<String> getFavorites(){
        return favorites;
    }
    public void addHistory(String s){
        if(s.equals("http://"))
            return;
        history.add(s);
    }
    public ArrayList<String> getHistory(){
        return history;
    }
    public void resetHistory(){
        history=new ArrayList<String>();
    }
}
