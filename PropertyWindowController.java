import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * This class is the controller of the 'propertyWindow.fxml' file.
 * It's used to display the name, host name, room type, price, minimum nights,
 * number of reviews, last review, reviews per month, and availability of the listing.
 * It also has the functionality which allows the user to click 'Add to favourites'
 * which this listing to the favourites list. (Which will be used in the favourites panel)
 *
 * @author Areeba Safdar(k20045738) Imaan Ghafur(k2102260)
 * Sabeeka Ahmad(k20012890) Zahra Amaan(k21011879)
 * @version 03/2022
 */

public class PropertyWindowController {

    private AirbnbListing listing;
    private MapStatistics mapStatistics = new MapStatistics();;

    @FXML
    ListView<String> propertyInfo;

    /**
     * Stores the property given in 'listing'
     * @param property
     */
    public PropertyWindowController(AirbnbListing property){
        listing = property;
    }

    /**
     * Adds the listing data to the 'propertyInfo' listView, which
     * is displayed in the 'propertyWindow.fxml' window
     */
    @FXML
    public void initialize(){
        propertyInfo.getItems().addAll(
            "Name: "+listing.getName(),
            "Host Name: " + listing.getHostName(),
            "Room Type: " + listing.getRoom_type(),
            "Price: " + listing.getPrice(),
            "Minimum Nights: "+ listing.getMinimumNights(),
            "Number of Reviews: "+ listing.getNumberOfReviews(),
            "Last Review: " + listing.getLastReview(),
            "Reviews Per Month: " + listing.getReviewsPerMonth(),
            "Availability: " + listing.getAvailability365()

        );
        propertyInfo.setFocusTraversable(false); //stop listView from outlining
        //        propertyInfo.setMouseTransparent(true); //stops mouse clicking on items
    }

    /**
     * The event handler for when the 'Add to Favourites' button
     * is clicked.
     * addFavProperty() is called on the mapStatistics object and if true is returned
     * an information dialog is displayed, which tells the user the property has been added.
     * Otherwise, the user is told they already have this property in their favourites list
     * via a warning alert box.
     * @param event the event object generated
     */
    @FXML
    public void addToFavourites(Event event){
        boolean hasListingBeenAdded = mapStatistics.addFavProperty(listing);
        if(hasListingBeenAdded){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Success!");
            alert.setContentText("Property has been added to favourites!");
            alert.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Property is already in favourites - cannot add again");
            alert.show();
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
