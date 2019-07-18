/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.classes;

/**
 *
 * @author pc
 */
public class reparation {
    
    private String vehiculeRepa;
    private String dateRepa;
    private String descriptionRepa;
    private float prixRepa;

    public reparation(int id, String vehiculeRepa, String dateRepa, String descriptionRepa, float prixRepa) {

        this.vehiculeRepa = vehiculeRepa;
        this.dateRepa = dateRepa;
        this.descriptionRepa = descriptionRepa;
        this.prixRepa = prixRepa;
    }
    public reparation() {
        
    }

    public String getVehiculeRepa() {
        return vehiculeRepa;
    }

    public String getDateRepa() {
        return dateRepa;
    }

    public String getDescriptionRepa() {
        return descriptionRepa;
    }

    public float getPrixRepa() {
        return prixRepa;
    }

    public void setVehiculeRepa(String vehiculeRepa) {
        this.vehiculeRepa = vehiculeRepa;
    }

    public void setDateRepa(String dateRepa) {
        this.dateRepa = dateRepa;
    }

    public void setDescriptionRepa(String descriptionRepa) {
        this.descriptionRepa = descriptionRepa;
    }

    public void setPrixRepa(float prixRepa) {
        this.prixRepa = prixRepa;
    }

    @Override
    public String toString() {
        return "reparation{" + "vehiculeRepa=" + vehiculeRepa + ", dateRepa=" + dateRepa + ", descriptionRepa=" + descriptionRepa + ", prixRepa=" + prixRepa + '}';
    }
}
