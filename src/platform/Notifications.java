package platform;

public class Notifications {
    private String message;
    private String movieName;

    public Notifications(final String movie, final String message) {
        this.movieName = movie;
        this.message = message;
    }

    /**
     * @return
     */
    public String getMovieName() {
        return movieName;
    }

    /**
     * @param movieName
     */
    public void setMovieName(final String movieName) {
        this.movieName = movieName;
    }
    /**
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
