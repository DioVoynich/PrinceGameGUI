
package sample;
import java.util.*;
import java.io.*;

public class GameManager {
   //all the game stats that have a string name and number
   private Map <String,Integer> stats;
   private Map <String, Event> lossEvents;
   //player name to be chosen at the start of a new game
   //private String name;

   //all potential events
   private ArrayList<Event> eventObjs = new ArrayList<Event>();
   private ArrayList<Event> lossEventsStorage = new ArrayList<>();
   //Contains strings of all stats that can trigger loss conditions.
   private Iterator<Map.Entry<String, Integer>> iter;

   public Event currentEvent;
   public boolean gameisRunning;

   //constructor
   //requires name of player character at start of the new game
   public GameManager() throws Exception {
//      this.name = name;
      String pathEvent = "E:\\All Computer Science Materials\\Java 240 Project\\PrinceFX\\src\\sample\\situation.txt";
      String lossPath = "E:\\All Computer Science Materials\\Java 240 Project\\PrinceFX\\src\\sample\\loss events.txt";
      eventObjs = loadEvents(pathEvent);
      lossEventsStorage = loadEvents(lossPath);

      stats = new HashMap<String,Integer>();
      //new game conditions
      stats.put("CLG_LOY", 100);
      stats.put("CLG_INF", 50);
      stats.put("NOB_LOY", 100);
      stats.put("NOB_INF", 50);
      stats.put("COM_LOY", 100);
      stats.put("COM_INF", 50);
      stats.put("WLTH", 100);
      stats.put("ARMY", 100);
      stats.put("HLTH", 100);
      stats.put("YEAR", 1066);
      stats.put("AGE", 20);

      lossEvents = new HashMap<>();
       for (int i = 0; i < 10; i++) {
            lossEventsStorage.get(i).isLoss = true;
       }
      lossEvents.put("CLG_LOY", lossEventsStorage.get(0));
      lossEvents.put("CLG_INF", lossEventsStorage.get(1));
      lossEvents.put("NOB_LOY", lossEventsStorage.get(2));
      lossEvents.put("NOB_INF", lossEventsStorage.get(3));
      lossEvents.put("COM_LOY", lossEventsStorage.get(4));
      lossEvents.put("COM_INF", lossEventsStorage.get(5));
      lossEvents.put("WLTH", lossEventsStorage.get(6));
      lossEvents.put("ARMY", lossEventsStorage.get(7));
      lossEvents.put("HLTH", lossEventsStorage.get(8));
      lossEvents.put("AGE", lossEventsStorage.get(9));


   }
   //loads game state from 
   public GameManager(File saveFile){
       //WIP
   }

   public Event setEvent(Random rng) {
//       if (rng.nextInt(100) < 20) {
//           //return obj from treeList
//       }
//       else {
           currentEvent = eventObjs.get(rng.nextInt(eventObjs.size()));
       //}
        return currentEvent;
   }

   public Event getEvent(Random rng) { // Functionality has been replaced by setEvent at the moment.

      return eventObjs.get(rng.nextInt(eventObjs.size()));
   }
// checks if the current event is a chain event.
   public Event nextEvent(Event event, int i, Random rng) {
        if (i == 1) {
            currentEvent = currentEvent.left;
        } else if (i == 2) {
            currentEvent = currentEvent.right;
        }
        else if (event == null) {
            return eventObjs.get(rng.nextInt(eventObjs.size()));
        }

        return currentEvent;
   }

