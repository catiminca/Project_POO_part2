package platform;

import com.fasterxml.jackson.annotation.JsonIgnore;
import inputs.MovieInput;
import inputs.UserInput;

import java.util.ArrayList;

public class User {
    private Credentials credentials;
    private int tokensCount;
    private int numFreePremiumMovies = 15;
    private ArrayList<Movie> purchasedMovies = new ArrayList<>();
    private ArrayList<Movie> watchedMovies = new ArrayList<>();
    private ArrayList<Movie> likedMovies = new ArrayList<>();
    private ArrayList<Movie> ratedMovies = new ArrayList<>();
    private ArrayList<Notifications> notifications;
    @JsonIgnore
    private ArrayList<String> subscribe;
    public User(final UserInput user) {
        this.credentials = user.getCredentials();
        this.tokensCount = user.getTokensCount();
        this.purchasedMovies = new ArrayList<>();
        for (MovieInput movie : user.getPurchasedMovies()) {
            Movie movieCop = new Movie(movie);
            this.purchasedMovies.add(movieCop);
        }
        this.watchedMovies = new ArrayList<>();
        for (MovieInput movie : user.getWatchedMovies()) {
            Movie movieCop = new Movie(movie);
            this.watchedMovies.add(movieCop);
        }
        this.likedMovies = new ArrayList<>();
        for (MovieInput movie : user.getLikedMovies()) {
            Movie movieCop = new Movie(movie);
            this.likedMovies.add(movieCop);
        }
        this.ratedMovies = new ArrayList<>();
        for (MovieInput movie : user.getRatedMovies()) {
            Movie movieCop = new Movie(movie);
            this.ratedMovies.add(movieCop);
        }
        this.notifications = new ArrayList<>();
    }

    public User(final User user) {
        this.credentials = new Credentials(user.getCredentials());
        this.tokensCount = user.getTokensCount();
        this.numFreePremiumMovies = user.getNumFreePremiumMovies();
        this.purchasedMovies = new ArrayList<>();
        for (Movie movie : user.getPurchasedMovies()) {
            Movie movieCop = new Movie(movie);
            this.purchasedMovies.add(movieCop);
        }
        this.watchedMovies = new ArrayList<>();
        for (Movie movie : user.getWatchedMovies()) {
            Movie movieCop = new Movie(movie);
            this.watchedMovies.add(movieCop);
        }
        this.likedMovies = new ArrayList<>();
        for (Movie movie : user.getLikedMovies()) {
            Movie movieCop = new Movie(movie);
            this.likedMovies.add(movieCop);
        }
        this.ratedMovies = new ArrayList<>();
        for (Movie movie : user.getRatedMovies()) {
            Movie movieCop = new Movie(movie);
            this.ratedMovies.add(movieCop);
        }
        this.notifications = new ArrayList<>();
        if (user.getNotifications() != null) {
            for (int i = 0; i < user.getNotifications().size(); i++) {
                Notifications notification = new Notifications(user.getNotifications()
                        .get(i).getMovieName(), user.getNotifications().get(i).getMessage());
                this.notifications.add(notification);
            }
        }
        this.subscribe = new ArrayList<>();
        if (user.getSubscribe() != null) {
            this.subscribe.addAll(user.getSubscribe());
        }
    }

    public User(final String name, final String password, final String accountType,
                final String country, final String balance) {
        this.credentials = new Credentials(name, password, accountType, country, balance);
    }

    public User(final String name, final String password, final String accountType,
                final String country, final String balance,
                final ArrayList<Movie> purchasedMovies) {
        this.credentials = new Credentials(name, password, accountType, country, balance);

    }

    /**
     * @return
     */
    public Credentials getCredentials() {
        return credentials;
    }

    /**
     * @param credentials
     */
    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    /**
     * @return
     */
    public int getTokensCount() {
        return tokensCount;
    }

    /**
     * @param numberTokens
     */
    public void setTokensCount(final int numberTokens) {
        this.tokensCount = numberTokens;
    }

    /**
     * @return
     */
    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    /**
     * @param freeMoviesNumber
     */
    public void setNumFreePremiumMovies(final int freeMoviesNumber) {
        this.numFreePremiumMovies = freeMoviesNumber;
    }

    /**
     * @return
     */
    public ArrayList<Movie> getPurchasedMovies() {
        return purchasedMovies;
    }

    /**
     * @param purchasedMovies
     */
    public void setPurchasedMovies(final ArrayList<Movie> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    /**
     * @return
     */
    public ArrayList<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    /**
     * @param watchedMovies
     */
    public void setWatchedMovies(final ArrayList<Movie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    /**
     * @return
     */
    public ArrayList<Movie> getLikedMovies() {
        return likedMovies;
    }

    /**
     * @param likedMovies
     */
    public void setLikedMovies(final ArrayList<Movie> likedMovies) {
        this.likedMovies = likedMovies;
    }

    /**
     * @return
     */
    public ArrayList<Movie> getRatedMovies() {
        return ratedMovies;
    }

    /**
     * @param ratedMovies
     */
    public void setRatedMovies(final ArrayList<Movie> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    /**
     * @return
     */
    public ArrayList<Notifications> getNotifications() {
        return notifications;
    }

    /**
     * @param notifications
     */
    public void setNotifications(final ArrayList<Notifications> notifications) {
        this.notifications = notifications;
    }

    /**
     * @return
     */
    public ArrayList<String> getSubscribe() {
        return subscribe;
    }

    /**
     * @param subscribe
     */
    public void setSubscribe(final ArrayList<String> subscribe) {
        this.subscribe = subscribe;
    }
}
