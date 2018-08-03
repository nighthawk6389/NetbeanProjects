/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networks.ie2147.step_4.proxy.src;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


/**
 *
 * @author Mavis Beacon
 */
public class Main {
    
    private static int localPort;
    private static int broadcastServerPort = 1452;
    private static String serverAddr = "128.59.9.102";
    
    private static Socket broadcastServerSock = null;
    private static ServerSocket serverSock;
    private final Socket clientSock;
    private boolean isConnected = true;
    
    private PrintWriter clientOut;
    private static PrintWriter broadcastOut;
    
    private Thread t1;
    private Thread t2;
    private Thread t3;
    private Thread t4;
    private Thread t5;
    
    private  LinkedBlockingQueue<String> fromGUIToBroadcastBuff = new LinkedBlockingQueue<String>();
    private  LinkedBlockingQueue<String> fromBroadcastToGUIBuff = new LinkedBlockingQueue<String>();
    private  LinkedBlockingQueue<String> ACKBuff = new LinkedBlockingQueue<String>();
    private  LinkedBlockingQueue<String> recvBuff = new LinkedBlockingQueue<String>();

    
    static Set<String> blocked = new HashSet<String>();
    
    private static int ACKWaitTime = 500;
    
    public static void main(String args []){
           
        
        try{
            if(args.length != 1){
                System.out.println("Usage: Main <Port>");
                return;
            }
            localPort = Integer.parseInt(args[0]); 
            
            System.out.println("ServerSock attempting to connect on port: " + localPort);
            serverSock = new ServerSocket(localPort);
            System.out.println("ServerSock connected. Listening for connections");
            
            System.out.println("BroadcastSock attempting to connect to "+ serverAddr +" on port: " + broadcastServerPort);
            try{
                broadcastServerSock = new Socket(InetAddress.getByName(serverAddr),broadcastServerPort);
            } catch(Exception ex){
                System.out.println("Broadcast server cant connect");
            }
            
            if(broadcastServerSock == null){
                System.out.println("\nBroadcast Server cannot be reached. Proxy is shutting down!!!\n");
                return;
            }
            
                        
            broadcastOut = new PrintWriter(broadcastServerSock.getOutputStream(),true);
            System.out.println("BroadcastOutSocket connected!");
            
                   
            Main m = null;
            while(true){
                    Socket clientSock = serverSock.accept();
                    if(m != null)
                        m.stopThreads();
                    m = new Main(clientSock);
                    m.startThreads();
                    System.out.println("Client connected!!");
                }

          
            
        } catch(Exception ex){
            System.out.println("FATAL EXCEPTION: " + ex.toString());
            return;
        }
    }
        
    private Main(Socket cs){
        this.clientSock = cs;
        isConnected = true;
    } 
    
