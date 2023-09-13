
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class will output the weather at the current time in London
 * we have imported IO classes in order to read data from the API
 *  @author Areeba Safdar(k20045738) Imaan Ghafur(k2102260)
 *  Sabeeka Ahmad(k20012890) Zahra Amaan(k21011879)
 *  @version 03/2022
 *
 *
 *
 */
public class ShowWeather {
    Map<String,Object> tempMap =null;
    Map<String,Object> weatherMap=null;

    /**
     * Java does not have a working translator therefore we need this method to convert the JSON to a map
     * we need to do this in order to easily wok with the data that is returned
     * @param str
     * @return
     */
    public static Map<String,Object> jsonToMap(String str){
        Map<String,Object> map=new Gson().fromJson(
                str, new TypeToken<HashMap<String,Object>>() {}.getType()
            );
        return map;
    }
    /**
     *
     * This method makes use of the openWeatherMap API.
     * 'apiKey' is the key for accessing the api online.
     * It queries the current day weather for london and uses the method
     * 'jsonToMap()' to store the resulting JSON string in a map.
     * respMap contains multiple different blocks of data
     * we create tempMap with the 'main' block as this block contains the temperature
     *
     */

    public Map<String,Object> loadCurrent() {
        String apiKey = "d50dfc833bfad7b5d023ed98fe0ab100";
        String location = "London,UK";
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + apiKey + "&units=metric";



        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);

            }
            reader.close();

            Map<String, Object> respMap = jsonToMap(result.toString());
            // main contains the information that we want
            tempMap = jsonToMap(respMap.get("main").toString());



        } catch (IOException e) {
            System.out.println(e.getMessage());
        }



        return tempMap;
    }

    /**
     * using tempMap generated in loadCurrent() we are able to retrieve the weather
     * @return temperature
     */
    public ArrayList<Object> getTemp(){
        Object temp = null;
        Object maxTemp = null;
        Object minTemp = null;
        try{
            Map<String,Object> main = loadCurrent();
            temp = main.get("temp");
            maxTemp = main.get("temp_max");
            minTemp = main.get("temp_min");


        } catch (Exception e){
            System.out.println(e);
        }
        ArrayList<Object> information= new ArrayList<Object>();
        information.add(temp);
        information.add(maxTemp);
        information.add(minTemp);
        return information;

    }}

