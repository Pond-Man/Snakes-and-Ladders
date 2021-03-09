// -------------------------
// Assignment #1
// Question: Part 1
// Written by: Briac Cordelle (40167269)
// -------------------------


import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 * @author Briac 40167269
 * COMP 249
 * Assignment 1
 * 08/02/2021
 * 
 */
public class LadderAndSnake {
			
	private int players;
	private String[] playerNames;
	private int[] order;
	
	private Map<Integer, Integer> snake;
	private Map<Integer, Integer> ladder;
	
	Scanner kb = new Scanner(System.in);

	
	/** Sets number of players to specified number
	 * 
	 * @param players number of players
	 * Initializes String array for player names
	 * Initializes int array used to determine order
	 * 
	 */
	public LadderAndSnake(int players) {
		this.setPlayers(players);
		this.playerNames = new String[players];
		this.order = new int[players];
	}
	
	/** Gets the number of players
	 * 
	 * @return number of players
	 */
	public int getPlayers() {
		return players;
	}
	
	/** Private method to set number of players
	 * 
	 * @param players
	 */
	private void setPlayers(int players) {
		this.players = players;
	}
	
	/** gets specified player name
	 * 
	 * @param index of array
	 * @return name at index of string array
	 */
	public String getPlayerName(int index) {
		return playerNames[index];
	}
	
	/** sets player name at specified index
	 * 
	 * @param index
	 * @param name
	 */
	private void setPlayerName(int index, String name) {
		this.playerNames[index] = name;
	}
	
	/** gets specified order value
	 * 
	 * @param index The player's position
	 * @return int corresponding to player value
	 */
	public int getOrderValue(int index) {
		return order[index];
	}
	
	/** sets order value at specified index
	 * 
	 * @param index The player's position
	 * @param value Player value
	 */
	private void setOrderValue(int index, int value) {
		this.order[index] = value;
	}
	
	/** flips a dice
	 * 
	 * @return int between 1-6 inclusively
	 * roll a coin flip a dice
	 */
	public int flipDice() {
		Random r = new Random();
		return r.nextInt(6) + 1;
	}
	
	/** ask user for player names and store in name[]
	 * 
	 */
	public void playerNames() {
				
		for(int j = 0; j < players; j++) {		
			System.out.println("Please enter the name of player " + (j + 1) + ":");
			String name = kb.next();
			setPlayerName(j, name);
		}
	}
	
	/** find the index at which the highest value of an array is 
	 * 
	 * @param a int[] that we need the maxindex of 
	 * @return maxIndex int value of index at which array a has its maximum value
	 */
	public int maxIndex(int[] a) {
		int max = a[0];
		int maxIndex = 0;
		for(int i = 0; i < a.length; i++) {
			if(max < a[i]) {
				maxIndex = i;
				max = a[i];
			}
		}
			
		return maxIndex;
	}
	
	/** find the index at which the lowest value of an array is 
	 * 
	 * @param a int[] that we need the minindex of 
	 * @return minIndex int value of index at which array a has its minimum value
	 */
	public int minIndex(int[] a) {
		int min = a[0];
		int minIndex = 0;
		for(int i = 0; i < a.length; i++) {
			if(a[i] != 0 && min > a[i]) {
				minIndex = i;
				min = a[i];
			}
		}
		
		return minIndex;
	}
	
	/** Count ties at max value
	 * 
	 * @param val Array with values of dice rolls
	 * @return tied Boolean value set to true if there is a tie and false if there isn't
	 */
	public boolean maxTies(int[] val) {
		boolean tied = false;
		
		int maxIndex = maxIndex(val);
		for(int i = 0; i < players; i++) {
			if((maxIndex != i && val[maxIndex] == val[i])) {
				tied = true;
			}
		}
		
		return tied;
		
	}
	
	/** Count ties at min value
	 * 
	 * @param val Array with values of dice rolls
	 * @return tied Boolean value set to true if there is a tie and false if there isn't
	 */
	public boolean minTies(int[] val) {
		boolean tied = false;
		
		int minIndex = minIndex(val);
		for(int i = 0; i < players; i++) {
			if((minIndex != i && val[minIndex] == val[i])) {
				tied = true;
			}
		}
		
		return tied;
	}
	
