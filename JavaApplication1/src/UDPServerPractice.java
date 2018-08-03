
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan
 */
public class UDPServerPractice {
    
    public static final int SERVERPORT = 9999;
    
    public static void main(String args []){
        
        try { 
                            /* Retrieve the ServerName */
           InetAddress serverAddr = InetAddress.getLocalHost();
           System.out.println("ServerAddr: " + serverAddr);

           System.out.println("S: Connecting...");
           /* Create new UDP-Socket */
           DatagramSocket socket = new DatagramSocket(SERVERPORT);

           while(true){

              /* By magic we know, how much data will be waiting for us */
              byte[] buf = new byte[100];
              /* Prepare a UDP-Packet that can
               * contain the data we want to receive */
              DatagramPacket packet = new DatagramPacket(buf, buf.length);

              /* Receive the UDP-Packet */
              socket.receive(packet);
              String s = new String(packet.getData()).trim();
              System.out.println("'" + s + "'");
           }
        } catch (Exception e) {
               System.out.println("S: Error: "  + e);
        }
        
	        
    }
    
}
