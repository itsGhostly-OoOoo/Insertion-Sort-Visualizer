module ghostly.insertionsortingvisualizer {
    requires javafx.controls;
    requires javafx.fxml;


    opens ghostly.insertionsortingvisualizer to javafx.fxml;
    exports ghostly.insertionsortingvisualizer;
}