   public ArrayList loadEvents(String fileName) throws FileNotFoundException{
       ArrayList<Event> eventList = new ArrayList<>();
       File inputFile = new File(fileName);
       if (!inputFile.canRead()) {
           System.out.println("Required input file not found; exiting.\n" + inputFile.getAbsolutePath());
           System.exit(1);
       }
       Scanner input = new Scanner(inputFile);
       // Scanner input = new Scanner(new File("situation.txt"));
       String currentEventStr = "";
       //array list with the events

       //Set<String> events = new HashSet<String>(String.CASE_INSENSITIVE_ORDER);
       //T refers to title
       String title = "";
       Boolean year = null;
       String situation = null;
       Decision[] decisions = new Decision[4];
       int dIndex = 0;
       int lineNum = 1;
       while(input.hasNextLine()){
           String currentLine = input.nextLine();
           //finds the title string find out what to do with it
           //sep is the separater : index
           if(lineNum<=3){
               //gets the situation string which is the string the user will see but I didn't see where to plug it in
               //maybe we should add a new field to the event class for the display string
               //nevermind I found were to put it
               int sep = currentLine.indexOf(":");
               String str = currentLine.substring(sep+1);
               if(lineNum == 2){
                   if (str.equals("N")) {
                       year = false;
                   } else {
                       year = true;
                   }
               }else if(lineNum == 1){
                   title = str;
               }else{
                   situation = str;
               }
               lineNum ++;
           }else if(currentLine.contains("*")){
               //creates the event with all of the variables found
               //adds the current event to the list

               Event currentEvent = new Event(year, situation, decisions, title);
               eventList.add(currentEvent);
               year = false;
               title = "";
               situation = "";
               decisions = new Decision[4];
               lineNum = 1;
               dIndex = 0;
           }else if(lineNum > 3){
               //sep is the separater )
               //int sep = input.indexOf(")");
               //String idLetter = input.substring(0,sep);
               String str = currentLine;
               Effect[] effects=new Effect[4];
               int index =0;
               //separates the string into an array with the separation taking place in every "|"
               //essentialy gets the text for the decision and the effects
               String[] firstSplit = str.split("/");
               //separates the sequence with the key for the stat and the value
               //here it splits the effect keys with their corresponding value
               String[] secondSplit = firstSplit[1].toString().split(" ");
               for(int i = 0; i < secondSplit.length; i++){
                   String currentKeyAndStat = secondSplit[i].toString();
                   //splits the effect key and value from the string and builds the effect object
                   String[] keyAndStat = currentKeyAndStat.split("#");
                   String key = keyAndStat[0].toString();
                   String statStr = keyAndStat[1].toString();
                   int stat = Integer.parseInt(statStr);
                   //builds the effect with the variables
                   Effect currentEff = new Effect(key,stat);
                   //adds to the list of effects
                   effects[index] = currentEff;
                   index++;
               }
               //I don't know how to name the decisions
               Decision currentDecision = new Decision(firstSplit[0].toString(), effects);
               decisions[dIndex] = currentDecision;
               dIndex++;
               lineNum++;
           }
       }
       return eventList;
   }

    //this is where the scanner will go
   public void setCurrentEvent() throws FileNotFoundException {
      
      Scanner in = new Scanner(new File("EventsFile.txt"));

      //creates Events and puts them into the eventPool
   }
  
   //currently does nothing
   //would check the trigger conditions for events
   public void checkTriggers() {
  
   }
  
   //prints gamestate to save file
   public void save(File saveFile) {
      //WIP
   }
   
   //returns String name for printing
//   public String getName() {
//
//       return name;
//   }
   
   //returns map of stats for printing
   public Map<String, Integer> getStats() {
      return stats;
   }

   // Retrieves map key and value using target string. It then stores value and updates it according to value passed into method.
   public void updateValue(String target, int value) {

      int newStat = stats.get(target) + value;

      if (newStat < 0) { //Handles scenario in which a stat rises above 100 or falls below 0.
          newStat = 0;
      }
      else if (newStat > 100) {
          newStat = 100;
      }
      stats.put(target, newStat);
   }

   public void increaseYear() {
       stats.put("AGE", stats.get("AGE") + 1);
       stats.put("YEAR", stats.get("YEAR") + 1);
   }

   // get rid of gameover field, check if currentEvent isLoss, if yes then print loss event and end loop
   public boolean isGameOver() {
       iter = stats.entrySet().iterator();
       while (iter.hasNext()) {
           Map.Entry<String, Integer> current = iter.next();
           if (current.getKey().equals("AGE") || current.getKey().equals("NOB_INF") || current.getKey().equals("COM_INF") || current.getKey().equals("CLG_INF")) {
               if (current.getValue() == 100) {
                    currentEvent = lossEvents.get(current.getKey());
                    return true;

               }

           } else {
               if (current.getValue() == 0) {
                   currentEvent = lossEvents.get(current.getKey());
                   return true;
               }
           }
       }
    return false;
   }    
    
    // This method is made by David. It is used to reset all the status
    // once the player chooses to leave the play scene. 
    public void reset() {
        stats.put("CLG_LOY", 100);
        stats.put("CLG_INF", 50);
        stats.put("NOB_LOY", 100);
        stats.put("NOB_INF", 50);
        stats.put("COM_LOY", 100);
        stats.put("COM_INF", 50);
        stats.put("WLTH", 100);
        stats.put("ARMY", 100);
        stats.put("HLTH", 100);
        stats.put("YEAR", 1066);
        stats.put("AGE", 20);
    }
        
}