package mcs;

import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
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
		SeqNode ptr = seqRear;
		SeqNode target;
		SeqNode after;
		
		do {
			if (ptr.next.seqValue == 27) {
				target = ptr.next;
				after = target.next;
				target.next = after.next;
				after.next = target;
				ptr.next = after;
				
				if (target == seqRear) {
					seqRear = after;
				}
				break;
			}
			ptr = ptr.next;
		} while (ptr != seqRear);
		// printList(seqRear);
	}
	
	/**
	 * Implements Step 2 - Flag B - on the sequence.
	 */
	void flagB() {
	    // COMPLETE THIS METHOD
		SeqNode ptr = seqRear;
		SeqNode target;
		SeqNode after;
		
		do {
			if (ptr.next.seqValue == 28) {
				target = ptr.next;
				after = target.next;
				target.next = after.next.next;
				after.next.next = target;
				ptr.next = after;
				
				if (target == seqRear) {
					seqRear = after;
				}
				break;
			}
			ptr = ptr.next;
		} while (ptr != seqRear);
	}
	
	/**
	 * Implements Step 3 - Triple Shift - on the sequence.
	 */
	void tripleShift() {
	    // COMPLETE THIS METHOD
		SeqNode head = seqRear.next;
		SeqNode tail = seqRear;
		SeqNode ptr = seqRear;
		SeqNode prevA = null;
		SeqNode nextB = null;
		SeqNode targetA = null;
		SeqNode targetB = null;
		
		do {
			if (ptr.next.seqValue == 27 || ptr.next.seqValue == 28) {
				if (targetA == null) {
					if (targetA == head) {
						targetA = ptr.next;
					} else {
						targetA = ptr.next;
						prevA = ptr;
					}
				} else if (targetB == null) {
					if (targetB == tail) {
						targetB = ptr.next;
					} else {
						targetB = ptr.next;
						nextB = ptr.next.next;
					}
					break;
				}
			}
			ptr = ptr.next;
		} while (true);
		
		if (targetA == head && targetB == tail) {
			// do nothing
		} else if (targetA == head) {
			seqRear = targetB;
		} else if (targetB == tail) {
			seqRear = prevA;
		} else {
			targetB.next = head;
			tail.next = targetA;
			prevA.next = nextB;
			seqRear = prevA;
		}
	}
	
	/**
	 * Implements Step 4 - Count Shift - on the sequence.
	 */
	void countShift() {		
	    // COMPLETE THIS METHOD
		int cnt = 1;
		int lastNum;
		SeqNode ptr = seqRear.next;
		SeqNode prevTarget = null;
		SeqNode target = null;
		SeqNode prevRear = null;
		
		if (seqRear.seqValue == 28) {
			lastNum = 27;
		} else {
			lastNum = seqRear.seqValue;
		}
		
		do {
			if (cnt == lastNum - 1) {
				prevTarget = ptr;
				target = ptr.next;
			}
			if (ptr.next == seqRear) {
				prevRear = ptr;
			}
			cnt += 1;
			ptr = ptr.next;
		} while (ptr != seqRear);
		
		prevTarget.next = target.next;
		prevRear.next = target;
		target.next = seqRear;
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
		int key = -1;
		
		do {
			flagA();
			flagB();
			tripleShift();
			countShift();
			// printList(seqRear);
			
			SeqNode ptr = seqRear.next;
			
			for (int i = 0; i < seqRear.seqValue - 1; i++) {
				ptr = ptr.next;
			}
			key = ptr.next.seqValue;
			
			if (key != 27 && key != 28) {
				//System.out.println(key);
				break;
			}
			
		} while (true);
		
	    return key;
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
		int key;
		String m = "";
		ArrayList<Integer> aPosition = new ArrayList<Integer>();
		ArrayList<Integer> keyStream = new ArrayList<Integer>();
		String encrypted = "";
		
		// get capital letter
		// get Alphabet 18 position
		for (int i = 0; i < message.length(); i++) {
			if (message.charAt(i) > 64 && message.charAt(i) < 91) {
				//System.out.println(message.charAt(i));
				m += message.charAt(i);
				aPosition.add((int)message.charAt(i) - 64);
			}
		}
		//System.out.println(m);
		//System.out.println(aPosition);
		
		for (int i = 0; i < aPosition.size(); i++) {
			keyStream.add(getKey());
			if (aPosition.get(i) + keyStream.get(i) > 26) {
				encrypted += (char)(aPosition.get(i) + keyStream.get(i) - 26 + 64);
			} else {
				encrypted += (char)(aPosition.get(i) + keyStream.get(i) + 64);
			}
		} 

		//System.out.println(aPosition.toString());
		//System.out.println(keyStream.toString());
		//System.out.println(encrypted);
		
		
	    return encrypted;
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
		int key;
		String m = "";
		ArrayList<Integer> aPosition = new ArrayList<Integer>();
		ArrayList<Integer> keyStream = new ArrayList<Integer>();
		String decrypted = "";
		
		// get capital letter
		// get Alphabet 18 position
		for (int i = 0; i < message.length(); i++) {
			if (message.charAt(i) > 64 && message.charAt(i) < 91) {
				//System.out.println(message.charAt(i));
				m += message.charAt(i);
				aPosition.add((int)message.charAt(i) - 64);
			}
		}
		//System.out.println(m);
		//System.out.println(aPosition);
		
		for (int i = 0; i < aPosition.size(); i++) {
			keyStream.add(getKey());
			if (aPosition.get(i) <= keyStream.get(i)) {
				decrypted += (char)(aPosition.get(i) + 26 - keyStream.get(i) + 64);
			} else {
				decrypted += (char)(aPosition.get(i) - keyStream.get(i) + 64);
			}
		} 
	    return decrypted;
	}
}
