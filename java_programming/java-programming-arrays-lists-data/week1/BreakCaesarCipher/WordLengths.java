
/**
 * Write a description of WordLengths here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.lang.*;

public class WordLengths {
    public void countWordLengths (FileResource resource,int[] counts){
        int arr_len = counts.length;

        for (String s : resource.words()) {
            int str_len = s.length();
            if (str_len == 0){
                counts[str_len] += 1;
            }
            else if (str_len == 1){
                if(Character.isLetter(s.charAt(0))){
                    counts[str_len] += 1;
                }
                else{
                    counts[str_len-1] += 1;
                }
            }
            else if (str_len > 1){
                if(Character.isLetter(s.charAt(0)) && Character.isLetter(s.charAt(str_len-1))){
                    if (str_len <= arr_len-1){
                        counts[str_len] += 1;
                    }
                    else{
                        counts[arr_len-1] += 1;
                    }
                }
                else if(Character.isLetter(s.charAt(0)) && !Character.isLetter(s.charAt(str_len-1))){
                    if (str_len-1 <= arr_len-1){
                        counts[str_len-1] += 1;
                    }
                    else{
                        counts[arr_len-1] += 1;
                    }
                }
                else if(!Character.isLetter(s.charAt(0)) && Character.isLetter(s.charAt(str_len-1))){
                    if (str_len-1 <= arr_len-1){
                        counts[str_len-1] += 1;
                    }
                    else{
                        counts[arr_len-1] += 1;
                    }
                }
                else if(!Character.isLetter(s.charAt(0)) && !Character.isLetter(s.charAt(str_len-1))){
                    if (str_len-2 <= arr_len-1){
                        counts[str_len-2] += 1;
                    }
                    else{
                        counts[arr_len-1] += 1;
                    }
                }
            }
        }
    }


    public void testCountWordLengths(){
        FileResource fr = new FileResource();
        int[] counts = new int[31];
        countWordLengths(fr,counts);
        for(int k=0; k < counts.length; k++){
            System.out.println("length "+k+" : "+counts[k]+" words");
        }

        int max_index = indexOfMax(counts);
        System.out.println("max_index: "+max_index);
    }

    public int indexOfMax (int[] values){
        int max_index = 0;

        for(int k=0; k < values.length; k++){
            if (values[k] > values[max_index]) {
                max_index = k;
            }
        }

        return max_index;
    } 

}
