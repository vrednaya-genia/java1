package app;

import app.model.Account;

import java.io.FileWriter;
import java.util.*;

public class Store {
    private static final Boolean sync = true;
    private static Map<String, Account> store = new HashMap<>();
    private static final String storeFile = "D:\\store.csv";

    static {
        for (int i = 0; i < 10 ; i++) {
            Account acc = new Account();
            String id = UUID.randomUUID().toString();
            acc.setId(id);
            acc.setPin(1000+i);
            acc.setHolder("Account_"+i);
            acc.setDate(new Date(System.currentTimeMillis()+365*24*3600*1000));
            acc.setAmount(Math.random()*1_000_000);
            store.put(acc.getId(), acc);
        }
        try (FileWriter writer = new FileWriter(getStoreFile())) {
            for (Account acc : store.values()) {
                writer.write(acc.toString() + "\n");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Map<String, Account> getStore(){
        return store;
    }

    public static String getStoreFile() {
        return storeFile;
    }

    public static Boolean getSync() {
        return sync;
    }
}
