function setBlack(pixel) {
    pixel.setRed(0)
    pixel.setGreen(0)
    pixel.setBlue(0)
    return pixel
}

function addBorder(image,thickness) {
    for (var pixel of image.values()){
        x=pixel.getX()
        y=pixel.getY()
        if(x <= thickness || x >= image.getWidth()-thickness){
            pixel=setBlack(pixel)
        }
        if(y <= thickness || y >= image.getHeight()-thickness){
            pixel=setBlack(pixel)
        }
    }
    return image
}

var img = new SimpleImage("skyline.jpg");
var thickness = 20
print(img)
print(addBorder(img,thickness))