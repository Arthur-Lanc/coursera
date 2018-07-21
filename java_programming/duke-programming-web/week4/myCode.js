var image = null;
var image2 = null;
var image3 = null;
var image4 = null;
var image5 = null;
var image6 = null;
var canvas = null;

function uploadFile(){
  canvas = document.getElementById("can");
  var fileinput = document.getElementById("finput");
  image = new SimpleImage(fileinput);
  image2 = new SimpleImage(fileinput);
  image3 = new SimpleImage(fileinput);
  image4 = new SimpleImage(fileinput);
  image5 = new SimpleImage(fileinput);
  image6 = new SimpleImage(fileinput);
  image.drawTo(canvas); 
}

function createComposite(){ 
  if (image == null || ! image.complete()) {
    alert('Foreground image has not been loaded.')
  }
  if (image2 == null || ! image2.complete()) {
    alert('Background image has not been loaded.')
  }
  
  var canvas = document.getElementById("can");
  var img3 = new SimpleImage(image.getWidth(),image.getHeight());
  
  image2.setSize(image.getWidth(), image.getHeight());
  
  for (var pixel of image.values()) {
      if (pixel.getGreen() > (pixel.getRed()+pixel.getBlue())) {
          x=pixel.getX()
          y=pixel.getY()
          img3.setPixel(x,y,image2.getPixel(x,y))
      }
      else{
          img3.setPixel(pixel.getX(),pixel.getY(),pixel)
      }
  }
  
  clearCanvas();
  img3.drawTo(canvas); 
}

function clearCanvas(){ 
  var context=canvas.getContext("2d");
  context.clearRect(0,0,canvas.width,canvas.height);
}

function doGray(){
  if (imageIsLoaded(image2)){
    clearCanvas();
    for (var pixel of image2.values()){
      var avg = (pixel.getRed()+pixel.getGreen()+pixel.getBlue())/3
      pixel.setRed(avg)
      pixel.setGreen(avg)
      pixel.setBlue(avg)
    }
    image2.drawTo(canvas); 
  }
}

function doRed(){
  if (imageIsLoaded(image3)){
    clearCanvas();
    for (var pixel of image3.values()){
      var avg = (pixel.getRed()+pixel.getGreen()+pixel.getBlue())/3;
      if (avg < 128) {
        pixel.setRed(2*avg);
        pixel.setGreen(0);
        pixel.setBlue(0);
      } else {
        pixel.setRed(255);
        pixel.setGreen(2*avg-255);
        pixel.setBlue(2*avg-255);
      }
    }
    image3.drawTo(canvas); 
  }
}

function doDesign(){
  if (imageIsLoaded(image4)){
    clearCanvas();
    var width = canvas.width;
    var height = canvas.height;
    for (var pixel of image4.values()){
      var avg = (pixel.getRed()+pixel.getGreen()+pixel.getBlue())/3;
      if (pixel.getY() <= height/10*Math.sin(pixel.getX() * Math.PI / (width/10))+(height*1/10)) {
        pixel.setRed(0);
        pixel.setGreen(0);
        pixel.setBlue(0);
      }
      else if (pixel.getY() >= height/10*Math.cos(pixel.getX() * Math.PI / (width/10))+(height*9/10)) {
        pixel.setRed(0);
        pixel.setGreen(0);
        pixel.setBlue(0);
      }
      else {
      pixel.setRed(avg);
      pixel.setGreen(avg);
      pixel.setBlue(avg);
      }
    }
    image4.drawTo(canvas); 
  }
}


