
package pkg512;

//The "components" class of the main screen

import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainComponents extends JFrame implements ActionListener{
    
    
    //variables of components
    JLabel question;
    
    //JLabels for sound on and sound off icon
    JLabel soundOn;
    JLabel soundOff;
    //boolean for saying if the sound should be on or off
    boolean sound;
    
    //for timers
    JLabel lblTimer;
    Timer timer;
    //make the start time of the variable 0
    //this is the variable to be displayed
    int time = 0;
    
    //boolean variable for if sound icon is on or off
    //set to true to start off with (yes the sound is on)
    boolean soundOnOrOff = true;
    
    //create 
    JPanel mainPanel; 
    
    //constructor
    public MainComponents() {
        //name the frame to display 512 as the name
        super("512");
    }
    
    //adds components
    public void setupFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //make sound initally true
        sound = true;
        
        //bring in images so i can scale them
        //"labels"... images for question mark, sound icons
        ImageIcon imgQuestion = new ImageIcon(getClass().getResource("images/questionMark.png"));
        question = new JLabel(new ImageIcon(imgQuestion.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH))); //used for scaling images (https://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon)
        
        ImageIcon imgSoundOn = new ImageIcon(getClass().getResource("images/sound.png"));
        soundOn = new JLabel(new ImageIcon(imgSoundOn.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        
        ImageIcon imgSoundOff = new ImageIcon(getClass().getResource("images/no sound.png"));
        soundOff = new JLabel(new ImageIcon(imgSoundOff.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        
        //make new timer (ticks every 1 second)
        timer = new Timer(1000, this); 
        //display of timer
        lblTimer = new JLabel("Time: " + time + " seconds");
 
        //object of game panel
        GamePanel game = new GamePanel(); 
        
        
        //set up the frame
        this.setBounds(0, 0, 400, 400);
        this.setResizable(false);
        this.setBackground(Color.WHITE);
        
        //make new panel for main
        mainPanel = new JPanel();
        
        //background colour is white
        mainPanel.setBackground(Color.WHITE);
        
        
        //null layout
        mainPanel.setLayout(null);
        
        //size and location of components
        question.setBounds(0, 0, 50, 50);
        soundOn.setBounds(75, 0, 50, 50);
        soundOff.setBounds(75, 0, 50, 50);
        lblTimer.setBounds(15, 315, 300, 50);
 
        //set sound off icon to invisible when the program loads
        soundOff.setVisible(false);
        soundOff.setEnabled(false);
        
        //add the components/panels to the main panel
        mainPanel.add(question);
        mainPanel.add(soundOn);
        mainPanel.add(soundOff);
        mainPanel.add(lblTimer);
        mainPanel.add(game);
        
        
        //object of type listener
        listener monitor = new listener();
        
        // add the addMouseListener to the monitor object
        addMouseListener(monitor);
        
        //add components to Mouse Listener
        question.addMouseListener(monitor);
        soundOn.addMouseListener(monitor);
        soundOff.addMouseListener(monitor);
        
        
        this.add(mainPanel);
        
        //set visible
        this.setVisible(true);
        
        //won't let the user resize the frame
        this.setResizable(false);
    }

    //method for starting the timer
    public void startTimer() {
        timer.start();
    }
    
    //method for stopping the timer
    public void stopTimer() {
        timer.stop();
    }

    //accessor method for getting the time
    public int getTime() {
        return this.time;
    }
    
    //for mouse event 
    private class listener implements MouseListener {

       
        @Override
        public void mouseReleased(MouseEvent e) {
           
        }

        @Override
        public void mouseClicked(MouseEvent e) {    
        
        //if question icon is hit
        //display instructions on how to play the game
        
        if (e.getSource () == question) {
            //make question frame visible
            Main.componentsObjectQuestion.setVisible(true);
            
        }
        //if sound icon is hit
        //turns on/off the sound
        else if(e.getSource() == soundOn) {

            
            //if sound icon is on, turn it off
            //if (soundOnOrOff == true) {
                
                //soundOnOrOff = false;

                //set soundOff to visible and soundOn to invisible
                soundOff.setEnabled(true);
                soundOff.setVisible(true);
                soundOn.setEnabled(false);
                soundOn.setVisible(false);
                
                //if the sound boolean variable was true, make it false and vice versa
                if (sound == true) {
                    sound = false;
                } else if (sound == false) {
                    sound = true;
                } 
           
            //refresh main panel
            mainPanel.repaint();

        }
        
        else if (e.getSource() == soundOff) {
            //soundOnOrOff = true;
                  
            //set soundOn to visible and soundOff to invisible
            soundOff.setVisible(false);
            soundOff.setEnabled(false);
            soundOn.setVisible(true);
            soundOn.setEnabled(true);
            
            //if the sound boolean variable was true, make it false and vice versa
            if (sound == true) {
                    sound = false;
            } else if (sound == false) {
                    sound = true;
            } 
        }
        
    }

        
        @Override
        public void mouseExited(MouseEvent e) {
           
        }  

        @Override
        public void mouseEntered(MouseEvent e) {
        } 

        @Override
        public void mousePressed(MouseEvent e) {
        }
        
        }
       
    //accessor method for getting the sound boolean variable
    public boolean getSoundOnOff() {
        return this.sound;
    }
        
    @Override
    public void actionPerformed(ActionEvent e) {

        //if timer is called, time variable (int) adds one each time and display time
        if (e.getSource() == timer) {
            time++;
            lblTimer.setText("Time: " + time + " seconds");

                                                                    
            repaint();
        }   
    
        
    }
    
    
    
}
   
   
    

