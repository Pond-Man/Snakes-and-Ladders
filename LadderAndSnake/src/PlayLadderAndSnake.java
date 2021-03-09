// -------------------------
// Assignment #1
// Question: Part 2
// Written by: Briac Cordelle (40167269)
// -------------------------

import java.util.Scanner;

/**
 * 
 * @author Briac 40167269
 * COMP 249
 * Assignment 1
 * 08/02/2021
 * 
 */
public class PlayLadderAndSnake {
	
	public static void main(String[] args) {
		
		Scanner kb = new Scanner(System.in);
		
		System.out.println("Hello there! Welcome to a high quality Snakes and Ladders game!");
		System.out.println("How many players will be playing today?");
		
		int players = 0;
	
		//check validity of input
		for(int i = 1; i <= 4; i++) {		
			
			players = kb.nextInt();
			
			if(players >= 2 && players <= 4) {
				break;
			}
			
			if(i == 4) {
				System.out.println("You failed to enter a number between 2 and 4 too many times.");
				System.out.println("Program will close now. Goodbye.");
				System.exit(0);
			}
			
			if(players > 4 || players < 2) {
				System.out.println("Bad attempt number " + i + ". Please enter a number between 2 and 4 inclusively.");
			}
		}
		
		LadderAndSnake m = new LadderAndSnake(players);
		
		//determine player names
		m.playerNames();
		//determine player order
		m.playerOrder();
		
		System.out.print("Player order determined: ");
		for(int i = 0; i < players - 1; i++) {
			System.out.print("Player " + m.getPlayerName(m.getOrderValue(i)) + ", ");
		}
		
		System.out.println("and finally, Player " + m.getPlayerName(m.getOrderValue(players - 1)) + ".");
		
		//start game
		m.play();
		
		kb.close();
		
	}

}
