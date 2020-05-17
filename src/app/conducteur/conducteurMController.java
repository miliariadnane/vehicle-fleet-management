
package app.conducteur;

import app.classes.Conducteur;
import static app.classes.DbConnection.connection;
import app.home.HomeController;
import app.modules.ConducteurModule;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
public class conducteurMController implements Initializable {

    //  Btns
    @FXML private JFXButton newSaveBtn;
    @FXML private AnchorPane paneRoot;
    
    
    //  edit
    @FXML private JFXComboBox <Conducteur> editconducteurSelect;
    @FXML private JFXTextField editCIN;
    @FXML private JFXTextField editNom;
    @FXML private JFXTextField editPrenom;
    @FXML private JFXTextField editEmail;
    @FXML private JFXDatePicker editDateN;
    @FXML private JFXToggleButton editGenre;
    @FXML private JFXCheckBox A1,A,B,C,D,EC,EB,ED;
          
    //  Select to edit
    
    @FXML void selectedConducteur(ActionEvent event) {
        
        if(editconducteurSelect.getValue() == null){
            return;
        }
        
        String selectedConducteurId = editconducteurSelect.getValue().getIdConducteur();
        
        try { 
            
            ResultSet rs;
            Statement statement = connection.createStatement();
            rs = statement.executeQuery("select * from conducteurs where conducteursId =  \"" + selectedConducteurId + "\"  ");
            rs.next();
              
                editCIN.setText(rs.getString(2));
                editNom.setText(rs.getString(3));  
                editPrenom.setText(rs.getString(4)); 
                editEmail.setText(rs.getString(6));
                editDateN.setValue(LocalDate.parse(rs.getString(7)));
                editGenre.setText(rs.getString(5)); 
                
                
                
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    //  Edit    
    @FXML void editSave(ActionEvent event) {

        if(editconducteurSelect.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setHeaderText("Select conducteur ?");
            //alert.setContentText("");
            ButtonType cancelButton = new ButtonType("Anuuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(cancelButton);
            Optional<ButtonType> result = alert.showAndWait();  
            return;
        }
        
        String selectedConducteurId = editconducteurSelect.getValue().getIdConducteur();
        
        if (editCIN.getText() == null || editNom.getText() == null || editPrenom.getText() == null || editEmail.getText() == null || editGenre.getText() == null || editDateN.getValue().equals("") ){
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setHeaderText("Fill all fields");
            ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(cancelButton);
            alert.showAndWait();
        
        }else{
            
            String id = editCIN.getText();
            String permiss = "";
            String date1 = editDateN.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));    
            Statement statement1,statement2;
            try {
                
                statement1 = connection.createStatement();
                statement2 = connection.createStatement();
                
                statement1.executeUpdate("update conducteurs set conducteursId = '" + editCIN.getText() + "' ,nom = '" + editNom.getText() + "', prenom = '" + editPrenom.getText()+ "', genre = '" + editGenre.getText()+"', email = '" + editEmail.getText()+ "', dateN = '"+ date1 + "' where conducteursId = '" + selectedConducteurId + "'");
                statement1.close();
                HomeController.getInstance().refreshConducteurs();

            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        newSaveBtn.getScene().getWindow().hide();
    }
    //  Delete Module
    
    @FXML void supC (ActionEvent event) {
    
        //  Verification
        
        if(editconducteurSelect.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setHeaderText("selctionner un conducteur ?");
            //alert.setContentText("");
            ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(cancelButton);
            Optional<ButtonType> result = alert.showAndWait();  
            return;
        }
        
        String selectedConducteurId = editconducteurSelect.getValue().getIdConducteur();
        
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
        
        //  Delete 
       
        try {
            PreparedStatement statment = (PreparedStatement) connection.prepareStatement("delete from conducteurs where conducteursId = '"+ selectedConducteurId +"' ");
            statment.executeUpdate();
            HomeController.getInstance().refreshConducteurs();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\n" + e.getCause());
        }
        
        try {
            PreparedStatement statment = (PreparedStatement) connection.prepareStatement("delete from typepermi where conducteursId = '"+ selectedConducteurId +"'");
            statment.executeUpdate();
            HomeController.getInstance().refreshConducteurs();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\n" + e.getCause());
        }

        newSaveBtn.getScene().getWindow().hide();
    }    
    
    //  CLOSE  //
    @FXML void close(ActionEvent event) {
        newSaveBtn.getScene().getWindow().hide();
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
                conducteursAL.add(new Conducteur(rs.getString(2), rs.getString(3), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6) ) );
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage()); 
        }

        ObservableList<Conducteur> conducteurOL = FXCollections.observableArrayList(conducteursAL);
        editconducteurSelect.setItems(conducteurOL);
        
    }    
}
