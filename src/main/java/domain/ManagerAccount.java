/**
 * @Author: Aimé
 * @Date:   2021-04-13 10:29:08
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-13 10:29:31
 */
package domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ManagerAccount {
    private Map<Account, Account> accounts = null;
    private Map<String, Account> accountIDs = null;
    private String accountsSerialNameString = Util.generateLocation("accounts.ser");

    public ManagerAccount() throws IOException {
        File accountsFile = new File(accountsSerialNameString);
        if (!accountsFile.exists()) {
            accountsFile.createNewFile();
            accounts = new HashMap<>();
            accountIDs = new HashMap<>();

        } else {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(accountsSerialNameString))) {
                accounts = (HashMap<Account, Account>) in.readObject();
                accountIDs = new HashMap<>();
                accounts.values().stream().forEach(acc -> {
                    accountIDs.put(acc.getId(), acc);
                });
            } catch (IOException ex) {
                ex.printStackTrace();
                accounts = new HashMap<>();
                accountIDs = new HashMap<>();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                accountIDs = new HashMap<>();
                accountIDs = new HashMap<>();
            }
        }
    }

    /**
     * creates new Account if account name already exists it returns null
     * 
     * @param nameString
     * @param passwordString
     * @return {@link Account } if account name already exits it returns null
     */
    public Account createAccount(String nameString, String passwordString) {
        Account account = new Account(nameString, passwordString);
        if (accounts.containsKey(account))
            return null;
        accounts.put(account, account);
        accountIDs.put(account.getId(), account);
        saveAccounts(accounts);
        return account;
    }

    private void saveAccounts(Map<Account, Account> accounts2) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(accountsSerialNameString))) {
            out.writeObject(accounts2);
            String userDirectory = System.getProperty("user.dir");
            System.out.println("Success saveAccounts");
            System.out.println("current dir: " + userDirectory);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Map<Account, Account> getAccounts() {
        return accounts;
    }

    public Map<String, Account> getAccountIDs() {
        return accountIDs;
    }

}