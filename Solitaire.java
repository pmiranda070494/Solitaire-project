package solitaire;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

/**
 * This class implements a simplified version of Bruce Schneier's Solitaire
 * Encryption algorithm.
 * 
 * @author RU NB CS112
 */
public class Solitaire {

	/**
	 * Circular linked list that is the deck of cards for encryption
	 */
	CardNode deckRear;

	/**
	 * Makes a shuffled deck of cards for encryption. The deck is stored in a
	 * circular linked list, whose last node is pointed to by the field deckRear
	 */
	public void makeDeck() {
		// start with an array of 1..28 for easy shuffling
		int[] cardValues = new int[28];
		// assign values from 1 to 28
		for (int i = 0; i < cardValues.length; i++) {
			cardValues[i] = i + 1;
		}

		// shuffle the cards
		Random randgen = new Random();
		for (int i = 0; i < cardValues.length; i++) {
			int other = randgen.nextInt(28);
			int temp = cardValues[i];
			cardValues[i] = cardValues[other];
			cardValues[other] = temp;
		}

		// create a circular linked list from this deck and make deckRear point
		// to its last node
		CardNode cn = new CardNode();
		cn.cardValue = cardValues[0];
		cn.next = cn;
		deckRear = cn;
		for (int i = 1; i < cardValues.length; i++) {
			cn = new CardNode();
			cn.cardValue = cardValues[i];
			cn.next = deckRear.next;
			deckRear.next = cn;
			deckRear = cn;
		}
	}

	/**
	 * Makes a circular linked list deck out of values read from scanner.
	 */
	public void makeDeck(Scanner scanner) throws IOException {
		CardNode cn = null;
		if (scanner.hasNextInt()) {
			cn = new CardNode();
			cn.cardValue = scanner.nextInt();
			cn.next = cn;
			deckRear = cn;
		}
		while (scanner.hasNextInt()) {
			cn = new CardNode();
			cn.cardValue = scanner.nextInt();
			cn.next = deckRear.next;
			deckRear.next = cn;
			deckRear = cn;
		}
	}

	/**
	 * Implements Step 1 - Joker A - on the deck.
	 */
	void jokerA() {
		shuffleJoker(27);
		printList(deckRear);

		// COMPLETE THIS METHOD
	}
	
	private void shuffleJoker(int joker) {
		if (deckRear.cardValue == joker) {
			CardNode tmp = deckRear;
			while (tmp.next != deckRear)
				tmp = tmp.next;
			CardNode jokerNode = deckRear;
			tmp.next = deckRear.next;
			deckRear = deckRear.next;
			jokerNode.next = deckRear.next;
			deckRear.next = jokerNode;
			return;
		}
		CardNode tmp = deckRear.next;
		CardNode prev = deckRear;
		while (tmp.cardValue != joker) {
			tmp = tmp.next;
			prev = prev.next;
		}
		if (tmp.next == deckRear) {
			CardNode jokerNode = tmp;
			jokerNode.next = deckRear.next;
			prev.next = deckRear;
			deckRear.next = jokerNode;
			deckRear = jokerNode;
			return;
		}
		CardNode jokerNode = tmp;
		tmp = tmp.next;
		prev.next = tmp;
		jokerNode.next = tmp.next;
		tmp.next = jokerNode;

	}

	/**
	 * Implements Step 2 - Joker B - on the deck.
	 */
	void jokerB() {
		shuffleJoker(28);
		shuffleJoker(28);
		printList(deckRear);
	}

	/**
	 * Implements Step 3 - Triple Cut - on the deck.
	 */
	void tripleCut() {
		CardNode front = deckRear.next;
		CardNode prev = deckRear;
		CardNode curr = deckRear.next;
		if (deckRear.cardValue == 27 || deckRear.cardValue == 28) {
			for (CardNode tmp = curr; tmp.next != deckRear; tmp = tmp.next) {
				if (tmp.cardValue == 27 || tmp.cardValue == 28) {
				    deckRear = prev;
					return;
				}
				prev = prev.next;
			}
		}
		if (front.cardValue == 27 || front.cardValue == 28) {
			curr = curr.next;
			for (CardNode tmp = curr; tmp.next != deckRear; tmp = tmp.next) {
				if (tmp.cardValue == 27 || tmp.cardValue == 28) {
					deckRear = tmp;
					return;
				}
				curr = curr.next;
			}
		}
		for (CardNode tmp = curr; tmp.next != deckRear; tmp = tmp.next) {
			if (tmp.cardValue == 27 || tmp.cardValue == 28)
				break;
			curr = curr.next;
			prev = prev.next;
		}
		curr = curr.next;
		for (CardNode tmp = curr; tmp.next != deckRear; tmp = tmp.next) {
			if (tmp.cardValue == 27 || tmp.cardValue == 28)
				break;
			curr = curr.next;
		}
		deckRear.next = prev.next;
		prev.next = curr.next;
		curr.next = front;
		deckRear = prev;
		printList(deckRear);

		// COMPLETE THIS METHOD
	}

