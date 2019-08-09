//package comp6441project;

import java.util.Random;

class Customer implements Runnable {
	int amount;
	String name;
	money m = new money();
	boolean myVariable = true;
	int nOfBanks=0;
	int finalAmount;
	Customer(int amount, String name) {
		this.finalAmount=amount;
		this.amount = amount;
		this.name = name;
		nOfBanks=money.getBankList().size();
	}int randomAmount;
int deniedCounter=0;

	@Override
	public void run(){
		try {
			String accepted;
			while (myVariable) {
				if(amount<50&&amount>1) {
					int num=amount;
					Random rand = new Random();
					randomAmount = rand.nextInt(num - 1) + 1;
				}else {
					int num=50;
					Random rand = new Random();
					randomAmount = rand.nextInt(num - 1) + 1;
				}
				
				accepted=m.getLoan(randomAmount, name,amount);
				if(accepted.equals("true")) {
					amount = amount - randomAmount;
					if (amount == 0) {
						myVariable = false;
						
						//System.out.println(Thread.currentThread().getId() + "Random Value" + randomAmount + " Amount : " + amount);
						money.printLoanAccepted(name,finalAmount);
						//System.out.println(m.deniedMap);
						m.printFunction(name,finalAmount);
					} else if (amount < 0) {
						amount = amount + randomAmount;
					} else {
						//System.out.println(Thread.currentThread().getId() + "Random Value" + randomAmount + " Amount : " + amount);
					}	
				}else if(accepted.equals("denied")) {
					deniedCounter++;
					//System.out.println(name+"thread Stopped");
					m.printFunction(name,finalAmount);
//					System.out.println(m.deniedMap);
//					System.out.println(m.bankBalances);
//					System.out.println(m.finalAmount);
				if(deniedCounter==nOfBanks)
					myVariable=false;
				}
				
				Thread.sleep(1000);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

	}
}