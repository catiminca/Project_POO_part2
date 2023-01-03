package platform;

import java.util.ArrayList;

public class CurrentMovieList {
    private final ArrayList<Movie> movielist = new ArrayList<>();
    public CurrentMovieList(final ArrayList<Movie> movies) {
        for (Movie value : movies) {
            Movie movie = new Movie(value);
            movielist.add(movie);
        }
    }
    public CurrentMovieList() { }

    /**
     * @return
     */
    public ArrayList<Movie> getMovielist() {
        return movielist;
    }

}
