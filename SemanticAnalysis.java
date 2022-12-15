import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SemanticAnalysis 
{
	static ArrayList<String>  coll_lexemes = new ArrayList<String>();
	private static final String alphabet ="abcdefghijklmnopqrstuvwxyz";
	private static final ArrayList<String> dataTypes = new ArrayList<String>();
	private static final String numbers = "1234567890";
	private static final char doub_quote='"';
	private static final String single_quote="'";
	
	public static void main(String[] args)
	{
		String result = analyzeSemantics("double x = '-2.3654';");
		System.out.println(result);
	}

	public static String analyzeSemantics(String input)
	{
		dataTypes.add("int");
		dataTypes.add("double");
		dataTypes.add("String");
		dataTypes.add("char");
		dataTypes.add("boolean");
		
		String result="", tokens="", syntax="";
		
		tokens = tokenize(input);
                System.out.println(tokens);
		syntax=analyzeSyntax(tokens); 
		System.out.println("syntax analysis: "+syntax);
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
                coll_lexemes.clear();
		return result;	             
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
				result="Your code is syntactically and semantically correct! "+ new String(Character.toChars(0x1F603));//+ new String(Character.toChars(0x1F349))
			}
			else
			{
				result="Your code is syntactically and semantically incorrect! "+new String(Character.toChars(0x1F61E));
			}
		}
		//value is a character
		else if(Character.toString(beginning).equals("'")&&Character.toString(last).equals("'"))
		{
			// data type/identifier is a character
			if(identifier.equals("char")&&value.length()==3)
			{
				result="Your code is syntactically and semantically correct! "+ new String(Character.toChars(0x1F603));

			}
			else
			{
				result="Your code is syntactically and semantically incorrect! "+new String(Character.toChars(0x1F61E));
			}
		}
		//value is a boolean
		else if(value.equals("true")||value.equals("false"))
		{
			// data type / identifier is a boolean
			if(identifier.equals("boolean"))
			{
				result="Your code is syntactically and semantically correct! "+ new String(Character.toChars(0x1F603));
			}
			else
			{
				result="Your code is syntactically and semantically incorrect! "+new String(Character.toChars(0x1F61E));
			}
		}
		//value is a float
		else if(value.contains("."))
		{
			//if it has letter
			if(alphabet.contains(value))
			{
				result="Your code is syntactically and semantically incorrect! "+new String(Character.toChars(0x1F61E));
			}
			else
			{
				//data type is a float
				if(identifier.equals("double"))
				{
					result="Your code is syntactically and semantically correct! "+ new String(Character.toChars(0x1F603));
				}
				else
				{
					result="Your code is syntactically and semantically incorrect! "+new String(Character.toChars(0x1F61E));
				}	
			}
		}
		//value is an integer

		else if(isInteger(value) && isLetter(value)==false)
		{
			//data type is a float
			if(identifier.equals("int"))
			{
				result="Your code is syntactically and semantically correct! "+ new String(Character.toChars(0x1F603));
			}
			else
			{
				result="Your code is syntactically and semantically incorrect! "+new String(Character.toChars(0x1F61E));
			}
		}
		else
		{
			result="Your code is syntactically and semantically incorrect! "+new String(Character.toChars(0x1F61E));
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
		String assignValue= "<data_type><identifier><assignment_operator><value><delimiter>";
		String initIdentifier="<data_type><identifier><delimiter>";
		
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
		
		for(int x=0;x<input.length();x++)// String x = "awe awe" ;
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
						count++;//1
						tempStr+= Character.toString(input_char);
					}
					//if it is the second double or single quotation 
					else if ((input_char == '"'|| Character.toString(input_char).equals("'")) && count==2)//awe awe"
					{
						count++;//2
						tempStr = tempStr+Character.toString(input_char);
						coll_lexemes.add(tempStr);
						x=y;
						break;
					}
					else
					{
						tempStr+=Character.toString(input_char);//awe awe
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
			else{
			
				if(temp.equals(" ")){
					
				}
				else{
					lexeme+=temp;
				}						
			}
		}
		return coll_lexemes;
	}
	public static void setColl_Lexemes(String lexeme){
		if(lexeme.isEmpty()==false){
			coll_lexemes.add(lexeme);
		}
	}
	
	public static String matchLexemes(ArrayList<String> coll_lexemes){
		String arr_token="";
		for(int y = 0;y<coll_lexemes.size();y++){
			String element = coll_lexemes.get(y);

			if(element.equals(";")){
				arr_token+= "<delimiter>";
			}
			else if (element.equals("=")){
				arr_token+= "<assignment_operator>";
			}
			else if(dataTypes.contains(element)){
				arr_token+= "<data_type>";
			}
			else{
				element = element.replaceAll(" ","");
				
				//string value
				//if the first index are double quotation
				if(element.charAt(0)==doub_quote)//(element.substring(0, element.length()-1).equals(Character.toString('"')))
				{
					arr_token+="<value>";
				}
				//if the first index is single quotation
				else if(Character.toString(element.charAt(0)).equals(single_quote)){
					arr_token+="<value>";
				}
				//if a number is the first index
				else if(isInteger(element)){
					arr_token+="<value>";
				}
				//if the element has dot
				else if (element.contains(".")){
					arr_token+="<value>";
				}
				else if(element.equals("true")||element.equals("false")){
					arr_token+="<value>";
				}

				else{
					arr_token+="<identifier>";
				}		
			}
		}		
		return arr_token;
	}
	public static boolean isInteger(String input){
		boolean confirmation=true;
		for(int x = 0 ; x<input.length();x++){
			String temp = String.valueOf(input.charAt(x));
			if(numbers.contains(temp)){
				
			}
			else
			{
				confirmation=false;
				break;
			}
		}

		return confirmation;
	}
	public static boolean isLetter(String input){
		boolean confirmation=true;
		for(int x = 0 ; x<input.length();x++){
			String temp = String.valueOf(input.charAt(x));
			if(alphabet.contains(temp)){

			}
			else{
				confirmation=false;
				break;
			}
		}
		return confirmation;
	}
}