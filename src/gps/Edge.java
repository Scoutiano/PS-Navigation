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
public class Edge implements Comparable<Edge>{
    City city;
    double distance;
    
    public Edge(){
        
    }
    
    public Edge(City city, double distance){
        this.city = city;
        this.distance = distance;
    }
    
    @Override
    public String toString(){
        return "[" + city + " " +distance + "]";
    }

    @Override
    public int compareTo(Edge o) {
        
        Double thisDistance = new Double(this.distance);
        Double otherDistance = new Double(o.distance);
        
        if(thisDistance.compareTo(otherDistance) > 0){
            return 1;
        }
        return 0;
    }
}
