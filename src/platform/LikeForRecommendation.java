package platform;

public class LikeForRecommendation {
    private String genre;
    private int nrlikes;
    public LikeForRecommendation(String genre, int nrlikes){
        this.genre = genre;
        this.nrlikes = nrlikes;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getNrlikes() {
        return nrlikes;
    }

    public void setNrlikes(int nrlikes) {
        this.nrlikes = nrlikes;
    }
}
