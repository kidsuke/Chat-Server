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
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class CommandInterpreter implements Runnable, HistoryObserver{
    private final BufferedReader reader;
    private final PrintWriter writer;
    private Users listOfUsers;
    //private List<UserCommand> listOfCommands = new ArrayList<UserCommand>();
    //private User currentUser;
    private String currentUser;
    private ChatHistory history;
    
    public CommandInterpreter(InputStream is, OutputStream ps){
        reader = new BufferedReader(new InputStreamReader(is));
        writer = new PrintWriter(ps, true);
        listOfUsers = Users.getIntance();
        history = ChatHistory.getInstance();
    }

    @Override
    public void run(){
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
                    currentUser = message.substring(message.indexOf(" "), message.length());
                    if (!listOfUsers.exists(currentUser)){ 
                        listOfUsers.insert(currentUser);
                        System.out.println(currentUser.length());
                        writer.println("Login Successfully");
                        writer.println("Current User: " + currentUser);
                        history.notifyAllObserverExcept(this, new ChatMessage(currentUser,"has logged in.", true));
                    }else
                        writer.println("invalid");
                }else if (message.equals(":quit")) {
                    listOfUsers.remove(currentUser);
                    history.notifyAllObserverExcept(this, new ChatMessage(currentUser,"has logged out.", true));
                    close();
                    break;
                }else{
                    history.notifyAllObserverExcept(this, new ChatMessage(currentUser,message, false));
                }
            }catch (SocketException e){
                close();
            }catch(IOException ioe){
                close();
            }catch(NullPointerException npe){
                close();
            }
                /*if (message.charAt(0) == ':'){
                    System.out.println("inside ?");
                    UserCommand command = checkForCommand(message);
                    System.out.println(command.toString());
                    if (command != null){
                        switch(command.getCommand()){
                            case ":user":
                                if (message.equals(command.getCommand())){
                                    if (currentUser == null)
                                        writer.println("Please log in");
                                    else
                                        writer.println("Current User: " + currentUser.getName());
                                }else{
                                    currentUser = checkForUser(message);
                                    if (currentUser == null)
                                        writer.println("User does not exist");
                                        //System.out.println("Logging in failed");
                                    else{
                                        System.out.println("here?");
                                        writer.println("Current User: " + currentUser.getName());
                                    }
                                }
                                break;
                            case ":list":
                                writer.println("List of users: ");
                                //System.out.println("User Lists");
                                break;
                            case ":history":
                                writer.println("Chat history");
                                //System.out.println("History");
                                break;
                            case ":quit":
                                writer.println("Goodbye!");
                                return;
                            default:
                                break;
                        }
                    }
                }else{
                    if (currentUser == null)
                        writer.println("Username not set. Give \":user username\" to log in.");
                    else{
                        history.notifyAllObserverExcept(this, new ChatMessage(currentUser,message));
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }*/
            messBuilder.setLength(0);
        }
    }
    
    public void close(){
        try{    
            if (writer != null)
                writer.close();
            if (reader != null)
                reader.close();
        }catch(IOException ioe){
            System.out.println("Cannot close Input or Output Stream");
        }
    }
    
    
    @Override
    public void update(ChatMessage chat){
        writer.println(chat);
    }
}
