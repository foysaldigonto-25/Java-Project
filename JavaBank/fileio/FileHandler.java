package fileio;

import entities.Account;
import entities.Transaction;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler
{
    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static final String TRANSACTIONS_FILE = "transactions.txt";

    public static List<Account> loadAccounts()
    {
        List<Account> accountList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split(",");  
                if (parts.length >= 3)
                {
                    String accountNumber = parts[0];
                    String ownerName = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    accountList.add(new Account(accountNumber, ownerName, balance));
                }
            }
        }
        catch (IOException e)
        {
            
        }

        return accountList;
    }

    public static void saveAccounts(List<Account> accounts)
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNTS_FILE)))
        {
            for (Account account : accounts)
            {
                writer.println(account.toString());  
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static List<Transaction> loadTransactions()
    {
        List<Transaction> transactionList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTIONS_FILE)))
        {
            String line;

            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split(",");  

                if (parts.length >= 5)
                {
                    String transactionId = parts[0];
                    String accountNumber = parts[1];
                    double amount        = Double.parseDouble(parts[2]);
                    double balanceAfter  = Double.parseDouble(parts[3]);
                    String type          = parts[4];  

                    transactionList.add(new Transaction(transactionId, accountNumber,amount, balanceAfter, type));
                }
            }
        }
        catch (IOException e)
        {
            
        }

        return transactionList;
    }
	
    public static void appendTransaction(Transaction t) 
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TRANSACTIONS_FILE, true)))
        {
            writer.println(t.toString());  
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String generateAccountNumber()
    {
        int totalAccounts    = loadAccounts().size();
        int newAccountCount  = totalAccounts + 1;
        long randomPart      = (long)(Math.random()*99999999999L);
        return String.format("0021-%011d-%02d", randomPart, newAccountCount);
    }
}