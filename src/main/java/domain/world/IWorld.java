package domain.world;

import java.util.Map;

import domain.buildings.Building;
import domain.players.Player2;

public interface IWorld {
    Map<Long, Building> getWorldBuildings(); 
}
