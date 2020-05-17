package app.home;


import app.classes.Conducteur;
import app.classes.DbConnection;
import static app.classes.DbConnection.connection;
import app.classes.Vehicule;
import app.classes.mission;
import app.modules.ConducteurModule;
import app.modules.assuranceModule;
import app.modules.missionModule;
import app.modules.reparationModule;
import app.modules.vehiculeModule;
import app.modules.vidangeModule;
import app.modules.vignetteModule;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.LoginSinginController;


/**
 * FXML Controller class
 *
 * @author pc
 */
public class HomeController implements Initializable {
    
    @FXML private JFXDatePicker searchBar;
    
    //  statictics conducteurs
    @FXML private Label labelF;
    @FXML private Label labelM;
    @FXML private Label labelTotal;
    
    //  statictics conducteurs
    @FXML private Label voiture;
    @FXML private Label camionette;
    @FXML private Label moto;
    @FXML private Label transport;
    
    
    //  statictics parc auto
    @FXML private Label labelC;
    @FXML private Label labelV;
    @FXML private Label labelMe;
    
    //  statictics parc auto
    @FXML private Label labelDisp;
    @FXML private Label labelDep;
    
    //  statictics parc auto
    @FXML private Label alerteAss;
    @FXML private Label alerteVid;
    
    //  statictics parc auto
    @FXML private Label techniqueV;
    @FXML private Label vignetteV;
    
    
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
    
    
   
