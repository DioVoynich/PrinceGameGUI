/*
The Event class is used to keep track of the description and
decisions that make up an event
*/
package sample;
import java.util.*;
   //event holds the event discription, if a year has passed and the decisions that you can take
public class Event {
   private boolean year;
   private String text;
   private Decision[] decisions;
   private String title;
   public Event left;
   public Event right;
   public boolean isLoss;
   
   //this is the constructor sets the fields to the parameter values
   public Event(boolean year, String text, Decision[] decisions, String title) {

      this.year = year;
      this.text = text;
      this.decisions = decisions;
      this.title = title;
      
   }
   //returns true if increases year else false
   public boolean increasesYear() {
      return year;
   }
   //returns the event description text
   public String getTxt() {
      return text;
   }
   // returns the title text
   public String getTitle() {
       return title;
   }
   //returns the array with the decisions
   public Decision[] getDecs() {
      return decisions;
   }

}
