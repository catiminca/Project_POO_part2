package sorts;

import platform.Movie;

import java.util.ArrayList;

public class SortVisitor implements Visitor {
    private ActorsSort actors;
    private GenreSort genres;
    @Override
    public void visit(ActorsSort actorsSort) {
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

    @Override
    public void visit(GenreSort genreSort) {
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

    public ActorsSort getActors() {
        return actors;
    }

    public void setActors(ActorsSort actors) {
        this.actors = actors;
    }

    public GenreSort getGenres() {
        return genres;
    }

    public void setGenres(GenreSort genres) {
        this.genres = genres;
    }
}
