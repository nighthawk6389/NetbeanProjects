/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parsers;

/**
 *
 * @author Ilan
 */
class Stop {
    
    int stopID;
    String name;
    double latitude;
    double longitute;

    public Stop(int stopID, String name, double latitude, double longitude) {
        this.stopID = stopID;
        this.name = name;
        this.latitude = latitude;
        this.longitute = longitude;
    }
    
    
    
}
