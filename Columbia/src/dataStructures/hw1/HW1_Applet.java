package dataStructures.hw1;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import dataStructures.TimeInterval;
import graph.Axis;
import graph.DataSet;
import graph.Graph2D;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Random;

/**
 *
 * @author Ilan Elkobi
 */
public class HW1_Applet extends Applet {
    
    private static int STOP_TIME = 30;
    
    @Override
    public void init(){
        
        int max = 100;
       
        Random generator= new Random();
        TimeInterval time = new TimeInterval();
        int numArray [];
        int randInt;
        
        double timeStore1 [] = new double[40];
        int timeStoreCount1 = 0;
        double timeStore2 [] = new double[40];
        int timeStoreCount2 = 0;
        double timeStore3 [] = new double[40];
        int timeStoreCount3 = 0;
        
        
        /** Algorithm 1 **/
        System.out.println("Starting Algorithm 1...");
        time.startTiming();
        while( (time.getElapsedTime() < STOP_TIME) && (max < 500000) ){
            numArray = new int[max];
            
            OUTER:
            for(int i = 0; i < max; i++){
                randInt = generator.nextInt(max);
                for(int j = 0; j < i; j++){
                    if( numArray[j] == randInt ){
                        i--;
                        continue OUTER;
                    }
                }
                numArray[i] = randInt;
            }
            
            timeStore1[timeStoreCount1++] = max;
            timeStore1[timeStoreCount1++] = time.getElapsedTime();
            max = max * 2;
            
            //System.out.println(time.getElapsedTime());
        }
        time.endTiming();
        System.out.println("Algorithm 1 Ended. Total time: " + time.getElapsedTime());
        
        /** Algorithm 2 **/
        System.out.println("Starting Algorithm 2...");
        time = new TimeInterval();
        time.startTiming();
        max = 100;
        boolean used [];        
        while( (time.getElapsedTime() < STOP_TIME) && (max < 500000) ){
            numArray = new int[max];
            used = new boolean[max]; 
            
            for(int i = 0; i < max; i++){
                randInt = generator.nextInt(max);
                if( used[randInt] == true ){
                    i--;
                    continue;
                }
                numArray[i] = randInt;
                used[randInt] = true;
            }
            
            timeStore2[timeStoreCount2++] = max;
            timeStore2[timeStoreCount2++] = time.getElapsedTime();
            //System.out.println(time.getElapsedTime() + " " + max);
            
            max = max * 2;
        }
        time.endTiming();
        System.out.println("Algorithm 2 Ended. Total time: " + time.getElapsedTime());
        
        /** Algorithm 3 **/
        System.out.println("Algorithm 3 started...");
        time = new TimeInterval();
        time.startTiming();
        max = 100;
        int temp;
        while( (time.getElapsedTime() < STOP_TIME) && (max < 500000) ){
            numArray = new int[max]; 
            
            for(int i = 0; i < max; i++){
                numArray[i] = i + 1;
            }
            
            for(int i = 0; i < max; i++){
                randInt = generator.nextInt(max);
                temp = numArray[i];
                numArray[i] = numArray[randInt];
                numArray[randInt] = temp;
            }
            
            timeStore3[timeStoreCount3++] = max;
            timeStore3[timeStoreCount3++] = time.getElapsedTime();
            //System.out.println(time.getElapsedTime() + " " + max);
            
            max = max * 2;
        }
        time.endTiming();
        System.out.println("Algorithm 3 Ended. Total time: " + time.getElapsedTime());
                
        Graph2D graph1 = this.getGraphFromDataSet(timeStore1, timeStoreCount1, Color.RED, "Y=Algorithm#1");
        Graph2D graph2 = this.getGraphFromDataSet(timeStore2, timeStoreCount2, Color.blue, "Y=Algorithm#2");
        Graph2D graph3 = this.getGraphFromDataSet(timeStore3, timeStoreCount3, Color.GREEN, "Y=Algorithm#3");
        
        setLayout(new GridLayout(3,1) );
        
        add(graph1);
        add(graph2);
        add(graph3);
        
    }
    
    private Graph2D getGraphFromDataSet(double[] timeStore,int timeStoreCount, Color lineColor, String title){
        
        Graph2D graph;
        DataSet data1;
        Axis    xaxis;
        Axis    yaxis_left;   
        
        graph = new Graph2D();
        graph.drawzero = false;
        graph.drawgrid = false;
        graph.borderTop = 50;
        graph.borderRight=100;
        
        data1 = graph.loadDataSet(timeStore,timeStoreCount/2);
        data1.linestyle = 1;
        data1.linecolor   =  lineColor;
        data1.marker    = 1;
        data1.markerscale = 1;
        data1.markercolor = new Color(0,0,255);
        data1.legend(200,100,title);
        data1.legendColor(Color.black);
        
        xaxis = graph.createAxis(Axis.BOTTOM);
        xaxis.attachDataSet(data1);
        xaxis.setTitleText("N (Int)");
        xaxis.setTitleFont(new Font("TimesRoman",Font.PLAIN,20));
        xaxis.setLabelFont(new Font("Helvetica",Font.PLAIN,15));
        
        yaxis_left = graph.createAxis(Axis.LEFT);
        yaxis_left.attachDataSet(data1);
        yaxis_left.setTitleText("Time (S)");
        yaxis_left.setTitleFont(new Font("TimesRoman",Font.PLAIN,20));
        yaxis_left.setLabelFont(new Font("Helvetica",Font.PLAIN,15));
        yaxis_left.setTitleColor( new Color(0,0,255) );
        
        return graph;
    }
    
}
