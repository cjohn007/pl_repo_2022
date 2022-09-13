import java.util.Scanner;

public class DFAtoCode 
{
    public static void main(String[] args) 
	
	{
        String state = "q0";
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter input: ");
		String input = scan.nextLine();

					
		for(int x=0;x<input.length();x++)
		{
			String storeChar = Character.toString(input.charAt(x));
						
			
			if(state.equals("q0" )&& storeChar.equals("0"))
			{
				state = "q1";
			}
			else if(state.equals("q0") && storeChar.equals("1"))
			{
				state = "q0";
			}
			else if(state.equals("q1")&&storeChar.equals("1"))
			{
				state="q2";
			}
			else if (state.equals("q1") && storeChar.equals("0"))
			{
				state = "q1";
			}
			else if(state.equals("q2")&& storeChar.equals("0"))
			{
				state = "q1";
			}
			else if (state.equals("q2") && storeChar.equals("1"))
			{
				state = "q0";
			}
			else
			{
				state = "dead state";
				break;
			}
			
		}
		if (state.equals("q2"))
		{
			System.out.println("String accepted");
		}
		else
		{
			System.out.println("String not accepted");
		}
		
				
		
		
	}
    
}
