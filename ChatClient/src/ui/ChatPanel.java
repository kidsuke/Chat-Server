/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;

/**
 *
 * @author ADMIN
 */
public class ChatPanel extends JPanel{
    private JTextArea inputArea;
    private JButton sendButton;
    
    public ChatPanel(){
        super(new GridBagLayout());
        inputArea = new JTextArea();
        sendButton = new JButton("Send");
        createComponents();
    }
    
    private void createComponents(){
        GridBagConstraints c = new GridBagConstraints();
        
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;;    
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.75;
        c.ipady = 70;
        c.ipadx = 250;
        c.insets = new Insets(0,0,0,5);
        inputArea = new JTextArea();
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(inputArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, c);
        
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_END;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0;
        c.ipady = 50;
        c.ipadx = 30;
        c.insets = new Insets(0,0,0,0);
        sendButton = new JButton("Send");
        add(sendButton, c);
    }
    
    public JButton getButton(){
        return sendButton;
    }
    
    public JTextArea getTextArea(){
        return inputArea;
    }
}
