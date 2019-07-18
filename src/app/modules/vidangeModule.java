/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.modules;

import app.classes.Vehicule;

/**
 *
 * @author pc
 */
public class vidangeModule extends Vehicule {
    
    public String nombreJour2;

    public vidangeModule(String nombreJour2, String matricule, String marqueV, String typeCarb, String dateAssur, String dateAssur2, String dateVidange) {
        super(matricule, marqueV, typeCarb, dateAssur, dateAssur2, dateVidange);
        this.nombreJour2 = nombreJour2;
    }

    public vidangeModule(String nombreJour2) {
        this.nombreJour2 = nombreJour2;
    }
    
    public vidangeModule(){
        
    }

}
