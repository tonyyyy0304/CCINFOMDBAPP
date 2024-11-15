import mvc_folder.*;

/**
 * The Main class to run the program.
 */
public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        MainView view = new MainView();
        new Controller(model, view);

        view.setVisible(true);
        
    }
}