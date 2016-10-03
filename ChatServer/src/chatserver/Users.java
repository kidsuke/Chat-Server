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

public class Users {
    List<String> listOfUsers;
    
    private Users(){
        listOfUsers = new ArrayList<String>();
    }
    
    public boolean exists(String username){
        return listOfUsers.contains(username);        
    }
    
    public void insert(String username){
        if (!listOfUsers.contains(username))
            listOfUsers.add(username);
    }
    
    public void remove(String username){
        if (listOfUsers.contains(username))
            listOfUsers.remove(username);
    }
    
    @Override
    public String toString(){
        String list = "";
        
        for (String s: listOfUsers){
            list += s + "\n";
        }
        
        return list;
    }
}
