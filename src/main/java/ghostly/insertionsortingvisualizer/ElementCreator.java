package ghostly.insertionsortingvisualizer;

import java.util.ArrayList;
import java.util.Random;

public class ElementCreator {
    // Initialise an empty array list
    private ArrayList<Integer> unsortedArray = new ArrayList<>();

    // Initialise a "random" variable
    private Random rand = new Random();

    // Generate the array with random integers
    public void GenerateElements(int numberOfDesiredElements){
        unsortedArray.clear();
        for (int i = 0; i < numberOfDesiredElements; i++){
            int generatedNumber = rand.nextInt(10, 300);
            unsortedArray.add(generatedNumber);
        }
    }

    // Give any functions that calls this function the unsorted version of the array
    public ArrayList<Integer> getElements(){
        return unsortedArray;
    }

    // Update the array values
    public ArrayList<Integer> setElements(ArrayList<Integer> newArray){
        unsortedArray = newArray;
        return unsortedArray;
    }
}
