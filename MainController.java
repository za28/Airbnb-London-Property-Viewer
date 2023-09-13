
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * PropertyViewer class is the main window for the application.
 * The widow to be seen by the user.
 * All FXML files are appropriately arranged on screen here.
 * The user picks their price range here to start using the application.
 *
 * @author Areeba Safdar(k20045738) Imaan Ghafur(k2102260)
 * Sabeeka Ahmad(k20012890) Zahra Amaan(k21011879)
 * @version 03/2022
 */
public class MainController extends Application
{
    private static MapStatistics mapStatistics;

    private Stage stage;
    private ArrayList panels;
    private int panelIndex;

    private MapController mapPanelController;
    private FavouritePanelController favouritePanelController;

    private ObservableList<Integer> fromBoxList = FXCollections.observableArrayList(0, 25, 50, 75, 100, 200, 300, 400, 500 ,600, 700, 800, 900, 1000, 2000, 3000, 40000, 5000, 6000, 7000, 8000, 9000, 10000);

    // main window FXML ids
    @FXML
    private Pane centre;
    @FXML
    private BorderPane window;
    @FXML
    private ComboBox fromBox;
    @FXML
    private ComboBox toBox;
    @FXML
    private Button leftButton;
    @FXML
    private Button rightButton;

    @FXML
    private Pane top;
    @FXML
    private Pane bottom;

    /**
     * Constructor for objects of class PropertyViewer.
     * All initial values and lists are initialised here.
     * @throws IOException
     */
    public MainController() throws IOException
    {
        mapStatistics = new MapStatistics();
        panelIndex = 0;
        panels = new ArrayList<Pane>();
        addPanels();
    }

    /**
     * The main entry point for JavaFX programs.
     * @throws IOException
     * @param stage
     */
    @Override
    public void start(Stage stage) throws IOException
    {
        this.stage = stage;

        URL url = getClass().getResource("mainWindow.fxml");
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles.css");

        stage.setTitle("Air bnb");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method to return the loaded panels using their string name.
     * @param fileName name of the FXML file
     * @return the loaded pane
     */
    private Pane loadPanel(String fileName) throws IOException {
        URL panelurl = getClass().getResource(fileName);
        FXMLLoader loader = new FXMLLoader(panelurl);
        Pane panel = loader.load();

        if(fileName.equals("MapPanel.fxml")){
            mapPanelController = loader.getController();
        }
        else if(fileName.equals("FavouritePanel.fxml")){
            favouritePanelController = loader.getController();
        }

        return panel;
    }

    /**
     * @param key pressed event.
     * If the right key is pressed and the buttons are active, this switches to next panel.
     * @throws IOException
     */
    @FXML
    private void onPressRight(KeyEvent ev) throws IOException{
        if(ev.getCode() == KeyCode.RIGHT){
            showNextPanel();
        }   
    }

    /**
     * @param key pressed event.
     * If the right key is pressed and the buttons are active, this switches to previous panel.
     * @throws IOException
     */
    @FXML
    private void onPressLeft(KeyEvent ev) throws IOException{
        if(ev.getCode() == KeyCode.LEFT){
            showPreviousPanel();
        }   
    }

    /**
     * This method loads and adds all the panels that will be viewed 
     * throughout the use of the app to an ArrayList.
     * 
     * @throws IOException
     */
    private void addPanels() throws IOException{

        Pane welcomePanel = loadPanel("WelcomePanel.fxml");
        Pane favouritePanel = loadPanel("FavouritePanel.fxml");
        Pane mapPanel = loadPanel("MapPanel.fxml");

        panels.add(welcomePanel);
        panels.add(mapPanel);
        panels.add(favouritePanel);
    }

    /**
     * This method initilized the main window.
     * The buttons are initially disabled so that they only 
     * become available after a price range is selected.
     * Options are added to the combobox for the user to select
     * a 'from' and 'to' price for the range.
     */
    @FXML
    public void initialize() throws IOException {
        fromBox.setItems(fromBoxList);
        toBox.setItems(fromBoxList);
        leftButton.setDisable(true);
        rightButton.setDisable(true);

        // Welcome panel is shown as soon as application is launched.
        updateScene();
    }

    /**
     * Method to initialise values in the combobox dropdown menu.
     * Numbers adde to fromBox and toBox.
     * @throws IOException
     */

    private void updateScene(){
        Pane newPanel = (Pane) panels.get(panelIndex);
        if(panelIndex==2){
            favouritePanelController.setPanel();
        }
        window.setCenter(newPanel);
    }

    /**
     * Method the clear the current panel on screen.
     * Called before update to make sure panels are
     * not on top of each other.
     */
    private void clearScene(){
        Pane currentPanel = (Pane) panels.get(panelIndex);
        window.getChildren().remove(currentPanel);
    }

    /**
     * Checks the range the user selects is valid.
     * Checks that the from value isn't more than the to value,
     * or else buttons are disabled and an error message is displayed.
     * If the price range is incorrect the user is taken back to main page.
     */
    @FXML
    public void checkInput() throws IOException {
        if(fromBox.getValue() != null && toBox.getValue() != null){
            int fromValue = (int) fromBox.getValue();
            int toValue = (int) toBox.getValue();

            //Storing the prices that user has set
            mapStatistics.setStartingPrice(fromValue);
            mapStatistics.setEndPrice(toValue);

            if(fromValue < toValue){
                leftButton.setDisable(false);
                rightButton.setDisable(false);
            } else {
                leftButton.setDisable(true);
                rightButton.setDisable(true);

                //Displaying error message for incorrect price range and moving back to main page
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Invalid Price Range!");
                alert.show();
                panelIndex = 0;
                updateScene();
            }
        }

        //Handling map colour based on new price range
        if(mapStatistics.getStartingPrice() < mapStatistics.getEndPrice()){
            mapPanelController.setBoroughColours();
        }

    }

    /**
     * Method to show the next panel when the next button
     * is selected.
     */
    @FXML
    public void showNextPanel() throws IOException{
        // show next panel on the click of the right button
        clearScene();
        // if the last panel had been reached, the panelIndex is set to 0, so panels loop around.
        if(panelIndex + 1 < panels.size()){
            panelIndex += 1;
        } else {
            panelIndex = 0;
        }
        updateScene();
    }

    /**
     * Method to show the previous panel in the ArrayList
     * when the back button is selected.
     */
    @FXML
    public void showPreviousPanel(){
        // show previous panel on the click of the left button
        clearScene();
        // if the last panel had been reached, the panelIndex is set to max, so panels loop around.
        if (panelIndex - 1 >= 0){
            panelIndex -= 1;
        } else {
            panelIndex = panels.size() - 1;
        }
        updateScene();
    }

    public static void main (String[] args){
        launch(args);
    }
}
