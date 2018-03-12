// Brandon Cegelski
// CS-469 : Intro to Computer Security
// Assignment #3
// 11/29/2017
package main;

//import the required BigInteger package
import java.math.BigInteger;

//import the required SecureRandom package
import java.security.SecureRandom;

// import java.util.Random to choose a random long message
import java.util.Random;

//import scanner to allow user input into command prompt style program
import java.util.Scanner;


public class Start {

	public static void main(String[] args){
		// initialize a scanner to read the user's input
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;
		String userInput;
		
		// creating 2 to the 1000, utilzed later to check if the difference between p and q is greater than 2^1000
		int exponentThousand = 1000;
		BigInteger baseTwo = new BigInteger("2");
		BigInteger twoToThousandValue = baseTwo.pow(exponentThousand);
		
		// Variables used to calculate the time for encryption/decryption (initializing their values to 0 each)
		long encryptionStartTime = 0;
		long encryptionFinishTime = 0;
		long decryptionStartTime = 0;
		long decryptionFinishTime = 0;
		
		// initializing a big integer valued at 1 and 0 in order to be compared to values later
		BigInteger one = new BigInteger("1");
		BigInteger zero = new BigInteger("0");
		
		// Initializing the different variables used for encryption/decryption
		BigInteger c = new BigInteger("0");
		BigInteger m = new BigInteger("0");
		BigInteger n = new BigInteger("0");
		BigInteger d = new BigInteger("0");
		
		// Initializing the e variable and value
		BigInteger e = new BigInteger("65537");
		int eInt = 65537;
		
		// variable used to determine if the user selected to do an encryption and generate a message before decryption can occur
		boolean encryptionComplete = false;
		
		// variable used to be checked if the variables meet all the parameters of the assingned project during the initial setup of the values
		boolean p_q_valid_values = false;

		// continue to loop creating new random variable values until they meet the specificiations of the project
		// Normally, the correct values are randomly generated on the first time through this loop
		while(p_q_valid_values == false){
			
			// Generating the secure random numbers of p and q due to p and q being able to be utilized to determine the private key we will calculate later
			SecureRandom random = new SecureRandom();
			
			// random generation of a value of at least 2500 bits, 1000% certainty to be a prime number, and using the secureRandom variable, assigned as the p value
			BigInteger p = new BigInteger(2500, 1000, random);
		
			// random generation of a value of at least 1537 bits, 1000% certainty to be a prime number, and using the secureRandom variable, assigned as the q value
			BigInteger q = new BigInteger(1537, 1000, random);
		
			System.out.println("Displaying the values of the involved varables for the encryption/decryption\n");
		
			System.out.println("p:");
			System.out.println("p Value: " + p);
			System.out.println("Prime: " + p.isProbablePrime(1000));	// 1000 set for the certainty to be sure the BigInteger value is prime when checking
			System.out.println("Bit Length: " + p.bitLength());	// printing out to clarify the bit length of the p value is greater than the assigned 1536-bits
		
			System.out.println("\n");
		
			System.out.println("q:");
			System.out.println("q Value: " + q);
			System.out.println("Prime: " + q.isProbablePrime(1000));	// 1000 set for the certainty to be sure the BigInteger value is prime
			System.out.println("Bit Length: " + q.bitLength());	// printing out to clarify the bit length of the q value is greater than the assigned 1536-bits
		
			System.out.println("\n");
		
			// Calculating the absolute value difference between p and q to determine if the two values difference is greater than 2^1000
			System.out.println("Absolute Value of difference between two primes (p and q): ");
			BigInteger difference_p_q = p.subtract(q);
			BigInteger differenceAbs = difference_p_q.abs();
			// Displaying the absolute value difference calculation to show user
			System.out.println(p);
			System.out.println("-");
			System.out.println(q);
			System.out.println("=");
			System.out.println(differenceAbs);
		
			// comparing the calculated value of the absolute value of the difference between p and q with 2^1000 to determine if the randomly generated values of p and q meet the requirements of the assignment
			boolean q_p_difference_valid = false;
			int compareResult = differenceAbs.compareTo(twoToThousandValue);
			if(compareResult == 0){
				System.out.println("Difference between p and q is equal to 2^1000, p and q values are not valid");
				q_p_difference_valid = false;
			}else if(compareResult == 1){
				System.out.println("Difference between p and q is greater than 2^1000, p and q values are valid");
				q_p_difference_valid = true;
			}else{
				System.out.println("Difference between p and q is less than 2^1000, p and q values are not valid");
				q_p_difference_valid = false;
			}
		
			System.out.println("\n");

			System.out.println("e Value:");
			System.out.println(e);
		
			System.out.println("\n");
		
			// Begining the inital setup of the RSA while also checking to be sure the values generated meet the requirement to be able to move onto the encryption/decryption part of the program
			System.out.println("RSA Setup\n");
		
			// Variables to determine if the values allow to continue past setup
			boolean e_less_than_phiofN = false;
			boolean gcd_e_phiofN_equals_1 = false;
		
			// displaying to the user the part (a) calculation of the RSA setup in computing n
			System.out.println("a) Compute n = p * q\n");
			System.out.println(p);
			System.out.println("*");
			System.out.println(q);
			System.out.println("=");
			n = p.multiply(q);	// performing the operation to multiply p and q in order to calculate n
			System.out.println(n);
		
			System.out.println("\n");
		
			// displaying to the user the part (b) calculation of the RSA setup in calculating phi of n
			System.out.println("b) Choose 0 < e < phi(n) such that gcd(e, phi(n)) = 1\n");
		
			System.out.println("Calculate phi(n) = (p-1) * (q-1)");
			BigInteger p_minus_1 = p.subtract(one);	// calculate first part of equation in p minus 1
			System.out.println(p_minus_1);
			System.out.println("*");
			BigInteger q_minus_1 = q.subtract(one);
			System.out.println(q_minus_1);	// calculate second part of the equation in q - 1
			System.out.println("=");
			BigInteger phiofN = p_minus_1.multiply(q_minus_1);	// multiplying the values (p-1)(q-1)
			System.out.println(phiofN);
			
			System.out.println("\n");
		
			// Checking and displaying to the user that the calculated phi of n value is larger than e
			System.out.println("Check 0 < e < phi(n)");
			BigInteger phiofN_minus_e = phiofN.subtract(e);	// perform subtraction operation to compare e and phi of n
			int phiofN_compare_e_Result = phiofN_minus_e.compareTo(zero);	// compare the result of difference between e and phi of n to zero to determine if phi of n is larger than n
			
			// determining if phi of n is larger than e via the calculated value above to determine if we are able to continue to encryption/decryption based off this RSA requirement
			if(phiofN_compare_e_Result == 0){
				System.out.println("Phi of N equal to e, not valid");
				e_less_than_phiofN = false;
			}else if(phiofN_compare_e_Result == 1){
				System.out.println("Phi of N greater than e, valid\n");
				System.out.println("0");
				System.out.println("<");
				System.out.println(e);
				System.out.println("<");
				System.out.println(phiofN);
				e_less_than_phiofN = true;
			}else{
				System.out.println("Phi of N less than e, not valid");
				e_less_than_phiofN = false;
			}
		
			System.out.println("\n");
		
			// Checking if the greatest common divisor of e and phi of n is 1
			System.out.println("Check gcd(e,phi(n)) = 1");
			BigInteger gcd_e_phiOfN = e.gcd(phiofN);	// performing the calculation to check if e and phi of n have a greatest common divisor of 1
		
			int gcd_compare_one_Result = gcd_e_phiOfN.compareTo(one);
		
			// determining if the result from the above calculation of checking if e and phi of n have a greatest common divisor of 1 in order to continue to encryption/decryption from RSA setup
			if(gcd_compare_one_Result == 0){
				System.out.println("Greatest Common Divisor of e and Phi of N equals 1, valid\n");
				System.out.println("gcd(");
				System.out.println(e);
				System.out.println(",");
				System.out.println(phiofN);
				System.out.println(") = 1");
				gcd_e_phiofN_equals_1 = true;
			}else if(gcd_compare_one_Result == 1){
				System.out.println("Greatest Common Divisor of e and Phi of N greater than 1, not valid");
				gcd_e_phiofN_equals_1 = false;
			}else{
				System.out.println("Greatest Common Divisor of e and Phi of N less than 1, not valid");
				gcd_e_phiofN_equals_1 = false;
			}
		
			System.out.println("\n");
		
			// Calcuate the value of d, the private key and ensure that the value is legal for RSA to continue to encryption/decryption
			System.out.println("c) Find 0<d<phi(n) such that ed = 1 (mod phi(n)) (Using Qin's Algorithm)");
		
			d = e.modInverse(phiofN);	// performing the calculation of d, the private key 
			
			boolean d_larger_than_zero = false;
			boolean d_less_than_phiOfN = false;
			
			// checking if d, the private key, value is larger than 0
			int compare_d_0_result = d.compareTo(zero);
			if(compare_d_0_result == 0){
				System.out.println("d is equal to zero, d is invalid");
				d_larger_than_zero = false;
			}else if(compare_d_0_result > 0){
				System.out.println("d is greater than zero, d is valid");
				d_larger_than_zero = true;
			}else{
				System.out.println("d is less than zero, d is invalid");
				d_larger_than_zero = false;
			}
			
			// checking if d, the private key, value is less than phi of n
			int compare_d_phiOfN_result = d.compareTo(phiofN);
			if(compare_d_phiOfN_result == 0){
				System.out.println("d is equal to phi(n), d is invalid");
				d_less_than_phiOfN = false;
			}else if(compare_d_phiOfN_result > 0){
				System.out.println("d is greater than phi(n), d is invalid");
				d_less_than_phiOfN = false;
			}else{
				System.out.println("d is less than phi(n), d is valid");
				d_less_than_phiOfN = true;
			}
			
			// displaying the calculated d value, private key, to the screen for the user to view
			System.out.println("d value:");
			System.out.println(d);
		
			System.out.println("\n");
		
			// Check if the setup variable values are valid to continue to the encryption/decryption step
			if(gcd_e_phiofN_equals_1 == true && e_less_than_phiofN == true){
			
				// displaying the public key
				System.out.println("The public key is:\n");
				System.out.println("(");
				System.out.println(e);
				System.out.println(",");
				System.out.println(n);
				System.out.println(")");
			
				System.out.println("\n");
			
			}else{
				if(e_less_than_phiofN == false){
					System.out.println("False: 0 < e < phi(n)");
				}
				if(gcd_e_phiofN_equals_1 == false){
					System.out.println("False: gcd(e,phi(n)) = 1");
				}
				System.out.println("Encryption Not Available");
			}
			
			// Checking every requirement for values to ensure they are correct. If they are, all the values fit the requirements of the assignment and RSA requirements
			if(p.isProbablePrime(1000) && q.isProbablePrime(1000) && q_p_difference_valid == true && e_less_than_phiofN == true && gcd_e_phiofN_equals_1 && d_larger_than_zero == true && d_less_than_phiOfN == true){
				p_q_valid_values = true;
			}
		}
		
		// loop to continuously prompt the user for an action of encryption, decryption, or to exit the program
		while(exit == false){
			// Reset the encryption time stamps
			encryptionStartTime = 0;
			encryptionFinishTime = 0;
			
			// Reset the decryption time stamps
			decryptionStartTime = 0;
			decryptionFinishTime = 0;
			
			// Prompting the user for input on what action should be performed
			// One cannot decrypt a message without first encrypting and generating the message randomly
			System.out.println("Please input action (Encrypt, Decrypt, Exit): ");
			userInput = scanner.nextLine();	// acquiring the user input on what action to perfrom
			userInput = userInput.toUpperCase();
			
			// if the user selected to encrypt
			if(userInput.equals("ENCRYPT")){
				
				// Encryption
				System.out.println("\nGenerating a random message (m) to encrypt");
				
				boolean m_coprime_n = false;
				// continue to loop until we randomly generate a large message that is coprime to N (N = p*q)
				// Typically, only takes one generation of a message to find one that is coprime to N
				while(m_coprime_n == false){
					Random rd = new Random();
					m = new BigInteger(2000, rd);	// randomly generating a message to be encrypted of bit length 2000 to meet the assingment requirement of a large number for the message used
					
					// ensuring the message (m) value is coprime to the value of N (N = p*q)
					BigInteger gcd_m_n = m.gcd(n);
					
					int gcd_compare_one_Result_m_n = gcd_m_n.compareTo(one);
					
					// determining if the message is coprime with n inorder to continue to the encryption
					if(gcd_compare_one_Result_m_n == 0){
						System.out.println("Greatest Common Divisor of m and N equals 1, valid\n");
						m_coprime_n = true;
					}else if(gcd_compare_one_Result_m_n == 1){
						System.out.println("Greatest Common Divisor of m and N is greater than 1, not valid");
						m_coprime_n = false;
					}else{
						System.out.println("Greatest Common Divisor of m and N is less than 1, not valid");
						m_coprime_n = false;
					}
				}
				
				// displaying message to be encrypted to the screen for the user
				System.out.println("Message to be encrypted (m):");
				System.out.println(m);
				
				System.out.println("\nThe formula used to encrypt the message is: C = M^e mod(n)\n");
				
				// Displaying the calculation of m^e for the user to view the encryption process of RSA
				System.out.println("Calculate m^e:");
				System.out.println(m);
				System.out.println("^");
				System.out.println(e);
				System.out.println("=");
				System.out.println("The result takes too long to print\n");
				
				// Displaying the whole calculation of m^e mod(n) for the user. This equation takes a very long time to print to the screen so we just display the variable names instead of the values
				System.out.println("Calculate m^e mod(n): Printing the equation will take too long\n");
				
				// performing the encryption
				c = m.modPow(e, n);
				
				// displaying the results from the encryption to the screen
				System.out.println("Ciphertext Generated (C) = " + c);
				
				System.out.println("\n");
				
				// Testing how long a single encryption takes. We must do this multiple times so that we do not just generate 0 due to the speed of the encryption
				// Set the time the encryption started
				encryptionStartTime = System.currentTimeMillis();
				
				// perform the encryption 50 times
				int i = 0;
				while(i < 50){
					BigInteger c_time_test = m.modPow(e, n); 	// performing the encryption
					i++;
				}
				
				// Set the time the encryption finished
				encryptionFinishTime = System.currentTimeMillis();
				
				// calculate the total time used to encrypt the message 50 times
				double encryptionTotalTime = encryptionFinishTime - encryptionStartTime;
				
				// print the total time of encryption the message 50 times in milliseconds
				System.out.println("Total time to complete 50 encryptions: " + encryptionTotalTime + " milliseconds");
				
				// calculate how long it took to encrypt the message once, of the 50 times
				double encryptionSingleTime = encryptionTotalTime / 50;
				
				// print the time to encrypt the message once to the screen in milliseconds
				System.out.println("Time to complete a single encryption: " + encryptionSingleTime + " milliseconds");
				
				System.out.println("\nEncryption Finished");
				System.out.println("\n");
				
				encryptionComplete = true;
				// This marks the end of the RSA encryption process
				
				// if the user selected to decrpyt
			}else if(userInput.equals("DECRYPT")){
				if(encryptionComplete == true){

					// Decryption
					System.out.println("\nCiphertext to be decrypted (C):");
					System.out.println(c);
					
					System.out.println("\nThe formula used to decrypt the message is: M = C^d mod(n)\n");
					
					// Display the formula of C^d to the user so that they are able to see the calculation required for decryption
					System.out.println("Calculate C^d:");
					System.out.println(c);
					System.out.println("^");
					System.out.println(d);
					System.out.println("=");
					System.out.println("The result takes too long to print\n");
					
					// Displaying the whole calculation of C^d mod(n) for the user. This equation takes a very long time to print to the screen so we just display the variable names instead of the values
					System.out.println("Calculate C^d mod(n): Printing the equation will take too long\n");
					
					BigInteger m_calculated = c.modPow(d, n);	// performing the decryption of the ciphertext to plaintext (message)
					
					System.out.println("Message (m) decrypted from the cyphertext (c) = " + m_calculated + "\n");	// displaying the results from the decryption to the screen for the user
				
					// Testing how long a single decryption takes. We must do this multiple times so that we do not just generate 0 due to the speed of the decryption
					// Set the time the decryption started
					decryptionStartTime = System.currentTimeMillis();
				
					// performing the decryption 50 times
					int i = 0;
					while(i < 50){
						BigInteger m_calculated_time_test = c.modPow(d, n); 
						i++;
					}
				
					// Set the time the decryption Finished
					decryptionFinishTime = System.currentTimeMillis();
					
					// calculating the total time needed to decrypt the ciphertext 50 times
					double decrytionTotalTime = decryptionFinishTime - decryptionStartTime;
					
					// display for the user the total time in milliseconds for 50 decryptions of the ciphertext
					System.out.println("Total time to complete 50 decryptions: " + decrytionTotalTime + " milliseconds");
					
					// calculating the time needed for a single decryption out of the 50
					double decryptionsSingleTime = decrytionTotalTime / 50;
					
					// display for the user the time in milliseconds for a single decryption of the ciphertext
					System.out.println("Time to complete a single decryption: " + decryptionsSingleTime + " milliseconds");
					
					// checking to ensure the decrypted ciphertext plaintext (message m) equals the orinal message generated during the encryption step
					int difference_m_mcalculated = m_calculated.compareTo(m);

					if(difference_m_mcalculated == 0){
						// displaying the original message value, ciphertext of the encrypted message, and finally the decrypted plaintext from the ciphertext
						System.out.println("\nDemonstrating the decryption was successful (original m equal to decrypted m value): ");
						System.out.println("m original value:");
						System.out.println(m);
						System.out.println("\nc value:");
						System.out.println(c);
						System.out.println("\nDecrypted m value:");
						System.out.println(m_calculated);
					}else{
						System.out.println("The decryption of ciphertext was not successful as the original message does not match the decrypted message");
					}
					
					System.out.println("\nEncryption Finished");
					System.out.println("\n");
					
					// end of the decryption process
					
					// if encryption has not been ran yet, need to do so first before decrypting a encrypted value
				}else{
					System.out.println("\nPlease encrypt a message first to obtain a Ciphertext\n\n");
				}
				
				// if the user selected to exit the program
			}else if(userInput.equals("EXIT")){
				System.out.println("\nExiting");
				exit = true;
				
				// not a valid input by the user, prompt them again
			}else{
				System.out.println("Could not read the input, try again");
			}
		}
	}
}
