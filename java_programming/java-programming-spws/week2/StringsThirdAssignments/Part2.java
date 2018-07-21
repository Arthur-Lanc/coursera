/**
 * Write a description of Part2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part2 {
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
            
        testOn2(dna_string1);
        
        testOn2(dna_string2);
        
        testOn2(dna_string3);
        
        testOn2(dna_string4);
        
        testOn2(dna_string5);

        testOn2(dna_string6);

        testOn2(dna_string7);
    }
}
