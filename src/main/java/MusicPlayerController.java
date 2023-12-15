import java.io.File;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


public class MusicPlayerController implements Initializable {
    @FXML
    private TextField numberCount;
    @FXML
    private Pane pane;
    @FXML
    private Label songLabel;
    @FXML
    private Button playButton, pauseButton, resetButton, previousButton, nextButton;
    @FXML
    private ComboBox<String> speedBox;
    @FXML
    private ProgressBar songProgressBar;

    private Media media;

    private MediaPlayer mediaPlayer;

    private File directory;

    private File[] files;

    private ArrayList<File> songs;

    private int songNumber;

    private int[] speeds = { 25, 50, 75, 100, 125, 150, 175, 200 };

    private Timer timer;

    private TimerTask task;

    private boolean running;
    /*The initialize method will be used for when the program starts, and needs to retrive objects and strings, this can be done with local files due to
     * the URL arg and bundle method as they are used to store local files
     * I first start with making a file to store the songs ie: ArrayList
     * for the if statement and for loop it basically compares files to the file, if there are songs in the file, which there are, it will add each song to the compared file.
     * I then instantiate the media and mediaplayer code into this method so I can call it. 
     * I also then use a for loop to check for speedbox which controlls the speed section of my code, adding the number and comparing speed, as well as seeing if the method should be used.
     * it will also retrive the name of the file and paste it
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        songs = new ArrayList<File>();
		
        songNumber = 0;
 
        directory = new File("src" + File.separator + "main" + File.separator + "music");

		
		files = directory.listFiles();
		
		if(files != null) {
			
			for(File file : files) {
				
				songs.add(file); 
			}
		}
        // System.out.println("Music directory: " + directory.getAbsolutePath()); //debugging
        // System.out.println("Number of songs: " + files.length);
        
        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        songLabel.setText(files[songNumber].getName());

        for (int i = 0; i < speeds.length; i++) {
            speedBox.getItems().add(Integer.toString(speeds[i]));  
        }
        speedBox.setOnAction(this::changeSpeed);

        songProgressBar.setStyle("-fx-accent: BLACK");  

    }
/*once the back button is clicked, the screen will return to home.
 * this of course increases navigation and will allow the user to go between screens
 */
    public void backClicked() {
        switchScreenTo("Home"); 
    }
/*in this section of code, once you hit play, the beginTimer() method will begin,
 calling the method to start as well as using mediaPlayer.play() method to actually start playing the song. */
    @FXML
    public void playMedia() { 
        beginTimer();  
        changeSpeed(null);
        mediaPlayer.play();
    }
/*once again, this tells the timer to stop, and the progress bar will stop,
 and mediaPlayer.pause() method initiates which just pauses the media. */
    @FXML
    public void pauseMedia() { 
        cancelTimer();
        mediaPlayer.pause();

    }
/*in this section of code, the progressbar gets set to 0 as the song will start over,
 then the seek method is used which finds the set time that I set, which is 0 and set the song to that point. */
    @FXML
    public void resetMedia() { 
        songProgressBar.setProgress(0);
        mediaPlayer.seek(Duration.seconds(0));
    }
/*in this section of code, the song number will be minused 1, which will go negatively 1 song, 
therefore going to a previous song. and if you did not go to another song, then it will play the last song. */
    @FXML
    public void previousMedia() {
        if (songNumber > 0) {
            songNumber--;
            mediaPlayer.stop();
            if (running) {
                cancelTimer();
            }
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());
        } else {
            songNumber = songs.size() - 1;
            mediaPlayer.stop();
            if (running) {
                cancelTimer();
            }
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());

        }
    }
/*simirairly to the previousMedia() method, this method does the same thing but instead of substracting from songNumber, it adds, therefore going forward 1 in the music files
I then instantiate the mediaPlayer methods and make the current song stop, next song play and the progressbar to reset.
 */
    @FXML
    public void nextMedia() {
        if (songNumber < songs.size() - 1) {
            songNumber++;
            mediaPlayer.stop();
            if (running) {
                cancelTimer();
            }
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());
        } else {
            songNumber = 0;
            mediaPlayer.stop();
            if (running) {
                cancelTimer();
            }
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());

        }
    }
    /*the changeSpeed method will control based on the event of clicking which box of speed the user will want
     * if the user does not change the speed, the default rate (setRate()) will be 1, if changed it will get the value of the box
     * and adjust it accordingly by multiplying by 0.01.
     */
    @FXML
    public void changeSpeed(ActionEvent event) {
        if (speedBox.getValue() == null) {
            mediaPlayer.setRate(1);
        } else {
            mediaPlayer.setRate(Integer.parseInt(speedBox.getValue()) * 0.01);
        }
    }
    /*This method controls the progress bar, it retrives the current time in the song, and then the entire length of the song
    * by dividing both numbers, you will get the result of where you are currently in the song, once it hits 1 the progressbar ends.
    * progressbar increases at a steady pace, using the scheduleAtFixedRate method.
    */
    @FXML
    public void beginTimer() {
        timer = new Timer();

        task = new TimerTask() {
            public void run() {
                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
                songProgressBar.setProgress(current / end);
                if (current / end == 1) {
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }
    /*within this method, if a song is not playing or has been paused, then the timer will stop */
    @FXML
    public void cancelTimer() {

        running = false;
        timer.cancel();

    }
    /*in this method, once you press the button, it takes the text within numberCount which is the text for where the number will count down
    then it will every 1000 milliseconds(1 second) the number will decrease by 1
    this is incapsulated in a try catch statement to avoid errors and if a letter was inputed instead of a time.
     */
    @FXML
    public void timerPressed(){
        Thread timerThread = new Thread(() -> {
            try {
                int count = Integer.parseInt(numberCount.getText());
                while (count > 0) {
                    Thread.sleep(1000);
                    count--;
                    updateNumberCount(count);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        timerThread.start();
    }
    /*this takes the previous method timerPressed() and displays it on screen.
     * once the numberCount hits 0, mediaPlayer will stop
     */
    private void updateNumberCount(int count) {
        javafx.application.Platform.runLater(() -> {
            numberCount.setText(String.valueOf(count));
            if (count == 0) {
                mediaPlayer.stop();
            }
        });
    }
    /*this makes the previous method of switchScreen to work, as if you do not have this, it will result in a error, 
     * this is the reason for the try catch statement.
     */
    private void switchScreenTo(String screenName) {
        try {
            Taskade.setRoot(screenName);
        } catch (Exception e) {
            System.out.println("IOException: " + screenName);
            e.printStackTrace();
        }
    }

}