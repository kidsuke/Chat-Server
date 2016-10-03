/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

/**
 *
 * @author ADMIN
 */
public class SendMessageListener implements ActionListener{
    private PrintWriter writer;
    private ChatPanel panel;
    
    public SendMessageListener (PrintWriter writer, ChatPanel panel){
        this.writer = writer;
        this.panel = panel;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String line = panel.getTextArea().getText();
        if (!line.equals("")){
            writer.println(line + " [-r-]");
            panel.getTextArea().setText("");
        }
    }

}
