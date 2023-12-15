import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

// Michal Buczek
/**
 * Controller class for the Budget.fxml file.
 */
public class BudgetController {
    Taskade state2 = new Taskade();
    private int selectedBudgetField = 0;
    private String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    @FXML
    private Button personalDataButton2, budgetButton2, stockSearchButton2, watchlistButton2, backToHomeButton2, resetBudget, calculateButton, SaveBudget;  

    @FXML
    private TextField field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, ieField1, ieField2, ieField3, ieField4, ieField5;

    @FXML
    private Label DateTime, m1, m2, m3, m4, m5;

    /**
     * Initializes the controller. This method is automatically called after the FXML file has been loaded. 
     * Sets all required elements right away.
     *
     * @throws FileNotFoundException If the file "budgetData.text" is not found.
     * @throws IOException           If an I/O error occurs while reading the file.
     */
    @FXML
    public void initialize() throws FileNotFoundException, IOException {
        TextField[] dataOutputFields = {field3, field4, field7, field8, field11, field12, field15, field16, field19, field20, ieField1, ieField2, ieField3, ieField4, ieField5};
        TextField[] allFields = {field1, field2, field3, field4, ieField1, field5, field6, field7, field8, ieField2, field9, field10, field11, field12, ieField3, field13, field14, field15, field16, ieField4, field17, field18, field19, field20, ieField5};
        Label clock = DateTime;
        int count = -1;
        Label[] monthLabel = {m1, m2, m3, m4, m5};
        int k = -1;
        // Sets the clock in the top right corner to the current date and time
        clock.setText(Main.DateTime());
        budgetButton2.setStyle("-fx-border-color: black; -fx-background-radius: 15; -fx-border-radius: 15; -fx-background-color: #f7d2f2");
        // makes the output fields uneditable
        for (int i = 0; i < dataOutputFields.length; i++) {
            dataOutputFields[i].setEditable(false);
        }
        // makes the months displayed accurately represent the current month and 4 previous months (works for any month)
        // for (int i = 4; i >= 0; i--) {
        //     k++;
        //     monthLabel[k].setText(months[Integer.valueOf(clock.getText().substring(6, 7)) - i - 1]);
        // }
        for (int i = 4; i >= 0; i--) {
            k++;
            int monthIndex = (Integer.valueOf(clock.getText().substring(6, 7)) - i - 1 + 12) % 12;
            monthLabel[k].setText(months[monthIndex]);
        }
        // reference used from https://stackoverflow.com/questions/5868369/how-can-i-read-a-large-text-file-line-by-line-using-java
        // reads budgetData file and displays any pre-existing saves
        try (BufferedReader br = new BufferedReader(new FileReader("budgetData.text"))) {
            String line;
            while ((line = br.readLine()) != null) {
                count++;
                allFields[count].setText(line);
                // if there is no characters on a line then the field is replaced with a "-"
                if (line.length() == 0) {
                    allFields[count].setText("-");
                }
                // these count values represent the 4th column (percentage change) of savings month to month
                if (!line.equals("-") && (count == 3 || count == 8 || count == 13 || count == 18 || count == 23)) {
                    // colour codes green as a positive increase red as negative
                    if (Float.valueOf(line) > 0) {
                        allFields[count].setStyle("-fx-background-color: #e8eced; -fx-border-color: grey; -fx-text-fill: green");
                    } else if (Float.valueOf(line) < 0) {
                        allFields[count].setStyle("-fx-background-color: #e8eced; -fx-border-color: grey; -fx-text-fill: red");
                    }
                }
            }
        } catch (Exception e) {
        } 
    }  

    /**
     * Event handler for the navigation buttons. 
     * Same usage as in other sections, switches screens. (More detailed comments in FinanceSection)
     *
     * @param event The button event.
     * @throws IOException If an I/O error occurs while changing the scene.
     */
    @FXML
    private void navigationButtonClick(Event event) throws IOException  {
        Button[] financeNavigator2 = {personalDataButton2, budgetButton2, stockSearchButton2, watchlistButton2, backToHomeButton2};
        Object buttonId2 = event.getSource();
        Button button2 = (Button)buttonId2;
        String screen = "budgetButton2";
        for(int i = 0; i < financeNavigator2.length; i++) {
            if (financeNavigator2[i].getId().toString().equals(button2.getId().toString())) {
                screen = financeNavigator2[i].getId().toString();
                for (int k = 0; k < financeNavigator2.length; k++) {
                    financeNavigator2[k].setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: black");
                }
                financeNavigator2[i].setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-background-color:  #f7d2f2");
            }
        }
        if (screen.equals("personalDataButton2")) {
            Taskade.setRoot("FinanceSection"); 
        } else if (screen.equals("budgetButton2")) {
            Taskade.setRoot("Budget"); 
        } else if (screen.equals("stockSearchButton2")) {
            Taskade.setRoot("StockSearch");
        } else if (screen.equals("watchlistButton2")) {
            Taskade.setRoot("Watchlist");
        } else if (screen.equals("backToHomeButton2")) {
            Taskade.setRoot("Home");
        }
    }

