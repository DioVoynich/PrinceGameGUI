// This program creates an ArrayList for all the audios playing in the game.

package sample;
import java.io.*;
import java.util.*;

public class createAudio {
    private ArrayList<String> storedSound;

    // This is the constructor.
    public createAudio() throws Exception{
        storedSound = new ArrayList<String>();
        File f =  new File("E:\\All Computer Science Materials\\Java " +
                "240 Project\\PrinceFX\\src\\soundSet.txt");
        Scanner console = new Scanner(f);
        while(console.hasNext()) {
            String token = console.next();
            storedSound.add(token);
        }
    }

    // Returning the the song name.
    public String getSong(int value) {
        return storedSound.get(value);
    }

    // Returning the size of the ArrayList.
    public int getSize() {
        return storedSound.size();
    }
}
