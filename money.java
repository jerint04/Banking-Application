//package comp6441project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class money {
	static HashMap<String, Integer> bankBalances = new HashMap<String, Integer>();
	static ArrayList<String> deniedList = new ArrayList<String>();
	static HashMap<String, ArrayList<String>> deniedMap = new HashMap<>();
	static ArrayList<String> bankList = new ArrayList<String>();

	public static ArrayList<String> getBankList() {
		return bankList;
	}
	
	static HashMap<String, Integer> finalAmount = new HashMap<String, Integer>();
	static int printCounter = 0;

	static public void insertBank(int amount, String bankName) {
		bankBalances.put(bankName, amount);
		// System.out.println(bankBalances);
	}

	public void insertBankList(String bankName) {
		bankList.add(bankName);
	}
	public static void printLoanAccepted(String name,int amount) {
		System.out.println(name+ " has reached the objectieve of "+amount+" dollar(s) . WOO HOO!");
	}
	public String getLoan(int randomAmount, String name, int finalCustomerAmount) {
		String accepted = "false";

		String bankName = getRandomBankName(randomAmount, name);
		//System.out.println(bankList);
		if (bankName.equals("false")) {
			accepted = "false";
		} else if (bankName.equals("denied")) {
			accepted = "denied";
			finalAmount.put(name, finalCustomerAmount);
			
		} else {
			int bankRemainingAmount = bankBalances.get(bankName);
			bankRemainingAmount = bankRemainingAmount - randomAmount;
			bankBalances.put(bankName, bankRemainingAmount);
			accepted = "true";
			System.out.println(name + " request a loan of " + randomAmount + " dollars from " + bankName);

			System.out.println(bankName + " approves a loan of " + randomAmount + " from " + name);
		}

		return accepted;
	}
	
	public String getRandomBankName(int randomAmount, String name) {

		String bankName = "false";

		ArrayList<String> tempList = new ArrayList<String>();
		tempList = deniedMap.get(name);
		System.out.print("");
		ArrayList<String> tempBankList = new ArrayList<String>();
		for (int i = 0; i < bankList.size(); i++)
			tempBankList.add(bankList.get(i));

		tempBankList.removeAll(tempList);
		// System.out.println("tempBankList" + tempBankList);
		// System.out.println("bakLst" + bankList);
		if (tempBankList.size() != 0) {
			String randombankName = tempBankList.get(new Random().nextInt(tempBankList.size()));
			int bankRemainingAmount = bankBalances.get(randombankName);
			if (randomAmount <= bankRemainingAmount) {
				bankName = randombankName;
			} else {
				bankName = randombankName;
				System.out.println(name + " request a loan of " + randomAmount + " dollars from " + bankName);
				System.out.println(bankName + " denies a loan of " + randomAmount + " from " + name);
				// bankList.remove(bankName);
				if (!deniedMap.get(name).contains(bankName))
					deniedMap.get(name).add(bankName);
				bankName = "false";
			}
		} else {
			if (!bankName.equals("false")) {
				System.out.println(name + " request a loan of " + randomAmount + " dollars from " + bankName);
				System.out.println(bankName + " denies a loan of " + randomAmount + " from " + name);
			}
			bankName = "denied";
		}

		return bankName;
	}

	public static void setDeniedMap(ArrayList<String> customerName) {
		for (int i = 0; i < customerName.size(); i++)
			deniedMap.put(customerName.get(i), deniedList);
	}

	public void printFunction(String name,int finalAmoun) {
		printCounter++;
		//ArrayList
		if (printCounter == deniedMap.size()) {
			//bankBalances.forEach((key, value) -> System.out.println(key + " has " + value+" remaining"));
			for (Entry<String, Integer> entry : bankBalances.entrySet()) {
				  String key = entry.getKey();
				  String value = entry.getValue().toString();
				  System.out.println(key + " has " + value+" remaining");
				}
			//System.out.println(deniedMap);
			//for(int i=0;i<bankBalances.size();i++)
			//System.out.println(bankBalances.get(i)+" "+bankBalances.values());
			//System.out.println(finalAmount);
			//finalAmount.forEach((key, value) -> System.out.println(key + " was  unable to borrrow " + value));
			for (Entry<String, Integer> entry : finalAmount.entrySet()) {
				  String key = entry.getKey();
				  String value = entry.getValue().toString();
				  System.out.println(key + " was  unable to borrrow " + value);
				}
		}
	}
	private static void print_customer(ArrayList<String> c_name, ArrayList<Integer> c_amount) {
		// TODO Auto-generated method stub
		System.out.println("** Customers and loan objectives **");
		for (int i = 0; i < c_name.size(); i++) {
			String CustName=c_name.get(i);
					int amo=c_amount.get(i);
			System.out.println(CustName + ":" + amo);
		}
			
	}

	private static void print_bank(ArrayList<String> b_name, ArrayList<Integer> b_amount) {
		// TODO Auto-generated method stub
		System.out.println("** Banks and financial resources **");
		for (int i = 0; i < b_name.size(); i++) {
			String nam=b_name.get(i);
			int am=b_amount.get(i);
			System.out.println(nam + ":" + am);
		}
			
	}

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		money m = new money();
		String fileNameCustomer = ".\\customers.txt";
		String fileNameBank = ".\\banks.txt";
		Scanner scanCustomer = new Scanner(new File(fileNameCustomer));
		Scanner scanBank = new Scanner(new File(fileNameBank));
		int nOfCustomer = 0;
		int nOfBank = 0;
		ArrayList<String> customerName = new ArrayList<String>();
		ArrayList<Integer> customerAmount = new ArrayList<Integer>();
		ArrayList<String> bankName = new ArrayList<String>();
		ArrayList<Integer> bankAmount = new ArrayList<Integer>();

		while (scanCustomer.hasNext()) {
			nOfCustomer++;
			String line = scanCustomer.nextLine();
			//String spi="[\\[\\]\\{\\}\\.]", "";
			line = line.replaceAll("[\\[\\]\\{\\}\\.]", "");
			customerName.add(line.split(",")[0]);
			int a=Integer.parseInt(line.split(",")[1].trim());
			customerAmount.add(a);
		}
		money.setDeniedMap(customerName);
		while (scanBank.hasNext()) {
			nOfBank++;
			String line = scanBank.nextLine();
			line = line.replaceAll("[\\[\\]\\{\\}\\.]", "");
			bankName.add(line.split(",")[0]);
			int b=Integer.parseInt(line.split(",")[1].trim());
			bankAmount.add(b);
		}print_customer(customerName, customerAmount);
		print_bank(bankName, bankAmount);
		// money.setnOfBanks(nOfBank);
		Thread[] bankArray = new Thread[nOfBank];
		for (int i = 0; i < nOfBank; i++) {
			bankArray[i] = new Thread(new Banks(bankAmount.get(i), bankName.get(i)));
			bankArray[i].start();
			bankArray[i].join();
		}
		Thread[] customerArray = new Thread[nOfCustomer];
		for (int i = 0; i < nOfCustomer; i++) {
			customerArray[i] = new Thread(new Customer(customerAmount.get(i), customerName.get(i)));
			customerArray[i].start();
			// customerArray[i].join();
		}
		int counter = 0;
		// search:
		//
		// for (int i = 0; i < nOfCustomer; i++) {
		// if (customerArray[i].isAlive()) {
		// counter++;
		// }}
		// if (counter != nOfCustomer) {
		// counter=0;
		// break search;
		// }else if(counter==nOfCustomer) {
		// System.out.println("Printing"+deniedMap);
		// System.out.println(bankBalances);
		// System.out.println(finalAmount);
		// }

	}
}
