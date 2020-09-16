package training.supportbank;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        System.out.println("Reading info in CSV file.");
        //Goes through the CSV file line by line and creates the transactions
        String csvFile = "/Users/erudling/IdeaProjects/SupportBank/Transactions2014.csv"; //location of the file
        String line = "";
        Person[] listOfPeople;
        Transaction[] listOfPayments;
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] transactionInfo = line.split(",");
                BigDecimal amount = new BigDecimal(transactionInfo[4]); //converts the String into a BigDecimal
                Transaction newTransaction = new Transaction(transactionInfo[0], transactionInfo[1], transactionInfo[2], transactionInfo[3], amount);
                Bank.AddTransaction(newTransaction); //adds a new transaction to the bank
                Person newPerson = new Person(transactionInfo[1]);
                Bank.AddAccount(newPerson); //creates the new person to the accounts list
                Person newPerson2 = new Person(transactionInfo[2]);
                Bank.AddAccount(newPerson2); //creates the new person to the accounts list
                //this will be checked, and added if it does not already exist as a person
            }
        }
        catch (IOException e) { System.out.println("Failed to read CSV file provided."); }
        while(1 == 1){
            System.out.println("Enter 'List All' to output all names and money owed.");
            System.out.println("Enter 'List Account' to output all the transactions for a specific account.");
            System.out.println("Enter 'Exit' to exit.");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.next();
            switch(input){
                case "List All":
                    Bank.ListAll();
                    break;
                case "List Account":
                    Bank.ListAccount();
                    break;
                case "Exit":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please enter a valid input.");
            }
        }
    }
}

class Bank {
    Person[] Accounts;
    Transaction[] Payments;
    static void AddTransaction(Transaction transaction){
        Transaction.add(transaction);
    }
    static void AddAccount(Person person){
        //checks to see if the account already exists and adds it if it does not
    }
    static void ListAll(){
        //list all - outputs all the names and money they owe
        //print all transaction objects
    }
    static void ListAccount(){
        //list [account] - prints all of the transactions for [account] chosen
        //print all transaction objects containing an account name
    }
}

class Person {
    String Name; //Name of the person
    public Person (String name){
        this.Name = name;
    }
}

//change to actual date
class Transaction {
    String Date;
    String To;
    String From;
    String Narrative;
    BigDecimal Amount;
    public Transaction (String date, String to, String from, String narrative, BigDecimal amount){
        this.Date = date;
        this.To = to;
        this.From = from;
        this.Narrative = narrative;
        this.Amount = amount;
    }
}
