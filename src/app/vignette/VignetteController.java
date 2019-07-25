/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.vignette;

import app.classes.Conducteur;
import app.classes.DbConnection;
import static app.classes.DbConnection.connection;
import app.classes.Vehicule;
import app.classes.Vignette;
import app.classes.mission;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
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
public class VignetteController implements Initializable {

    @FXML private JFXButton vignetteBtnS;
    
    @FXML private JFXComboBox<Conducteur> newConducteurV;
    @FXML private JFXComboBox<Vehicule> newVehiculeV;
    @FXML private JFXTextField km;
    @FXML private JFXTextField libelleV;
    @FXML private JFXDatePicker dateV;
    @FXML private JFXTextField prix;
    @FXML private JFXTextField station;

    ////////////////////////
//  METHODES ACTIONS  //
////////////////////////////////////////////////////////////////////////////////  
    
    //  SAVE  //
    
    @FXML
    void addVignette(ActionEvent event) throws ParseException  {
        
        Connection connection = DbConnection.DbConnect();
        
        String lib = libelleV.getText();
        String dateVegnette = dateV.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String kmV = km.getText();
        Conducteur condu = newConducteurV.getValue();
        Vehicule vehi = newVehiculeV.getValue();
        String prixV = prix.getText();
        String stationV = station.getText();

        if (lib.equals("") || prixV.equals("") || stationV.equals("") || stationV.equals("") || condu.equals("") || vehi.equals("") ){
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setHeaderText("remplir les champs");
            ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(cancelButton);
            Optional<ButtonType> result = alert.showAndWait();
            return ;
        }  
        
        try {
            Statement statement;
                    
                statement = connection.createStatement();
                
                statement.execute("insert into vignette (libelleV,kmV,conducteurV,vehiculeV,dateV,station,prix) values('" + lib + "','" + kmV + "','" + condu.getIdConducteur() + "','" + vehi.getMatricule() + "','" + dateVegnette + "', '" + stationV + "', '" + prixV + "')");

                statement.close();

        }catch (Exception ex) {
            ex.printStackTrace();
        }
        vignetteBtnS.getScene().getWindow().hide();
    }
    
    //  CLOSE  //
    @FXML void close(ActionEvent event) {
        vignetteBtnS.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //  Set conducteurs
        ArrayList conducteursAL = new ArrayList();
        
        try {
            ResultSet rs1;
            Statement statement1 = connection.createStatement();
            rs1 = statement1.executeQuery("select * from conducteurs");
                     
            while(rs1.next()){
                conducteursAL.add(new Conducteur(rs1.getString(2), rs1.getString(3), rs1.getString(3), rs1.getString(4), rs1.getString(5), rs1.getString(6) ) );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ObservableList<Conducteur> conducteurOL = FXCollections.observableArrayList(conducteursAL);
        newConducteurV.setItems(conducteurOL);
        
        //  Set vehicules
        ArrayList vehiculesAL = new ArrayList();
        
        
        try {
            ResultSet rs2;
            Statement statement2 = connection.createStatement();
            rs2 = statement2.executeQuery("select * from vehicules");

            while(rs2.next()){
                
                vehiculesAL.add(new Vehicule (rs2.getString(2), rs2.getString(3), rs2.getString(4), rs2.getString(5), rs2.getString(6), rs2.getString(7)) );
                
                }
            }catch (Exception ex) {
             ex.printStackTrace();
        }

        ObservableList<Vehicule> vehiculeOL = FXCollections.observableArrayList(vehiculesAL);
        newVehiculeV.setItems(vehiculeOL);

        
    }    
    
}
