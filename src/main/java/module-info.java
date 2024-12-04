module online.speedwagon.maze {
    requires javafx.controls;
    requires javafx.fxml;


    opens online.speedwagon.maze to javafx.fxml;
    exports online.speedwagon.maze;
}