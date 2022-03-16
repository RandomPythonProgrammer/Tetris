/**
 * Tetrad class to be completed for Tetris project
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.awt.*;
import java.util.ArrayList;

public class Tetrad
{
    private Block[] blocks;
    private BoundedGrid<Block> grid;
    public Tetrad(BoundedGrid<Block> grid)
    {
        this.grid = grid;
        //Exercise 1.2  Insert code here to
        //                  instantiate blocks Block array (length 4)
        //                  initialize blocks array with new Block objects
        //                  declare color variable
        //                  declare and instantiate locs Location array (length 4)
        //                  declare shape variable and set equal to zero
        blocks = new Block[4];
        for (int i = 0; i < blocks.length; i++){
            blocks[i] = new Block();
        }

        Color color = Color.BLACK;
        Location[] locs = new Location[4];
        int shape = 0;

        //Exercise 2.0  Insert code here to
        //                  choose a random integer from 0 to 6

        shape = (int) (Math.random() * 7);

        //Exercise 1.2  Insert code here to
        //                  branch (if statements) based on each shape number, to then
        //                      set the color variable for that shape
        //                      set the block locations for that shape
        switch (shape){
            case 0:
                color = Color.RED;
                locs = new Location[]{
                        new Location(0, 0),
                        new Location(1, 0),
                        new Location(2, 0),
                        new Location(3, 0)
                };
                break;
            case 1:
                color = Color.GRAY;
                locs = new Location[]{
                        new Location(1, 0),
                        new Location(1, 1),
                        new Location(1, 2),
                        new Location(2, 1)
                };
                break;
            case 2:
                color = Color.CYAN;
                locs = new Location[]{
                        new Location(1, 0),
                        new Location(1, 1),
                        new Location(2, 0),
                        new Location(2, 1)
                };
                break;
            case 3:
                color = Color.YELLOW;
                locs = new Location[]{
                        new Location(0, 0),
                        new Location(1, 0),
                        new Location(2, 0),
                        new Location(2, 1)
                };
                break;
            case 4:
                color = Color.MAGENTA;
                locs = new Location[]{
                        new Location(0, 1),
                        new Location(1, 1),
                        new Location(2, 1),
                        new Location(2, 0)
                };
                break;
            case 5:
                color = Color.BLUE;
                locs = new Location[]{
                        new Location(1, 2),
                        new Location(1, 3),
                        new Location(2, 0),
                        new Location(2, 1)
                };
                break;
            case 6:
                color = Color.GREEN;
                locs = new Location[]{
                        new Location(1, 0),
                        new Location(1, 1),
                        new Location(2, 1),
                        new Location(2, 2)
                };
                break;
        }
        for (Block block: blocks){
            block.setColor(color);
        }
        addToLocations(grid, locs);
        //Exercise 1.2  Insert code here (after the above if statements) to
        //                  loop through the blocks array to
        //                      set the color of each block
        //                  call addToLocations
    }

    //precondition:  blocks are not in any grid;
    //               blocks.length = locs.length = 4.
    //postcondition: The locations of blocks match locs,
    //               and blocks have been put in the grid.
    private void addToLocations(BoundedGrid<Block> grid, Location[] locs)
    {
        for (int i = 0; i < locs.length; i++){
            grid.put(locs[i], blocks[i]);
        }
    }

    //precondition:  Blocks are in the grid.
    //postcondition: Returns old locations of blocks;
    //               blocks have been removed from grid.
    private Location[] removeBlocks()
    {
        Location[] array = new Location[blocks.length];   // replace this line
        for(int i = 0; i<blocks.length; i++) {
            array[i] = blocks[i].getLocation();
            blocks[i].removeSelfFromGrid();
        }
        return array;
    }

    //postcondition: Returns true if each of locs is
    //               valid (on the board) AND empty in
    //               grid; false otherwise.
    private boolean areEmpty(BoundedGrid<Block> grid,
                             Location[] locs)
    {
        for(int i = 0; i<locs.length; i++) {
            if(!grid.isValid(locs[i])||grid.get(locs[i])!=null) {
                return false;
            }
        }
        return true;  // replace this line
    }

    //postcondition: Attempts to move this tetrad deltaRow
    //               rows down and deltaCol columns to the
    //               right, if those positions are valid
    //               and empty; returns true if successful
    //               and false otherwise.
    public boolean translate(int deltaRow, int deltaCol)
    {
        //Exercise 2.2    Insert code here to
        //              ask any block for its grid and store value
        //              remove the blocks (but save the locations)
        //              create an array of the new (possible) locations
        //              check if the new locations are empty
        //              replace the tetrad in the proper place (translated)
        //              return true if moved, false if not moved
        Location[] locs = removeBlocks();
        for (Location loc : locs) {
            loc = new Location(loc.getRow() + deltaRow, loc.getCol() + deltaCol);
        }
        if (areEmpty(grid, locs)) {
            addToLocations(grid, locs);
            return true;
        } else {
            for (Location loc : locs) {
                loc = new Location(loc.getRow() - deltaRow, loc.getCol() - deltaCol);
            }
            addToLocations(grid, locs);
            return false;
        }

    }

    //postcondition: Attempts to rotate this tetrad
    //               clockwise by 90 degrees about its
    //               center, if the necessary positions
    //               are empty; returns true if successful
    //               and false otherwise.
    public boolean rotate()
    {
        //Exercise 3.0  Insert code here to
        //              ask any block for its grid and store value
        //              remove the blocks (but save the locations)
        //              check if the new locations are empty
        //              replace the tetrad in the proper place (rotated)

        int row0 = blocks[0].getLocation().getRow();
        int col0 = blocks[0].getLocation().getCol();

        Location[] locs = removeBlocks();
        for (Location loc : locs) {
            loc = new Location(row0 - loc.getCol(), row0 + col0 - loc.getRow());
        }
        if (areEmpty(grid, locs)) {
            addToLocations(grid, locs);
            return true;
        } else {
            for (Location loc : locs) {

            }
            addToLocations(grid, locs);
            return false;
        }
// replace this line
    }
}