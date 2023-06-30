
package pkg512;

//the components class of the question screen

//packages
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class QuestionComponents extends JFrame{
    //how to play screens
    JLabel howToPlay1;
    JLabel howToPlay2;
    
    //arrows 
    JLabel arrowLeft;
    JLabel arrowRight;
    
    //create panel
    JPanel questionPanel; 
    
    //constructor
    public QuestionComponents() {
        //make the frame called "How To Play"
        super("How To Play");
    }
    
    //method for the basic components of the question area
    public void setupFrame() {
       
    //images of how to play and arrows to move between the images    
    ImageIcon imgHowToPlay1 = new ImageIcon(getClass().getResource("images/other/howToPlay1.png"));
    howToPlay1 = new JLabel(new ImageIcon(imgHowToPlay1.getImage().getScaledInstance(275, 375, Image.SCALE_SMOOTH)));

    ImageIcon imgHowToPlay2 = new ImageIcon(getClass().getResource("images/other/howToPlay2.png"));
    howToPlay2 = new JLabel(new ImageIcon(imgHowToPlay2.getImage().getScaledInstance(275, 375, Image.SCALE_SMOOTH)));
    
    //images of arrows and how to play screens
    ImageIcon imgArrowLeft = new ImageIcon(getClass().getResource("images/other/arrowLeft.png"));
    arrowLeft = new JLabel(new ImageIcon(imgArrowLeft.getImage().getScaledInstance(50, 40, Image.SCALE_SMOOTH)));

    ImageIcon imgArrowRight = new ImageIcon(getClass().getResource("images/other/arrowRight.png"));
    arrowRight = new JLabel(new ImageIcon(imgArrowRight.getImage().getScaledInstance(50, 40, Image.SCALE_SMOOTH)));
    
    //set up the frame... make it beside the main frame 
    this.setBounds(400, 0, 300, 425);
    
    //background is white
    this.setBackground(Color.WHITE);
        
    //make new panel
    questionPanel = new JPanel();
    questionPanel.setBackground(Color.WHITE);
        
    //question panel has layout of null
    questionPanel.setLayout(null);
    
    //location of how to play images (takes up whole panel)
    howToPlay1.setBounds(0, 0, 300, 400);
    howToPlay2.setBounds(0, 0, 300, 400);

    //how to play 2 invisible... how to play 1 is visible
    howToPlay2.setVisible(false);
    
    //location of arrows to move between instructions
    arrowRight.setBounds(215, 335, 50, 40);
    arrowLeft.setBounds(50, 335, 50, 40);

    //set arrow left to invisible and locked 
    arrowLeft.setVisible(false);
    arrowLeft.setEnabled(false);
    
    //add the components to the question panel
        questionPanel.add(howToPlay1);
        questionPanel.add(howToPlay2);
        questionPanel.add(arrowRight);
        questionPanel.add(arrowLeft);
        
        
        //object of type listener
        listener monitor = new listener();
        
        // add the addMouseListener to the monitor object
        addMouseListener(monitor);
        
        //add arrows to Mouse Listener
        arrowRight.addMouseListener(monitor);
        arrowLeft.addMouseListener(monitor);
        
        //add panel to the frame
        this.add(questionPanel);

        //set visible
        this.setVisible(true);

        //won't let the user resize the frame
        this.setResizable(false);
    }
    
    //class for when the mouse is interacted with...
    private class listener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        //when the mouse is pressed
        @Override
        public void mousePressed(MouseEvent e) {
           
            //if arrowRight is clicked
            if (e.getSource () == arrowRight) {
                //make arrowRight invisible and locked
                arrowRight.setEnabled(false);
                arrowRight.setVisible(false);
                
                //make arrowLeft visible and enabled
                arrowLeft.setEnabled(true);
                arrowLeft.setVisible(true);
                
                
                //how to play 1 screen is invisible
                //how to play2 screen is visible
                howToPlay1.setVisible(false);
                howToPlay2.setVisible(true);
            }
            //if arrowLeft is clicked
            else if (e.getSource() == arrowLeft) {
                //make arrowLeft invisible and locked
                arrowLeft.setEnabled(false);
                arrowLeft.setVisible(false);
                
                //make arrowRight visible and enabled
                arrowRight.setEnabled(true);
                arrowRight.setVisible(true);
                
                //how to play 1 screen is visible
                //how to play2 screen is invisible
                howToPlay2.setVisible(false);
                howToPlay1.setVisible(true);
                
            }
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
        
    }
}
