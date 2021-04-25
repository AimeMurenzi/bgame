/**
 * @Author: Aimé
 * @Date:   2021-04-13 03:02:37
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-13 03:03:08
 */
package domain;

public class PlayerToken {
    private String playerToken;
    private long lifeSpan;

    /**
     * 
     * @return {@link playerToken}
     */
    public String getPlayerToken() {
        return playerToken;
    }
/**
 * 
 * @param playerToken
 */
    public void setPlayerToken(String playerToken) {
        this.playerToken = playerToken;
    }
/**
 * 
 * @return
 */
    public long getLifeSpan() {
        return lifeSpan;
    }
/**
 * 
 * @param lifeSpan
 */
    public void setLifeSpan(long lifeSpan) {
        this.lifeSpan = lifeSpan;
    }
/**
 * 
 * @param playerToken
 * @param lifeSpan
 */
    public PlayerToken(String playerToken, long lifeSpan) {
        this.playerToken = playerToken;
        this.lifeSpan = lifeSpan;
    }

}
