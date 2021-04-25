package domain.players;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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

    private long id;
    @NotBlank
    private String password;

    private Account() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
private static Set<Long> idSet=new HashSet<>();
private String idsFileNameString = Util.generateLocation("accountsIDPool.ser");
public static long generateID(){
    long id=Util.generateID();
    while (idSet.contains(id)) {
        id=Util.generateID();
    }
    idSet.add(id);
    return id;
}
    public static class Builder {
        private long id;
        @NotNull
        @NotEmpty
        private String password;

        public Builder id(long id) {
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
