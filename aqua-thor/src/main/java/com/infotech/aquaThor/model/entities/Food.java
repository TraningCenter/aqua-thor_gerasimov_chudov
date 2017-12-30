/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.model.entities;

import com.infotech.aquaThor.interfaces.IFood;
import com.infotech.aquaThor.model.utils.Element;
import com.infotech.aquaThor.model.utils.Tuple;

/**
 *
 * @author alegerd
 */
public class Food extends Element implements IFood{

    public Tuple getCoordinates() {
        return new Tuple(getXCoord(), getYCoord());
    }
    
}
