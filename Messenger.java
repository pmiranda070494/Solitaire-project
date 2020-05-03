package solitaire;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Messenger {
	public static void main(String[] args) 
	throws IOException {
		
		//System.out.println("5,26,22,7,11,[27],10,20,1,18,23,21,19,12,[28],9,16,24,13,25,6,14,3,8,17,15,2,4".replaceAll(",", " ").replaceAll("\\[", "").replaceAll("\\]", ""));
		
		Solitaire ss = new Solitaire();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//System.out.print("Enter deck file name => ");
		
		
		
		Scanner sc = new Scanner(new File("deck.txt"));
		ss.makeDeck(sc);
//		ss.jokerA();
//		ss.jokerB();
//		ss.tripleCut();
		ss.countCut();
//		ss.getKey();
		
		
//		System.out.print("Encrypt or decrypt? (e/d), press return to quit => ");
//		String inp = br.readLine();
//		if (inp.length() == 0) {
//			System.exit(0);
//		}
//		char ed = inp.charAt(0);
//		System.out.print("Enter message => ");
//		String message = br.readLine();
//		if (ed == 'e') {
//			System.out.println("Encrypted message: " + ss.encrypt(message));
//		} else {
//			System.out.println("Decrypted message: " + ss.decrypt(message));
//		}
	}
}
