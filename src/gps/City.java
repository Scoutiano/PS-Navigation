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
public class City {
    
    String cityName;
    double x;
    double y;
    int cityID;
    
    //isXnY checks if the entered longitude and latitude variable needs to be transformed or not
    //true value indicates that the entered value is already X and Y
    //false value indicates that the entered value is to be transferred to X and Y
    public City(String cityName,int cityID,double longitude,double latitude,boolean isXnY){
        this.cityName = cityName;
        
        if(isXnY){
            this.x = longitude;
            this.y = latitude;
        } else {
            this.x = longToX(longitude);
            this.y = latToY(latitude);
        }
        this.cityID = cityID;
    }
    
    public double longToX(double longitude) {
        double x = (Math.abs(longitude - 34.193862) * 369.216394715) * 1.037;
        System.out.println(cityName + " X: " + x);
        return x;
    }

    public double latToY(double latitude) {
        double y = (Math.abs(latitude - 31.219080) * 429.781639092) * -1.04956521739;
        System.out.println(cityName + " Y: " + y);
        return y;
    }
    
    @Override
    public String toString(){
        return cityName;
    }
}