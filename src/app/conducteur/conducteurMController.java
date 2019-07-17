
package app.conducteur;

import app.classes.Conducteur;
import static app.classes.DbConnection.connection;
import app.home.HomeController;
import app.modules.ConducteurModule;
import com.jfoenix.controls.JFXButton;
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
    @FXML private JFXComboBox <ConducteurModule> editconducteurSelect;
    @FXML private JFXTextField editCIN;
    @FXML private JFXTextField editNom;
    @FXML private JFXTextField editPrenom;
    @FXML private JFXTextField editEmail;
    @FXML private JFXDatePicker editDateN;
    @FXML private JFXComboBox editTypeP;
    @FXML private JFXToggleButton editGenre;
          
    //  Select to edit
    @FXML void selectedConducteur(ActionEvent event) {
        
        if(editconducteurSelect.getValue() == null){
            return;
        }
        
        String selectedConducteurId = editconducteurSelect.getValue().getIdConducteur();
        
        System.out.println(selectedConducteurId);
        try { 
            
            ResultSet rs;
            Statement statement = connection.createStatement();
            rs = statement.executeQuery("select * from conducteurs where conducteursId =  \"" + selectedConducteurId + "\"  ");
            rs.next();
                editCIN.setText(rs.getString(1));  
                editNom.setText(rs.getString(2));  
                editPrenom.setText(rs.getString(3)); 
                editEmail.setText(rs.getString(5));
                editDateN.setValue(LocalDate.parse(rs.getString(6)));
                editGenre.setText(rs.getString(4));       
                
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
        
        if (editCIN.getText().equals("") || editNom.getText().equals("") || editPrenom.getText().equals("") || editEmail.getText().equals("") ){
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setHeaderText("Fill all fields");
            //alert.setContentText("");

            ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(cancelButton);
            
            alert.showAndWait();
        
        }else{
            
            String dateS = editDateN.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));    
            
            try {
                
                Statement statement = connection.createStatement();
                statement.execute("update conducteurs set conducteursId = '" + editCIN.getText() + "', nom = '" + editNom.getText() + "', prenom = '" + editPrenom.getText()+ "', genre = '" + editGenre.getText()+"', email = '" + editEmail.getText()+ dateS + "' where moduleId = '" + selectedConducteurId + "'  ");
                statement.close();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }
    
    //  Delete Module
    
    @FXML void supC (ActionEvent event) {
    
        //  Verification
        
        if(editconducteurSelect.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Select module ?");
            //alert.setContentText("");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
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
        
        //  Delete subscribes of student into this module
        try {
            PreparedStatement statment = (PreparedStatement) connection.prepareStatement("delete from conducteurs where conducteursId = '"+ selectedConducteurId +"' ");
            statment.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\n" + e.getCause());
        }
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

        ObservableList<ConducteurModule> conducteurOL = FXCollections.observableArrayList(conducteursAL);
        editconducteurSelect.setItems(conducteurOL);
        
    }    
}
