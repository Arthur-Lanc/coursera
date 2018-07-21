import java.util.*;
import edu.duke.*;
import java.io.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder slice_sb = new StringBuilder();
        StringBuilder message_sb = new StringBuilder(message);
        for (int idx = whichSlice; idx < message_sb.length(); idx+=totalSlices){
            slice_sb.append(message_sb.charAt(idx));
        }
        return slice_sb.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker ccracker = new CaesarCracker(mostCommon);
        for (int idx=0;idx<klength;idx++){
            String encrypted_slice_str = sliceString(encrypted,idx,klength);
            key[idx] = ccracker.getKey(encrypted_slice_str);
        }
        return key;
    }

    // public void breakVigenere() {
    //     FileResource fr = new FileResource();
    //     String contents = fr.asString();
    //     int[] key = tryKeyLength(contents,4,'e');
    //     VigenereCipher vcipher = new VigenereCipher(key);
    //     String decrypted_str= vcipher.decrypt(contents);
    //     //System.out.println(decrypted_str);
    //     System.out.println(decrypted_str.substring(0,300));
    // }

    // public void breakVigenere() {
    //     FileResource fr = new FileResource();
    //     String contents = fr.asString();

    //     FileResource fr2 = new FileResource("dictionaries/English");
    //     HashSet<String> dict_set = readDictionary(fr2);

    //     String best_decrypted_str = breakForLanguage(contents,dict_set);
    //     System.out.println(best_decrypted_str);
    //     // System.out.println(best_decrypted_str.substring(0,300));
    // }

    public void breakVigenere() {
        HashMap<String, HashSet<String>> languages = new HashMap<String, HashSet<String>>();
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            HashSet<String> dictionary = readDictionary(fr);
            languages.put(f.getName(),dictionary);
        }

        FileResource fr2 = new FileResource();
        String encrypted = fr2.asString();

        String best_decrypted = breakForAllLangs(encrypted,languages);
        System.out.println("best_decrypted: "+best_decrypted);
    }

    public HashSet<String> readDictionary(FileResource fr){
        HashSet<String> dict_set = new HashSet<String>();
        for (String line : fr.lines()) {
            String line_lc = line.toLowerCase();
            dict_set.add(line_lc);
        }
        return dict_set;
    }

    public int countWords(String message,HashSet<String> dictionary){
        int count_num = 0;
        String[] word_array = message.split("\\W+");
        for(String word_str: word_array){
            String word_lc = word_str.toLowerCase();
            if(dictionary.contains(word_lc)){
                count_num += 1;
            }
        }
        return count_num;
    }

    // public String breakForLanguage(String encrypted,HashSet<String> dictionary){
    //     String best_decrypted_str = "";
    //     int max_count_num = 0;
    //     int[] best_key = {9,9,9};
    //     for (int klength=1;klength<=100;klength++){
    //         int[] key = tryKeyLength(encrypted,klength,'e');
    //         VigenereCipher vcipher = new VigenereCipher(key);
    //         String decrypted_str= vcipher.decrypt(encrypted);
    //         int count_num = countWords(decrypted_str,dictionary);
    //         if (klength == 1){
    //             best_decrypted_str = decrypted_str;
    //             max_count_num = count_num;
    //             best_key = key;
    //         }
    //         else{
    //             if(count_num > max_count_num){
    //                 best_decrypted_str = decrypted_str;
    //                 max_count_num = count_num;
    //                 best_key = key;
    //             }
    //         }
    //     }
    //     System.out.println("max_count_num: "+max_count_num);
    //     System.out.println("key: "+Arrays.toString(best_key));
    //     System.out.println("key length: "+best_key.length);
    //     return best_decrypted_str;
    // }

    public String breakForLanguage(String encrypted,HashSet<String> dictionary){
        String best_decrypted_str = "";
        int max_count_num = 0;
        int[] best_key = {9,9,9};
        char most_comm_ch = mostCommonCharIn(dictionary);
        for (int klength=1;klength<=100;klength++){
            int[] key = tryKeyLength(encrypted,klength,most_comm_ch);
            VigenereCipher vcipher = new VigenereCipher(key);
            String decrypted_str= vcipher.decrypt(encrypted);
            int count_num = countWords(decrypted_str,dictionary);
            if (klength == 1){
                best_decrypted_str = decrypted_str;
                max_count_num = count_num;
                best_key = key;
            }
            else{
                if(count_num > max_count_num){
                    best_decrypted_str = decrypted_str;
                    max_count_num = count_num;
                    best_key = key;
                }
            }
        }
        System.out.println("max_count_num: "+max_count_num);
        System.out.println("key: "+Arrays.toString(best_key));
        System.out.println("key length: "+best_key.length);
        return best_decrypted_str;
    }

    public char mostCommonCharIn(HashSet<String> dictionary){
        HashMap<Character, Integer> char_number_map = new HashMap<Character, Integer>();
        String alphabet_str = "abcdefghijklmnopqrstuvwxyz";
        int max_count_num = 0;
        char max_commom_char = ' ';

        for (char letter_ch : alphabet_str.toCharArray()) {
            char_number_map.put(letter_ch, 0);
        }

        for (String word_str : dictionary) {
            for (char letter_ch2 : alphabet_str.toCharArray()) {
                if (word_str.indexOf(letter_ch2) != -1){
                    char_number_map.put(letter_ch2, char_number_map.get(letter_ch2)+1);
                }
            }
        }

        for (Character char_key : char_number_map.keySet()) {
            if (char_number_map.get(char_key) > max_count_num){
                max_count_num = char_number_map.get(char_key);
                max_commom_char = char_key;
            }
        }

        return max_commom_char;
    }

    public String breakForAllLangs(String encrypted,HashMap<String, HashSet<String>> languages){
        int max_count_num = 0;
        String best_decrypted = null;

        for (String lang_key : languages.keySet()) {
            HashSet<String> dictionary= languages.get(lang_key);
            String decrypted = breakForLanguage(encrypted,dictionary);
            int count_num = countWords(decrypted,dictionary);
            if(count_num > max_count_num){
                best_decrypted = decrypted;
                max_count_num = count_num;
            }
            System.out.println("language: "+lang_key);
            System.out.println("count_num: "+count_num);
            System.out.println(" ");
            // System.out.println("decrypted: "+decrypted);
        }

        return best_decrypted;
    }
    
}
