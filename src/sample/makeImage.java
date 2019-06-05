// This program creates an ArrayList to store all the images will be used in the game.

package sample;
import java.io.*;
import java.util.*;

public class makeImage {
    private ArrayList<String> storedImage;

    // This is the constructor.
    public makeImage() throws Exception{
        storedImage = new ArrayList<String>();
        File f =  new File("C:\\Users\\Public\\Documents\\PrinceFX\\src\\imageSet.txt");
        Scanner console = new Scanner(f);
        while(console.hasNext()) {
            String token = console.next();
            storedImage.add(token);
        }
    }

    // Returning the the image name.
    public String getImage(int value) {
        return storedImage.get(value);
    }

    // Returning the size of the ArrayList.
    public int getSize() {
        return storedImage.size();
    }
}