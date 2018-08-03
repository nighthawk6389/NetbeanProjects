/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;



/**
 *
 * @author Mavis Beacon
 */
public class Step4_Client_Main {
    
    static int localReadPort = 1479;
    static int localWritePort = 1480;
    static int broadcastServerPort; 
    static String serverAddr = null;
    static Socket proxySock;
    private static int socketTimeout = 500;
    
    private static final int MAX_REPEAT_REQUESTS = 10;
    
    public static void main(String args []){
                   
        try{
            if(args.length > 4 || args.length < 2){
                System.out.println("Usage: Main <IP> <Port> <optional: -a,-r>");
                return;
            }
            broadcastServerPort = Integer.parseInt(args[1]);
            serverAddr = args[0];
            
            String arg3=null,arg4 = null;
            String prefix3 = null, prefix4 = null;
            String names3 = null, names4 = null;
            String [] names3Array = null,names4Array = null; 
            if(args.length >= 3){
                arg3 = args[2];
                int index = arg3.indexOf(":");
                if( index == -1){
                    System.out.println("Incorrect args");
                    return;
                }
                prefix3 = arg3.substring(0,index);
                names3 = arg3.substring(index+1);
                names3Array = names3.split(",");
            }
            if(args.length == 4){
                arg4 = args[3];
                int index = arg4.indexOf(":");
                if( index == -1){
                    System.out.println("Incorrect args");
                    return;
                }
                prefix4 = arg4.substring(0,index);
                names4 = arg4.substring(index+1);
                names4Array = names4.split(",");
            }
            
            System.out.println("ProxySocket attempting to connect on port: " + broadcastServerPort);
            proxySock = new Socket(InetAddress.getByName(serverAddr),broadcastServerPort);
            
            
            BufferedReader inSock = null;
            PrintWriter outSock = null;
            if( prefix3!= null ){
                System.out.println("Opening streams for args...");
                inSock = new BufferedReader(new InputStreamReader(proxySock.getInputStream()));
                outSock = new PrintWriter(proxySock.getOutputStream(),true);
                System.out.println("Opened input/output streams");
            }
            
            proxySock.setSoTimeout(socketTimeout);
            if(prefix3 != null){
                System.out.println("Sending args: 3");
                sendArgumentsToProxy(prefix3,names3Array,inSock,outSock);
            }
            if(prefix4 != null){
                System.out.println("Sending args: 4");
                sendArgumentsToProxy(prefix4,names4Array,inSock,outSock);
            }
            proxySock.setSoTimeout(0);
            
        } catch(Exception ex){
            System.out.println("FATAL EXCEPTION: " + ex.toString());
            return;
        }

        /**Listening socket **/
       Thread t1 = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    System.out.println("LocalReadSocket attempting to connect on port: " + localReadPort);
                    Socket localRecv = new Socket(InetAddress.getLocalHost(),localReadPort);
                    System.out.println("LocalReadSocket connected!");
                    
                    PrintWriter broadcastOut = new PrintWriter(proxySock.getOutputStream(),true);
                    System.out.println("BroadCastWriteSocket connected!");

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
                        broadcastOut.println(outputTextWithInfo);
                        System.out.println("Sent to broadcastServer, outputText: " + outputTextWithInfo);
                        //buff.put(nextLine);
                    }
                    System.out.println("Listening Thread Done!");
                
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        });
       t1.start();

        /** Writing Thread **/
        Thread t2 = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    
                    System.out.println("LocalWriteSocket attempting to connect on port: " + localWritePort);
                    Socket writeLocalSock = new Socket(InetAddress.getLocalHost(),localWritePort);
                    PrintWriter localOut = new PrintWriter(writeLocalSock.getOutputStream(),true);
                    System.out.println("LocalWriteSocket connected!");


                    System.out.println("ProxyRead waiting for input...");
                    Scanner scanner = new Scanner(proxySock.getInputStream());
                    String nextLine;
                    while(scanner.hasNext()){
                        nextLine = scanner.nextLine(); 
                        System.out.println("Recieved from proxy server: " + nextLine);
                        String [] split = nextLine.split("[ :]");
                        if(split.length < 10){
                            System.out.println("WritingThread: Received Malformed Message. Length: " + split.length);
                            continue;
                        }
                        String textToGui = split[3] + " " +
                                split[5] + " " +
                                split[7] + " " + 
                                split[9];
                        localOut.println(textToGui);
                        System.out.println("Sent textToGui: " + textToGui);
                    }
                    System.out.println("Writing Thread Done!");
                } catch (Exception ex) {
                System.out.println(ex.toString());
                }
            }
        });
        t2.start();
                  
        
    }

    private static void sendArgumentsToProxy(String prefix, String[] namesArray,BufferedReader inSock,PrintWriter outSock ) {

        //Make sure proxy gets connected to broadcast server first
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Step4_Client_Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            String operation;
            if(prefix.equals("-a")){
                operation = "add";
            }
            else if(prefix.equals("-r")){
                operation = "remove";
            }
            else{
                System.out.println("Bad Operation. Prefix was: " + prefix);
                return;
            }
            
            
            /**
             * BEGIN PROTOCOL
             */
            String [] messages = new String [100];
            int totalMessagesToSend = 0;
            messages[totalMessagesToSend++] = "0:"+operation;
            for(int i = 0; i < namesArray.length;i++){
                messages[i+1] = ""+(i+1)+":"+namesArray[i];
                System.out.println("messages["+(i+1)+"] = " + messages[i+1]);
                totalMessagesToSend++;
            }
            messages[totalMessagesToSend] = totalMessagesToSend + ":FINISHED";
            totalMessagesToSend++;
            
            boolean resendHandshake = false;
            int resendAttempts = 0;
            do{
                System.out.println("Sending handshake..." + messages[0]);
                //Send handshake
                outSock.println(messages[0]);

                //Get return handshake
                String returnMessage = null;
                try{
                    returnMessage = inSock.readLine();
                } catch(SocketTimeoutException ste){
                    System.out.println("Handshake return timed out");
                }
                resendHandshake = false;
                if( returnMessage == null || !returnMessage.equalsIgnoreCase("READY")){
                    resendHandshake = true;
                    resendAttempts++;
                }  
                if(resendAttempts >= 10){
                    System.out.println("Max resent attempts. Failed to send update. Returning");
                    return;
                }
                
            } while( resendHandshake && resendAttempts < 5);
            
            System.out.println("Handshake good");
            
            int messageToSend = 1;
            int repeatRequestCounter = 0;
            while(messageToSend < totalMessagesToSend){
                
                //Sending messages to proxy
                System.out.println("Sending message: " + messages[messageToSend]);
                outSock.println(messages[messageToSend]);
                messageToSend++;
                
                //Check for REPEAT request
                String returnMessage = null;
                String request = null;
                int subjectMessageNum;
                try{
                    returnMessage = inSock.readLine();
                    System.out.println("Recieved from proxy: " + returnMessage);
                    if(returnMessage == null)
                        continue;
                    int index = returnMessage.indexOf(":");
                    if(index == -1){
                        System.out.println("Colon not found");
                        continue;
                    }
                    request = returnMessage.substring(0,index);
                    subjectMessageNum = Integer.parseInt(returnMessage.substring(index+1));
                    
                    // Repeat Logic
                    if(request.equalsIgnoreCase("REPEAT")){
                        messageToSend = subjectMessageNum;
                        repeatRequestCounter++;
                    }
                    if(repeatRequestCounter >= MAX_REPEAT_REQUESTS){
                        System.out.println("Too many repeat requests. Resetting...");
                        messageToSend = 1;
                        outSock.println("-1:RESET");
                        continue;
                    }
                    
                    if( !request.equalsIgnoreCase("END") && (messageToSend == totalMessagesToSend) ){
                        System.out.println("End Not Received. Resending...(Incorrect=()");
                        messageToSend--;
                    }
                    
                    
                } catch(SocketTimeoutException ste){
                    //System.out.println("No post-message message");
                    if(messageToSend == totalMessagesToSend){
                        System.out.println("End Not Recieved. Resending...(Timeout)");
                        messageToSend--;
                    }
                    repeatRequestCounter = 0;
                }                               
            }
                
        } catch (IOException ex) {
            Logger.getLogger(Step4_Client_Main.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
}
