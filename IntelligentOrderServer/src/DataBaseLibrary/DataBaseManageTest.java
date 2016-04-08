package DataBaseLibrary;

import javax.persistence.DiscriminatorValue;

public class DataBaseManageTest {

 	static String flag[]={"true"};
	
	class NotifyThread extends Thread
	{
		public NotifyThread(String name)
		{
			super(name);
		}
		
		@Override
		public void run()
		{
			try {
				sleep(3000);
			} catch (Exception e) {
				// TODO: handle exception
			}
//			synchronized (flag) {
//				flag[0]="false";
//				
//				flag.notify();
//				flag.notify();
//				flag.notify();
//			}
			
			
		}
	}
	
	class WaitThread extends Thread
	{
		public WaitThread(String name)
		{
			super(name);
		}
		
		public void run()
		{
			
//			synchronized (flag) {
//				while(flag[0]!="false")
//				{
//					System.out.println(getName()+" begin waiting");
//					long waitTime=System.currentTimeMillis();
//					
//					try {
//						flag.wait();
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					
//					waitTime=System.currentTimeMillis()-waitTime;
//					System.out.println("wait time "+waitTime);
//					System.out.println(getName()+" end waiting!");
//				}
//				
//			}
			synchronized (flag) {
				try {
					flag.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			synchronized (flag) {
				if(flag[0]!="false")
					;
			}
			System.out.println("haha!");

		}
	}
	
	
	public static void main(String arg[])
	{
		System.out.println("Main Thread Run.");
		DataBaseManageTest test=new DataBaseManageTest();
		NotifyThread notifyThread=test.new NotifyThread("notify01");
		WaitThread waitThread1=test.new WaitThread("wait1");
		WaitThread waitThread2=test.new WaitThread("wait2");
		WaitThread waitThread3=test.new WaitThread("wait3");
		
		notifyThread.start();
		waitThread1.start();
		waitThread2.start();
		waitThread3.start();
		
		
		synchronized (flag) {
			flag[0]="false";
			
			flag.notify();
			flag.notify();
			flag.notify();
		}

	}




}
