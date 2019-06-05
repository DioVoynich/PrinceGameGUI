/*
James Kahle

The Decision class is used to keep track of the description and
effects of each decision

desciption text should be 1 sentence long, begining with number that
corresponds to the number of the decision

*/
package sample;
import java.util.*;

public class Decision {
   //String for text
   private String text;
   
   //effects are stored in an array for ease of passing to the play class
   private Effect[] effects; 

   public Decision(String text, Effect[] effects ) {
      this.text = text;
      this.effects = effects;
   }
   public String getTxt() {
      return text;
   }
   
   public Effect[] getEffs() {
      return effects;  
   }   
   
   //we may want a print method but that can be done from the oneGame class
}