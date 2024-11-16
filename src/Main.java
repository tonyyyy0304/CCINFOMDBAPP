package src;

import src.main.java.mvc_folder.Controller;
import src.main.java.mvc_folder.Model;
import src.main.java.mvc_folder.View;

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
 * # Compile the Java files
javac -cp ".;mysql-connector-j-9.1.0.jar" -d bin src/main/java/mvc_folder/Model.java src/main/java/mvc_folder/View.java src/main/java/mvc_folder/Controller.java src/Main.java

# Run the application
java -cp ".;bin;mysql-connector-j-9.1.0.jar" src.Main
 */