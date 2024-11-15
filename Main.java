import mvc_folder.*;

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