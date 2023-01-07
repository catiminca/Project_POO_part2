package platform;

public class LikeForRecommendation {
    private String genre;
    private int nrlikes;
    public LikeForRecommendation(final String genre, final int nrlikes) {
        this.genre = genre;
        this.nrlikes = nrlikes;
    }

    /**
     * @return
     */
    public String getGenre() {
        return genre;
    }

    /**
     * @param genre
     */
    public void setGenre(final String genre) {
        this.genre = genre;
    }

    /**
     * @return
     */
    public int getNrlikes() {
        return nrlikes;
    }

    /**
     * @param nrlikes
     */
    public void setNrlikes(final int nrlikes) {
        this.nrlikes = nrlikes;
    }
}
