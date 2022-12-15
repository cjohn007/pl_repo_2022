import java.util.Scanner;
public class Parser 
{
    public static void main(String[] args) 
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Please input syntax: ");
		String input = scan.nextLine();
		String result=doesSyntaxCorrect(input);
		System.out.println(result);
		
	}
    public static String doesSyntaxCorrect(String input)
	{
		String result="";
		String[] correctSyntax= {"<data_type><identifier><assignment_operator><value><delimiter>","<data_type><identifier><delimiter>"};
		input = input.replaceAll(" ", "");
		for(String r:correctSyntax)
		{
			if(input.equals(r))
			{
				result="Syntax is correct! ";
				break;
			}
			else
			{
				result="Syntax is Error!";
			}
		}
		return result;
	}
}