    private void startThreads(){
        try {
            
            
            clientOut = new PrintWriter(clientSock.getOutputStream(),true);
            System.out.println("ClientOutSocket connected!");

            /**Client->Broadcast Thread **/
            t1 = new Thread(new Runnable(){
                @Override
                public void run() {
                    try {

                        System.out.println("ClientSock  waiting for input...");
                        Scanner scanner = new Scanner(clientSock.getInputStream());
                        String nextLine;
                        while(scanner.hasNext() && isConnected){
                            nextLine = scanner.nextLine();
                            System.out.println("Next input to ClientSock : " + nextLine);
                            if( nextLine.contains("CONFIG:") ){
                                System.out.println("CONFIG found");
                                updateBlockedList(nextLine);
                                
                                int equalsIndex = nextLine.indexOf("=");
                                int numToBeAck = Integer.parseInt(nextLine.substring(0, equalsIndex));
                                //ACK message
                                fromBroadcastToGUIBuff.add("ACK="+numToBeAck);
                                continue;
                            }
                            if(nextLine.contains("ACK")){
                                System.out.println("ACK found");
                                recvBuff.add(nextLine);
                                continue;
                            }
                            
                            
                            int equalsIndex = nextLine.indexOf("=");
                            int numToBeAck = Integer.parseInt(nextLine.substring(0, equalsIndex));
                            String messageToBeSent = nextLine.substring(equalsIndex+1);
                            fromGUIToBroadcastBuff.add(messageToBeSent);
                            
                            //ACK message
                            fromBroadcastToGUIBuff.add("ACK="+numToBeAck);
                        }
                        
                        System.out.println("Client->BroadcastThread Done!");

                    } catch (Exception ex) {
                        System.out.println("Thread1: "+ex.toString());
                    }
                }
            });
            t1.start();
            
            t2 = new Thread(new Runnable(){
                @Override
                public void run() {
                    while(isConnected){
                        try {
                            String nextLine = fromGUIToBroadcastBuff.take();
                            broadcastOut.println(nextLine);
                            System.out.println("\nTo Broadcast Server: outputText: " + nextLine);
                        } catch (Exception ex) {
                            System.out.println("Thread2: "+ex.toString());
                        }
                    }
                }
            });
            t2.start();

            /** Broadcast->Client Thread **/
            t3 = new Thread(new Runnable(){
                @Override
                public void run() {
                    try {

                        System.out.println("BroadcastSock waiting for input...");
                        Scanner scanner = new Scanner(broadcastServerSock.getInputStream());
                        String nextLine;
                        while(scanner.hasNext() && isConnected){
                            nextLine = scanner.nextLine(); 
                            System.out.println("\nRecieved from BroadcastServer: " + nextLine);
                            
                            //Check for blocked UNI
                            String [] nextLineArray = nextLine.split("[: ]");
                            if(nextLineArray.length < 2){
                                System.out.println("No UNI, Skipping...");
                                continue;
                            }
                            String uni = nextLineArray[1];
                            System.out.println("Checking UNI: " + uni);
                            boolean isBlocked = blocked.contains(uni);
                            System.out.println("isBlocked: " + isBlocked);
                            if(isBlocked){
                                System.out.println("UNI: " + uni + " is blocked. Skipping...");
                                continue;
                            }
                            
                            fromBroadcastToGUIBuff.add(nextLine);
                        }
                        System.out.println("Broadcast->Client Thread Done");

                    } catch (Exception ex) {
                        System.out.println("Thread2:" +ex.toString());
                    }
                } 
            });
            t3.start();
            
        } catch (Exception ex) {
           System.out.println("In constructor: " + ex.toString());
        }
        
        
        t4 = new Thread(new Runnable(){
                @Override
                public void run() {
                    int messageIDCounter = 0;
                    while(isConnected){
                        try {
                            String nextLine = fromBroadcastToGUIBuff.take();
                            
                            String messageWithID = nextLine;
                            if( !nextLine.contains("ACK") ){
                                messageWithID = messageIDCounter+"="+nextLine;
                                //Check if proxy received
                                ACKBuff.add(messageWithID);
                            }
                            
                            clientOut.println(messageWithID);
                            System.out.println("\nTo GUI:  message: " + messageWithID);
                            messageIDCounter++;
                            
                        } catch (Exception ex) {
                            System.out.println("Thread4: "+ex.toString());
                        }
                    }
                }
            });
            t4.start();
            
           t5 = new Thread(new Runnable(){
           @Override
            public void run() {
               ArrayList<Integer> needAck = new ArrayList<Integer>();
               while(isConnected){
                   try {
                        String nextLine = (String)ACKBuff.take();
                        System.out.println("ACKThread: Received from buffer (message -> " + nextLine + "), checking...");
                        int equalsIndex = nextLine.indexOf("=");
                        int ackNumber = Integer.parseInt(nextLine.substring(0,equalsIndex));
                        String message = nextLine.substring(equalsIndex+1);
                        
                        needAck.add(ackNumber);
                        
                        String recv = null;
                        recv = (String)recvBuff.poll(ACKWaitTime, TimeUnit.MILLISECONDS);
                        if(recv != null){
                            int ackEqualsIndex = recv.indexOf("=");
                            if( ackEqualsIndex == -1){
                                System.out.println("Bad ACK...Resending....");
                                fromBroadcastToGUIBuff.add(message);
                                continue;
                            }
                            String ack = recv.substring(0,ackEqualsIndex);
                            int ackRespNum = Integer.parseInt(recv.substring(ackEqualsIndex+1));

                            if(needAck.contains(ackRespNum) && ack.equals("ACK")){
                                System.out.println("ACK recieved for message " + ackNumber + ". Breaking");
                                needAck.remove(new Integer(ackRespNum));
                                continue;
                            }
                        }
                        System.out.println("ACK NOT recieved for message number " + ackNumber +". Resending...");
                        fromBroadcastToGUIBuff.add(message);
                        
                        
                        
                    
                   } catch( InterruptedException iex){
                       System.out.println("Interuppted. BREAKING!");
                       return;
                   }
                   catch(Exception ex){
                        System.out.println("ACKThreadException: " + ex.toString());
                   }
               }
           }
       });
           t5.setDaemon(true);
       t5.start();
    }
    
    private void stopThreads() throws IOException{
        //broadcastServerSock.close();
        clientSock.close();
        isConnected = false;
        if(t1 != null && t1.isAlive()){
            t1.interrupt();
        }
        if(t2 != null && t2.isAlive()){
            t2.interrupt();
        }
        if(t3 != null && t3.isAlive()){
            t3.interrupt();
        }
        if(t4 != null && t4.isAlive()){
            t4.interrupt();
        }
        if(t5 != null && t5.isAlive()){
            t5.interrupt();
        }
    }
    
    private static void updateBlockedList(String nextLine){
        
        System.out.println("IN UPDATE....");
        int config = nextLine.indexOf("CONFIG:")+7;
        String args = nextLine.substring(config);
        System.out.println("ARGS: " + args);
        
        String [] operation = args.split("[ ]");
        System.out.println("OPERATION LENGTH WAS "+operation.length);
        
        for(int i = 0 ; i < operation.length ; i++){
            String first = operation[i];
            int colon = first.indexOf(":");
            if(colon == -1){
                System.out.println("INVALID CONFIG: colon not found");
                return;
            }
            String firstOp = first.substring(0, colon);
            String restOfNames = first.substring(colon+1);
            String [] names = restOfNames.split(",");

            List<String> hold = new LinkedList<String>();
            hold.addAll(Arrays.asList(names));
            System.out.println("Hold was: " + hold.toString());

            if( firstOp.contains("-a"))
                blocked.addAll(hold);
            else if (firstOp.contains("-r"))
                blocked.removeAll(hold);
            else
                System.out.println("OPERATION NOT FOUND");
        }
        System.out.println("Blocked is now: " + blocked.toString());
        
       
        
    }

}
