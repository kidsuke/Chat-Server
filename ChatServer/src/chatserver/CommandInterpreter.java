/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

/*
 * @author ADMIN
 * Long Vu - 1504200
 * Phuc Ho - 1504223
 * Liam Nguyen - 1504241
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class CommandInterpreter implements Runnable, HistoryObserver{
    private BufferedReader reader;
    private PrintWriter writer;
    private Users listOfUsers;
    //private List<UserCommand> listOfCommands = new ArrayList<UserCommand>();
    //private User currentUser;
    private Socket socket;
    private String currentUser;
    private ChatHistory history;
    
    public CommandInterpreter(Socket s){
        this.socket = s;
        listOfUsers = Users.getIntance();
        history = ChatHistory.getInstance();
    }

    @Override
    public void run(){
        initIO();
        String line;
        String message;
        StringBuilder messBuilder = new StringBuilder(500);
        
        history.register(this);
      
        while(true){
            try{
                while (!(line = reader.readLine()).endsWith("[-r-]")){
                    
                    messBuilder.append(line).append("\n");
                } 
                
                messBuilder.append(line.substring(0, line.indexOf(" [-r-]")));
                message = messBuilder.toString();
          
                if (message.equals(""))
                    continue;
                
                if (message.startsWith(":user ")){
                    currentUser = message.substring(message.indexOf(" ")+1, message.length());
                    if (!listOfUsers.exists(currentUser)){ 
                        listOfUsers.insert(currentUser);
                        writer.println("Login Successfully");
                        writer.println("Current User: " + currentUser + " [-r-]");
                        history.notifyAllObserverExcept(this, new ChatMessage(currentUser,"has logged in.", true));
                    }else
                        writer.println("invalid");
                }else if (message.equals(":list")){
                    writer.println("Online:\n" + listOfUsers.toString());
                }else if (message.equals(":history")){
                    writer.println("History:\n" + history.toString());
                }else if (message.equals(":quit")) {
                    break;
                }else{
                    history.notifyAllObserverExcept(this, new ChatMessage(currentUser,message, false));
                }
            }//catch (SocketException e){
                //listOfUsers.remove(currentUser);
                //close();
               // break;
            //}
            catch(IOException ioe){
                break;
            }
            catch(NullPointerException npe){
                break;
            }
              
            messBuilder.setLength(0);
        }
        
        listOfUsers.remove(currentUser);
        history.notifyAllObserverExcept(this, new ChatMessage(currentUser,"has logged out.", true));
        history.deregister(this);
        close();
    }
    
    public void close(){
        try{    
            if (writer != null)
                writer.close();
            if (reader != null)
                reader.close();
            if (socket != null)
                socket.close();
        }catch(IOException ioe){
            System.out.println("Cannot close Input or Output Stream");
        }
    }
    
    public void initIO(){
        try{
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        }catch(IOException ioe){
            System.out.println("Cannot open IO Stream");
        }
    }
    
    @Override
    public void update(ChatMessage chat){
        writer.println(chat);
    }
}
