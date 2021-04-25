/**
 * @Author: Aimé
 * @Date:   2021-04-12 12:07:22
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-13 12:06:47
 */
package domain;

import java.util.Comparator;

import domain.world.WorldCoordinate;
/**
 * compares both x and y world coordinates on X first then Y
 */
public class ComperatorCoordinates implements Comparator<WorldCoordinate> {

    @Override
    public int compare(WorldCoordinate worldCoordinateOriginal, WorldCoordinate worldCoordinateToCompareTo) { 
        int originalX=worldCoordinateOriginal.getX();
        int originalY=worldCoordinateOriginal.getY();
        int toCompareX=worldCoordinateToCompareTo.getX();
        int toCompareY=worldCoordinateToCompareTo.getY(); 
        
        int result=originalY-toCompareY; 
        if(result!=0){
            return result; 
        }else{
            result=originalX-toCompareX;
            if (result!=0) {
                return result;
            }else{
                return result;
            }
        } 
    } 
}
