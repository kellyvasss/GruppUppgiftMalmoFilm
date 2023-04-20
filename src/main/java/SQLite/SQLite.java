package SQLite;

import Movie.Movie;

import java.sql.*;

public class SQLite {

    Connection conn; // Objekt för att kommunicera med databasen
    String  dbName; //namnet på databasfilen
    public SQLite(String dbName){
        this.dbName=dbName; //sparar namnet i databasfilen
        conn=null; //initierar anslutningsobjektet till null
        try{
            conn= DriverManager.getConnection("jdbc:sqlite:"+dbName); //anslut till databasen
            System.out.println("Databasen har öppnats"); // meddelande för lyckad anslutning
        }catch (SQLException e){ //fånga undantag
            System.out.println("Error: " +e.getMessage()); // felmeddelande vid misslyckad anslutning
        }
    }
    public Movie[] getMovie(String title) throws SQLException { //metod för att hämta filmer baserat på titel
        PreparedStatement pstmt= conn.prepareStatement("SELECT * FROM movies WHERE title = ?");
        pstmt.setString(1, title);
        return getMovie(pstmt.toString());
    }
    public Movie[] getMovie(String title, String year) throws SQLException{//metod för att hämta filmer baserat på titel och år
        PreparedStatement pstmt= conn.prepareStatement("SELECT * FROM movies WHERE title = ? AND year = ?");
        pstmt.setString(1, title);
        pstmt.setString(2, year);
        return getMovie(pstmt.toString());
    }
    public Movie[] getMovie(String title, String year, String type) throws SQLException{//metod för att hämta filmer baserat på titel, år och typ
        PreparedStatement pstmt= conn.prepareStatement("SELECT * FROM movies WHERE title = ? AND year = ? AND type = ?");
        pstmt.setString(1, title);
        pstmt.setString(2, year);
        pstmt.setString(3, type);
        return getMovie(pstmt.toString());
    }
    public Movie[] getActor(String actor) throws SQLException{// Metod för att hämta filmer baserat på skådespelare
        PreparedStatement pstmt= conn.prepareStatement("SELECT * FROM movies WHERE actors LIKE ?");
        pstmt.setString(1,"&" + actor + "&");
        return getMovie(pstmt.toString());
    }
    public Movie[] getDirector(String director) throws SQLException{// Metod för att hämta filmer baserat på regissör
        PreparedStatement pstmt= conn.prepareStatement("SELECT * FROM movies WHERE director  = ?");
        pstmt.setString(1, director);
        return getMovie(pstmt.toString());
    }
    public Movie[] getGenre(String genre) throws SQLException{// Metod för att hämta filmer baserat på genre
        PreparedStatement pstmt= conn.prepareStatement("SELECT * FROM movies WHERE genre LIKE ?");
        pstmt.setString(1, "&" + genre + "&");
        return getMovie(pstmt.toString());
    }
    public Movie[] getYear(String year) throws SQLException{// Metod för att hämta filmer baserat på år
        PreparedStatement pstmt= conn.prepareStatement("SELECT * FROM movies WHERE year = ?");
        pstmt.setString(1, year);
        return getMovie(pstmt.toString());
    }

    public void addMovie(Movie movie){
        try{
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO movies (title, year, type, director, actors,genre) VALUES (?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getYear());
            pstmt.setString(3, movie.getType());
            pstmt.setString(4, movie.getDirector());
            pstmt.setString(5, movie.getActor());
            pstmt.setString(6, movie.getGenre());
            pstmt.executeUpdate();
            pstmt.close();
        }catch (SQLException e){
            System.out.println("Error: " +e.getMessage()); //skriv ut felmeddelande
        }
        System.out.println("Filmen har lagts till"); //skriv ut film lagts till
    }
    public void createTable(){
        try {
            Statement stmt = conn.createStatement();
            String sql= "CREATE TABLE IF NOT EXISTS movies (\n"
                    + "id INTEGER PRIMARY KEY,\n"
                    + "title TEXT,\n"
                    + "year TEXT,\n"
                    + "type TEXT,\n"
                    + "director TEXT,\n"
                    + "actors TEXT,\n"
                    + "genre TEXT\n"
                    + ");";
            stmt.execute(sql);
            stmt.close();
        }catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Tabellen har skapats.");
    }

    public void close(){
        try{
            conn.close();
        }catch (SQLException e){
            System.out.println("Error: " +e.getMessage());
        }
        System.out.println("Databasanslutningen stängd.");
    }
}

