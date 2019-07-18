/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.conducteur;

import app.classes.DbConnection;
import static app.classes.DbConnection.connection;
import app.classes.Type;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import jdk.nashorn.internal.objects.NativeArray;



public class conducteurAController implements Initializable {
    
    //  Btns
    @FXML private JFXButton SaveBtn;
    @FXML private AnchorPane paneRoot;
    
    //  new
    @FXML private JFXTextField CIN;
    @FXML private JFXTextField nom;
    @FXML private JFXTextField prenom;
    @FXML private JFXTextField email;
    @FXML private JFXDatePicker dateN;
    @FXML private JFXComboBox typeP;
    @FXML private JFXToggleButton genre;
    
    @FXML private JFXCheckBox A1,A,B,C,D,EC,EB,ED;
    

    
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
    
     
    @FXML void saveC(ActionEvent event) {
        
        Connection connection = DbConnection.DbConnect();

        String id = CIN.getText();
        String fn = nom.getText();
        String ln = prenom.getText();
        String em = email.getText();
        String date = dateN.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String sx = (!genre.isSelected()) ? "M" : "F";// MALE FEMALE
        String permiss = "";

        if (fn.equals("") || ln.equals("") || em.equals("") || date.equals("") ){
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Select a student or a groupe of students");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(cancelButton);
            Optional<ButtonType> result = alert.showAndWait();
            return ;
        }  
        ArrayList permis = new ArrayList();
        if (A.isSelected())
            permis.add("A"); 
        if (A1.isSelected())
            permis.add("A1"); 
        if (B.isSelected())
            permis.add("B"); 
        if (C.isSelected())
            permis.add("C"); 
        if (D.isSelected())
            permis.add("D"); 
        if (EC.isSelected())
            permis.add("EC"); 
        if (ED.isSelected())
            permis.add("ED"); 
        if (EB.isSelected())
            permis.add("EB"); 
        permis.forEach(e->{
            try {
                Statement statement = connection.createStatement();
                statement.execute("INSERT INTO `typepermi`(`conducteursId`, `type`) VALUES ('" + id + "','" + e + "') ");
            } catch (SQLException ex) {
                Logger.getLogger(conducteurAController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
        try {
            Statement statement = connection.createStatement();
            
                //  driver  
                statement.execute("insert into conducteurs values('" + id + "','" + fn + "','" + ln + "','" + sx + "','" + em + "', '" + date + "')");

            statement.close();

        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //  CLOSE  //
    @FXML void close(ActionEvent event) {
        SaveBtn.getScene().getWindow().hide();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
        
    }      
}

