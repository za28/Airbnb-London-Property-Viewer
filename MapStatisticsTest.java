import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.*;

/**
* The test class test the MapStatistics class.
* It checks if the user's starting and end prices are being stored correctly.
* Checks if the arrayList of listings are being sorted correctly and if the
* correct list of properties within a borough are being returned.
*
* @author Areeba Safdar(K20045738), Imaan Ghafur(K21012260)
* Sabeeka Ahmad(K20012890), Zahra Amaan (k21011879)
* @version 03/20222
*/
public class MapStatisticsTest
{
 AirbnbDataLoader dataloader;
 MapStatistics stats;
 List<AirbnbListing> allProperties;

 /**
  * Sets up the test fixture.
  * Called before every test case method.
  */
 @BeforeEach
 public void setUp()
 {
     dataloader = new AirbnbDataLoader();
     allProperties = dataloader.load(); //All properties in the CSV file
     stats = new MapStatistics();
 }

 /**
  * Testing to see if the correct full name of the borough is returned
  * is it's abbreviated name is passed in
  */
 @Test
 public void testgetBoroughFullName(){
     String nameRecieved = stats.getBoroughFullName("ENFI");
     assertEquals("Enfield", nameRecieved);
 }

 /**
  * Testing if the correct starting price is being stored.
  */
 @Test
 public void testSetStartingPrice(){
     int price = 20;
     stats.setStartingPrice(price);
     assertEquals(20, stats.getStartingPrice());

 }

  /**
  * Testing if the correct property price is being stored.
  */
 @Test
 public void testAddFavouritePropertiess(){
     AirbnbListing testProperty = allProperties.get(0);
     stats.addFavProperty(testProperty);

     AirbnbListing lastPropertyAddedToList = stats.getFavouriteProperties().get(stats.getFavouriteProperties().size() -1);

     assertEquals(testProperty, lastPropertyAddedToList);
 }

 /**
  * Testing if the correct end price is being stored
  */
 @Test
 public void testSetEndPrice(){
     int price = 100;
     stats.setEndPrice(price);
     assertEquals(100, stats.getEndPrice());

 }

 /**
  * Testing if the correct list of properties, within the borough, are being returned.
  * Retrieves the list of properties within 'enfield' from the 'getBoroughProperties()'
  * method. Then loops through allProperties and checks if the 'enfieldProperties' contains
  * all properties which have the neighbourhood name 'enfield'.
  * If a property is missing then the test fails.
  */
 @Test
 public void testGettingPropertiesInABorough(){
     String enfieldBoroughName = "Enfield";

     ArrayList<AirbnbListing> enfieldProperties = stats.getBoroughProperties(enfieldBoroughName);

     boolean doesListContainAllEnfieldProperties = true;

     for(AirbnbListing listing: allProperties){
         if(listing.getNeighbourhood().equals(enfieldBoroughName)){
             if(!enfieldProperties.contains(listing)){
                 doesListContainAllEnfieldProperties = false;
                 return;
             }
         }
     }

     assertTrue(doesListContainAllEnfieldProperties);
 }

 /**
  * Testing if the correct properties are returned within a specific price range
  * Sets a dummy price range and retrieves properties within that price range.
  * Then loops through allProperties and checks if the 'receivedProperties' contains
  * all properties which are within test price range..
  * If a property is missing then the test fails.
  */
 @Test
 public void testGettingPropertiesWithinAPriceRange(){
     int testStartingPrice = 20;
     int testEndPrice = 40;

     stats.setStartingPrice(testStartingPrice);
     stats.setEndPrice(testEndPrice);

     List<AirbnbListing> receivedProperties = stats.getPropertiesInAPriceRange(allProperties);

     boolean doesListContainAllPropertiesInPriceRange = true;

     for(AirbnbListing listing: allProperties){
         if(listing.getPrice() >= testStartingPrice || listing.getPrice() <= testEndPrice){
             if(!receivedProperties.contains(listing)){
                 doesListContainAllPropertiesInPriceRange = false;
                 return;
             }
         }
     }

     assertTrue(doesListContainAllPropertiesInPriceRange);
 }

 /**
  * Testing to see if the given list is correctly sorted alphabetically (A-Z) by
  * host name.
  * Retrieves the sorted list and iterates through the sorted list. If the host name
  * of the current property is larger alphabetically than the host name of the next property,
  * then the test fails.
  */
 @Test
 public void testSortByHostName(){
     List<AirbnbListing> recievdProperties = stats.sortByHostName(allProperties);

     boolean isListSorted = true;
     for(int i=0; i < recievdProperties.size()-1; i++){

         AirbnbListing currentListing = recievdProperties.get(i);
         AirbnbListing nextListing = recievdProperties.get(i+1);

         if((currentListing.getHostName().compareTo(nextListing.getHostName())) > 0){
             System.out.println(currentListing.getHostName()+ " "+ nextListing.getHostName());
             isListSorted = false;
             return;
         }
     }

     assertTrue(isListSorted);
 }

  /**
   * Testing to see if the given list is correctly sorted by price (ascending).
   * Retrieves the sorted list and iterates through the sorted list. If the price
   * of the current property is larger than the price of the next property,
   * then the test fails.
   */
 @Test
 public void testSortByPrice(){
     List<AirbnbListing> recievdProperties = stats.sortByPrice(allProperties);

     boolean isListSorted = true;

     for(int i=0; i < recievdProperties.size()-1; i++){
         AirbnbListing currentListing = recievdProperties.get(i);
         AirbnbListing nextListing = recievdProperties.get(i+1);

         if(currentListing.getPrice() > nextListing.getPrice()) {
             isListSorted = false;
             break;
         }
     }

     assertTrue(isListSorted);
 }

  /**
   * Testing to see if the given list is correctly sorted by reviews (descending).
   * Retrieves the sorted list and iterates through the sorted list. If the number of reviews
   * of the current property is less than the number of reviews of the next property,
   * then the test fails.
   */
 @Test
 public void testSortByReviews(){
     List<AirbnbListing> recievdProperties = stats.sortByReview(allProperties);

     boolean isListSorted = true;

     for(int i=0; i < recievdProperties.size()-1; i++){
         AirbnbListing currentListing = recievdProperties.get(i);
         AirbnbListing nextListing = recievdProperties.get(i+1);

         if(currentListing.getNumberOfReviews() < nextListing.getNumberOfReviews()) {
             isListSorted = false;
             break;
         }
     }

     assertTrue(isListSorted);
 }
}