    public void refreshConducteurs(){
        
        ArrayList<ConducteurModule> conducteursAL = new ArrayList<>();
        conducteursTableCheckBox.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        conducteursTableFirstName.setCellValueFactory(new PropertyValueFactory<>("nomConducteur"));
        conducteursTableLastName.setCellValueFactory(new PropertyValueFactory<>("prenomConducteur"));
        conducteursTableEmail.setCellValueFactory(new PropertyValueFactory<>("emailConducteur"));
        conducteursTableSex.setCellValueFactory(new PropertyValueFactory<>("genreConducteur"));
        conducteursTablePermi.setCellValueFactory(new PropertyValueFactory<>("typeP"));
        conducteursTableDate.setCellValueFactory(new PropertyValueFactory<>("dateConducteur"));
        
        try {
            
            Statement statement1,statement2 ;
            statement1 = connection.createStatement();
                
            ResultSet rs1 = statement1.executeQuery("select * from conducteurs");
            while(rs1.next()){
                
                    String x=rs1.getString(2);
                
                    ConducteurModule cd = new ConducteurModule();
                    cd.setIdConducteur(rs1.getString(2));
                    cd.setNomConducteur(rs1.getString(3));
                    cd.setPrenomConducteur(rs1.getString(4));
                    cd.setGenreConducteur(rs1.getString(5));
                    cd.setEmailConducteur(rs1.getString(6));
                    cd.setDateConducteur(rs1.getString(7));
                    
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
        ConducteursTable.setItems(conducteurOL);
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
    
    

    
    public void refreshVehicules(){
        
        ArrayList <vehiculeModule> vehicules = new ArrayList();
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
                
                    String x=rs1.getString(2);
                
                    vehiculeModule veh = new vehiculeModule();
                    
                    veh.setMatricule(rs1.getString(2));
                    veh.setMarqueV(rs1.getString(3));
                    veh.setTypeCarb(rs1.getString(4));
                    veh.setDateAssur(rs1.getString(5));
                    veh.setDateAssur2(rs1.getString(6));
                    veh.setDateVidange(rs1.getString(7));

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
        carsTable.setItems(vehiculesOL);
        carsTable.setEditable(true);
    }
    
    //  mission tab
    
    @FXML private TableView<missionModule> missionTable;
    
    @FXML private TableColumn<missionModule, JFXCheckBox> missionTableCheckBox;
    @FXML private TableColumn<missionModule, String> tacheTableLibelle;
    @FXML private TableColumn<missionModule, String> tacheTableConducteur;
    @FXML private TableColumn<missionModule, String> tacheTableVehicule;
    @FXML private TableColumn<missionModule, String> tacheTableDateD;
    @FXML private TableColumn<missionModule, String> tacheTableDateF;
    @FXML private TableColumn<missionModule, Integer> tacheTablePercentage;
    @FXML private TableColumn<missionModule, String> tacheTableDesc;
    

    
    private static String searchDateVar = "" ;
    
    public void refreshMissions(){
        
        ArrayList <missionModule> mission = new ArrayList();
       
        tacheTableLibelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        tacheTableConducteur.setCellValueFactory(new PropertyValueFactory<>("conducteur"));
        tacheTableVehicule.setCellValueFactory(new PropertyValueFactory<>("vehicule"));
        tacheTableDateD.setCellValueFactory(new PropertyValueFactory<>("dateD"));
        tacheTableDateF.setCellValueFactory(new PropertyValueFactory<>("DateF"));
        tacheTablePercentage.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        tacheTableDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        try {
            
            Statement statement1 ;
            statement1 = connection.createStatement();

            ResultSet rs1 = statement1.executeQuery("select * from missions " + searchDateVar );
            while(rs1.next()){
                
                    missionModule mes = new missionModule();
                    
                    mes.setLibelle(rs1.getString(2));
                    mes.setVehicule(rs1.getString(3));
                    mes.setConducteur(rs1.getString(4));
                    mes.setDescription(rs1.getString(5));
                    mes.setDateD(rs1.getString(6));
                    mes.setDateF(rs1.getString(7));
                    mes.setPercentage(rs1.getInt(8));
                    
                    mission.add(mes);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        ObservableList<missionModule> missionsOL = FXCollections.observableArrayList(mission); 
        missionTable.setItems(missionsOL);
        missionTable.setEditable(true);
    }
   
    @FXML void search (ActionEvent event) {
        
        searchDateVar = " where dateFin = \"" + searchBar.getValue()+"\"";
        missionTable.getItems().clear();
        refreshMissions();
    }
    /*
     @FXML
    void search(KeyEvent event) {
        missionTable.getItems().clear();
        searchDateVar = " where dateFin = \"" + searchBar.getValue()+"\"";
        refreshMissions();
        
    }
*/
    //  vignette tab
    
    @FXML private TableView<vignetteModule> vignetteTable;
    
    @FXML private TableColumn<vignetteModule, String> vignetteTableLibelle;
    @FXML private TableColumn<vignetteModule, String> vignetteTableKm;
    @FXML private TableColumn<vignetteModule, String> vignetteTableVehicule;
    @FXML private TableColumn<vignetteModule, String> vignetteTableDate;
    @FXML private TableColumn<vignetteModule, String> vignetteTablePrix;
    @FXML private TableColumn<vignetteModule, String> vignetteTableStation;
 

    
    public void refreshVignette(){

        ArrayList <vignetteModule> vignette = new ArrayList();
        vignetteTableLibelle.setCellValueFactory(new PropertyValueFactory<>("libelleV"));
        vignetteTableKm.setCellValueFactory(new PropertyValueFactory<>("kmV"));
        vignetteTableVehicule.setCellValueFactory(new PropertyValueFactory<>("vehiculeV"));
        vignetteTableDate.setCellValueFactory(new PropertyValueFactory<>("dateV"));
        vignetteTablePrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        vignetteTableStation.setCellValueFactory(new PropertyValueFactory<>("station"));
        
        try {
            
            Statement statement1 ;
            statement1 = connection.createStatement();

            ResultSet rs1 = statement1.executeQuery("select * from vignette");
            while(rs1.next()){
                
                
                    vignetteModule veg = new vignetteModule();
                    
                    veg.setLibelleV(rs1.getString(2));
                    veg.setKmV(rs1.getString(3));
                    veg.setVehiculeV(rs1.getString(4));
                    veg.setDateV(rs1.getString(5));
                    veg.setStation(rs1.getString(6));
                    veg.setPrix(rs1.getString(7));
                    
                    vignette.add(veg);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        ObservableList<vignetteModule> vignetteOL = FXCollections.observableArrayList(vignette); 
        vignetteTable.setItems(vignetteOL);
        vignetteTable.setEditable(true);
    }
    
    //  reparation tab
    @FXML private TableView <reparationModule> reparationTable;
    
    @FXML private TableColumn<reparationModule, String> vehiculeR;
    @FXML private TableColumn<reparationModule, String> dateR;
    @FXML private TableColumn<reparationModule, String> descR;
    @FXML private TableColumn<reparationModule, String> prixR;

    
    
   
    public void refreshReparation(){
        
        ArrayList <reparationModule> reparationAL = new ArrayList<>();
        vehiculeR.setCellValueFactory(new PropertyValueFactory<>("vehiculeRepa"));
        dateR.setCellValueFactory(new PropertyValueFactory<>("dateRepa"));
        descR.setCellValueFactory(new PropertyValueFactory<>("descriptionRepa"));
        prixR.setCellValueFactory(new PropertyValueFactory<>("prixRepa"));
        
        try {
            
            Statement statement1;
            statement1 = connection.createStatement();
                
            ResultSet rs1 = statement1.executeQuery("select * from repations");
            while(rs1.next()){
                
                    reparationModule rm = new reparationModule();
                    
                    rm.setVehiculeRepa(rs1.getString(1));
                    rm.setDateRepa(rs1.getString(2));
                    rm.setDescriptionRepa(rs1.getString(3));
                    rm.setPrixRepa(rs1.getString(4));
                    
                    
                    reparationAL.add(rm);
                }
                
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        ObservableList<reparationModule> reparationOL = FXCollections.observableArrayList(reparationAL); 
        reparationTable.setItems(reparationOL);
        reparationTable.setEditable(true);
    }


    //  assurance tab
    @FXML private TableView <assuranceModule> AssuranceTable;
    
    @FXML private TableColumn<assuranceModule, String> matricule1;
    @FXML private TableColumn<assuranceModule, String> marqueV;
    @FXML private TableColumn<assuranceModule, String> assurance1;
    @FXML private TableColumn<assuranceModule, String> assurance2;
    @FXML private TableColumn<assuranceModule, String> nombreJour;

    
    
   
    public void refreshAssurance(){
        
        ArrayList <assuranceModule> assuranceAL = new ArrayList<>();
        matricule1.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        marqueV.setCellValueFactory(new PropertyValueFactory<>("marqueV"));
        assurance1.setCellValueFactory(new PropertyValueFactory<>("dateAssur"));
        assurance2.setCellValueFactory(new PropertyValueFactory<>("dateAssur2"));
        nombreJour.setCellValueFactory(new PropertyValueFactory<>("nombreJour"));
        
       
        try {
            
            Statement statement1,statement0;
            statement1 = connection.createStatement();
                
            ResultSet rs1 = statement1.executeQuery("select * from vehicules");
            while(rs1.next()){
                
                    String x=rs1.getString(2);
                
                    assuranceModule assur = new assuranceModule();
                    assur.setMatricule(rs1.getString(2));
                    assur.setMarqueV(rs1.getString(3));
                    assur.setDateAssur(rs1.getString(5));
                    assur.setDateAssur2(rs1.getString(6));
                    
                    statement0 = connection.createStatement();
                    ResultSet rs0 = statement0.executeQuery("SELECT DATEDIFF(vehiculesDateAss2, CURDATE()) FROM vehicules where vehiculesMatr = '"+ x +"'");  
                    
                    while(rs0.next()){
                        assur.setNombreJour(rs0.getInt(1));
                }  
                    assuranceAL.add(assur);
                }
                
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        ObservableList<assuranceModule> assuranceOL = FXCollections.observableArrayList(assuranceAL); 
        AssuranceTable.setItems(assuranceOL);
        AssuranceTable.setEditable(true);
    }
    
     //  vidange tab
    @FXML private TableView <vidangeModule> VidangeTable;
    
    @FXML private TableColumn<vidangeModule, String> matricule11;
    @FXML private TableColumn<vidangeModule, String> marqueVe;
    @FXML private TableColumn<vidangeModule, String> vidange1;
    @FXML private TableColumn<assuranceModule, String> nombreJour2;

    
    
   
    public void refreshVidange(){

        ArrayList <vidangeModule> vidangeAL = new ArrayList<>();
        matricule11.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        marqueVe.setCellValueFactory(new PropertyValueFactory<>("marqueV"));
        vidange1.setCellValueFactory(new PropertyValueFactory<>("dateVidange"));
        nombreJour2.setCellValueFactory(new PropertyValueFactory<>("nombreJour2"));
       
        try {
            
            Statement statement1,statement2;
            statement1 = connection.createStatement();
                
            ResultSet rs1 = statement1.executeQuery("select * from vehicules");
            while(rs1.next()){
                
                    String x = rs1.getString(2);
                
                    vidangeModule vidg = new vidangeModule();
                    vidg.setMatricule(rs1.getString(2));
                    vidg.setMarqueV(rs1.getString(3));
                    vidg.setDateVidange(rs1.getString(7));
                    
                    statement2 = connection.createStatement();
                    ResultSet rs2 = statement2.executeQuery("SELECT DATEDIFF(vehiculesDateVid, CURDATE()) FROM vehicules where vehiculesMatr = '"+ x +"'");
                    
                    while(rs2.next()){
                        vidg.setNombreJour2(rs2.getInt(1));
                    } 
                    vidangeAL.add(vidg);
                }
                
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        ObservableList<vidangeModule> vidangeOL = FXCollections.observableArrayList(vidangeAL); 
        VidangeTable.setItems(vidangeOL);
        VidangeTable.setEditable(true);
    }
    
    //  STATISTICS  ////////////////////////////////////////////////////////////
    
    public void refreshStatistics(){
        
        //  conducteurs sta    ////////////////////////////////////////////////////
        
        //  conducteurs Statistics
        int m=0,f=0,t=0;
        try {
            
            ResultSet rs1, rs2, rs3;
            Statement statement1 , statement2, statement3;
            
            statement1 = connection.createStatement();
            rs1 = statement1.executeQuery("select count(*) from conducteurs where genre = \"M\"  ");
            rs1.next();
            m = rs1.getInt(1);
            
            statement2 = connection.createStatement();
            rs2 = statement2.executeQuery("select count(*) from conducteurs where genre = \"F\"  ");
            rs2.next();
            f = rs2.getInt(1);
            
            statement3 = connection.createStatement();
            rs3 = statement3.executeQuery("select count(*) from conducteurs");
            rs3.next();
            t = rs3.getInt(1);

            labelF.setText(""+ (int) (f));
            labelM.setText(""+ (int) (m));
            labelTotal.setText(""+ (int) (t));
            
            statement1.close();
            statement2.close();
            statement3.close();
                    
        } catch (Exception ex) {
            System.out.println(ex.getMessage()); 
        }
        
        //  conducteurs sta    ////////////////////////////////////////////////////
        
        //  conducteurs Statistics
        int mo=0,vo=0,ca=0,tr=0;
        try {
            
            ResultSet rs1, rs2, rs3,rs4;
            Statement statement1 , statement2, statement3,statement4;
            
            statement1 = connection.createStatement();
            rs1 = statement1.executeQuery("select count(*) from typevehicule where typeV = \"voiture\"  ");
            rs1.next();
            vo = rs1.getInt(1);
            
            statement2 = connection.createStatement();
            rs2 = statement2.executeQuery("select count(*) from typevehicule where typeV = \"camionette\"  ");
            rs2.next();
            ca = rs2.getInt(1);
            
            statement3 = connection.createStatement();
            rs3 = statement3.executeQuery("select count(*) from typevehicule where typeV = \"moto\" ");
            rs3.next();
            mo = rs3.getInt(1);
            
            statement4 = connection.createStatement();
            rs4 = statement4.executeQuery("select count(*) from typevehicule where typeV = \"transport\" ");
            rs4.next();
            tr = rs4.getInt(1);

            moto.setText(""+ (int) (mo));
            voiture.setText(""+ (int) (vo));
            camionette.setText(""+ (int) (ca));
            transport.setText(""+ (int) (tr));
            
            statement1.close();
            statement2.close();
            statement3.close();
            statement4.close();
                    
        } catch (Exception ex) {
            System.out.println(ex.getMessage()); 
        }
        
        //  conducteurs Statistics
        int c=0,v=0,me=0;
        try {
            
            ResultSet rs1, rs2,rs3;
            Statement statement1 , statement2, statement3;
            
            statement1 = connection.createStatement();
            rs1 = statement1.executeQuery("select count(*) from conducteurs ");
            rs1.next();
            c = rs1.getInt(1);
            
            statement2 = connection.createStatement();
            rs2 = statement2.executeQuery("select count(*) from vehicules ");
            rs2.next();
            v = rs2.getInt(1);
            
            statement3 = connection.createStatement();
            rs3 = statement3.executeQuery("select count(*) from missions ");
            rs3.next();
            me = rs3.getInt(1);
            
            labelV.setText(""+ (int) (v));
            labelC.setText(""+ (int) (c));
            labelMe.setText(""+ (int) (me));

            statement1.close();
            statement2.close();
            statement3.close();
                    
        } catch (Exception ex) {
            System.out.println(ex.getMessage()); 
        }
        
        //  suivis Statistics
        int disp=0,dep=0;
        try {
            
            ResultSet rs1, rs2,rs3;
            Statement statement1 , statement2, statement3;
            
            statement1 = connection.createStatement();
            rs1 = statement1.executeQuery("select count(*) from vehicules where exitsV = 0 ");
            rs1.next();
            disp = rs1.getInt(1);
            
            statement2 = connection.createStatement();
            rs2 = statement2.executeQuery("select count(*) from vehicules where exitsV = 1 ");
            rs2.next();
            dep = rs2.getInt(1);
            
            labelDisp.setText(""+ (int) (disp));
            labelDep.setText(""+ (int) (dep));

            statement1.close();
            statement2.close();
                    
        } catch (Exception ex) {
            System.out.println(ex.getMessage()); 
        }
        
        //  assurance et vidange Statistics
        int assurance=0;
        int i=0;
        try {
            
            Statement statement1,statement2;
            statement1 = connection.createStatement();
                
            ResultSet rs1 = statement1.executeQuery("select * from vehicules");
            while(rs1.next()){
                
                    String x = rs1.getString(2);
                    
                    statement2 = connection.createStatement();
                    ResultSet rs2 = statement2.executeQuery("SELECT DATEDIFF(vehiculesDateAss2, CURDATE()) FROM vehicules where vehiculesMatr = '"+ x +"'");
                    rs2.next();
                    assurance = rs2.getInt(1);
                    if(assurance < 15 && assurance >=0){
                        i++;
                    }
                    statement2.close();
                }
            alerteAss.setText(""+ (int) (i));
            statement1.close();
                
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        /*
        int vidange=0;
        int i2=0;
        try {
            
            Statement statement1,statement2;
            statement1 = connection.createStatement();
                
            ResultSet rs1 = statement1.executeQuery("select * from vehicules");
            while(rs1.next()){
                
                    String x = rs1.getString(2);
                    
                    statement2 = connection.createStatement();
                    ResultSet rs2 = statement2.executeQuery("SELECT DATEDIFF(vehiculesDateVid, CURDATE()) FROM vehicules where vehiculesMatr = '"+ x +"' ");
                    rs2.next();
                    vidange = rs2.getInt(1);
                    if(vidange <= 15 && vidange >=0){
                        i++;
                    }
                    statement2.close();
                }
            alerteVid.setText(""+ (int) (i2));
            statement1.close();
                
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        */
        
        
        //  conducteurs Statistics
        int tec=0,veg=0;
        try {
            
            ResultSet rs1, rs2, rs3;
            Statement statement1 , statement2, statement3;
            
            statement1 = connection.createStatement();
            rs1 = statement1.executeQuery("select sum(prixReparation) from repations ");
            rs1.next();
            tec = rs1.getInt(1);
            
            statement2 = connection.createStatement();
            rs2 = statement2.executeQuery("select sum(prix) from vignette ");
            rs2.next();
            veg = rs2.getInt(1);

            techniqueV.setText(""+ (int) (tec));
            vignetteV.setText(""+ (int) (veg));
            
            statement1.close();
            statement2.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage()); 
        }
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
    
    //  MODULES MISSIONS  ///////C//////////////////////////////////////////////
    
    @FXML void vignette(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader9 = new FXMLLoader(getClass().getResource("/app/vignette/vignette.fxml"));
            Parent root9 = (Parent) fxmlLoader9.load();
            Stage stage9 = new Stage();
            stage9.setScene(new Scene(root9));
            //stage5.initStyle(StageStyle.UNDECORATED);
            stage9.show();
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
        refreshVignette();
        refreshAssurance();
        refreshVidange();
        refreshReparation();
        refreshStatistics();
    }  
}