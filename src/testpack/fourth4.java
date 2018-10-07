package testpack;

public class fourth4
{
	public static void main(String[] args)
	{
		System.out.println("6が出たら終了");
		while(true)
		{
			int dice = (int)(Math.random()*6)+1;
			System.out.println(dice);
			if(dice == 6)
			{
				break;
			}
		}
		System.out.println("終了");
	}
}