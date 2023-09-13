import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.ObservableList;
import java.awt.event.MouseEvent;
import java.net.URI;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;

/**
 * This class is the controller of the FavouritePanel.fxml file.
 * It allows the user to view information about the favourite properties
 * that they have chosen, and it allows them to compare their
 * favourite properties based on price, availability, popularity and an
 * overall comparison.
 *
 * @author Areeba Safdar(K20045738), Imaan Ghafur(K21012260)
 * Sabeeka Ahmad(K20012890), Zahra Amaan (k21011879)
 * @version 03/2022
 */
public class FavouritePanelController
{

    //2 lists of fave properties- one for the left and one for the right
    @FXML
    private ListView<String> leftList = new ListView<String>();

    @FXML
    private ListView<String> rightList = new ListView<String>();

    //gridpanes display info about the properties selected in listview
    @FXML
    private GridPane propertyDisplayLeft = new GridPane();

    @FXML
    private GridPane propertyDisplayRight = new GridPane();

    private MapStatistics stats = new MapStatistics();
    //list of fave properties selected by the user
    private ArrayList<AirbnbListing> faveProperties = new ArrayList();

    //list of names of fave properties that will be displayed in the list view
    ObservableList<String> items = FXCollections.observableArrayList (new ArrayList<String>());

    //maps name to actual property
    HashMap<String, AirbnbListing> actualProperty = new HashMap<String, AirbnbListing>();

    // labels that need to be set when a property is displayed
    // labels for the left side
    @FXML
    private Label LNameText;

    @FXML
    private Label LHostNameText;

    @FXML
    private Label LNeighbourhoodText;

    @FXML
    private Label LRoomTypeText;

    @FXML
    private Label LPriceText;

    @FXML
    private Label LNumberOfReviewsText;

    @FXML
    private Label LAvailabilityText;

    // labels for the right side
    @FXML
    private Label RNameText;

    @FXML
    private Label RHostNameText;

    @FXML
    private Label RNeighbourhoodText;

    @FXML
    private Label RRoomTypeText;

    @FXML
    private Label RPriceText;

    @FXML
    private Label RNumberOfReviewsText;

    @FXML
    private Label RAvailabilityText;

    //the current property on the left and right should be stored
    private AirbnbListing currentLeftProperty;
    private AirbnbListing currentRightProperty;

    // labels for the comparisons
    @FXML
    private Label betterPriceText;

    @FXML
    private Label betterAvailabilityText;

    @FXML
    private Label betterPopularityText;

    @FXML
    private Label betterListingText;

    private boolean instructionsShown = false;

    /**
     * Sets the favourites panel
     */
    public void setPanel(){
        if(stats.getFavouriteProperties().size()==0){
            noFavePropertiesAlert();
        }
        else if(!instructionsShown){
            howToUsePanel();
            instructionsShown = true;
        }

        hideComparisonLabels();

        //hiding the left and right displays
        propertyDisplayLeft.setVisible(false);
        propertyDisplayRight.setVisible(false);

        if(faveProperties.size() != stats.getFavouriteProperties().size()){//only sets panel if the faveproperties have changed
            hideComparisonLabels();

            //get the fave properties that have been selected by the user
            faveProperties = (ArrayList)stats.getFavouriteProperties().clone();

            //clears the observable list
            items.clear();

            // go through fave properties, get names and add to observable list, then add observable list to list view
            for(AirbnbListing listing : faveProperties){

                String name = listing.getName();

                //add name of property and actual property to a hashmap
                actualProperty.put(name ,listing);

                //this is adding the names of the properties to the observable list
                items.add(name);

            }

            // adding observable list to each list view
            leftList.setItems(items);

            rightList.setItems(items);

            //hiding the left and right displays
            propertyDisplayLeft.setVisible(false);

            propertyDisplayRight.setVisible(false);
        }
    }

    /**
     * Shows information about the property selected on the left.
     */
    @FXML
    private void showPropertyPressedLeft(ActionEvent event){
        hideComparisonLabels();

        if(leftList.getSelectionModel().getSelectedItem() != null){
            String s = leftList.getSelectionModel().getSelectedItem();
            currentLeftProperty = actualProperty.get(s);
            displayPropertyOnLeft(currentLeftProperty);
        }
        else{
            noFavePropertiesSelectedAlert();
        }
    }

    /**
     * Shows property selected on the left in Google Maps.
     */
    @FXML
    private void showInMapPressedLeft(ActionEvent event) throws Exception{
        if(currentLeftProperty != null){
            viewMap(currentLeftProperty.getLongitude(),currentLeftProperty.getLatitude());
        }else{
            noFavePropertiesSelectedAlert();
        }
    }

