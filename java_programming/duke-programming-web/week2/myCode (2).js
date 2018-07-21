// write your code here
var img = new SimpleImage("duke_blue_devil.png");
print(img);

img_width=img.getWidth()
for (var pixel of img.values()) {
    if (pixel.getRed() != 255 && pixel.getGreen() != 255 && pixel.getBlue() != 255) {
        pixel.setRed(255)
        pixel.setGreen(255)
        pixel.setBlue(0)
    }
}
print(img);