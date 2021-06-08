public class David{
	public static void main(String[] args){
		System.out.println("sup");
		System.out.println("sup bro");

		//Countdown
		for(int i = 0; i < 11; i++)
		{
			System.out.println(i);
			try 
			{
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
			
			if( i == 10 )
			{
				i = 0;
			}
		}
	}
}