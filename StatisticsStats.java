
import java.util.*;

/**
 * This class is used to handle the statistics for the statistics panel
 * Here we calculate all the statistics that are shown on the statistics panel
 * the methods in this class are called in statistics controller in order to be shown on the panel
 * @author Areeba Safdar(k20045738) Imaan Ghafur(k2102260)
 * Sabeeka Ahmad(k20012890) Zahra Amaan(k21011879)
 * @version 03/2022
 *
 */

public class StatisticsStats {

    AirbnbDataLoader dataLoader = new AirbnbDataLoader();
    ArrayList<AirbnbListing> properties = dataLoader.load();
    public HashMap<String,Integer> prices = new HashMap<>();

    /**
     * To fine the average number of reviews (for all properties) I iterate through the ArrayList properties
     * for each listing within properties I use the getNumberOfReviews method to get the number of reviews and add this to the total
     * which has been set to 0 at the start
     * I then divide this total by the size of properties in order to get an average
     * @return the average by floor dividing total by number of listings
     */
    public int averageReviews() {
        int total = 0;

        for(int i=0; i<properties.size(); i++){
            AirbnbListing listing =properties.get(i);
            int reviews = listing.getNumberOfReviews();
            total=total+reviews;

        }
        return Math.floorDiv(total ,properties.size());

    }

    /**
     * if the availibility of the property is greater than 0 then the total is incremented
     * the total represents the number of available properties
     * @return total
     */
    public int available() {
        int total=0;
        for(int i=0; i< properties.size();i++){
            AirbnbListing listing=properties.get(i);
            if(listing.getAvailability365()>0){
                total++;
            }
        }return total;

    }

    /**
     * Calculates the total number of listings that are homes.
     * iterate through all the listings and if the room type is home then the counter is incremented by 1
     * @return total number of homes
     */
    public int homes() {
        int total = 0;
        for (int i = 0; i < properties.size(); i++) {
            AirbnbListing listing = properties.get(i);
            if (listing.getRoom_type().equals("Entire home/apt")) {
                total++;
            }
        }
        return total;

    }

    /**
     * Find the maximum value by using collections.max() method
     * iterate rounf the hashMap to find the key that has is mapped to this value
     * The borough (the Key) is returned
     * @return the most expensive borough
     */

    public String getMostExpensiveBorough() {
        HashMap<String,Integer> averagePrice= getAveragePrice();
        int maxValue = (Collections.max(averagePrice.values()));
        String expensive = null;
        for (Map.Entry<String, Integer> entry : averagePrice.entrySet()) {
            if (entry.getValue() == maxValue) {
                expensive = entry.getKey();
            }
        }
        return expensive;
    }

    /**
     * In getAveragePrice we are creating another HashMap the borough will again be the Key but the value will be the average price
     * To obtain the average price we first add up all the prices
     * then using the HashMap created in getListingsPerBorough we are able to generate to total by dividing the total of the prices
     * by the counter stored in the value of the getListingsPerBorough HashMap
     * @return HashMap
     */

    public HashMap<String,Integer> getAveragePrice() {

        HashMap<String, Integer> averagePrice = new HashMap<>();
        HashMap<String, Integer> listingsCount = getListingsPerBorough();
        for (AirbnbListing listing : properties) {
            // listing.getNeighbourhood is the key of this hashmap, and right now the value is the price x the minimum number of nights
            // the value will later change to the average price per borough
            if (averagePrice.containsKey(listing.getNeighbourhood())) {
                //if the key is already present this means we have found another property in the borough so we add the price to the value we originally have saved
                averagePrice.put(listing.getNeighbourhood(), averagePrice.get(listing.getNeighbourhood()) + (listing.getPrice() * listing.getMinimumNights()));

            } else {
                // if the borough of the property is not a key in out hashmap then we add it along with its minimum cost

                averagePrice.put(listing.getNeighbourhood(), listing.getPrice() * listing.getMinimumNights());
            }
        }
        
        for (String j : averagePrice.keySet()) {
            //once we have populated the hashmap and set the key as the borough and the values as the total of the minimum price for every property in that borough
            // we can now edit the values in order to get the average property price by borough
            // we do this by dividing the total price(original value) by the value in the listingCount hashmap, as this holds the amount of properties each borough has

            averagePrice.put(j, Math.floorDiv(averagePrice.get(j), listingsCount.get(j)));

        }

        return averagePrice;

    }
    