    /**
     * Event handler for clicking on a budget field.
     *
     * @param event The mouse event.
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    private void budgetFieldClick(Event event) throws IOException {
        TextField[] dataInputFields = {field1, field2, field5, field6, field9, field10, field13, field14, field17, field18};
        // finds out which text field was clicked based on object id's
        Object fieldId = event.getSource();
        TextField field = (TextField)fieldId;
        for (int i = 0; i < dataInputFields.length; i++) {
            if (dataInputFields[i].getId().toString().equals(field.getId().toString())) {
                // automatically get rid of "-"
                if (dataInputFields[i].getText().equals("-")) {
                    dataInputFields[i].clear();
                }
                // focuses on the clicked field
                dataInputFields[i].requestFocus();
                selectedBudgetField = i;
            }
        }
    }

    /**
     * Event handler for typing in a budget field.
     * Checks value for whever or not it is a number
     *
     * @param event The key event.
     */
    @FXML
    public void budgetFieldTyped(KeyEvent event) {
        TextField[] dataInputFields = {field1, field2, field5, field6, field9, field10, field13, field14, field17, field18};
        Button saveButton = SaveBudget;
        // if the clicked key is not a number than it is invalid and cleared immediately using the isNumeric method to check
        if (!isNumeric(dataInputFields[selectedBudgetField].getText())) {
            dataInputFields[selectedBudgetField].clear();
        } else {
            saveButton.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-background-color:  #D2D2D2");
        }
    }

    /**
     * Checks if a string is numeric.
     *
     * @param strNum The string to check.
     * @return True if the string is numeric, false otherwise.
     */
    // reference used from https://www.baeldung.com/java-check-string-number#:~:text=Perhaps%20the%20easiest%20and%20the,Double.parseDouble(String)
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
     * Event handler for the calculate button.
     * Uses math equations to solve for output fields.
     */
    @FXML
    private void calculateClick() {
        TextField[] dataInputFields = {field1, field2, field5, field6, field9, field10, field13, field14, field17, field18};
        TextField[] dataOutputFields = {field3, field4, field7, field8, field11, field12, field15, field16, field19, field20};
        TextField[] ieRatioFields = {ieField1, ieField2, ieField3, ieField4, ieField5};
        int k = -1;
        for (int i = 0; i < dataInputFields.length; i+=2) {
            k++;
            // checks if the input fields are not blank or contain a "-"
            if ((!dataInputFields[i].getText().equals("-") && !dataInputFields[i].getText().equals("")) && (!dataInputFields[i + 1].getText().equals("-") && !dataInputFields[i + 1].getText().equals(""))) {
                // calculates the amount saved (income - expenses)
                dataOutputFields[i].setText(String.valueOf(Integer.valueOf(dataInputFields[i].getText()) - Integer.valueOf(dataInputFields[i + 1].getText())));
                // calculates the ratio between income and expenses (income/expenses)
                ieRatioFields[k].setText(String.valueOf(Math.round(Float.valueOf(dataInputFields[i].getText()) / Float.valueOf(dataInputFields[i + 1].getText()))) + " : 1");
            }
            if (i > 0 && (!dataInputFields[i].getText().equals("-") && !dataInputFields[i].getText().equals("")) && (!dataInputFields[i - 2].getText().equals("-") && !dataInputFields[i - 2].getText().equals(""))) {
                // calculates the percent change from last months saved amount ((amount saved/last month amt saved)/last month amt saved)
                dataOutputFields[i + 1].setText(String.valueOf(((Float.valueOf(dataOutputFields[i].getText()) - Float.valueOf(dataOutputFields[i - 2].getText()))/Math.abs((Float.valueOf(dataOutputFields[i - 2].getText()))))*100));
                // colour codes green = positive, red = negative
                if (Float.valueOf(dataOutputFields[i + 1].getText()) > 0) {
                    dataOutputFields[i + 1].setStyle("-fx-background-color: #e8eced; -fx-border-color: grey; -fx-text-fill: green");
                } else if (Float.valueOf(dataOutputFields[i + 1].getText()) < 0) {
                    dataOutputFields[i + 1].setStyle("-fx-background-color: #e8eced; -fx-border-color: grey; -fx-text-fill: red");
                }
            }
        }
    }

    /**
     * Event handler for the reset button.
     * Resets the text fields to "-"
     */
    @FXML
    private void resetBudget() {
        TextField[] budgetFields =  {field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, ieField1, ieField2, ieField3, ieField4, ieField5};
        Button saveButton = SaveBudget;
        saveButton.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-background-color:  #D2D2D2");
        for (int i = 0; i < budgetFields.length; i++) {
            budgetFields[i].clear();
            budgetFields[i].setText("-");
        }
    }

    /**
     * Event handler for the save button.
     * saves values to budgetData file line by line.
     *
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    @FXML
    private void SaveBudget() {
        TextField[] budgetFields =  {field1, field2, field3, field4, ieField1, field5, field6, field7, field8, ieField2, field9, field10, field11, field12, ieField3, field13, field14, field15, field16, ieField4, field17, field18, field19, field20, ieField5};
        Button saveButton = SaveBudget;
        saveButton.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-background-color: #f7d2f2");
        // reference used from https://stackoverflow.com/questions/56004215/writing-to-a-text-file-line-by-line and https://intellipaat.com/community/69798/how-to-clear-a-text-file-without-deleting-it
        // writes to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("budgetData.text", true))){
            FileWriter fw = new FileWriter("budgetData.text", false);
            PrintWriter pw = new PrintWriter(fw, false);
            pw.flush();
            pw.close();
            fw.close();
            for (int i = 0; i < budgetFields.length; i++) {
                writer.write(budgetFields[i].getText());
                writer.newLine();
            }
          }
          catch(IOException ex){
            ex.printStackTrace();
          }
    }
}
