/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.model.utils;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 *
 * @author alegerd
 */

@XmlEnum
public enum Orientation {
    
    @XmlEnumValue("vertical")
    VERTICAL,
    
    @XmlEnumValue("horizontal")
    HORIZONTAL
}
