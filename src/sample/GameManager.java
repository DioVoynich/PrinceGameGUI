
package sample;
import java.util.*;
import java.io.*;

public class GameManager {
   //all the game stats that have a string name and number
   private Map <String,Integer> stats;
   
   private Map <String, Event> lossEvents;
   
   //player name to be chosen at the start of a new game
   private String name;
   
   //all potential events
   private ArrayList<Event> eventObjs = new ArrayList<Event>();
   
   //private ArrayList<Event> lossEventsStorage = new ArrayList<Events>();
   private ArrayList<Event> chainEvents;
   
   //Contains strings of all stats that can trigger loss conditions.
   private Iterator<Map.Entry<String, Integer>> iter;
   
   public Event currentEvent;

   //public boolean gameIsRunning;
   
   //constructor
   //requires name of player character at start of the new game
   public GameManager() throws Exception {
      this.name = "Harry";
      eventObjs = new ArrayList<Event>();
      eventObjs = loadEvents("E:\\All Computer Science Materials\\Java 240 Project\\PrinceFX\\src\\sample\\situation.txt");
      chainEvents = new ArrayList<Event>();
      stats = new HashMap<>();
      lossEvents = new HashMap<>();
      makeChains();
      buildStatsMap();
      buildLossMap();
      

      //new game conditions

      
     // sets the first event
      //setEvent();
   }

   public void setEvent() {
      Random rng = new Random();
      int dice = rng.nextInt(99);
     if(!chainEvents.isEmpty()){ 
         if (dice < 10) {
           currentEvent= chainEvents.get(rng.nextInt(chainEvents.size()));
         } else {
           currentEvent = eventObjs.get(rng.nextInt(eventObjs.size()));
         }  
      } else {
         currentEvent = eventObjs.get(rng.nextInt(eventObjs.size()));
      }
   }

   public Event getEvent() { 
      return currentEvent;
   }
// if the current event is a chain event, it will take it in the direction it is supposed to go
//else it will send to SetEvent and pick randomly
   public void nextEvent(int i) {

        //deletes the chain events for no duplicates      
        if(chainEvents.contains(currentEvent)) {
            chainEvents.remove(currentEvent);
        }              
        if( currentEvent.left == null && currentEvent.right == null ) {
            setEvent();

        } else if (i == 0) {
               if(currentEvent.left != null ) {
                  currentEvent = currentEvent.left;
               } else {
                  setEvent();
               }
        } else if (i == 1) {
               if( currentEvent.right != null) {
                  currentEvent = currentEvent.right;
               } else {
                  setEvent();
               }
        } else {
          setEvent();
        } 

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

                Event currEvent = new Event(year, situation, decisions, title);
                eventList.add(currEvent);
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
                    int stat = 0;
                    if(statStr.contains("-")){
                        int signIndex = statStr.indexOf('-') + 1;
                        statStr = statStr.substring(signIndex);
                        stat = Integer.parseInt(statStr);
                        stat*=-1;
                        //builds the effect with the variables
                    }else{
                        stat = Integer.parseInt(statStr);
                    }
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
  
   
   //returns String name for printing
   public String getName() {
      return name;
   }
   
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
          //Contains strings of all stats that can trigger loss conditions.
       Iterator<Map.Entry<String, Integer>> iter = stats.entrySet().iterator();
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
   
   private void makeChains() throws Exception {
      makeChainWitchHunt();
      tutorial();
   }
   
   private void makeChainWitchHunt()throws Exception {
      ArrayList<Event> witchHunt = new ArrayList<Event>();
      witchHunt = loadEvents("E:\\All Computer Science Materials\\Java 240 Project\\PrinceFX\\src\\sample\\witch hunt.txt");
      
      witchHunt.get(0).left = witchHunt.get(1);
      witchHunt.get(0).right = witchHunt.get(2);
      witchHunt.get(1).left = witchHunt.get(3);
      witchHunt.get(1).right = witchHunt.get(2);
      witchHunt.get(3).left = witchHunt.get(4);
      witchHunt.get(3).right = witchHunt.get(12); 
      witchHunt.get(4).left = witchHunt.get(5);
      witchHunt.get(4).right = witchHunt.get(12);
      witchHunt.get(5).left = witchHunt.get(6);
      witchHunt.get(5).right = witchHunt.get(12);
      witchHunt.get(6).left = witchHunt.get(7);
      witchHunt.get(6).right = witchHunt.get(12);
      witchHunt.get(7).left = witchHunt.get(8);
      witchHunt.get(7).right = witchHunt.get(12);
      witchHunt.get(8).left = witchHunt.get(9);
      witchHunt.get(8).right = witchHunt.get(12);
      witchHunt.get(9).left = witchHunt.get(10);
      witchHunt.get(9).right = witchHunt.get(13);
      witchHunt.get(10).left = witchHunt.get(11);
      witchHunt.get(10).right = witchHunt.get(12);
      
      chainEvents.add(witchHunt.get(0));
   }
   private void tutorial() throws Exception {
      ArrayList<Event> tutorial = new ArrayList<Event>();
      tutorial = loadEvents("E:\\All Computer Science Materials\\Java 240 Project\\PrinceFX\\src\\sample\\tutorial.txt");
      tutorial.get(0).left = tutorial.get(1);
      tutorial.get(1).left = tutorial.get(2);
      tutorial.get(2).left = tutorial.get(3);
      tutorial.get(3).left = tutorial.get(4);
      currentEvent = tutorial.get(0);
      
   }
   
   private void buildStatsMap() {

       stats.put("CLG_LOY", 50);
       stats.put("CLG_INF", 50);
       stats.put("NOB_LOY", 50);
       stats.put("NOB_INF", 50);
       stats.put("COM_LOY", 50);
       stats.put("COM_INF", 50);
       stats.put("WLTH", 100);
       stats.put("ARMY", 100);
       stats.put("HLTH", 100);
       stats.put("YEAR", 1066);
       stats.put("AGE", 20);

   }

   private void buildLossMap() throws Exception {
       ArrayList<Event> lossEventsStorage = new ArrayList<Event>();
       lossEventsStorage = loadEvents("E:\\All Computer Science Materials\\Java 240 Project\\PrinceFX\\src\\sample\\loss events.txt");
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
   public void newGame() throws Exception {
      stats.clear();
      buildStatsMap();
      chainEvents.clear();
      makeChains();
   }
}