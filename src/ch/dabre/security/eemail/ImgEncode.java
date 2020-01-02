package ch.dabre.security.eemail;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImgEncode{	
	
	public static void StringToImage(String ascii, String pathToSaveWithFileNameDotpng){
		while(Math.sqrt(ascii.length())%1 != 0){
			ascii+=" ";
		}
		
		String hex = asciiToHex(ascii);
		
		try {
			ImageIO.write(generateKeyImage(hex), "png", new File(pathToSaveWithFileNameDotpng));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String ImageToString(String pathToKeyFileAsImage){
		try {
			String hexOut = getKeyFromImage(ImageIO.read(new File(pathToKeyFileAsImage)));
		    StringBuilder output = new StringBuilder();
		    for (int i = 0; i < hexOut.length(); i+=2) {
		        String str = hexOut.substring(i, i+2);
		        output.append((char)Integer.parseInt(str, 16));
		    }
		    return output.toString().replaceAll(" ", "");
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static BufferedImage generateKeyImage(String data) throws Exception{
		if(Math.sqrt(data.length()/2) % 1 != 0){
			throw new Exception("The Input cannot be composed into a square image. Length: " + data.length());
		}else{
			
		}
		int size = (int)(Math.sqrt(data.length()/2));
		int thisX=0,thisY=0;
		BufferedImage gen = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		
		for(int i = 0;i<data.length()	-1;i+=2){
			String thisPixel = data.substring(i,i+2);
			int thisPixelInt = Integer.parseInt(thisPixel,16);
			Color thisPixelColor = new Color(thisPixelInt,thisPixelInt,thisPixelInt,255);
			
			gen.setRGB(thisX, thisY, thisPixelColor.getRGB());
	   
			//System.out.println("writing...\t"+thisX+","+thisY+"\t"+thisPixelColor);
	   
			thisY++;
			if(thisY == gen.getHeight()){
				thisY = 0;
				thisX++;
			}
		}
			return gen;
	}
	
	private static String getKeyFromImage(BufferedImage image){
		String outString = new String(); 
		
		  for(int i = 0;i < image.getWidth(); i++){
			  for (int j = 0; j < image.getHeight(); j++) {
				  int rgb = image.getRGB(i, j);
				  
				  outString += Integer.toHexString(rgb).substring(Integer.toHexString(rgb).length()-2);
				  
			  }
		  }
		  
		  return outString;
	}
	
	private static String asciiToHex(String ascii){
        StringBuilder hex = new StringBuilder();
       
        for (int i=0; i < ascii.length(); i++) {
            hex.append(Integer.toHexString(ascii.charAt(i)));
        }      
        return hex.toString();
    }  
}