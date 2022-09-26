import java.util.ArrayList;
import java.util.Scanner;

public class Tokenizer 
{
    
    public static void main(String[] args) 
	{
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Please input code: ");
		String input = scan.nextLine();
		
		//return token
		String arr_token = tokenizer(input);
		
		System.out.println(arr_token);
	}

    public static String tokenizer(String input)
	{
		String arr_token = "";
		String lexeme = "";
		ArrayList<String> coll_lexemes = new ArrayList<String>();
		String dataTypes = "int boolean String char double";
		
		//separate lexemes then store it in coll_lexeme
		for(int x = 0;x<input.length();x++)
		{
			char each_char = input.charAt(x);
			String str_char = Character.toString(each_char); 
			
			if(each_char == ';')
			{
				if(lexeme.isEmpty()==false)
				{
					coll_lexemes.add(lexeme);
					coll_lexemes.add(";");
				}
				else
				{
					coll_lexemes.add(";");
				}
				
			}
			else if(each_char == '=')
			{
				coll_lexemes.add("=");
			}
			else if(each_char == '"')
			{
				String tempStr = "";
				int count = 1;
				for(int y=x;y<input.length();y++)
				{
					char input_char = input.charAt(y);
					if(input_char == '"' && count==1)
					{
						count++;
						tempStr+= Character.toString(input_char);
					}
					else if (input_char == '"' && count==2)
					{
						count++;
						tempStr = tempStr+Character.toString(input_char);
						coll_lexemes.add(tempStr);
						x=y;
						break;
					}
					else
					{
						tempStr+=Character.toString(input_char);
					}
				}
				
			}
			else if(each_char != ' ')
			{
				if(x==input.length()-1)
				{
					lexeme+=str_char;
					coll_lexemes.add(lexeme);
				}
				else
				{
					lexeme+=str_char;
				}
			}
			else if(each_char ==' ' && lexeme.isEmpty()==false)
			{
				
				coll_lexemes.add(lexeme);
				lexeme="";
			}
		}
		
		System.out.println(coll_lexemes);
		//Identify the lexeme
		for(int y = 0;y<coll_lexemes.size();y++)
		{
			String element = coll_lexemes.get(y);
			
			if(element.equals(";"))
			{
				arr_token+= "<delimeter>";
			}
			else if (element.equals("="))
			{
				arr_token+= "<assignment_operator>";
			}
			else if(dataTypes.contains(element))
			{
				arr_token+= "<data_type> ";
			}
			else
			{
				char double_quote = '"';
				String dobQoute_str = Character.toString(double_quote);
				char elem_char = element.charAt(0);
				String numbers = "1234567890";
				
				
				//int 
				if(element.contains(Character.toString('"')))
				{
					arr_token+="<value>";

				}
				//char
				else if (element.contains("'"))
				{
					arr_token+="<value>";
				}
				//double
				else if (element.contains("."))
				{
					arr_token+="<value>";
				}
				else if (numbers.contains(Character.toString(elem_char)))
				{
					arr_token+="<value>";
				}
				else 
				{
					arr_token+="<identifier>";
				}
				
				
			}
		}
		
		
		
		return arr_token;
	}
}
