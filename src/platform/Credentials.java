package platform;

public class Credentials {
    private String name;
    private String password;
    private String accountType;
    private String country;
    private String balance;

    public Credentials() {
    }

    public Credentials(final String name, final String password, final String accountType,
                       final String country, final String balance) {
        this.name = name;
        this.password = password;
        this.accountType = accountType;
        this.country = country;
        this.balance = balance;
    }

    public Credentials(final Credentials credentials) {
        this.name = credentials.getName();
        this.password = credentials.getPassword();
        this.country = credentials.getCountry();
        this.balance = credentials.getBalance();
        this.accountType = credentials.getAccountType();
    }

    /**
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @param password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * @return
     */
    public String getAccountType() {
        return this.accountType;
    }

    /**
     * @param accountType
     */
    public void setAccountType(final String accountType) {
        this.accountType = accountType;
    }

    /**
     * @return
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * @param country
     */
    public void setCountry(final String country) {
        this.country = country;
    }

    /**
     * @return
     */
    public String getBalance() {
        return this.balance;
    }

    /**
     * @param balance
     */
    public void setBalance(final String balance) {
        this.balance = balance;
    }
}
