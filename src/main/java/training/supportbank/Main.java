package training.supportbank;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        Bank bank = new Bank();
        System.out.println("Reading info in CSV file.");
        //Goes through the CSV file line by line and creates the transactions
        String csvFile = "/Users/erudling/IdeaProjects/SupportBank/Transactions2014.csv"; //location of the file
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] transactionInfo = line.split(",");
                BigDecimal amount = new BigDecimal(transactionInfo[4]); //converts the String into a BigDecimal
                Transaction newTransaction = new Transaction(transactionInfo[0], transactionInfo[1], transactionInfo[2], transactionInfo[3], amount);
                bank.AddTransaction(newTransaction); //adds a new transaction to the bank
                Person newPerson = new Person(transactionInfo[1]);
                bank.AddAccount(newPerson); //creates the new person to the accounts list
                Person newPerson2 = new Person(transactionInfo[2]);
                bank.AddAccount(newPerson2); //creates the new person to the accounts list
                //this will be checked, and added if it does not already exist as a person
            }
        }
        catch (IOException e) { System.out.println("Failed to read CSV file provided."); }
        while(1 == 1){
            System.out.println("1 -- 'List All' to output all names and money owed.");
            System.out.println("2 -- 'List Account' to output all the transactions for a specific account.");
            System.out.println("3 -- 'Exit' to exit.");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.next();
            switch(input){
                case "1":
                    bank.ListAll();
                    break;
                case "2":
                    bank.ListAccount();
                    break;
                case "3":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please enter a valid input.");
            }
        }
    }
}

class Bank {
    ArrayList<Person> Accounts = new ArrayList<Person>();
    ArrayList<Transaction> Payments = new ArrayList<Transaction>();
    void AddTransaction(Transaction transaction){
        Payments.add(transaction);
    }
    void AddAccount(Person person){
        //checks to see if the account already exists and adds it if it does not
        boolean accountExists = false;
        for (int i = 0; i < Accounts.size(); i++) {
            if (Accounts.get(i).Name == person.Name){
                accountExists = true;
            }
        }
        if (accountExists == false){
            //if the name is not found in the search, it is added to the list of accounts
            Accounts.add(person);
        }
    }
    void ListAll(){
        for (int i = 0; i < Payments.size(); i++) { //lists all transactions
            System.out.println(Payments.get(i));
        }
    }
    void ListAccount(){ //list [account] - prints all of the transactions for [account] chosen
        System.out.println("Enter the name of the account you would like to list details of.");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();

        //print all transaction objects containing an account name
        boolean relevant = false;
        for (int i = 0; i < Accounts.size(); i++) {
            if (Payments.get(i).To.equals(input)){
                relevant = true;
            }
            if (Payments.get(i).From.equals(input)){
                relevant = true;
            }
            if (relevant == true){
                System.out.println(Payments.get(i));
            }
        }
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