    /**
     * In getAverageReviews we are creating another HashMap in this the borough will again be the Key but the value will be the average number of reviews
     * To obtain the average number of reviews we first add up all the reviews for each property
     * then using the HashMap created in getListingsPerBorough we are able to generate an average by dividing the total of the reviews
     * by the counter stored in the value of the getListingsPerBorough HashMap
     * @return HashMap
     */

    public HashMap<String,Integer> getAverageReviews(){
        HashMap<String,Integer> averageReview = new HashMap<>();
        Map<String,Integer> listingCount = getListingsPerBorough();
        for(AirbnbListing listing:properties){
            if(averageReview.containsKey(listing.getNeighbourhood())){
                averageReview.put(listing.getNeighbourhood(),averageReview.get(listing.getNeighbourhood())+ listing.getNumberOfReviews());

            }else{
                averageReview.put(listing.getNeighbourhood(), listing.getNumberOfReviews());
            }
        }
        for(String k: averageReview.keySet()){
            averageReview.put(k, Math.floorDiv(averageReview.get(k), listingCount.get(k)));
        }
        return averageReview;
    }


    /**
     * This method will create a hashmap called listingsCount
     *  the key will be the borough and that will be mapped to the total number of properties in that borough
     *
     */

    public HashMap<String,Integer>  getListingsPerBorough() {

        HashMap<String, Integer> listingsCount = new HashMap<>();

        // loop through the properties
        for (AirbnbListing listing : properties) {
            //listing.getNeighbourhood returns the borough which is the key
            // if the hashmap already contains a key for a neighbourhood then we increment the value by one, if not then we add that key into the hashmap with a value of one
            if (listingsCount.containsKey(listing.getNeighbourhood())) {

                listingsCount.put(listing.getNeighbourhood(), listingsCount.get(listing.getNeighbourhood()) + 1);

            } else {
                listingsCount.put(listing.getNeighbourhood(), 1);
            }
        }

        return listingsCount;
    }

    /**
     * Thw Haversine formula is used to find the distance between 2 points using the latitude and longitude of the points
     * we are using the spherical law of cosines as an alternative as this is simpler but still accurate
     * after applying the formula we must multiply the answer by th radius of the earth in the units that we want the distance to be in
     */

    public double getDistance(double latProperty, double longProperty, AttractionListing attraction){
        double latAttraction = attraction.getLatitude();
        double longAttraction = attraction.getLongitude();
        double theta = longAttraction-longProperty;
        double distance = Math.sin(Math.toRadians(latAttraction))
                *Math.sin(Math.toRadians(latProperty))
                +Math.cos(Math.toRadians(latAttraction))
                *Math.cos(Math.toRadians(latProperty))*
                Math.cos(Math.toRadians(theta));
        distance=Math.acos(distance);
        distance = distance * 3963; //Multiply by radius of earth in miles
        return distance;

    }

    /**
     * The attraction chosen by the user will be passed into this method
     * We will then calculate the distance from each property to this attraction
     * While iterating round and save those that are less than a mile away
     * @param attraction
     * @return nearest property array list
     *
     */

    public ArrayList<AirbnbListing> checkDistance(AttractionListing attraction){
        ArrayList<AirbnbListing> nearestProperties = new ArrayList<>(properties);

        nearestProperties.removeIf(property -> getDistance(property.getLatitude(), property.getLongitude(),attraction) > 1);
        return nearestProperties;

    }


}
