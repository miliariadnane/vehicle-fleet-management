package app.mission;

import app.classes.Conducteur;
import app.classes.DbConnection;
import static app.classes.DbConnection.connection;
import app.classes.Vehicule;
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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.event.ActionEvent;


public class MissionController implements Initializable {
    
    
    @FXML private JFXButton missionBtnS;
    
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
    void addMission(ActionEvent event) throws ParseException  {
        
        Connection connection = DbConnection.DbConnect();
        
        String lib = libelle.getText();
        String desc = description.getText();
        String dateDebut = dateD.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String dateFin = dateF.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int perce = (int) Percentage.getValue();
        Conducteur condu = newConducteur.getValue();
        Vehicule vehi = newVehicule.getValue();

        if (lib.equals("") || desc.equals("") || dateDebut.equals("") || condu.equals("") || dateFin.equals("") ){
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setHeaderText("remplir les champs");
            ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(cancelButton);
            Optional<ButtonType> result = alert.showAndWait();
            return ;
        }  
        
        try {
            Statement statement,statement1,statement2;
                    
                statement = connection.createStatement();
                
                statement.execute("insert into missions (libelle,vehicule,conducteur,description,dateDebut,dateFin,percentage) values('" + lib + "','" + vehi.getMatricule() + "','" + condu.getIdConducteur() + "','" + desc + "', '" + dateDebut + "', '" + dateFin + "', '" + perce + "')");
                
                statement1 = connection.createStatement();
                
                statement1.executeUpdate("update vehicules set exitsV = 1 where vehiculesMatr = '" + vehi.getMatricule() + "' ");
                
                statement2 = connection.createStatement();
                
                statement2.executeUpdate("update conducteurs set existC = 1 where conducteursId = '" + condu.getIdConducteur() + "' ");             
                
            
            statement1.close();
            statement2.close();
            statement.close();

        }catch (Exception ex) {
            ex.printStackTrace();
        }
        missionBtnS.getScene().getWindow().hide();
    }
    
    public void refreshMissionsDate(){

        try {
            
            Statement statement6 ;
            statement6 = connection.createStatement();

            statement6.executeUpdate("update vehicules inner join missions on missions.vehicule = vehicules.vehiculesMatr set exitsV = 0 where CURDATE() > dateFin");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            
            Statement statement6 ;
            statement6 = connection.createStatement();

            statement6.executeUpdate("update conducteurs inner join missions on missions.conducteur = conducteurs.conducteursId set existC = 0 where CURDATE() > dateFin");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    //  CLOSE  //
    @FXML void close(ActionEvent event) {
        missionBtnS.getScene().getWindow().hide();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        refreshMissionsDate();
        
        //  Set conducteurs
        ArrayList conducteursAL = new ArrayList();
        
        try {
            ResultSet rs1;
            Statement statement1 = connection.createStatement();
            rs1 = statement1.executeQuery("select * from conducteurs where existC = 0");
                     
            while(rs1.next()){
                conducteursAL.add(new Conducteur(rs1.getString(2), rs1.getString(3), rs1.getString(3), rs1.getString(4), rs1.getString(5), rs1.getString(6) ) );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ObservableList<Conducteur> conducteurOL = FXCollections.observableArrayList(conducteursAL);
        newConducteur.setItems(conducteurOL);
        
        //  Set vehicules
        ArrayList vehiculesAL = new ArrayList();
        
        
        try {
            ResultSet rs2;
            Statement statement2 = connection.createStatement();
            rs2 = statement2.executeQuery("select * from vehicules where exitsV = 0");

            while(rs2.next()){
                
                vehiculesAL.add(new Vehicule (rs2.getString(2), rs2.getString(3), rs2.getString(4), rs2.getString(5), rs2.getString(6), rs2.getString(7)) );
                
                }
            }catch (Exception ex) {
             ex.printStackTrace();
        }

        ObservableList<Vehicule> vehiculeOL = FXCollections.observableArrayList(vehiculesAL);
        newVehicule.setItems(vehiculeOL);

    }
    
    
}    
    

