
/**
 * Write a description of Part3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;

public class Part3 {
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

    public float cgRatio(String dna) {
        float dna_len=dna.length();
        int count = 0;

        for (char ch : dna.toCharArray()){
            if (ch == 'C' || ch == 'G'){
                count = count + 1;
            }
        }

        return count/dna_len;
    }


    public int countCTG(String dna) {
        int count = 0;
        String ctg_condon = "CTG";
        int last_index = 0;

        while(true){
            int curr_index = dna.indexOf(ctg_condon,last_index);

            if (curr_index == -1) {
                break;
            }

            count = count + 1;
            last_index = curr_index + ctg_condon.length();
        }

        return count;
    }


    public void processGenes(StorageResource sr) {
        int count1 = 0;
        int count2 = 0;
        int longes_len = 0;
        
        //System.out.println("dna_string: " + dna);
        //System.out.println("");

        // print all the Strings in sr that are longer than 9 characters
        // print the number of Strings in sr that are longer than 9 characters
        for (String s : sr.data()) {
            if (s.length() > 60) {
                System.out.println("result: " + s);
                count1 = count1 + 1;
            }
        }
        System.out.println("number of string longer than 60 ch: " + count1);
        System.out.println("");

        // print the Strings in sr whose C-G-ratio is higher than 0.35
        // print the number of strings in sr whose C-G-ratio is higher than 0.35
        for (String s : sr.data()) {
            if (cgRatio(s) > 0.35) {
                System.out.println("result: " + s);
                count2 = count2 + 1;
            }
        }
        System.out.println("number of string cgRatio higer than 0.35: " + count2);
        System.out.println("");

        // print the length of the longest gene in sr
        for (String s : sr.data()) {
            if (s.length() > longes_len) {
                longes_len = s.length();
            }
        }
        System.out.println("length of the longest gene in sr: " + longes_len);
        System.out.println("");

    }

    public void testOn(String dna) {
        float store = cgRatio(dna);
        
        System.out.println("dna_string: " + dna);
        System.out.println("result: " + store);
        System.out.println("");
        
    }

    public void testOn2(String dna) {
        int store = countCTG(dna);
        
        System.out.println("dna_string: " + dna);
        System.out.println("result: " + store);
        System.out.println("");
        
    }

    public void testOn3(String dna) {
        StorageResource store = getAllGenes(dna);
        
        System.out.println("------------------------------------------");
        System.out.println("------------------------------------------");
        System.out.println("dna_string: " + dna);
        System.out.println("genes_number: " + store.size());
        processGenes(store);
    }

    public void testOn4(String dna) {
        StorageResource store = getAllGenes(dna);
        
        System.out.println("------------------------------------------");
        System.out.println("------------------------------------------");
        System.out.println("dna_string: " + dna);
        System.out.println("genes_number: " + store.size());
        processGenes(store);
    }

    public void testcgRatio() {
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

    public void testcountCTG_old() {
        // String dna_string1 = "AAAAATGTTTTGATAGG";
        String dna_string1 = "AAAACTGATGTTTTCTGGGTCTGACG";
        String dna_string2 = "AAAAATATTTTGGTAAG";
        String dna_string3 = "AAAAATATTTTGGTACG";
        String dna_string4 = "AAAAATGTTTTGGTAAG";
        String dna_string5 = "AAAAATGTTTTGGCTAAG";
        // String dna_string6 = "AAAAATGTTTTGGTGAGGGTAA";
        String dna_string6 = "AAAAATGTTTTGGGTGAGGTAAAATGCCCTGATAATGGGGCCCTAGTTT";
        String dna_string7 = "";
        String result;
            
        testOn2(dna_string1);
        
        testOn2(dna_string2);
        
        testOn2(dna_string3);
        
        testOn2(dna_string4);
        
        testOn2(dna_string5);

        testOn2(dna_string6);

        testOn2(dna_string7);
    }

    public void testcountCTG() {
        // String dna_string1 = "AAAAATGTTTTGATAGG";
        String dna_string1 = "AAAACTGATGTTTTCTGGGTCTGACG";
        String dna_string2 = "AAAAATATTTTGGTAAG";
        String dna_string3 = "AAAAATATTTTGGTACG";
        String dna_string4 = "AAAAATGTTTTGGTAAG";
        String dna_string5 = "AAAAATGTTTTGGCTAAG";
        // String dna_string6 = "AAAAATGTTTTGGTGAGGGTAA";
        String dna_string6 = "AAAAATGTTTTGGGTGAGGTAAAATGCCCTGATAATGGGGCCCTAGTTT";
        String dna_string7 = "";
        String result;


        FileResource fr = new FileResource("GRch38dnapart.fa");
        String dna = fr.asString();
        dna = dna.toUpperCase();
        testOn2(dna);
    }


    public void testProcessGenes_old() {
        // String dna_string1 = "AAAAATGTTTTGATAGG";
        String dna_string1 = "AAAACTGATGTTTTCTGGGTCTGACG";
        String dna_string2 = "AAAAATATTTTGGTAAG";
        String dna_string3 = "AAAAATATTTTGGTACG";
        String dna_string4 = "AAAAATGTTTTGGTAAG";
        String dna_string5 = "AAAAATGTTTTGGCTAAG";
        // String dna_string6 = "AAAAATGTTTTGGTGAGGGTAA";
        String dna_string6 = "AAAAATGTTTTGGGTGAGGTAAAATGCCCTGATAATGGGGCCCTAGTTT";
        String dna_string7 = "";
        String result;
            
        testOn3(dna_string1);
        
        testOn3(dna_string2);
        
        testOn3(dna_string3);
        
        testOn3(dna_string4);
        
        testOn3(dna_string5);

        testOn3(dna_string6);

        testOn3(dna_string7);
    }

    public void testProcessGenes() {
        // String dna_string1 = "AAAAATGTTTTGATAGG";
        String dna_string1 = "AAAACTGATGTTTTCTGGGTCTGACG";
        String dna_string2 = "AAAAATATTTTGGTAAG";
        String dna_string3 = "AAAAATATTTTGGTACG";
        String dna_string4 = "AAAAATGTTTTGGTAAG";
        String dna_string5 = "AAAAATGTTTTGGCTAAG";
        // String dna_string6 = "AAAAATGTTTTGGTGAGGGTAA";
        String dna_string6 = "AAAAATGTTTTGGGTGAGGTAAAATGCCCTGATAATGGGGCCCTAGTTT";
        String dna_string7 = "";
        String result;
            
        FileResource fr = new FileResource("GRch38dnapart.fa");
        String dna = fr.asString();
        dna = dna.toUpperCase();
        testOn4(dna);
    }
}
