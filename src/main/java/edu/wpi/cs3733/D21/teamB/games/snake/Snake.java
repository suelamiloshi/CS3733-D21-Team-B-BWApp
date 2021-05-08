package edu.wpi.cs3733.D21.teamB.games.snake;

import edu.wpi.cs3733.D21.teamB.database.DatabaseHandler;
import edu.wpi.cs3733.D21.teamB.entities.map.MapCache;
import edu.wpi.cs3733.D21.teamB.entities.map.MapDrawer;
import edu.wpi.cs3733.D21.teamB.entities.map.Coord;
import edu.wpi.cs3733.D21.teamB.entities.map.data.Edge;
import edu.wpi.cs3733.D21.teamB.entities.map.data.Node;
import edu.wpi.cs3733.D21.teamB.util.CSVHandler;
import edu.wpi.cs3733.D21.teamB.views.map.PathfindingMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sun.security.mscapi.CKeyPairGenerator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class Snake extends JPanel implements ActionListener {

    private List<Node> nodes;
    private List<Edge> edges;
    private MapDrawer mapDrawer;
    private MapCache mapCache;
    private AnchorPane nodeHolder;
    private int snakeSize;
    private Coord snakeHeadLoc;
    private Coord appleCoord;
    boolean left;
    boolean right;
    boolean up;
    boolean down;
    private int x[] = new int[snakeSize];
    private int y[] = new int[snakeSize];
    private int DOT_SIZE = 10;

    public Snake(MapDrawer mapDrawer, MapCache mapCache, AnchorPane nodeHolder) {
        this.mapDrawer = mapDrawer;
        this.mapCache = mapCache;
        this.nodeHolder = nodeHolder;
        this.addKeyListener(new MyKeyAdapter());

    }

    public void initializeMap() {
        // Load nodes and edges
        nodes = CSVHandler.loadCSVNodes("/edu/wpi/cs3733/D21/teamB/csvFiles/snakeNodes.csv");
        edges = CSVHandler.loadCSVEdges("/edu/wpi/cs3733/D21/teamB/csvFiles/snakeEdges.csv");
        try {
            DatabaseHandler.getHandler().loadNodesEdges(nodes, edges);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        mapDrawer.drawAllElements();
        mapDrawer.drawEdgesOnFloor();
        initializeGame();
    }

    public void initializeGame() {
        // Set the snake at a starting location (Francis Lobby Entrance)
        Node snake = nodes.get(122);
        snakeHeadLoc = new Coord(snake.getXCoord(), snake.getYCoord());
        try {
            ImageView i = FXMLLoader.load(Objects.requireNonNull(Snake.class.getResource("/edu/wpi/cs3733/D21/teamB/views/map/misc/snake.fxml")));
            i.setLayoutX((snake.getXCoord() / PathfindingMenuController.COORDINATE_SCALE) - (i.getFitWidth() / 4));
            i.setLayoutY((snake.getYCoord() / PathfindingMenuController.COORDINATE_SCALE) - (i.getFitHeight()));
            nodeHolder.getChildren().add(i);
            mapCache.getNodePlaced().add(i);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // restart size of snake
        snakeSize = 1;
        placeApple();
    }

    public void placeApple() {
        // Randomly select a node to place the apple at
        int index = (int) (Math.random() * nodes.size());
        Node appleNode = nodes.get(index);
        try {
            ImageView i = FXMLLoader.load(Objects.requireNonNull(Snake.class.getResource("/edu/wpi/cs3733/D21/teamB/views/map/misc/apple.fxml")));
            i.setLayoutX((appleNode.getXCoord() / PathfindingMenuController.COORDINATE_SCALE) - (i.getFitWidth() / 4));
            i.setLayoutY((appleNode.getYCoord() / PathfindingMenuController.COORDINATE_SCALE) - (i.getFitHeight()));
            nodeHolder.getChildren().add(i);
            mapCache.getNodePlaced().add(i);
        } catch (IOException e) {
            e.printStackTrace();
        }

        appleCoord = new Coord(appleNode.getXCoord(), appleNode.getYCoord());
    }

    public void checkApple() {
        // Check if the current node location is the location of the apple
        // Add to length of snake and call placeApple()
        if (appleCoord.equals(snakeHeadLoc)) {
            //delete the apple off of the map
            snakeSize++;
            placeApple();

        }
    }

    public boolean isValid() {
        //if snake part is there it is invalid
        //if there is no adjacent edge to where you are travelling to it is invalid
        return true;
    } //stub

    @Override
    public void actionPerformed(ActionEvent e) {
        move();

    }

    public void move() {
        for (int z = snakeSize; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (left) {
            x[0] -= DOT_SIZE;
        }

        if (right) {
            x[0] += DOT_SIZE;
        }

        if (up) {
            y[0] -= DOT_SIZE;
        }

        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int xVal = 0;
            int yVal = 0;


            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (isValid()) {
                        left = true;
                        right = false;
                        up = false;
                        down = false;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (isValid()) {
                        right = true;
                        left = false;
                        up = false;
                        down = false;
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (isValid()) {
                        left = false;
                        right = false;
                        up = true;
                        down = false;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (isValid()) {
                        left = false;
                        right = false;
                        up = false;
                        down = true;
                    }
                    break;
            }

            //this will go in a loop later because the size will grow
            try {
                ImageView i = FXMLLoader.load(Objects.requireNonNull(Snake.class.getResource("/edu/wpi/cs3733/D21/teamB/views/map/misc/snake.fxml")));
                i.setLayoutX((x[0] / PathfindingMenuController.COORDINATE_SCALE) - (i.getFitWidth() / 4));
                i.setLayoutY((y[0] / PathfindingMenuController.COORDINATE_SCALE) - (i.getFitHeight()));
                nodeHolder.getChildren().add(i);
                mapCache.getNodePlaced().add(i);
            } catch (IOException exception) {
                exception.printStackTrace();
            }

        }
    }
}
