import org.json.JSONObject;

import Movie.Movie;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OMDBApi {
    private final String apiKey;

    public OMDBApi(String apiKey) {
        this.apiKey = apiKey;
    }

    public Movie getMovie(String title) {
        JSONObject json = searchTitle(title);
        return createMovie(json);
    }

    public Movie getMovie(String title, String year) {
        JSONObject json = searchTitleAndYear(title, year);
        return createMovie(json);
    }

    public Movie getMovie(String title, String year, String type) {
        JSONObject json = searchTitleYearAndType(title, year, type);
        return createMovie(json);
    }

    private JSONObject searchTitle(String title) {
        String apiUrl = "https://www.omdbapi.com/?apikey=" + apiKey + "&t=" + title;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return new JSONObject(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private JSONObject searchTitleAndYear(String title, String year) {
        String apiUrl = "https://www.omdbapi.com/?apikey=" + apiKey + "&t=" + title + "&y=" + year;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return new JSONObject(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private JSONObject searchTitleYearAndType(String title, String year, String type) {
        String apiUrl = "https://www.omdbapi.com/?apikey=" + apiKey + "&t=" + title + "&y=" + year + "&type=" + type;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return new JSONObject(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Movie createMovie(JSONObject json) {
        if (json == null) {
            return null;
        }

        String title = json.optString("Title", "");
        String year = json.optString("Year", "");
        String director = json.optString("Director", "");
        String plot = json.optString("Plot", "");
        String posterUrl = json.optString("Poster", "");
        String actor = json.optString("actor", "");
        String genre = json.optString("Genre", "");
        String type = json.optString("type", "");

        return new Movie(title,year,genre,director,actor,type);
    }
}