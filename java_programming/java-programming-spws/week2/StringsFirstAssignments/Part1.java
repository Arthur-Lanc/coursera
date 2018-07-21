
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part1 {
    public String findSimpleGene(String dna) {
        int start = dna.indexOf("ATG");
        if (start == -1) {
            return "";
        }
        int stop = dna.indexOf("TAA", start+3);
        if (stop == -1) {
            return "";
        }
        
        if ((stop - start) % 3 == 0) {
            return dna.substring(start, stop+3);
        }
        else {
            return "";
        }
    }
    
    public void testSimpleGene() {
        String dna_string1 = "AAAAATGTTTTGATAGG";
        String dna_string2 = "AAAAATATTTTGATAAG";
        String dna_string3 = "AAAAATATTTTGATACG";
        String dna_string4 = "AAAAATGTTTTGATAAG";
        String dna_string5 = "AAAAATGTTTTGACTAAG";
        String result = "";
            
        result = findSimpleGene(dna_string1);
        System.out.println("dna_string: " + dna_string1);
        System.out.println("result: " + result);
        
        result = findSimpleGene(dna_string2);
        System.out.println("dna_string: " + dna_string2);
        System.out.println("result: " + result);
        
        result = findSimpleGene(dna_string3);
        System.out.println("dna_string: " + dna_string3);
        System.out.println("result: " + result);
        
        result = findSimpleGene(dna_string4);
        System.out.println("dna_string: " + dna_string4);
        System.out.println("result: " + result);
        
        result = findSimpleGene(dna_string5);
        System.out.println("dna_string: " + dna_string5);
        System.out.println("result: " + result);
    }
}
