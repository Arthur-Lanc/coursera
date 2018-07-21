
/**
 * Write a description of BatchInversions here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class BatchInversions {
    public ImageResource makeInversion (ImageResource ir1){
        ImageResource ir2 = new ImageResource(ir1.getWidth(),ir1.getHeight());
        for (Pixel p2 : ir2.pixels()){
            Pixel p1 = ir1.getPixel(p2.getX(), p2.getY());
            p2.setRed(255-p1.getRed());
            p2.setGreen(255-p1.getGreen());
            p2.setBlue(255-p1.getBlue());
        }
        return ir2;
    }

    public void selectAndConvert(){
        DirectoryResource dr = new DirectoryResource();

        for (File f : dr.selectedFiles()) {
            ImageResource ir1 = new ImageResource(f);
            ImageResource ir2 = makeInversion(ir1);
            
            String fname = ir1.getFileName();
            String newName = "inverted-"+fname;
            ir2.setFileName(newName);
            ir2.draw();
            ir2.save();
        }
    }
}
