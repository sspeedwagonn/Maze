package online.speedwagon.maze;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Maze extends Application {

    private static final int TILE_SIZE = 30;
    private static final int MAZE_WIDTH = 10;
    private static final int MAZE_HEIGHT = 10;

    private int playerX = 0;
    private int playerY = 0;
    private int endX = MAZE_WIDTH - 1;
    private int endY = MAZE_HEIGHT - 1;
    private boolean gameWon = false;
    private long startTime;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, MAZE_WIDTH * TILE_SIZE, MAZE_HEIGHT * TILE_SIZE + 30);


        int[][] maze = {
                {0, 0, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 1, 0, 0, 0, 1, 1, 1, 1},
                {1, 0, 1, 0, 1, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 1, 1, 1, 0, 1, 1},
                {0, 0, 1, 1, 0, 0, 1, 0, 1, 1},
                {0, 1, 1, 1, 0, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 1, 1, 0},
                {1, 1, 1, 0, 1, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1, 1, 1, 0, 1, 1},
                {0, 1, 1, 0, 0, 0, 1, 0, 0, 0}
        };

        drawMaze(root, maze);

        Rectangle player = new Rectangle(TILE_SIZE, TILE_SIZE, Color.BLUE);
        player.setX(playerX * TILE_SIZE);
        player.setY(playerY * TILE_SIZE);
        root.getChildren().add(player);

        Rectangle end = new Rectangle(TILE_SIZE, TILE_SIZE, Color.RED);
        end.setX(endX * TILE_SIZE);
        end.setY(endY * TILE_SIZE);
        root.getChildren().add(end);

        Text timerText = new Text(10, MAZE_HEIGHT * TILE_SIZE + 20, "Time: 0s");
        root.getChildren().add(timerText);

        startTime = System.currentTimeMillis();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (!gameWon) {
                long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
                timerText.setText("Time: " + elapsedTime + "s");
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        scene.setOnKeyPressed(e -> {
            if (!gameWon) {
                if (e.getCode() == KeyCode.W && isMoveValid(maze, playerX, playerY - 1)) {
                    playerY--;
                } else if (e.getCode() == KeyCode.S && isMoveValid(maze, playerX, playerY + 1)) {
                    playerY++;
                } else if (e.getCode() == KeyCode.A && isMoveValid(maze, playerX - 1, playerY)) {
                    playerX--;
                } else if (e.getCode() == KeyCode.D && isMoveValid(maze, playerX + 1, playerY)) {
                    playerX++;
                }

                player.setX(playerX * TILE_SIZE);
                player.setY(playerY * TILE_SIZE);

                if (playerX == endX && playerY == endY) {
                    gameWon = true;
                    long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
                    timerText.setText("You win! Time: " + elapsedTime + "s");
                }
            }
        });

        primaryStage.setTitle("Maze");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();
    }

    private void drawMaze(Pane root, int[][] maze) {
        for (int y = 0; y < MAZE_HEIGHT; y++) {
            for (int x = 0; x < MAZE_WIDTH; x++) {
                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE);
                if (maze[y][x] == 1) {
                    tile.setFill(Color.BLACK);
                } else {
                    tile.setFill(Color.WHITE);
                }
                tile.setX(x * TILE_SIZE);
                tile.setY(y * TILE_SIZE);
                root.getChildren().add(tile);
            }
        }
    }

    private boolean isMoveValid(int[][] maze, int x, int y) {
        if (x < 0 || x >= MAZE_WIDTH || y < 0 || y >= MAZE_HEIGHT) {
            return false;
        }
        return maze[y][x] == 0;
    }
}
