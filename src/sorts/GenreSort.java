package sorts;

import platform.Movie;

import java.util.ArrayList;

public class GenreSort implements Visitable{
    private GenreSort genreSort;
    private ArrayList<Movie> totalMovies;
    private ArrayList<String> genre;
    public GenreSort(ArrayList<Movie> movies, ArrayList<String> genre) {
        this.totalMovies = new ArrayList<>();
        this.totalMovies.addAll(movies);
        this.genre = new ArrayList<>();
        this.genre.addAll(genre);
    }
    public void accept(Visitor vistor) {
        vistor.visit(this);
    }

    public ArrayList<Movie> getTotalMovies() {
        return totalMovies;
    }

    public void setTotalMovies(ArrayList<Movie> totalMovies) {
        this.totalMovies = totalMovies;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
    }
}
