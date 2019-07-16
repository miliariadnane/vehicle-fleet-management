/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.classes;

public class mission {
    
    private String libelle;
    private String vehicule;
    private String conducteur;
    private String typeV;
    private String description;
    private String dateD;
    private String DateF;
    private int percentage;

    public mission(String libelle, String vehicule, String conducteur, String typeV, String description, String dateD, String DateF, int percentage) {
        this.libelle = libelle;
        this.vehicule = vehicule;
        this.conducteur = conducteur;
        this.typeV = typeV;
        this.description = description;
        this.dateD = dateD;
        this.DateF = DateF;
        this.percentage = percentage;
    }

    public mission() {
    }

    public String getLibelle() {
        return libelle;
    }

    public String getVehicule() {
        return vehicule;
    }

    public String getConducteur() {
        return conducteur;
    }

    public String getTypeV() {
        return typeV;
    }

    public String getDescription() {
        return description;
    }

    public String getDateD() {
        return dateD;
    }

    public String getDateF() {
        return DateF;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setVehicule(String vehicule) {
        this.vehicule = vehicule;
    }

    public void setConducteur(String conducteur) {
        this.conducteur = conducteur;
    }

    public void setTypeV(String typeV) {
        this.typeV = typeV;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateD(String dateD) {
        this.dateD = dateD;
    }

    public void setDateF(String DateF) {
        this.DateF = DateF;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "mission{" + "libelle=" + libelle + ", vehicule=" + vehicule + ", conducteur=" + conducteur + ", typeV=" + typeV + ", description=" + description + ", dateD=" + dateD + ", DateF=" + DateF + ", percentage=" + percentage + '}';
    }
}
