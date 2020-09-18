package training.supportbank;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.math.BigDecimal;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();
    public static void main(String args[]) {
        LOGGER.info("Start of info Log");
        Bank bank = new Bank();
        System.out.println("Reading info in file provided.");
        //Goes through the CSV file line by line and creates the transactions
        String csvFile = "/Users/erudling/IdeaProjects/SupportBank/Transactions2014.csv"; //location of the file
        String csvFile2 = "/Users/erudling/IdeaProjects/SupportBank/DodgyTransactions2015.csv"; //location of the file
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFile2));
            LOGGER.info("Processing CSV file started.");
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] transactionInfo = line.split(",");
                BigDecimal amount = new BigDecimal(transactionInfo[4]); //converts the String into a BigDecimal
                Transaction newTransaction = new Transaction(transactionInfo[0], transactionInfo[1], transactionInfo[2], transactionInfo[3], amount);
                bank.addTransaction(newTransaction); //adds a new transaction to the bank
                Person newPerson = new Person(transactionInfo[1]);
                bank.addAccount(newPerson); //creates the new person to the accounts list
                Person newPerson2 = new Person(transactionInfo[2]);
                bank.addAccount(newPerson2); //creates the new person to the accounts list
                //this will be checked, and added if it does not already exist as a person
            }
        }
        catch (IOException e) { System.out.println("Failed to read CSV file provided."); }
        while(true){
            System.out.println("1 -- 'List All' to output all names and money owed.");
            System.out.println("2 -- 'List Account' to output all the transactions for a specific account.");
            System.out.println("3 -- 'Exit' to exit.");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.next();
            switch(input){
                case "1":
                    bank.listAll();
                    break;
                case "2":
                    bank.listAccount();
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
    ArrayList<Person> accounts = new ArrayList<Person>();
    ArrayList<Transaction> payments = new ArrayList<Transaction>();
    void addTransaction(Transaction transaction){
        payments.add(transaction);
    }
    void addAccount(Person person){
        //checks to see if the account already exists and adds it if it does not
        boolean accountExists = false;
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).Name.equals(person.Name)) {
                accountExists = true;
                break;
            }
        }
        if (!accountExists){
            //if the name is not found in the search, it is added to the list of accounts
            accounts.add(person);
        }
    }
    void listAll(){
        System.out.println("Date, To, From, Narrative, Amount:");
        for (int i = 0; i < payments.size(); i++) { //lists all transactions
            System.out.println(payments.get(i).date + ", " + payments.get(i).to + ", " + payments.get(i).from + ", " + payments.get(i).narrative + ", £" + payments.get(i).amount);
        }
    }
    void listAccount(){ //list [account] - prints all of the transactions for [account] chosen
        System.out.println("Enter the name of the account you would like to list details of.");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        boolean relevant = false;
        System.out.println("Date, To, From, Narrative, Amount:");
        for (int i = 0; i < payments.size(); i++) { //print all transaction objects containing an account name
            relevant = false;
            if (payments.get(i).to.equals(input)){
                relevant = true;
            }
            if (payments.get(i).from.equals(input)){
                relevant = true;
            }
            if (relevant){
                System.out.println(payments.get(i).date + ", " + payments.get(i).to + ", " + payments.get(i).from + ", " + payments.get(i).narrative + ", £" + payments.get(i).amount);
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

class Transaction {
    String date;
    String to;
    String from;
    String narrative;
    BigDecimal amount;
    public Transaction (String date, String to, String from, String narrative, BigDecimal amount){
        this.date = date;
        this.to = to;
        this.from = from;
        this.narrative = narrative;
        this.amount = amount;
    }
}