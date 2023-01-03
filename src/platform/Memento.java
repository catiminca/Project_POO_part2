package platform;

public class Memento {
    private String state;
    public Memento(final String state) {
        this.state = state;
    }

    /**
     * @return
     */
    public String getState() {
        return state;
    }
}
