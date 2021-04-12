/**
 * @Author: Aimé
 * @Date:   2021-04-11 21:26:01
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-11 21:54:33
 */
package domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

enum CoordinateType {
    LAND("LAND"), WATER("WATER"), FOREST("FOREST"), UNKNOWN("UNKNOWN");

    public String value;

    private CoordinateType(String value) {
        this.value = value.toUpperCase();
    }
}

public class WorldCoordinate implements Serializable {
    private static final long serialVersionUID = 1L;
    private int x;
    private int y;
    private CoordinateType coordinateType;
    // @JsonIgnore
    private IPlayer player;

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

    public WorldCoordinate(int x, int y, CoordinateType coordinateType) {
        this.x = x;
        this.y = y;
        setCoordinateType(coordinateType);
    }

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

    public IPlayer getPlayer() {
        return player;
    }

    public void setPlayer(IPlayer player) {
        this.player = player;
    }
    @Override
    public String toString() {
        return String.format("Coordinate: %d:%d:%s",x,y,coordinateType);
    }

    public WorldCoordinate() {
    }
}