    /**
     * Shows information about the property selected on the right.
     */
    @FXML
    private void showPropertyPressedRight(ActionEvent event){
        hideComparisonLabels();

        if(rightList.getSelectionModel().getSelectedItem() != null){
            String s = rightList.getSelectionModel().getSelectedItem();
            currentRightProperty = actualProperty.get(s);
            displayPropertyOnRight(currentRightProperty);
        }
        else{
            noFavePropertiesSelectedAlert();
        }
    }

    /**
     * Shows property selected on the right in Google Maps.
     */
    @FXML
    private void showInMapPressedRight(ActionEvent event) throws Exception{
        if(currentRightProperty != null){
            viewMap(currentRightProperty.getLongitude(),currentRightProperty.getLatitude());
        }
        else{
            noFavePropertiesSelectedAlert();
        }
    }

    /**
     * Compares the price of the property selected on the left and the property selected on the right.
     */
    @FXML
    private void comparePriceButton(ActionEvent event) throws Exception{
        if (!propertyDisplayRight.isVisible() || !propertyDisplayLeft.isVisible()){
            noPropertiesSelectedAlert();
        }
        else{
            AirbnbListing betterListing = comparePrice();

            if(betterListing == null){
                betterPriceText.setText("both have the same price.");
            }else{
                betterPriceText.setText(betterListing.getName()+" has a better price.");
            }

            betterPriceText.setVisible(true);
        }
    }

    /**
     * Compares the availability of the property selected on the left and the property selected on the right.
     */
    @FXML
    private void compareAvailabilityButton(ActionEvent event) throws Exception{
        if (!propertyDisplayRight.isVisible() || !propertyDisplayLeft.isVisible()){
            noPropertiesSelectedAlert();
        }
        else{
            AirbnbListing betterListing = compareAvailability();

            if(betterListing == null){
                betterAvailabilityText.setText("both have the same availability.");
            }else{
                betterAvailabilityText.setText(betterListing.getName()+" has better availability.");
            }

            betterAvailabilityText.setVisible(true);
        }
    }

    /**
     * Compares the popularity of the property selected on the left and the property selected on the right.
     */
    @FXML
    private void comparePopularityButton(ActionEvent event) throws Exception{
        if (!propertyDisplayRight.isVisible() || !propertyDisplayLeft.isVisible()){
            noPropertiesSelectedAlert();
        }
        else{
            AirbnbListing betterListing = comparePopularity();

            if(betterListing == null){
                betterPopularityText.setText("both have the same popularity.");
            }else{
                betterPopularityText.setText(betterListing.getName()+" has better popularity.");
            }

            betterPopularityText.setVisible(true);
        }
    }

    /**
     * Compares the property selected on the left and the property selected on the right.
     */
    @FXML
    private void compareOverallButton(ActionEvent event) throws Exception{
        if (!propertyDisplayRight.isVisible() || !propertyDisplayLeft.isVisible()){
            noPropertiesSelectedAlert();
        }
        else{
            AirbnbListing betterListing =compareOverall();

            //set the text for the betterListing label
            if(betterListing == null){
                betterListingText.setText("both listings are equally good.");
            }else{
                betterListingText.setText(betterListing.getName()+" is the better listing.");
            }

            betterListingText.setVisible(true);
        }
    }

    /**
     * Event handler for when the help button is clicked.
     * Displays the instruction alert via the howToUsePanel()
     * @param event the event generated
     */
    @FXML
    public void handleHelpButton(ActionEvent event) throws Exception{
        howToUsePanel();
    }

    /**
     * Displays information about the property selected on the left.
     * The labels for the property on the left are set.
     */
    private void displayPropertyOnLeft(AirbnbListing a){

        LNameText.setText(a.getName());

        LHostNameText.setText(a.getHostName());

        LNeighbourhoodText.setText(a.getNeighbourhood());

        LRoomTypeText.setText(a.getRoom_type());

        LPriceText.setText(Integer.toString(a.getPrice()));

        LNumberOfReviewsText.setText(Integer.toString(a.getNumberOfReviews()));

        LAvailabilityText.setText(Integer.toString(a.getAvailability365()));

        propertyDisplayLeft.setVisible(true);

    }

    /**
     * Displays information about the property selected on the right.
     * The labels for the property on the right are set.
     */
    private void displayPropertyOnRight(AirbnbListing a){

        RNameText.setText(a.getName());

        RHostNameText.setText(a.getHostName());

        RNeighbourhoodText.setText(a.getNeighbourhood());

        RRoomTypeText.setText(a.getRoom_type());

        RPriceText.setText(Integer.toString(a.getPrice()));

        RNumberOfReviewsText.setText(Integer.toString(a.getNumberOfReviews()));

        RAvailabilityText.setText(Integer.toString(a.getAvailability365()));

        propertyDisplayRight.setVisible(true);

    }

