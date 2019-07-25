/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.modules;

import app.classes.Vignette;

/**
 *
 * @author pc
 */
public class vignetteModule extends Vignette{

    public vignetteModule(String libelleV, String kmV, String conducteurV, String conducteurVig, String dateV, String prix, String station) {
        super(libelleV, kmV, conducteurV, conducteurVig, dateV, prix, station);
    }

    public vignetteModule() {
    }

}
