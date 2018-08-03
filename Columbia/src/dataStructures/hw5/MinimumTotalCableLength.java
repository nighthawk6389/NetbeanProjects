package dataStructures.hw5;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;
import javax.swing.JFrame;

/**
 *
 * @author Mavis Beacon
 */
public class MinimumTotalCableLength {
    
    public static void main(String args []) {
        Scanner scanner = null;
        try {
            File file = new File("uscities.txt");
            scanner = new Scanner(file);
            scanner.useDelimiter("\n");
        } catch (Exception ex) { 
            System.out.println(ex.toString());
            return;
        }
        
        ArrayList<City> cities = new ArrayList<City>();
        
        while( scanner.hasNext() ){
            String next = scanner.next();
            String [] array = next.split("[ \t]");
            City city = new City( array[0], Integer.parseInt(array[1].trim()), Integer.parseInt(array[2].trim()) );
            cities.add(city);
        }
        //System.out.println(cities);
        
        ArrayList<Edge> edges = new ArrayList<Edge>();
        for( int x = 0; x < cities.size(); x++){
            City outerCity = cities.get(x);
            for( int y = x + 1; y < cities.size(); y++){
                City innerCity = cities.get(y);
                Edge edge = new Edge(innerCity, outerCity);
                edge.setDisplay(true);
                //Edge edge2 = new Edge(outerCity, innerCity);
                edges.add(edge);
                //edges.add(edge2);
            }
        }
        System.out.println("-------------------------------------------------------");
        System.out.println(" Distances between cities: ");
        System.out.println("-------------------------------------------------------");
        int counter = 0;
        for( Edge edge : edges){
            System.out.println(edge);
            counter ++;
            
        }     
        
        ArrayList<Edge> answer = implementKruskal(cities, edges, cities.size());
        //System.out.println(answer);
        
        System.out.println();
        System.out.println("-------------------------------------------------------");
        System.out.println("Minimum Spanning Tree :");
        System.out.println("-------------------------------------------------------");
        for( Edge edge : edges){
            System.out.println(edge);
        }
        
        DisplayMapOfCities dmc = new DisplayMapOfCities(cities,answer);
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(800,600);
        frame.getContentPane().add(dmc);
        
    }
    
    public static ArrayList<Edge> implementKruskal(ArrayList<City> cities, ArrayList<Edge> edges, int vertices){
        
        Comparator<Edge> comp = new Comparator<Edge>(){
            @Override
            public int compare(Edge o1, Edge o2) {
                if( o1.getDistance() < o2.getDistance())
                    return -1;
                if( o1.getDistance() > o2.getDistance())
                    return 1;
                return 0;
            }      
        };

        HashMap<String,Integer> djSets = new HashMap<String,Integer>();
        PriorityQueue<Edge> pq = new PriorityQueue<Edge>(edges.size(),comp);
        pq.addAll(edges);
        ArrayList<Edge> mst = new ArrayList<Edge>();
        
        int counter = 1;
        while( pq.size() > 0 &&  mst.size() < vertices - 1){
            Edge e = pq.poll();
            Integer uSet = djSets.get( e.getCity1().getName() );
            Integer vSet = djSets.get( e.getCity2().getName() ); 
            
            if( uSet == null || vSet == null || uSet.intValue() != vSet.intValue() ){
                mst.add(e);
                
                if( uSet == null && vSet == null){
                   uSet = counter++;
                   djSets.put(e.getCity1().getName(), uSet);
                   djSets.put(e.getCity2().getName(), uSet); 
                   continue;
                   
                }
               
                if( uSet == null){
                   djSets.put(e.getCity1().getName(), vSet);
                   continue;
                }
                if( vSet == null){
                   djSets.put(e.getCity2().getName(), uSet);
                   continue;
                }
               
                for(String s : djSets.keySet()){
                   Integer i = djSets.get(s);
                   if( i.intValue() == vSet.intValue()){
                       djSets.put(s, uSet); 
                   }
                }
                
            }
        }
                
        return mst;
        
    }
    
}
