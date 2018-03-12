Instructions on how to compile and run the program:

1) Unzip
2) Naviage to the directory src\main inside the unzipped directory and set the path variable to the current jdk
	Ex: set path=%path%;C:\Program Files\Java\jdk1.8.0_121\bin
3) Sitting in the main directory of the project directory and enter: javac Start.java
4) Navigate to the src directory of the project directory and enter: java main.Start
5) After these steps you will see the program launch, this may take some time due to printing and calculating BigInteger values right from the start

Instructions on running the program:

1) Displayed first upon the program running and with no user input will be the p, q values. They will print out that they are prime and larger than the specified bit minimum requirement
2) The e value will be displayed
3) The RSA setup will be displayed with all the calculations needed to calculate additional values
4) The public and private keys will be printed to the screen
5) The previous steps will continue to execute in a loop until all the values meet the requirements for RSA and the assignment (These steps typically only execute once)
6) Once the above steps have been completed successfully, you will be prompted with the option for encrypt/decrypt/exit. Type the action you would like (not case sensative)
7) Encryption should be ran first because inside of encryption the random message is generated and encrypted. As the program runs the steps and printouts of values will be dispalyed for the user for the whole encryption process.
8) After encryption has finished you will be prompted again for an input for an action
9) Choose decryption and let the program run to display the decryption process with all the required steps and printouts for the user.
10) Once completed you could run encryption again to generate a new message and continue selecting encrypt/decrypt until finished. At this point when prompted for action type 'exit' and you will stop the program execution