package app.home;


import app.classes.Conducteur;
import app.classes.DbConnection;
import static app.classes.DbConnection.connection;
import app.classes.Vehicule;
import app.modules.ConducteurModule;
import app.modules.assuranceModule;
import app.modules.missionModule;
import app.modules.reparationModule;
import app.modules.vehiculeModule;
import app.modules.vidangeModule;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class HomeController implements Initializable {
    
    @FXML private JFXTextField searchBar;
    Connection connection;
    //  Tables
    
    //  Driver tab
    @FXML private TableView <ConducteurModule> ConducteursTable;
    
    @FXML private TableColumn<ConducteurModule, JFXCheckBox> conducteursTableCheckBox;
    @FXML private TableColumn<ConducteurModule, String> conducteursTableFirstName;
    @FXML private TableColumn<ConducteurModule, String> conducteursTableLastName;
    @FXML private TableColumn<ConducteurModule, String> conducteursTableEmail;
    @FXML private TableColumn<ConducteurModule, String> conducteursTableSex;
    @FXML private TableColumn<ConducteurModule, String> conducteursTableDate;
    @FXML private TableColumn<ConducteurModule, String> conducteursTablePermi;
    
    ArrayList<ConducteurModule> conducteursAL = new ArrayList<>();
   
    public void refreshConducteurs(){

        conducteursTableCheckBox.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        conducteursTableFirstName.setCellValueFactory(new PropertyValueFactory<>("nomConducteur"));
        conducteursTableLastName.setCellValueFactory(new PropertyValueFactory<>("prenomConducteur"));
        conducteursTableEmail.setCellValueFactory(new PropertyValueFactory<>("genreConducteur"));
        conducteursTableSex.setCellValueFactory(new PropertyValueFactory<>("emailConducteur"));
        conducteursTablePermi.setCellValueFactory(new PropertyValueFactory<>("typeP"));
        conducteursTableDate.setCellValueFactory(new PropertyValueFactory<>("dateConducteur"));
       
        try {
            
            Statement statement1,statement2 ;
            statement1 = connection.createStatement();
                
            ResultSet rs1 = statement1.executeQuery("select * from conducteurs");
            while(rs1.next()){
                
                    String x=rs1.getString(1);
                
                    ConducteurModule cd = new ConducteurModule();
                    cd.setIdConducteur(rs1.getString(1));
                    cd.setNomConducteur(rs1.getString(2));
                    cd.setPrenomConducteur(rs1.getString(3));
                    cd.setGenreConducteur(rs1.getString(4));
                    cd.setEmailConducteur(rs1.getString(5));
                    cd.setDateConducteur(rs1.getString(6));
                    
                statement2 = connection.createStatement();
                ResultSet rs2 = statement2.executeQuery("select * from typepermi where conducteursId = '"+ x +"'");
                String mot="";
                while(rs2.next()){
                    mot = mot+ rs2.getString(2)+" ; ";
                    cd.typeP.add(rs2.getString(2));
                }
                conducteursAL.add(cd);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        ObservableList<ConducteurModule> conducteurOL = FXCollections.observableArrayList(conducteursAL); 
        ConducteursTable.getItems().addAll(conducteurOL);
        ConducteursTable.setEditable(true);
    }
    
    //  car tab
    
    @FXML private TableView<vehiculeModule> carsTable;
    
    @FXML private TableColumn<vehiculeModule, JFXCheckBox> VehiculesTableCheckBox;
    @FXML private TableColumn<vehiculeModule, String> matricule;
    @FXML private TableColumn<vehiculeModule, String> marqueCar;
    @FXML private TableColumn<vehiculeModule, String> carburant;
    @FXML private TableColumn<vehiculeModule, String> dateAssurance;
    @FXML private TableColumn<vehiculeModule, String> dateAssurance2;
    @FXML private TableColumn<vehiculeModule, String> dateVidange;
    @FXML private TableColumn<vehiculeModule, String> typeVehicule;
    
    
    private ArrayList <vehiculeModule> vehicules = new ArrayList();
    
    public void refreshVehicules(){

        VehiculesTableCheckBox.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        matricule.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        marqueCar.setCellValueFactory(new PropertyValueFactory<>("marqueV"));
        carburant.setCellValueFactory(new PropertyValueFactory<>("typeCarb"));
        dateAssurance.setCellValueFactory(new PropertyValueFactory<>("dateAssur"));
        dateAssurance2.setCellValueFactory(new PropertyValueFactory<>("dateAssur2"));
        dateVidange.setCellValueFactory(new PropertyValueFactory<>("dateVidange"));
        typeVehicule.setCellValueFactory(new PropertyValueFactory<>("typeV"));
        
        
        try {
            
            Statement statement1,statement2 ;
            statement1 = connection.createStatement();

            ResultSet rs1 = statement1.executeQuery("select * from vehicules");
            while(rs1.next()){
                
                    String x=rs1.getString(1);
                
                    vehiculeModule veh = new vehiculeModule();
                    
                    veh.setMatricule(rs1.getString(1));
                    veh.setMarqueV(rs1.getString(2));
                    veh.setTypeCarb(rs1.getString(3));
                    veh.setDateAssur(rs1.getString(4));
                    veh.setDateAssur2(rs1.getString(5));
                    veh.setDateVidange(rs1.getString(6));

                statement2 = connection.createStatement();
                ResultSet rs2 = statement2.executeQuery("select * from typevehicule where vehiculesMatr = '"+ x +"'");
                String mot="";
                while(rs2.next()){
                    mot = mot+ rs2.getString(3)+" ; ";
                    veh.typeV.add(rs2.getString(3));
                }
                vehicules.add(veh);

                
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        ObservableList<vehiculeModule> vehiculesOL = FXCollections.observableArrayList(vehicules); 
        carsTable.getItems().addAll(vehiculesOL);
        carsTable.setEditable(true);
    }
    
    
    //  mission tab
    
    @FXML private TableView<missionModule> missionTable;
    
    @FXML private TableColumn<missionModule, JFXCheckBox> missionTableCheckBox;
    @FXML private TableColumn<missionModule, String> tacheTableLibelle;
    @FXML private TableColumn<missionModule, String> tacheTableConducteur;
    @FXML private TableColumn<missionModule, String> tacheTableVehicule;
    @FXML private TableColumn<missionModule, String> tacheTableTypeV;
    @FXML private TableColumn<missionModule, String> tacheTableDateD;
    @FXML private TableColumn<missionModule, String> tacheTableDateF;
    @FXML private TableColumn<missionModule, Integer> tacheTablePercentage;
    @FXML private TableColumn<missionModule, String> tacheTableDesc;
    
    private ArrayList <missionModule> mission = new ArrayList();
    
    public void refreshMissions(){

        VehiculesTableCheckBox.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        tacheTableLibelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        tacheTableConducteur.setCellValueFactory(new PropertyValueFactory<>("conducteur"));
        tacheTableVehicule.setCellValueFactory(new PropertyValueFactory<>("vehicule"));
        tacheTableTypeV.setCellValueFactory(new PropertyValueFactory<>("typeV"));
        tacheTableDateD.setCellValueFactory(new PropertyValueFactory<>("dateD"));
        tacheTableDateF.setCellValueFactory(new PropertyValueFactory<>("DateF"));
        tacheTablePercentage.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        tacheTableDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        try {
            
            Statement statement1 ;
            statement1 = connection.createStatement();

            ResultSet rs1 = statement1.executeQuery("select * from missions");
            while(rs1.next()){
                
                    String x=rs1.getString(1);
                
                    missionModule mes = new missionModule();
                    
                    mes.setLibelle(rs1.getString(2));
                    mes.setVehicule(rs1.getString(3));
                    mes.setConducteur(rs1.getString(4));
                    mes.setTypeV(rs1.getString(5));
                    mes.setDescription(rs1.getString(6));
                    mes.setDateD(rs1.getString(7));
                    mes.setDateF(rs1.getString(8));
                    mes.setPercentage(rs1.getInt(9));
                    
                    mission.add(mes);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        ObservableList<missionModule> missionsOL = FXCollections.observableArrayList(mission); 
        missionTable.getItems().addAll(missionsOL);
        missionTable.setEditable(true);
    }
    
    //  assurance tab
    @FXML private TableView <assuranceModule> AssuranceTable;
    
    @FXML private TableColumn<assuranceModule, String> matricule1;
    @FXML private TableColumn<assuranceModule, String> marqueV;
    @FXML private TableColumn<assuranceModule, String> assurance1;
    @FXML private TableColumn<assuranceModule, String> assurance2;
    //@FXML private TableColumn<assuranceModule, String> nombreJour;

    
    ArrayList <assuranceModule> assuranceAL = new ArrayList<>();
   
    public void refreshAssurance(){
        /*
        try {
            
            Statement statement0;
            statement0 = connection.createStatement();
                
            ResultSet rs0 = statement0.executeQuery("SELECT DATEDIFF(day,sysdate, vehiculesDateAss2)");  
            String x=rs0.getString(1);
                
        } catch (Exception ex) {
            ex.printStackTrace();
        }
*/

        matricule1.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        marqueV.setCellValueFactory(new PropertyValueFactory<>("marqueV"));
        assurance1.setCellValueFactory(new PropertyValueFactory<>("dateAssur"));
        assurance2.setCellValueFactory(new PropertyValueFactory<>("dateAssur2"));
        //nombreJour.setCellValueFactory(new PropertyValueFactory<>("x"));
       
        try {
            
            Statement statement1;
            statement1 = connection.createStatement();
                
            ResultSet rs1 = statement1.executeQuery("select * from vehicules");
            while(rs1.next()){
                
                    String x=rs1.getString(1);
                
                    assuranceModule assur = new assuranceModule();
                    assur.setMatricule(rs1.getString(1));
                    assur.setMarqueV(rs1.getString(2));
                    assur.setDateAssur(rs1.getString(4));
                    assur.setDateAssur2(rs1.getString(5));
                    
                    
                    assuranceAL.add(assur);
                }
                
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        ObservableList<assuranceModule> assuranceOL = FXCollections.observableArrayList(assuranceAL); 
        AssuranceTable.getItems().addAll(assuranceOL);
        AssuranceTable.setEditable(true);
    }
    
     //  assurance tab
    @FXML private TableView <vidangeModule> VidangeTable;
    
    @FXML private TableColumn<vidangeModule, String> matricule11;
    @FXML private TableColumn<vidangeModule, String> marqueVe;
    @FXML private TableColumn<vidangeModule, String> vidange1;
    //@FXML private TableColumn<assuranceModule, String> nombreJour2;

    
    ArrayList <vidangeModule> vidangeAL = new ArrayList<>();
   
    public void refreshVidange(){

        matricule11.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        marqueVe.setCellValueFactory(new PropertyValueFactory<>("marqueV"));
        vidange1.setCellValueFactory(new PropertyValueFactory<>("dateVidange"));
        //nombreJour2.setCellValueFactory(new PropertyValueFactory<>("nombreJour2"));
       
        try {
            
            Statement statement1;
            statement1 = connection.createStatement();
                
            ResultSet rs1 = statement1.executeQuery("select * from vehicules");
            while(rs1.next()){
                
                    String x=rs1.getString(1);
                
                    vidangeModule vidg = new vidangeModule();
                    vidg.setMatricule(rs1.getString(1));
                    vidg.setMarqueV(rs1.getString(2));
                    vidg.setDateVidange(rs1.getString(6));
                    //vidg.setDateAssur2(rs1.getString(5));
                    
                    
                    vidangeAL.add(vidg);
                }
                
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        ObservableList<vidangeModule> vidangeOL = FXCollections.observableArrayList(vidangeAL); 
        VidangeTable.getItems().addAll(vidangeOL);
        VidangeTable.setEditable(true);
    }
    
    //  reparation tab
    @FXML private TableView <reparationModule> reparationTable;
    
    @FXML private TableColumn<reparationModule, String> vehiculeR;
    @FXML private TableColumn<reparationModule, String> dateR;
    @FXML private TableColumn<reparationModule, String> descR;
    @FXML private TableColumn<reparationModule, String> prixR;

    
    ArrayList <reparationModule> reparationAL = new ArrayList<>();
   
    public void refreshReparation(){

        vehiculeR.setCellValueFactory(new PropertyValueFactory<>("vehiculeRepa"));
        dateR.setCellValueFactory(new PropertyValueFactory<>("dateRepa"));
        descR.setCellValueFactory(new PropertyValueFactory<>("descriptionRepa"));
        prixR.setCellValueFactory(new PropertyValueFactory<>("prixRepa"));
        
        try {
            
            Statement statement1;
            statement1 = connection.createStatement();
                
            ResultSet rs1 = statement1.executeQuery("select * from repations");
            while(rs1.next()){
                
                    String x=rs1.getString(1);
                
                    reparationModule rm = new reparationModule();
                    
                    rm.setVehiculeRepa(rs1.getString(1));
                    rm.setDateRepa(rs1.getString(2));
                    rm.setDescriptionRepa(rs1.getString(3));
                    rm.setPrixRepa(rs1.getFloat(4));
                    
                    
                    reparationAL.add(rm);
                }
                
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        ObservableList<reparationModule> reparationOL = FXCollections.observableArrayList(reparationAL); 
        reparationTable.getItems().addAll(reparationOL);
        reparationTable.setEditable(true);
    }



    ////////////////////////////////////////////////////////////////////////////
    // INSTANCES ///////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    private static HomeController instance ;

    public HomeController(){
        instance = this;
    }
    
    public static HomeController getInstance(){
        return instance;
    }
    ////////////////////////////////////////////////////////////////////////////
    
    public void closeHome(){
        Stage currentStage = (Stage) ConducteursTable.getScene().getWindow();
        currentStage.close();
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    //  logout  ////////////////////////////////////////////////////////////////
    
    @FXML void logout(ActionEvent event) throws IOException {
        
        //  Close the main page
        
        Stage currentStage = (Stage) ConducteursTable.getScene().getWindow();
        currentStage.close();

        //  Open login page

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login/loginSingin.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
   
    }

    //  Drivers  //////////////////////////////////////////////////////////////
    
    @FXML void newC(ActionEvent event) {
        try {
            
            FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/app/conducteur/conducteurA.fxml"));
            Parent root2 = (Parent) fxmlLoader2.load();
            Stage stage2 = new Stage();
            stage2.setScene(new Scene(root2));
            //stage2.initStyle(StageStyle.UNDECORATED);
            stage2.show();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    
    //  edit Drivers  //////////////////////////////////////////////////////////////
    
    @FXML void editC(ActionEvent event) {
        try {
            
            FXMLLoader fxmlLoader3 = new FXMLLoader(getClass().getResource("/app/conducteur/conducteurM.fxml"));
            Parent root3 = (Parent) fxmlLoader3.load();
            Stage stage3 = new Stage();
            stage3.setScene(new Scene(root3));
            //stage3.initStyle(StageStyle.UNDECORATED);
            stage3.show();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    //  Cars  //////////////////////////////////////////////////////////////
    
    @FXML void newV(ActionEvent event) {
        try {
            
            FXMLLoader fxmlLoader4 = new FXMLLoader(getClass().getResource("/app/vehicule/vehiculeA.fxml"));
            Parent root4 = (Parent) fxmlLoader4.load();
            Stage stage4 = new Stage();
            stage4.setScene(new Scene(root4));
            //stage4.initStyle(StageStyle.UNDECORATED);
            stage4.show();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    //  RDIT CARS  //////////////////////////////////////////////////////////////
    
    @FXML void editV(ActionEvent event) {
        try {
            
            FXMLLoader fxmlLoader7 = new FXMLLoader(getClass().getResource("/app/vehicule/vehiculeM.fxml"));
            Parent root7 = (Parent) fxmlLoader7.load();
            Stage stage7 = new Stage();
            stage7.setScene(new Scene(root7));
            //stage2.initStyle(StageStyle.UNDECORATED);
            stage7.show();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //  reparation  //////////////////////////////////////////////////////////////
    
    @FXML void reparation(ActionEvent event) {
        try {
            
            FXMLLoader fxmlLoader8 = new FXMLLoader(getClass().getResource("/app/reparation/reparation.fxml"));
            Parent root8 = (Parent) fxmlLoader8.load();
            Stage stage8 = new Stage();
            stage8.setScene(new Scene(root8));
            //stage2.initStyle(StageStyle.UNDECORATED);
            stage8.show();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // LEFT PANEL //////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    //  MODULES MISSIONS  ///////C//////////////////////////////////////////////
    
    @FXML void module(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader6 = new FXMLLoader(getClass().getResource("/app/mission/mission.fxml"));
            Parent root6 = (Parent) fxmlLoader6.load();
            Stage stage6 = new Stage();
            stage6.setScene(new Scene(root6));
            //stage5.initStyle(StageStyle.UNDECORATED);
            stage6.show();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = DbConnection.DbConnect();
        refreshConducteurs();
        refreshVehicules();
        refreshMissions();
        refreshAssurance();
        refreshVidange();
    }  
}