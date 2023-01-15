package com.grozetckii;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {

        final String serverHost = "localhost";

        Socket socket = null;
        PrintWriter output = null;
        BufferedReader input = null;

        try {
            socket = new Socket(serverHost, 7777);

            output = new PrintWriter(socket.getOutputStream());
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + serverHost);
            return;
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + serverHost);
            return;
        }

        String message;
        Scanner scan = new Scanner(System.in);

        while(!(message = input.readLine()).equals("exit")) {
            System.out.println(message);
            message = scan.next();
            output.println(">> " + message);
            output.flush();
        }


        /*Scanner scan = new Scanner(System.in);
        while ((message = input.readLine()) != null) {
            System.out.println(message);
            if (message.contains("OK")) {
                break;
            }
            if(scan.hasNext()){
                message = scan.nextLine();
                output.println(message);
                output.flush();
            }
        }*/

        output.close();
        input.close();
        socket.close();
    }
}
