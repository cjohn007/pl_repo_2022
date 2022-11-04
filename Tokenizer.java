import java.util.ArrayList;
import java.util.Scanner;

public class Tokenizer 
{
	private static ArrayList<String> coll_lexemes;
	private static char doub_quote = '"';
	private static String single_quote="'";
	private static ArrayList<String> dataTypes= new ArrayList<String>();
	private static String numbers="0123456789";
    
    public static void main(String[] args) 
	{
		dataTypes.add("int");
		dataTypes.add("double");
		dataTypes.add("String");
		dataTypes.add("char");
		dataTypes.add("boolean");
		Scanner scan = new Scanner(System.in);
		coll_lexemes = new ArrayList<String>();
		boolean run = true;
		while(run==true)
		{
			System.out.println("Please input code: ");
			String input = scan.nextLine();
			//return token
			String arr_token = tokenize(input);
			System.out.println(arr_token);

			//clear the List
			coll_lexemes.clear();
			
			run = Boolean.valueOf(scan.nextLine());
		}

	}

	public static String tokenize(String input)
	{
		String tokens = "";

		//separate lexeme then store it in coll_lexeme
		coll_lexemes=splitLexemes(coll_lexemes,input);
		//match every lexeme to corresponding token
		tokens = matchLexemes(coll_lexemes);
		
		
		return tokens;
	}
	public static ArrayList<String> splitLexemes(ArrayList<String> coll_lexemes, String input)
	{
		String lexeme="";
		
		for(int x=0;x<input.length();x++)
		{
			String temp = Character.toString(input.charAt(x)); 

			//if temp is equal to double or single quotation
			if(input.charAt(x) == '"' || temp.equals("'"))
			{
				int count = 1;//determine how many quotations
				String tempStr="";
				/*Loop to store the String values*/
				for(int y=x;y<input.length();y++)
				{
					char input_char = input.charAt(y);

					//if it is the first double or single quotation 
					if((input_char == '"'|| Character.toString(input_char).equals("'")) && count==1)
					{
						count++;
						tempStr+= Character.toString(input_char);
					}
					//if it is the second double or single quotation 
					else if ((input_char == '"'|| Character.toString(input_char).equals("'")) && count==2)
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
				lexeme="";
				
			}
			//if temp is equal to assignment operator
			else if(temp.equals("="))
			{
				setColl_Lexemes(lexeme);
				coll_lexemes.add("=");
				lexeme="";
			}
			//if temp is equal to semi-colon
			else if(temp.equals(";"))
			{
				setColl_Lexemes(lexeme);
				coll_lexemes.add(";");
				lexeme="";
			}
			//if x is at the last character of input
			else if(x==input.length()-1)
			{
				lexeme+=temp;
				setColl_Lexemes(lexeme);
			}
			//if lexeme is within the dataTypes
			//store the lexeme
			else if(dataTypes.contains(lexeme)&& input.charAt(x+1)==' ') 
			{
				setColl_Lexemes(lexeme);
				lexeme="";
			}
			
			//if lexeme is an identifier
			else if(lexeme.isEmpty()==false && (temp.equals(" ")))
			{
				setColl_Lexemes(lexeme);
				lexeme="";
			}
			else
			{
			
				if(temp.equals(" "))
				{
					
				}
				else
				{
					lexeme+=temp;
				}
			
				
			}
		}
		return coll_lexemes;
	}
	public static void setColl_Lexemes(String lexeme)
	{
		if(lexeme.isEmpty()==false)
		{
			coll_lexemes.add(lexeme);
		}
	}
	
	public static String matchLexemes(ArrayList<String> coll_lexemes)
	{
		String arr_token="";
	
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
				element = element.replaceAll(" ","");

				
				//string value
				//if the first index are double quotation
				if(element.charAt(0)==doub_quote)//(element.substring(0, element.length()-1).equals(Character.toString('"')))
				{
					arr_token+="<value>";
				}
				//if the first index is single quotation
				else if(Character.toString(element.charAt(0)).equals(single_quote))
				{
					arr_token+="<value>";
				}
				//if a number is the first index
				else if(numbers.contains(element))
				{
					arr_token+="<value>";
				}
				//if the element has dot
				else if (element.contains("."))
				{
					arr_token+="<value>";
				}
				else if(element.equalsIgnoreCase("true")||element.equalsIgnoreCase("false"))
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


