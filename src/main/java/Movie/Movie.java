package Movie;

public class Movie {

    //Deklarera instansvariabler för att lagra information om en film
     String title;
     String year;
     String genre;
     String director;
     String type;


     //Konstruktor för att skapa en instans av klassen Movie.Movie
    public Movie(String title, String year, String rated, String released, String runtime, String genre, String director, String writer, String actors, String plot, String language, String country, String awards, String poster, String metascore, String imdbRating, String imdbVotes, String imdbID, String type) {

        //Sätt värden för alla variabler
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.director = director;
        this.type = type;
    }

    //Getters & Setters-metoder för att hämta och ändra instansvariabler
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}