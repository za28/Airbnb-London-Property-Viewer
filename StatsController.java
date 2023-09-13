import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is the controller for the statistics panel
 * In this class we set the labels and buttons
 * Initialise comboBox, set the columns and the corresponding data to be filled in
 * @author Areeba Safdar(k20045738) Imaan Ghafur(k2102260)
 * Sabeeka Ahmad(k20012890) Zahra Amaan(k21011879)
 * @version 03/2022
 */

public class StatsController {
    // ArrayLists to hold the texts of each label
    private ArrayList display1 = new ArrayList<String>();
    private ArrayList display2 = new ArrayList<String>();
    private ArrayList display3 = new ArrayList<String>();
    private ArrayList display4 = new ArrayList<String>();

    // initializing the display index of each menu component to 0
    private int display1Index, display2Index, display3Index, display4Index = 0;

    /**
     * Created an enum for the different attractions that the user is able to select from the dropdown menu
     */
    enum attractionOptions {
        LONDON_EYE,
        BIG_BEN,
        ST_PAULS,
        TOWER_OF_LONDON,
        HYDE_PARK,
        THE_O2,
        WESTMINISTER_ABBEY,
        HARRODS

    }

    private StatisticsStats statisticsStats = new StatisticsStats();
    private ShowWeather weather = new ShowWeather();
    private AttractionDataLoader data1 = new AttractionDataLoader();
    AttractionDataLoader attractionLoader = new AttractionDataLoader();
    ArrayList<AttractionListing> attractions = attractionLoader.load();


    @FXML
    Label averages;
    @FXML
    Label graphs;
    @FXML
    Label totals;
    @FXML
    Label weatherAndDistance;


    @FXML
    Button next1;
    @FXML
    Button next2;
    @FXML
    Button next3;
    @FXML
    Button next4;
    @FXML
    Button back1;
    @FXML
    Button back2;
    @FXML
    Button back3;
    @FXML
    ComboBox<attractionOptions> attractionsComboBox;

    /**
     * Here I have set the labels to what they should be when the panel is loaded
     */
    @FXML
    public void initialize() {
        attractionsComboBox.getItems().addAll(attractionOptions.values());
        setComboBoxCells();

        initializeShowItems();

        showInitial();


    }

    /**
     * This method is used to set custom text for the attraction options of the attraction comboBox.
     * setCellFactory method is called on 'attractionComboBox, which implements the 'Callback'
     * interface. This interface takes two generic type parameters and returns
     * the 2nd parameter type. In this case a ListCell is being returned which will be used as
     * a parameter to the setCellFactory method. This cell contains the user-friendly
     * message for each combBox option.
     */

    public void setComboBoxCells() {
        attractionsComboBox.setCellFactory(new Callback<ListView<attractionOptions>, ListCell<attractionOptions>>() {
            @Override
            public ListCell<attractionOptions> call(ListView<attractionOptions> sortingOptionsListView) {
                return new ListCell<attractionOptions>() {
                    @Override
                    public void updateItem(attractionOptions option, boolean empty) {
                        super.updateItem(option, empty);
                        if (option != null) {
                            switch (option) {
                                case LONDON_EYE:
                                    setText("London Eye");
                                    break;
                                case BIG_BEN:
                                    setText("Big Ben");
                                    break;
                                case ST_PAULS:
                                    setText("St Paul's Cathedral");
                                    break;
                                case HYDE_PARK:
                                    setText("Hyde Park");
                                    break;
                                case TOWER_OF_LONDON:
                                    setText("Tower of London");
                                    break;
                                case THE_O2:
                                    setText("The O2");
                                    break;
                                case WESTMINISTER_ABBEY:
                                    setText("Westminister Abbey");
                                    break;
                                case HARRODS:
                                    setText("Harrods");
                                    break;
                            }
                        }
                    }
                };
            }
        });
    }

    ;

