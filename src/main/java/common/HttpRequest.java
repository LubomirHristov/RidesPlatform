package common;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class HttpRequest {

    /**
     * Makes a request to a supplier's API
     * @param supplier name of the supplier
     * @param pickup pickup location of the journey
     * @param dropOff drp off location of the journey
     * @return returns a string json response from the API
     * @throws IOException throws IOException
     */
    public String makeRequest(String supplier, String pickup, String dropOff) throws IOException {
        URL baseUrl = new URL(String.format("https://techtest.rideways.com/%s?pickup=%s&dropoff=%s", supplier, pickup, dropOff));
        HttpsURLConnection connection = (HttpsURLConnection) baseUrl.openConnection();
        connection.setConnectTimeout(2000);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);
        in.close();

        return response.toString();
    }
}