	/**
	 * Implements Step 4 - Count Cut - on the deck.
	 */
	void countCut() {
		int count = deckRear.cardValue;
		CardNode beforeLast = deckRear.next;
		while (beforeLast.next != deckRear)
			beforeLast = beforeLast.next;
		beforeLast.next = deckRear.next;
		if (deckRear.cardValue == 28 || deckRear.cardValue == 27) {
			count = 27;
		}
		CardNode tmp = deckRear.next;
		for (int i = 1; i < count; i++) {
			tmp = tmp.next;
		}
		deckRear.next = tmp.next;
		tmp.next = deckRear;
		printList(deckRear);
		// COMPLETE THIS METHOD
	}

	// 
	/**
	 * Gets a key. Calls the four steps - Joker A, Joker B, Triple Cut, Count
	 * Cut, then counts down based on the value of the first card and extracts
	 * the next card value as key. But if that value is 27 or 28, repeats the
	 * whole process (Joker A through Count Cut) on the latest (current) deck,
	 * until a value less than or equal to 26 is found, which is then returned.
	 * 
	 * @return Key between 1 and 26
	 */
	private void doEverything() {
		jokerA();
		//printList(deckRear);
		jokerB();
		//printList(deckRear);
		tripleCut();
		//printList(deckRear);
		countCut();
		//printList(deckRear);
		//System.out.println();
	}

	int getKey() {
		doEverything();
		CardNode curr = null;
		CardNode tmp = null;
		while (curr==null) {
			tmp = deckRear.next;
			int count = tmp.cardValue;
			if (tmp.cardValue == 28)
				count = 27;
			for (int i = 0; i < count; i++) {
				tmp = tmp.next;
			}
			if (tmp.cardValue == 27 || tmp.cardValue == 28) {
				doEverything();
			} else {
				curr = tmp;
			}
			System.out.println(curr.cardValue);
		}
		return curr.cardValue;
		 //COMPLETE THIS METHOD
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
	}

	/**
	 * Utility method that prints a circular linked list, given its rear pointer
	 * 
	 * @param rear
	 *            Rear pointer
	 */
	// REVERT THIS BACK TO ITS ORIGINAL FORM
	private static void printList(CardNode rear) {
		if (rear == null) { 
			return;
		}
		System.out.print(rear.next.cardValue);
		CardNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("," + ptr.cardValue);
		} while (ptr != rear);
		System.out.println("\n");
	}

	/**
	 * Encrypts a message, ignores all characters except upper case letters
	 * 
	 * @param message
	 *            Message to be encrypted
	 * @return Encrypted message, a sequence of upper case letters only
	 */
	public String encrypt(String message) {
		char c;
		String secretMsg = "";
		message = message.toUpperCase();		
		for (int i = 0; i < message.length(); i++) {
			c = message.charAt(i);
			if (c >= 'A' && c <= 'Z') {
				int key = getKey();
				int val = (int) (c - 64 + key);
				if (val > 26)
					val -= 26;
				c = (char) ('A' + val - 1);
				secretMsg += c;
			}
		}
		// COMPLETE THIS METHOD
		return secretMsg;
	}

	/**
	 * Decrypts a message, which consists of upper case letters only
	 * 
	 * @param message
	 *            Message to be decrypted
	 * @return Decrypted message, a sequence of upper case letters only
	 */
	public String decrypt(String message) {
		int val;
		char c;
		String actualMsg = "";
		message = message.toUpperCase();
		for (int i = 0; i < message.length(); i++) {
			c = message.charAt(i);
			if (c >= 'A' && c <= 'Z') {
				int key = getKey();
				val = (int) c - 64;
				if (val < key)
					val += 26;
				val -= key;
				c = (char) ('A' + val - 1);
				actualMsg += c;
			}
		}
		// COMPLETE THIS METHOD
		return actualMsg;
	}
}
