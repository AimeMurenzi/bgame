/**
 * @Author: Aimé
 * @Date:   2021-04-12 05:07:28
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-12 05:09:34
 */
package domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

public class ManagerIDs {
    private Set<String> ids = null;
    private String idsFileNameString = Util.generateLocation("ids.ser");
    private static class HoldInstance { 
        private static final ManagerIDs INSTANCE = new ManagerIDs();
    }

    public static ManagerIDs getInstance() {
        return HoldInstance.INSTANCE;
    }
    private  ManagerIDs() {
        File accountsFile=new File(idsFileNameString);
        if (!accountsFile.exists()) {
            try {
                accountsFile.createNewFile();
            } catch (IOException e) { 
                e.printStackTrace();
            }
            ids=new HashSet<>();
        }else{
            try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(idsFileNameString)))
            {      
                ids = (HashSet<String>)in.readObject();  
            }  
            catch(IOException ex) 
            {  
                ex.printStackTrace();
                ids=new HashSet<>();
            }  
            catch(ClassNotFoundException ex) 
            { 
                ex.printStackTrace();
                ids=new HashSet<>();
            } 
        }
    }

    private boolean collisionCheck(String id) {
        return ids.contains(id);
    }

    /**
     * returns null if there is a collision with existing IDs
     * 
     * @param id
     * @return
     */
    public static String saveId(String id) {
        if (!getInstance().collisionCheck(id)) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(getInstance().idsFileNameString))) {
                getInstance().ids.add(id);
                out.writeObject(getInstance().ids);
            } 
            catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        }else{
            return null;
        }
        return id;
    }
}