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
import app.home.HomeController;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class VignetteController implements Initializable {

    @FXML private JFXButton vignetteBtnS;
    
    @FXML private JFXTextField km;
    @FXML private JFXComboBox<Vehicule> newVehiculeV;
    @FXML private JFXTextField lettre;
    @FXML private JFXTextField libelleV;
    @FXML private JFXDatePicker dateV;
    @FXML private JFXTextField prix;
    @FXML private JFXTextField station;
    @FXML private RadioButton nv;
    @FXML private ToggleGroup kmtoggle;
    @FXML private RadioButton fin;
    @FXML private Label kmparcoru;

    ////////////////////////
//  METHODES ACTIONS  //
////////////////////////////////////////////////////////////////////////////////  
    
    //  SAVE  //
    
    @FXML
    void addVignette(ActionEvent event) throws ParseException  {
        
        Connection connection = DbConnection.DbConnect();
        
        String lib = libelleV.getText();
        String dateVegnette = dateV.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String kmV = lettre.getText();
        String kmVic = km.getText();
        Vehicule vehi = newVehiculeV.getValue();
        String prixV = prix.getText();
        String stationV = station.getText();
        
        Vehicule savedvehi;

        if (lib.equals("") || prixV.equals("") || stationV.equals("") || stationV.equals("") || km.equals("") || vehi.equals("") ){
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setHeaderText("remplir les champs");
            ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(cancelButton);
            Optional<ButtonType> result = alert.showAndWait();
            return ;
        }  
        
        try {
            Statement statement,statment2, statment3;
                    
                statement = connection.createStatement();
                statment2 = connection.createStatement();
                statment3 = connection.createStatement();
                ResultSet rs2;
                
                statement.execute("insert into vignette (libelleV,kmV,vehiculeV,dateV,station,prix) values('" + lib + "','" + kmV + "','" + vehi.getMatricule() + "','" + dateVegnette + "', '" + stationV + "', '" + prixV + "')");
                rs2 = statment2.executeQuery("select sum(vihecule) from kilometrage where vihecule = '" + vehi.getMatricule() + "'");
                rs2.next();
                if(rs2.getInt(1) == 0){
                    statment3.execute("insert into kilometrage (vihecule, kmitit, kmfin) values('" + vehi.getMatricule() + "'," + Integer.parseInt(km.getText()) + "," + Integer.parseInt(km.getText()) +")");
                }else{
                    if(nv.isSelected()){
                        statment3.executeUpdate("update kilometrage set kmitit = " + Integer.parseInt(km.getText()) + ",kmfin = " + Integer.parseInt(km.getText()) +  " where vihecule = '"+ vehi.getMatricule() +"'");
                    }else{
                        statment3.executeUpdate("update kilometrage set kmfin = " + Integer.parseInt(km.getText()) +  " where vihecule = '"+ vehi.getMatricule() +"'");
                    }
                }
                statement.close();
                HomeController.getInstance().refreshVignette();

        }catch (Exception ex) {
            ex.printStackTrace();
        }
        
        savedvehi= vehi;
        vignetteBtnS.getScene().getWindow().hide();
        
        
        int km = 0;
        try {
            Statement statement = connection.createStatement();
            Statement statement2 = connection.createStatement();
            ResultSet rs;
            rs = statement.executeQuery("select * from kilometrage where vihecule = '" + savedvehi.getMatricule() + "'");
            
            if(rs.next() == true){
                km = rs.getInt(4) - rs.getInt(3);
                if(km >= 10000){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Information dialog");
                    alert.setContentText("SVP - Changer le vidange\nEtes vous le changer");
                    ButtonType button1 = new ButtonType("Rempoter le vidange");
                    ButtonType button2 = new ButtonType("Le vidange renouveler");
                    alert.getButtonTypes().setAll(button1,button2);
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.get()== button2){
                        statement2.executeUpdate("update kilometrage set kmitit = " + rs.getInt(4) + " where vihecule = '"+ rs.getString(2) +"'");
                    }
                }
            }
                
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    //  CLOSE  //
    @FXML void close(ActionEvent event) {
        vignetteBtnS.getScene().getWindow().hide();
    }

    @FXML void selectV(ActionEvent event) {
        Vehicule vehi = newVehiculeV.getValue();
        int km = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs;
            rs = statement.executeQuery("select * from kilometrage where vihecule = '" + vehi.getMatricule() + "'");
            
            if(rs.next() == true){
                km = rs.getInt(4) - rs.getInt(3);
                kmparcoru.setText(km + " Km");
            }else{
                kmparcoru.setText("Aucun information sur cette vehicule");
         
            }
                
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        fin.selectedProperty().setValue(Boolean.TRUE);
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
