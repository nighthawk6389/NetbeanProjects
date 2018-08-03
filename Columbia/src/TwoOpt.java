
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mavis Beacon
 */
public class TwoOpt extends JPanel implements ActionListener{
    
    private Point points [];
    static int MAX_RAND = 500;
    public static int THREAD_SLEEP = 300;
    private static final int REPLACE_ONE = 1001;
    private static final int REPLACE_TWO = 1002;
    private ArrayList<Edge> tour;
    private TwoOptAnimation toa;
    
    private JButton compute, newButton, setPoints;
    private JRadioButton slow, medium, fast;
    private Thread t1;
    private Runnable r;
    
    public static void main(String args []){
        
        int n = 15;
        TwoOpt ch = new TwoOpt(n);
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(800,600);
        frame.getContentPane().add(ch);
        
    }
    
    public TwoOpt(int n){
        this.setBackground(Color.white);
        
        this.points = new Point[n];
        
        points = this.getRandomPoints();
        
        tour = this.getRandomTour();
        
        /////////////
        //  GUI  ///
        ////////////
        this.setLayout(new BorderLayout());
        
        compute = new JButton("Do Two Opt Exchange");
        compute.addActionListener(this);
        newButton = new JButton("New");
        newButton.addActionListener(this);
        setPoints = new JButton("Set # Of Points");
        setPoints.addActionListener(this);
        
        slow = new JRadioButton();
        medium = new JRadioButton();
        medium.setSelected(true);
        fast = new JRadioButton();
        slow.addActionListener(this);
        medium.addActionListener(this);
        fast.addActionListener(this);
        
        ButtonGroup group = new ButtonGroup();
        group.add(slow);
        group.add(medium);
        group.add(fast); 
        
        JPanel south = new JPanel();
        south.add(compute);
        south.add(newButton);
        south.add(setPoints);
        south.add(new JLabel("Slow:"));
        south.add(slow);
        south.add(new JLabel("Medium:"));
        south.add(medium);
        south.add(new JLabel("Fast"));
        south.add(fast);
        
        toa = new TwoOptAnimation(points,tour);
        this.add(toa,BorderLayout.CENTER); 
        this.add(south,BorderLayout.SOUTH); 
        
        r = new Runnable(){

            @Override
            public void run() {
                newButton.setEnabled(false);
                Edge [] eArray;
                do {
                    //System.out.println("Running");
                    //Get TwoOpt if available
                    TwoOptHolder hold = TwoOpt.this.getTwoOptExchange();
                    if( hold == null)
                        break;
                    eArray = hold.edges;
                    if( eArray == null)
                        break; 
                    TwoOpt.this.toa.twoOptSelect = eArray;
                    EventQueue.invokeLater(new Runnable(){

                        @Override
                        public void run() {
                            TwoOpt.this.invalidate();
                            TwoOpt.this.repaint();;
                        }
                    });

                    try {
                        Thread.sleep(THREAD_SLEEP);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(TwoOpt.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    TwoOpt.this.toa.twoOptSelect = null;
                    //Replace with TwoOpt found
                    Edge [] eReplace = TwoOpt.this.setTwoOptReplacement(eArray, hold.type);
                    TwoOpt.this.toa.twoOptReplace = eReplace;
                    EventQueue.invokeLater(new Runnable(){

                        @Override
                        public void run() {
                            TwoOpt.this.invalidate();
                            TwoOpt.this.repaint();;
                        }
                    });


                    try {
                        Thread.sleep(THREAD_SLEEP);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(TwoOpt.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    TwoOpt.this.toa.twoOptReplace = null;
                    
                } while( eArray != null);
                
                //System.out.println("DONE : " + tour);
                newButton.setEnabled(true);
                
            }
        };
        
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) { 
        if( e.getSource() == compute ){
            t1 = new Thread(r);
            t1.start();
        }
        else if ( e.getSource() == newButton){
            this.remove(toa);
            points = this.getRandomPoints();   
            tour = this.getRandomTour();
            toa = new TwoOptAnimation(points,tour);
            this.add(toa, BorderLayout.CENTER);
            this.revalidate();
            this.repaint();
            
        }
        else if ( e.getSource() == setPoints ){
             String nString = JOptionPane.showInputDialog("Enter The Amount Of Points");
             int n = Integer.parseInt(nString);
             points = new Point[n];
             this.remove(toa);
             points = this.getRandomPoints();   
             tour = this.getRandomTour();
             toa = new TwoOptAnimation(points,tour);
             this.add(toa, BorderLayout.CENTER);
             this.revalidate();
             this.repaint();
        }
        else if( e.getSource() == slow ){
            TwoOpt.THREAD_SLEEP = 500;
        }
        else if( e.getSource() == medium){
            TwoOpt.THREAD_SLEEP = 350;
        }
        else if( e.getSource() == fast){
            TwoOpt.THREAD_SLEEP = 200;
        }
            
    }
    
    private Point [] getRandomPoints(){
        ArrayList<Point> uniquePoints = new ArrayList<Point>();
        Point [] pArray = new Point[points.length];
        
        Random rand = new Random();
        for(int x = 0; x < pArray.length; x++){
            Point p = new Point(rand.nextInt(MAX_RAND),rand.nextInt(MAX_RAND));
            if( uniquePoints.contains(p) ){
                x--;
                continue;
            }
            pArray[x] = p;
            uniquePoints.add(p);
        }
        
        return pArray;
        
    }
    
    private ArrayList<Edge> getRandomTour(){ 
        LinkedList<Point> pointList = new LinkedList<Point>();
        pointList.addAll(Arrays.asList(points));
        
        ArrayList<Edge> randomTour = new ArrayList<Edge>();
        Point start = pointList.removeFirst();
        Point one = start;
        while( pointList.size() > 0 ){
            int rand = (int)(Math.random() * pointList.size());
            Point two = pointList.remove(rand);
            randomTour.add( new Edge(one,two) );  
            one = two;
        }
        randomTour.add( new Edge(start, one) );
        
        //System.out.println(randomTour);
        return randomTour;
    }
    
    private TwoOptHolder getTwoOptExchange(){
        
        double minDistance = Double.MAX_VALUE;
        Edge [] minEdges = new Edge[2];
        int minType = -1;
        for( Edge e1 : tour){
            for( Edge e2 : tour){
                if( e1.equals(e2)){
                    //System.out.println("EQUALS :  " + e1 + " " + e2);
                    continue;
                }
                if( e1.p1.x == e1.p2.x || e2.p1.x == e2.p2.x){
                    //System.out.println("X EQUALS :  " + e1 + " " + e2);
                    continue;
                }
                     
                double slope1 = (double)(e1.p1.y - e1.p2.y) / ( e1.p1.x - e1.p2.x);
                double slope2 = (double)(e2.p1.y - e2.p2.y) / ( e2.p1.x - e2.p2.x);
                double b1 = e1.p1.y - slope1*(e1.p1.x);
                double b2 = e2.p1.y - slope2*(e2.p1.x);
                
                //System.out.println( slope1 + " " + slope2 + " " + b1 + " " + b2 );  
                
                if( slope1 - slope2 == 0)
                    continue;
                
                double xIntersect = (int) ( (b2 - b1) / (slope1 - slope2) ); 
                xIntersect = Math.round(xIntersect);
                
                if(  ((xIntersect > e1.p1.x && xIntersect < e1.p2.x) || (xIntersect < e1.p1.x && xIntersect > e1.p2.x)) &&
                     ((xIntersect > e2.p1.x && xIntersect < e2.p2.x) || (xIntersect < e2.p1.x && xIntersect > e2.p2.x)) && 
                        !e1.p1.equals(e2.p1) && !e1.p2.equals(e2.p1) && !e1.p1.equals(e2.p2) && !e1.p2.equals(e2.p2)  ){
                    
                    int replace_type = TwoOpt.REPLACE_ONE;
                    double distance = getTourDistanceWithReplacement(e1,e2,TwoOpt.REPLACE_ONE);
                    //System.out.println("XIntersect was: " + xIntersect + ". Points: " + e1 + " "  + e2);
                    //System.out.println("Distance from REPLACE_ONE was: " + distance);
                    if( distance == -1 ){
                        distance = getTourDistanceWithReplacement(e1,e2,TwoOpt.REPLACE_TWO);
                        replace_type = TwoOpt.REPLACE_TWO;
                        //System.out.println("Distance from REPLACE_TWO was: " + distance);
                    }
                    if( distance == -1){
                        //System.out.println(" -1 AFTER TWO REPLACEMENT!!!!");
                        continue; 
                    }
                    
                    if( distance < minDistance){
                        minDistance = distance;
                        minEdges[0] = e1;
                        minEdges[1] = e2;
                        minType = replace_type;
                    }
                }
            }
        }
        
        if( minEdges[0] != null && minEdges[1] != null){
            //System.out.println(" GET TWO OPT returning " + minEdges[0] +" " + minEdges[1] + " Dist: " + minDistance + " Type : " + minType);
            return new TwoOptHolder(minEdges,minDistance,minType);
        }
        
        //System.out.println("GET TWO OPT returing null");
        return null;
    }

    private Edge[] setTwoOptReplacement(Edge[] eArray, int type) { 
        Edge e1 = eArray[0], e2 = eArray[1];
        Edge e3 = null, e4 = null;
        if( type == TwoOpt.REPLACE_ONE){
            e3 = new Edge( e1.p1, e2.p1 );
            e4 = new Edge( e1.p2, e2.p2 );
        } else {
            e3 = new Edge( e1.p1, e2.p2 );
            e4 = new Edge( e1.p2, e2.p1 );
        }
        int index1 = tour.indexOf(eArray[0]);
        tour.set(index1, e3);
        int index2 = tour.indexOf(eArray[1]);
        tour.set(index2, e4);
        
        Edge [] eReplace = new Edge[2];
        eReplace[0] = e3;
        eReplace[1] = e4;
        
        return eReplace;
    }

    private double getTourDistanceWithReplacement(Edge e1, Edge e2, int type) {
        ArrayList<Edge> tempTour = new ArrayList<Edge>(tour);
        Edge e3 = null, e4 = null;
        if( type == TwoOpt.REPLACE_ONE){
            e3 = new Edge( e1.p1, e2.p1 );
            e4 = new Edge( e1.p2, e2.p2 );
        } else {
            e3 = new Edge( e1.p1, e2.p2 );
            e4 = new Edge( e1.p2, e2.p1 );
        }
        int index1 = tempTour.indexOf( e1 );
        tempTour.set(index1, e3);
        int index2 = tempTour.indexOf( e2 );
        tempTour.set(index2, e4);
        
        double distance = 0; 
        Edge e = tempTour.get(0);
        Point p1 = e.p1;
        Point toGet = e.p2;
        //System.out.println(" E0 was : " + e );
        while( tempTour.size() > 0 ){
            tempTour.remove(e);
            e = getEdgeThatContainsPoint( tempTour, toGet );
            distance += e.distance;
            if( e.p1.equals(p1) || e.p2.equals(p1)){
                tempTour.remove(e);
                distance += e.distance;
                break;  
            }
            if( e.wasP1 )
                toGet = e.p2;
            else
                toGet = e.p1;
        }
        
        //System.out.println("tempTour was : " + tempTour);
        if( tempTour.isEmpty() )
            return distance;
        return -1;
    }

    private Edge getEdgeThatContainsPoint( ArrayList<Edge> tempTour, Point toGet) {
        
        for( Edge e : tempTour){
            if ( e.p1.equals(toGet) ){
                e.wasP1 = true;
                return e;
            }
            if(  e.p2.equals(toGet) ){
                e.wasP1 = false;
                return e;
            }
        }
        return null;
    }
   

}

class TwoOptHolder {
    Edge [] edges;
    double distance;
    int type;

    public TwoOptHolder(Edge [] edges, double distance, int type) {
        this.edges = edges;
        this.distance = distance;
        this.type = type;
    }
    
    
}

class Edge {
    Point p1;
    Point p2;
    double distance;
    boolean wasP1 = false;
    
    public Edge(Point p1, Point p2){
        this.p1 = p1;
        this.p2 = p2;
        if( p1.equals(p2) ){
            distance = Double.MAX_VALUE;
            return;
        }
        distance = Math.hypot( p1.x - p2.x , p1.y - p2.y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Edge other = (Edge) obj;
        if (this.p1 != other.p1 && (this.p1 == null || !this.p1.equals(other.p1)) ) {
            return false;
        }
        if (this.p2 != other.p2 && (this.p2 == null || !this.p2.equals(other.p2)) ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.p1 != null ? this.p1.hashCode() : 0);
        hash = 67 * hash + (this.p2 != null ? this.p2.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Edge{" + "p1=" + p1 + ", p2=" + p2 + ", distance=" + distance + '}';
    }
    
    
}

class TwoOptAnimation extends JPanel{
    private int CIRCLE_WIDTH = 10;
    
    private Point points [];
    private ArrayList<Edge> tour;
    Edge [] twoOptSelect;
    Edge [] twoOptReplace;

    TwoOptAnimation(Point[] points, ArrayList<Edge> tour) {
        this.points = points;
        this.tour = tour;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
     
        double screenWidth = this.getWidth();
        double screenHeight = this.getHeight();
        
        double xScale = ( screenWidth  / (ConvexHull.MAX_RAND + 20) );
        double yScale = ( screenHeight / (ConvexHull.MAX_RAND + 100) );
        
        int xAdd = 0;
        int yAdd= 10; 
        
        g.setColor(Color.red);
        for(Point p : points){
            int x = (int) ( p.x * xScale + xAdd);
            int y = (int) ( p.y * yScale + yAdd);
            g.fillOval( x , y , CIRCLE_WIDTH, CIRCLE_WIDTH);
            g.drawString(p.x + " " + p.y, x, y);
        }
        
        g.setColor(Color.red);
        for(Edge e : tour){
            int x1 = (int) ( e.p1.x * xScale + xAdd);
            int y1 = (int) ( e.p1.y * yScale + yAdd);
            int x2 = (int) ( e.p2.x * xScale + xAdd);
            int y2 = (int) ( e.p2.y * yScale + yAdd);
            g.drawLine(x1, y1, x2, y2);
        }
        
        if( twoOptSelect != null){
            g.setColor(Color.YELLOW);
            for(Edge e : twoOptSelect){
                int x1 = (int) ( e.p1.x * xScale + xAdd);
                int y1 = (int) ( e.p1.y * yScale + yAdd);
                int x2 = (int) ( e.p2.x * xScale + xAdd);
                int y2 = (int) ( e.p2.y * yScale + yAdd);
                g.drawLine(x1, y1, x2, y2);
            }
        }
        
        if( twoOptReplace != null){
            g.setColor(Color.GREEN);
            for(Edge e : twoOptReplace){
                int x1 = (int) ( e.p1.x * xScale + xAdd);
                int y1 = (int) ( e.p1.y * yScale + yAdd);
                int x2 = (int) ( e.p2.x * xScale + xAdd);
                int y2 = (int) ( e.p2.y * yScale + yAdd);
                g.drawLine(x1, y1, x2, y2);
            }
        }
         
    }
    
}

