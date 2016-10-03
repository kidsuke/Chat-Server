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

public interface ObservableHistory {
    public void register(HistoryObserver o);
    public void deregister(HistoryObserver o);
    public void notifyAllObserver(ChatMessage chat);
}
