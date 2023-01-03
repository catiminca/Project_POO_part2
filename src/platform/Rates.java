package platform;

import java.util.ArrayList;

public class Rates {
    private ArrayList<User> users;
    private ArrayList<Double> currentRate;
    public Rates(final ArrayList<User> names) {
        this.users = new ArrayList<>();
        for (User user : names) {
                User usercop = new User(user);
                this.users.add(usercop);
        }
    }

    public void addRate(final String username, final Double rating, final String name) {
        for (int i = 0; i < users.size(); i ++) {
            if (users.get(i).getCredentials().getName().equals(username)) {
                for (int j = 0; j < users.get(i).getRatedMovies().size(); j++) {
                    if (users.get(i).getRatedMovies().get(j).getName().equals(name)) {
                        users.get(i).getRatedMovies().get(j).setRating(rating);
                    }
                }
            }
        }
    }

    public void addMovies(final ArrayList<Movie> ratedMovies, final User user) {
        for (User value : users) {
            if (value.getCredentials().getName().equals(user.getCredentials().getName())) {
                value.getRatedMovies().addAll(ratedMovies);
                break;
            }
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(final ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Double> getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(final ArrayList<Double> currentRate) {
        this.currentRate = currentRate;
    }
}
