package sorts;

import platform.Movie;

import java.util.ArrayList;

public class ActorsSort implements Visitable{
    private ActorsSort actorsSort;
    private ArrayList<Movie> totalMovies;
    private ArrayList<String> actors;
    public ActorsSort(ArrayList<Movie> movies, ArrayList<String> actors) {
        this.totalMovies = new ArrayList<>();
        this.totalMovies.addAll(movies);
        this.actors = new ArrayList<>();
        this.actors.addAll(actors);
    }
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public ArrayList<Movie> getTotalMovies() {
        return totalMovies;
    }

    public void setTotalMovies(ArrayList<Movie> totalMovies) {
        this.totalMovies = totalMovies;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    public ActorsSort getActorsSort() {
        return actorsSort;
    }

    public void setActorsSort(ActorsSort actorsSort) {
        this.actorsSort = actorsSort;
    }
}
