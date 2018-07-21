// write your code here
var img = new SimpleImage("smalllion.jpg");
print(img);

img_width=img.getWidth()
for (var pixel of img.values()) {
    p_red = pixel.getRed()
    p_green = pixel.getGreen()
    pixel.setRed(p_green)
    pixel.setGreen(p_red)
}
print(img);