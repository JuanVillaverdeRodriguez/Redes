package es.udc.redes.tutorial.tcp.server;

import java.net.*;
import java.io.*;

/**
 * MonoThread TCP echo server.
 */
public class MonoThreadTcpServer {

    public static void main(String argv[]) throws IOException {
        if (argv.length != 1) {
            System.err.println("Format: es.udc.redes.tutorial.tcp.server.MonoThreadTcpServer <port>");
            System.exit(-1);
        }
        ServerSocket serverSocket = null;
        Socket connectionSocket = null;
        try {
            // Create a server socket
            int port = Integer.parseInt(argv[0]);
            serverSocket = new ServerSocket(port);
            // Set a timeout of 300 secs
            serverSocket.setSoTimeout(300000);
            while (true) {
                // Wait for connections
                connectionSocket = serverSocket.accept();
                // Set the input channel
                BufferedReader sInput = new BufferedReader(new InputStreamReader(
                        connectionSocket.getInputStream()));
                // Set the output channel
                PrintWriter sOutput = new PrintWriter(connectionSocket.getOutputStream(), true);
                // Receive the client message
                String received = sInput.readLine();
                System.out.println("SERVER: Received " + received
                        + " from " + connectionSocket.getInetAddress().toString()
                        + ":" + connectionSocket.getPort());
                // Send response to the client
                sOutput.println(received);
                System.out.println("SERVER: Sending " + received +
                        " to " + connectionSocket.getInetAddress().toString() +
                        ":" + connectionSocket.getPort());
                // Close the streams
                sOutput.close();
                sInput.close();
            }
        // Uncomment next catch clause after implementing the logic            
        } catch (SocketTimeoutException e) {
            System.err.println("Nothing received in 300 secs ");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
	        //Close the socket
            try {
                serverSocket.close();
                connectionSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
