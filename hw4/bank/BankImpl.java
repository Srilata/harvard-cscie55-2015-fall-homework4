package cscie55.hw4.bank;


import java.util.*;

/**
 * @Author ssurabhi on 11/7/15.
 * @version 1.0
 */

public class BankImpl implements Bank{

   private Hashtable<Integer, Account> accounts = new Hashtable<Integer, Account>();

    /*
   add Account
   @param account
   */
    @Override
    public void addAccount(Account account) throws DuplicateAccountException {
        if(accounts.containsKey(account.id())){
            throw new DuplicateAccountException(account.id());
        }
        accounts.put(account.id(), account);

    }

    /*
     transferWithoutLocking
     @param fromId,toId,amount
  */
    @Override
    public void transferWithoutLocking(int fromId, int toId, long amount) throws InsufficientFundsException {

        Account fromAccount = accounts.get(fromId);
        Account toAccount = accounts.get(toId);
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
    }

    /*
    transferLockingBank
    @param fromId,toId,amount
   */
    @Override
    public void transferLockingBank(int fromId, int toId, long amount) throws InsufficientFundsException {

        Account fromAccount = accounts.get(fromId);
        Account toAccount = accounts.get(toId);
        synchronized (this) {
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
        }
    }


    /*
    transferLockingAccounts
    @param fromId,toId,amount
   */
    @Override
    public void transferLockingAccounts(int fromId, int toId, long amount) throws InsufficientFundsException {

        Account fromAccount = accounts.get(fromId);
        Account toAccount = accounts.get(toId);
        synchronized (fromAccount) {
            fromAccount.withdraw(amount);
        }

        synchronized (toAccount) {
            toAccount.deposit(amount);
        }
    }

    /*
   totalBalances
   @param
   */
    @Override
    public long totalBalances() {
        long totalBalance = 0;
        Enumeration<Integer> keys = accounts.keys();
        while(keys.hasMoreElements()){
            totalBalance = totalBalance + ((Account)accounts.get(keys.nextElement())).balance();
        }

        return totalBalance;
    }

   /*
   numberOfAccounts
   @param
  */
    @Override
    public int numberOfAccounts() {
        return accounts.size();
    }
}
