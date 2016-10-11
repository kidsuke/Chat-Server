/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.util.ArrayList;
import java.util.List;

/*
 * @author ADMIN
 * Long Vu - 1504200
 * Phuc Ho - 1504223
 * Liam Nguyen - 1504241
 */

public class ChatHistory implements ObservableHistory{
    List<ChatMessage> history;
    List<HistoryObserver> listOfObservers;
    private static ChatHistory instance;
    
    private ChatHistory(){
        history = new ArrayList<ChatMessage>();
        listOfObservers = new ArrayList<HistoryObserver>();
    }
    
    public static ChatHistory getInstance(){
        if (instance == null)
            instance = new ChatHistory();
        
        return instance;
    }

    @Override
    public synchronized void register(HistoryObserver o) {
        if (!listOfObservers.contains(o))
           listOfObservers.add(o);
    }

    @Override
    public synchronized void deregister(HistoryObserver o) {
        if (listOfObservers.contains(o))
           listOfObservers.remove(o);
    }  

    @Override
    public synchronized void notifyAllObserver(ChatMessage chat){
        for (HistoryObserver ho: listOfObservers){
            ho.update(chat);   
       }
    }
    
    @Override
    public synchronized void notifyAllObserverExcept(HistoryObserver o, ChatMessage chat){
        history.add(chat);
        for (HistoryObserver ho: listOfObservers){
            if (!ho.equals(o))
                ho.update(chat);   
       }
    }
    
    @Override
    public String toString(){
        String list = "";
        
        for (ChatMessage m: history){
            list += m.toString() + "\n";
        }
        
        return list.substring(0, list.lastIndexOf("\n")) + " [-r-]";
    }
    
}
