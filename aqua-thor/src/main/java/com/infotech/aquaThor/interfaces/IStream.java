/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.interfaces;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 *
 * @author alegerd
 */

@XmlAccessorType(XmlAccessType.FIELD)
public interface IStream {
    Integer getStartPos();

    void setStartPos(Integer startPos);

    Integer getFinishPos();

    void setFinishPos(Integer finishPos);
    
    void setSpeed(Integer speed);
    
    Integer getSpeed();
    
    String toString();
    
    
}
