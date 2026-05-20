package entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction 
   {
    private String accountNumber;
    private String type;
    private double amount;
    private double balanceAfter;
    private String dateTime;

    public Transaction(String accountNumber,String type,double amount, double balanceAfter) 
	{
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dateTime = dtf.format(LocalDateTime.now());      
    }

    public Transaction(String accountNumber,String type,double amount,double balanceAfter,String dateTime) 
	{              
	    this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.dateTime = dateTime;
    }

    public String getAccountNumber() 
	{
        return accountNumber;
    }
    public String getType() 
	{
        return type;
    }
    public double getAmount() 
	{
        return amount;
    }
    public double getBalanceAfter() 
	{
        return balanceAfter;
    }
    public String getDateTime() 
	{
        return dateTime;
    }
    public String toString()
	{
        return accountNumber + "," + type + "," + String.format("%.2f", amount) + ","+ String.format("%.2f", balanceAfter)+ "," + dateTime;           
    }
}