	/** Count ties 
	 * 
	 * @param val Array with values of dice rolls
	 * @return tied Int value of number of ties
	 * used to determine how to handle tie
	 */
	public int ties(int[] val) {
		int tied = 0;
		
		for(int i = 0; i < players; i++) {
			for(int j = players - 1; j > i; j--) {
				if(val[i] == val[j]) {
					tied++;
				}
			}
		}
		return tied;
	}
	
	/** breaks a tie between two numbers
	 * 
	 * @param val Int array with values of dice rolls
	 */
	public void tieBreaker(int[] val) {
		for(int i = 0; i < players; i++) {
			for(int j = players - 1; j > i; j--) {
				while(val[i] != 0 && val[j] != 0 && val[i] == val[j]) {
					
					System.out.println("There is a tie between Player " + playerNames[i] + " and Player " + playerNames[j] + ". Attempting to break tie...");
					
					val[i] = flipDice();
					val[j] = flipDice();
					
					System.out.println("Player " + playerNames[i] + " got a dice value of " + val[i]);
					System.out.println("Player " + playerNames[j] + " got a dice value of " + val[j]);
					
					//if val[i] == val[j] the while loop will run again
					//in case the two players that tied tie again
					if((val[i] != val[j])){
						if(val[i] > val[j]) {
							setOrderValue(players - 2, i);
							setOrderValue(players - 1, j);
						}else {
							setOrderValue(players - 2, j);
							setOrderValue(players - 1, i);
						}
						//we don't want the new values to be compared to the old
						val[i] = 0;
						val[j] = 0;
						
					}
				}
			}
		}
	}
	
	/** determine how to handle tie type based on
	 * information from maxTies, minTies and ties
	 * @param val Int array with dice roll values
	 */
	public void determineTies(int[] val) {
		
		boolean maxTied = maxTies(val); //ties between two high numbers
		boolean minTied = minTies(val); //ties between two low numbers
		int ties = ties(val); //number of ties
		
		int maxIndex = maxIndex(val); //find index of max value
		int minIndex = minIndex(val); //find index of min value
		
		switch(players) {
		case(4):
			
			if(maxTied) {
				setOrderValue(1, minIndex); //set lowest value to second
				val[minIndex] = 0; //remove lowest value from val
				setOrderValue(0, minIndex(val)); //set new lowest value to first
				tieBreaker(val);
			}else if(minTied) {
				setOrderValue(0, maxIndex); //set highest value to first
				val[maxIndex] = 0; //remove highest value from val
				setOrderValue(1, maxIndex(val)); //set new highest value to second
				tieBreaker(val);
			}else if(ties == 1) { 
				setOrderValue(0, maxIndex);
				setOrderValue(1, minIndex);
				tieBreaker(val);
			}else {
				System.out.println("No ties this time. Game will start shortly.");
				for(int i = 0; i < players; i++) {
					setOrderValue(i, maxIndex(val));
					val[maxIndex(val)] = 0;
				}
			}
		
			break;
		
		case(3):
			if(maxTied) {
				setOrderValue(0, minIndex); //set lowest value to first
				tieBreaker(val);
			}else if(minTied) {
				setOrderValue(0, maxIndex); //set highest value to first
				tieBreaker(val);
			}else {
				System.out.println("No ties this time. Game will start shortly.");
				for(int i = 0; i < players; i++) {
					setOrderValue(i, maxIndex(val));
					val[maxIndex(val)] = 0;
				}
			}
			
			break;
			
		default:
			if(ties == 1) {
				tieBreaker(val);
			}else {
				setOrderValue(0, maxIndex);
				setOrderValue(1, minIndex);
			}
		}
	}
	
	/** fills val array with dice flip values
	 * 
	 */
	public void playerOrder() {
		
		int[] val = new int[players];
		
		for(int i = 0; i < players; i++) {
			val[i] = flipDice();
			System.out.println("Player " + playerNames[i] + " got a dice value " + val[i]);
		}
		
		int ties = ties(val); //number of ties
		
		if(ties >= 2) {
			System.out.println("Too many ties. Rerolling.");
			playerOrder();
		}else {
			determineTies(val);
		}
		
	}
	
