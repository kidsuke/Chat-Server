/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import chatclient.ChatClient;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

/**
 *
 * @author ADMIN
 */
public class SendMessageListener implements ActionListener{
    private PrintWriter writer;
    private ChatClient client;
    
    public SendMessageListener (PrintWriter writer, ChatClient client){
        this.writer = writer;
        this.client = client;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            String line = client.getChatPanel().getTextArea().getText();
            if (!line.equals("")){
                writer.println(line + " [-r-]");
                client.update("> " + line);
                client.getChatPanel().getTextArea().setText("");
            }
        }catch(Exception err){
            err.printStackTrace();
        }
    }

}
