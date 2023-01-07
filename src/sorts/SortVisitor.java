package sorts;

import platform.Movie;

import java.util.ArrayList;

public class SortVisitor implements Visitor {
    private ActorsSort actors;
    private GenreSort genres;

    /**
     * metoda visit pentru clasa ActorsSort
     * @param actorsSort
     */
    @Override
    public void visit(final ActorsSort actorsSort) {
        ArrayList<Movie> allSortedMovies = new ArrayList<>();
        for (Movie movie : actorsSort.getTotalMovies()) {
            if (!actorsSort.getActors().isEmpty()) {
                for (String s : actorsSort.getActors()) {
                    if (movie.getActors().contains(s)) {
                        allSortedMovies.add(movie);
                    }
                }
            }
        }
        actorsSort.getTotalMovies().clear();
        actorsSort.getTotalMovies().addAll(allSortedMovies);
    }

    /**
     * metoda visit pentru clasa GenreSort
     * @param genreSort
     */
    @Override
    public void visit(final GenreSort genreSort) {
        ArrayList<Movie> allSortedMovies = new ArrayList<>();
        for (Movie movie : genreSort.getTotalMovies()) {
            if (!genreSort.getGenre().isEmpty()) {
                for (String s : genreSort.getGenre()) {
                    if (movie.getGenres().contains(s)) {
                        allSortedMovies.add(movie);
                    }
                }
            }
        }
        genreSort.getTotalMovies().clear();
        genreSort.getTotalMovies().addAll(allSortedMovies);
    }

    /**
     * @return
     */
    public ActorsSort getActors() {
        return actors;
    }

    /**
     * @param actors
     */
    public void setActors(final ActorsSort actors) {
        this.actors = actors;
    }

    /**
     * @return
     */
    public GenreSort getGenres() {
        return genres;
    }

    /**
     * @param genres
     */
    public void setGenres(final GenreSort genres) {
        this.genres = genres;
    }
}
