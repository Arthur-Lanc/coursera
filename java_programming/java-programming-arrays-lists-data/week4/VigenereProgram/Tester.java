import java.util.*;
import edu.duke.*;
import java.io.*;

public class Tester {
    public void testEncryptLetter(){
        CaesarCipher cc = new CaesarCipher(2);
        char result_ch = cc.encryptLetter('f');
        System.out.println(result_ch);
    }

    public void testDdecryptLetter(){
        CaesarCipher cc = new CaesarCipher(2);
        char result_ch = cc.decryptLetter('h');
        System.out.println(result_ch);
    }

    public void testEncrypt(){
        CaesarCipher cc = new CaesarCipher(2);
        FileResource fr = new FileResource("titus-small.txt");
        String contents = fr.asString();
        String result_str = cc.encrypt(contents);
        System.out.println(result_str);
    }

    public void testDecrypt(){
        CaesarCipher cc = new CaesarCipher(2);
        FileResource fr = new FileResource("titus-small.txt");
        String contents = fr.asString();
        String result_str = cc.encrypt(contents);
        String result_str2 = cc.decrypt(result_str);
        System.out.println(result_str2);
    }

    public void testDecryptInCCracker(){
        //CaesarCracker ccracker = new CaesarCracker();
        //FileResource fr = new FileResource("titus-small_key5.txt");
        //String contents = fr.asString();
        //String result_str = ccracker.decrypt(contents);
        //System.out.println(result_str);

        CaesarCracker ccracker = new CaesarCracker('a');
        FileResource fr = new FileResource("oslusiadas_key17.txt");
        String contents = fr.asString();
        String result_str = ccracker.decrypt(contents);
        System.out.println(result_str);
    }

    public void testEncryptAndDecryptInVCipher(){
        int[] key = {17, 14, 12, 4};
        VigenereCipher VCipher= new VigenereCipher(key);
        FileResource fr = new FileResource("titus-small.txt");
        String contents = fr.asString();
        String encrypted_str = VCipher.encrypt(contents);
        System.out.println(encrypted_str);
        System.out.println("-----------------------------------");
        String decrypted_str = VCipher.decrypt(encrypted_str);
        System.out.println(decrypted_str);
    }

    public void testSliceString(){
        String result_str;
        VigenereBreaker vbreaker = new VigenereBreaker();

        result_str = vbreaker.sliceString("abcdefghijklm", 0, 3);
        System.out.println(result_str);
        result_str = vbreaker.sliceString("abcdefghijklm", 1, 3);
        System.out.println(result_str);
        result_str = vbreaker.sliceString("abcdefghijklm", 2, 3);
        System.out.println(result_str);
        result_str = vbreaker.sliceString("abcdefghijklm", 0, 4);
        System.out.println(result_str);
        result_str = vbreaker.sliceString("abcdefghijklm", 1, 4);
        System.out.println(result_str);
        result_str = vbreaker.sliceString("abcdefghijklm", 2, 4);
        System.out.println(result_str);
        result_str = vbreaker.sliceString("abcdefghijklm", 3, 4);
        System.out.println(result_str);
        result_str = vbreaker.sliceString("abcdefghijklm", 0, 5);
        System.out.println(result_str);
        result_str = vbreaker.sliceString("abcdefghijklm", 1, 5);
        System.out.println(result_str);
        result_str = vbreaker.sliceString("abcdefghijklm", 2, 5);
        System.out.println(result_str);
        result_str = vbreaker.sliceString("abcdefghijklm", 3, 5);
        System.out.println(result_str);
        result_str = vbreaker.sliceString("abcdefghijklm", 4, 5);
        System.out.println(result_str);
    }

    public void testTryKeyLength(){
        // VigenereBreaker vbreaker = new VigenereBreaker();
        // FileResource fr = new FileResource("secretmessage1.txt");
        // String contents = fr.asString();
        // int[] key = vbreaker.tryKeyLength(contents,4,'e');
        // System.out.println(Arrays.toString(key));

        VigenereBreaker vbreaker = new VigenereBreaker();
        
        FileResource fr = new FileResource("secretmessage2.txt");
        String contents = fr.asString();
        int[] key = vbreaker.tryKeyLength(contents,38,'e');

        VigenereCipher vcipher = new VigenereCipher(key);
        String decrypted_str= vcipher.decrypt(contents);

        FileResource fr2 = new FileResource("dictionaries/English");
        HashSet<String> dictionary = vbreaker.readDictionary(fr2);

        int count_num = vbreaker.countWords(decrypted_str,dictionary);
        System.out.println("count_num: "+count_num);
    }

    public void testBreakVigenere(){
        VigenereBreaker vbreaker = new VigenereBreaker();
        vbreaker.breakVigenere();
    }

    public void testReadDictionary(){
        VigenereBreaker vbreaker = new VigenereBreaker();
        FileResource fr = new FileResource("dictionaries/English");
        HashSet<String> dict_set = vbreaker.readDictionary(fr);
        System.out.println(dict_set.contains("schoolmasters"));
        System.out.println(dict_set.contains("schoolmastersd"));
    }

    public void testCountWords(){
        VigenereBreaker vbreaker = new VigenereBreaker();
        FileResource fr = new FileResource("dictionaries/English");
        HashSet<String> dict_set = vbreaker.readDictionary(fr);

        FileResource fr2 = new FileResource("titus-small.txt");
        String contents = fr2.asString();

        int count_num = vbreaker.countWords(contents,dict_set);
        System.out.println(count_num);
    }

    public void testBreakForLanguage(){
        VigenereBreaker vbreaker = new VigenereBreaker();
        FileResource fr = new FileResource("dictionaries/English");
        HashSet<String> dict_set = vbreaker.readDictionary(fr);

        FileResource fr2 = new FileResource("secretmessage1.txt");
        String contents = fr2.asString();

        String best_decrypted_str = vbreaker.breakForLanguage(contents,dict_set);
        System.out.println(best_decrypted_str);
        //System.out.println(best_decrypted_str.substring(0,300));
    }

    public void testMostCommonCharIn(){
        VigenereBreaker vbreaker = new VigenereBreaker();
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            HashSet<String> dictionary = vbreaker.readDictionary(fr);
            char most_common_char = vbreaker.mostCommonCharIn(dictionary);
            System.out.println(f.getName()+": "+most_common_char);
        }
    }

    public void testBreakForAllLangs(){
        VigenereBreaker vbreaker = new VigenereBreaker();
        HashMap<String, HashSet<String>> languages = new HashMap<String, HashSet<String>>();
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            HashSet<String> dictionary = vbreaker.readDictionary(fr);
            languages.put(f.getName(),dictionary);
        }

        FileResource fr2 = new FileResource("secretmessage1.txt");
        String encrypted = fr2.asString();

        String best_decrypted = vbreaker.breakForAllLangs(encrypted,languages);
        System.out.println("best_decrypted: "+best_decrypted);
    }
}