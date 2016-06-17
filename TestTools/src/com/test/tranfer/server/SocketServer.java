package com.test.tranfer.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    private int socket;

    public SocketServer(int socket) {
        if (socket <= 0 || socket > 64999) {
            throw new IllegalArgumentException("Socket number is not in the range..");
        }
        this.socket = socket;

    }

    public void startServer() {
        init();
    }

    private void init() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(socket);
            System.out.println("Waiting for Connection... on port" + socket);
            while (true) {
                Socket clientConnection = server.accept();
                System.out.println("GotConnection... on port" + socket);
                handleClientConnection(clientConnection);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null)
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }

    private void handleClientConnection(Socket clientConnection) {
        ClientHandler clientHandler = new ClientHandler();
        System.out.println("Initalizing client Handler");
        clientHandler.handleClient(clientConnection);
    }
}
