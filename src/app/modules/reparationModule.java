/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.modules;

import app.classes.Vehicule;
import app.classes.reparation;
import com.jfoenix.controls.JFXCheckBox;

/**
 *
 * @author pc
 */
public class reparationModule extends reparation {

    private JFXCheckBox checkBox;
    
    public reparationModule( String vehiculeRepa, String dateRepa, String descriptionRepa, String prixRepa) {
        super( vehiculeRepa, dateRepa, descriptionRepa, prixRepa);
    }

    public reparationModule() {
        
    }

    public JFXCheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(JFXCheckBox checkBox) {
        this.checkBox = checkBox;
    }
    
}
