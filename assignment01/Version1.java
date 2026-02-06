package assignment01;

public class Version1 implements Code {

	private char[] encode; 
    private char[] decode; 

	public Version1() {

		encode = new char[52];
        decode = new char[52];

		char[] upper = {
    		'M','Q','Z','A','W','T','E','Y','R','U',
    		'O','P','S','D','F','G','H','J','K','L',
    		'X','C','V','B','N','I'
		};

        char[] lower = {
    		'm','q','z','a','w','t','e','y','r','u',
    		'o','p','s','d','f','g','h','j','k','l',
 			'x','c','v','b','n','i'
		};

        for (int i = 0; i < 26; i++) {
            encode[i] = upper[i];        
            encode[i + 26] = lower[i]; 
        }

        for (int i = 0; i < 26; i++) {
            char encodedUpper = encode[i];
            decode[encodedUpper - 'A'] = (char) ('A' + i);
            char encodedLower = encode[i + 26];
            decode[encodedLower - 'a' + 26] = (char) ('a' + i);
        }
	}
	
	@Override
	public String encode(String input) {
		String output = "";

		for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            if (Character.isUpperCase(ch)) {
                output += encode[ch - 'A'];
            } 
            else if (Character.isLowerCase(ch)) {
                output += encode[ch - 'a' + 26];
            } 
            else {
                output += ch;
            }
        }
		
		return output;
	}
	
	@Override
	public String decode(String input) {
		String output = "";

		for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            if (Character.isUpperCase(ch)) {
                output += decode[ch - 'A'];
            } 
            else if (Character.isLowerCase(ch)) {
                output += decode[ch - 'a' + 26];
            } 
            else {
                output += ch;
            }
        }

		return output;
	}
}
