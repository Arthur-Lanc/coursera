// write your code here
function crop(image, width, height) {
    var crop_image = new SimpleImage(width,height);
    
    for (var crop_pixel of crop_image.values()){
        x=crop_pixel.getX()
        y=crop_pixel.getY()
        pixel = image.getPixel(x,y)
        crop_pixel.setRed(pixel.getRed())
        crop_pixel.setGreen(pixel.getGreen())
        crop_pixel.setBlue(pixel.getBlue())
    }
    
    return crop_image
}

function pixchange (pix_number) {
    return Math.floor(pix_number/16)*16
}

function chop2hide(image) {
    for (var pixel of image.values()){
        pixel.setRed(pixchange(pixel.getRed()))
        pixel.setGreen(pixchange(pixel.getGreen()))
        pixel.setBlue(pixchange(pixel.getBlue()))
    }
    return image
}

function pixchange2 (pix_number) {
    return Math.floor(pix_number/16)
}

function shift(image) {
    for (var pixel of image.values()){
        pixel.setRed(pixchange2(pixel.getRed()))
        pixel.setGreen(pixchange2(pixel.getGreen()))
        pixel.setBlue(pixchange2(pixel.getBlue()))
    }
    return image
}

function newpv(p,q) {
    new_p = p + q
    if (new_p > 255) {
        print('error in newpv')
        return 255
    }
    return new_p
}

function combine(image1,image2) {
    var width = image1.getWidth()
    var height = image1.getHeight()
    var image3 = new SimpleImage(width,height);
    
    for (var pixel of image3.values()){
        x=pixel.getX()
        y=pixel.getY()
        
        pixel1 = image1.getPixel(x,y)
        pixel2 = image2.getPixel(x,y)
        
        pixel.setRed(newpv(pixel1.getRed(),pixel2.getRed()))
        pixel.setGreen(newpv(pixel1.getGreen(),pixel2.getGreen()))
        pixel.setBlue(newpv(pixel1.getBlue(),pixel2.getBlue()))
    }
    
    return image3
}


// var image = new SimpleImage("usain.jpg");
// print(image)
// var crop_image = crop(image, 200, 200)
// print(crop_image)


var start = new SimpleImage("usain.jpg");
var hide = new SimpleImage("skyline.jpg");

start = chop2hide(start);
hide = shift(hide);
var ans = combine(start,hide);
print(ans);

print(start.getPixel(50,60))
print(hide.getPixel(50,60))
print(ans.getPixel(50,60))

