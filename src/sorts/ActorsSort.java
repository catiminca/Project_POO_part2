package sorts;

import platform.Movie;

import java.util.ArrayList;

public class ActorsSort implements Visitable {
    private ActorsSort actorsSort;
    private ArrayList<Movie> totalMovies;
    private ArrayList<String> actors;
    public ActorsSort(final ArrayList<Movie> movies, final ArrayList<String> actors) {
        this.totalMovies = new ArrayList<>();
        this.totalMovies.addAll(movies);
        this.actors = new ArrayList<>();
        this.actors.addAll(actors);
    }

    /**
     * @param visitor
     */
    public void accept(final Visitor visitor) {
        visitor.visit(this);
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
    public ArrayList<String> getActors() {
        return actors;
    }

    /**
     * @param actors
     */
    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    /**
     * @return
     */
    public ActorsSort getActorsSort() {
        return actorsSort;
    }

    /**
     * @param actorsSort
     */
    public void setActorsSort(final ActorsSort actorsSort) {
        this.actorsSort = actorsSort;
    }
}
