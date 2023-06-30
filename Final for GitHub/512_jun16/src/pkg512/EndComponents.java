//Creates the end screen where the user sees if they won or lost
package pkg512;

//packages
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class EndComponents extends JFrame implements ActionListener {
    //if the player won or not
    private boolean won;
    
    //how long it took them
    private int timeTaken;
    
    //JPanel of main panel
    JPanel mainPanel;
    
    //JButton
    JButton btnRestart;
    
    //JLabels
    JLabel lblCongrats;
    JLabel lblTimeWin;
    
    JLabel lblGameOver;
    
    //contructor
    //
    public EndComponents(boolean win, int time) {
        //named 512
        super("512");
        
        //assign the instance variables
        won = win;
        timeTaken = time;
    }
    
    //set up the fram with components
    public void setupFrame() {
        //set the characteristics of the frame itself
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 400, 400);
        
        //make the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        
        //restart btn
        btnRestart = new JButton("Play Again");
        btnRestart.setBounds(150, 300, 100, 25);
        btnRestart.addActionListener(this);
        
        //put a different image if the player wins or loses
        if (won == true) {
            //image for them winning
            ImageIcon imgCongrats = new ImageIcon(getClass().getResource("images/other/congrats.png"));
            lblCongrats = new JLabel(new ImageIcon(imgCongrats.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH)));
            lblCongrats.setBounds(0, 0, 400, 400);
            
            //show their time
            lblTimeWin = new JLabel(timeTaken + " seconds");
            lblTimeWin.setBounds(125, 112, 200, 20);
            
            //put the time on top of the label
            lblCongrats.add(lblTimeWin);
            
            
            //add to main
            mainPanel.add(lblCongrats);

        } else {
            //they lost
            //show lose image
            ImageIcon imgGameOver = new ImageIcon(getClass().getResource("images/other/gameover.png"));
            lblGameOver = new JLabel(new ImageIcon(imgGameOver.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH)));
            lblGameOver.setBounds(0, 0, 400, 400);
            
            //add to main panel
            mainPanel.add(lblGameOver);

        }
                
        mainPanel.add(btnRestart);
        
        //add main panel to the frame and make visible
        this.add(mainPanel);
        setVisible(true);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRestart) {
            Main.componentsObject = new MainComponents();
            Main.componentsObject.setupFrame();
            this.dispose();
        }
        
    }
}
