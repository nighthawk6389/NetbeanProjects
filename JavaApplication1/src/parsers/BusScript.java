/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parsers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ilan
 */
public class BusScript {
    
    public static void main(String args []) throws IOException{
        
        final int DISTANCE_TO_STOP = 25;
        final int SLEEP_TIME = 10*1000;
        final PrintWriter out = new PrintWriter(new FileWriter("real-time.log", true));
        final PrintWriter statusOut = new PrintWriter(new FileWriter("statusbus.log", true));
        
        
        Thread t1 = new Thread(new Runnable(){
            
            HashMap<String,String> map = new HashMap<String,String>();
            HashSet<String> status_list = new HashSet<String>();

            @Override
            public void run() {

                
                URL url = null;
                try {
                    url = new URL("http://bustime.mta.info/api/siri/vehicle-monitoring.json?key=4e391729-633d-40ae-a079-a753ca1df8f3");
                } catch (MalformedURLException ex) {
                    Logger.getLogger(BusScript.class.getName()).log(Level.SEVERE, null, ex);
                }

                while( true ){
                    try{
                        
                        try { 
                            Thread.sleep(SLEEP_TIME);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(BusScript.class.getName()).log(Level.SEVERE, null, ex);
                            return;
                        }
                        
                        
                        // general method, same as with data binding
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode root = null;
                        try {
                            // src can be a File, URL, InputStream etc
                            root = mapper.readTree(url); // src can be a File, URL, InputStream etc
                        } catch (IOException ex) { 
                            Logger.getLogger(BusScript.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        doVehicleMonitoring(root);
                        doStatusMonitoring(root);
                        

                    }  catch(Exception t){
                        t.printStackTrace();
                        System.out.println(t);
                    }
                } // end while
            } //end run
            
            private void doStatusMonitoring(JsonNode root){
                
                HashSet<String> status_new = new HashSet<String>();
                
                JsonNode sed = root.path("Siri").path("ServiceDelivery").path("SituationExchangeDelivery");
                int i = 0;
                for(; sed.has(i); i++){
                    
                    JsonNode pse  = sed.get(i).path("Situations").get("PtSituationElement");
                    if( pse.isMissingNode() ){
                            System.out.println("PSE (Situation) MISSING");
                    }
                  
                    
                    int j = 0;
                    for(; pse.has(j); j++){
                        JsonNode item = pse.get(j);
                        
                        JsonNode sn = item.get("SituationNumber");
                        String situationID = sn.textValue().substring(9);
                        
                        JsonNode pw = item.get("PublicationWindow");
                        JsonNode st = pw.get("StartTime");
                        JsonNode et = pw.get("EndTime");
                        // Format date and time
                        String text = st.textValue();
                        int index = text.indexOf("T");
                        String start_date = text.substring(1,index);
                        String start_time = text.substring(index + 1, text.lastIndexOf("."));
                        String end_date = "-1";
                        String end_time = "-1";
                        if( et != null ){
                            text = et.textValue();
                            index = text.indexOf("T");
                            end_date = text.substring(1,index);
                            end_time = text.substring(index + 1, text.lastIndexOf("."));
                        }
                        
                        JsonNode s = item.get("Summary");
                        JsonNode d = item.get("Description");
                        
                        JsonNode avj = item.get("Affects").get("VehicleJourneys").get("AffectedVehicleJourney");
                        List<String> lines = new ArrayList<String>();
                        List<String> directions = new ArrayList<String>();
                        int k = 0;
                        for(; avj.has(k); k++){
                            JsonNode affected = avj.get(k);
                            String line = affected.get("LineRef").textValue();
                            line = line.substring(9);
                            lines.add(line);
                            directions.add(affected.get("DirectionRef").textValue());
                        }
                        
                        JsonNode c = item.get("Consequences").get("Consequence").get(0).get("Condition");
                       
                        status_new.add(situationID);
                        
                        if( !status_list.contains(situationID) ){
                            status_list.add(situationID);
                            writeToStatusFile(
                                        situationID,
                                        start_date,
                                        start_time,
                                        end_date,
                                        end_time,
                                        lines,
                                        directions,
                                        s.textValue(),
                                        d.textValue(),
                                        c.textValue()
                                    );
                        }//end if

                    } //end pse loop
                } //end sed loop
                
                status_list.retainAll(status_new);
                
            } //do status monitoring
            
            private void doVehicleMonitoring(JsonNode root){
                
                JsonNode vmd = root.path("Siri").path("ServiceDelivery").path("VehicleMonitoringDelivery");
                JsonNode va  = vmd.get(0).path("VehicleActivity");
                if( vmd.isMissingNode() || va.isMissingNode() ){
                    System.out.println("MISSING");
                }


                int i = 0;
                for( ; va.has(i); i++ ){
                    JsonNode arrayItem = va.get(i);

                    JsonNode mvj = arrayItem.get("MonitoredVehicleJourney");
                    JsonNode recorded = arrayItem.get("RecordedAtTime");

                    JsonNode tripID = mvj.get("FramedVehicleJourneyRef").get("DatedVehicleJourneyRef");

                    JsonNode pln = mvj.get("PublishedLineName");
                    JsonNode pr = mvj.get("ProgressRate");
                    JsonNode vr = mvj.get("VehicleRef");  

                    if( pr.textValue().equalsIgnoreCase("noProgress") )
                        continue;

                    JsonNode mc = mvj.get("MonitoredCall");
                    if( mc == null ){
                        System.out.println("NULL: " + mvj);
                        continue;
                    }
                    JsonNode spr = mc.get("StopPointRef"); 
                    JsonNode vn = mc.get("VisitNumber");
                    JsonNode spn = mc.get("StopPointName");

                    JsonNode d = mc.get("Extensions").get("Distances");
                    JsonNode cdar = d.get("CallDistanceAlongRoute");
                    JsonNode drc = d.get("DistanceFromCall");
                    JsonNode pd = d.get("PresentableDistance");
                    JsonNode sfc = d.get("StopsFromCall");

                    //Format TripID
                    String tripID_text = tripID.textValue().substring(9);

                    // Format date and time
                    String text = recorded.textValue();
                    int index = text.indexOf("T");
                    String date = text.substring(1,index);
                    String time = text.substring(index + 1, text.lastIndexOf("."));

                    //Format stopID
                    String stopID = spr.textValue().substring(4);

                    if( drc.doubleValue() < DISTANCE_TO_STOP ){
                       boolean isWriteable = doAtStopAction(tripID_text,stopID);
                       if( isWriteable ){
                           writeToFile( 
                                    tripID_text,
                                    date,
                                    time,
                                    pln.textValue(),
                                    pr.textValue(),
                                    vr.textValue(),
                                    stopID,
                                    vn.intValue(),
                                    spn.textValue(),
                                    cdar.doubleValue(),
                                    drc.doubleValue(),
                                    pd.textValue()
                                   );
                       } // end isWritable
                    }// end if
                }//end for
            }// end do vehicle monitoring

            private boolean doAtStopAction(String tripID, String stopID) {
                
                String item = map.get(tripID);
                if( item == null ){
                    map.put(tripID, stopID);
                    return true;
                }
                
                if( item.equalsIgnoreCase(stopID) ){
                    //System.out.println("NOT WRITEABLE: " + tripID + " " + stopID);
                    return false;
                }
                
                map.put(tripID,stopID);
                return true;
                
            }

            private void writeToFile(String tripID, String date, 
                    String time, String line, String progress, 
                    String busNum, String stopID, int stopNumOnTrip, 
                    String stopName, double distFromStart, double distFromStop, 
                    String presentDist) {
                
                String data = String.format("%s,%s,%s,%s,%s,%s,%s,%d,%s,%f,%f,%s\n",
                        tripID, date, time, line, progress, busNum, stopID,
                        stopNumOnTrip, stopName, distFromStart, distFromStop,
                        presentDist);
                
                out.write(data);
                out.flush();

                
            }

            private void writeToStatusFile(String situationID, String start_date,
                    String start_time, String end_date, String end_time, 
                    List<String> lines, List<String> directions, 
                    String summary, String description, String condition) {
                
                Iterator lineIt = lines.iterator();
                Iterator directionsIt = directions.iterator();
                for(; lineIt.hasNext() && directionsIt.hasNext(); ){
                    String data = String.format("%s,%s,%s,%s,%s,%s,%s\n",
                        situationID, start_date, end_date, lineIt.next(),
                        directionsIt.next(), summary, /*description,*/ condition
                        );
                
                    statusOut.write(data);
                    statusOut.flush();
                    System.out.println("print: " + data);
                }
                
            }
            
        }); //end thread
        t1.start();
        	
        
    }



    
    
}
