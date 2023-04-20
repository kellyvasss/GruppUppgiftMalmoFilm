package Movie;

public class Movie {

    //Deklarera instansvariabler för att lagra information om en film
     String title;
     String year;
     String genre;
     String director;
     String type;
     String actor;
     String poster;


     //Konstruktor för att skapa en instans av klassen Movie.Movie
    public Movie(String title, String year,  String genre, String director, String actor, String type, String poster) {

        //Sätt värden för alla variabler
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.director = director;
        this.type = type;
        this.actor=actor;
        this.poster = poster;
    }
    public String getPoster() {
        return poster;
    }
    @Override
    public String toString() {
        return "Title: " + title +
                "\nYear: " + year +
                "\nGenre: " + genre +
                "\nDirector: " + director +
                "\nType: " + type +
                "\nActors: " + actor;
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

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
}