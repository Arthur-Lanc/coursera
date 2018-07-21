// write your code here
var img = new SimpleImage("hilton.jpg");
print(img);

img_width=img.getWidth()
for (var pixel of img.values()) {
    if (pixel.getX() >= 0 && pixel.getX() < img_width/3) {
        pixel.setRed(255)
    }
    else if (pixel.getX() >= img_width/3 && pixel.getX() < 2*img_width/3) {
        pixel.setGreen(255)
    }
    else if (pixel.getX() >= 2*img_width/3 && pixel.getX() < img_width) {
        pixel.setBlue(255)
    }
}
print(img);