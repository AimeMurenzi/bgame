/**
 * @Author: Aimé
 * @Date:   2021-04-16 05:43:21
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-07-21 20:50:49
 */
package domain.players;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import domain.Util;

public class Player implements Serializable { 
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    private int maxLandClaims = 5;

    private String id;
    @NotBlank 
    private String name;

    private Player() {}

    private Player(String id, @NotBlank String name) {
        this.id = id;
        setName(name);
    }

    public int getMaxLandClaims() {
        return maxLandClaims;
    }

    public void setMaxLandClaims(int maxLandClaims) {
        this.maxLandClaims = maxLandClaims;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public final void setName(String name) {
        if (name != null && !name.isBlank()) {
            this.name = name.trim().toLowerCase();
            return;
        }
        Util.throwBadRequest("name is blank");
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

    public static class Builder {
        private String id;
        @NotBlank
        private String name;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Player build() {

            return new Player(id, name);
        }
    }

}
