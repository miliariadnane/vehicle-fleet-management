package app.modules;


import app.classes.Conducteur;
import com.jfoenix.controls.JFXCheckBox;


public class ConducteurModule extends Conducteur{
    
    private JFXCheckBox checkBox;
    private String permi;
    
    public ConducteurModule(JFXCheckBox checkBox, String idConducteur, String nomConducteur, String prenomConducteur, String emailConducteur, String genreConducteur, String dateConducteur, String permi) {
        super(idConducteur, nomConducteur, prenomConducteur, emailConducteur, genreConducteur, dateConducteur);
        this.checkBox = checkBox;
        this.permi = permi;
    }

    public ConducteurModule() {
    }

    public JFXCheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(JFXCheckBox checkBox) {
        this.checkBox = checkBox;
    }
    
}
