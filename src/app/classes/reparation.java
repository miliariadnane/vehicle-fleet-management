
package app.classes;

public class reparation {
    
    private String vehiculeRepa;
    private String dateRepa;
    private String descriptionRepa;
    private String prixRepa;

    public reparation( String vehiculeRepa, String dateRepa, String descriptionRepa, String prixRepa) {

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

    public String getPrixRepa() {
        return prixRepa;
    }

    public void setPrixRepa(String prixRepa) {
        this.prixRepa = prixRepa;
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

    

    @Override
    public String toString() {
        return "reparation{" + "vehiculeRepa=" + vehiculeRepa + ", dateRepa=" + dateRepa + ", descriptionRepa=" + descriptionRepa + ", prixRepa=" + prixRepa + '}';
    }
}
