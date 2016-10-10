/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @author ADMIN
 * Long Vu - 1504200
 * Phuc Ho - 1504223
 * Liam Nguyen - 1504241
 */

public class ChatHistory implements ObservableHistory{
    Map<User,ChatMessage> history;
    List<HistoryObserver> listOfObservers;
    private static ChatHistory instance;
    
    private ChatHistory(){
        history = new HashMap<User,ChatMessage>();
        listOfObservers = new ArrayList<HistoryObserver>();
    }
    
    public void addMessage(User user, ChatMessage message){
        
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
        for (HistoryObserver ho: listOfObservers){
            if (!ho.equals(o))
                ho.update(chat);   
       }
    }
    
    
}
