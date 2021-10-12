/**
 * @Author: Aimé
 * @Date:   2021-04-24 11:38:30
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-07-21 20:55:07
 */
package domain.players;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import domain.Util;

/**
 * why am i not using the proper jax-rx mechanisms to handle log in. 1. this is
 * a rapid fire thing. no time to setup the config files for the app and the
 * server 2. i want more control over sessions and login mechanisms 3. i dont
 * have enough experience to bend the session and login headers my will to
 * replace what what i'm trying to achieve here.
 */
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id; 
    @NotBlank
    private String password;

    private Account() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public final void setPassword(String password) {
        if (password != null && !password.isBlank()) {
            // TODO: add password policy
            this.password = password;
            return;
        }
        Util.throwBadRequest("password is blank");
    }

    public static class Builder {
        private String id;
        @NotNull
        @NotEmpty
        private String password;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Account build() {
            Account account = new Account();
            account.id = this.id;
            account.password = this.password;
            return account;
        }
    }
}
