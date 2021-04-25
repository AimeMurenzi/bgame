/**
 * @Author: Aimé
 * @Date:   2021-04-12 05:06:03
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-13 11:44:39
 */
package domain;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
/**
 * why am i not using the proper jax-rx mechanisms to handle log in.
 * 1. this is a rapid fire thing. no time to setup the config files for the app and the server
 * 2. i want more control over sessions and login mechanisms
 * 3. i dont have enough experience to bend the session and login headers my will to replace what 
 *    what i'm trying to achieve here.
 */
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String password; 
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
        this.name = name.toLowerCase().trim();
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
        Account other = (Account) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
/**
 * 
 * @param id
 * @param name
 * @param passwordString
 */
    public Account(String id, String name, String passwordString) {
        this.id = id;
        setName(name);
        setPassword(passwordString);
    }
/**
 * 
 * @param name
 * @param passwordString
 */
    public Account(String name,String passwordString) {
        setName(name);
        setPassword(passwordString);
        String id=ManagerIDs.saveId(Util.generateYouTubeStyleNameID());
        while (id==null) {
            id=ManagerIDs.saveId(Util.generateYouTubeStyleNameID());
        }
        setId(id);
    }
    public Account(String name){
        setName(name);
    }

    public String getPassword() {
        return password;
    }

    public final void setPassword(String passwordString) {
        this.password = passwordString;
    }

    public Account() {
    }

}
