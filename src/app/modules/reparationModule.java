/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.modules;

import app.classes.Vehicule;
import app.classes.reparation;

/**
 *
 * @author pc
 */
public class reparationModule extends reparation {

    public reparationModule(int id, String vehiculeRepa, String dateRepa, String descriptionRepa, float prixRepa) {
        super(id, vehiculeRepa, dateRepa, descriptionRepa, prixRepa);
    }

    public reparationModule() {
        
    }
    
}
