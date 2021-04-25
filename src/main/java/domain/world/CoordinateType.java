/**
 * @Author: Aimé
 * @Date:   2021-04-13 12:40:25
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-13 12:43:18
 */
package domain.world;

public enum CoordinateType {
    LAND("LAND"), WATER("WATER"), FOREST("FOREST"), UNKNOWN("UNKNOWN");

    public String value;

    private CoordinateType(String value) {
        this.value = value.toUpperCase();
    }
}