	/** checks position after dice roll to see if 
	 * out of bounds or if a player has won
	 * 
	 * @param curPos Int value for position of player
	 * @param roll Dice roll value
	 * @return Int value based on either out of bounds, victory or normal roll
	 */
	public int checkPos(int curPos, int roll) {
		if(curPos + roll > 100) {
			return 2; //out of bounds
		}else if (curPos + roll == 100) {
			return 1; //winner
		}else {
			return 0; //normal roll
		}		
	}
	
	/** Place snakes and ladders on hashmap
	 * 
	 */
	public void initializeSnakesLadders() {
	    snake = new HashMap <>();
	    ladder = new HashMap <>();
	    
	    snake.put(98, 78);
	    snake.put(97, 76);
	    snake.put(95, 24);
	    snake.put(93, 68);
	   	snake.put(79, 19);
	   	snake.put(64, 60);
	   	snake.put(48, 30);
	   	snake.put(16, 6);
	   	
	   	ladder.put(1, 38);
	   	ladder.put(4, 14);
	   	ladder.put(9, 31);
    	ladder.put(21, 42);
    	ladder.put(28, 84);
    	ladder.put(36, 44);
    	ladder.put(51, 67);
    	ladder.put(71, 91);
    	ladder.put(80, 100);
	
	}

	/** Initializaes gameboard and then plays through a snakes and ladders game
	 * until one player wins or the user stops playing
	 * 
	 */
	public void play() {

		initializeSnakesLadders();
		
		Player[] playersArray = new Player[players];
		for(int i = 0; i < players; i++) {
			playersArray[i] = new Player();
		}
		
		String str, name;
		int roll = 0;
		int curPos = 0;
		int newPos = 0;
		
		do {
			
			for(int i = 0; i < players; i++) {
				
				name = getPlayerName(order[i]);
				
				System.out.println("\nIt's Player " + name + "'s turn!");
				
				curPos = playersArray[order[i]].getPosition();
				roll = flipDice();
				
				System.out.print("Player " + name + " rolled a " + roll);
				
				//check if win, over 100 or normal roll
				switch(checkPos(curPos, roll)) {
				//over 100
				case(2): 
					
					newPos = 200 - (curPos + roll);
				
					System.out.println(". You rolled over 100, tough luck. You are back to square " + newPos);
					
					playersArray[order[i]].setPosition(newPos);
					break;
				//win
				case(1):
					
					System.out.println("\n\n" + name + " has won! Congratulations to " + name + "!\n");
					System.exit(0);
					break;
				//normal roll
				case(0):
					
					newPos = curPos + roll;
				
					System.out.println(". Player " + name + " has moved to square " + newPos);
					
					playersArray[order[i]].setPosition(newPos);
					break;
				//keeping the compiler happy	
				default:
					System.out.println("This shouldn't run");
				}
				
				curPos = newPos; //set curPos to the updated position
				
				//check square for ladder or snake
				if(snake.containsKey(curPos)) {
					
					System.out.println("There is a snake on this square.");
					
					newPos = snake.get(curPos);
					playersArray[order[i]].setPosition(newPos);
					
					System.out.println("Player " + name + " has been moved down to " + newPos);
					
				}else if(ladder.containsKey(curPos)) {
					
					System.out.println("There is a ladder on this square.");
					
					newPos = ladder.get(curPos);
					playersArray[order[i]].setPosition(newPos);
					
					System.out.println("Player " + name + " has been moved up to " + newPos);
					
					//check victory as one ladder goes to 100
					if(newPos == 100) {
						System.out.println("\n\n" + name + " has won! Congratulations to " + name + "!\n");
						System.exit(0);
					}
				
				}else {
					System.out.println("No ladders or snakes on this square.");
				}
			}
			
			System.out.println("\nEnd of this turn. Would you like to continue playing?");
			System.out.print("Enter \" Q \" to stop playing this game, anything else to continue: " );
			str = kb.next();
			
		}while(!("Q".equalsIgnoreCase(str)));
		
		kb.close();
		System.exit(0);
		
	}
	
	











	
	
	
		
		
}
	
