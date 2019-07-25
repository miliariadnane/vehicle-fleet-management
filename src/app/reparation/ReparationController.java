/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.reparation;

import app.classes.Conducteur;
import app.classes.DbConnection;
import static app.classes.DbConnection.connection;
import app.classes.Vehicule;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class ReparationController implements Initializable {

    @FXML private JFXButton repaBtnS;

    @FXML private JFXComboBox<Vehicule> reparationV;
    @FXML private JFXTextField prixR;
    @FXML private JFXTextArea descriptionR;
    @FXML private JFXDatePicker dateR;

    ////////////////////////
//  METHODES ACTIONS  //
////////////////////////////////////////////////////////////////////////////////  
    
    //  SAVE  //
    
    @FXML
    void newReparation(ActionEvent event)  {
        
        Connection connection = DbConnection.DbConnect();

        String prixReparation = prixR.getText();
        String descR = descriptionR.getText();
        String dateRep = dateR.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Vehicule vehi = reparationV.getValue();

        if (prixReparation.equals("") || descR.equals("") || dateRep.equals("") ){
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setHeaderText("remplir les champs");
            ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(cancelButton);
            Optional<ButtonType> result = alert.showAndWait();
            return ;
        }  
        
        try {
            Statement statement = connection.createStatement();
            
                //  driver  
                statement.execute("insert into repations values('" + vehi + "','" + dateRep + "','" + descR + "','" + prixReparation + "')");

            statement.close();

        }catch (Exception ex) {
            ex.printStackTrace();
        }
        
        repaBtnS.getScene().getWindow().hide();
    }
    
    //  CLOSE  //
    @FXML void close(ActionEvent event) {
        repaBtnS.getScene().getWindow().hide();
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //  Set vehicules
        ArrayList vehiculesAL = new ArrayList();
        
        try {
            ResultSet rs;
            Statement statement = connection.createStatement();
            rs = statement.executeQuery("select * from vehicules");

            while(rs.next()){
                
                vehiculesAL.add(new Vehicule (rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)) );
                
                }
            }catch (Exception ex) {
            System.out.println(ex.getMessage()); 
        }

        ObservableList<Vehicule> vehiculeOL = FXCollections.observableArrayList(vehiculesAL);
        reparationV.setItems(vehiculeOL);
    }    
    
}
