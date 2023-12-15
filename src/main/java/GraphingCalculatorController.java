import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * The controller file for the graphing calculator section of the app
 * 
 * @author Venkata Adapala
 * @version 16-06-2023
 */
public class GraphingCalculatorController {
    /**
     * The text fields that get the X and Y co-ordinates
     * of a point that is to be plotted on the graph
     */
    @FXML
    private TextField textFieldXCoord, textFieldYCoord;
    /**
     * The text fields in which the user types the equation to be graphed
     */
    @FXML
    private TextField textFieldEq1, textFieldEq2, textFieldEq3;
    /**
     * The text fields the user uses to set the viewing range of the graph
     * (the lower and upper bounds of the X and Y axis)
     */
    @FXML
    private TextField textFieldXMin, textFieldXMax, textFieldYMin, textFieldYMax;
    /**
     * The graph on which the points and the equations are plotted on
     */
    @FXML
    private LineChart<NumberAxis, NumberAxis> graph;
    /**
     * The X and Y axes of the graph
     */
    @FXML
    private NumberAxis xAxis, yAxis;
    /**
     * The numerical values of the X and Y co-ordinates of a certain point
     * (for the point plotting feature)
     */
    private double xCoord, yCoord;
    /**
     * The equations typed by the user that will be plotted on the graph
     */
    private String equation1, equation2, equation3;
    /**
     * The content text of the Help Dialog
     */
    private String helpContentText;

    /**
     * The constructor of the controller class
     */
    public GraphingCalculatorController() {
    }