function doRainbow(){
  if (imageIsLoaded(image5)){
    clearCanvas();
    var width = canvas.width;
    var height = canvas.height;
    
    for (var pixel of image5.values()){
      var avg = (pixel.getRed()+pixel.getGreen()+pixel.getBlue())/3;
      if (pixel.getY() < height*1/7) {
        // if (avg < 128) {
        //   pixel.setRed(102/127.5*avg);
        //   pixel.setGreen(102/127.5*avg);
        //   pixel.setBlue(153/127.5*avg);
        // } else {
        //   pixel.setRed((2-102/127.5)*avg+2*102-255);
        //   pixel.setGreen((2-102/127.5)*avg+2*102-255);
        //   pixel.setBlue((2-153/127.5)*avg+2*153-255);
        // }
        if (avg < 128) {
          pixel.setRed(2*avg);
          pixel.setGreen(0);
          pixel.setBlue(0);
        } else {
          pixel.setRed(255);
          pixel.setGreen(2*avg-255);
          pixel.setBlue(2*avg-255);
        }
      } else if (pixel.getY() < height*2/7) {
        if (avg < 128) {
          pixel.setRed(2*avg);
          pixel.setGreen(0.8*avg);
          pixel.setBlue(0);
        } else {
          pixel.setRed(255);
          pixel.setGreen(1.2*avg-51);
          pixel.setBlue(2*avg-255);
        }
      } else if (pixel.getY() < height*3/7) {
        if (avg < 128) {
          pixel.setRed(2*avg);
          pixel.setGreen(2*avg);
          pixel.setBlue(0);
        } else {
          pixel.setRed(255);
          pixel.setGreen(255);
          pixel.setBlue(2*avg-255);
        }
      } else if (pixel.getY() < height*4/7) {
        if (avg < 128) {
          pixel.setRed(0);
          pixel.setGreen(2*avg);
          pixel.setBlue(0);
        } else {
          pixel.setRed(2*avg-255);
          pixel.setGreen(255);
          pixel.setBlue(2*avg-255);
        }
      } else if (pixel.getY() < height*5/7) {
        if (avg < 128) {
          pixel.setRed(0);
          pixel.setGreen(0);
          pixel.setBlue(2*avg);
        } else {
          pixel.setRed(2*avg-255);
          pixel.setGreen(2*avg-255);
          pixel.setBlue(255);
        }
      } else if (pixel.getY() < height*6/7) {
        if (avg < 128) {
          pixel.setRed(0.8*avg);
          pixel.setGreen(0);
          pixel.setBlue(2*avg);
        } else {
          pixel.setRed(1.2*avg-51);
          pixel.setGreen(2*avg-255);
          pixel.setBlue(255);
        }
      } else if (pixel.getY() < height*7/7) {
        if (avg < 128) {
          pixel.setRed(1.6*avg);
          pixel.setGreen(0);
          pixel.setBlue(1.6*avg);
        } else {
          pixel.setRed(0.4*avg+153);
          pixel.setGreen(2*avg-255);
          pixel.setBlue(0.4*avg+153);
        }
      }
    }
    
    image5.drawTo(canvas); 
  }
}

function doReset(){  
  if (imageIsLoaded(image)){
    clearCanvas();
    image.drawTo(canvas); 
    var fileinput = document.getElementById("finput");
    image2 = new SimpleImage(fileinput);
    image3 = new SimpleImage(fileinput);
    image4 = new SimpleImage(fileinput);
    image5 = new SimpleImage(fileinput);
    image6 = new SimpleImage(fileinput);
  }
}

function imageIsLoaded(image){
  if (image == null || ! image.complete()) {
    alert('Image has not been loaded.')
    return false;
  }
  else{
    return true;
  }
}


function doBlur(){
  if (imageIsLoaded(image6)){
    clearCanvas();
    var width = canvas.width;
    var height = canvas.height;
    var blur_image = new SimpleImage(image6.getWidth(),image6.getHeight());

    for (var pixel of image6.values()){
      var avg = (pixel.getRed()+pixel.getGreen()+pixel.getBlue())/3;
      var random_num = Math.random();
      var x=pixel.getX();
      var y=pixel.getY();

      if (random_num < 0.5) {
        blur_image.setPixel(x,y,pixel)
      } else {
        var nearby_dist = 10;
        var randon_int_x = Math.floor(Math.random()*nearby_dist*2)-Math.floor((nearby_dist/1));
        var randon_int_y = Math.floor(Math.random()*nearby_dist*2)-Math.floor((nearby_dist/1));
        var new_x = x+randon_int_x;
        var new_y = y+randon_int_y;
        if (new_x < 0) {
          new_x = 0;
        } else if (new_x > width-1) {
          new_x = width-1;
        }
        if (new_y < 0) {
          new_y = 0;
        } else if (new_y > height-1) {
          new_y = height-1;
        }
        blur_image.setPixel(x,y,image6.getPixel(new_x,new_y));
      }

    }

    blur_image.drawTo(canvas); 
  }
}