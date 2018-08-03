/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parsers;

/**
 *
 * @author Ilan
 */
class Trip {

    String routeID;
    String serviceID;
    String tripID;
    String head_sign;
    String direction;

    public Trip(String routeID, String serviceID, String tripID, String head_sign, String direction) {
        this.routeID = routeID;
        this.serviceID = serviceID;
        this.tripID = tripID;
        this.head_sign = head_sign;
        this.direction = direction;
    }
    
    
}
