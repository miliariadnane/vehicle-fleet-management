/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.mission;

import app.classes.Conducteur;
import app.classes.DbConnection;
import static app.classes.DbConnection.connection;
import app.classes.Type;
import app.classes.Vehicule;
import app.conducteur.conducteurAController;
import app.home.HomeController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;


public class MissionController implements Initializable {
    
    @FXML private JFXComboBox<Conducteur> newConducteur;
    @FXML private JFXComboBox<Vehicule> newVehicule;
    @FXML private JFXComboBox newType;
    @FXML private JFXTextField libelle;
    @FXML private JFXTextArea description;
    @FXML private JFXDatePicker dateD;
    @FXML private JFXDatePicker dateF;
    @FXML private JFXSlider Percentage;
    
////////////////////////
//  METHODES ACTIONS  //
////////////////////////////////////////////////////////////////////////////////  
    
    //  SAVE  //
    
    @FXML
    void addMission(ActionEvent event)  {
        
        Connection connection = DbConnection.DbConnect();

        String lib = libelle.getText();
        String desc = description.getText();
        String dateDebut = dateD.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String dateFin = dateF.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int perce = (int) Percentage.getValue();
        Conducteur condu = newConducteur.getValue();
        Vehicule vehi = newVehicule.getValue();
        String typeV = (String) newType.getValue();
        
        
        if (lib.equals("") || desc.equals("") || condu.equals("") || condu.equals("") ){
            
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
                statement.execute("insert into missions values('" + lib + "','" + vehi + "','" + condu + "','" + typeV + "','" + desc + "', '" + dateDebut + "', '" + dateFin + "', '" + perce + "')");

            statement.close();

        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //  Set conducteurs
        ArrayList conducteursAL = new ArrayList();
        
        try {
            ResultSet rs;
            Statement statement = connection.createStatement();
            rs = statement.executeQuery("select * from conducteurs");
                     
            while(rs.next()){
                conducteursAL.add(new Conducteur(rs.getString(1), rs.getString(2), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5) ) );
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage()); 
        }

        ObservableList<Conducteur> conducteurOL = FXCollections.observableArrayList(conducteursAL);
        newConducteur.setItems(conducteurOL);
        
        
        //  Set vehicules
        ArrayList vehiculesAL = new ArrayList();
        
        try {
            ResultSet rs;
            Statement statement = connection.createStatement();
            rs = statement.executeQuery("select * from vehicules");

            while(rs.next()){
                
                vehiculesAL.add(new Vehicule (rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)) );
                
                }
            }catch (Exception ex) {
            System.out.println(ex.getMessage()); 
        }

        ObservableList<Vehicule> vehiculeOL = FXCollections.observableArrayList(vehiculesAL);
        newVehicule.setItems(vehiculeOL);
        
        // TYPE CARBURANT
        ArrayList typeVehiculeAL = new ArrayList();
        
        typeVehiculeAL.add("moto");
        typeVehiculeAL.add("voiture");
        typeVehiculeAL.add("camionette");
        
        ObservableList typeVehiculeOL = FXCollections.observableArrayList(typeVehiculeAL);
        newType.setItems(typeVehiculeOL);
        
    }     
}    
    

