import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection; 

/**
 * Controller class for the StockSearch.fxml file.
 */
public class StockSearchController {
    Taskade state3 = new Taskade();
    boolean validStock = false;

    @FXML 
    private Button personalDataButton3, budgetButton3, stockSearchButton3, watchlistButton3, backToHomeButton3, addWatch;

    @FXML
    private Label DateTime, Ticker, Price, dollarChange, percentChange;

    @FXML
    private TextField StockSearchBar;

    /**
     * Initializes the controller.
     *
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    public void initialize() throws IOException{
        // sets clock at top right to real time
        Label clock = DateTime;
        clock.setText(Main.DateTime());
    }

    /**
     * Event handler for navigation buttons.
     * Same functionality as in other classes (explained in FinanceSection)
     *
     * @param event The button click event.
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    private void navigationButtonClick(Event event) throws IOException  {
        Button[] financeNavigator = {personalDataButton3, budgetButton3, stockSearchButton3, watchlistButton3, backToHomeButton3};
        Object buttonId = event.getSource();
        Button button = (Button)buttonId;
        String screen = "stockSearchButton3";
        for(int i = 0; i < financeNavigator.length; i++) {
            if (financeNavigator[i].getId().toString().equals(button.getId().toString())) {
                screen = financeNavigator[i].getId().toString();
                for (int k = 0; k < financeNavigator.length; k++) {
                    financeNavigator[k].setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: black");
                }
                financeNavigator[i].setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: black; -fx-background-color: #f7d2f2");
            }
        }
        if (screen.equals("personalDataButton3")) {
            Taskade.setRoot("FinanceSection"); 
        } else if (screen.equals("budgetButton3")) {
            Taskade.setRoot("Budget"); 
        } else if (screen.equals("stockSearchButton3")) {
            Taskade.setRoot("StockSearch");
        } else if (screen.equals("watchlistButton3")) {
            Taskade.setRoot("Watchlist");
        } else if (screen.equals("backToHomeButton3")) {
            Taskade.setRoot("Home");
        }
    }

    /**
     * Event handler for the search button.
     * Clears the search bar contents when clicked
     */
    @FXML
    private void SearchClick() {
        TextField searchBar = StockSearchBar;
        searchBar.clear();
    }

