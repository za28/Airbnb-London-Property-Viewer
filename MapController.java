import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.layout.*;

/**
 * This class is used handle the map as it's the controller of the 'MapPanel.fxml'
 * file.
 * It sets the colours of each borough based on the price range selected
 * and displays a new window containing all the properties when a borough is clicked.
 *
 * @author Areeba Safdar(k20045738) Imaan Ghafur(k2102260)
 * Sabeeka Ahmad(k20012890) Zahra Amaan(k21011879)
 * @version 03/2022
 */
public class MapController {

    private static MapStatistics mapStatistics;

    @FXML
    AnchorPane paneHoldingButtons;

    public MapController(){
        mapStatistics = new MapStatistics();
    }

    /**
     * Sets the colours of each borough.
     * Access buttons from the children of 'paneHoldingButtons' and casts them
     * to buttons.
     * Uses the text of the button to get the full name of the borough and gets the
     * number of the properties. Based on the number of properties a colour is assigned to
     * the button.
     */
    public void setBoroughColours(){
        ObservableList<Node> boroughButtons = paneHoldingButtons.getChildren();

        for(Node button: boroughButtons){
            Button borough = (Button) button;
            String name = mapStatistics.getBoroughFullName(borough.getText());

            int numberOfPropertiesInBorough = (mapStatistics.getPropertiesInAPriceRange(mapStatistics.getBoroughProperties(name))).size();

            String boroughColour = getBoroughColour(numberOfPropertiesInBorough);
            borough.setStyle("-fx-background-color: " + boroughColour +";");
        }

    }

    /**
     * Method used to determine a colour, dependent on the number of properties
     * there are.
     * @param numberOfProperties the number of the properties with the price range
     * @return the hex value of the colour
     */
    private String getBoroughColour(int numberOfProperties){

        if(numberOfProperties < 25){
            return "#F9BEC7";
        } else if(numberOfProperties < 50) {
            return "#FBB1BD";
        } else if(numberOfProperties < 75){
            return "#FF99AC";
        } else if(numberOfProperties < 100){
            return "#FF85A1";
        } else if(numberOfProperties < 500){
            return "#FF7096";
        } else if (numberOfProperties < 1000){
            return "#FF5C8A";
        } else if (numberOfProperties < 1500){
            return "#FF477E";
        } else if (numberOfProperties < 2000){
            return "#FF0A54";
        } else{
            return "#c23350";
        }
    }

    /**
     * Event handler for when a borough is clicked; shows a new window with all the properties
     * in the borough clicked. Sets the controller of the 'BoroughWindow.fxml' file here as this
     * allows the boroughName to be passed in as a parameter to the controller.
     * Uses the event passed and the statistics class object to get the name of the borough clicked.
     * @param event the event object generated
     */
    @FXML
    private void displayProperties(ActionEvent event) {
        Button borough = (Button) event.getSource();
        String abbreviation = borough.getText();
        String boroughName = mapStatistics.getBoroughFullName(abbreviation);

        //Creating the borough window to show the properties
        try{
            Stage stage = new Stage();
            BoroughController boroughContoller = new BoroughController(boroughName);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("BoroughWindow.fxml"));
            loader.setController(boroughContoller); //Set controller here as passing name variables in
            Parent boroughPanel = loader.load();

            Scene scene = new Scene(boroughPanel, 700, 400);
            stage.setTitle("Properties in " + boroughName);
            stage.setScene(scene);

            //Blocks any event from being delivered to any other application window
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Event handler for when show statistics button is called.
     * Displays the statistics panel in another pop-up window
     */
    @FXML
    public void showStatsPanel(ActionEvent event){
        //Creating the borough window to show the properties
        try{
            Stage stage = new Stage();

            BorderPane statsPanel = FXMLLoader.load(getClass().getResource("StatsPanel.fxml"));
            Scene scene=new Scene(statsPanel, 700, 400);
            stage.setTitle("Statistics");
            stage.setScene(scene);

            //Blocks any event from being delivered to any other application window
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

}