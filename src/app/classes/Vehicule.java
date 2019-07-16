/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.classes;

import java.util.ArrayList;

/**
 *
 * @author pc
 */
public class Vehicule {
    
    private String matricule;
    private String marqueV;
    private String typeCarb;
    private String dateAssur;
    private String dateVidange;
    public ArrayList typeV = new ArrayList();

    public Vehicule(String matricule, String marqueV, String typeCarb, String dateAssur, String dateVidange) {
        this.matricule = matricule;
        this.marqueV = marqueV;
        this.typeCarb = typeCarb;
        this.dateAssur = dateAssur;
        this.dateVidange = dateVidange;
    }

    public Vehicule() {
    }

    public String getMatricule() {
        return matricule;
    }

    public String getMarqueV() {
        return marqueV;
    }

    public String getTypeCarb() {
        return typeCarb;
    }

    public String getDateAssur() {
        return dateAssur;
    }

    public String getDateVidange() {
        return dateVidange;
    }

    public ArrayList getTypeV() {
        return typeV;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public void setMarqueV(String marqueV) {
        this.marqueV = marqueV;
    }

    public void setTypeCarb(String typeCarb) {
        this.typeCarb = typeCarb;
    }

    public void setDateAssur(String dateAssur) {
        this.dateAssur = dateAssur;
    }

    public void setDateVidange(String dateVidange) {
        this.dateVidange = dateVidange;
    }

    public void setTypeV(ArrayList typeV) {
        this.typeV = typeV;
    }

    @Override
    public String toString() {
        return   matricule + "  " + marqueV ;
    }

     
}
