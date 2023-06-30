// Name: Elisabeth Mark
// Date: 6/5/2023
// Description: This program will allow the user to play a game based off 2048.
// The user's goal is to merge blocks to reach the 512 block using arrow keys. 

package pkg512;

import javax.swing.JFrame;

//packages from the website
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.swing.JOptionPane;


public class Main {
//frames
static MainComponents componentsObject;
static QuestionComponents componentsObjectQuestion;
   
    public static void main(String[] args) {
        //create the starting frame
        componentsObject = new MainComponents();
        componentsObject.setupFrame();
        
    }
    
      
    
    
}

//researched stuff
//sound: https://www.infoworld.com/article/2077521/java-tip-24--how-to-play-audio-in-applications.html
//add sound feature to MainComponents ---> actionPerformed