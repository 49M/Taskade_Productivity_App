import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 * Controller class for the Watchlist section
 */
public class WatchlistController implements Initializable {
    Taskade state3 = new Taskade();

    //Implemented concepts learnt from this video (on how to use table views): https://www.youtube.com/watch?v=fnU1AlyuguE

    @FXML 
    private Button personalDataButton4, budgetButton4, stockSearchButton4, watchlistButton4, backToHomeButton4;

    @FXML
    private Label DateTime;

    @FXML
    private TableView<Watchlist> watchlistTable;

    @FXML
    private TableColumn<Watchlist, String> ticker; 

    @FXML
    private TableColumn<Watchlist, String> price;

    @FXML
    private TableColumn<Watchlist, String> dollarChange;

    @FXML
    private TableColumn<Watchlist, String> percentChange;

    ObservableList<Watchlist> list = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller.
     * Sets up the table columns and loads data from the watchlistStocks.text file.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // creates instances of columns
        ticker.setCellValueFactory(new PropertyValueFactory<Watchlist, String>("ticker"));
        price.setCellValueFactory(new PropertyValueFactory<Watchlist, String>("price"));
        dollarChange.setCellValueFactory(new PropertyValueFactory<Watchlist, String>("dollarChange"));
        percentChange.setCellValueFactory(new PropertyValueFactory<Watchlist, String>("percentChange"));
        try {
            initialize();
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        watchlistTable.setItems(list);
    }

    /**
     * Initializes the data for the watchlist table.
     * Loads the data from the watchlistStocks.text file and adds it to the table.
     * @throws NumberFormatException if a numeric conversion error occurs
     * @throws IOException if an I/O error occurs
     */
    @FXML
    public void initialize() throws NumberFormatException, IOException {
        Label clock = DateTime;
        clock.setText(Main.DateTime());
        int count = -1;
        String ticker2 = "";
        String price2 = "";
        String dollarChange2 = "";
        String percentChange2 = "";
        String[] watchListFields = {ticker2, price2, dollarChange2, percentChange2}; 
        // reads watchlistStocks file for saved stocks
        try (BufferedReader br = new BufferedReader(new FileReader("watchlistStocks.text"))) {
            String line;
            // reads 4 lines and then makes instances of each given value into watchListFields where it is then displayed then it restarts the counter for the next 4 lines
            while ((line = br.readLine()) != null) {
                count++;
                watchListFields[count] = line;
                if (count >= 3) {
                    count = -1;
                    list.add(new Watchlist(watchListFields[0], watchListFields[1], watchListFields[2], watchListFields[3]));
                }
            }
        }
    }  

    /**
     * Handles the click event of the navigation buttons.
     * Changes the active screen based on the clicked button.
     * @param event the button click event
     * @throws IOException if an I/O error occurs
     */
    @FXML
    private void navigationButtonClick(Event event) throws IOException  {
        Button[] financeNavigator = {personalDataButton4, budgetButton4, stockSearchButton4, watchlistButton4, backToHomeButton4};
        Object buttonId = event.getSource();
        Button button = (Button)buttonId;
        String screen = "watchlistButton4";
        for(int i = 0; i < financeNavigator.length; i++) {
            if (financeNavigator[i].getId().toString().equals(button.getId().toString())) {
                screen = financeNavigator[i].getId().toString();
                for (int k = 0; k < financeNavigator.length; k++) {
                    financeNavigator[k].setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: black");
                }
                financeNavigator[i].setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: black; -fx-background-color: #f7d2f2");
            }
        }
        if (screen.equals("personalDataButton4")) {
            Taskade.setRoot("FinanceSection"); 
        } else if (screen.equals("budgetButton4")) {
            Taskade.setRoot("Budget"); 
        } else if (screen.equals("stockSearchButton4")) {
            Taskade.setRoot("StockSearch");
        } else if (screen.equals("watchlistButton4")) {
            Taskade.setRoot("Watchlist");
        } else if (screen.equals("backToHomeButton4")) {
            Taskade.setRoot("Home");
        }
    }
}
