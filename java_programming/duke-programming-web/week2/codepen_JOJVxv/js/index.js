var image = null;
var image2 = null;

function uploadForeground(){
  var fileinput = document.getElementById("finput");
  var canvas = document.getElementById("can");
  image = new SimpleImage(fileinput);
  image.drawTo(canvas); 
}

function uploadBackground(){
  var fileinput = document.getElementById("finput2");
  var canvas = document.getElementById("can2");
  image2 = new SimpleImage(fileinput);
  image2.drawTo(canvas); 
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
  var canvas = document.getElementById("can");
  var canvas2 = document.getElementById("can2");
  
  var context=canvas.getContext("2d");
  context.clearRect(0,0,canvas.width,canvas.height);
  
  var context2=canvas2.getContext("2d");
  context2.clearRect(0,0,canvas2.width,canvas2.height);
}