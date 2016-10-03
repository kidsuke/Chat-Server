/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

/**
 *
 * @author ADMIN
 */
import chatclient.HandleIncomingMessage;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

public class UserInterface implements Runnable{
    private PrintWriter writer;
    private JFrame frame;
    private MessagePanel messPanel;
    private ChatPanel chatPanel;
    private final Socket s;
    private final DefaultStyledDocument doc;
    
    public UserInterface(Socket s){
        messPanel = new MessagePanel();
        chatPanel = new ChatPanel();
        this.s = s;
        doc = new DefaultStyledDocument();
        try {
            writer = new PrintWriter(s.getOutputStream(), true);
        }catch (IOException e){
            System.out.println("Cannot open output stream");
        }
    }
    
    @Override
    public void run() {
        createChatGUI();
        
        try{
            doc.insertString(doc.getLength(), s.toString() + "\n", null);
            doc.insertString(doc.getLength(), "Connected to " + s.getRemoteSocketAddress() + "\n\n", null);
        }catch(BadLocationException e){
            System.out.println("Attempts to reference a location that doesn't exist");
        }
        
        sendMessage();
        
        try {
            HandleIncomingMessage handleMess = new HandleIncomingMessage(s.getInputStream(), messPanel, doc);
            Thread t = new Thread(handleMess);
            t.start();
        }catch (Exception e){
            
        }
    }
    
    public void createChatGUI(){
        frame = new JFrame("Client");
        frame.setPreferredSize(new Dimension(500, 400));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(new JPanel());
        
        createComponents((JPanel)frame.getContentPane());
        
        frame.pack();
        frame.setVisible(true);
    }
    
    public void createComponents(JPanel container){
        
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        container.add(messPanel);
        messPanel.getTextPane().setDocument(doc);
        
        container.add(chatPanel);
    }
    
    public void sendMessage(){
        chatPanel.getButton().addActionListener(new SendMessageListener(writer, chatPanel));
        
        chatPanel.getTextArea().getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "Send");
        chatPanel.getTextArea().getActionMap().put("Send", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                chatPanel.getButton().doClick();
            }
        });
        
        chatPanel.getTextArea().getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,InputEvent.SHIFT_DOWN_MASK), "AddNewLine");
        chatPanel.getTextArea().getActionMap().put("AddNewLine", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                chatPanel.getTextArea().append("\n");
            }
        });
    }
    
}
