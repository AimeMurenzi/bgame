/**
 * @Author: Aimé
 * @Date:   2021-04-11 21:26:01
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-30 08:52:00
 */
package domain.world;

import java.io.Serializable;

import domain.buildings.Capital;

public class WorldCoordinate implements Serializable { 
    private static final long serialVersionUID = 1L;
    private int x;
    private int y;
    private CoordinateType coordinateType; 
    private Capital capital;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    private WorldCoordinate(){} 
    public CoordinateType getCoordinateType() {
        return coordinateType;
    }

    public final void setCoordinateType(String coordinateType) {
        for (CoordinateType cType : CoordinateType.values()) {
            if (cType.value == coordinateType) {
                this.coordinateType = cType;
                return;
            }
        }
        this.coordinateType = CoordinateType.UNKNOWN;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }
/**
 * 
 * equal if x==x and y==y
 */  
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WorldCoordinate other = (WorldCoordinate) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    public void setCoordinateType(CoordinateType coordinateType) {
        this.coordinateType = coordinateType;
    }
 
    @Override
    public String toString() {
        return String.format("Coordinate: %d:%d:%s",x,y,coordinateType);
    } 

    public Capital getCapital() {
        return capital;
    }

    public void setCapital(Capital capital) {
        this.capital = capital;
    }

    /**
     * when capital is null then the land is free
     * @return true if capital is null 
     */
    public boolean isFree() {
        return getCapital()==null;
    }
public static class Builder{
    private int x;
    private int y;
    private CoordinateType coordinateType; 
    private Capital capital;
    public Builder x(int x){
        this.x=x;
        return this;
    }
    public Builder y(int y){
        this.y=y;
        return this;
    }
    public Builder coordinateType(CoordinateType coordinateType){
        this.coordinateType=coordinateType;
        return this;
    }
    public Builder capital(Capital capital){
        this.capital=capital;
        return this;
    }
    public WorldCoordinate build(){
        WorldCoordinate worldCoordinate=new WorldCoordinate();
        worldCoordinate.x=this.x;
        worldCoordinate.y=this.y;
        worldCoordinate.coordinateType=coordinateType==null?CoordinateType.UNKNOWN:this.coordinateType;
        worldCoordinate.capital=this.capital;
        return worldCoordinate;
    } 
}
}