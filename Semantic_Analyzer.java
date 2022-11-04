
import java.util.ArrayList;
import java.util.Scanner;

public class Semantic_Analyzer 
{
	
	static ArrayList<String>  coll_lexemes = new ArrayList<String>();
	private static final String alphabet ="abcdefghijklmnopqrstuvwxyz";
	private static final ArrayList<String> dataTypes = new ArrayList<String>();
	private static final String numbers = "1234567890";
	private static final char doub_quote='"';
	private static final String single_quote="'";

	public Semantic_Analyzer()
	{
		dataTypes.add("int");
		dataTypes.add("double");
		dataTypes.add("String");
		dataTypes.add("char");
		dataTypes.add("boolean");
	}

	public static void main(String[] args) 
	{
		boolean operation = true;
		while(operation==true)
		{
			new Semantic_Analyzer();
			Scanner scan = new Scanner(System.in);
			System.out.println("Please input assignment code: ");
			String input = scan.nextLine();
			analyzeSemantics(input);
			operation = Boolean.parseBoolean(scan.nextLine());
		}
		

	}
	
	public static void analyzeSemantics(String input)
	{
		String result="", tokens="", syntax="";
		
		tokens = tokenize(input);
		//System.out.println(tokens);
		syntax=analyzeSyntax(tokens); 
		
		/*****determine if the syntax is initialization or assigning-value*****/
		//if it is assigning-value
		if(syntax.equals("Correct_1"))
		{
			result= isCorrectValue(coll_lexemes);
		}
		else if(syntax.equals("Correct_2"))
		{
			result="Correct code!";
		}
		else
		{
			result="Incorrect code!";
		}
		System.out.println(result);
		
	}
	public static String isCorrectValue(ArrayList<String> coll_lexemes)
	{
		String result="",identifier="", value="";
		String[] collection_identifiers= {"int", "float", "String", "char","boolean"};
		char beginning, last;
		
		identifier=coll_lexemes.get(0);
		value = coll_lexemes.get(3);

		//System.out.println("beginning: "+beginning+"last: "+last );
		beginning=value.charAt(0);
		last = value.charAt(value.length()-1);
		
		
		
		//value is a String
		if(beginning==doub_quote&&last==doub_quote)
		{
			// data type/identifier is a String
			if(identifier.equals("String"))
			{
				result="Correct code!";
			}
			else
			{
				result="Incorrect code!";
			}
		}
		//value is a character
		else if(Character.toString(beginning).equals("'")&&Character.toString(last).equals("'")
				&&value.length()==3)
		{
			// data type/identifier is a character
			if(identifier.equals("char"))
			{
				result="Correct code!";
			}
			else
			{
				result="Incorrect code!";
			}
		}
		//value is a boolean
		else if(value.equalsIgnoreCase("true")||value.equalsIgnoreCase("false"))
		{
			// data type / identifier is a boolean
			if(identifier.equals("boolean"))
			{
				result="Correct code!";
			}
			else
			{
				result="Incorrect code!";
			}
		}
		//value is a float
		else if(value.contains("."))
		{
			//if it has letter
			if(alphabet.contains(value))
			{
				result="Incorrect code!";
			}
			else
			{
				//data type is a float
				if(identifier.equals("double"))
				{
					result="Correct code!";
				}
				else
				{
					result="Incorrect code!";
				}	
			}
		}
		//value is an integer

		else if(numbers.contains(value) && alphabet.contains(value)==false)
		{
			//data type is a float
			if(identifier.equals("int"))
			{
				result="Correct code!";
			}
			else
			{
				result="Incorrect code!";
			}
		}
		else
		{
			result="Incorrect code!!";
		}
		
		return result;
	}
	public static boolean isInt(String value)
	{
		boolean result = true;
		char temp;
		System.out.println("value: "+value);
		for(int i=0;i<value.length();i++)
		{
			temp=value.charAt(i);
			boolean tempChar = Character.isDigit(temp);
			if(tempChar==false)
			{
				System.out.println("tempChar is false! ");
				result=false;
			}
		}
		
		return result;
	}
	
	public static String analyzeSyntax(String input)
	{
		String result="";
		String assignValue= "<data_type><identifier><assignment_operator><value><delimeter>";
		String initIdentifier="<data_type><identifier><delimeter>";
		
		input = input.replaceAll(" ", "");
		if(input.equals(assignValue))
		{
			result="Correct_1";
		}
		else if(input.equals(initIdentifier))
		{
			result="Correct_2";
		}
		else
		{
			result="Error";
		}
		return result;
		
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
