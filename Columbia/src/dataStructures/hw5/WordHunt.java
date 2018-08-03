package dataStructures.hw5;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.EventQueue;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;

/**
 *
 * @author Mavis Beacon
 */
public class WordHunt {
    
    static int THREAD_SLEEP_TIME = 30;
    private static WordHuntAnimation animation;
    private static boolean useAnimation = true;
     
    public static void main(String args []){
        
        if( args.length < 2){ 
            System.out.println();
            System.out.println("Usage: WordHunt <word_length> <dictionary> <Optional: useAnimation>");
            System.out.println("------ useAnimation: Default is on. Use \"off\" to turn off  ---------");
           // return; 
        }
        final int wordLength = Integer.parseInt(args[0]);
        
        String dictionaryFile = null; //"dictionary.txt";
        dictionaryFile = args[1];
        
        if( args.length >= 3){
            System.out.println("Using animation: " + args[2]);
            if( args[2].equals("off")){
                useAnimation = false;
            }
           
        }
        
        Scanner scanner = null;
        try {
            File file = new File("word_hunt_matrix.txt");
            scanner = new Scanner(file);
            scanner.useDelimiter("\n");
        } catch (Exception ex) {
            System.out.println("Word Hunt Matrix Not Found : " + ex.toString());
            return;
        }
        
        /*
         * Read in Vertices
         */
        final ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        Vertex v = null;
        while( scanner.hasNext() ){
            String next = scanner.next();
            if( next.trim().length() == 1){
                String coor = scanner.next();
                String [] coorArray = coor.split(" ");
                v = new Vertex(next.charAt(0),coorArray[0].trim(),coorArray[1].trim(),coorArray[2].trim());
                vertices.add(v);
                continue;
            }
            String [] nextArray = next.split(" ");
            v.addNeighbor(nextArray[0].trim(), nextArray[1].trim(), nextArray[2].trim());
        }
        for( Vertex x : vertices ){
            for( Vertex y : vertices){
                if( x == y)
                    continue;
                if( x.isNeighbor(y) ){
                    x.replaceNeighbor(y);
                    y.replaceNeighbor(x);
                }
            }
        }
        //System.out.println(vertices); 
        
        /*
         * Read in dictionary
         */
        try {
            File file = new File(dictionaryFile);
            scanner = new Scanner(file);
            scanner.useDelimiter("\n");
        } catch (Exception ex) {
            System.out.println("Dictionary not found: " +ex.toString());
            return;
        }
        
        final MyHashTable hashTable = new MyHashTable(307691);
        double counter  = 0;
        while( scanner.hasNext() ){
            String next = scanner.next();
            hashTable.put(next.trim());
            counter++;
        }
        
        animation = new WordHuntAnimation(vertices);
        
        if( useAnimation ){
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            frame.setSize(800,600);
            frame.getContentPane().add(animation);
        }
        
        Thread t = new Thread(new Runnable(){
            @Override
            public void run() {
                /*
                 * Begin word hunt
                 */
                System.out.println();
                System.out.println("Words found: ");
                for(Vertex vertex : vertices){
                    findWordsInMatrix(vertex,hashTable,wordLength,"");
                }
                
                if( useAnimation ){
                    animation.done = true;
                    animation.current = null;
                    System.out.println("DONE");
                    animation.invalidate();
                    animation.repaint();
                }

            }
        });
        t.start();
        
        
    }

    private static void findWordsInMatrix(final Vertex vertex, MyHashTable hashTable, int wordLength,String word) {
        
        word += vertex.letter;
        wordLength--;
        
        if( useAnimation ){
            //Allow GUI to update
            try {

                // Put a call for the GUI to update in the event-dispatch thread
                EventQueue.invokeLater( new Runnable(){
                    @Override
                    public void run() {
                        animation.current = vertex;
                        animation.invalidate();
                        animation.repaint();
                    }
                });

                Thread.sleep(THREAD_SLEEP_TIME); 


            } catch (InterruptedException ex) {
                System.out.println("Thread interrupted: " + ex.toString());
            }
        }
        
        if( wordLength == 0){
            boolean contains = hashTable.isInTable(word.trim());
            if( contains ){
                System.out.println(" " + word);
                animation.wordsFound.add(word);
            }
            return;
        }
        
        for( Vertex v : vertex.neighbors){
            findWordsInMatrix(v,hashTable,wordLength,word);
        }
        
    }
    
}
