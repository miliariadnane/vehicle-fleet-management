/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.home;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class HomeController implements Initializable {

    @FXML
    
    private Pane pan1,pan2,pan3,pan4,pan5;
    
    @FXML
    
    private JFXButton btn1,btn2,btn3,btn4,btn5;
    
    private void handleButtonAction(ActionEvent event){
        
        if(event.getSource() == btn1)
        {
            pan1.toFront();
        }
        else if (event.getSource() == btn2)
        {
            pan2.toFront();
        }
        else if (event.getSource() == btn3)
        {
            pan3.toFront();
        }
        else if (event.getSource() == btn4)
        {
            pan4.toFront();
        }
        else if (event.getSource() == btn5)
        {
            pan5.toFront();
        }
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
