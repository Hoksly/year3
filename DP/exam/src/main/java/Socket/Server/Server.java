package Socket.Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


import Socket.Client.ClientHandler;

import DataManager.DataManager;
import DataManager.ConcertFactory;

public class Server {
    // private ServerDB serverDB;
    public DataManager dbManager;
    private ServerSocket serverSocket;
    private final int portId = 1234;

    public Server() {
        try {
            this.serverSocket = new ServerSocket(portId);
            dbManager = new DataManager(ConcertFactory.initSampleData(), "S");
            listenForClients();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            closeServer();
        }
    }

    public void run() {
        listenForClients();
    }

    private void listenForClients() {
        while (!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("\n A new Client has connected!\n");
                ClientHandler handler = new ClientHandler(socket, this);

                Thread thr = new Thread(handler);
                thr.start();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                closeServer();
            }
        }
    }

    private void closeServer() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}