package com.grozetckii;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    static ServerSocket socket;
    public static List<ConnectedClient> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        socket = new ServerSocket(7777);

        while (true) {
            ConnectedClient client = new ConnectedClient(socket.accept());
            clients.add(client);
            client.start();
        }
    }
}

class ConnectedClient extends Thread {
    private Socket sock;
    private BufferedReader input;
    private PrintWriter output;

    public ConnectedClient(Socket s) throws IOException {
        sock = s;
        System.out.println("new user connected from " + s.getInetAddress().toString());
        input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        output = new PrintWriter(sock.getOutputStream());
    }

    @Override
    public void run() {
        try {
            output.println("hi");
            output.flush();
            while (sock.isConnected()) {
                String readed = input.readLine();
                for (ConnectedClient c : Server.clients) {
                    c.send(readed);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            System.out.println("user disconnected from " + sock.getInetAddress().toString());
        }
    }

    public void send(String s) throws IOException {
        output.println(s);
        output.flush();
    }
}
