package app.service.impl;

import app.Store;
import app.model.Account;
import app.service.StoreService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileStoreService implements StoreService {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private Collection<Account> readFile() {
        Collection<Account> accounts = new ArrayList<>();
        lock.readLock().lock();
        try {
            File f = new File(Store.getStoreFile());
            try (Scanner scan = new Scanner(f)) {
                while (scan.hasNextLine()) {
                    String line = scan.nextLine();
                    try {
                        Account account = Account.parseAccount(line);
                        accounts.add(account);
                    } catch (Exception ignored) { }
                }
            } catch (IOException e) {
                throw new RuntimeException("Can't read file:" + Store.getStoreFile());
            }
        } finally {
            lock.readLock().unlock();
        }
        return accounts;
    }

    private void writeFile(Collection<Account> accounts) {
        lock.writeLock().lock();
        try {
            try (FileWriter writer = new FileWriter(Store.getStoreFile())) {
                for (Account account : accounts) {
                    writer.write(account.toString() + "\n");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public Account get(String id) {
        Collection<Account> accounts = readFile();
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return account;
            }
        }
        throw new RuntimeException("Account not found by id:" + id);
    }

    @Override
    public Collection<Account> get() {
        Collection<Account> accounts = readFile();
        if (accounts.isEmpty()) {
            throw new RuntimeException("Store is empty");
        }
        return accounts;
    }

    @Override
    public void delete(String id) {
        Collection<Account> accounts = readFile();
        boolean removed = accounts.removeIf(account -> account.getId().equals(id));
        if (!removed) {
            throw new RuntimeException("Account not found by id:" + id);
        }
        writeFile(accounts);
    }

    @Override
    public void insert(Account account) {
        Collection<Account> accounts = readFile();
        accounts.removeIf(acc -> acc.getId().equals(account.getId()));
        accounts.add(account);
        writeFile(accounts);
    }

    @Override
    public void update(Account account) {
        Collection<Account> accounts = readFile();
        boolean removed = accounts.removeIf(acc -> acc.getId().equals(account.getId()));
        if (!removed) {
            throw new RuntimeException("Account not found by id:" + account.getId());
        }
        accounts.add(account);
        writeFile(accounts);
    }
}
