/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan
 */

 
import com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Type;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.UnknownFieldSet.Field;
import com.google.transit.realtime.GtfsRealtime.*;
import com.google.transit.realtime.GtfsRealtime.TripUpdate.StopTimeUpdate;
import com.google.transit.realtime.NYCTSubway;
import com.google.transit.realtime.NYCTSubway.NyctTripDescriptor;
import com.google.transit.realtime.NYCTSubway.NyctTripDescriptor.Direction;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProtoPractice {
    
    public static void main(String args []){
        try { 
            
            final ExtensionRegistry extensionRegistry = ExtensionRegistry.newInstance();
            NYCTSubway.registerAllExtensions(extensionRegistry);
            
            URL url = new URL("http://datamine.mta.info/files/k38dkwh992dk/gtfs");
            FeedMessage fm = FeedMessage.parseFrom(url.openStream(),extensionRegistry);
            //FeedMessage fm = FeedMessage.parseFrom(new FileInputStream("C:\\Proto\\gtfs"),extensionRegistry);
            
            
            FeedEntity fe = fm.getEntity(10);
            TripUpdate tu = fe.getTripUpdate();
            TripDescriptor td = tu.getTrip();
            NyctTripDescriptor ntd = td.getExtension(NYCTSubway.nyctTripDescriptor);
            
            /*
            for(FeedEntity feItem : fm.getEntityList() ){
                TripUpdate tuItem = feItem.getTripUpdate();
                NyctTripDescriptor ntdItem = tuItem.getTrip().getExtension(NYCTSubway.nyctTripDescriptor); 
                if( ntdItem.getIsAssigned()){
                    System.out.print(tuItem.getTrip().getRouteId() + " (" + ntdItem.getDirection() + ") "+" : " + tuItem.getStopTimeUpdateCount());
                    System.out.print(" Closest Station: " + tuItem.getStopTimeUpdate(0).getStopId());
                    long time = tuItem.getStopTimeUpdate(0).getArrival().getTime();
                    long now = System.currentTimeMillis() / 1000;
                    long mins = (time - now) / 60;
                    long secs = (time - now ) % 60;
                    System.out.print(" Time: " + mins + " , " + secs + " seconds");
                    System.out.println();
                }
            }
            * */
            
            String train = "GS";
            Direction dir = Direction.SOUTH;
            String station = "505S";
            
           
            int count = 0;
            for(FeedEntity feItem : fm.getEntityList() ){            
                TripUpdate tuItem = feItem.getTripUpdate();
                NyctTripDescriptor ntdItem = tuItem.getTrip().getExtension(NYCTSubway.nyctTripDescriptor); 
                
                count++;
                if( feItem.hasAlert()){
                    System.out.println(feItem.getAlert().getInformedEntityList()+": " + feItem.getAlert());
                }
                
                if( !tuItem.getTrip().getRouteId().equals(train)){
                    continue;
                }
                
                if( !ntdItem.getIsAssigned()){
                    continue;
                }
                
                //System.out.println("Checking station for train " + ntdItem.getTrainId());
                for( StopTimeUpdate stu : tuItem.getStopTimeUpdateList()){
                    String stopId = stu.getStopId();
                    //System.out.println("\t Station: " + stopId);
                    //if( stopId.equalsIgnoreCase(station)){
                        long time = stu.getArrival().getTime();
                        long now = System.currentTimeMillis() / 1000;
                        long mins = (time - now) / 60;
                        long secs = (time - now ) % 60;
                        System.out.print(ntdItem.getTrainId());
                        System.out.print(" Time: " + mins + " , " + secs + " seconds");
                        System.out.print(" Currently at or approaching: " + tuItem.getStopTimeUpdate(0).getStopId());
                        System.out.println();
                    //}
                }
                
            }
            
            
            //System.out.println(tu);
    
        
            //com.google.transit.realtime.NYCTSubway.
            
            //System.out.println(fm.getEntity(10));
            System.out.println(fm.getEntityCount());
            
        } catch (IOException ex) {
            Logger.getLogger(ProtoPractice.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
} 
