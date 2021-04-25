/**
 * @Author: Aimé
 * @Date:   2021-04-13 10:26:59
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-13 12:43:45
 */
package domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ext.Provider;

import domain.buildings.Capital;
import domain.world.CoordinateType;
import domain.world.WorldCoordinate; 
@Provider
public class PlayerPool implements IPlayerPool {

    private String playerPoolFileString = Util.generateLocation("playerPool.ser");
    private Map<String, IPlayer> playerPool;
    private Map<String, PlayerToken> playerTokens = new HashMap<>();
    private ManagerAccount accountManager;
    private Map<String, Map<String, IPlayer>> sessionMask;
    private final int tokeLifeSpan = 600000;// 10 min

    public PlayerPool() {
        try {
            accountManager = new ManagerAccount();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File playerPoolFile = new File(playerPoolFileString);
        if (!playerPoolFile.exists()) {
            try {
                playerPoolFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            playerPool = new HashMap<>();
        } else {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(playerPoolFileString))) {
                playerPool = (HashMap<String, IPlayer>) in.readObject();
            } catch (IOException ex) {
                ex.printStackTrace();
                playerPool = new HashMap<>();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                playerPool = new HashMap<>();
            }
        }
    }

    @Override
    public IPlayer getPlayerById(String playerID) {
        return playerPool.get(playerID);
    }

    @Override
    public Account createNewPlayer(String playerName, String playerPassword) {
        Account account = accountManager.createAccount(playerName, playerPassword);
        if (account == null) {
            throw new BadRequestException(String.format("dublicate account name[ {%s} ]", playerName));
        }
        playerPool.put(account.getId(), new Player());
        playerTokens.put(account.getId(), getNewPlayerToken());

        savePlayerPool(playerPool);
        // String playerID = Util.generateYouTubeStyleNameID();
        // IPlayer player = new Player();
        // playerPool.put(playerID, player);

        return account;
    }

    @Override
    public PlayerToken getNewPlayerToken() {
        return new PlayerToken(Util.generateYouTubeStyleNameID(), System.currentTimeMillis() + tokeLifeSpan);
    }

    @Override
    public Account login(String username, String password) {
        Account account = new Account(username);
        Account accountDB = accountManager.getAccounts().get(account);
        if (accountDB == null) {

        } else {
            if (accountDB.getPassword().equals(password)) {
                getPlayerTokens().put(accountDB.getId(), getNewPlayerToken());
                return accountDB;
            }

        }
        return null;
    }

    public Account verifyCredentials(String playerID, String playerToken) {
        Account account = accountManager.getAccountIDs().get(playerID);
        if (account != null) {
            PlayerToken token = getPlayerTokens().get(playerID);
            if (token != null) {
                if (token.getPlayerToken().equals(playerToken)) {
                    if (token.getLifeSpan() > System.currentTimeMillis()) {
                        return account;
                    }
                }
            }
        }
        return null;
    }

    private void savePlayerPool(Map<String, IPlayer> playerPool) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(playerPoolFileString))) {
            out.writeObject(playerPool);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Map<String, IPlayer> getPlayers() {
        return playerPool;
    }

    // @Override
    // public int attack(String attackerSessionID, String defenderSessionID) {
    //     System.out.println(attackerSessionID + " : " + defenderSessionID);
    //     if (attackerSessionID != null && (attackerSessionID = attackerSessionID.trim()).length() > 0
    //             && defenderSessionID != null && (defenderSessionID = defenderSessionID.trim()).length() > 0
    //             && !attackerSessionID.equals(defenderSessionID)

    //     ) {
    //         IPlayer attacker = playerPool.get(attackerSessionID);
    //         IPlayer defender = playerPool.get(defenderSessionID);
    //         if (attacker != null && defender != null) {
    //             attacker.hit(defender);
    //             return defender.getHP();
    //         }

    //     }
    //     return 0;
    // }
    // OLD idea of scrabling the the player id's
    // @Override
    // public String createNewPlayer() {
    // String playerID = generateYouTubeStyleNameID();
    // String playerIDMask = generateYouTubeStyleNameID();
    // IPlayer player = new Player();
    // playerPool.put(playerID, player);
    // Map<String,IPlayer> privatePlayerPool=new HashMap<>();

    public Map<String, PlayerToken> getPlayerTokens() {
        return playerTokens;
    }
    // for (IPlayer player2 : playerPool.values()) {
    // if (player2==player) {
    // privatePlayerPool.put(playerIDMask, player2);
    // }else
    // privatePlayerPool.put(generateYouTubeStyleNameID(), player2);
    // }
    // sessionMask.put(playerIDMask, privatePlayerPool);
    // return playerIDMask;
    // }

    @Override
    public WorldCoordinate claim(Account account, WorldCoordinate land, IWorldMap worldMap) {
        IPlayer player=getPlayerById(account.getId());
        if (land.isFree()) {
            // Capital townHall=new Capital(player.) 
        }
        if(!player.hasLand(land)){
            WorldCoordinate coordinate=worldMap.get(land);
            if(coordinate!=null && coordinate.getCoordinateType()== CoordinateType.LAND && player.claimLand(coordinate,worldMap))
             return worldMap.get(coordinate);
        }
        throw new BadRequestException(String.format("%s not claimed", land.toString()));

    }

    @Override
    public IPlayer abandon(Account account, WorldCoordinate land, IWorldMap worldMap) {
        IPlayer player=getPlayerById(account.getId());
        if(player.hasLand(land)){
            WorldCoordinate coordinate=worldMap.get(land);
            if(coordinate!=null && coordinate.getCoordinateType()== CoordinateType.LAND && player.abandonLand(coordinate,worldMap))
             return player;
        }
        throw new BadRequestException(String.format("%s not claimed", land.toString()));

    }

}