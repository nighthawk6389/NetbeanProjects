/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networks.ie2147.step_1.src;

import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;


 
/**
 *
 * @author Mavis Beacon
 */ 
public class Main {
       
    static int listeningPort = 1479;
    static int writingPort = 1480;
    static LinkedBlockingQueue buff = new LinkedBlockingQueue(); 
    
    public static void main(String args []){
           
        /**Listening Thread **/     
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    System.out.println("ReadSocket attempting to connect on port: " + listeningPort);
                    Socket recv = new Socket(InetAddress.getLocalHost(),listeningPort);
                    System.out.println("ReadSocket connected!");

                    while(true){
                        System.out.println("ReadSocket waiting for input...");
                        Scanner scanner = new Scanner(recv.getInputStream());
                        String nextLine;
                        while(scanner.hasNext()){
                            nextLine = scanner.nextLine();
                            System.out.println("Next input to ReadSocket: " + nextLine);
                            buff.put(nextLine);
                        } 
                        System.out.println();
                    }
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        }).start();

        /** Writing Thread **/
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                                        
                    System.out.println("WritingSocket attempting to connect on port: " + writingPort);
                    Socket writeSock = new Socket(InetAddress.getLocalHost(),writingPort);
                    PrintWriter out = new PrintWriter(writeSock.getOutputStream(),true);
                    System.out.println("WritingSocket connected!");

                    String nextLine;
                    while(true){
                        nextLine = (String) buff.take();
                        out.println(nextLine);
                        System.out.println("Sent nextLine: " + nextLine);
                    }
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        }).start();
                  
        
    }
}
