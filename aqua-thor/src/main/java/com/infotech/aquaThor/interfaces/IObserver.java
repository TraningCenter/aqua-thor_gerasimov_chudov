/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.interfaces;

import com.infotech.aquaThor.model.utils.Cell;
import com.infotech.aquaThor.model.utils.CellContent;
import com.infotech.aquaThor.model.utils.Tuple;

/**
 *
 * @author alegerd
 */
public interface IObserver {
    
    public void fishEatten(Cell coord);
    
    public void foodEatten(Cell coord);
    
    public void fishCreated(Cell coord, CellContent type, IFish newFish);
}
