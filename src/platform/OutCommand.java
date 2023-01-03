package platform;

import java.util.ArrayList;

public class OutCommand {
    private String error;
    private ArrayList<Movie> currentMoviesList;
    private User currentUser;

    public OutCommand(final String error, final ArrayList<Movie> currentMovieList,
                      final User currentUser) {
        this.error = error;
        this.currentMoviesList = new ArrayList<>();
        for (Movie value : currentMovieList) {
            Movie movie = new Movie(value);
            this.currentMoviesList.add(movie);
        }
        this.currentUser = new User(currentUser);
    }

    public OutCommand(final String error, final User currentUser) {
        this.error = error;
        this.currentMoviesList = new ArrayList<>();
        this.currentUser = new User(currentUser);
    }
    public OutCommand(final String error) {
        this.error = error;
        this.currentMoviesList = new ArrayList<>();
        this.currentUser = null;

    }
    public OutCommand(final User user) {
        this.error = null;
        this.currentMoviesList = null;
        this.currentUser = new User(user);
    }
    /**
     * @return
     */
    public String getError() {
        return error;
    }

    /**
     * @param error
     */
    public void setError(final String error) {
        this.error = error;
    }
    /**
     * @return
     */
    public ArrayList<Movie> getCurrentMoviesList() {
        return currentMoviesList;
    }

    /**
     * @param currentMovieList
     */
    public void setCurrentMoviesList(final ArrayList<Movie> currentMovieList) {
        this.currentMoviesList = currentMovieList;
    }

    /**
     * @return
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * @param currentUser
     */
    public void setCurrentUser(final User currentUser) {
        this.currentUser = currentUser;
    }

}
