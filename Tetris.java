import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Tetris class to be completed for Tetris project
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class Tetris implements ArrowListener
{
    private double score;
    private int frame;

    public static void main(String[] args)
    {
        Tetris tetris = new Tetris();
        tetris.play();
    }

    private BoundedGrid<Block> grid;
    private BlockDisplay display;
    private Tetrad activeTetrad;

    public Tetris() {
        grid = new BoundedGrid<Block>(20, 10);
        display = new BlockDisplay(grid);
        display.setTitle("Tetris");
        display.setArrowListener(this);
        activeTetrad = new Tetrad(grid);
        frame = 0;
        File file = new File("theme.wav");
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file.toURI().toURL());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(-1);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void upPressed()
    {
        activeTetrad.rotate();
    }

    public void downPressed()
    {
        activeTetrad.translate(1, 0);
    }

    public void leftPressed()
    {
        activeTetrad.translate(0, -1);
    }

    public void rightPressed()
    {
        activeTetrad.translate(0, 1);
    }

    public void spacePressed()
    {
        while(activeTetrad.translate(1,0));
    }

    public void play()
    {
        while (true)
        {
            try { Thread.sleep(25); } catch(Exception e) {}

            //Insert Exercise 3.2 code here
            if (frame >= 50-Math.pow(score, 0.05)) {
                    if (!activeTetrad.translate(1, 0)) {
                        int rows = clearCompletedRows();
                        score += Math.pow(rows, 1.5) * 100;
                        score += 10;
                        display.setTitle(String.format("Your Score: %d", Math.round(score)));
                        if (!topRowsEmpty()) {
                            display.setTitle(String.valueOf((int) score));
                        }
                        activeTetrad = new Tetrad(grid);
                    }
                frame = 0;
            }
            //Insert Exercise 3.3 code here
            frame++;
            display.showBlocks();
        }
    }

    //precondition:  0 <= row < number of rows
    //postcondition: Returns true if every cell in the
    //               given row is occupied;
    //               returns false otherwise.
    private boolean isCompletedRow(int row)
    {
        for (int i = 0; i < grid.getNumCols(); i++){
            if (grid.get(new Location(row, i)) == null){
                return false;
            }
        }
        return true;
    }

    //precondition:  0 <= row < number of rows;
    //               given row is full of blocks
    //postcondition: Every block in the given row has been
    //               removed, and every block above row
    //               has been moved down one row.
    private void clearRow(int row)
    {
        for (int i = 0; i < grid.getNumCols(); i++){
            grid.remove(new Location(row, i));
        }
        for (int i = row - 1; i >= 0; i--){
            for (int j = 0; j < grid.getNumCols(); j++){
                try {
                    grid.get(new Location(i, j)).moveTo(new Location(i+1, j));
                } catch (NullPointerException ignored){

                }
            }
        }
    }

    //postcondition: All completed rows have been cleared.
    private int clearCompletedRows()
    {
        int count = 0;
        for (int i = 0; i < grid.getNumRows(); i++) {
            if (isCompletedRow(i)){
                clearRow(i);
                count ++;
            }
        }
        return count;
    }

    //returns true if top two rows of the grid are empty (no blocks), false otherwise
    private boolean topRowsEmpty()
    {
        for (int i = 0; i < 1; i++){
            for (int j = 0; j < grid.getNumCols(); j++){
                if (grid.get(new Location(i, j)) != null){
                    return false;// replace this line
                }
            }
        }
        return true;
    }

}