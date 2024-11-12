package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;


public class MyServer {

    private final ClientHandler ch;
    private final int port;
    private volatile boolean running;

    public MyServer(int port, ClientHandler ch) {
        this.ch = ch;
        this.port = port;
        this.running = false;
    }

    public int getPort() {
        return this.port;
    }

    public ClientHandler getClient() {
        return this.ch;
    }

    public void close(){
        running = false;
    }

    public void start(){
        new Thread (()->runServer()).start();    
    }

    private void runServer() {
        running = true;
        try (ServerSocket theServer = new ServerSocket(port)) {
            theServer.setSoTimeout(1000); // 1 second timeout for accepting clients
           // System.out.println("Server listening on port: " + port);

            while (running) {
                try (Socket aClient = theServer.accept();
                     InputStream inFromClient = aClient.getInputStream();
                     OutputStream outToClient = aClient.getOutputStream()) {
            
                    //System.out.println("Client connected: " + aClient.getInetAddress());
            
                    // Handle the client's request and response
                    ch.handleClient(inFromClient, outToClient);
                    aClient.close();
                   // System.out.println("Client connection closed.");
            
                } catch (SocketTimeoutException e) {
                    // Timeout reached, but server keeps listening
                   // System.out.println("Waiting for client connection...");
                } catch (IOException e) {
                  //  System.err.println("Error handling client: " + e.getMessage());
                }
            }
            
        }
        catch (IOException e) {
            //System.err.println("Error starting server: " + e.getMessage());
        }
        
    }
}