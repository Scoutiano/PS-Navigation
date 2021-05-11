/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gps;

/**
 *
 * @author Mohanad
 */
public class Container {
    int[] previous;
    double[] distance;
    
    public Container(int[] previous, double[] distance){
        this.previous = previous;
        this.distance = distance;
    }
}
