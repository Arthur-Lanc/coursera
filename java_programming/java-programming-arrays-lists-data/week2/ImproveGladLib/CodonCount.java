
/**
 * Write a description of CodonCount here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;

public class CodonCount {
    private HashMap<String,Integer> codon_count_hm;

    public CodonCount(){
        codon_count_hm = new HashMap<String,Integer>();        
    }

    private void buildCodonMap(int start,String dna){
        codon_count_hm.clear();        

        while((start+3) <= dna.length()){
            String codon = dna.substring(start,start+3);
            if(codon_count_hm.containsKey(codon)){
                codon_count_hm.put(codon,codon_count_hm.get(codon)+1);
            }
            else{
                codon_count_hm.put(codon,1);    
            }
            start = start + 3;            
        }
    }

    private String getMostCommonCodon(){
        int max_count = 0;
        String most_common_codon = "";

        for (String s : codon_count_hm.keySet()) {
            int count = codon_count_hm.get(s);
            if(count > max_count){
                max_count = count;
                most_common_codon = s;
            }
        } 
        return most_common_codon;
    }

    private void printCodonCounts(int start,int end){
        for(String s : codon_count_hm.keySet()){
            int count = codon_count_hm.get(s);
            if(count >= start && count <= end){
                System.out.println(s+" "+count);
            }
        }
    }

    public void tester (){
        FileResource fr = new FileResource();
        String dna = fr.asString();
        dna = dna.toUpperCase();
        dna = dna.trim();

        int pcc_start = 7;
        int pcc_end = 7;

        for(int start=0;start <3;start++){
            buildCodonMap(start,dna);            
            System.out.println("Reading frame starting with "+start+" results in "+codon_count_hm.size()+" unique codons");

            String most_common_codon = getMostCommonCodon();
            int count = codon_count_hm.get(most_common_codon);
            System.out.println("and most common codon is "+most_common_codon+" with count "+count);

            System.out.println("Counts of codons between "+pcc_start+" and "+pcc_end+" inclusive are:");
            printCodonCounts(pcc_start,pcc_end);
            System.out.println("");
        }
    }


}
