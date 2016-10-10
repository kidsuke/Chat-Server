/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import ui.ChatPanel;
import ui.MessagePanel;

/*
 * @author ADMIN
 * Long Vu - 1504200
 * Phuc Ho - 1504223
 * Liam Nguyen - 1504241
 */

public class ChatClient {
    private PrintWriter writer;
    private JFrame frame;
    private MessagePanel messPanel;
    private ChatPanel chatPanel;
    private Socket socket;
    private DefaultStyledDocument doc;
    private boolean check;
    
    public ChatClient(){
        messPanel = new MessagePanel();
        chatPanel = new ChatPanel();
        doc = new DefaultStyledDocument();
        check = false;
    }
    public void run(){
        connect();
        initIO();
        createChatGUI();
        
        try{
            HandleIncomingMessage handleMess = new HandleIncomingMessage(this, socket);
            Thread t = new Thread(handleMess);
            t.start();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        while (true) {
            String username = JOptionPane.showInputDialog("Enter Username");
            if (!username.equals(""))
                writer.println(":user " + username + " [-r-]");
            
            if (check = true)
                break;
            
            JOptionPane.showMessageDialog(null, "Invalid Username or Username has been used");
        }
        
       
    }
    
    public void connect(){
        //while (true) {
            //String address = JOptionPane.showInputDialog("Enter IP Address");
            //InetAddress serverAddress = InetAddress.getByName(address);
            try {
                //if (serverAddress.isReachable(1000)){
                socket = new Socket("localhost", 9000);
                update(socket.toString()); 
                update("Connected to " + socket.getRemoteSocketAddress() + "\n");
               
            }catch(IOException ioe) {
                ioe.printStackTrace();
            }
            //JOptionPane.showMessageDialog(null, "IP Address is not available");
        //}
  
    }
    
    public void initIO(){
         try {
            writer = new PrintWriter(socket.getOutputStream(), true);
        }catch (IOException e){
            System.out.println("Cannot open output stream");
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
        
        sendMessageSetup();
    }
    
    public void sendMessageSetup(){
        chatPanel.getButton().addActionListener(new SendMessageListener(writer, this));
        
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
    
    public Style stylingMessage(){
        messPanel.getTextPane().setDocument(doc);
        StyleContext context = new StyleContext();
        Style style = context.addStyle("test", null);
        StyleConstants.setForeground(style, Color.BLUE);
        StyleConstants.setBold(style, true);
        
        return style;
    }
    
    public void insertMessage(String msg){
        try{
            if (msg.startsWith("@")){
                doc.insertString(doc.getLength(), msg.substring(0, msg.indexOf(":") + 1), stylingMessage());
                doc.insertString(doc.getLength(), msg.substring(msg.indexOf(":") + 1, msg.length()) + "\n", null);
            }else
                doc.insertString(doc.getLength(), msg + "\n", null);
        }catch(BadLocationException e){
            System.out.println("Attempts to reference a location that doesn't exist");
        }
    }
    
    public void update(String msg){
        if (!msg.equals("invalid"))
            check = true;
        else
            return;
            
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                insertMessage(msg);
            }
        });
    }
    
    public ChatPanel getChatPanel(){
        return chatPanel;
    }
    
}
