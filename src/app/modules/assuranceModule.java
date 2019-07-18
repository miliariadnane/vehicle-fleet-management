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
public class assuranceModule extends Vehicule{
    
    public String nombreJour;

    public assuranceModule(String nombreJour, String matricule, String marqueV, String typeCarb, String dateAssur, String dateAssur2, String dateVidange) {
        super(matricule, marqueV, typeCarb, dateAssur, dateAssur2, dateVidange);
        this.nombreJour = nombreJour;
    }

    public assuranceModule(String nombreJour) {
        this.nombreJour = nombreJour;
    }

    public assuranceModule() {
        
    }
}
