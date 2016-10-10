/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/*
 * @author ADMIN
 * Long Vu - 1504200
 * Phuc Ho - 1504223
 * Liam Nguyen - 1504241
 */

public class HandleIncomingMessage implements Runnable{
    private BufferedReader reader;
    private Socket socket;
    private ChatClient client;
    
    public HandleIncomingMessage(ChatClient client, Socket s){
        this.socket = s;
        this.client = client;
    }
    
    @Override
    public void run(){
        initIO();
        
        try{
            String msg;
            while(true){
                if ((msg = reader.readLine()) == null)
                    continue;
                
                client.update(msg);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void initIO(){
        try{
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch(IOException ioe){
            System.out.println("Cannot open input stream");
        }
    }
}
