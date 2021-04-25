package domain.players;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import domain.IWorldMap;
import domain.Util;
import domain.buildings.Capital;
import domain.world.WorldCoordinate;

public class Player2 implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    private int maxLandClaims = 5;
    private final List<Capital> capitals = new ArrayList<>();

    private long id;
    @NotEmpty
    @NotNull
    private String name; 
    private Player2() {
    }   

    private Player2(long id, @NotEmpty @NotNull String name) {
        this.id = id;
        setName(name);
    }



    public int getMaxLandClaims() {
        return maxLandClaims;
    }

    public List<Capital> getCapitals() {
        return new ArrayList<>(capitals);
    }

    public void addCapital(Capital capital) {
        capitals.add(capital);
    }

    public void setMaxLandClaims(int maxLandClaims) {
        this.maxLandClaims = maxLandClaims;
    }

    public boolean claimLand(WorldCoordinate coordinate, IWorldMap worldMap) {
        // if (claimedLands.size() < getMaxLandClaims()) {
        // if (worldMap.setOwner(this, coordinate))
        // return claimedLands.add(worldMap.get(coordinate));
        // }
        return false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public final void setName(String name) { 
        if (name!=null && !name.isBlank()) {
            this.name=name.trim().toLowerCase();        
            return;
        }
       Util.throwBadRequest("name is blank");
    }

   

    public boolean abandonLand(WorldCoordinate coordinate, IWorldMap worldMap) {
        // if (worldMap.removeOwner(this, coordinate))
        // return claimedLands.remove(coordinate);
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
        Player2 other = (Player2) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    public static class Builder {
        private long id;
        @NotEmpty
        @NotNull
        private String name;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Player2 build(){

            return new Player2(id, name);
        }
    }

}
