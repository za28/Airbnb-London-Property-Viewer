/**
 * Represents one listing of an attraction
 * This is essentially one row in the data table. Each column
 * has a corresponding field.
 * 
 * @author Imaan Ghafur(k2102260), Areeba Safdar(k20045738)
 * Sabeeka Ahmad(k20012890) Zahra Amaan(k21011879)
 * @version 03/2022
 */

public class AttractionListing {
    private double longitude;
    private double latitude;
    private String name;
    public AttractionListing(String name, double longitude, double latitude){
        this.name=name;
        this.longitude=longitude;
        this.latitude=latitude;
    }

    /**
     * returns the name of the attraction
     * @return String name
     */
    public String getName(){
        return name;
    }

    /**
     * returns the longitude of the attraction
     * @return double longitude
     */
    public double getLongitude(){
        return longitude;
    }

    /**
     * returns the latitude of the attraction
     * @return double latitude
     */
    public double getLatitude(){
        return latitude;
    }
}
