import java.io.*;

/*
   parametri di configurazione per le info sull'user
*/

public class ParametriUser {
    
    private String nickname;

    public ParametriUser(String nick) {
        this.nickname = nick;
    }

    public String getNickname(){
        return nickname;
    }
}