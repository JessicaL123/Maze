/**
 *
 */
public class MazeCell {
    private DisjSets DisjSets;
    public boolean[][] wall;
    private int row;
    private int col;
    public int[] wallNum;
    
    /**
     * contruct the Maze Cell object
     * @param col the number of columns of the maze.
     * @param row the number of the rows of the maze.
     */
    public MazeCell(int col, int row){
        this.row = row;
        this.col = col;
        DisjSets = new DisjSets(col * row);
        wall = new boolean[col * row][3];
        for(int i = 0; i < row * col; i++){ //side[i][j][0] means left wall, side[i][j][1] means bottom wall
            wall[i][0] = true;
            wall[i][1] = true;
        }
    }
    
    /**
     * a method to generate the maze
     * If two cells are not connected, break the walls between them, and union the two cells
     */
    public void build(){
        // create an array that contains all the walls, each cell has left wall and right wall
        wallNum = new int[row * col * 2];
        for(int i = 0; i < row * col * 2; i ++)
            wallNum[i] = i;
        
        // swap the wall number in a random sequence
        for(int i = 0, j; i < row * col * 2; i++){
            j = (int)(0+java.lang.Math.random()*(row * col * 2 -1));
            int temp = wallNum[i];
            wallNum[i] = wallNum[j];
            wallNum[j] = temp;
        }

        // traverse the walls and breaks the wall if two adjacent cells divided by the wall is not connected
        for(int k = 0,count = 0;count<row*col-1;k++){
            int wallPos = wallNum[k];
            int cellPos = wallPos/2;    // get the position of the cell in the 'DisjSets'

            // if the cell is the leftmost of a row, then do nothing
            if(cellPos % col == 0 && wallPos % 2 == 0)
                continue;
            // if the cell is the bottommost of column, then do nothing
            if(cellPos > (row-1)*col-1 && wallPos%2 == 1)
                continue;
            
            if(wallPos % 2 == 0){   // if the wall is even, means it is the left wall of a cell
                int root1 = DisjSets.find(cellPos);
                int root2 = DisjSets.find(cellPos -1);
                if(root1 != root2){
                    wall[cellPos][0] = false;
                    DisjSets.union(root1, root2);
                    count++;
                }
            }
            else{   // if the wall is odd, means it is the bottom wall of a cell
                int root1 = DisjSets.find(cellPos);
                int root2 = DisjSets.find(cellPos + col);
                if(root1 != root2){
                    wall[cellPos][1] = false;
                    DisjSets.union(root1, root2);
                    count++;
                }
            }   
        }
    }
}
