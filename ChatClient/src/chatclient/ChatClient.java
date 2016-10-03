/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.net.Socket;
import java.util.Scanner;
import javax.swing.SwingUtilities;
import ui.UserInterface;

/*
 * @author ADMIN
 * Long Vu - 1504200
 * Phuc Ho - 1504223
 * Liam Nguyen - 1504241
 */

public class ChatClient {

    public void sendConnect(){
        try {
            System.out.println("Connecting to Chat Server");
            System.out.println("Please wait...");
            Socket sk = new Socket("localhost", 9000);

            SwingUtilities.invokeLater(new UserInterface(sk));
        }catch (Exception e){
            e.printStackTrace();
        }
    } 
}
