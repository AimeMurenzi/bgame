/**
 * @Author: Aimé
 * @Date:   2021-04-07 04:15:19
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-07 04:15:41
 */
package domain;

public enum ResourceType {
    UNKNOWN(0),
    BUILDMAT1(1),
    BUILDMAT2(2),
    BUILDMAT3(3),
    MATPRODUCER(4),
    CAPITAL(5),
    STORAGE(6)
    ;

    private int type;
    ResourceType(int type){
        this.type=type;
    }
    public int getType() {
        return type;
    }
    
}