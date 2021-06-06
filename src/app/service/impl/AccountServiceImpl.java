package app.service.impl;

import app.Store;
import app.model.Account;
import app.service.AccountService;
import app.service.StoreService;

public class AccountServiceImpl implements AccountService {
    private StoreService service;

    public AccountServiceImpl(){

    }

    public AccountServiceImpl(StoreService service){
        this.service = service;
    }

    @Override
    public double balance(Account account) {
        return account.getAmount();
    }

    @Override
    public void deposit(Account account, double amount) {
        double sum = account.getAmount() + amount;
        account.setAmount(sum);
        synchronized (Store.getSync()) {
            service.update(account);
        }
    }

    @Override
    public void withdraw(Account account, double amount) {
        double sum = account.getAmount() - amount;
        if (sum < 0) {
            throw new RuntimeException("Not enough money");
        }
        account.setAmount(sum);
        synchronized (Store.getSync()) {
            service.update(account);
        }
    }

    @Override
    public void transfer(Account from, Account to, double amount) {
        double fromSum = from.getAmount() - amount;
        double toSum = to.getAmount() + amount;
        if (fromSum < 0) {
            throw new RuntimeException("Not enough money");
        }
        from.setAmount(fromSum);
        to.setAmount(toSum);
        synchronized (Store.getSync()) {
            service.update(from);
            try {
                service.update(to);
            } catch (RuntimeException e) {
                from.setAmount(fromSum + amount);
                service.update(from);
                throw new RuntimeException("Can't transfer money");
            }
        }
    }

    public static void main(String[] args) {
        FileStoreService fss = new FileStoreService();
        StoreServiceImpl ssi = new StoreServiceImpl();
        AccountServiceImpl asi = new AccountServiceImpl(ssi);
        Thread[] t = new Thread[Store.getStore().size()];
        int i = 0;
        for (Account acc : Store.getStore().values()) {
            t[i] = new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " " + asi.balance(acc));
                asi.deposit(acc, 2.0);
                System.out.println(Thread.currentThread().getName() + " " + asi.balance(acc));
                asi.withdraw(acc, 1.0);
                System.out.println(Thread.currentThread().getName() + " " + asi.balance(acc));
            });
            t[i++].start();
        }
    }
}
