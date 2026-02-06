package assignment01;

import java.util.*;

public class Version3 implements Code {

	// These variables persist for the entire life of each Version3 object
	private Map<Character, Integer> encodeMap; 
	private Map<Integer, Character> decodeMap;
	private Random random;

	public Version3() {

		encodeMap = new TreeMap<>();
		decodeMap = new TreeMap<>();
		random = new Random();

		// Store all the acceptable characters (i.e., a-z, A-Z, special char) in a list
		List <Character> chars = new ArrayList<>(); 
		for (char c = 'A'; c <= 'Z'; c++) {
			chars.add(c);
		}
		for (char c = 'a'; c <= 'z'; c++) {
			chars.add(c);
		}
        chars.add(' ');
        chars.add(',');
        chars.add('.');
        chars.add(':');
        chars.add(';');
        chars.add('?');
        chars.add('!');

		// Create all possible 2-digit codes 
        List <Integer> two_digit_codes = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 5; j++) {
                two_digit_codes.add(i * 10 + j);
            }
        }

		// Create all possible 3-digit codes
		List <Integer> three_digit_codes = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			for (int j = 1; j <= 5; j++) {
				for (int k = 1; k <= 5; k++) {
					three_digit_codes.add((i * 100) + (j * 10) + k);
				}
			}
		}

		// Randomize all the 2 and 3 digit codes, and assign them to the characters we want to encode
		// We have 59 characters. To ensure we have a mix of 2 and 3 digit codes, first 20 characters get the randomized 2 digit
		// codes. Remaining 39 get the randomized 3 digit codes.  
		Collections.shuffle(two_digit_codes, random);
		Collections.shuffle(three_digit_codes, random);
		int two_count = 20;
		for (int i = 0; i < two_count; i++) {
			char c = chars.get(i);
			int code = two_digit_codes.get(i);

			encodeMap.put(c, code);
			decodeMap.put(code, c);
		}
		for (int i = two_count; i < chars.size(); i++) {
			char c = chars.get(i);
			int code = three_digit_codes.get(i - two_count);

			encodeMap.put(c, code);
			decodeMap.put(code, c);
		}
	
	}
	
	@Override
	public String encode(String input) {
		String output = "";

		// Encoding each character of the input string
		for (int i = 0; i < input.length(); i++) {
			char curr_char = input.charAt(i); // current character 

			// Simply skip the current character if it is not "encodable"
			if (!encodeMap.containsKey(curr_char)) { 
				continue;
			}
			// If it is encodable, we grab the encoding and add "noise"
			int encoding = encodeMap.get(curr_char);
			output += encoding;
			int noise_length = 1 + random.nextInt(3); // gives me a value E[1,3]
			for (int j = 0; j < noise_length; j++) {
				output += (6 + random.nextInt(4)); // this loop generates some random numbers E[6,9] 
			}
			if (random.nextInt(10) > 7) output += "0";

		}

		return output;
	}
	
	@Override
	public String decode(String input) {
		String output = "";
		int pos = 0; // position of where we are in the input

		// Nonsense characters
		List <Character> nonsense = new ArrayList<> ();
		nonsense.add('6');
		nonsense.add('7');
		nonsense.add('8');
		nonsense.add('9');
		nonsense.add('0');

		// Scan the input to find any valid characters that should be decoded (valid characters E [1,5])
		while (pos < input.length()) {
			char curr_char = input.charAt(pos);

			// If we have a nonsense character, we skip past it 
			if (nonsense.contains(curr_char)) { 
				pos++;
				continue; 
			}

			// Otherwise, we have a valid character. We need to keep searching for valid characters until we reach an invalid
			// character again. After that, we will have the full encoded value to decode  
			String encoded_value = "";
			encoded_value += curr_char; 
			pos++; // go to next position
			while (pos < input.length() && encoded_value.length() < 3) {
				char next_char = input.charAt(pos);
				if (nonsense.contains(next_char)) break; // if we run into an invalid character, we're done here 
				encoded_value += next_char;
				pos++; 
			}
			
			// Now, we can decode that encoded value 
			if (encoded_value.length() == 2 || encoded_value.length() == 3) {
				int code = Integer.parseInt(encoded_value);
				if (decodeMap.containsKey(code)) {
					output += decodeMap.get(code);
				}
			}

		}
		
		return output;
	}
}
