public class Palindrome
{
   public static void main(String[] args)
   {
       String input = "redivider";
       boolean pal_confirmation =false;
       String b="";
 
       input = input.toLowerCase();    
       input = input.replace(" ","");
       for(int i = input.length()-1;i>=0;i--) 
       {
           b+=Character.toString(input.charAt(i));
       }
       if (input.equals(b))
       {
           pal_confirmation = true;
       }

       System.out.println("Does the '"+input+"' a palindrome? "+pal_confirmation);
   } 
}