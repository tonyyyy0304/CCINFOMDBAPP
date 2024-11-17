import main.java.mvc_folder.Controller;
import main.java.mvc_folder.Model;
import main.java.mvc_folder.View;

/**
 * The Main class to run the program.
 */
public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View();
        new Controller(model, view);

        view.setVisible(true);
        
    }
}

/*
 mvn clean install
 mvn exec:java -Dexec.mainClass="Main"
 */