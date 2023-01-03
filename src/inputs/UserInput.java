package inputs;

import platform.Credentials;

import java.util.ArrayList;

public final class UserInput {
    private Credentials credentials;
    private int tokensCount;
    private int freeMoviesNumber;
    private ArrayList<MovieInput> purchasedMovies = new ArrayList<>();
    private ArrayList<MovieInput> watchedMovies = new ArrayList<>();
    private ArrayList<MovieInput> likedMovies = new ArrayList<>();
    private ArrayList<MovieInput> ratedMovies = new ArrayList<>();

    public UserInput() {
    }

    public Credentials getCredentials() {
        return this.credentials;
    }

    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    public int getTokensCount() {
        return this.tokensCount;
    }

    public void setTokensCount(final int tokensCount) {
        this.tokensCount = tokensCount;
    }

    public ArrayList<MovieInput> getPurchasedMovies() {
        return this.purchasedMovies;
    }

    public void setPurchasedMovies(final ArrayList<MovieInput> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public ArrayList<MovieInput> getWatchedMovies() {
        return this.watchedMovies;
    }

    public void setWatchedMovies(final ArrayList<MovieInput> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public ArrayList<MovieInput> getLikedMovies() {
        return this.likedMovies;
    }

    public void setLikedMovies(final ArrayList<MovieInput> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<MovieInput> getRatedMovies() {
        return this.ratedMovies;
    }

    public void setRatedMovies(final ArrayList<MovieInput> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    public int getFreeMoviesNumber() {
        return freeMoviesNumber;
    }

    public void setFreeMoviesNumber(int freeMoviesNumber) {
        this.freeMoviesNumber = freeMoviesNumber;
    }
}

