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

public class ChatMessage {
    private String account;
    private String chat;
    private boolean isFromServer;
    
    public ChatMessage(String account, String chat, boolean isFromServer){
        this.account = account;
        this.chat = chat;
        this.isFromServer = isFromServer;
    }
    
    @Override
    public String toString(){
        if (!isFromServer)
            return "@" + account + ": " + chat + " [-r-]";
        else
            return account + " " + chat + " [-r-]";
    }
}
