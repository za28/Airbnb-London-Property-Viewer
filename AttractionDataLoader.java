import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

/**
 * This class is used to load the data stores in the Attractions csv file.
 * 
 * @author Imaan Ghafur(k2102260), Areeba Safdar(k20045738)
 * Sabeeka Ahmad(k20012890) Zahra Amaan(k21011879)
 * @version 03/2022
 *
 */
public class AttractionDataLoader {

    /** 
     * Return an ArrayList containing the rows in the Attraction csv file
     */
    public ArrayList<AttractionListing> load(){
        ArrayList<AttractionListing> attractions = new ArrayList<AttractionListing>();

        try{
            URL url = this.getClass().getResource("Attractions.csv");
            CSVReader reader = new CSVReader(new FileReader((new File(url.toURI())).getAbsolutePath()));
            reader.readNext();
            String[] line;
            while((line = reader.readNext()) !=null){
                ArrayList attraction = new ArrayList();
                String name = line[0];
                double latitude = convertDouble(line[1]);
                double longitude = convertDouble(line[2]);
                AttractionListing attractionListing = new AttractionListing(name,longitude,latitude);
                attractions.add(attractionListing);

            }
        }catch(URISyntaxException | IOException var24) {
            System.out.println("Failure! Something went wrong");
            var24.printStackTrace();

        }
        return attractions;

    }
    
    /**
     *
     * @param doubleString the string to be converted to Double type
     * @return the Double value of the string, or -1.0 if the string is 
     * either empty or just whitespace
     */
    private Double convertDouble(String doubleString){
        if(doubleString != null && !doubleString.trim().equals("")){
            return Double.parseDouble(doubleString);
        }
        return -1.0;
    }

}