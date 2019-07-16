/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.modules;

import app.classes.mission;
import com.jfoenix.controls.JFXCheckBox;

/**
 *
 * @author pc
 */
public class missionModule extends mission {
    
    private JFXCheckBox checkBox;

    public missionModule(String libelle, String vehicule, String conducteur, String typeV, String description, String dateD, String DateF, int percentage) {
        super(libelle, vehicule, conducteur, typeV, description, dateD, DateF, percentage);
    }

    public missionModule() { 
    }

    public JFXCheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(JFXCheckBox checkBox) {
        this.checkBox = checkBox;
    }
}
