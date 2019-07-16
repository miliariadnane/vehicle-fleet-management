/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.modules;

import app.classes.Vehicule;
import com.jfoenix.controls.JFXCheckBox;

public class vehiculeModule extends Vehicule{
    
    private JFXCheckBox checkBox;
    private String typeVEH;

    public vehiculeModule(String matricule, String marqueV, String typeCarb, String dateAssur, String dateVidange) {
        super(matricule, marqueV, typeCarb, dateAssur, dateVidange);
        this.checkBox = checkBox;
        this.typeVEH = typeVEH;
        
    }

    public vehiculeModule() {
    }

    public JFXCheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(JFXCheckBox checkBox) {
        this.checkBox = checkBox;
    }
}
