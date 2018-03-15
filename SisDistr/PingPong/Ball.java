/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufp.sd.pingpong.server;

import java.io.Serializable;



/**
 *
 * @author bernardomaia
 */
public class Ball implements Serializable {
    int id;

    public Ball(int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
}
