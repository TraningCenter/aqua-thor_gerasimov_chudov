/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infotech.aquaThor.interfaces;

import com.infotech.aquaThor.model.Model;


/**
 *
 * @author alegerd
 */
public interface IParser {
    Model parse() throws Exception;
}
