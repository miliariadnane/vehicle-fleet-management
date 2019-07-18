
package app.classes;

import java.util.ArrayList;

public class Conducteur {
    
    private String idConducteur;
    private String nomConducteur;
    private String prenomConducteur;
    private String emailConducteur;
    private String genreConducteur;
    private String dateConducteur;
    public ArrayList typeP = new ArrayList();

    public Conducteur(String idConducteur, String nomConducteur, String prenomConducteur, String emailConducteur, String genreConducteur, String dateConducteur) {
        this.idConducteur = idConducteur;
        this.nomConducteur = nomConducteur;
        this.prenomConducteur = prenomConducteur;
        this.emailConducteur = emailConducteur;
        this.genreConducteur = genreConducteur;
        this.dateConducteur = dateConducteur;
    }

    public Conducteur() {
    }

    public Conducteur(String nom, String prenom) {
        this.nomConducteur = nomConducteur;
        this.prenomConducteur = prenomConducteur;
    }

    public String getIdConducteur() {
        return idConducteur;
    }

    public String getNomConducteur() {
        return nomConducteur;
    }

    public String getPrenomConducteur() {
        return prenomConducteur;
    }

    public String getEmailConducteur() {
        return emailConducteur;
    }

    public String getGenreConducteur() {
        return genreConducteur;
    }

    public String getDateConducteur() {
        return dateConducteur;
    }

    public ArrayList getTypeP() {
        return typeP;
    }

    public void setIdConducteur(String idConducteur) {
        this.idConducteur = idConducteur;
    }

    public void setNomConducteur(String nomConducteur) {
        this.nomConducteur = nomConducteur;
    }

    public void setPrenomConducteur(String prenomConducteur) {
        this.prenomConducteur = prenomConducteur;
    }

    public void setEmailConducteur(String emailConducteur) {
        this.emailConducteur = emailConducteur;
    }

    public void setGenreConducteur(String genreConducteur) {
        this.genreConducteur = genreConducteur;
    }

    public void setDateConducteur(String dateConducteur) {
        this.dateConducteur = dateConducteur;
    }

    public void setTypeP(ArrayList typeP) {
        this.typeP = typeP;
    }

    @Override
    public String toString() {
        return idConducteur + " | " + nomConducteur;
    }

}
