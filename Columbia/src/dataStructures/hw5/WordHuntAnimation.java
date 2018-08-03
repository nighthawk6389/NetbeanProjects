package dataStructures.hw5;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mavis Beacon
 */
public class WordHuntAnimation extends JPanel{
    
    private ArrayList<Vertex> vertices;
    private BufferedImage img;
    Vertex current;
    ArrayList<String> wordsFound = new ArrayList<String>();
    boolean done = false;
    
    public WordHuntAnimation(){
        
    }

    WordHuntAnimation(ArrayList<Vertex> vertices) {
        this.vertices = vertices;
        try {
            img = ImageIO.read(new File("sphere_small.jpg"));
        } catch (IOException e) {
            System.out.println("Couldnt read img");
        }
        this.setBackground(Color.white);
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setBackground(Color.white);
        
        int PICSIZE = 50;
        int VWIDTH = 70;
        int XOFFSET = this.getWidth()/2 - 250;
        int YOFFSET = 50;
        
        int MULTX = 2 * VWIDTH;
        int MULTY = 2 * VWIDTH;
        int MULTZ = VWIDTH;
        
        double zFactorX = 1.2;
        double zFactorY = .8;
        
        g.setColor(Color.gray);
        for(Vertex vertex: vertices){
            int x = vertex.x;
            int y = vertex.y;
            int z = vertex.z;
            
            z = z * MULTZ;
            x = (int) (x * MULTX + XOFFSET - (zFactorX * z));
            y = (int) (y * MULTY + YOFFSET + (zFactorY * z));
            
            for(Vertex neighbor : vertex.neighbors){
                int nX = neighbor.x;
                int nY = neighbor.y;
                int nZ = neighbor.z;
                
                nZ = nZ * MULTZ;
                nX = (int) (nX * MULTX + XOFFSET - (zFactorX * nZ));
                nY = (int) (nY * MULTY + YOFFSET + (zFactorY * nZ)) ;
                
                g.setColor(Color.gray);
                g.drawLine(x + 25, y + 25, nX + 25, nY + 25);
            }
            
            if( img != null && vertex != current)
                g.drawImage(img, x, y, null);
            else{
                g.setColor(Color.black);
                g.fillOval( x , y , VWIDTH, VWIDTH);
            }
            
            g.setColor(Color.red);
            g.drawString("" + Character.toUpperCase(vertex.letter), x + PICSIZE/2, y + PICSIZE/2);
            
            
        }
        
        int SWIDTH = this.getWidth();
        int SHEIGHT = this.getHeight();
        int foundX = SWIDTH - 250;
        int foundY = 100;
        g.drawString("Words Found:", foundX,foundY);
        foundY += 5;
        for(String s : wordsFound){
            if(foundY >= SHEIGHT - 20){
                foundY = 105;
                foundX += 50;
            }
            foundY += 20;
            g.drawString(s, foundX, foundY);
        }
        
        if( done ){
            g.setColor(Color.black);
            g.drawString("WORD SEARCH FINISHED", SWIDTH-500, SHEIGHT - 50);
        }
        
    }
    
    
    
}
