/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Ilan
 */
public class DB_Proj1_Part2_Inssert {
    
    public static void main(String args []) throws IOException{
        
        final PrintWriter out = new PrintWriter(new FileWriter("C:\\Users\\Ilan\\Documents\\School\\Columbia\\Spring 2013\\Databases\\part2\\insert.txt",true),true);
        final PrintWriter out_stop_times = new PrintWriter(new FileWriter("C:\\Users\\Ilan\\Documents\\School\\Columbia\\Spring 2013\\Databases\\part2\\insert_stops_times.txt",true),true);
        final PrintWriter out_real_times = new PrintWriter(new FileWriter("C:\\Users\\Ilan\\Documents\\School\\Columbia\\Spring 2013\\Databases\\part2\\insert_real_times.txt",true),true);
        String subset_real_time_path = "C:\\Users\\Ilan\\Documents\\School\\Columbia\\Spring 2013\\Databases\\part2\\subset_real_time.log";
        final PrintWriter out_subset_real_time = new PrintWriter(new FileWriter(subset_real_time_path,true),true);
        String subset_trips_path = "C:\\Users\\Ilan\\Documents\\School\\Columbia\\Spring 2013\\Databases\\part2\\subset_trips.log";
        final PrintWriter out_subset_trips = new PrintWriter(new FileWriter(subset_trips_path,true),true);
        String subset_stop_times_path = "C:\\Users\\Ilan\\Documents\\School\\Columbia\\Spring 2013\\Databases\\part2\\subset_stop_times.log";
        final PrintWriter out_subset_stop_times = new PrintWriter(new FileWriter(subset_stop_times_path,true),true);
        
        Set<String> set = new HashSet<String>();
        //set.add("BX36");
        //set.add("BX19");
       // set.add("BX15");
       // set.add("B63");
       // set.add("BX6");
       // set.add("BX7");
       // set.add("M100");
        set.add("M34A+");
        set.add("M34+");
        
       
                write_subset_stop_times(out_subset_stop_times, subset_trips_path,"manhattan");
        /*
        System.out.print("Writing subset real time ..... ");
        write_subset_real_time(out_subset_real_time,new HashSet(set));
        
        System.out.println("Writing trips ...... ");
        write_subset_trips(out_subset_trips,set, "manhattan");
        write_subset_trips(out_subset_trips,set, "bronx");
        write_subset_trips(out_subset_trips,set, "queens");
        write_subset_trips(out_subset_trips,set, "brooklyn");
        write_subset_trips(out_subset_trips,set, "staten_island");
            
         
        System.out.println("Writing stop times ..... ");
        write_subset_stop_times(out_subset_stop_times, subset_trips_path,"bronx");
        write_subset_stop_times(out_subset_stop_times, subset_trips_path,"queens");
        write_subset_stop_times(out_subset_stop_times, subset_trips_path, "brooklyn");
        write_subset_stop_times(out_subset_stop_times, subset_trips_path, "staten_island");
        **/
        
        HashMap<Integer, Stop> stops_map = new HashMap<Integer, Stop>();
        HashMap<String, Trip> trips_map = new HashMap<String, Trip>();
        
        out.println("SET DEFINE ~");
        out.println("SET TRANSACTION");
        
        //insert_into_cal(out);
        
        //insert_into_routes(out);
        
        /*
        insert_into_stops(out,"manhattan", stops_map);
        insert_into_stops(out,"bronx", stops_map);
        insert_into_stops(out,"brooklyn", stops_map);
        insert_into_stops(out,"queens", stops_map);
        insert_into_stops(out,"staten_island", stops_map);
        System.out.println(stops_map);
        * */
        
        //insert_into_trips(out, subset_trips_path, trips_map);
            
        insert_into_stop_times(out_stop_times, subset_stop_times_path);
        
        //insert_into_status(out);
        
        //insert_into_real_time(out_real_times, subset_real_time_path);
        
        out.println("COMMIT;");
        
    }

    private static void insert_into_cal(PrintWriter out) throws FileNotFoundException {
        
        Scanner s = new Scanner(new File("C:\\Users\\Ilan\\Documents\\School\\Columbia\\Spring 2013\\Databases\\google_transit_manhattan\\calendar.txt"));
        
        s.next(); //Get rid of header line
        while(s.hasNext()){
            String str = s.next();
            String [] array = str.split(",");
            out.printf("INSERT INTO cal VALUES( '%s', %s, %s, %s, %s, %s, %s, %s, to_date('%s','YYYYMMDD'), to_date('%s','YYYYMMDD') );\n",
                            array[0],array[1],array[2],array[3],array[4],array[5],array[6],array[7],array[8],array[9]);
        }
        
    }

    private static void insert_into_routes(PrintWriter out) throws FileNotFoundException {
        Scanner s = new Scanner(new File("C:\\Users\\Ilan\\Documents\\School\\Columbia\\Spring 2013\\Databases\\google_transit_manhattan\\routes.txt"));
        s.useDelimiter("\n");
        
        s.next(); //Get rid of header line
        while(s.hasNext()){
            String str = s.next();
            String [] array = str.split(",");
            out.printf("INSERT INTO routes VALUES( '%s', '%s', '%s' );\n",
                            array[0],array[3],array[7]);
        }
    }

