/**
 * Represents a watchlist entry.
 */
public class Watchlist {

    private String ticker;
    private String price;
    private String dollarChange;
    private String percentChange;

    /**
     * Constructs a new Watchlist entry.
     * @param ticker the ticker symbol
     * @param price the price
     * @param dollarChange the dollar change
     * @param percentChange the percentage change
     */
    public Watchlist(String ticker, String price, String dollarChange, String percentChange) {
        this.ticker = ticker;
        this.price = price;
        this.dollarChange = dollarChange;
        this.percentChange = percentChange;
    }
    
    /**
     * Gets the ticker symbol.
     * @return the ticker symbol
     */
    public String getTicker() {
        return ticker;
    }

    /**
     * Gets the price.
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * Gets the dollar change.
     * @return the dollar change
     */
    public String getDollarChange() {
        return dollarChange;
    }

    /**
     * Gets the percentage change.
     * @return the percentage change
     */
    public String getPercentChange() {
        return percentChange;
    }
}
