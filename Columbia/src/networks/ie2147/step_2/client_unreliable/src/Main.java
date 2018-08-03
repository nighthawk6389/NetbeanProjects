/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networks.ie2147.step_2.client_unreliable.src;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;



/**
 *
 * @author Mavis Beacon
 */
public class Main {
    
    private static LinkedBlockingQueue sentBuff = new LinkedBlockingQueue();
    private static LinkedBlockingQueue recvBuff = new LinkedBlockingQueue();
    private static final int localReadPort = 1479;
    private static final int localWritePort = 1480;
    private static final int broadcastServerPort = 1453;
    private static final String serverAddr = "128.59.9.102";
    private static final int waitTime = 500;
    private static final int maxResendAttempts = 10;
        
    public static void main(String args []) throws IOException{
        
        System.out.println("BroadcastSocket attempting to connect on port: " + broadcastServerPort);
        final Socket broadcastSocket = new Socket(InetAddress.getByName(serverAddr),broadcastServerPort);
        if( broadcastSocket == null){
            System.out.println("Failed to connect to broadcast server..Failing");
            return;
        }
        System.out.println("BroadCastSocket connected!");
           
        /**Listening to GUI socket/ Writing to Broadcast Server **/
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    System.out.println("LocalReadSocket attempting to connect on port: " + localReadPort);
                    Socket localRecv = new Socket(InetAddress.getLocalHost(),localReadPort);
                    System.out.println("LocalReadSocket connected!");
                    
                    System.out.println("BroadcastSocket attempting to open outputStream: " + broadcastServerPort);
                    PrintWriter broadcastOut = new PrintWriter(broadcastSocket.getOutputStream(),true);
                    System.out.println("BroadCastSocket outputStream opened!");

                    System.out.println("LocalReadSocket waiting for input...");
                    Scanner scanner = new Scanner(localRecv.getInputStream());
                    String nextLine;
                    while(scanner.hasNext()){
                        nextLine = scanner.nextLine();
                        System.out.println("Next input to LocalReadSocket: " + nextLine);
                        String [] lineSplit = nextLine.split(" ");
                        String outputTextWithInfo = "UNI:ie2147" +
                            " Shape:" + lineSplit[0] + 
                            " X:"     + lineSplit[1] +
                            " Y:"     + lineSplit[2] +
                            " Color:"+ lineSplit[3]  +
                            " AdditionalInfo:" + System.currentTimeMillis();
                        broadcastOut.println(outputTextWithInfo);
                        //For DroppedThread check
                        sentBuff.put(outputTextWithInfo);
                        System.out.println("Sent to broadcastServer, outputText: " + outputTextWithInfo);
                    }
                    System.out.println("\nLocal InputStream Closed!!! Restart program");
                
                } catch (Exception ex) {
                    System.out.println("Thread1: " + ex.toString());
                }
            }
        }).start();

        /** Writing to GUI Thread / Listening to Broadcast Server **/
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    
                    System.out.println("WritingToLocalSocket attempting to connect on port: " + localWritePort);
                    Socket writeServerSock = new Socket(InetAddress.getLocalHost(),localWritePort);
                    PrintWriter localOut = new PrintWriter(writeServerSock.getOutputStream(),true);
                    System.out.println("WritingToLocalSocket connected!");

                    System.out.println("BroadcastRead waiting for input...");
                    Scanner scanner = new Scanner(broadcastSocket.getInputStream());
                    String nextLine = " ";
                    String lastRecv = " ";
                    while(scanner.hasNext()){
                        nextLine = scanner.nextLine();
                        if(lastRecv.equals(nextLine)){
                            System.out.println("Received same message of: " + lastRecv + ". Skipping");
                        }
                        System.out.println("Recieved from broadcast server: " + nextLine);
                        //For DroppedThread check
                        recvBuff.add(nextLine);
                        String [] split = nextLine.split("[ :]");
                        if( split.length < 10){
                            System.out.println("Received malformed message. Skipping!!!!!");
                            continue;
                        }
                        String textToGui = split[3] + " " +
                                split[5] + " " +
                                split[7] + " " + 
                                split[9];
                        localOut.println(textToGui);
                        System.out.println("Sent textToGui: " + textToGui);
                        lastRecv = nextLine;
                    }
                    
                    System.out.println("Broadcast InputStream Closed!!! Restart program");
                    
                } catch (Exception ex) {
                    System.out.println("Thread2: " + ex.toString());
                }
            }
        }).start();
        
        /**Dropped Thread **/     
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    System.out.println("Droppedthread attempting to open outputStream");
                    PrintWriter broadcastOut = new PrintWriter(broadcastSocket.getOutputStream(),true);
                    System.out.println("DroppedThread connected!");

                    boolean received = false;
                    int resendAttempts = 0;
                    while(true){
                        received = false;
                        String nextLine = (String)sentBuff.take();
                        System.out.println("DroppedThread: Received from buffer, checking...");
                        String recv = null;
                        long checkUntilTime = System.currentTimeMillis() + waitTime;
                        while( System.currentTimeMillis() < checkUntilTime ){
                            recv = (String)recvBuff.poll(waitTime, TimeUnit.MILLISECONDS);
                            if(recv != null && recv.equals(nextLine)){
                                System.out.println("DroppedThread: Received message, breaking");
                                received = true;
                                resendAttempts = 0;
                                break;
                            }
                        }
                        
                        if(!received && (resendAttempts < maxResendAttempts) ){
                            System.out.println("DroppedThread: Did NOT recieve message, resending...");
                            broadcastOut.println(nextLine);
                            sentBuff.put(nextLine);
                            resendAttempts++;
                        } else if(resendAttempts >= maxResendAttempts){
                            System.out.println("Max resend attempts hit: "  + maxResendAttempts);
                            resendAttempts = 0;
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("DroppedThread: " + ex.toString());
                }
            }
        }).start();
        
    }
}
