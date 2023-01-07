package sorts;

import platform.Movie;

import java.util.ArrayList;

public class GenreSort implements Visitable {
    private GenreSort genreSort;
    private ArrayList<Movie> totalMovies;
    private ArrayList<String> genre;
    public GenreSort(final ArrayList<Movie> movies, final ArrayList<String> genre) {
        this.totalMovies = new ArrayList<>();
        this.totalMovies.addAll(movies);
        this.genre = new ArrayList<>();
        this.genre.addAll(genre);
    }

    /**
     * @param vistor
     */
    public void accept(final Visitor vistor) {
        vistor.visit(this);
    }

    /**
     * @return
     */
    public ArrayList<Movie> getTotalMovies() {
        return totalMovies;
    }

    /**
     * @param totalMovies
     */
    public void setTotalMovies(final ArrayList<Movie> totalMovies) {
        this.totalMovies = totalMovies;
    }

    /**
     * @return
     */
    public ArrayList<String> getGenre() {
        return genre;
    }

    /**
     * @param genre
     */
    public void setGenre(final ArrayList<String> genre) {
        this.genre = genre;
    }
}
