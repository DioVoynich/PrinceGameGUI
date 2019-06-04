
package sample;
import java.util.*;
import java.io.*;

public class PrinceGame {

    public static void main(String[] args) throws Exception{
        Scanner in = new Scanner(System.in);
        PrintStream out = new PrintStream(System.out);

        menu(in, out);
    }

    public static void menu (Scanner in, PrintStream out) throws Exception{
        String console = new String();

        while(!console.equals("q")) {
            out.println("n for New Game");
            out.println("l for Load Game");
            out.println("q to Quit");
            console = in.next();

            if(console.equals("n")) {
                GameManager game = new GameManager();
                while (!game.isGameOver()) {


                    play(game, in, out);
                    //game.newGame();
                }
                printScreen(game.currentEvent, game, out);

            } else if(console.equals("l")) {
                //WIP
            }
        }
    }

    // Take in input from user (1-4)
    public static void play (GameManager game, Scanner in, PrintStream out) {
        //WIP
        
//        game.setEvent();
        Decision[] options = game.currentEvent.getDecs();
        printScreen(game.currentEvent, game, out);
        String input = in.next();
        int choice = Integer.parseInt(input);
        Effect[] update = options[choice - 1].getEffs(); // NOTE: NullPointerException if player chooses option that does't exist
        // for loop applying stats updates
        for (int i = 0; i < update.length; i++) {
            if (update[i] == null) { //rough handling of null exceptions (change later)
                break;
            }
            else {
                String faction = update[i].getTarget();
                int statChange = update[i].getValue();
                game.updateValue(faction, statChange);
            }
        }
        if (game.currentEvent.increasesYear()) {
            game.increaseYear();
        }
        //chooses next event for the next round
        game.nextEvent(choice);
        



    }

    public static void printScreen(Event event, GameManager game, PrintStream out) {
        out.println("Prince "+game.getName()+"\t\t\t\t\t\t\tThe Prince\t\t\t\t\t\tAD: "+game.getStats().get("YEAR"));
        out.println("Age: "+ game.getStats().get("AGE"));
        out.println("Wealth: "+game.getStats().get("WLTH")+"\t\t\t\tClergy:\t\t\t\tNobility:\t\t\tCommoners:");
        out.println("Army:   "+game.getStats().get("ARMY")+"\t\t\t\tLoy:"+game.getStats().get("CLG_LOY")+"\t\t\t\tLoy:"+game.getStats().get("NOB_LOY")+"\t\t\t\tLoy:"+game.getStats().get("COM_LOY"));
        out.println("Health: "+game.getStats().get("HLTH")+"\t\t\t\tInf:"+game.getStats().get("CLG_INF")+"\t\t\t\tInf:"+game.getStats().get("NOB_INF")+"\t\t\t\tInf:"+game.getStats().get("COM_INF"));
        //out.println("Wealth: "+game.getStats().get("WLTH"));
        // out.println("Army:   "+game.getStats().get("ARMY"));
        // out.println("Health: "+game.getStats().get("HLTH"));
        if(event!=null){

            printParagraph(event.getTxt(), out);
            for (int i = 0; i < 4; i++) {
                if (event.getDecs()[i] == null) {
                    break;
                }
                else {
                    out.println("\n\t\t\t\t\t"+(i+1)+"."+event.getDecs()[i].getTxt());
                }



            }
         /*
         out.println("\n\t\t\t\t\t1."+event.getDecs()[0].getTxt());
         out.println("\t\t\t\t\t2."+event.getDecs()[1].getTxt());
         out.println("\t\t\t\t\t3."+event.getDecs()[2].getTxt());
         out.println("\t\t\t\t\t4."+event.getDecs()[3].getTxt());
         */
        }
    }

    //takes a single stream and prints it out in paragraph form.
    private static void printParagraph(String string, PrintStream out) {
        String[] para = string.split(" ");
        int txtLength = 50;
        out.print("\t\t\t\t\t");
        for(int i = 0; i< para.length; i++) {
            if(para[i].length() > txtLength) {
                out.print("\n\t\t\t\t\t");
                txtLength = 50;
            }
            out.print(para[i]+" ");
            txtLength = txtLength - para[i].length();
        }
        out.print("\n");
    }

}