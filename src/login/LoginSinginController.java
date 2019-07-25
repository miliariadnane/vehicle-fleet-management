/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import app.classes.DbConnection;
import static app.classes.DbConnection.connection;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

/**
 * FXML Controller class
 *
 * @author pc
 */
public class LoginSinginController implements Initializable {

    //  Connection  ////////////////////////////////////////////////////////////
    
    @FXML private Label connectedLabel;
    
    ////////////////////////////////////////////////////////////////////////////
    private static String user = null;
    private static String type = null;
    

    public static String getUser(){
        return user;
    }
    
    public static String getType(){
        return type;
    }
    ////////////////////////////////////////////////////////////////////////////
    
    //  LOGIN   ////////////////////////////////////////////////////////////////
    
    //  LOGIN   ////////////////////////////////////////////////////////////////
    
    //  LOGIN ATTRIBUTES  //
    
    @FXML private JFXTextField loginUsername;
    @FXML private JFXPasswordField loginPassword;
    
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
                ex.printStackTrace();
            }
    }
    
     //  LOGIN METHODE   ////////////////////////////////////////////////////////
    
    @FXML void login(ActionEvent event) throws SQLException {
        
        if( connectedLabel.getText().equals("CONNECTION FIELD")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Il faut se connecter à la base de données d'abord");
            return;
        }
        
        String username = loginUsername.getText();
        String password = loginPassword.getText();

        if (username.equals("") || password.equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Il faut remplir les champs");
            return;
        }
        
        try {
            
            ResultSet rs;
            Statement statement = connection.createStatement();
                // students
                
                rs = statement.executeQuery("select adminId from admin where (adminId = '" + username + "' or adminEmail = '" + username + "') and adminPassword = '" + password + "' ");
 
            if(!rs.next()){
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("informtion erroné");
                

            }else{

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("bien s'ethentifier");

                
                //  save the user logged in
                
                user = rs.getString(1);
                statement.close();
                
                //  Close the login page
                
                Node node = (Node) event.getSource();
                Stage currentStage = (Stage) node.getScene().getWindow();
                currentStage.close();
                
                //  Open main page

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/app/home/home.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
     //  SINGING ////////////////////////////////////////////////////////////////
    
    //  SINGING ATTRIBUTES //  
    
    @FXML private JFXTextField singinCIN;
    @FXML private JFXTextField singinFirstName;
    @FXML private JFXTextField singinLastName;
    @FXML private JFXTextField singinEmail;
    @FXML private JFXToggleButton singinSex;
    @FXML private JFXDatePicker singingDate;
    @FXML private JFXPasswordField singingPassword;
    @FXML private JFXPasswordField singingPasswordC;

    @FXML private Pane snackbarParent2;
    @FXML private JFXSnackbar snackbar2;
    
    @FXML private Label message2;

    @FXML void singing(ActionEvent event) {
        
        if( connectedLabel.getText().equals("CONNECTION FIELD")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            return;
        }
        
        String id = singinCIN.getText();
        String fn = singinFirstName.getText();
        String ln = singinLastName.getText();
        String em = singinEmail.getText();
        String date = singingDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String sx = (!singinSex.isSelected()) ? "M" : "F";// MALE FEMALE
        String pass = singingPassword.getText();

        if (id.equals("") || fn.equals("") || ln.equals("") || em.equals("") ||  pass.equals("") ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Il faut remplir les champs");
            ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(cancelButton);
            Optional<ButtonType> result = alert.showAndWait();
            return;
        }
        
        if( !singingPassword.getText().equals(singingPasswordC.getText())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("le mot de passe c'est pas le meme");
            return;
        }
       
        try {
            
            Statement statement = connection.createStatement();

                statement.execute("insert into admin values('" + id + "','" + fn + "','" + ln + "','" + sx + "','" + em + "','" + pass + "', \"" + date + "\" )");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("nouveau utilisateur ajouté");
            statement.close();

        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

     //  CANCEL /////////////////////////////////////////////////////////////////
    
    @FXML void cancel(ActionEvent event) {
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //  Db connection  /////////////////////////////////////////////////////
        
        try {
            Connection connection = DbConnection.DbConnect();
            connectedLabel.setPrefWidth(90);
            connectedLabel.setText("CONNECTED");
        } catch (Exception ex) {
            System.out.println(ex.getMessage()); 
            connectedLabel.setPrefWidth(130);
            connectedLabel.setText("CONNECTION FIELD");
        }
    }    
}