    /**
     * Event handler for key press in the search bar.
     * Searches for the inputed stock ticker and then outputs results.
     *
     * @param event The key event.
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    public void searchKeyPressed(KeyEvent event) throws IOException{
        TextField searchBar = StockSearchBar;
        Label tickerName = Ticker;
        Label stockPrice = Price;
        Label[] dailyStats = {dollarChange, percentChange}; 
        boolean secondTime = false;
        // when enter key is clicked
        if (event.getCode() == KeyCode.ENTER && searchBar.getText().length() != 0) {
            String searchedStock = searchBar.getText().toUpperCase();
            // Stock info (referenced used from: https://www.youtube.com/watch?v=UVqjMbYlCFs)
            // searches the url with the ticker typed included
            URL url = new URL("https://www.google.com/finance/quote/" + searchedStock + ":NASDAQ");
            // gets url html code and reads it
            URLConnection urlConn = url.openConnection();
            InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
            BufferedReader buff = new BufferedReader(inStream);
            String price = "not found";
            String priceDayChange = "0.00";
            String dayChangePercentage = "0.00";
            String line = buff.readLine();
            // runs while the html content is not equal to null
            while(line != null) {
                // looks for a specific line of code (text)
                if (line.contains("[\"" + searchedStock + "\",")) {
                    int target = line.indexOf("[\"" + searchedStock + "\",");
                    // gets the decimal location for the price )this is used to navigate around the line for values
                    int deci = line.indexOf(".", target);
                    int start = deci;
                    int comaLocation = deci;
                    validStock = true;
                    // while it is a number it keeps subtracting the start place for indexing
                    while(isNumeric(line.substring(start - 1, start))) {
                        start--;
                    }
                    // gets the live stock price
                    price = line.substring(start, deci + 3);
                    start = deci + 1;
                    // finds the coma location for next value
                    while(isNumeric(line.substring(start, start + 1)) || line.substring(start, start + 1).equals(",") || line.substring(start, start + 1).equals("-")) {
                        if (line.substring(start, start + 1).equals(",")) {
                            comaLocation = start;
                        }
                        start++;
                    }
                    // gets the change in price for the day and colour codes it green = positive, red = negative 
                    priceDayChange = line.substring(comaLocation + 1, start + 3);
                    if (secondTime) {
                        if (Float.valueOf(priceDayChange) >= 0) {
                            dailyStats[0].setText("+" + priceDayChange);
                            dailyStats[0].setStyle("-fx-background-color: white; -fx-border-color: black; -fx-text-fill: green");

                        } else {
                            dailyStats[0].setText(priceDayChange);
                            dailyStats[0].setStyle("-fx-background-color: white; -fx-border-color: black; -fx-text-fill: red");
                        }
                    }
                    start++;
                    // does the same process as above to find the next value (percent change in price for the day)
                    while(isNumeric(line.substring(start, start + 1)) || line.substring(start, start + 1).equals(",")|| line.substring(start, start + 1).equals("-")) {
                        if (line.substring(start, start + 1).equals(",")) {
                            comaLocation = start;
                        }
                        start++;
                    }
                    dayChangePercentage = line.substring(comaLocation + 1, start + 3);
                    if (secondTime) {
                        dailyStats[1].setText("(" + dayChangePercentage + "%)");
                        if (Float.valueOf(dayChangePercentage) >= 0) {
                            dailyStats[1].setStyle("-fx-background-color: white; -fx-border-color: black; -fx-text-fill: green");
                        } else {
                            dailyStats[1].setStyle("-fx-background-color: white; -fx-border-color: black; -fx-text-fill: red");

                        }
                    }
                    secondTime = true;
                }
                // reads the lines
                line = buff.readLine();
            }
            // error checks if a stock does not exist the unkown values are replaced with "-"
            if (price.equals("]]]]}}; var AF_initDataChunkQueue = []; var AF_initDataCallback; var AF_initDataInitializeCallback; if (AF_initDataInitializeCallback) {AF_initDataInitializeCallback(AF_initDataKeys, AF_initDataChunkQueue, AF_dataServiceRequests);}if (!AF_initDataCallback) {AF_initDataCallback = function(chunk) {AF_initDataChunkQueue.pu") || price.equals(".pu")) {
                price = "-";
                stockPrice.setText(price);
                tickerName.setText("not found");
                dailyStats[0].setText("-");
                dailyStats[1].setText("-");
                validStock = false;

            } else {
                // say's stock not found if it does not exist
                tickerName.setText(searchedStock);
                if (price.equals("not found")) {
                    stockPrice.setText(price);
                    validStock = false;
                } else {
                    stockPrice.setText(price.substring(0, price.length()));
                }
            }

        }
        // reference used from https://stackoverflow.com/questions/41970252/how-to-control-javafx-textfield-input-so-it-wont-accept-space
        // makes it impossible to type a space as no stock ticker has spaces and it produces an error
        searchBar.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().equals(" ")) {
                change.setText("");
            }
            return change;
        }));
    }

    /**
     * Checks if a given string is numeric.
     *
     * @param strNum the string to check
     * @return true if the string is numeric, false otherwise
     */
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Handles the click event of the "Add to Watchlist" button.
     * Writes the stock data to a file if the stock is valid.
     * This data is used for the watchlist
     */
    @FXML
    private void addWatchClick() {
        Label[] stockData = {Ticker, Price, dollarChange, percentChange};
        if (validStock) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("watchlistStocks.text", true))){
                FileWriter fw = new FileWriter("budgetData.text", false);
                PrintWriter pw = new PrintWriter(fw, false);
                pw.flush();
                pw.close();
                fw.close();
                for (int i = 0; i < stockData.length; i++) {
                    writer.write(stockData[i].getText());
                    writer.newLine();
                }
              }
              catch(IOException ex){
                ex.printStackTrace();
              }
        }
    }
}