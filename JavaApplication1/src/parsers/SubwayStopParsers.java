/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ilan
 */
public class SubwayStopParsers {
    
    public static void main(String args []) throws Exception{
        BufferedReader reader = null, reader2 = null;
        Map<String, Map<String,Set<String>>> map = new LinkedHashMap<String,Map<String,Set<String>>>();
        Map<String, String> map2 = new HashMap<String,String>();
        try {
            File file = new File("C:\\Users\\Ilan\\Documents\\EclipseProjects\\NYCSubwayHelper\\google_transit\\stop_times.txt");
            reader = new BufferedReader(new FileReader(file));
            
            File file2 = new File("C:\\Users\\Ilan\\Documents\\EclipseProjects\\NYCSubwayHelper\\google_transit\\stops.txt");
            reader2 = new BufferedReader(new FileReader(file2));
            
            
            //Reading stop_times.txt and getting all the stops for each line 
            String line;
            reader.readLine();
            while( (line = reader.readLine()) != null){
                //System.out.println(line);
                String [] array = line.split(",");
                int index = array[0].lastIndexOf("_");
                int index2 = array[0].indexOf('.');
                String lineNum = array[0].substring(index + 1, index2);
                Map<String,Set<String>> trip_map = map.get(lineNum);
                if( trip_map == null){
                    trip_map = new LinkedHashMap<String,Set<String>>();
                    map.put(lineNum, trip_map);
                }
                
                String trip_id = array[0];
                Set<String> trip_stops = trip_map.get(trip_id);
                if( trip_stops == null){
                    trip_stops = new LinkedHashSet<String>();
                    trip_map.put(trip_id,trip_stops);
                }

                String station =  array[3];
                trip_stops.add(station);
              
            }
            
            /*Reading stops.txt to map each number to string of station */
            String line2;
            reader2.readLine();
            while( (line2 = reader2.readLine()) != null){
                System.out.println(line2);
                String array2 [] = line2.split(",");
                map2.put(array2[0], array2[2]);
            }
            
            FileOutputStream saveFile = new FileOutputStream("stationToIDMap.mapObj");
            ObjectOutputStream save = new ObjectOutputStream(saveFile);
            save.writeObject(map2);
            save.close();
            
            
            
            Map<String,Set<String>> trainLineMap = new LinkedHashMap<String,Set<String>>();
            for( String i : map.keySet()){
                Map<String,Set<String>> trip_map = map.get(i);
                int max = 0;
                String maxIndex = "-1";
                int index = 0;
                for( String j : trip_map.keySet()){
                    Set<String> stations = trip_map.get(j);
                    //System.out.println("Size: " + stations.size());
                    if( stations.size() > max){
                        max = stations.size();
                        maxIndex = j;
                    }
                    index++;
                }
                System.out.println("\n\n<string-array name=\"stations_array_"+i+"\">");
                ArrayList<String> s = new ArrayList(trip_map.get(maxIndex));
                trainLineMap.put(i+"",trip_map.get(maxIndex));
                if(maxIndex.contains("N")){
                    Collections.reverse(s);
                }
                for(String station : s){
                    String stationName = map2.get(station);
                    System.out.println("<item>"+stationName+"</item>");
                }
                System.out.println("</string-array>");
            }
            
            //System.out.println(map);
            
            System.out.println(trainLineMap);
            
            FileOutputStream saveLineStops = new FileOutputStream("trainLineStops.mapObj");
            ObjectOutputStream oos = new ObjectOutputStream(saveLineStops);
            oos.writeObject(trainLineMap);
            oos.close();
            
            /*
            for(Integer key : map.keySet()){
                System.out.println("\n\nkey was " + key);
                Set<Integer> set  = map.get(key);
                ArrayList<Integer> list = new ArrayList<Integer>(set);
                Collections.sort(list);
                for(Integer i : list){
                    String s = map2.get(i+"");
                    if( s == null){
                        System.out.println("Null: " + i);
                        throw new Exception("Station wasnt found");
                    }
                    System.out.println(s + " " + i);
                }   
            }
            * */
            
            
        } catch (IOException ex) {
            Logger.getLogger(SubwayStopParsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(SubwayStopParsers.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
