package com.example.chatappserverside.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chatappserverside.Background.ConfiqRead;
import com.example.chatappserverside.helper.Constanat;

@Service
public class ServerServices {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String textMessage;
    private Boolean isValid = false;
    private ConfiqRead serverFile;

    @Autowired
    private Constanat constant;

    public ServerServices(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Autowired
    public ServerServices(ConfiqRead serverFile) {
        this.serverFile = serverFile;
        serverStart();
    }

    // Start the server connection
    public void serverStart() {
        try {
            String ClientIp = serverFile.getValue("CHAT_APP_HOST").replace("\"", "").trim();

            String ClientPort = serverFile.getValue("CHAT_APP_PORT");

            InetAddress ip = InetAddress.getByName(ClientIp);

            serverSocket = new ServerSocket(Integer.parseInt(ClientPort), 50, ip);
            while (!serverSocket.isClosed()) {
                clientSocket = serverSocket.accept();
                System.out.println("New Friend Connected");

                Thread thread = new Thread(() -> sendMessage(clientSocket));
                thread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in serverStart: " + e.getMessage());
        }
    }

    // Send and receive messages from client
    public void sendMessage(Socket clientSocket) {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while ((textMessage = in.readLine()) != null) {
                System.out.println("Received from client: " + textMessage);
                if ("exit".equalsIgnoreCase(textMessage)) {
                    System.out.println("Client exited");
                    break;
                }else{
                    System.out.println("Session Response Message: " + textMessage);
                    validTextMessage(textMessage);

                }

            }


            

            if (serverFile.getValue("SERVER_CONF").contains("FALSE")) {
                System.out.println("Where THE USER Is Exit !!");
                stop(clientSocket);

            }

        } catch (Exception e) {
            System.err.println("Error in sendMessage: " + e.getMessage());
        }
    }

    // Stop connection and session for a specific client
    public void stop(Socket clientSocket) throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        System.out.println("Client connection and session closed.");
    }

    // Close the server socket
    public void closeServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("Server socket closed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in closeServer: " + e.getMessage());
        }
    }

    // Validate the text message that can be sent to client
    public void validTextMessage(String message) {
        if (message == null || message.trim().isEmpty()) {
            this.isValid = false;
        } else {
            this.isValid = true;
            if (this.isValid) {
                String uniqueId = registrationInAllClientInServer("Register", "127.0.0.1");
                out.println("You Are Successfully Registered In Server with Unique ID:" + "" + uniqueId);

            }
        }

    }

    // Registration in all clients in Server

    public String registrationInAllClientInServer(String request, String Ip) {
        String registerId = "";
        try {
            if (request.trim().isEmpty() || request == null) {
                System.err.println("Request Message is Not Found In Server");
                return "";
            }
            if (request.length() <= 3) {
                System.err.println("Where Request Message Is Very Short");
                return "";
            }
            if (request.contains("Exit")) {
                System.err.println("Where Client Want Exit !...");
                return "";
            }

            if (request.contains("Register")) {
                registerId = UUID.randomUUID().toString() + "" + Ip + "" + "CR011JXT";

            }

        } catch (Exception e) {

            System.err.println("Error are found in Registration" + e.getMessage());
            e.printStackTrace();
        }
        return registerId;

    }
}
