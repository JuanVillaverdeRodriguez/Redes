package es.udc.redes.tutorial.tcp.server;
import java.io.IOException;
import java.net.*;

/** Multithread TCP echo server. */

public class TcpServer {

  public static void main(String argv[]) {
    if (argv.length != 1) {
      System.err.println("Format: es.udc.redes.tutorial.tcp.server.TcpServer <port>");
      System.exit(-1);
    }
    ServerSocket serverSocket = null;
    try {
      // Create a server socket
      int port = Integer.parseInt(argv[0]);
      serverSocket = new ServerSocket(port);
      // Set a timeout of 300 secs
      serverSocket.setSoTimeout(300000);
      while (true) {
        // Wait for connections
        // Create a ServerThread object, with the new connection as parameter
        ServerThread newServerThread = new ServerThread(serverSocket.accept());
        // Initiate thread using the start() method
        newServerThread.start();
      }
    // Uncomment next catch clause after implementing the logic
     } catch (SocketTimeoutException e) {
      System.err.println("Nothing received in 300 secs");
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
     } finally{
	    //Close the socket
        try {
          serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  }
}