    /**
     * The initialize method is called after the constructor is run and
     * any @FXML annotated fields are populated. This is required because the
     * constructor does NOT have access to the @FXML annotated fields.
     */
    @FXML
    public void initialize() {
        /*
         * The content text of the help dialog is too huge to be written directly in this file.
         * So, the text has been written in a text file (in the resources folder) and every time
         * this controller is initialized, the text from the text file.
         */
        try {
            File helpContent = new File(Taskade.class.getResource("GraphCalcHelpContent.txt").getPath());
            FileReader fileReader = new FileReader(helpContent);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                helpContentText += (line + "\n"); // Adding a new line after every line for better readability
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is for taking all sorts of user input
     * (like equations and points) from text fields
     */
    @FXML
    private void whenTyped() {

        // These text fields need numerical input only, so it is validated first
        takeNumberInput(textFieldXCoord);
        takeNumberInput(textFieldYCoord);
        takeNumberInput(textFieldXMin);
        takeNumberInput(textFieldXMax);
        takeNumberInput(textFieldYMin);
        takeNumberInput(textFieldYMax);

        // These text fields need alphanumeric input, so the text is not validated for now
        equation1 = textFieldEq1.getText();
        equation2 = textFieldEq2.getText();
        equation3 = textFieldEq3.getText();
    }

    /**
     * This method plots a singular point using the
     * input values from the x and y co-ordinate values
     */
    @FXML
    private void plotPoint() {
        // Checking to see if the text fields aren't empty
        if (textFieldXCoord.getText().length() >= 1 && textFieldYCoord.getText().length() >= 1) {
            graph.getData().clear();
            graph.setCreateSymbols(true); // This ensures that the point is highlighted and clearly visible
            plotThePoint(xCoord, yCoord);
        }
    }

    /**
     * This method graphs all three equations given by the user
     */
    @FXML
    private void graphEquations() {
        graph.getData().clear();
        graph.setCreateSymbols(false); // This ensures that the graphs are smooth lines
        graphEquation(textFieldEq1, equation1);
        graphEquation(textFieldEq2, equation2);
        graphEquation(textFieldEq3, equation3);
    }

    /**
     * This method "zooms in" on the graph i.e.
     * halves the upper and lower bounds of the graph's axes
     */
    @FXML
    private void whenZoomInClicked() {
        xAxis.setLowerBound(xAxis.getLowerBound() / 2);
        xAxis.setUpperBound(xAxis.getUpperBound() / 2);
        yAxis.setLowerBound(yAxis.getLowerBound() / 2);
        yAxis.setUpperBound(yAxis.getUpperBound() / 2);
        graphEquations();
    }

    /**
     * This method "zooms out" on the graph i.e.
     * doubles the upper and lower bounds of the graph's axes
     */
    @FXML
    private void whenZoomOutClicked() {
        xAxis.setLowerBound(xAxis.getLowerBound() * 2);
        xAxis.setUpperBound(xAxis.getUpperBound() * 2);
        yAxis.setLowerBound(yAxis.getLowerBound() * 2);
        yAxis.setUpperBound(yAxis.getUpperBound() * 2);
        graphEquations();
    }

    /**
     * This method clears out all important elements
     * while resetting the range of the graph
     */
    @FXML
    private void resetGraph() {
        graph.getData().clear();

        xAxis.setLowerBound(-10); // Setting default bounds
        xAxis.setUpperBound(10);
        yAxis.setLowerBound(-10);
        yAxis.setUpperBound(10);

        textFieldXCoord.clear();
        textFieldYCoord.clear();

        textFieldEq1.clear();
        textFieldEq2.clear();
        textFieldEq3.clear();

        textFieldEq1.setStyle(null); // Default formatting of these text fields
        textFieldEq2.setStyle(null);
        textFieldEq3.setStyle(null);

        textFieldXMin.clear();
        textFieldXMax.clear();
        textFieldYMin.clear();
        textFieldYMax.clear();

        equation1 = "";
        equation2 = "";
        equation3 = "";
    }

    /**
     * This method makes the application return to the home page
     */
    @FXML
    private void whenHomeClicked() {
        switchScreenTo("Home");
    }

    /**
     * When the help button is clicked, a dialog pops up which
     * has helpful details regarding the application
     */
    @FXML
    private void whenHelpClicked() {
        Alert alert = new Alert(AlertType.INFORMATION);

        alert.setTitle("Helpful Details");
        alert.setHeaderText("Helpful Details");
        // The first 4 characters are excluded since they say "null"
        alert.setContentText(helpContentText.substring(4, helpContentText.length()));

        alert.showAndWait();
    }

    /**
     * This method checks if the input given is a valid number,
     * and then assigns it to relevant variables.
     * 
     * @param textField The textfield where the input is being typed
     */
    private void takeNumberInput(TextField textField) {
        // Using a regex that only allows positive/negative decimal (or non-decimal) numbers
        if (textField.getText().length() != 0 && textField.getText().matches("-?[0-9]+\\.?[0-9]*")) {
            // Based on the text field, the data is assigned to a certain variable
            switch (textField.getId()) {
                case "textFieldXCoord":
                    xCoord = convertTextToNumber(textFieldXCoord.getText());
                    break;

                case "textFieldYCoord":
                    yCoord = convertTextToNumber(textFieldYCoord.getText());
                    break;

                case "textFieldXMin":
                    xAxis.setLowerBound(convertTextToNumber(textField.getText()));
                    // It is required to graph the equations every time the range of the graph is changed
                    graphEquations();
                    break;

                case "textFieldXMax":
                    xAxis.setUpperBound(convertTextToNumber(textField.getText()));
                    // It is required to graph the equations every time the range of the graph is changed
                    graphEquations();
                    break;

                case "textFieldYMin":
                    yAxis.setLowerBound(convertTextToNumber(textField.getText()));
                    // It is required to graph the equations every time the range of the graph is changed
                    graphEquations();
                    break;

                case "textFieldYMax":
                    yAxis.setUpperBound(convertTextToNumber(textField.getText()));
                    // It is required to graph the equations every time the range of the graph is changed
                    graphEquations();
                    break;

                default:
                    break;
            }
        } else if (!textField.getText().equals("-")) // Ensuring that a hyphen is allowed and not cleared
            textField.clear();
    }

    /**
     * Checks if a valid mathematical equation is typed into the text field
     * 
     * @param textField The textfield where the equation input is being typed
     * @return true if the input can be evaluated as a valid mathematical equation
     */
    private boolean validateEquationInput(TextField textField) {
        if (textField.getText().length() >= 1) {
            // A combination of random numbers unlikely to be used in a practical scenario
            if (evaluateExpression(textField.getText(), 1022309.23502) == 83895.31857)
                return false;
            else
                return true;
        } else // If the text field is left empty, it is not a valid input
            return false;
    }

    /**
     * This graphs the equation only if the equation input has been proved to be valid,
     * else the text ends up being red.
     * 
     * @param textField The textfield where the input is being typed
     * @param equationIn The equation given by the user in the text field
     */
    private void graphEquation(TextField textField, String equationIn) {
        if (validateEquationInput(textField)) { // If the equation input is valid
            textField.setStyle(null); // Setting the text field's formatting to default
            graphPoints(equationIn, xAxis.getLowerBound(), xAxis.getUpperBound());
        } else if (textField.getText().length() != 0) // If the equation input is invalid
            textField.setStyle("-fx-text-fill: red;"); // Changes the text color in the field to red
    }

    /**
     * Converts text input from the user to a number
     * used for calculations
     * 
     * @param text The text input given by the user
     * @return The converted value of the text (to a double)
     */
    private double convertTextToNumber(String text) {
        if (text.equals("-")) // Ignoring a hyphen
            return 0;
        else
            return Double.valueOf(text);
    }

    /**
     * Using a certain external dependency (exp4j) to mathematically
     * evaluate the mathematical equation input given by the user
     * 
     * @param equation The equation that is to be mathematically evaluated
     * @param x The x-value in the equation (ex: if x=3, x^3 + sinx ==> (60)^3 + sin(60))
     * @return The resultant y-value after the equation has been evaluated
     */
    private double evaluateExpression(String equation, double x) {
        // If an invalid equation is typed, the try catch will catch it and will send a particular value
        // (that is unlikely to be used in a practical scenario)
        try {
            Expression expression = new ExpressionBuilder(equation).variable("x").build().setVariable("x", x);
            expression.evaluate();
        } catch (Exception e) {
            return 83895.31857; // A random value that will probably never be calculated
        }

        // If there is no error, nothing out of the ordinary will happen, and the result of the evaluation is returned
        Expression expression = new ExpressionBuilder(equation).variable("x").build().setVariable("x", x);
        return expression.evaluate();
    }

    /**
     * Plots only one point on the graph using a data series
     * 
     * @param xCoordIn The X co-ordinate of the point that is to be graphed
     * @param yCoordIn The Y co-ordinate of the point that is to be graphed
     */
    private void plotThePoint(double xCoordIn, double yCoordIn) {
        // Making a data series since this is the way anything can be plotted on the line chart element
        XYChart.Series<NumberAxis, NumberAxis> pointCoords = new XYChart.Series<NumberAxis, NumberAxis>();
        pointCoords.setName("(" + xCoordIn + ", " + yCoordIn + ")"); // Labelling the point for the user
        pointCoords.getData().add(new XYChart.Data(xCoordIn, yCoordIn));
        graph.getData().add(pointCoords); // Graphing the data series
    }

    /**
     * Graphs a series of points using a data series and a for loop
     * 
     * @param equation The equation that provides the series of points that are to be graphed
     * @param lowerBoundIn The minimum x-value from which the values are evaluated and graphed
     * @param upperBoundIn The minimum x-value from which the values are evaluated and graphed
     */
    private void graphPoints(String equation, double lowerBoundIn, double upperBoundIn) {
        XYChart.Series<NumberAxis, NumberAxis> pointsOnGraph = new XYChart.Series<NumberAxis, NumberAxis>(); // Using a data series
        pointsOnGraph.setName(equation); // Labelling the equation for the suer
        double range = upperBoundIn - lowerBoundIn;
        // Starting from the lower bound and ending at the upper bound while the step value is
        // adjusted based on the range (for better efficiency since if the range is too)
        for (double i = lowerBoundIn; i <= upperBoundIn; i += (range / 200)) {
            pointsOnGraph.getData().add(new XYChart.Data(i, evaluateExpression(equation, i))); // plotting on x and y axes
        }
        graph.getData().add(pointsOnGraph);
    }

    /**
     * Switching screens by setting a different root, and
     * catching a possible exception
     * 
     * @param screenName The name of the FXML file associated with the screen that must appear
     */
    private void switchScreenTo(String screenName) {
        try {
            Taskade.setRoot(screenName);
        } catch (Exception e) {
            System.out.println("Exception in the " + screenName + " section");
            e.printStackTrace();
        }
    }
}
