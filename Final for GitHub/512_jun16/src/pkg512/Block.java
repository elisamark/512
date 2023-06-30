//Creates a numerical or bomb block that can move and merge on the game board


package pkg512;

//packages
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

//Block class 
//has JLabel properties
public class Block extends JLabel {
    
    //images for each block
    ImageIcon imgBlankGridSpace = new ImageIcon(getClass().getResource("images/blank grid space.png"));
    ImageIcon img2 = new ImageIcon(getClass().getResource("images/blocks/2.png"));
    ImageIcon img4 = new ImageIcon(getClass().getResource("images/blocks/4.png"));
    ImageIcon img8 = new ImageIcon(getClass().getResource("images/blocks/8.png"));
    ImageIcon img16 = new ImageIcon(getClass().getResource("images/blocks/16.png"));
    ImageIcon img32 = new ImageIcon(getClass().getResource("images/blocks/32.png"));
    ImageIcon img64 = new ImageIcon(getClass().getResource("images/blocks/64.png"));
    ImageIcon img128 = new ImageIcon(getClass().getResource("images/blocks/128.png"));
    ImageIcon img256 = new ImageIcon(getClass().getResource("images/blocks/256.png"));
    ImageIcon img512 = new ImageIcon(getClass().getResource("images/blocks/512.png"));
    ImageIcon imgBomb = new ImageIcon(getClass().getResource("images/blocks/bomb.png"));

    //value of the block
    int value;
    
    //size of block (width and height)
    final int SIZE = 60;
    
    //constructor
    //takes in value of block
    //assigns value and changes the image of the block corresponding to its value
    public Block(int val) {
        super();
        
        value = val;
        changeImage();
    }
    
    
    //move method: takes in the direction of motion (which arrow key is pressed), row and col of block, and boolean variable to see if the user can make a move
    public Boolean move(int direction, int row, int col, boolean valid) {
        //initially, the user's move is invalid
        valid = false;
        
        //variables for temporary coloumn and row
        int tempCol;
        int tempRow;
        
        //case statement for direction of arrow key
        switch (direction) {
            case 0:
                //up
                
                //make the temporary row equal to the row taken in
                tempRow = row;
                
                //check that its not already all the way over
                if (row != 0) {
                    //check that its not a blank block
                    if (value != 0) {
                        while (tempRow > 0) {
                            //if the block next to it is a zero, we can move the block
                            if (GamePanel.blocksArr[tempRow - 1][col].getValue() == 0) {
                                
                                GamePanel.blocksArr[tempRow - 1][col] = new Block(value);
                                GamePanel.blocksArr[tempRow][col] = new Block(0);
                                
                                //step to the next position (block shifts over)
                                tempRow -= 1;
                                
                                valid = true; //a valid move was made
                            } else {
                                //block can't move anymore, so break and go to the next one
                                break;
                            }
                            
                            //repeat the process until we can't anymore for this block
                            
                        }
                    }
                }
                
                break;
                
            case 1:
                //see case 0 for details, case 1 does the same thing except from the opposite side
                //down
                tempRow = row;
                
                if (row != 3) {
                    if (value != 0) {
                        while (tempRow < 3) {
                            if (GamePanel.blocksArr[tempRow + 1][col].getValue() == 0) {
                                GamePanel.blocksArr[tempRow + 1][col] = new Block(value);
                                GamePanel.blocksArr[tempRow][col] = new Block(0);
                                
                                tempRow += 1;
                                valid = true;
                            } else {
                                break;
                            }
                            
                        }
                    }
                }
                
                break;
                
            case 2:
                //see case 0, except now we work in the left-right direction
                //left
                tempCol = col;
                
                if (col != 0) {
                    if (value != 0) {
                        while (tempCol > 0) {
                            if (GamePanel.blocksArr[row][tempCol - 1].getValue() == 0) {
                                //do a flip
                                GamePanel.blocksArr[row][tempCol - 1] = new Block(value);
                                GamePanel.blocksArr[row][tempCol] = new Block(0);
                                
                                tempCol -= 1;
                                valid = true;
                            } else {
                                break;
                            }
                            
                        }
                    }
                }
                
                break;
                
            case 3:
                //see case 2, except now in the opposite direction
                //right
                tempCol = col;
                
                if (col != 3) {
                    
                    if (value != 0) {
                        
                        while (tempCol < 3) {
                            if (GamePanel.blocksArr[row][tempCol + 1].getValue() == 0) {
                                //do a flip
                                GamePanel.blocksArr[row][tempCol + 1] = new Block(value);
                                GamePanel.blocksArr[row][tempCol] = new Block(0);
                                
                                tempCol += 1;
                                valid = true;
                            } else {
                                break;
                            }
                            
                        }
                    }
                }
                
                break;
        }
        
        // return if a valid move was made
        return valid;
    }
    
    //method to change image of block
    //case statement for the value and changes accordingly
    private void changeImage() {
        switch (value) {
            case -1:
                //bomb
                setIcon(new ImageIcon(imgBomb.getImage().getScaledInstance(SIZE, SIZE, Image.SCALE_DEFAULT)));
                break;
            case 0:
                //blank grid square
                setIcon(new ImageIcon(imgBlankGridSpace.getImage().getScaledInstance(SIZE, SIZE, Image.SCALE_DEFAULT)));
                break;
            case 2:
                //2 block.... etc
                setIcon(new ImageIcon(img2.getImage().getScaledInstance(SIZE, SIZE, Image.SCALE_SMOOTH)));
                break;
            case 4:
                setIcon(new ImageIcon(img4.getImage().getScaledInstance(SIZE, SIZE, Image.SCALE_SMOOTH)));
                break;
            case 8:
                setIcon(new ImageIcon(img8.getImage().getScaledInstance(SIZE, SIZE, Image.SCALE_SMOOTH)));
                break;
            case 16:
                setIcon(new ImageIcon(img16.getImage().getScaledInstance(SIZE, SIZE, Image.SCALE_SMOOTH)));
                break;
            case 32:
                setIcon(new ImageIcon(img32.getImage().getScaledInstance(SIZE, SIZE, Image.SCALE_SMOOTH)));
                break;
            case 64:
                setIcon(new ImageIcon(img64.getImage().getScaledInstance(SIZE, SIZE, Image.SCALE_SMOOTH)));
                break;
            case 128:
                setIcon(new ImageIcon(img128.getImage().getScaledInstance(SIZE, SIZE, Image.SCALE_SMOOTH)));
                break;
            case 256:
                setIcon(new ImageIcon(img256.getImage().getScaledInstance(SIZE, SIZE, Image.SCALE_SMOOTH)));
                break;
            case 512:
                setIcon(new ImageIcon(img512.getImage().getScaledInstance(SIZE, SIZE, Image.SCALE_SMOOTH)));
                break;
        }
        
        
    }
    
    //accessor method for value
    //returns value of block
    public int getValue() {
        return value;
    }
}
