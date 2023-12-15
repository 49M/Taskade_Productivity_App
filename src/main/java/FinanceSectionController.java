import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller class for the Finance Section UI.
 */
public class FinanceSectionController {
    Taskade state = new Taskade();
    
    @FXML
    private Button personalDataButton, budgetButton, stockSearchButton, watchlistButton, backToHomeButton, saveData;  

    @FXML
    private Label DateTime;

    @FXML
    private TextField fullNameSlot, emailSlot, phoneSlot, startCapitalSlot, capitalGoalSlot, planOfActionSlot;

    /**
     * Initializes the Finance Section UI. In this screen, the users personal data is found to distinguish them from other users. 
     * While there is currently no sign in option, this is where the personal info would be and it would make the saved data unique 
     * to that person (instance).
     *
     * @throws NumberFormatException If a number cannot be parsed from a string.
     * @throws IOException           If an I/O error occurs while reading data.
     */
    @FXML
    public void initialize() throws NumberFormatException, IOException {
        // Sets the clock in the top right corner to the current date and time
        Label clock = DateTime;
        clock.setText(Main.DateTime());
        TextField[] pdSlots = {fullNameSlot, emailSlot, phoneSlot, startCapitalSlot, capitalGoalSlot, planOfActionSlot};
        // Sets the personal data button (in the navigation) style to a pink background
        personalDataButton.setStyle("-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-color: black; -fx-background-color: #f7d2f2");
        int count = -1;
        // reads from personalData file to see if there is any previously saved data to be displayed
        try (BufferedReader br = new BufferedReader(new FileReader("personalData.text"))) {
            String line;
            while ((line = br.readLine()) != null) {
                count++;
                // Set the saved values in the text fields (if there are any)
                pdSlots[count].setText(line);
            }
        }
    }  

    /**
     * Handles the click event of the navigation buttons. The navigation buttons are responsible for switching screens.
     *
     * @param event The event triggered by the button click.
     * @throws IOException If an I/O error occurs while changing the UI.
     */
    @FXML
    private void navigationButtonClick(Event event) throws IOException  {
        Button[] financeNavigator = {personalDataButton, budgetButton, stockSearchButton, watchlistButton, backToHomeButton};
        // gets the object id for the button
        Object buttonId = event.getSource();
        Button button = (Button)buttonId;
        String screen = "personalDataButton";
        for(int i = 0; i < financeNavigator.length; i++) {
            // if the button fx id is equal to the one clicked, then it knows which button was pressed
            if (financeNavigator[i].getId().toString().equals(button.getId().toString())) {
                screen = financeNavigator[i].getId().toString();
                // Highlights all buttons black to show no change in the ones not clicked
                for (int k = 0; k < financeNavigator.length; k++) {
                    financeNavigator[k].setStyle("-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-color: black");
                }
                // sets clicked button to pink
                financeNavigator[i].setStyle("-fx-border-radius: 15; -fx-background-radius: 15; -fx-border-color: black; -fx-background-color:  #f7d2f2");
            }
        }
        // Set the root based on the selected button
        if (screen.equals("personalDataButton")) {
            Taskade.setRoot("FinanceSection"); 
        } else if (screen.equals("budgetButton")) {
            Taskade.setRoot("Budget"); 
        } else if (screen.equals("stockSearchButton")) {
            Taskade.setRoot("StockSearch");
        } else if (screen.equals("watchlistButton")) {
            Taskade.setRoot("Watchlist");
        } else if (screen.equals("backToHomeButton")) {
            Taskade.setRoot("Home");
        }
    }

    /**
     * Handles the click event of the save data button. Saves all personal data slots to personalData text file, for reusage. 
     */
    @FXML
    private void saveDataClick() {
        Button savePersonal = saveData;
        TextField[] pdSlots = {fullNameSlot, emailSlot, phoneSlot, startCapitalSlot, capitalGoalSlot, planOfActionSlot};
        //sets colour of button
        savePersonal.setStyle("-fx-background-radius: 15; -fx-background-color: #f7d2f2");
        // reference used from https://stackoverflow.com/questions/56004215/writing-to-a-text-file-line-by-line and https://intellipaat.com/community/69798/how-to-clear-a-text-file-without-deleting-it
        // reads the file line by line
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("personalData.text", true))){
            FileWriter fw = new FileWriter("personalData.text", false);
            PrintWriter pw = new PrintWriter(fw, false);
            pw.flush();
            pw.close();
            fw.close();
            // writes to the corresponding personal data slot
            for (int i = 0; i < pdSlots.length; i++) {
                writer.write(pdSlots[i].getText());
                writer.newLine();
            }
          }
          catch(IOException ex){
            ex.printStackTrace();
          }
    }
}
