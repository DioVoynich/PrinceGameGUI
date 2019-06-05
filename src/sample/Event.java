/*
James Kahle

The Event class is used to keep track of the description and
decisions that make up an event

desciption text should be around 3 to 4 sentences long, begining with an 
address to the player "M'lord" and ending with asking what the players command is
*/
package sample;
import java.util.*;

public class Event {
   //does this event take 1 year of game time
   //public boolean isChain;
   private boolean year;
  
  //string text for event description
   private String text;
   
   //decisions are stored in a decision array
   private Decision[] decisions;
   private String title;
   public Event left;
   public Event right;
   public boolean isLoss;
   
   //notice how the constructor requires the decision array to already be formed
   //we may want to change this order later
   
   public Event(boolean year, String text, Decision[] decisions, String title) {

      this.year = year;
      this.text = text;
      this.decisions = decisions;
      this.title = title;
      
   }
   
   public boolean increasesYear() {
      return year;
   }
   
   public String getTxt() {
      return text;
   }

   public String getTitle() {
       return title;
   }
   
   public Decision[] getDecs() {
      return decisions;
   }


   

   
   //additional methods will almost certaintly be needed for things
   //event trees but this is just the most basic design
}