import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

/**
 * This is the controller for the NearestPropertyWindow which shows all the properties close to an attraction that the user chooses.
 *
 * @author Areeba Safdar(k20045738) Imaan Ghafur(k2102260)
 * Sabeeka Ahmad(k20012890) Zahra Amaan(k21011879)
 * @version 03/2022
 */

public class NearestPropertyController {
    ObservableList<AirbnbListing> nearestProperties;
    @FXML
    TableView<AirbnbListing> listingsTable;

    /**
     * Nearest properties is an array list generated in statsController which is passed into this method
     * nearestProperties is the list that the table view will loop through
     * @param nearestProperties
     */

    public NearestPropertyController(ArrayList<AirbnbListing> nearestProperties){
        this.nearestProperties = FXCollections.observableList(nearestProperties);

    }

    /**
     * Sets up the initial state of the fxml file.
     * The values of the enum 'sortingOptions' are added to the attractions comboBox and the
     * columns for the properties tableView are created. Each column will access objects
     * stored in an ObservableArrayList and call the method defined in 'PropertyValueFactory' to
     * populate the columns with the correct data
     *
     */
    @FXML
    public void initialize(){
        TableColumn<AirbnbListing, String> propertyNameCol = new TableColumn<>("Property Name");
        propertyNameCol.setSortable(false);
        propertyNameCol.setPrefWidth(100);
        propertyNameCol.setCellValueFactory(
            new PropertyValueFactory<AirbnbListing,String>("name")
        );
        TableColumn<AirbnbListing, String> propertyBoroughCol = new TableColumn<>("Property Borough");
        propertyBoroughCol.setSortable(false);
        propertyBoroughCol.setPrefWidth(100);
        propertyBoroughCol.setCellValueFactory(
                new PropertyValueFactory<AirbnbListing,String>("neighbourhood")
        );

        TableColumn<AirbnbListing, String> hostNameCol = new TableColumn<>("Host Name");
        hostNameCol.setSortable(false);
        hostNameCol.setPrefWidth(100);
        hostNameCol.setCellValueFactory(
            new PropertyValueFactory<AirbnbListing,String>("hostName")
        );
        TableColumn<AirbnbListing, Integer> priceCol = new TableColumn<>("Price");
        priceCol.setSortable(false);
        priceCol.setPrefWidth(100);
        priceCol.setCellValueFactory(
            new PropertyValueFactory<AirbnbListing,Integer>("price")
        );

        TableColumn<AirbnbListing, Integer> minNightsCol = new TableColumn<>("Minimum nights");
        minNightsCol.setSortable(false);
        minNightsCol.setPrefWidth(100);
        minNightsCol.setCellValueFactory(
            new PropertyValueFactory<AirbnbListing,Integer>("minimumNights")
        );

        listingsTable.setItems(nearestProperties);
        listingsTable.getColumns().addAll(propertyNameCol,propertyBoroughCol,hostNameCol, priceCol, minNightsCol);

    }
}
