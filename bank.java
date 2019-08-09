//package comp6441project;

import java.util.Random;

class Banks implements Runnable {
	int amount;
	String name;
	money m = new money();
	Banks(int amount, String name) {
		this.amount = amount;
		this.name = name;
	}

	@Override
	public void run() {
		//for(int i=0;i<10;i++){
		//thread.start();	
		//}
		
		//for(int j =0;j<10;j++){
		//thread.start();	
		//}
		//thread.runnable();
		//customer.thread;
		// TODO Auto-generated method stub
		Random rand = new Random();
		int n = rand.nextInt(50);
		//System.out.println(Thread.currentThread().getId() + "Value" + n);
		m.insertBank(amount, name);
		m.insertBankList(name);
		//System.out.println(name + "Name " + n);

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}

}