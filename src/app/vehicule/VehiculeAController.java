/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.vehicule;

import app.classes.DbConnection;
import static app.classes.DbConnection.connection;
import app.conducteur.conducteurAController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
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
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class VehiculeAController implements Initializable {

    //  Btns
    @FXML private JFXButton saveBtnV;
    @FXML private AnchorPane paneRoot;
    
    //  new
    @FXML private JFXTextField matricule;
    @FXML private JFXTextField marque;
    @FXML private JFXDatePicker dateA;
    @FXML private JFXDatePicker dateA2;
    @FXML private JFXDatePicker dateV;

    //
    @FXML private JFXComboBox typeC;
    
    @FXML private JFXRadioButton moto,voiture,camionette;
    
    @FXML private ImageView img;
    @FXML private ImageView img1;
    
    
    // UPLOADING IMAGE /////////////////////////////////////////////////////////
    

    @FXML void uploadImage(ActionEvent event) throws IOException {
       
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        

        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.JPG");
        FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)","*.PNG");
        fc.getExtensionFilters().addAll(ext1,ext2);
        File file = fc.showOpenDialog(primaryStage);
          
        BufferedImage bf;
        
            try {
                bf = ImageIO.read(file);
                WritableImage image = SwingFXUtils.toFXImage(bf, null);
                img.setImage(image);
                FileInputStream fin = new FileInputStream(file);
                int len = (int)file.length();
                
                PreparedStatement ps = connection.prepareStatement("INSERT INTO img(img)VALUES(?)");
                ps.setBinaryStream(1,fin,len);
                int status = ps.executeUpdate();
                if(status>0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Information dialog");
                    alert.setContentText("Photo saved successfully");
                    alert.showAndWait();
                    
                        
                          
               }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog.");
                    alert.setHeaderText("Error Information");
                    alert.showAndWait();
                }
                
                DbConnection.connection.close();
                   
            } catch (Exception ex) {
                System.out.println(ex.getCause());
            }

            
    }
     
    @FXML void saveV(ActionEvent event) {
        
        Connection connection = DbConnection.DbConnect();

        String mat = matricule.getText();
        String marq = marque.getText();
        String typeCar = (String) typeC.getValue();
        String dateAssu = dateA.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String dateAssu2 = dateA2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String dateVid = dateV.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if (mat.equals("") || marq.equals("") || typeC.equals("") ){
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Il faut remplir les champs");
            ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(cancelButton);
            Optional<ButtonType> result = alert.showAndWait();
            return ;
        } 
        // TYPE VEHICULE
        
        ArrayList typeV = new ArrayList();
        if (moto.isSelected())
            typeV.add("moto"); 
        if (voiture.isSelected())
            typeV.add("voiture"); 
        if (camionette.isSelected())
            typeV.add("camionette"); 
        typeV.forEach(e->{
            try {
                Statement statement = connection.createStatement();
                statement.execute("INSERT INTO `typevehicule`(`vehiculesMatr`, `typeV`) VALUES ('" + mat + "','" + e + "') ");
            } catch (SQLException ex) {
                Logger.getLogger(conducteurAController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        try {
            Statement statement = connection.createStatement();
            //  cars  
            statement.execute("insert into vehicules(vehiculesMatr, vehiculesMarque, vehiculesCarb, vehiculesDateAss, vehiculesDateAss2, vehiculesDateVid) values('" + mat + "','" + marq + "','" + typeCar + "','" + dateAssu + "','" + dateAssu2 + "','" + dateVid + "')");

            statement.close();

        }catch (Exception ex) {
            ex.printStackTrace();
        }
        saveBtnV.getScene().getWindow().hide();
    }

    //  CLOSE  //
    @FXML void close(ActionEvent event) {
        saveBtnV.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TYPE CARBURANT
        ArrayList carburantAL = new ArrayList();
        
        carburantAL.add("Essence");
        carburantAL.add("Diesel");
        
        ObservableList carburantOL = FXCollections.observableArrayList(carburantAL);
        typeC.setItems(carburantOL);
        
    }    
    
}
