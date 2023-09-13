import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * The controller binds the user interface to its logic
 * There are two user interfaces for GraphController which are PricesGraphView.fxml and ReviewsGraphView.fxml
 *  @author Areeba Safdar(k20045738) Imaan Ghafur(k2102260)
 *  Sabeeka Ahmad(k20012890) Zahra Amaan(k21011879)
 * @version 03/2022
 *
 */
public class GraphController {
    
    @FXML
    private BarChart barChart;

    private HashMap<String, Integer> information;
    public GraphController() {
        information = new HashMap<>();

    }

    /**
     * Sets the HashMap information to the hashmap that is passed in
     * @param map
     */

    public void setHashMap(HashMap<String, Integer> map) {

        information = map;

    }
    
    /**
     * Using the Hashmap I created when finding the most expensive borough I am populating the graph
     * the Key of the HashMap is the Borough which will be on the x-axis
     * the value of the HashMap is the average price of properties, this will be on the y-axis of the graph
     */

    public void addPricesData(String typeOfAverage) {

        XYChart.Series averages = new XYChart.Series();
        averages.setName(typeOfAverage);

        for (Map.Entry<String, Integer> entry : information.entrySet()) {
            String borough = entry.getKey();
            Integer average = entry.getValue();

            averages.getData().add(new XYChart.Data(borough, average));
        }

        barChart.getData().add(averages);
    }

}
