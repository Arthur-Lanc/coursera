
/**
 * Write a description of CaesarBreaker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;

public class CaesarBreaker {
    public int[] countLetters(String message){
        String alph = "abcdefghijklmnopqrstuvwxyz";
        int[] counts = new int[26];
        for (int k=0;k<message.length();k++){
            char ch = Character.toLowerCase(message.charAt(k));
            int dex = alph.indexOf(ch);
            if (dex != -1){
                counts[dex] += 1;
            }
        }
        return counts;
    }

    public int maxIndex(int[] vals){
        int maxDex = 0;
        for(int k=0;k<vals.length;k++){
            if(vals[k]>vals[maxDex]){
                maxDex = k;
            }
        }
        return maxDex;
    }

    public String decrypt(String encrypted){
        CaesarCipher cc = new CaesarCipher();
        int[] freqs = countLetters(encrypted);
        int maxDex = maxIndex(freqs);
        int dkey = maxDex - 4;
        if (maxDex < 4){
            dkey = 26 -(4-maxDex);
        }
        System.out.println("dkey: "+dkey);
        String message = cc.encrypt(encrypted,26-dkey);
        return message;
    }

    public void testDecrypt(){
        int key = 1;
        FileResource fr = new FileResource();
        String message = fr.asString();
        CaesarCipher cc = new CaesarCipher();
        String encrypted = cc.encrypt(message, key);

        String d_message = decrypt(encrypted);
        System.out.println("d_message: "+d_message);
    }

    public String halfOfString(String message,int start){
        StringBuilder sb = new StringBuilder();

        for (int k=0;k<message.length();k++){
            if ((k >= start) && (k%2 == start%2)){
                char ch = message.charAt(k);
                sb.append(ch);
            }
        }

        return sb.toString();
    }

    public void testhalfOfString() {
        String result;

        result = halfOfString("Qbkm Zgis", 0);
        System.out.println("result: "+result);

        result = halfOfString("Qbkm Zgis", 1);
        System.out.println("result: "+result);
    }

    public int getKey(String s){
        int[] freqs = countLetters(s);
        int maxDex = maxIndex(freqs);

        int dkey = maxDex - 4;
        if (maxDex < 4){
            dkey = 26 -(4-maxDex);
        }

        return dkey;
    }

    public void decryptTwoKeys(String encrypted){
        String encrypted_p1;
        String encrypted_p2;
        encrypted_p1 = halfOfString(encrypted, 0);
        encrypted_p2 = halfOfString(encrypted, 1);

        int dkey1 = getKey(encrypted_p1);
        int dkey2 = getKey(encrypted_p2);
        System.out.println("dkey1: "+dkey1);
        System.out.println("dkey2: "+dkey2);

        CaesarCipher cc = new CaesarCipher();
        String message = cc.encryptTwoKeys (encrypted,26-dkey1,26-dkey2);
        System.out.println("message: "+message);
    }

    public void testdecryptTwoKeys(){
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        decryptTwoKeys(encrypted);
        
        //decryptTwoKeys("Akag tjw Xibhr awoa aoee xakex znxag xwko");
    }
}
