package OOP.FinalExam.t1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class to manage and process two lists (list1 and list2) to generate a third list (list3)
 * according to specific rules, including error handling for out-of-bounds indices.
 * 
 * <p><b>Algorithm:</b></p>
 * <ol>
 *   <li><b>Step 1:</b> For each element in list1 (integers), use its value (+1) as an index to fetch 
 *       a corresponding element from list2 (strings). Combine the fetched string with the original 
 *       integer and store the result in list3.</li>
 *   <li><b>Error Handling:</b> If the calculated index is out of bounds for list2, skip the element 
 *       and log a warning.</li>
 *   <li><b>Step 2:</b> Remove elements from list3 where the indices match values in list1. 
 *       Indices are processed in descending order to avoid shifting issues.</li>
 * </ol>
 * 
 * @author Elizaveta Darsalia
 * @version 3.0
 */
public class ListManager {
    /**
     * Main method that executes the list management process.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Initialize list1 with predefined values as shown in the animation
        List<Integer> list1 = new ArrayList<>();
        list1.add(6);
        list1.add(1);
        list1.add(8);
        list1.add(7);
        list1.add(3);
        list1.add(5);
        list1.add(9);
        list1.add(10);
        list1.add(4);
        list1.add(2);

        System.out.println("List1: " + list1);
         
        // Initialize list2 with predefined values as shown in the animation
        List<String> list2 = new ArrayList<>();
        list2.add("axg");
        list2.add("djp");
        list2.add("wge");
        list2.add("knr");
        list2.add("ceh");
        list2.add("idc");
        list2.add("czu");
        list2.add("lvm");
        list2.add("cqh");
        list2.add("znm");
        list2.add("esf");
        list2.add("eri");
        System.out.println("List2: " + list2);

        // Create list3 to store the results
        List<String> list3 = new ArrayList<>();
        
        // Step 1: Process list1 and list2 to create list3
        for (int i = 0; i < list1.size(); i++) {
            int indexFromList1 = list1.get(i);
            // Calculate index for list2 (value + 1)
            int list2Index = indexFromList1 + 1;
            
            try {
                // Get corresponding element from list2
                String list2Element = list2.get(list2Index);
                // Create combined string and add to list3
                String combined = list2Element + indexFromList1;
                list3.add(combined);
            } catch (IndexOutOfBoundsException e) {
                // Skip this element if index is out of bounds
                System.out.println("Skipping element " + indexFromList1 + 
                                 " at position " + i + 
                                 ": index " + list2Index + 
                                 " is out of bounds for list2");
            }
        }
        
        // Display list3 after step 1
        System.out.println("List3 after step 1: " + list3);
        
       // Step 2: Remove elements from list3 where list1 value equals an index in list3
       // First collect all valid indexes to remove
        Set<Integer> indexesToRemove = new HashSet<>();
        for (int value : list1) {
            if (value >= 0 && value < list3.size()) {
                indexesToRemove.add(value);
            }
        }

        // Convert to list and sort in descending order
        List<Integer> sortedIndexes = new ArrayList<>(indexesToRemove);
        sortedIndexes.sort(Collections.reverseOrder());

        // Remove from highest index to lowest to avoid index shifting issues
        for (int index : sortedIndexes) {
            list3.remove(index);
        }
        
        // Display final list3
        System.out.println("Final list3: " + list3);
    }
}

