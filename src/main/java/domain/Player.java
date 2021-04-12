/**
 * @Author: Aimé
 * @Date:   2021-04-11 03:53:50
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-11 21:52:55
 */
package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonIgnore;

// import org.springframework.stereotype.Service;

@Provider
public class Player implements IPlayer, Serializable {
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    private int maxLandClaims = 5;
    // @JsonIgnore
    private List<WorldCoordinate> claimedLands = new ArrayList<>();
    @NotEmpty
    @NotNull
    private String name;

    public Player() {
    }

    public boolean hasLand(WorldCoordinate land) {
        for (WorldCoordinate worldCoordinate : claimedLands) {
            if (worldCoordinate.equals(land)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public List<WorldCoordinate> getClaimedLands() {
        // if (claimedLands!=null) {
        return new ArrayList<>(claimedLands);
        // }
        // return new ArrayList<>();
    }

    public int getMaxLandClaims() {
        return maxLandClaims;
    }

    public void setMaxLandClaims(int maxLandClaims) {
        this.maxLandClaims = maxLandClaims;
    }

    @Override
    public boolean claimLand(WorldCoordinate coordinate, IWorldMap worldMap) {
        if (claimedLands.size() < getMaxLandClaims()) {
            if (worldMap.setOwner(this, coordinate))
                return claimedLands.add(worldMap.get(coordinate));
        }
        return false;
    }

    @Override
    public boolean abandonLand(WorldCoordinate coordinate, IWorldMap worldMap) {
        if (worldMap.removeOwner(this, coordinate))
            return claimedLands.remove(coordinate);
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Player other = (Player) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}