import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The controller class of the home screen
 */
public class HomeController {
    /**
     * The buttons that are on the screen
     */
    @FXML
    private Button notesButton, musicPlayerButton, financeButton, graphCalcButton, quitButton;
    /**
     * The image view which displays the images of the sections of the app
     */
    @FXML
    private ImageView mainImgView;
    /**
     * The label at the bottom of the screen which has a description of each section of the app
     */
    @FXML
    private Label descLabel;
    /**
     * A description of the application in general
     */
    private String appDesc;
    /**
     * Descriptions of each section
     */
    private String notesDesc, financeDesc, graphCalcDesc, musicPlayerDesc;
    /**
     * The images of each section of the app that are displayed
     */
    private Image notesImg, financeImg, graphCalcImg, musicPlayerImg;
    
    /**
     * The constructor of the home screen
     */
    public HomeController() {
    }

    /**
     * The initialize method is called after the constructor is run and
     * any @FXML annotated fields are populated.  This is required because the 
     * constructor does NOT have access to the @FXML annotated fields.
     */
    @FXML
    public void initialize() {

        // The descriptions that are displayed
        appDesc = "Taskade is a productivity app made for Students, with various kinds of features";
        notesDesc = "A Note-taking app that allows you to make, edit and save notes";
        financeDesc = "A Budget manager with Stock-analysis";
        graphCalcDesc = "A graphing calculator for mathematical equations and points";
        musicPlayerDesc = "A Music Player with an inbuilt timer that lets you stay productive";

        // The images that are displayed
        notesImg = new Image(Taskade.class.getResourceAsStream("notesImage.png"));
        financeImg = new Image(Taskade.class.getResourceAsStream("financeImage.jpg"));
        graphCalcImg = new Image(Taskade.class.getResourceAsStream("graphCalcImage.png"));
        musicPlayerImg = new Image(Taskade.class.getResourceAsStream("musicPlayerImage.jpg"));
    }

    /**
     * Switching the screen to the Notes section
     */
    @FXML
    private void whenNotesClicked() {
        switchScreenTo("Notes");
    }

    /**
     * Switching the screen to the Music Player section
     */
    @FXML
    private void whenMusicPlayerClicked() {
        switchScreenTo("MusicPlayer");
    }

    /**
     * Switching the screen to the Graphing Calculator section
     */
    @FXML
    private void whenGraphCalcClicked() {
        switchScreenTo("GraphingCalculator");
    }

    /**
     * Switching the screen to the Finance section
     */
    @FXML
    private void whenFinanceSectionClicked() {
        switchScreenTo("FinanceSection");
    }

    /**
     * Quits the app when the button is clicked
     */
    @FXML
    private void whenQuitClicked() {
        Platform.exit();
    }

    /**
     * When the mouse hovers over the button, display a certain image,
     * a certain description, and change text fill
     */
    @FXML
    private void whenMouseHoversOnNotes() {
        mainImgView.setImage(notesImg);
        descLabel.setText(notesDesc);
        notesButton.setStyle("-fx-text-fill: black;");
    }

    /**
     * Return the button's formatting and the image view 
     * to normal when the mouse is not hovering on the button
     */
    @FXML
    private void whenMouseDoesNotHoverOnNotes() {
        mainImgView.setImage(null);
        descLabel.setText(appDesc);
        notesButton.setStyle("-fx-text-fill: gray;");
    }

    /**
     * When the mouse hovers over the button, display a certain image,
     * a certain description, and change text fill
     */
    @FXML
    private void whenMouseHoversOnFinance() {
        mainImgView.setImage(financeImg);
        descLabel.setText(financeDesc);
        financeButton.setStyle("-fx-text-fill: black;");
    }

    /**
     * Return the button's formatting and the image view 
     * to normal when the mouse is not hovering on the button
     */
    @FXML
    private void whenMouseDoesNotHoverOnFinance() {
        mainImgView.setImage(null);
        descLabel.setText(appDesc);
        financeButton.setStyle("-fx-text-fill: gray;");
    }

    /**
     * When the mouse hovers over the button, display a certain image,
     * a certain description, and change text fill
     */
    @FXML
    private void whenMouseHoversOnGraphCalc() {
        mainImgView.setImage(graphCalcImg);
        descLabel.setText(graphCalcDesc);
        graphCalcButton.setStyle("-fx-text-fill: black;");
    }

    /**
     * Return the button's formatting and the image view 
     * to normal when the mouse is not hovering on the button
     */
    @FXML
    private void whenMouseDoesNotHoverOnGraphCalc() {
        mainImgView.setImage(null);
        descLabel.setText(appDesc);
        graphCalcButton.setStyle("-fx-text-fill: gray;");
    }

    /**
     * When the mouse hovers over the button, display a certain image,
     * a certain description, and change text fill
     */
    @FXML
    private void whenMouseHoversOnMusicPlayer() {
        mainImgView.setImage(musicPlayerImg);
        descLabel.setText(musicPlayerDesc);
        musicPlayerButton.setStyle("-fx-text-fill: black;");
    }

    /**
     * Return the button's formatting and the image view 
     * to normal when the mouse is not hovering on the button
     */
    @FXML
    private void whenMouseDoesNotHoverOnMusicPlayer() {
        mainImgView.setImage(null);
        descLabel.setText(appDesc);
        musicPlayerButton.setStyle("-fx-text-fill: gray;");
    }

    /**
     * When the mouse hovers over the button, change the text fill
     */
    @FXML
    private void whenMouseHoversOnQuit() {
        quitButton.setStyle("-fx-text-fill: black;");
    }

    /**
     * Return the button's formatting to normal when
     * the mouse is not hovering on the button
     */
    @FXML
    private void whenMouseDoesNotHoverOnQuit() {
        quitButton.setStyle("-fx-text-fill: gray;");
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
