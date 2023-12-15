
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 

public class Main {
    public static void main(String[] args) {
        Taskade.main(args);
    }
    // reference used from https://www.javatpoint.com/java-get-current-date
    public static String DateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
        LocalDateTime now = LocalDateTime.now();  
        return dtf.format(now); 
    }
}

