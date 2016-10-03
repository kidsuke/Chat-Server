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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CommandInterpreter implements Runnable, HistoryObserver{
    private final BufferedReader reader;
    private final PrintWriter writer;
    private List<User> listOfUsers = new ArrayList<User>();
    private List<UserCommand> listOfCommands = new ArrayList<UserCommand>();
    private User currentUser;
    private ChatHistory history;
    
    public CommandInterpreter(InputStream is, PrintStream ps){
        reader = new BufferedReader(new InputStreamReader(is));
        writer = new PrintWriter(ps, true);
        listOfCommands.add(new UserCommand(":user"));
        listOfCommands.add(new UserCommand(":list"));
        listOfCommands.add(new UserCommand(":history"));
        listOfCommands.add(new UserCommand(":quit"));
        listOfUsers.add(new User("longvu"));
        listOfUsers.add(new User("phucho"));
        listOfUsers.add(new User("liamnguyen"));
        history = ChatHistory.getInstance();
    }

    @Override
    public void run(){
        String line;
        String message;
        StringBuilder messBuilder = new StringBuilder(500);
        history.register(this);
      
        while(true){
            message = "";
            try{
                while (!(line = reader.readLine()).endsWith("[-r-]")){
                    
                    messBuilder.append(line).append("\n");
                } 
                messBuilder.append(line.substring(0, line.indexOf(" [-r-]")));
                message = messBuilder.toString();
                if (message.equals(""))
                    continue;
                System.out.println(message);
                if (message.charAt(0) == ':'){
                    UserCommand command = checkForCommand(message);
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
                                    else
                                        writer.println("Current User: " + currentUser.getName());
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
                        history.notifyAllObserver(new ChatMessage(currentUser,message));
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            messBuilder.setLength(0);
        }
    }
    
    public UserCommand checkForCommand(String chat){
        for (UserCommand command: listOfCommands){
            if (chat.contains(command.getCommand() + " ") || chat.equals(command.getCommand()))
                return command;
        }
        
        return null;
    }
    
    public User checkForUser(String chat){
        for (User user: listOfUsers){
            if (chat.substring(chat.indexOf(" "), chat.length()).contains(user.getName()))
                return user;
        }
        
        return null;
    }
    
    @Override
    public void update(ChatMessage chat){
        writer.println(chat);
    }
}
