import java.util.Scanner;  
import javafx.animation.KeyFrame;
import javafx.animation.Timeline; 
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import javafx.util.Duration;
/**
 * CS 5343 Project 4 Maze Generation
 */
public class ShowMazeAnimation extends Application {
   
    @Override
    public void start(Stage primaryStage) {
        LinePane mazeLine = new LinePane();
        Scene scene = new Scene(mazeLine, (mazeLine.col + 1) * 20 + 20, mazeLine.row * 20 + 40, Color.WHITE);
        primaryStage.setTitle("Maze Generation");
        primaryStage.setScene(scene);
        primaryStage.show(); 
    }
    public static void main(String[] args){ 
        launch(args);
    }
}
   
class LinePane extends Pane {
    public int row;
    public int col;
    private Timeline animation;
    private int index = -1;
    private MazeCell maze;
    
    public LinePane() {
        System.out.print("Enter the number of rows:");
        Scanner input = new Scanner(System.in);
        row = input.nextInt();
        System.out.print("Enter the number of columns:");
        input = new Scanner(System.in);
        col = input.nextInt();
        // Draw the maze with all walls
        maze = new MazeCell(col,row);
        Line line1;
        for(int i = 0; i < row * col; i++){
            int c = i % col;
            int r = (i - c) / col; 
            if(maze.wall[i][0]){
                line1 = new Line(c * 20 + 20, r * 20 + 40, c * 20 + 20, (r - 1) * 20 + 40);
                line1.setStrokeWidth(6);
                line1.setStroke(Color.BLACK);
                getChildren().add(line1);
            }
            if(maze.wall[i][1]){
                line1 = new Line(c * 20 + 20, r * 20 + 40, (c + 1) * 20 + 20, r * 20 + 40);
                line1.setStrokeWidth(6);
                line1.setStroke(Color.BLACK);
                getChildren().add(line1);
            }
        }
        // generate a maze
        maze.build();
        
        // draw the maze's boundaries
        line1 = new Line(40, 20, col * 20 + 20, 20);
        line1.setStrokeWidth(6);
        line1.setStroke(Color.BLACK);
        getChildren().add(line1);
        line1 = new Line(col * 20 + 20, 20, col * 20 + 20, (row - 2) * 20 + 40);
        line1.setStrokeWidth(6);
        line1.setStroke(Color.BLACK);
        getChildren().add(line1);
        // control the speed of the animation
        animation = new Timeline(new KeyFrame(Duration.millis(5), e -> showBreakWall()));
        animation.setCycleCount(row * col * 2); // control the number of iterations
        animation.play();
    }
    
    
    /**
     * an animation to break the walls
     */
    private void showBreakWall(){
        index++;
        Line line2;
        int cellPos = maze.wallNum[index] / 2;
        // convert the index of each mazeCell into coordinates
        int c = cellPos % col;
        int r = (cellPos - c) / col;
        
        if(!maze.wall[cellPos][0]){ // break the vertical walls
            line2 = new Line(c * 20 + 20, r * 20 + 40 - 6, c * 20 + 20, (r - 1) * 20 + 40 + 6);
            line2.setStrokeWidth(6);
            line2.setStroke(Color.WHITE);
            getChildren().add(line2);
        }
        if(!maze.wall[cellPos][1]){   // break the herizonal walls
            line2 = new Line(c * 20 + 20 + 6, r * 20 + 40, (c + 1) * 20 + 20 - 6, r * 20 + 40);
            line2.setStrokeWidth(6);
            line2.setStroke(Color.WHITE);
            getChildren().add(line2);
        }
    }
}
