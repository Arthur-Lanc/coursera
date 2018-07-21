
/**
 * Write a description of Part2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part2 {
    public String findSimpleGene(String dna,String startCodon,String stopCodon) {
        String dna_lowercase = dna.toLowerCase();
        String startCodon_lowercase = startCodon.toLowerCase();
        String stopCodon_lowercase = stopCodon.toLowerCase();
        
        int start = dna_lowercase.indexOf(startCodon_lowercase);
        if (start == -1) {
            return "";
        }
        int stop = dna_lowercase.indexOf(stopCodon_lowercase, start+3);
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
        String dna_string6 = "ATGGGTTAAGTC";
        String dna_string7 = "gatgctataat";
        String result = "";
        String startCodon = "ATG";
        String stopCodon = "TAA";
            
        result = findSimpleGene(dna_string1,startCodon,stopCodon);
        System.out.println("dna_string: " + dna_string1);
        System.out.println("result: " + result);
        
        result = findSimpleGene(dna_string2,startCodon,stopCodon);
        System.out.println("dna_string: " + dna_string2);
        System.out.println("result: " + result);
        
        result = findSimpleGene(dna_string3,startCodon,stopCodon);
        System.out.println("dna_string: " + dna_string3);
        System.out.println("result: " + result);
        
        result = findSimpleGene(dna_string4,startCodon,stopCodon);
        System.out.println("dna_string: " + dna_string4);
        System.out.println("result: " + result);
        
        result = findSimpleGene(dna_string5,startCodon,stopCodon);
        System.out.println("dna_string: " + dna_string5);
        System.out.println("result: " + result);

        result = findSimpleGene(dna_string6,startCodon,stopCodon);
        System.out.println("dna_string: " + dna_string6);
        System.out.println("result: " + result);

        result = findSimpleGene(dna_string7,startCodon,stopCodon);
        System.out.println("dna_string: " + dna_string7);
        System.out.println("result: " + result);
    }
}
