
/**
 * Write a description of TestCaesarCipher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;

public class TestCaesarCipher {
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

    public void simpleTests(){
        String encrypted;
        String decrypted1;
        String decrypted2;

        //FileResource fr = new FileResource();
        //String message = fr.asString();
        
        String message = "Can you imagine life WITHOUT the internet AND computers in your pocket?";
        
        CaesarCipher cc = new CaesarCipher(15);
        encrypted = cc.encrypt(message);
        System.out.println("encrypted = "+encrypted);
        decrypted1 = cc.decrypt(encrypted);
        System.out.println("decrypted1 = "+decrypted1);

        decrypted2 = breakCaesarCipher(encrypted);
        System.out.println("decrypted2 = "+decrypted2);
    }

    public String breakCaesarCipher (String input){
        // CaesarCipher cc = new CaesarCipher();
        int[] freqs = countLetters(input);
        int maxDex = maxIndex(freqs);
        int dkey = maxDex - 4;
        if (maxDex < 4){
            dkey = 26 -(4-maxDex);
        }
        System.out.println("dkey: "+dkey);

        CaesarCipher cc = new CaesarCipher(dkey);
        String message = cc.decrypt(input);
        return message;
    }
}
