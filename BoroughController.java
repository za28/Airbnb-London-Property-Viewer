import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

/**
 * This class is the controller of the 'BoroughWindow.fxml' file.
 * It sets the header for table view columns, and fills up the table with
 * correct values of the properties in the borough.
 * It also sets up the sorting options and handles sorting the properties by
 * Reviews, Host Name and Price.
 * If a property is clicked then another window is displayed to show the full description
 * of that property.
 *
 * @author Areeba Safdar(k20045738) Imaan Ghafur(k2102260)
 * Sabeeka Ahmad(k20012890) Zahra Amaan(k21011879)
 * @version 03/2022
 */
public class BoroughController {

    enum sortingOptions{
        REVIEWS,
        PRICE,
        HOST
    }

    private static MapStatistics mapStatistics;
    private ObservableList<AirbnbListing> properties;
    private String name; //stores the abbreviated name of the borough

    @FXML
    TableView<AirbnbListing> listingsTable;
    @FXML
    ComboBox<sortingOptions> sortComboBox;

    /**
     * Takes ths name of the borough clicked (abbreviated version) and
     * stores it in the 'name' field.
     * @param boroughName the abbreviated name of the borough
     */
    public BoroughController(String boroughName) {
        mapStatistics = new MapStatistics();
        name = boroughName;
    }

    /**
     * Sets up the initial state of the fxml file.
     * The values of the enum 'sortingOptions' are added to the sorting comboBox and the
     * columns for the properties tableView are created. Each column will access objects
     * stored in an ObservableArrayList and call the method defined in 'PropertyValueFactory' to
     * populate the columns with the correct data
     *
     */
    @FXML
    public void initialize() {
        //Setting comboBox Options
        sortComboBox.getItems().addAll(sortingOptions.values());
        setComboBoxCells();

        //Setting up tableView columns
        TableColumn<AirbnbListing, String> hostNameCol = new TableColumn<>("Host Name");
        hostNameCol.setSortable(false);
        hostNameCol.setPrefWidth(100);
        hostNameCol.setCellValueFactory(
            new PropertyValueFactory<AirbnbListing,String>("hostName") //will access 'getHostName'
        );
        TableColumn<AirbnbListing, Integer> priceCol = new TableColumn<>("Price");
        priceCol.setSortable(false);
        priceCol.setPrefWidth(100);
        priceCol.setCellValueFactory(
            new PropertyValueFactory<AirbnbListing,Integer>("price") //will access 'getPrice'
        );
        TableColumn<AirbnbListing, Integer> reviewsCol = new TableColumn<>("Number of Reviews");
        reviewsCol.setSortable(false);
        reviewsCol.setPrefWidth(100);
        reviewsCol.setCellValueFactory(
            new PropertyValueFactory<AirbnbListing,Integer>("numberOfReviews") //will access 'getNumberOfReviews'
        );
        TableColumn<AirbnbListing, Integer> minNightsCol = new TableColumn<>("Minimum nights");
        minNightsCol.setSortable(false);
        minNightsCol.setPrefWidth(100);
        minNightsCol.setCellValueFactory(
            new PropertyValueFactory<AirbnbListing,Integer>("minimumNights") //will access 'getMinimumNights'
        );

        //Setting up columns and their initial values (ensuring properties are within specified price range)
        properties = FXCollections.observableList(mapStatistics.getPropertiesInAPriceRange(mapStatistics.getBoroughProperties(name)));

        listingsTable.setItems(properties);
        listingsTable.getColumns().addAll(hostNameCol, priceCol, reviewsCol, minNightsCol);

    }

    /**
     * This method is used to set custom text for the sorting options of the sort comboBox.
     * setCellFactory method is called on 'sortComboBox, which implements the 'Callback'
     * interface. This interface takes two generic type parameters and returns
     * the 2nd parameter type. In this case a ListCell is being returned which will be used as
     * a parameter to the setCellFactory method. This cell contains the user-friendly
     * message for each combBox option.
     */
    public void setComboBoxCells(){
        sortComboBox.setCellFactory(new Callback<ListView<sortingOptions>, ListCell<sortingOptions>>() {
                @Override
                public ListCell<sortingOptions> call(ListView<sortingOptions> sortingOptionsListView) {
                    return new  ListCell<sortingOptions>() {
                        @Override
                        public void updateItem(sortingOptions option, boolean empty) {
                            super.updateItem(option, empty);
                            if(option!= null) {
                                switch (option) {
                                    case HOST:
                                        setText("Host (A-Z)");
                                        break;
                                    case PRICE:
                                        setText("Price (Lowest-Highest)");
                                        break;
                                    case REVIEWS:
                                        setText("Review (Highest-Lowest)");
                                        break;
                                }
                            }
                        }
                    };
                }
            });
    }

    /**
     * Event handler method for when the user selects an option from the
     * comboBox. Switches through the options and sorts the properties based
     * on the option selected.
     * Since 'properties' is an observableList the tableView automatically displays
     * the updated/sorted list of properties in the table.
     * @param event the event object generated
     */
    @FXML
    public void sortListings(Event event) {
        setComboBoxCells();
        sortingOptions optionSelected = sortComboBox.getValue();

        switch (optionSelected) {
            case REVIEWS:
                properties = FXCollections.observableList(mapStatistics.sortByReview(properties));
                break;
            case PRICE:
                properties = FXCollections.observableList(mapStatistics.sortByPrice(properties));
                break;
            case HOST:
                properties = FXCollections.observableList(mapStatistics.sortByHostName(properties));
                break;
        }
    }

    /**
     * Event handler for when a property is clicked; shows a new window with the full
     * description of the property. Sets the controller of the 'propertyWindow.fxml' file here as
     * this allows the propertySelected to be passed in as a parameter to the controller.
     * Displays a warning dialog box if the user has clicked on an empty row
     * @param event the event object generated
     */
    @FXML
    public void showPropertyDescription(Event event){
        AirbnbListing propertySelected = (AirbnbListing) listingsTable.getSelectionModel().getSelectedItem();
        if(propertySelected == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Cannot click on an empty cell");
            alert.show();
            return;
        }

        try{
            Stage stage = new Stage();
            PropertyWindowController propertyWindowController = new PropertyWindowController(propertySelected);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("propertyWindow.fxml"));
            loader.setController(propertyWindowController); //Set controller here as passing name variables in
            Parent boroughPanel = loader.load();

            Scene scene = new Scene(boroughPanel, 500, 300);
            stage.setTitle("Description");
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
     * Method is called when the user presses the done button.
     * If retrieves the stage from which the action occurred and closes the current
     * stage.
     * @param event the event object generated from the onAction event
     */
    @FXML
    public void closeStage(Event event){

        //Getting the source from where the event was triggered and using
        //the source to get access to the stage we want to close
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();

        stage.close();
    }
}

