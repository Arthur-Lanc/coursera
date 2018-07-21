
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.StorageResource;

public class Part1 {
    public int findStopCodon(String dna,int startIndex,String stopCodon) {
        String startCodon = "ATG";
        String dna_lowercase = dna.toLowerCase();
        String startCodon_lowercase = startCodon.toLowerCase();
        String stopCodon_lowercase = stopCodon.toLowerCase();
        int start = dna_lowercase.indexOf(startCodon_lowercase,startIndex);
        
        if (start == -1) {
            return dna.length();
        }

        int curr_idx = start+3;

        while (true) {
            int stop = dna_lowercase.indexOf(stopCodon_lowercase,curr_idx);

            if (stop == -1) {
                return dna.length();
            }
            
            if ((stop - start) % 3 == 0) {
                return stop;
            }
            else {
                curr_idx = stop + 1;
            }
        }
    }

    public String findGene(String dna,int where) {
        String startCodon = "ATG";
        int start = dna.indexOf(startCodon,where);

        if (start == -1) {
            return "";
        }

        int taa_stop_index = findStopCodon(dna,start,"TAA");
        int tag_stop_index = findStopCodon(dna,start,"TAG");
        int tga_stop_index = findStopCodon(dna,start,"TGA");

        int min_index_temp1 = Math.min(taa_stop_index,tag_stop_index);
        int min_index = Math.min(min_index_temp1,tga_stop_index);

        if (min_index == dna.length()) {
            return "";
        }

        return dna.substring(start,min_index+3);
    }
     
    
    public void printAllGenes(String dna) {
        String result;
        int where = 0;

        System.out.println("dna_string: " + dna);

        while(true) {
            result = findGene(dna,where);

            if (result.isEmpty()) {
                break;
            }

            where = dna.indexOf(result,where) + result.length();
            System.out.println("result: " + result);    
        }
    }

    public StorageResource getAllGenes(String dna) {
        String result;
        int where = 0;
        StorageResource store = new StorageResource(); 

        //System.out.println("dna_string: " + dna);

        while(true) {
            result = findGene(dna,where);

            if (result.isEmpty()) {
                break;
            }

            where = dna.indexOf(result,where) + result.length();
            //System.out.println("result: " + result);    
            store.add(result);
        }

        return store;
    }
    
    public void testFindStopCodon() {
        // String dna_string1 = "AAAAATGTTTTGATAGG";
        String dna_string1 = "";
        String dna_string2 = "AAAAATATTTTGATAAG";
        String dna_string3 = "AAAAATATTTTGATACG";
        String dna_string4 = "AAAAATGTTTTGATAAG";
        String dna_string5 = "AAAAATGTTTTGACTAAG";
        String dna_string6 = "ATGGGTTAAGTC";
        String dna_string7 = "gatgctataat";
        int result;
        int startIndex = 0;
        String stopCodon = "TAA";
            
        result = findStopCodon(dna_string1,startIndex,stopCodon);
        System.out.println("dna_string: " + dna_string1);
        System.out.println("result: " + result);
        
        result = findStopCodon(dna_string2,startIndex,stopCodon);
        System.out.println("dna_string: " + dna_string2);
        System.out.println("result: " + result);
        
        result = findStopCodon(dna_string3,startIndex,stopCodon);
        System.out.println("dna_string: " + dna_string3);
        System.out.println("result: " + result);
        
        result = findStopCodon(dna_string4,startIndex,stopCodon);
        System.out.println("dna_string: " + dna_string4);
        System.out.println("result: " + result);
        
        result = findStopCodon(dna_string5,startIndex,stopCodon);
        System.out.println("dna_string: " + dna_string5);
        System.out.println("result: " + result);

        result = findStopCodon(dna_string6,startIndex,stopCodon);
        System.out.println("dna_string: " + dna_string6);
        System.out.println("result: " + result);

        result = findStopCodon(dna_string7,startIndex,stopCodon);
        System.out.println("dna_string: " + dna_string7);
        System.out.println("result: " + result);
    }

    public void testPrintAllGenes() {
        // String dna_string1 = "AAAAATGTTTTGATAGG";
        String dna_string1 = "AAAAATGTTTTGGTACG";
        String dna_string2 = "AAAAATATTTTGGTAAG";
        String dna_string3 = "AAAAATATTTTGGTACG";
        String dna_string4 = "AAAAATGTTTTGGTAAG";
        String dna_string5 = "AAAAATGTTTTGGCTAAG";
        // String dna_string6 = "AAAAATGTTTTGGTGAGGGTAA";
        String dna_string6 = "AAAAATGTTTTGGGTGAGGTAAAATGCCCTGATAATGGGGCCCTAGTTT";
        String dna_string7 = "";
        String result;
            
        printAllGenes(dna_string1);
        
        printAllGenes(dna_string2);
        
        printAllGenes(dna_string3);
        
        printAllGenes(dna_string4);
        
        printAllGenes(dna_string5);

        printAllGenes(dna_string6);

        printAllGenes(dna_string7);
    }

    public void testOn(String dna) {
        StorageResource store = getAllGenes(dna);
        
        System.out.println("dna_string: " + dna);
        for (String s : store.data()) {
            System.out.println("result: " + s);
        }
    }

    public void testGetAllGenes() {
        // String dna_string1 = "AAAAATGTTTTGATAGG";
        String dna_string1 = "AAAAATGTTTTGGTACG";
        String dna_string2 = "AAAAATATTTTGGTAAG";
        String dna_string3 = "AAAAATATTTTGGTACG";
        String dna_string4 = "AAAAATGTTTTGGTAAG";
        String dna_string5 = "AAAAATGTTTTGGCTAAG";
        // String dna_string6 = "AAAAATGTTTTGGTGAGGGTAA";
        String dna_string6 = "AAAAATGTTTTGGGTGAGGTAAAATGCCCTGATAATGGGGCCCTAGTTT";
        String dna_string7 = "";
        String result;
            
        testOn(dna_string1);
        
        testOn(dna_string2);
        
        testOn(dna_string3);
        
        testOn(dna_string4);
        
        testOn(dna_string5);

        testOn(dna_string6);

        testOn(dna_string7);
    }

}
