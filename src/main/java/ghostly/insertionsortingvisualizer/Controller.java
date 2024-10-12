package ghostly.insertionsortingvisualizer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.application.Platform;

public class Controller {
    // Initialise ElementCreator class
    private ElementCreator elementCreator = new ElementCreator();

    // Setup pane for visualising array values
    @FXML
    private Pane drawingPane;

    @FXML
    private Button generateButton, sortButton;

    // Generate the array and set up the foundation for the visualisation of the array.
    @FXML
    protected void onGenerateButtonClick(){
        // Generate the array (Hard-coded to generate an array with 100 elements.)
        elementCreator.GenerateElements(100);

        // Clear all previously created squares from prior runs.
        drawingPane.getChildren().clear();

        int elementCount = elementCreator.getElements().size();

        // Bind the pane to the height and width of the window (For dynamic scaling)
        drawingPane.widthProperty().addListener((obs, oldVal, newVal) -> updateSquares(elementCount));
        drawingPane.heightProperty().addListener((obs, oldVal, newVal) -> updateSquares(elementCount));

        // Initial call to update squares
        updateSquares(elementCount);
    }

    // Display the array values as squares within the pane
    private void updateSquares(int elementCount) {
        // Clears all previous squares generated
        drawingPane.getChildren().clear();

        double paneWidth = drawingPane.getWidth();
        double paneHeight = drawingPane.getHeight();

        // Calculate the maximum element value for scaling heights
        int maxElementValue = elementCreator.getElements().stream().max(Integer::compareTo).orElse(1);

        // Calculate available width for each square to ensure all values fit on screen (including spacing)
        double spacing = 2; // Adjust the spacing between squares
        double availableWidth = paneWidth - (elementCount - 1) * spacing;
        double squareWidth = availableWidth / elementCount;

        // Ensure that squareWidth is at least 1 pixel to prevent visualising values that are wayy too small to see
        squareWidth = Math.max(squareWidth, 1);

        // Loop through each element and create a rectangle
        for (int i = 0; i < elementCreator.getElements().size(); i++) {
            int elementValue = elementCreator.getElements().get(i);

            // Calculate the height based on the value, scaled to fit within the pane's height
            double scaledHeight = (elementValue / (double) maxElementValue) * paneHeight;

            // Create a new rectangle with the calculated width and scaled height
            Rectangle square = new Rectangle(squareWidth, scaledHeight);

            square.setFill(Color.WHITE);

            // Position the squares in the pane
            square.setX(i * (squareWidth + spacing));  // X position with dynamic spacing
            square.setY(paneHeight - scaledHeight);  // Y position (bottom aligned)

            // Add the square to the pane
            drawingPane.getChildren().add(square);
        }
    }

    @FXML
    protected void onSortButtonClicked() {
        // Disable buttons so the functions are not interrupted
        generateButton.setDisable(true);
        sortButton.setDisable(true);

        // Create a new thread to show the insertion sort process
        new Thread(() -> {
            try {
                insertionSort();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void insertionSort() throws InterruptedException {
        int n = elementCreator.getElements().size();

        // Iterate over the elements, starting from the second position (array[1])
        for (int i = 1; i < n; ++i) {
            int key = elementCreator.getElements().get(i);
            int j = i - 1;

            // Move elements of the array that are greater than the key to one position ahead
            while (j >= 0 && elementCreator.getElements().get(j) > key) {
                elementCreator.getElements().set(j + 1, elementCreator.getElements().get(j));
                j = j - 1;

                // Update UI on the JavaFX Application Thread
                int finalJ = j;
                Platform.runLater(() -> updateSquares(n));

                // Delay for visualization
                Thread.sleep(1);
            }
            elementCreator.getElements().set(j + 1, key);

            // Update UI on the JavaFX Application Thread
            Platform.runLater(() -> updateSquares(n));

            // Delay for visualization
            Thread.sleep(1);
        }

        // Reactivate the buttons
        generateButton.setDisable(false);
        sortButton.setDisable(false);
    }
}
