/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import ui.MessagePanel;

/*
 * @author ADMIN
 * Long Vu - 1504200
 * Phuc Ho - 1504223
 * Liam Nguyen - 1504241
 */

public class HandleIncomingMessage implements Runnable{
    private final BufferedReader reader;
    private final MessagePanel panel;
    private final DefaultStyledDocument doc;
    
    public HandleIncomingMessage(InputStream is, MessagePanel panel, DefaultStyledDocument doc){
        reader = new BufferedReader(new InputStreamReader(is));
        this.doc = doc;
        this.panel = panel;
    }
    
    @Override
    public void run(){
      
        
        try{
            String msg;
     
            while(true){
                if ((msg = reader.readLine()) == null)
                    continue;

                insertMessage(msg);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public Style stylingMessage(){
        panel.getTextPane().setDocument(doc);
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
}
