package assignment01;

import java.util.*;

public class Version2 implements Code {

	private Map<Character, Integer> encodeMap;
    private Map<Integer, Character> decodeMap;
    private Random random;

	public Version2() {

		encodeMap = new TreeMap<>();
        decodeMap = new TreeMap<>();
        random = new Random();

        List<Character> chars = new ArrayList<>();

        for (char c = 'A'; c <= 'Z'; c++) {
            chars.add(c);
        }

        chars.add(' ');
        chars.add(',');
        chars.add('.');
        chars.add(':');
        chars.add(';');
        chars.add('?');
        chars.add('!');

        List<Integer> codes = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            for (int j = 1; j <= 7; j++) {
                codes.add(i * 10 + j);
            }
        }

        Collections.shuffle(codes, random);

        for (int i = 0; i < chars.size(); i++) {
            char ch = chars.get(i);
            int code = codes.get(i);

            encodeMap.put(ch, code);
            decodeMap.put(code, ch);
        }
	
	}
	
	@Override
	public String encode(String input) {
		String output = "";

		input = input.toUpperCase(); 

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            if (!encodeMap.containsKey(ch)) {
                continue; 
            }

            output += encodeMap.get(ch);

            output += (8 + random.nextInt(2));
            output += (8 + random.nextInt(2));

            if (random.nextInt(10) > 7) {
                output += "0";
            }
        }
		
		return output;
	}
	
	@Override
	public String decode(String input) {
		String output = "";
		int i = 0;

        while (i < input.length() - 1) {
            char c = input.charAt(i);

            if (c == '8' || c == '9' || c == '0') {
                i++;
                continue;
            }

            String value = "" + input.charAt(i) + input.charAt(i + 1);
            int code = Integer.parseInt(value);

            if (decodeMap.containsKey(code)) {
                output += decodeMap.get(code);
            }

            i += 2;
        }

		return output;
	}
}
