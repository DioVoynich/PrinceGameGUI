/*
The Decision class is used to keep track of the description and
effects of each decision
*/
package sample;
import java.util.*;

public class Decision {
   //text is the decision or choice description
   private String text;
   
   //effects are stored in an array for ease of passing to the play class
   private Effect[] effects; 
   
   //sets the fields to the parameter values
   public Decision(String text, Effect[] effects ) {
      this.text = text;
      this.effects = effects;
   }
   //returns the decision string
   public String getTxt() {
      return text;
   }
   //returns the effect arrays
   public Effect[] getEffs() {
      return effects;  
   }   
   
}
