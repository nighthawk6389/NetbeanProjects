/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networks.ie2147.step_4.client.src;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;



/**
 *
 * @author Mavis Beacon
 */
public class Main {
    
    static int localReadPort = 1479;
    static int localWritePort = 1480;
    static int broadcastServerPort; 
    static String serverAddr = null;
    static Socket proxySock;
    
    private static int ACKWaitTime = 500;
    
    private static LinkedBlockingQueue<String> guiToProxyBuff = new LinkedBlockingQueue<String>();
    private static LinkedBlockingQueue<String> proxyToGUIBuff = new LinkedBlockingQueue<String>();
    private static LinkedBlockingQueue<String> ACKBuff = new LinkedBlockingQueue<String>();
    private static LinkedBlockingQueue<String> recvBuff = new LinkedBlockingQueue<String>();

    
    public static void main(String args []){
       
        
       try{
            if(args.length > 4 || args.length < 2){
                System.out.println("Usage: Main <IP> <Port> <optional: -a,-r>");
                return;
            }
            broadcastServerPort = Integer.parseInt(args[1]);
            serverAddr = args[0];
                        
            String argsString = "";
            if(args.length >= 3){
                argsString += args[2];
                
            }
            if(args.length == 4){
                argsString += (" "+ args[3]);
            }
            
            System.out.println("ProxySocket attempting to connect on port: " + broadcastServerPort);
            proxySock = new Socket(InetAddress.getByName(serverAddr),broadcastServerPort);
            proxySock.setSoTimeout(0);
            
            if(args.length >= 3 && !argsString.equals("")){
                System.out.println("Sending args: " + argsString);
                guiToProxyBuff.add("CONFIG:"+argsString);
            }
            
        } catch(Exception ex){
            System.out.println("FATAL EXCEPTION: " + ex.toString());
            return;
        }
        
        
        /**Listening(GUI) socket **/
       Thread t1 = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    System.out.println("LocalReadSocket attempting to connect on port: " + localReadPort);
                    Socket localRecv = new Socket(InetAddress.getLocalHost(),localReadPort);
                    System.out.println("LocalReadSocket connected!");

                    System.out.println("LocalReadSocket waiting for input...");
                    Scanner scanner = new Scanner(localRecv.getInputStream());
                    String nextLine;
                    while(scanner.hasNext()){
                        nextLine = scanner.nextLine();
                        System.out.println("Next input to LocalReadSocket: " + nextLine);
                        String [] lineSplit = nextLine.split(" ");
                        if( lineSplit.length < 4){
                            System.out.println("Received malformed message. Skipping!!!!!");
                            continue;
                        }
                        String outputTextWithInfo = "UNI:ie2147" +
                            " Shape:" + lineSplit[0] + 
                            " X:"     + lineSplit[1] +
                            " Y:"     + lineSplit[2] +
                            " Color:"+ lineSplit[3]  +
                            " AdditionalInfo:" + System.currentTimeMillis();
                        
                        guiToProxyBuff.add(outputTextWithInfo);
                    }
                    System.out.println("Listening Thread Done!");
                
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        });
       
       Thread t2 = new Thread(new Runnable(){
           @Override
            public void run() {
               PrintWriter proxyOut = null;
               int messageIDCounter = 0;
                try {
                    proxyOut = new PrintWriter(proxySock.getOutputStream(),true);
                } catch (IOException ex) {
                    System.out.println("Proxy server failed to connect. Please restart program!!!");
                    return;
                }
               System.out.println("ProxyOutput connected!");
               
               while(true){ 
                   try {
                       String nextLine =  guiToProxyBuff.take();
                       
                       String messageWithID = nextLine;
                       if( !nextLine.contains("ACK") ){
                           messageWithID = messageIDCounter+"="+nextLine;
                           //Check if proxy received
                           ACKBuff.add(messageWithID);
                       }

                       proxyOut.println(messageWithID);
                       System.out.println("\nTo Proxy:  message: " + messageWithID);
                       messageIDCounter++;
                   
                   }catch(Exception e){
                        System.out.println("Thread2: "+ e.toString());
                    }
               }
       }});

        /** Writing Thread **/
        Thread t3 = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    
                    System.out.println("ProxyRead waiting for input...");
                    Scanner scanner = new Scanner(proxySock.getInputStream());
                    String nextLine;
                    while(scanner.hasNext()){
                        nextLine = scanner.nextLine(); 
                        System.out.println("\nRecieved from proxy server: " + nextLine);
                        if(nextLine.contains("ACK")){
                            System.out.println("ACK Found");
                            recvBuff.add(nextLine);
                            continue;
                        }
                        
                        int equalsIndex = nextLine.indexOf("=");
                        int numToBeAck = Integer.parseInt(nextLine.substring(0, equalsIndex));
                        String messageToBeSent = nextLine.substring(equalsIndex+1);

                        //ACK message
                        guiToProxyBuff.add("ACK="+numToBeAck);
                        
                        String [] split = messageToBeSent.split("[ :]");
                        if(split.length < 10){
                            System.out.println("WritingThread: Received Malformed Message. Length: " + split.length);
                            continue;
                        }
                        String textToGui = split[3] + " " +
                                split[5] + " " +
                                split[7] + " " + 
                                split[9];
                        proxyToGUIBuff.add(textToGui);
                        
                    }
                    System.out.println("Writing Thread Done!");
                } catch (Exception ex) {
                System.out.println(ex.toString());
                }
            }
        });     
        
        
        Thread t4 = new Thread(new Runnable(){
           @Override
            public void run() {
               Socket writeLocalSock = null;
               PrintWriter localOut = null;
                try {
                    System.out.println("LocalWriteSocket attempting to connect on port: " + localWritePort);
                    writeLocalSock = new Socket(InetAddress.getLocalHost(),localWritePort);
                    localOut = new PrintWriter(writeLocalSock.getOutputStream(),true);
                    System.out.println("LocalWriteSocket connected!");
                } catch (IOException ex) {
                    System.out.println("Local write failed to connect. Please restart program!!!");
                    return;
                }
               System.out.println("LocalWriteSocket connected!");
               
               while(true){ 
                   try {
                       String message =  proxyToGUIBuff.take();
                       localOut.println(message);
                       System.out.println("Sent to GUI, message: " + message);
                   
                   }catch(Exception e){
                        System.out.println("Thread4: "+ e.toString());
                    }
               }
       }});
       
       Thread t5 = new Thread(new Runnable(){
           @Override
            public void run() {
               ArrayList<Integer> needAck = new ArrayList<Integer>();
               while(true){
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
                                guiToProxyBuff.add(message);
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
                        guiToProxyBuff.add(message);
                        
                        
                        
                    
                   } catch(Exception ex){
                        System.out.println("ACKThreadException: " + ex.toString());
                   }
               }
           }
       });
        
       
       t1.start();
       t2.start();
       t3.start();
       t4.start();
       t5.start();
       
      
       
       
    }
}
