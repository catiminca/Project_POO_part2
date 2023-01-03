package sorts;

import platform.Movie;

import java.util.ArrayList;
import java.util.Comparator;
interface SortStrategy {
    ArrayList<Movie> sortStrategy(ArrayList<Movie> movies);
}
public class Sort {
    private String rating;
    private String duration;

    public Sort() {

    }

    /**
     * @return
     */
    public String getRating() {
        return this.rating;
    }

    /**
     * @param rating
     */
    public void setRating(final String rating) {
        this.rating = rating;
    }

    /**
     * @return
     */
    public String getDuration() {
        return this.duration;
    }

    /**
     * @param duration
     */
    public void setDuration(final String duration) {
        this.duration = duration;
    }

    /**
     * sortare in functie de nota pe care o are(rating)
     * @param movies
     * @param rating
     */
    public ArrayList<Movie> sortByRating(final ArrayList<Movie> movies, final String rating) {
        SortByRating sortByRating = new SortByRating(rating, movies);
        sortByRating.sortStrategy(movies);
        return movies;
    }

    /**
     * sortare in functie de durata
     * @param movies
     * @param duration
     */
    public void sortByDuration(ArrayList<Movie> movies, final String duration) {
        SortByDuration sortByDuration = new SortByDuration(duration, movies);
        movies = sortByDuration.sortStrategy(movies);
    }

    /**
     * sortare dupa ambele criterii, folosind functiile specifice
     * pentru fiecare tip de sortare
     * @param movies
     */
    public ArrayList<Movie> sort(final ArrayList<Movie> movies) {
        ArrayList<Movie> sortedMovies;
        sortedMovies = sortByRating(movies, this.rating);
        sortByDuration(sortedMovies, this.duration);
        return sortedMovies;
    }
}

class SortByRating implements SortStrategy {
    private final String rating;

    SortByRating(final String rating, final ArrayList<Movie> currentMovies) {
        this.rating = rating;
        ArrayList<Movie> movies = new ArrayList<>();
        movies.addAll(currentMovies);
    }

    @Override
    public ArrayList<Movie> sortStrategy(final ArrayList<Movie> movies) {
        for (Movie movie : movies) {
            if (movie.getRating() == null) {
                movie.setRating(0.0);
            }
        }
        if (rating.equals("increasing")) {
            movies.sort(Comparator.comparingDouble(Movie::getRating));
        } else if (rating.equals("decreasing")) {
            movies.sort(Comparator.comparingDouble(Movie::getRating).reversed());
        }
        return movies;
    }
}

class SortByDuration implements SortStrategy {
    private final String duration;
    SortByDuration(final String duration, final ArrayList<Movie> currentMovies) {
        this.duration = duration;
        ArrayList<Movie> movies = new ArrayList<>();
        movies.addAll(currentMovies);
    }
    @Override
    public ArrayList<Movie> sortStrategy(final ArrayList<Movie> movies) {
        if (duration.equals("increasing")) {
            movies.sort(Comparator.comparingDouble(Movie::getDuration));
        } else if (duration.equals("decreasing")) {
            movies.sort(Comparator.comparingDouble(Movie::getDuration).reversed());
        }
        return movies;
    }
}
