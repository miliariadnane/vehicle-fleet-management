/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.classes;


public class Vignette {
    
    private String libelleV;
    private String kmV;
    private String conducteurV;
    private String vehiculeV;
    private String dateV;
    private String prix;
    private String station;

    public Vignette(String libelleV, String kmV, String conducteurV, String vehiculeV, String dateV, String prix, String station) {
        this.libelleV = libelleV;
        this.kmV = kmV;
        this.conducteurV = conducteurV;
        this.vehiculeV = vehiculeV;
        this.dateV = dateV;
        this.prix = prix;
        this.station = station;
    }

    public Vignette() {
    }

    public String getLibelleV() {
        return libelleV;
    }

    public String getConducteurV() {
        return conducteurV;
    }

    public String getVehiculeV() {
        return vehiculeV;
    }

    public String getDateV() {
        return dateV;
    }

    public String getPrix() {
        return prix;
    }

    public String getStation() {
        return station;
    }

    public void setLibelleV(String libelleV) {
        this.libelleV = libelleV;
    }

    public void setConducteurV(String conducteurV) {
        this.conducteurV = conducteurV;
    }

    public void setVehiculeV(String vehiculeV) {
        this.vehiculeV = vehiculeV;
    }

    public void setDateV(String dateV) {
        this.dateV = dateV;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getKmV() {
        return kmV;
    }

    public void setKmV(String kmV) {
        this.kmV = kmV;
    }

}
