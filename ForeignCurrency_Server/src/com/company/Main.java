package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {

    public static final int PORT = 4500;

    public static void main(String[] args) {
        //client connection process in separate thread
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(PORT);
            while(true){
                System.out.println("waiting for incoming communication....");
                Socket socket = serverSocket.accept();
                System.out.println("client connected!! :)");
                Thread thread = new ClientThread(socket);
                thread.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
