package mcs;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

/**
 * This class implements a mini cipher system.
 * 
 * @author RU NB CS112
 */
public class MiniCipherSys {
	
	/**
	 * Circular linked list that is the sequence of numbers for encryption
	 */
	SeqNode seqRear;
	
	/**
	 * Makes a randomized sequence of numbers for encryption. The sequence is 
	 * stored in a circular linked list, whose last node is pointed to by the field seqRear
	 */
	public void makeSeq() {
		// start with an array of 1..28 for easy randomizing
		int[] seqValues = new int[28];
		// assign values from 1 to 28
		for (int i=0; i < seqValues.length; i++) {
			seqValues[i] = i+1;
		}
		
		// randomize the numbers
		Random randgen = new Random();
 	        for (int i = 0; i < seqValues.length; i++) {
	            int other = randgen.nextInt(28);
	            int temp = seqValues[i];
	            seqValues[i] = seqValues[other];
	            seqValues[other] = temp;
	        }
	     
	    // create a circular linked list from this sequence and make seqRear point to its last node
	    SeqNode sn = new SeqNode();
	    sn.seqValue = seqValues[0];
	    sn.next = sn;
	    seqRear = sn;
	    for (int i=1; i < seqValues.length; i++) {
	    	sn = new SeqNode();
	    	sn.seqValue = seqValues[i];
	    	sn.next = seqRear.next;
	    	seqRear.next = sn;
	    	seqRear = sn;
	    }
	}
	
	/**
	 * Makes a circular linked list out of values read from scanner.
	 */
	public void makeSeq(Scanner scanner) 
	throws IOException {
		SeqNode sn = null;
		if (scanner.hasNextInt()) {
			sn = new SeqNode();
		    sn.seqValue = scanner.nextInt();
		    sn.next = sn;
		    seqRear = sn;
		}
		while (scanner.hasNextInt()) {
			sn = new SeqNode();
	    	sn.seqValue = scanner.nextInt();
	    	sn.next = seqRear.next;
	    	seqRear.next = sn;
	    	seqRear = sn;
		}
	}
	
	/**
	 * Implements Step 1 - Flag A - on the sequence.
	 */
	void flagA() {
	    // COMPLETE THIS METHOD
	}
	
	/**
	 * Implements Step 2 - Flag B - on the sequence.
	 */
	void flagB() {
	    // COMPLETE THIS METHOD
	}
	
	/**
	 * Implements Step 3 - Triple Shift - on the sequence.
	 */
	void tripleShift() {
	    // COMPLETE THIS METHOD
	}
	
	/**
	 * Implements Step 4 - Count Shift - on the sequence.
	 */
	void countShift() {		
	    // COMPLETE THIS METHOD
	}
	
	/**
	 * Gets a key. Calls the four steps - Flag A, Flag B, Triple Shift, Count Shift, then
	 * counts down based on the value of the first number and extracts the next number 
	 * as key. But if that value is 27 or 28, repeats the whole process (Flag A through Count Shift)
	 * on the latest (current) sequence, until a value less than or equal to 26 is found, 
	 * which is then returned.
	 * 
	 * @return Key between 1 and 26
	 */
	int getKey() {
	    // COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
	    return -1;
	}
	
	/**
	 * Utility method that prints a circular linked list, given its rear pointer
	 * 
	 * @param rear Rear pointer
	 */
	private static void printList(SeqNode rear) {
		if (rear == null) { 
			return;
		}
		System.out.print(rear.next.seqValue);
		SeqNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("," + ptr.seqValue);
		} while (ptr != rear);
		System.out.println("\n");
	}

	/**
	 * Encrypts a message, ignores all characters except upper case letters
	 * 
	 * @param message Message to be encrypted
	 * @return Encrypted message, a sequence of upper case letters only
	 */
	public String encrypt(String message) {	
	    // COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
	    return null;
	}
	
	/**
	 * Decrypts a message, which consists of upper case letters only
	 * 
	 * @param message Message to be decrypted
	 * @return Decrypted message, a sequence of upper case letters only
	 */
	public String decrypt(String message) {	
	    // COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
	    return null;
	}
}
