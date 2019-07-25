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
public class Type {
    
    private String typeV;

    public Type(String typeV) {
        this.typeV = typeV;
    }

    public String getTypeV() {
        return typeV;
    }

    public void setTypeV(String typeV) {
        this.typeV = typeV;
    }

    @Override
    public String toString() {
        return "Type{" + "typeV=" + typeV + '}';
    }
}
