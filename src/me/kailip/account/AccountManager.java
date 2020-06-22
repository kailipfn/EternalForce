package me.kailip.account;

import me.kailip.utils.Config;
import twitter4j.TwitterException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccountManager {
    public static List<Account> list = new ArrayList<>();
    private static Config accConfig = new Config("account");
    public static Account addAccount(String token,String secret,AccType type) {
        try {
            Account a = new Account(token,secret,"",type);
            String id = a.getTwitter().verifyCredentials().getScreenName();
            Account account = new Account(token,secret,id,type);
            list.add(account);
            accConfig.setString(token,secret + "--" + type.toString().toLowerCase() + "--" + account.getId());
            return account;
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return new Account("","","",null);
    }

    public static Account removeAccount(String token) {
        for(Account account : getAccountList()) {
            list.remove(account);
            accConfig.setString(token, "deleted");
            return account;
        }
        return new Account("","","",null);
    }

    public static String getSecretFromToken(String token) {
        for(String str : getList()) {
            if(str.contains(token)) {
                String[] buri = str.split("=",2);
                String[] kiji = buri[1].split("--",3);
                return kiji[0];
            }
        }
        return "";
    }

    public static AccType getAccountTypeFromToken(String token) {
        for(String str : getList()) {
            if(str.contains(token)) {
                String[] buri = str.split("=",2);
                String[] kiji = buri[1].split("--",3);
                return AccType.valueOf(kiji[1].toUpperCase());
            }
        }
        return null;
    }

    public static String getIdFromToken(String token) {
        for(String str : getList()) {
            if(str.contains(token)) {
                String[] buri = str.split("=",2);
                String[] kiji = buri[1].split("--",3);
                return kiji[2];
            }
        }
        return "";
    }

    public static String getTokenFromId(String id) {
        for(String str : getList()) {
            if(str.contains(id)) {
                String[] buri = str.split("=",2);
                return buri[0];
            }
        }
        return "";
    }

    public static String getSecretFromId(String id) {
        for(String str : getList()) {
            if(str.contains(id)) {
                String[] buri = str.split("=",2);
                String[] kiji = buri[1].split("--",3);
                return kiji[0];
            }
        }
        return "";
    }

    public static AccType getAccountTypeFromId(String id) {
        for(String str : getList()) {
            if(str.contains(id)) {
                String[] buri = str.split("=",2);
                String[] kiji = buri[1].split("--",3);
                return AccType.valueOf(kiji[1].toUpperCase());
            }
        }
        return null;
    }

    public static void init() {
        for(String str : getList()) {
            String[] buri = str.split("=",2);
            String[] kiji = buri[1].split("--",3);
            list.add(new Account(buri[0],kiji[0],kiji[2],AccType.valueOf(kiji[1].toUpperCase())));
        }
    }

    public static List<String> getList() {
        List<String> list = new ArrayList<>();
        try {

            BufferedReader br = new BufferedReader(new FileReader(accConfig.getConfigFile()));

            String line;

            while ((line = br.readLine()) != null) {
                if (!line.contains("#")) {
                    if (!line.contains("deleted")) {
                        list.add(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> getIdList() {
        List<String> list = new ArrayList<>();
        try {

            BufferedReader br = new BufferedReader(new FileReader(accConfig.getConfigFile()));

            String line;

            while ((line = br.readLine()) != null) {
                if (!line.contains("#")) {
                    if (!line.contains("deleted")) {
                        String[] buri = line.split("=", 2);
                        String[] kiji = buri[1].split("--", 3);
                        list.add(kiji[2]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Account> getAccountList() {
        return list;
    }
}
