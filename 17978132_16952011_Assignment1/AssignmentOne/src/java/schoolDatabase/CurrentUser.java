/*
 * CurrentUser JavaBean representing active "admin" user
 * At this point in time, entered username does not matter as there are no
 * actual, recorded "admin" users
 * This is to record session tracking of the currently active user  
*/

package schoolDatabase;

import java.io.Serializable;

/**
 *
 * @author dqp6065 & vmm0807
 */
public class CurrentUser implements Serializable{
    private String username;
    
   public CurrentUser(){
       username = null;
   }
   public CurrentUser(String username){
       this.username = username;
   }
   
   public String getUsername(){
       return username;
   }
   public void setUsername(String username){
       this.username = username;
   }
}