    @FXML
    /**
     * Sets the attraction to the one selected by the user
     * checks the distance and finds the nearest properties to this attraction
     *
     */
    public void displayNearestProperties() {
        AttractionListing selectedAttraction = null;
        attractionOptions optionSelected = attractionsComboBox.getValue();
        switch (optionSelected) {
            case LONDON_EYE:
                selectedAttraction = getAttraction("London eye");

                break;
            case BIG_BEN:
                selectedAttraction = getAttraction("Big ben");

                break;
            case ST_PAULS:
                selectedAttraction = getAttraction("St Paul's Cathedral");

                break;
            case HYDE_PARK:
                selectedAttraction = getAttraction("Hyde Park");

                break;
            case TOWER_OF_LONDON:
                selectedAttraction = getAttraction("Tower of London");

                break;
            case THE_O2:
                selectedAttraction = getAttraction("The O2");
                break;
            case WESTMINISTER_ABBEY:
                selectedAttraction = getAttraction("Westminister Abbey");
                break;
            case HARRODS:
                selectedAttraction = getAttraction("Harrods");
                break;

        }

        ArrayList<AirbnbListing> closestProperties = statisticsStats.checkDistance(selectedAttraction);

        try {
            Stage stage = new Stage();
            NearestPropertyController nearestPropertyController = new NearestPropertyController(closestProperties);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("NearestPropertiesWindow.fxml"));
            loader.setController(nearestPropertyController); //Set controller here as passing in closestProperteis
            Parent nearestPropertyWindow = loader.load();

            Scene scene = new Scene(nearestPropertyWindow, 900, 500);
            stage.setTitle("Properties within 1 mile");
            stage.setScene(scene);

            //Blocks any event from being delivered to any other application window
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Using the combobox the user is able to select an attraction the name if this selected attraction is passed into his method and the object is returned
     *
     * @param name
     * @return the attraction that is selected by the user
     */
    public AttractionListing getAttraction(String name) {
        for (AttractionListing attraction : attractions) {
            if (attraction.getName().equals(name)) {
                return attraction;
            }
        }
        return null;

    }

    /**
     * Shows the average number of reviews for all of the properties
     * This is achieved by calling the averageReviews() methods in the StatisticsStats class
     * The text of te averages label is set to the number of average reviews along with some text
     */

    public void reviews() {
        averages.setText(String.valueOf("Average number of reviews:\n" + statisticsStats.averageReviews()));

    }

    /**
     * Shows how many properties are available
     * This is achieved by calling the available() method from the StatisticsStats panel
     */

    public void available() {
        graphs.setText(String.valueOf("Number of available properties:\n" + statisticsStats.available()));

    }

    public void something() {
        graphs.setText("hi");

    }

    /**
     * Generates a graph using the appropriate HashMap (generates review or prices graph)
     *
     * @param hashMap
     * @param graphType
     */

    public void graph(HashMap<String, Integer> hashMap, String graphType) {

        try {
            FXMLLoader loader = new FXMLLoader();
            if (graphType.equals("prices")) {
                loader = new FXMLLoader(getClass().getResource("PricesGraphView.fxml"));
            } else {
                loader = new FXMLLoader(getClass().getResource("ReviewsGraphView.fxml"));
            }

            Parent graphPane = loader.load();
            GraphController graphController = loader.getController();
            graphController.setHashMap(hashMap);
            graphController.addPricesData(graphType);

            Stage stage = new Stage();


            Scene scene = new Scene(graphPane, 800, 500);
            stage.setTitle("Average" + "" + graphType + " for London Boroughs");
            stage.setScene(scene);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void homes() {
        weatherAndDistance.setText(String.valueOf("Number of homes \n" + statisticsStats.homes()));
    }

    /**
     * These 2 methods will generate a different graph by passing a different hashmap and graph type into the graph() method
     */

    public void showPricesGraph() {
        graph(statisticsStats.getAveragePrice(), "prices");
    }

    public void showReviewsGraph() {
        graph(statisticsStats.getAverageReviews(), "reviews");
    }

    /**
     * @param currentIndex
     * @param options
     * @return the new index value after incrementing.
     */
    private int incrementIndex(int currentIndex, ArrayList options) {
        if (currentIndex + 1 < options.size()) {
            return ++currentIndex;
        }
        return currentIndex = 0;
    }

    /**
     * @param currentIndex
     * @param options
     * @return the new index value after decrementing.
     */
    private int decrementIndex(int currentIndex, ArrayList options) {
        if (currentIndex - 1 >= 0) {
            return --currentIndex;
        }
        return options.size() - 1;
    }

    /**
     * The strings to be the text set on the labels are added to the label's Arraylists.
     */
    private void initializeShowItems() {
        display1.add("Average number of reviews:\n" + statisticsStats.averageReviews());
        display1.add("The most expensive borough is:\n" + statisticsStats.getMostExpensiveBorough());

        display2.add("Click button to vew average prices graph");
        display2.add("Click button to vew average reviews graph");

        display3.add("Number of available properties:\n" + statisticsStats.available());
        display3.add("Number of homes \n" + statisticsStats.homes());

        display4.add("Pick an attraction to see the nearest properties");
        display4.add("The current temperature in London is:\n" + weather.getTemp().get(0) + "°C\n"+ "maximum temperature is:\n" + weather.getTemp().get(1) + "°C\n "+"minumum temperature:\n"+weather.getTemp().get(2)+"°C" );
    }

    /**
     * Setting the initial values of all the labels by getting the first value in the respective arrays
     */
    private void showInitial() {
        averages.setText(String.valueOf(display1.get(0)));
        graphs.setText(String.valueOf(display2.get(0)));
        totals.setText(String.valueOf(display3.get(0)));
        weatherAndDistance.setText(String.valueOf(display4.get(0)));
    }

    /**
     * The method for when the user clicks a button on the first panel.
     * When the next button is pressed for the 1st panel the index is incremented, when the back button is pressed in index is decremeted
     * A different value and message is shown depending on the index
     */
    @FXML
    private void panel1Buttons(ActionEvent event) {
        Button button = (Button) event.getSource();
        String id = button.getId();
        //Scene scene = source.getScene();
        if (id.equals(next1.getId())) {
            display1Index = incrementIndex(display1Index, display1);
        } else {
            display1Index = decrementIndex(display1Index, display1);

        }
        averages.setText(String.valueOf(display1.get(display1Index)));
    }



    /**
     * The methods for when the user clicks a button on the second panel.
     * When the next button is pressed for the 2nd panel the index is incremented when the back button is pressed the index is decremented
     * A different graph and message is shown depending on the index
     */
    @FXML

    private void panel2Buttons(ActionEvent event) {
        Button button = (Button) event.getSource();
        String id = button.getId();


        if (id.equals(next2.getId())) {
            display2Index = incrementIndex(display2Index, display2);
        } else {
            display2Index = decrementIndex(display2Index, display2);

        }
        graphs.setText(String.valueOf(display2.get(display2Index)));
    }


    /**
     * The methods for when the user clicks the 'view graph' button on the third panel.
     * The allocated graph for the label is shown in a pop-up.
     * depending on the index a different graph is made available
     */
    @FXML
    private void showGraph() {
        if (display2Index == 0) {
            showPricesGraph();
        } else {
            showReviewsGraph();
        }
    }

    /**
     * The methods for when the user clicks the next button on the third panel.
     * When the next button is pressed for the 3rd panel the index is incremented
     * A different value and message is shown depending on the index
     */
    @FXML
    private void panel3Buttons(ActionEvent event){
        Button button = (Button) event.getSource();
        String id = button.getId();


        if (id.equals(next3.getId())) {
            display3Index = incrementIndex(display3Index, display3);
        } else {
            display3Index = decrementIndex(display3Index, display3);

        }
        totals.setText(String.valueOf(display3.get(display3Index)));


    }



    /**
     * The methods for when the user clicks a button on the fourth panel.
     * When the next button is clicked the index is incremented when the back button is clicked the index is decremented
     * depending on the index the comboBox is shown
     * depending on the index a different message is shown
     */
    @FXML
    private void panel4Buttons(ActionEvent event){
        Button button = (Button) event.getSource();
        String id = button.getId();


        if (id.equals(next4.getId())) {
            display4Index = incrementIndex(display4Index, display4);
        } else {
            display4Index = decrementIndex(display4Index, display4);

        }
        if (weather.getTemp() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No internet Connect");
            alert.setContentText("Connect WiFi to view statistics!");
            alert.show();
            return;
        } else {

            weatherAndDistance.setText(String.valueOf(display4.get(display4Index)));
            if (display4Index > 0) {
                attractionsComboBox.setVisible(false);
            } else {
                attractionsComboBox.setVisible(true);
            }
        }


    }



}

