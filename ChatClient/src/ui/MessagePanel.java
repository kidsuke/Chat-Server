/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.Dimension;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author ADMIN
 */
public class MessagePanel extends JPanel{
    private final JTextPane test;
    
    public MessagePanel(){
        test = new JTextPane();
        createComponents();
    }
    
    private void createComponents(){
        test.setEditable(false);
        DefaultCaret caret = (DefaultCaret)test.getCaret();
        //DefaultCaret caret = (DefaultCaret)outputArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JScrollPane scrollPane = new JScrollPane(test, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(462, 230));
        scrollPane.setViewportView(test);
        
        add(scrollPane);
    }
    
    public JTextPane getTextPane(){
        return test;
    }
}
