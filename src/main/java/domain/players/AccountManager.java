/**
 * @Author: Aimé
 * @Date:   2021-04-24 11:37:06
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-30 08:50:24
 */
package domain.players;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import de.rtner.security.auth.spi.SimplePBKDF2;
import domain.BGameSettings;
import domain.Util;
import domain.world.World; 

public class AccountManager  implements Serializable{ 
    private static final long serialVersionUID = 1L;
    private String ACCOUNTS_SERIAL_NAME_STRING = Util.generateLocation("accounts.ser");

    private Map<Long, Account> accountIDs = new HashMap<>();
   

    private AccountManager() {
        File accountsFile = new File(ACCOUNTS_SERIAL_NAME_STRING);
        if (!accountsFile.exists()) {
            try {
                accountsFile.createNewFile();
                accountIDs = new HashMap<>(); 
            } catch (IOException e) { 
                e.printStackTrace();
            } 
            

        } else {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ACCOUNTS_SERIAL_NAME_STRING))) {
                AccountManager accountManager=(AccountManager) in.readObject();
                 
                accountIDs.putAll(accountManager.accountIDs);                
            } catch (IOException ex) {
                ex.printStackTrace(); 
                accountIDs = new HashMap<>();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                accountIDs = new HashMap<>();
                accountIDs = new HashMap<>();
            }
        }
    }
/**
 * 
 * @param nameString
 * @param passwordString
 */
    public static void createAccount(String nameString, String passwordString) {
        if (nameString!=null && passwordString!=null
        && !nameString.isBlank() && !passwordString.isBlank()
        ) {
            //check player if exist
            final Map<Player, Player> players =World.getPlayers();
            Player player=players.get(new Player.Builder().name(nameString).build());
            if(player==null){
                String saltedPassword= new SimplePBKDF2(8,100000).deriveKeyFormatted(passwordString);
                Account account=new Account.Builder().id(getInstance().generateAccountID()).password(saltedPassword).build();
                player=new Player.Builder().id(account.getId()).name(nameString).build();
                getInstance().accountIDs.put(account.getId(), account);
                World.addPlayer(player); 
                getInstance().saveAccounts();
                return;              
            }            
            //create account check id collision
            //make PBKDF2 password
            //set player id to account id
            // add player to world
            // add account to map 

            
        }
        Util.throwBadRequest("name | password");  
    }
    /**
     * generates a guaranteed non existing account id
     * @return generated account id
     */
    public  long generateAccountID(){ 
        long id=Util.generateID();
        while (accountIDs.containsKey(id)) {
            id=Util.generateID();
        }  
        return id;
    }
    private String accountsSerialNameString = Util.generateLocation("accounts.ser");
    private void saveAccounts() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(accountsSerialNameString))) {
            out.writeObject(this); 
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static Map<Long, Account> getAccountIDs() {
        return new HashMap<>(getInstance().accountIDs);
    }
    private static class HoldInstance {
        private static final AccountManager INSTANCE = new AccountManager();
    }

    public static AccountManager getInstance() {
        return HoldInstance.INSTANCE;
    }
  
    public static String login(String username, String password) {
        if (username!=null && password!=null
        && !password.isBlank() && !password.isBlank()
        ) { 
            final Map<Player, Player> players =World.getPlayers();
            Player player=players.get(new Player.Builder().name(username).build());
            if(player!=null){ 
                Account account=getAccountIDs().get(player.getId());
                boolean verified= new SimplePBKDF2().verifyKeyFormatted(account.getPassword(), password);
                if (verified) {
                    try {
                        final String SECRET_KEY=BGameSettings.getJWTKey();
                        final int SECRET_KEY_TTL=BGameSettings.getJWTKeyTTL();
                        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY); 
                        String token = JWT.create()
                            .withIssuer("browser game")
                            .withClaim("name", player.getName())
                            .withIssuedAt(new Date(System.currentTimeMillis()))
                            .withExpiresAt(new Date(System.currentTimeMillis()+SECRET_KEY_TTL))
                            .sign(algorithm);
                            return token;
                    } catch (JWTCreationException jwtCreationException){
                        //Invalid Signing configuration / Couldn't convert Claims.
                        //TODO return proper response
                        jwtCreationException.printStackTrace();
                    }  
                }           
            }  
        }
        Util.throwBadRequest("name | password");

        return null;
    }
}