    private static void insert_into_stops(PrintWriter out, String burrow, HashMap<Integer,Stop> map) throws FileNotFoundException {
        Scanner s = new Scanner(new File("C:\\Users\\Ilan\\Documents\\School\\Columbia\\Spring 2013\\Databases\\google_transit_"+ burrow +"\\stops.txt"));
        s.useDelimiter("\n");
        
        s.next(); //Get rid of header line
        while(s.hasNext()){
            String str = s.next();
            String [] array = str.split(",");
            out.printf("INSERT INTO stops VALUES( %s, '%s', %s, %s );\n",
                            array[0],array[2],array[4], array[5]);
            
            Integer stopID = Integer.parseInt(array[0]);
            Stop stop = new Stop(stopID, array[2], Double.parseDouble(array[4]), Double.parseDouble(array[5]));
            map.put(stopID,stop);
        }
    }

    private static void insert_into_trips(PrintWriter out, String path, HashMap<String,Trip> map) throws FileNotFoundException {
        Scanner s = new Scanner(new File(path));
        s.useDelimiter("\n");
        
        s.next(); //Get rid of header line
        while(s.hasNext()){
            String str = s.next();
            String [] array = str.split(",");
            out.printf("\"%s\", \"%s\", \"%s\", %s, \"%s\"\n",
                            array[0],array[1],array[2], array[4], array[3]);
            
            Trip trip = new Trip(array[0], array[1], array[3], array[3], array[4]);
            map.put(array[2], trip);
        }
    }

    private static void insert_into_stop_times(PrintWriter out, String path) throws FileNotFoundException {
        Scanner s = new Scanner(new File(path));
        s.useDelimiter("\n");
        
        s.next(); //Get rid of header line
        while(s.hasNext()){
            String str = s.next();
            String [] array = str.split(",");
            out.printf("\"%s\", 01/01/01 %s , 01/01/01 %s , %s, %s \n",
                    array[0],array[1],array[2], array[3], array[4]);
        }
    }

    private static void insert_into_status(PrintWriter out) throws FileNotFoundException {
        Scanner s = new Scanner(new File("statusbus.log"));
        s.useDelimiter("\n");
        
        s.next(); //Get rid of header line
        while(s.hasNext()){
            String str = s.next();
            String [] array = str.split(",");
            array[5] = array[5].replaceAll("'", "");
            out.printf("INSERT INTO status VALUES( %s, '%s', %s, '%s', to_date('%s', 'YYY-MM-DD'), to_date('%s', 'YYY-MM-DD'), '%s');\n",
                            array[0],array[3],array[4], array[6], array[1], array[2], array[5]);
        }
    }
    
    private static void insert_into_real_time(PrintWriter out, String subset_real_time_path) throws FileNotFoundException {
        Scanner s = new Scanner(new File(subset_real_time_path));
        s.useDelimiter("\n");
        
        
       s.next(); //Get rid of header line
        while(s.hasNext()){
            String str = s.next();
            String [] array = str.split(",");
            String route = array[3].toUpperCase();
            if( route.contains("M34") ){
                if( route.contains("M34-SBS") ){
                    route = "M34+";
                }
                else if( route.contains("M34A-SBS") ){
                    route = "M34A+";
                }
            }
            out.printf("\"%s\",\"%s\",%s,\"%s\",\"%s\",%s,\"%s\",%s,%s,\"%s\",%s %s\n",
                   array[0],route,array[6], array[5], array[4], array[7], array[8], array[9], array[10], array[11], array[1],array[2] );
        }
        
    }
    
    private static void write_subset_real_time(PrintWriter out, Set<String> routes) throws FileNotFoundException {
        Scanner s = new Scanner(new File("real-time.log")); 
        s.useDelimiter("\n");
        
        routes.add("M34-SBS");
        routes.add("M34A-SBS");
        routes.remove("M34+");
        routes.remove("M34A+");
        
        int count = 0;
        s.next();
        while( s.hasNext() ){
            String str = s.next();
            String [] array = str.split(",");
            String key = array[3].toUpperCase();
            if( routes.contains(key) ){
                out.println(str);
                count++;
            }
            
        }
        
        System.out.println(count);
    }

    private static void write_subset_trips(PrintWriter out, Set<String> set, String burrow) throws FileNotFoundException {
        Scanner s = new Scanner(new File("C:\\Users\\Ilan\\Documents\\School\\Columbia\\Spring 2013\\Databases\\google_transit_"+burrow+"\\trips.txt"));
        s.useDelimiter("\n");
        
        int count = 0;
        s.next();
        while( s.hasNext() ){
            String str = s.next();
            String [] array = str.split(",");
            String key = array[0];
            if( set.contains(key) ){
                out.println(str);
                count++;
            }
            
        }
        
        System.out.println(count);
    }

    private static void write_subset_stop_times(PrintWriter out, String subset_trips_path, String burrow) throws FileNotFoundException {
        Scanner s = new Scanner(new File("C:\\Users\\Ilan\\Documents\\School\\Columbia\\Spring 2013\\Databases\\google_transit_"+burrow+"\\stop_times.txt"));
        Scanner s2 = new Scanner(new File(subset_trips_path));
        s.useDelimiter("\n");
        s2.useDelimiter("\n");
        
        HashSet<String> set = new HashSet<String>();
        s2.next();
        while( s2.hasNext() ){
            String str = s2.next();
            String [] array = str.split(",");
            set.add(array[2]);
        }
        
        int count = 0;
        s.next();
        while( s.hasNext() ){
            String str = s.next();
            String [] array = str.split(",");
            String key = array[0];
            if( set.contains(key) ){
                out.println(str);
                count++;
            }
            
        }
        
        System.out.println(count);
    }
}
