package dataStructures.hw5;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Mavis Beacon
 */
public class DisplayMapOfCities extends JPanel{
    
    ArrayList<City> cities;
    ArrayList<Edge> edges;
    
    public DisplayMapOfCities(ArrayList<City> cities, ArrayList<Edge> edges){
        this.cities = cities;
        this.edges = edges;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        double screenWidth = this.getWidth();
        double screenHeight = this.getHeight();
        
        double xScale = ( screenWidth / 2700 );
        double yScale = (screenHeight / 1500);
        
        int xAdd = 15;
        int yAdd= 20;
        
         
        g.setColor(Color.black);
        for(City city : cities){
            int x = (int) ( city.getX() * xScale + xAdd);
            int y = (int) ( Math.abs( city.getY() - 1400 ) * yScale +yAdd);
            g.fillOval( x , y , 10, 10);
            g.drawString(city.getName(), x, y);
        }
        
        g.setColor(Color.red);
        for(Edge e: edges){
            int x1 = (int) ( e.getCity1().getX() * xScale + xAdd);
            int x2 = (int) ( e.getCity2().getX() * xScale + xAdd);
            int y1 = (int) ( Math.abs(e.getCity1().getY() - 1400) * yScale + yAdd);
            int y2 = (int) ( Math.abs(e.getCity2().getY() - 1400) * yScale + yAdd);
            
            g.drawLine(x1, y1, x2, y2);
        }
        
    }
    
    
    
}
