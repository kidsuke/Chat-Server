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

public class User {
    private String username;
    private boolean active;
    
    public User(String username){
        this.username = username;
        active = false;
    }
    
    public boolean isActive(){
        return active;
    }
    
    public void setActive(boolean activeState){
        active = activeState;
    }
    
    public String getName(){
        return username;
    }
    
    public boolean getState(){
        return active;
    }
    
    @Override
    public String toString(){
        return username;
    }
    
    @Override
    public boolean equals(Object object){
        if (object == null || getClass() != object.getClass()) 
           return false;
       
        User user = (User) object;
        
        return user.getName().equals(this.username) && user.getState() == this.active;
    }

    @Override
    public int hashCode() {
        if (this.username == null)
            return 7;
        
        return this.username.hashCode();
    }
}
