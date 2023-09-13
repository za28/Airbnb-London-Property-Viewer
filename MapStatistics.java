import java.util.*;
/**
 * This class is used to handle the statistics for the map panel.
 * It contains a hashmap linking the abbreviated name to the full name of the boroughs.
 * This class handles updating the starting and end price and also updates the favourites
 * properties list.
 * It also handles sorting properties lists bases on their prices, reviews and minimum nights
 * @author Areeba Safdar(k20045738) Imaan Ghafur(k2102260)
 * Sabeeka Ahmad(k20012890) Zahra Amaan(k21011879)
 * @version 03/2022
 */
public class MapStatistics {

    private static int startingPrice = 0;
    private static int endPrice = 0;
    private static ArrayList<AirbnbListing> favouriteProperties = new ArrayList<>();
    private static ArrayList<AirbnbListing> loadedProperties = new ArrayList<>();

    private HashMap<String, String> boroughNames; //Stores abbreviation and full name
    private AirbnbDataLoader loader;

    /**
     * Loads all properties via the dataloader class and populates
     * the hashmap with borough names.
     */
    public MapStatistics() {
        loader = new AirbnbDataLoader();
        loadedProperties = loader.load();

        boroughNames = new HashMap<>();
        setBoroughNames();
    }

    /**
     * Defines the key-value paris of the borough names and their abbreviated versions
     */
    public void setBoroughNames() {
        boroughNames.put("ENFI", "Enfield");
        boroughNames.put("BARN", "Barnet");
        boroughNames.put("HRGY", "Haringey");
        boroughNames.put("WALT", "Waltham Forest");
        boroughNames.put("HRRW", "Harrow");
        boroughNames.put("BREN", "Brent");
        boroughNames.put("CAMD", "Camden");
        boroughNames.put("ISLI", "Islington");
        boroughNames.put("HACK", "Hackney");
        boroughNames.put("REDB", "Redbridge");
        boroughNames.put("HAVE", "Havering");
        boroughNames.put("HILL", "Hillingdon");
        boroughNames.put("EALI", "Ealing");
        boroughNames.put("KENS", "Kensington and Chelsea");
        boroughNames.put("WSTM", "Westminster");
        boroughNames.put("TOWH", "Tower Hamlets");
        boroughNames.put("NEWH", "Newham");
        boroughNames.put("BARK", "Barking and Dagenham");
        boroughNames.put("HOUN", "Hounslow");
        boroughNames.put("HAMM", "Hammersmith and Fulham");
        boroughNames.put("WAND", "Wandsworth");
        boroughNames.put("CITY", "City of London");
        boroughNames.put("GWCH", "Greenwich");
        boroughNames.put("BEXL", "Bexley");
        boroughNames.put("RICH", "Richmond upon Thames");
        boroughNames.put("MERT", "Merton");
        boroughNames.put("LAMB", "Lambeth");
        boroughNames.put("STHW", "Southwark");
        boroughNames.put("LEWS", "Lewisham");
        boroughNames.put("KING", "Kingston upon Thames");
        boroughNames.put("SUTT", "Sutton");
        boroughNames.put("CROY", "Croydon");
        boroughNames.put("BROM", "Bromley");
    }

    /**
     * Returns the full name of the borough based on the abbreviation given
     * @param abbreviation the short-form name of the borough
     * @return hte full name of the borough
     */
    public String getBoroughFullName(String abbreviation) {
        return boroughNames.get(abbreviation);
    }

    /**
     * Sets startingPrice to the price given
     * @param price the value to set the startingPrice
     */
    public void setStartingPrice(int price){
        startingPrice = price;
    }

    /**
     * Sets endPrice to the price given
     * @param price the value to set the endPrice
     */
    public void setEndPrice(int price){
        endPrice = price;
    }

    /**
     * @return the startingPrice
     */
    public int getStartingPrice(){
        return startingPrice;
    }

    /**
     * @return the endPrice
     */
    public int getEndPrice(){
        return endPrice;
    }

    /**
     * Adds a given listing to the favouriteProperties list if it's not already
     * in the list. Returns true is the siting has been added, otherwise false is returned.
     * @param listing the property being added
     * @return true if listing has been added - otherwise false
     */
    public boolean addFavProperty(AirbnbListing listing){
        if(!favouriteProperties.contains(listing)){
            favouriteProperties.add(listing);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Returns the favouriteProperties ArrayList
     * @return favouriteProperties
     */
    public ArrayList<AirbnbListing> getFavouriteProperties(){
        return favouriteProperties;
    }

    /**
     * Used to return all the properties within a borough.
     * Loads all properties and removes any property from the ArrayList if it's neighbourhood
     * is not the same as the borough given as a parameter.
     * @param boroughName the borough used to find all the properties within it
     * @return boroughProperties A list of all the properties within the specified borough
     */
    public ArrayList<AirbnbListing> getBoroughProperties(String boroughName) {
        ArrayList<AirbnbListing> boroughProperties = new ArrayList<>(loadedProperties);
        boroughProperties.removeIf(listing -> !(listing.getNeighbourhood().equals(boroughName)));
        return boroughProperties;
    }

    /**
     * Takes an arrayList of properties and returns the properties within a specified
     * price range.
     * @param properties the list of properties being checked
     * @return the new ArrayList containing properties within the starting and end price
     */
    public List<AirbnbListing> getPropertiesInAPriceRange(List<AirbnbListing> properties){
        properties.removeIf(listing -> listing.getPrice() < startingPrice || listing.getPrice() > endPrice);
        return properties;
    }

    /**
     * Sorts a given a list of properties alphabetically by their host name (A-Z).
     * Using the java's built in sort method
     * @param listing the sorted list.
     */
    public static List<AirbnbListing> sortByHostName(List<AirbnbListing> listing) {
        listing.sort(Comparator.comparing(AirbnbListing::getHostName));
        return listing;
    }

    /**
     * Sorts a given a list of properties by their price. The property with
     * the lowest price is as the front of the list.
     * Using the java's built in sort method
     * @param listing the sorted list.
     */
    public static List<AirbnbListing> sortByPrice(List<AirbnbListing> listing) {
        listing.sort(Comparator.comparing(AirbnbListing::getPrice));
        return listing;
    }

    /**
     * Sorts a given a list of properties by their number of reviews.
     * The property with the highest number of reviews is at the front
     * Using the java's built in sort method
     * @param listing the sorted list.
     */
    public static List<AirbnbListing> sortByReview(List<AirbnbListing> listing) {
        listing.sort(Comparator.comparing(AirbnbListing::getNumberOfReviews).reversed());
        return listing;
    }

}

