// write your code here
var img1 = new SimpleImage("drewRobert.png");
var img2 = new SimpleImage("dinos.png");
var img3 = new SimpleImage(img1.getWidth(),img1.getHeight());
// print(img1);
// print(img2);
// print(img3);
for (var pixel of img1.values()) {
    if (pixel.getGreen() > (pixel.getRed()+pixel.getBlue())) {
        x=pixel.getX()
        y=pixel.getY()
        img3.setPixel(x,y,img2.getPixel(x,y))
    }
    else{
        img3.setPixel(pixel.getX(),pixel.getY(),pixel)
    }
}
print(img3);