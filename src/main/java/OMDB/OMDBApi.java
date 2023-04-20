package OMDB;

import Movie.Movie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class OMDBApi {
    private final String apiKey;

    public OMDBApi(String apiKey) {
        this.apiKey = apiKey;
    }

    public Movie getMovie(String title) throws IOException {
        // Konstruerar API-anropets URL med API-nyckeln och titeln på filmen som ska hämtas
        String apiUrl = String.format("http://www.omdbapi.com/?apikey=%s&t=%s", apiKey, URLEncoder.encode(title, "UTF-8"));
        URL url = new URL(apiUrl);

        // Öppnar en anslutning till API:et
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Läser API:ets svar och bygger upp en sträng
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        String response = "";
        while ((line = reader.readLine()) != null) {
            response += line;
        }
        reader.close();

        // Pararser API-svaret och extraherar filmens titel, år, genre, regissör och skådespelare
        String[] parts = response.split(",");
        String movieTitle = "";
        int movieYear = 0;
        String movieGenre = "";
        String movieDirector = "";
        String movieActors = "";
        for (String part : parts) {
            String[] keyValue = part.split(":");
            if (keyValue.length > 1) {
                String key = keyValue[0].trim().replaceAll("\"", "");
                String value = keyValue[1].trim().replaceAll("\"", "");
                if (key.equals("Title")) {
                    movieTitle = value;
                } else if (key.equals("Year")) {
                    movieYear = Integer.parseInt(value);
                } else if (key.equals("Genre")) {
                    movieGenre = value;
                } else if (key.equals("Director")) {
                    movieDirector = value;
                } else if (key.equals("Actors")) {
                    movieActors = value;
                }
            }

        }
    }
}
