/**
 * @Author: Aimé
 * @Date:   2021-04-24 18:44:34
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-30 08:50:39
 */
package domain.players;

import javax.validation.constraints.NotBlank; 

public class NewAccountHelper {
    @NotBlank
    private String name;
    @NotBlank
    private String password;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
}
