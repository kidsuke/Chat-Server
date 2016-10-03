/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/*
 * @author ADMIN
 * Long Vu - 1504200
 * Phuc Ho - 1504223
 * Liam Nguyen - 1504241
 */

public class ChatServer{
    private ServerSocket serverSocket;
    private Socket clientSocket;
   
    
    public ChatServer(){
        serverSocket = null;
        clientSocket = null;
       
    }
    
    public void serve(){
        try{
            System.out.println("Chat Server");
            serverSocket = new ServerSocket(9000);
        }catch(Exception e){
            e.printStackTrace();
        }
        while (true){
            try {
                System.out.println("Server started: " + serverSocket);
                System.out.println("Waiting for a client ..."); 
                clientSocket = serverSocket.accept();
                System.out.println("Client accepted: " + clientSocket);
                System.out.println("");
                CommandInterpreter ci = new CommandInterpreter(clientSocket.getInputStream(), new PrintStream(clientSocket.getOutputStream(), true));
                Thread t = new Thread(ci);
                t.start();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    
}
