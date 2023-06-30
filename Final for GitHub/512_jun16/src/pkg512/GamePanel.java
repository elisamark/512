//Game Panel class
//the four by four area


package pkg512;

//packages
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class GamePanel extends JPanel {
    
    //boolean variable setting the winStatus to false
    boolean winStatus = false;
    
    //audio variables
    //string variable of the location of the explosion sound
    String explodeFile = "src/pkg512/Sound/explode1.wav";
    String path;
    
    //JLabel component of the grid
    JLabel fourByFour;
    
    //2D array with 4 rows and 4 coloumns
    public static Block[][] blocksArr = new Block[4][4];
    
    //constructor 
    public GamePanel() {
        
        //calls set up frame
        setupFrame();
        
        //for implementing the arrow key listener
        BlockListener monitorBlock = new BlockListener();
        addKeyListener(monitorBlock);
        
    }
    
    //for all of the game component stuff
    public void setupFrame() {
        
        //can use arrow keys
        setFocusable(true);
        
        //variables for spacing of blocks in the game board
        int boarderLengthSpacing = 20; //same with this
        int lengthOfSquare = 60; //this literally does nothing


        //location and size of panel
        this.setBounds(60, 55, 275, 270);
      
        //fill the 2D array with blank blocks
        for (int row = 0; row < blocksArr.length; row++) {
            for (int col = 0; col < blocksArr[row].length; col++) {
                blocksArr[row][col] = new Block(0);
            }
        }
       
        //panel is visible
        this.setVisible(true);
           
        //beginning of game
        //call the BlockGenerator Method for two, 2 blocks in random locations
        for (int i =0; i<=1; i ++) {
            blockGenerator();
        }
        //call addBlocks
        addBlocks();
    }
    
    //blockGenerator method
    //spawns a new block
    //makes sure the new block can't spawn on a gird square occupied by a block that is already there
    //determines the type of block being spawned
    public void blockGenerator() {
        //make random variable
        Random rand = new Random();
        
        //rnd num from 0-3 for x and y coordinates
        int twoBlockX1 = rand.nextInt(3 - 0 + 1) + 0;
        int twoBlockY1 = rand.nextInt(3 - 0 + 1) + 0;
        
        //rnd num from 0 - 6 for the type of block (2, 4 or bomb)
        int typeOfBlock = rand.nextInt(9 - 0 + 1) + 0;
        
        //check to see if the grid space is open
        while (blocksArr[twoBlockX1][twoBlockY1].value!=0) {

            twoBlockX1 = rand.nextInt(3 - 0 + 1) + 0;
            twoBlockY1 = rand.nextInt(3 - 0 + 1) + 0;
        }
       
        //case statement for if the block being spawned is 2 or 4
        //case 0 = bomb
        //case 1 and 2 = 4 block
        //case 3 - 6 = 2 block
        switch (typeOfBlock)
        {
            case 0:
                //bomb
                blocksArr[twoBlockX1][twoBlockY1] = new Block(-1);
                break;
            case 1:
            case 2:
                //4
                blocksArr[twoBlockX1][twoBlockY1] = new Block(4);
                break;
            default:
                //2
                blocksArr[twoBlockX1][twoBlockY1] = new Block(2);
                
            
        }
             

    } 
    
    //addBlocks method
    //adds the blocks to the panel
    public void addBlocks() {
        for (int row = 0; row < blocksArr.length; row++) {
            for (int col = 0; col < blocksArr[row].length; col++) {
                this.add(blocksArr[row][col]);
                
            }             
        }     
    }

         
    //class for when keys are interacted with
    private class BlockListener implements KeyListener {
        
        
        
            @Override
            public void keyPressed(KeyEvent e) {
                
                //booleans for saying if the keyboard click is valid (blocks move when you hit an arrow key
                boolean upStatus = false;
                boolean downStatus = false;
                boolean leftStatus = false;
                boolean rightStatus = false;
                
                if (winStatus == false) {
                    if (e.getKeyCode() == KeyEvent.VK_UP)
                    {
                        //loop through the blocks and move them
                        for (int col = 0; col < blocksArr[0].length; col++) {
                            for (int row = 0; row < blocksArr.length; row++) {

                                if (upStatus == false) {
                                    upStatus = blocksArr[row][col].move(0, row, col, upStatus);
                                } else {
                                    blocksArr[row][col].move(0, row, col, upStatus);
                                }

                            }
                        }
                        
                        //deal with any bombs
                        boomUp();
                        
                        //merge any blocks that need to be merged
                        //store if any were merged
                        boolean merging = mergeUp();

                        //move again to make sure everything get pushed all the way up
                        for (int col = 0; col < blocksArr[0].length; col++) {
                            for (int row = 0; row < blocksArr.length; row++) {
                                if (upStatus == false) {
                                    upStatus = blocksArr[row][col].move(0, row, col, upStatus);
                                } else {
                                    blocksArr[row][col].move(0, row, col, upStatus);
                                }
                            }
                        }


                        //only add a new block if there was movement or merging
                        if (upStatus == true || merging == true) {

                            blockGenerator();
                            addBlocks();
                        }
                    }
                    else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        //repeat for all the rest of te arrow keys
                        for (int col = 0; col < blocksArr[0].length; col++) {
                            for (int row = blocksArr.length - 1; row >= 0; row--) {
                                if (downStatus == false) {
                                    downStatus = blocksArr[row][col].move(1, row, col, downStatus);
                                } else {
                                    blocksArr[row][col].move(1, row, col, downStatus);
                                }
                            }
                        }

                        boomDown();

                        boolean merging = mergeDown();

                        for (int col = 0; col < blocksArr[0].length; col++) {
                            for (int row = blocksArr.length - 1; row >= 0; row--) {

                                if (downStatus == false) {
                                    downStatus = blocksArr[row][col].move(1, row, col, downStatus);
                                } else {
                                    blocksArr[row][col].move(1, row, col, downStatus);
                                }

                            }
                        }

                        if (downStatus == true || merging == true) {

                            blockGenerator();
                            addBlocks();
                        }

                    }
                    else if (e.getKeyCode() == KeyEvent.VK_LEFT) {

                        for (int row = 0; row < blocksArr.length; row++) {
                            for (int col = 0; col < blocksArr[row].length; col++) {

                                if (leftStatus == false) {
                                    leftStatus = blocksArr[row][col].move(2, row, col, leftStatus);
                                } else {
                                    blocksArr[row][col].move(2, row, col, leftStatus);
                                }

                            }
                        }

                        boomLeft();

                        boolean merging = mergeLeft();

                        for (int row = 0; row < blocksArr.length; row++) {
                            for (int col = 0; col < blocksArr[row].length; col++) {

                                if (leftStatus == false) {
                                    leftStatus = blocksArr[row][col].move(2, row, col, leftStatus);
                                } else {
                                    blocksArr[row][col].move(2, row, col, leftStatus);
                                }


                            }
                        }

                        if (leftStatus == true || merging == true) {
                            blockGenerator();
                            addBlocks();
                        }

                    }

                    else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

                        for (int row = 0; row < blocksArr.length; row++) {
                            for (int col = blocksArr[row].length - 1; col >= 0; col--) {
                                if (rightStatus == false) {
                                    rightStatus = blocksArr[row][col].move(3, row, col, rightStatus);
                                } else {
                                    blocksArr[row][col].move(3, row, col, rightStatus);
                                }


                            }
                        }

                        boomRight();

                        boolean merging = mergeRight();

                        for (int row = 0; row < blocksArr.length; row++) {
                            for (int col = blocksArr[row].length - 1; col >= 0; col--) {
                                if (rightStatus == false) {
                                    rightStatus = blocksArr[row][col].move(3, row, col, rightStatus);
                                } else {
                                    blocksArr[row][col].move(3, row, col, rightStatus);
                                }
                            }
                        }

                        if (rightStatus == true || merging == true) {

                            blockGenerator();
                            addBlocks();
                        }

                    }

                    //check if the user has got the 512 block OR if they have no more valid moves to make
                    checkWin();

                    //start the timer when the user first taps an arrow
                    if (winStatus == false) {
                        Main.componentsObject.startTimer();
                    }
                    
                    //redraw the blocks
                    repaintScreen();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
        
            }
            
            @Override
            public void keyTyped(KeyEvent e) {
        
            }
    }
    
    //method for if you want to merge up (up arrow is pressed)
    private boolean mergeUp() {
        //initally, merged is set to false
        boolean merged = false;
        
        //for loop for coloumns
        for (int col = 0; col < blocksArr.length; col++) {
            //varible for row at 0
            int row = 0;
            
                //look at the top 2 blocks in a coloumn and see if they are the same and if the top one of the two isnt a blank grid square
                if (blocksArr[row][col].getValue() == blocksArr[row+1][col].getValue() && blocksArr[row][col].getValue() != 0) {
                    
                    //look to see if the top block is a numeric block (not a bomb)
                    if (blocksArr[row][col].getValue() != -1) {
                        //yes
                        //make the top block double the value
                        blocksArr[row][col] = new Block(blocksArr[row][col].getValue()*2);
                    } else {
                        //no, the block is a bomb
                        //explode
                        //make the top block a blank grid square
                        blocksArr[row][col] = new Block(0);
                    }
                    
                    blocksArr[row+1][col] = new Block(0);//replace bottom with zero block
                    
                    //look at bottom 2 blocks in the coloumn
                    //see if they're the same and the upper one is not a blank grid square
                    if (blocksArr[row+2][col].getValue() == blocksArr[row+3][col].getValue() && blocksArr[row+2][col].getValue() != 0) {
                        
                        //look to see if the 2nd block overall in the column is a numeric block
                        if (blocksArr[row+1][col].getValue() != -1) {
                            //not a bomb
                            //make the second block in the coloumn double the value as the 3rd block (or fourth) in the coloum
                            blocksArr[row+1][col] = new Block(blocksArr[row+2][col].getValue()*2);
                        } else {
                            //a bomb
                            //explode
                            //make the top block a blank grid square
                            blocksArr[row+1][col] = new Block(0);
                        }
                        
                        //make the bottom two blocks blank grid squares
                        blocksArr[row+2][col] = new Block(0);
                        blocksArr[row+3][col] = new Block(0);
                    }
                    //set merged to true
                    merged = true;
                }
               
                //look at middle blocks in the column
                //make sure top one of the two isnt a blank grid square
                else if (blocksArr[row+1][col].getValue() == blocksArr[row+2][col].getValue() && blocksArr[row+1][col].getValue() != 0) {
                        //if the top one of two is not a bomb
                        if (blocksArr[row+1][col].getValue() != -1) {
                            //merge
                            //make the top one of the two double the value
                            blocksArr[row+1][col] = new Block(blocksArr[row+1][col].getValue()*2);
                        } else {
                            //the top one of the two is a bomb
                            //blank gird square (the bomb "deletes" numeric blocks)
                            blocksArr[row+1][col] = new Block(0);
                        }
                        
                        //the second block of the two is a blank grid square
                        blocksArr[row+2][col] = new Block(0);
                        
                        //set merged to true
                        merged = true;
                }
                
                //look at bottom two blocks in the column
                else if (blocksArr[row+2][col].getValue() == blocksArr[row+3][col].getValue() && blocksArr[row+2][col].getValue() != 0) {
                         //if the top one of two is not a bomb
                        if (blocksArr[row+2][col].getValue() != -1) {
                            //merge
                            //make the top one of the two double the value
                            blocksArr[row+2][col] = new Block(blocksArr[row+2][col].getValue()*2);
                        } else {
                            //the top one of the two is a bomb
                            //blank gird square (the bomb "deletes" numeric blocks)
                            blocksArr[row+2][col] = new Block(0);
                        }
                        
                        //the second block of the two is a blank grid square
                        //set merged to true 
                        blocksArr[row+3][col] = new Block(0);
                        merged = true;
                }
            
            }
            //return merged variable
            return merged;
        }
    
    //method for merging down
    //very similar to mergeUp()
    private boolean mergeDown() {
        boolean merged = false;
        
        
        //loop through coloumns (like mergeUp)
        for (int col = 0; col < blocksArr.length; col++) {
            int row = 0;
                //look at bottom two blocks and see if theyre equal and are not blank grid squares
                if (blocksArr[row+3][col].getValue() == blocksArr[row+2][col].getValue() && blocksArr[row+3][col].getValue() != 0) {
                    //merge
                    if (blocksArr[row+3][col].getValue() != -1) {
                        //not bombs
                        //make the bottom one the one with double the value
                        blocksArr[row+3][col] = new Block(blocksArr[row+3][col].getValue()*2);
                    } else {
                        //are bombs
                        //make bottom blank
                        blocksArr[row+3][col] = new Block(0);
                    }
                    //make third block of the column blank
                    blocksArr[row+2][col] = new Block(0);
                    
                    //look at top two in coloumn
                    if (blocksArr[row+1][col].getValue() == blocksArr[row][col].getValue() && blocksArr[row+1][col].getValue() != 0) {
                        
                        
                        if (blocksArr[row+2][col].getValue() != -1) {
                            //not bombs
                            //make the third block of the coloumn double the value as the second blocj in the coloumn
                            blocksArr[row+2][col] = new Block(blocksArr[row+1][col].getValue()*2);
                        } else {
                            //blocks are bombs
                            //make blank gird squares
                            blocksArr[row+2][col] = new Block(0);
                        }
                        
                        //make top two blocks in coloumn blank grid squares
                        blocksArr[row+1][col] = new Block(0);
                        blocksArr[row][col] = new Block(0);
                    }
                    //set merged as true
                    merged = true;
                }
               //look at middle blocks
                else if (blocksArr[row+1][col].getValue() == blocksArr[row+2][col].getValue() && blocksArr[row+1][col].getValue() != 0) {
                        
                        if (blocksArr[row+2][col].getValue() != -1) {
                            //yay theyre not bombs
                            //merge em and make the third block overall the value of twice its orginal value
                            blocksArr[row+2][col] = new Block(blocksArr[row+1][col].getValue()*2);
                        } else {
                            //noo theyre bombs
                            //make blank grid square
                            blocksArr[row+2][col] = new Block(0);
                        }
                        
                        //make second block in column a blank grid square
                        blocksArr[row+1][col] = new Block(0);
                        //set merged as true
                        merged = true;
                }
                
                //look at top two blocks
                else if (blocksArr[row][col].getValue() == blocksArr[row+1][col].getValue() && blocksArr[row][col].getValue() != 0) {
                        if (blocksArr[row+1][col].getValue() != -1) {
                            //theyre not bombs
                            //merge and make into double the value at the lower block coordinate
                            blocksArr[row+1][col] = new Block(blocksArr[row][col].getValue()*2);
                        } else {
                            //theyre bombs
                            //make the lower block a blank grid square
                            blocksArr[row+1][col] = new Block(0);
                        }
                        
                        //make top block into a blank grid square
                        blocksArr[row][col] = new Block(0);
                        //set merged as true
                        merged = true;
                }
            
        }
        //return merged variable
        return merged;
    }
    
    //method for merging left
    //similar to merging right
    private boolean mergeLeft() {
        //set merged variable to false initially
        boolean merged = false;
        
        //look at the rows (per row)
        //opposite of mergeUp and down
        for (int row = 0; row < blocksArr.length; row++) {
            int col = 0;
            //look at coloumn 0 and 1, see if theyre the same and if theyre not grid squares
                if (blocksArr[row][col].getValue() == blocksArr[row][col + 1].getValue() && blocksArr[row][col].getValue() != 0) {
                    //merge
                    
                    if (blocksArr[row][col].getValue() != -1) {
                        //not bombs
                        //merge in left most grid coordinate
                        blocksArr[row][col] = new Block(blocksArr[row][col].getValue()*2);
                    } else {
                        //they are bombs
                        //kaboom
                        //make blank grid square
                        blocksArr[row][col] = new Block(0);
                    }
                    //make blank grid square
                    blocksArr[row][col+1] = new Block(0);
                    
                    //look at rightmost blocks in the row
                    //see if their values are the same and if theyre not grid squares
                    if (blocksArr[row][col+2].getValue() == blocksArr[row][col+3].getValue() && blocksArr[row][col+2].getValue() != 0) {
                        //look to see if theyre bombs
                        if (blocksArr[row][col+1].getValue() != -1) {
                            //not bombs
                            //merge the blocks but make the block in coloumn 1 
                            blocksArr[row][col+1] = new Block(blocksArr[row][col+2].getValue()*2);
                        } else {
                            //noooo theyre bombs
                            //make blank grid square
                            blocksArr[row][col+1] = new Block(0);
                        }
                        
                        //make blank grid squares
                        blocksArr[row][col+2] = new Block(0);
                        blocksArr[row][col+3] = new Block(0);
                    }
                    //set merged as true
                    merged = true;
                }
               
                //look at middle blocks in the row
                else if (blocksArr[row][col+1].getValue() == blocksArr[row][col+2].getValue() && blocksArr[row][col+1].getValue() != 0) {
                        
                    //see if theyre bombs
                    if (blocksArr[row][col+1].getValue() != -1) {
                        //not bombs
                        //merge
                        //store in left most block (col 1)
                        blocksArr[row][col+1] = new Block(blocksArr[row][col+1].getValue()*2);
                    } else {
                        //bombs
                        //make blank grid square
                        blocksArr[row][col+1] = new Block(0);
                    }
                        
                    //make blank grid square
                    blocksArr[row][col+2] = new Block(0);
                    //set merged as true
                    merged = true;
                }
                
                //look at last two blocks in the row
                else if (blocksArr[row][col+2].getValue() == blocksArr[row][col+3].getValue() && blocksArr[row][col+2].getValue() != 0) {
                        if (blocksArr[row][col+2].getValue() != -1) {
                            //not bombs
                            //merge and store in left most block from the two
                            blocksArr[row][col+2] = new Block(blocksArr[row][col+2].getValue()*2);
                        } else {
                            //make blank grid square
                            blocksArr[row][col+2] = new Block(0);
                        }
                        
                        //make blank grid square
                        blocksArr[row][col+3] = new Block(0);
                        //set merged as true
                        merged = true;
                }
            
        }
        //return merged
        return merged;
    }
    
    //method for merging right
    //very similar to mergeLeft
    private boolean mergeRight() {
        boolean merged = false;
        
        //for loop for going through rows
        for (int row = 0; row < blocksArr.length; row++) {
            int col = 0;
                //look at right most blocks
                //see if theyre the same and arent blank grid squares
                if (blocksArr[row][col+3].getValue() == blocksArr[row][col +2].getValue() && blocksArr[row][col+3].getValue() != 0) {
                    //merge
                    if (blocksArr[row][col+3].getValue() != -1) {
                        //not bombs
                        //make right most block double the value
                        blocksArr[row][col+3] = new Block(blocksArr[row][col+3].getValue()*2);
                    } else {
                        //they were bombs
                        //make blank grid square
                        blocksArr[row][col+3] = new Block(0);
                    }
                    
                    //make blank grid square
                    blocksArr[row][col+2] = new Block(0);
                    
                    //look at first two blocks in the row
                    if (blocksArr[row][col+1].getValue() == blocksArr[row][col].getValue() && blocksArr[row][col+1].getValue() != 0) {
                        if (blocksArr[row][col+2].getValue() != -1) {
                            //they are not bombs
                            //merge in the second most right area of the row
                            blocksArr[row][col+2] = new Block(blocksArr[row][col+1].getValue()*2);
                        } else {
                            //make blank grid square
                            blocksArr[row][col+2] = new Block(0);
                        }
                        
                        //make blank grid squares
                        blocksArr[row][col+1] = new Block(0);
                        blocksArr[row][col] = new Block(0);
                    }
                    //set merged as true
                    merged = true;
                }
                
                //look at middle blocks in the row
                else if (blocksArr[row][col+1].getValue() == blocksArr[row][col+2].getValue() && blocksArr[row][col+1].getValue() != 0) {
                    //see if theyre not bombs
                    if (blocksArr[row][col+2].getValue() != -1) {
                        //not bombs
                        //merge at first coloumn
                        blocksArr[row][col+2] = new Block(blocksArr[row][col+1].getValue()*2);
                    } else {
                        //make blank grid square
                        blocksArr[row][col+2] = new Block(0);
                    }
                        
                        //make blank grid square
                        blocksArr[row][col+1] = new Block(0);
                        merged = true;
            }
                //look at first two blocks in the row
                else if (blocksArr[row][col].getValue() == blocksArr[row][col+1].getValue() && blocksArr[row][col].getValue() != 0) {
                        if (blocksArr[row][col+1].getValue() != -1) {
                            //not bombs
                            //merge at right most block
                            blocksArr[row][col+1] = new Block(blocksArr[row][col].getValue()*2);
                        } else {
                            //make blank grid square
                            blocksArr[row][col+1] = new Block(0);
                        }
                        
                        //make blank grid square
                        blocksArr[row][col] = new Block(0);
                        merged = true;
                        }
            
                
        }
        
        return merged;
    }
     
    //4 methods to handle the bombs
    //each method will search through rows or columns (depending if left/right or up/down) for if theres a bomb
    //if there is, loop through the row/column and see if the bomb will destroy anything. The bomb will destroy the 
    //first thing it encounters
    
    private void boomUp() {
        for (int col = 0; col < blocksArr.length; col++) {
            for (int row = 3; row >= 0; row--) {
                if (blocksArr[row][col].getValue() == -1) {
                    int tempRow = row;
                    int tempCol = col;
                    
                    
                    try {
                        if (blocksArr[tempRow-1][tempCol].getValue() != 0) {
                            //destroy both
                            blocksArr[tempRow][tempCol] = new Block(0);
                            blocksArr[tempRow-1][tempCol] = new Block(0);
                        } else {
                            blocksArr[tempRow][tempCol].move(0, tempRow, tempCol, false);
                        }
                    } catch (IndexOutOfBoundsException ex) {
                        blocksArr[tempRow][tempCol] = new Block(0);
                    }
                    
                    //check if the sound is turned on
                    if (Main.componentsObject.getSoundOnOff() == true) {
                        try {
                            //make the explode sound
                            Sound(explodeFile);
                        } catch (UnsupportedAudioFileException ex) {
                            System.out.println(ex);
                        } catch (IOException ex) {
                            System.out.println(ex);
                        } catch (LineUnavailableException ex) {
                            System.out.println(ex);
                        }
                    }
                    
                }
            }
            
            
            
        }
    }
    
    private void boomDown() {
        for (int col = 0; col < blocksArr.length; col++) {
            for (int row = 0; row < blocksArr.length; row++) {
                if (blocksArr[row][col].getValue() == -1) {
                    int tempRow = row;
                    int tempCol = col;
                    
                    
                    try {
                        if (blocksArr[tempRow+1][tempCol].getValue() != 0) {
                            //destroy both
                            blocksArr[tempRow][tempCol] = new Block(0);
                            blocksArr[tempRow+1][tempCol] = new Block(0);
                        } else {
                            blocksArr[tempRow][tempCol].move(1, tempRow, tempCol, false);
                        }
                    } catch (IndexOutOfBoundsException ex) {
                        blocksArr[tempRow][tempCol] = new Block(0);
                    }
                    
                    if (Main.componentsObject.getSoundOnOff() == true) {
                        try {
                            Sound(explodeFile);
                        } catch (UnsupportedAudioFileException ex) {
                            System.out.println(ex);
                        } catch (IOException ex) {
                            System.out.println(ex);
                        } catch (LineUnavailableException ex) {
                            System.out.println(ex);
                        }
                    }
                    
                }
            }
            
            
            
        }
    }
    
    private void boomLeft() {
        for (int row = 0; row < blocksArr.length; row++) {
            for (int col = 3; col >= 0; col--) {
                if (blocksArr[row][col].getValue() == -1) {
                    int tempRow = row;
                    int tempCol = col;
                    
                    
                    try {
                        if (blocksArr[tempRow][tempCol-1].getValue() != 0) {
                            //destroy both
                            blocksArr[tempRow][tempCol] = new Block(0);
                            blocksArr[tempRow][tempCol-1] = new Block(0);
                        } else {
                            blocksArr[tempRow][tempCol].move(2, tempRow, tempCol, false);
                        }
                    } catch (IndexOutOfBoundsException ex) {
                        blocksArr[tempRow][tempCol] = new Block(0);
                    }
                    
                    
                    if (Main.componentsObject.getSoundOnOff() == true) {
                        try {
                            Sound(explodeFile);
                        } catch (UnsupportedAudioFileException ex) {
                            System.out.println(ex);
                        } catch (IOException ex) {
                            System.out.println(ex);
                        } catch (LineUnavailableException ex) {
                            System.out.println(ex);
                        }
                    }
                }
            }
        }
    }
    
    private void boomRight() {
        for (int row = 0; row < blocksArr.length; row++) {
            for (int col = 0; col < blocksArr.length; col++) {
                if (blocksArr[row][col].getValue() == -1) {
                    int tempRow = row;
                    int tempCol = col;
                    
                    
                    try {
                        if (blocksArr[tempRow][tempCol+1].getValue() != 0) {
                            //destroy both
                            blocksArr[tempRow][tempCol] = new Block(0);
                            blocksArr[tempRow][tempCol+1] = new Block(0);
                        } else {
                            blocksArr[tempRow][tempCol].move(3, tempRow, tempCol, false);
                        }
                    } catch (IndexOutOfBoundsException ex) {
                        blocksArr[tempRow][tempCol] = new Block(0);
                    }
                    
                    if (Main.componentsObject.getSoundOnOff() == true) {
                        try {
                            Sound(explodeFile);
                        } catch (UnsupportedAudioFileException ex) {
                            System.out.println(ex);
                        } catch (IOException ex) {
                            System.out.println(ex);
                        } catch (LineUnavailableException ex) {
                            System.out.println(ex);
                        }
                    }
                }
            }
        }
    }
    
    //check if the player has won or lost
    public void checkWin() {
        //assume the board is full to start with
        boolean full = true;
        
        //assume the player has lost until we find a 0
        boolean lose = true;
        
        //loop through all blocks
        for (int row = 0; row < blocksArr.length; row++) {
            for (int col = 0; col < blocksArr.length; col++) {
                //check for a 512
                if (blocksArr[row][col].getValue() == 512) {
                    //if there is one, its a win
                    winStatus = true;
                    
                    //stop the timer
                    Main.componentsObject.stopTimer();
                    
                    
                    
                    //go to the win screen
                    showFinalScreen(true);
                    
                } else if (blocksArr[row][col].getValue() == 0 || blocksArr[row][col].getValue() == -1) {
                    // if theres an empty block or a bomb, then there are moves the user can make, so the board is not full and they have at least 1 valid move
                    full = false;
                }
            }
        }
        
        //if the board is full, need to see if the user can merge any blocks
        if (full == true) {
            //loop through all blocks, row by row
            for (int row = 0; row < blocksArr.length; row++) {
                for (int col = 0; col < blocksArr.length - 1; col++) {
                    //check if the block beside can be merged with
                    if (blocksArr[row][col].getValue() == blocksArr[row][col+1].getValue()) {
                        lose = false;
                        break;
                    }
                }
                
                //if the player has a move then stop doing the for loop since we don't need to anymore
                if (lose == false) {
                    break;
                }
            }
            
            //do the same column by column
            for (int col = 0; col < blocksArr.length; col++) {
                for (int row = 0; row < blocksArr.length - 1; row++) {
                    if (blocksArr[row][col].getValue() == blocksArr[row+1][col].getValue()) {
                        lose = false;
                        break;
                    }
                }
                
                //stop
                if (lose == false) {
                    break;
                }
            }
            
            
            if (lose == true) {
                //you lose
                
                //stop timer
                Main.componentsObject.stopTimer();
                
                
                
                //losing screen
                showFinalScreen(false);
                
            } //else nothing happens
        }
    }
    
    //plays a sound from the file
    private void Sound(String soundFile) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        //found here: https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
        File f = new File(soundFile);
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());  
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
    }
    
    //removes the blocks and replaces them in new places
    private void repaintScreen() {
        removeAll();
        
        for (int row = 0; row < blocksArr.length; row++) {
            for (int col = 0; col < blocksArr[row].length; col++) {
                this.add(blocksArr[row][col]);
            }
        }
        
        //i found this method while researching how to make the components refresh
        //from this website: https://coderanch.com/t/452435/java/refresh-JFrame-JPanel-lots-Components
        revalidate();
    }
    
    //remove the gameFrame and show the ending screen
    public void showFinalScreen(boolean win) {
        
        EndComponents endScreen = new EndComponents(win, Main.componentsObject.getTime());
        
        endScreen.setupFrame();
        
        Main.componentsObject.dispose();
    }
    
    
}

