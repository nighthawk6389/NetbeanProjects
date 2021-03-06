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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;

/**
 *
 * @author Mavis Beacon
 */
public class HW1_Applet3 extends Applet {
    
    private static double STOP_TIME = 30.00;
    
    private Graph2D graph;
    private DataSet data1;
    private Axis    xaxis;
    private Axis    yaxis_left;    
    
    @Override
    public void init(){
        
        int max = 100;
       
        Random generator= new Random();
        TimeInterval time = new TimeInterval();
        double timeStore [] = new double[40];
        int timeStoreCount = 0;
        
        int numArray [];
        int randInt;
        int temp;
        while( (time.getElapsedTime() < STOP_TIME) && (max < 500000) ){
            numArray = new int[max]; 
            time.startTiming();
            
            for(int i = 0; i < max; i++){
                numArray[i] = i + 1;
            }
            
            for(int i = 0; i < max; i++){
                randInt = generator.nextInt(max);
                temp = numArray[i];
                numArray[i] = numArray[randInt];
                numArray[randInt] = temp;
            }
            
            time.endTiming();
            timeStore[timeStoreCount++] = max;
            timeStore[timeStoreCount++] = time.getElapsedTime();
            System.out.println(time.getElapsedTime() + " " + max);
            
            max = max * 2;
        }
        
        graph = new Graph2D();
        graph.drawzero = false;
        graph.drawgrid = false;
        graph.borderTop = 50;
        graph.borderRight=100;
        setLayout( new BorderLayout() );
        add("Center", graph);
        
        data1 = graph.loadDataSet(timeStore,timeStoreCount/2);
        data1.linestyle = 1;
        data1.linecolor   =  new Color(0,255,0);
        data1.marker    = 1;
        data1.markerscale = 1;
        data1.markercolor = new Color(0,0,255);
        data1.legend(200,100,"Y=Algorithm#2");
        data1.legendColor(Color.black);
        
        xaxis = graph.createAxis(Axis.BOTTOM);
        xaxis.attachDataSet(data1);
        xaxis.setTitleText("N (Integers)");
        xaxis.setTitleFont(new Font("TimesRoman",Font.PLAIN,20));
        xaxis.setLabelFont(new Font("Helvetica",Font.PLAIN,15));
        
        yaxis_left = graph.createAxis(Axis.LEFT);
        yaxis_left.attachDataSet(data1);
        yaxis_left.setTitleText("Time (Seconds)");
        yaxis_left.setTitleFont(new Font("TimesRoman",Font.PLAIN,20));
        yaxis_left.setLabelFont(new Font("Helvetica",Font.PLAIN,15));
        yaxis_left.setTitleColor( new Color(0,0,255) );
    }
    
}
