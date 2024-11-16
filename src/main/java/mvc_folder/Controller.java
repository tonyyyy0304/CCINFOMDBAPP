package src.main.java.mvc_folder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller 
{
    private Model model;
    private View view;

    public Controller(Model model, View view) 
    {
        this.model = model;
        this.view = view;
    }
    
    public void listeners(){
        this.view.setProductAddBtn(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("lol");
            }
        });

        this.view.setProductRemoveBtn(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("test");
            }
        });

        
    }
}
