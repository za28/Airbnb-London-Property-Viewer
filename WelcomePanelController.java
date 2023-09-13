import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;

import javafx.scene.control.*;

/**
 * This class is the controller for the 'WelcomePanel.fxml' class.
 * It sets the initials welcome text to be displayed in the welcome window.
 *
 * @author Areeba Safdar(k20045738) Imaan Ghafur(k2102260)
 * Sabeeka Ahmad(k20012890) Zahra Amaan(k21011879)
 * @version 03/2022
 */
public class WelcomePanelController
{

    @FXML
    private Label welcomeTitleLabel;
    @FXML
    private Label instructionsLabel;    

    private String WelcomeText = "Welcome to the airbnb Property Viewer. \n \n";
    private String instructionText = "This is an application for exploring properties that are available for temporary rental in London.\n"
        +"The application consists of 3 pages:\n"
        +"1. A welcome page (this page)\n"
        +"2. A map page - on this page you can select a borough from the map and view properties available in that borough. " +
        "The properties displayed will be in the price range selected. If you like a property, you can add it to your favourites. " +
        "This page also contains statistics about the boroughs - accessed via a button.\n"
        +"3. A favourites page - on this page you can view all your favourite properties and compare them\n"
        +"Select a price range to begin.";


    /**
     * Sets text of labels of in the welcome window.
     */
    @FXML
    public void initialize() {
        welcomeTitleLabel.setText(WelcomeText);
        instructionsLabel.setText(instructionText);
    }
    
}
