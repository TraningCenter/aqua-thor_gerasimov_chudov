/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.view.parsers;

import com.infotech.aquaThor.interfaces.IField;
import com.infotech.aquaThor.model.Field;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class FieldAdapter extends XmlAdapter<FieldAdapter.AdapterField, IField>{
    
    public static class AdapterField{
        
        @XmlAttribute(name="width")
        public int width;
        
        @XmlAttribute(name="height")
        public int height;
        
        @XmlElement(name="closed")
        public boolean closed;
    }
    
    @Override
    public IField unmarshal(AdapterField v) throws Exception {
        IField field = new Field(v.width, v.height, v.closed);
        return field;
    }

    @Override
    public AdapterField marshal(IField v) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