    /**
     * This method opens the system's default internet browser
     * The Google Maps page should show the current properties' location on the map.
     * @param longitude - longitude of the property you want to view in maps
     * @param latitude - latitude of the property you want to view in maps
     */
    public void viewMap(double longitude, double latitude) throws Exception
    {
        URI uri = new URI("https://www.google.com/maps/place/" + latitude + "," + longitude);
        java.awt.Desktop.getDesktop().browse(uri);
    }

    /**
     * Compares the price of the current listing on the left and the current listing on the right.
     * @return AirbnbListing return listing with the lowest price - if both are same price return null
     */
    private AirbnbListing comparePrice(){
        double lPrice = currentLeftProperty.getPrice();
        double rPrice = currentRightProperty.getPrice();

        // return the property with the better price
        if(lPrice>rPrice){
            return currentRightProperty;
        }
        else if(rPrice>lPrice){
            return currentLeftProperty;
        }
        else{//if equal
            return null;
        }

    }

    /**
     * Compares the availability of the current listing on the left and the current listing on the right.
     * @return AirbnbListing return listing with the most availability - if both are same availability return null
     */
    private AirbnbListing compareAvailability(){
        double lAvailability = currentLeftProperty.getAvailability365();
        double rAvailability = currentRightProperty.getAvailability365();

        //return the more available property
        if(lAvailability>rAvailability){
            return currentLeftProperty;
        }
        else if(rAvailability>lAvailability){
            return currentRightProperty;
        }
        else{//if equal
            return null;
        }

    }

    /**
     * Compares the popularity of the current listing on the left and the current listing on the right.
     * @return AirbnbListing return listing with the most popularity - if both are same popularity return null
     */
    private AirbnbListing comparePopularity(){

        double lPopularity = currentLeftProperty.getNumberOfReviews();
        double rPopularity = currentRightProperty.getNumberOfReviews();

        //return the more popular property
        if(lPopularity>rPopularity){
            return currentLeftProperty;
        }
        else if(rPopularity>lPopularity){
            return currentRightProperty;
        }
        else{//if equal
            return null;
        }

    }

    /**
     * Compares the current listing on the left and the current listing on the right.
     * This will compare price, availability and popularity and return the best overall property.
     * @return AirbnbListing return the better listing - if both are same return null
     */
    private AirbnbListing compareOverall(){
        //each property gets points depending on which is better
        int lPoints = 0;
        int rPoints = 0;

        comparePrice();
        compareAvailability();
        comparePopularity();

        //add points to which property is better, if equal add no points
        if(comparePrice()==currentLeftProperty){
            lPoints++;
        }
        else if(comparePrice()==currentRightProperty){
            rPoints++;
        }

        if(compareAvailability()==currentLeftProperty){
            lPoints++;
        }
        else if(compareAvailability()==currentRightProperty){
            rPoints++;
        }

        if(comparePopularity()==currentLeftProperty){
            lPoints++;
        }
        else if(comparePopularity()==currentRightProperty){
            rPoints++;
        }

        //return the better property - property with most points
        if(lPoints>rPoints){
            return currentLeftProperty;
        }
        else if(rPoints>lPoints){
            return currentRightProperty;
        }
        else{
            return null;
        }

    }

    /**
     * Hides the comparison labels
     */
    private void hideComparisonLabels(){
        betterPriceText.setVisible(false);
        betterAvailabilityText.setVisible(false);
        betterPopularityText.setVisible(false);
        betterListingText.setVisible(false);
    }

    /**
     * Alerts the user to select a fave property to display.
     */
    private void noFavePropertiesSelectedAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Select a favourite property to display.");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }

    /**
     * Alerts the user to select 2 fave properties to compare.
     */
    private void noPropertiesSelectedAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Select 2 favourite properties to compare.");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }

    /**
     * Alerts the user to select a fave properties.
     */
    private void noFavePropertiesAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("You have not selected any favourite properties. \nGo back and select at least 2 favourite properties to compare.");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }

    /**
     * Informs the user how to use the panel by creating an alert box.
     */
    private void howToUsePanel(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Instructions");
        alert.setContentText("Select properties to show their details. Show one on the right and one on the left. Once you have shown them you can view them in Google Maps and once you have shown 2 properties you can compare them. You can compare them based on price, popularity, availability and you can also see an overall comparison.");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }


}
