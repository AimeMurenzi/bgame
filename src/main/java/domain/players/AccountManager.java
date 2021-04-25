package domain.players;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import de.rtner.security.auth.spi.SimplePBKDF2;
import domain.Util;
import domain.world.World;

public class AccountManager {
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
            final Map<Player2, Player2> players =World.getPlayers();
            Player2 player=players.get(new Player2.Builder().name(nameString).build());
            if(player==null){
                String saltedPassword= new SimplePBKDF2(8,100000).deriveKeyFormatted(passwordString);
                Account account=new Account.Builder().id(Account.generateID()).password(saltedPassword).build();
                player=new Player2.Builder().id(account.getId()).name(nameString).build();
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
}
