package es.udc.redes.tutorial.udp.server;

import javax.swing.*;
import java.net.*;

/**
 * Implements a UDP echo server.
 */
public class UdpServer {
    public static void main(String argv[]) {
        if (argv.length != 1) {
            System.err.println("Format: es.udc.redes.tutorial.udp.server.UdpServer <port_number>");
            System.exit(-1);
        }
        DatagramSocket sDatagram = null;
        try {
            // Create a server socket
            String serverPort = argv[0];
            sDatagram = new DatagramSocket(Integer.parseInt(serverPort));
            // Set maximum timeout to 300 secs
            sDatagram.setSoTimeout(300000);
            while (true) {
                // Prepare datagram for reception
                byte array[] = new byte[1024];
                DatagramPacket receptedDatagram = new DatagramPacket(array, array.length);
                // Receive the message
                sDatagram.receive(receptedDatagram);
                System.out.println("SERVER: Received "
                        + new String(receptedDatagram.getData(), 0, receptedDatagram.getLength())
                        + " from " + receptedDatagram.getAddress().toString() + ":"
                        + receptedDatagram.getPort());
                // Prepare datagram to send response
                int senderPort = receptedDatagram.getPort();
                InetAddress senderIp = receptedDatagram.getAddress();
                String responseMessage = new String(receptedDatagram.getData(), 0, receptedDatagram.getLength());

                DatagramPacket responseDatagram = new DatagramPacket(responseMessage.getBytes(), responseMessage.length(), senderIp, senderPort);
                
                // Send response
                sDatagram.send(responseDatagram);
                System.out.println("SERVER: Sending "
                        + new String(responseDatagram.getData()) + " to "
                        + responseDatagram.getAddress().toString() + ":"
                        + responseDatagram.getPort());
            }
          
         //Uncomment next catch clause after implementing the logic
        } catch (SocketTimeoutException e) {
            System.err.println("No requests received in 300 secs ");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
        // Close the socket
            sDatagram.close();
        }
    }
}
