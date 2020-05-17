/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.vehicule;

import app.classes.Conducteur;
import static app.classes.DbConnection.connection;
import app.classes.Vehicule;
import app.conducteur.conducteurAController;
import app.home.HomeController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.mysql.jdbc.PreparedStatement;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class VehiculeMController implements Initializable {

    //  Btns
    @FXML private JFXButton editBtnV;
    @FXML private AnchorPane paneRoot;
    
    //  edit
    @FXML private JFXComboBox <Vehicule> editvehiculeSelect;
    @FXML private JFXTextField editMatricule;
    @FXML private JFXTextField editMarque;
    @FXML private JFXComboBox editCarburant;
    @FXML private JFXDatePicker editDateAss;
    @FXML private JFXDatePicker editDateAss2;
    @FXML private JFXDatePicker editDateV;
    @FXML private JFXRadioButton moto,voiture,camionette,transport;
    
    @FXML void selectedVehicule(ActionEvent event) {
        
        if(editvehiculeSelect.getValue() == null){
            return;
        }
        
        String selectedVehiculeId = editvehiculeSelect.getValue().getMatricule();
        
        try { 
            
            ResultSet rs;
            Statement statement = connection.createStatement();
            rs = statement.executeQuery("select * from vehicules where vehiculesMatr =  \"" + selectedVehiculeId + "\"  ");
            rs.next(); 
            
                editMatricule.setText(rs.getString(2));
                editMarque.setText(rs.getString(3));  
                editCarburant.setValue(rs.getString(4)); 
                editDateAss.setValue(LocalDate.parse(rs.getString(5)));
                editDateAss2.setValue(LocalDate.parse(rs.getString(6)));
                editDateV.setValue(LocalDate.parse(rs.getString(7)));
     
                
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    //  Edit    
    @FXML void editSaveV(ActionEvent event) {

        if(editvehiculeSelect.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setHeaderText("Select conducteur ?");
            //alert.setContentText("");
            ButtonType cancelButton = new ButtonType("Anuuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(cancelButton);
            Optional<ButtonType> result = alert.showAndWait();  
            return;
        }
        
        String selectedVehiculeId = editvehiculeSelect.getValue().getMatricule();
        
        if (editMatricule.getText() == null || editMarque.getText() == null || editCarburant.getValue().equals("") || editDateAss.getValue().equals("") || editDateAss2.getValue().equals("") || editDateV.getValue().equals("") ){
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setHeaderText("Fill all fields");
            ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(cancelButton);
            alert.showAndWait();
        
        }else{
            
            String mat = editMatricule.getText();
            String date1 = editDateAss.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String date2 = editDateAss2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String date3 = editDateV.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            
            Statement statement1,statement2;
            try {
                
                statement1 = connection.createStatement();
                statement2 = connection.createStatement();
                
                statement1.executeUpdate("update vehicules set vehiculesMatr = '" + editMatricule.getText() + "',vehiculesMarque = '" + editMarque.getText() + "', vehiculesCarb = '" + editCarburant.getValue()+ "', vehiculesDateAss = '" + date1 + "', vehiculesDateAss2 = '" + date2 + "', vehiculesDateVid = '"+ date3 + "' where vehiculesMatr = '" + selectedVehiculeId + "'");
                statement1.close();
                HomeController.getInstance().refreshVehicules();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            ArrayList typeV = new ArrayList();
            if (moto.isSelected())
                typeV.add("moto"); 
            if (voiture.isSelected())
                typeV.add("voiture"); 
            if (camionette.isSelected())
                typeV.add("camionette");
            if (transport.isSelected())
                typeV.add("transport"); 
            typeV.forEach(e->{
                try {
                    Statement statement = connection.createStatement();
                    statement.execute("INSERT INTO `typevehicule`(`vehiculesMatr`, `typeV`) VALUES ('" + mat + "','" + e + "') ");
                    HomeController.getInstance().refreshVehicules();
                } catch (SQLException ex) {
                    Logger.getLogger(conducteurAController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        editBtnV.getScene().getWindow().hide();
    }
    
    //  Delete Module
    
    @FXML void supV (ActionEvent event) {
    
        //  Verification
        
        if(editvehiculeSelect.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Select module ?");
            //alert.setContentText("");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(cancelButton);
            Optional<ButtonType> result = alert.showAndWait();  
            return;
        }
        
        String selectedVehiculeId = editvehiculeSelect.getValue().getMatricule();
        
        //  Confirmation
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you want to delet your module ?");
        
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        
        alert.getButtonTypes().setAll(yesButton, noButton, cancelButton);
        Optional<ButtonType> result = alert.showAndWait();  
        
        if(result.get() == noButton || result.get() == cancelButton ){
            return;
        }
        
        //  Delete subscribes of student into this module
        try {
            PreparedStatement statment = (PreparedStatement) connection.prepareStatement("delete from vehicules where vehiculesMatr = '"+ selectedVehiculeId +"' ");
            statment.executeUpdate();
            HomeController.getInstance().refreshVehicules();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\n" + e.getCause());
        }
        
        try {
            PreparedStatement statment = (PreparedStatement) connection.prepareStatement("delete from typevehicule where vehiculesMatr = '"+ selectedVehiculeId +"'");
            statment.executeUpdate();
            HomeController.getInstance().refreshVehicules();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\n" + e.getCause());
        }
        editBtnV.getScene().getWindow().hide();
    }    
    
    //  CLOSE  //
    @FXML void close(ActionEvent event) {
        editBtnV.getScene().getWindow().hide();
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
        editvehiculeSelect.setItems(vehiculeOL);
        
        // TYPE CARBURANT
        ArrayList typeCarburantAL = new ArrayList();
        
        typeCarburantAL.add("Essence");
        typeCarburantAL.add("Diesel");
        
        ObservableList typeCarburantOL = FXCollections.observableArrayList(typeCarburantAL);
        editCarburant.setItems(typeCarburantOL);
    }    
    
}
