package BackEnd;
import GUI.*;

import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;
import java.util.ArrayList;


/**
 * 
 * The class is the basis for the two player game mode.
 * It sets player user names, allows each player to place
 * their ships within the board, and goes through the turn
 * based firing actions after each player is done initializing
 * their board.
 * 
 * @author Jackson, David, Nathanael, Ryan, and Sam
 *
 */
public class TwoPlayer
{
	public Scanner input = new Scanner(System.in);
	private boolean winner = false;
	private boolean player1turn = true;
	private boolean playerDoneTurn = false;
	private int gameMode;
	private int board;
	public Player player1 = new Player();	//makes new objects from Player class
	public Player player2 = new Player();
	private boolean gameLoaded = false;

	/**
	 * Setter for player's turn.
	 * @param turn
	 */
	public void setPlayer1Turn(boolean turn)
	{
		player1turn = turn;
	}

	/**
	 * Setter for board.
	 * @param b
	 */
	public void setBoard(int b)
	{
		board = b;
	}
	
	/**
	 * Getter for board.
	 * @return
	 */
	public int getBoard() 
	{
		return board;
	}
	
	/**
	 * Setter for array board size.
	 */
	public void setBoardSize()
	{
		player1.setBoardLength(board-1);
		player2.setBoardLength(board-1);
	}

	/**
	 * Getter for player's turn.
	 * @return player1Turn
	 */
	public boolean getPlayer1Turn()
	{
		return player1turn;
	}

	/**
	 * Getter for gameLoaded
	 * @return gameLoaded
	 */
	public boolean getGameLoaded()
	{
		return gameLoaded;
	}
	
	/**
	* Saves the current loaded game
	*/
	public void save()
	{
		String fileName = "twoplayer.txt";

		PrintWriter outputStream = null;
		try
		{
			outputStream = new PrintWriter(fileName);
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Error opening the file " + fileName);
			System.exit(0);
		}
		outputStream.println(board);
		for(int i = 0; i < board; i++)
		{
			for(int j = 0; j < board; j++)
			{
				outputStream.print(player1.playerBoard.grid[i][j].getHasShip() + " " + player1.playerBoard.grid[i][j].getBeenHit() + " ");
			}
			outputStream.println();
		}
		for(int i = 0; i < board;i++)
		{
			for(int j = 0; j < board; j++)
			{
				outputStream.print(player1.enemyBoard.grid[i][j].getHasShip() + " " + player1.enemyBoard.grid[i][j].getBeenHit() + " ");
			}
			outputStream.println();
		}


		for(int i = 0; i < board; i++)
		{
			for(int j = 0; j < board; j++)
			{
				outputStream.print(player2.playerBoard.grid[i][j].getHasShip() + " " + player2.playerBoard.grid[i][j].getBeenHit() + " ");
			}
			outputStream.println();
		}
		for(int i = 0; i < board; i++)
		{
			for(int j = 0; j < board; j++)
			{
				outputStream.print(player2.enemyBoard.grid[i][j].getHasShip() + " " + player2.enemyBoard.grid[i][j].getBeenHit() + " ");
			}
			outputStream.println();
		}

		outputStream.println(player1turn);
		
		outputStream.println(player1.shipsPlaced.size());
		outputStream.println(player2.shipsPlaced.size());

		for(int i = 0; i < player1.shipsPlaced.size(); i++)
		{
			outputStream.print(player1.shipsPlaced.get(i).getName() + " " + player1.shipsPlaced.get(i).getXPos() + " " + player1.shipsPlaced.get(i).getYPos() + " " + player1.shipsPlaced.get(i).getLife()+" "+player1.shipsPlaced.get(i).getVerticalOrientation()+" ");
		}
		outputStream.println();
		for(int i = 0; i < player2.shipsPlaced.size(); i++)
		{
			outputStream.print(player2.shipsPlaced.get(i).getName() + " " + player2.shipsPlaced.get(i).getXPos() + " " + player2.shipsPlaced.get(i).getYPos() + " " + player2.shipsPlaced.get(i).getLife()+" "+player2.shipsPlaced.get(i).getVerticalOrientation()+" ");
		}
		
		outputStream.println();
		
		outputStream.println(player1.getName());
		outputStream.println(player2.getName());
		
		outputStream.close();
	}
	
	/**
	 * Loads the last saved game status, by reading the save file.
	 */
	public void load()
	{
		String fileName = "twoplayer.txt";
		Scanner inputStream = null;

		try
		{
			inputStream = new Scanner(new File(fileName));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Error opening the file " + fileName);
			//System.exit(0);
			return;
		}
		board = inputStream.nextInt();
		inputStream.nextLine();

		choosingBoardSize(board);
		setBoardSize();
		player1.playerBoard.boardPopulate();	//populates boards
		player1.enemyBoard.boardPopulate();
		player2.playerBoard.boardPopulate();
		player2.enemyBoard.boardPopulate();

		//Creates all ships for both players
		player1.battleship.setName("Battleship");
		player1.battleship.setLength(4);
		
		player1.submarine.setName("Submarine");
		player1.submarine.setLength(3);

		player1.destroyer.setName("Destroyer");
		player1.destroyer.setLength(3);		

		player1.patrolBoat.setName("PatrolBoat");
		player1.patrolBoat.setLength(2);
		
		player1.nullShip.setName(null);

		player2.battleship.setName("Battleship");
		player2.battleship.setLength(4);

		player2.submarine.setName("Submarine");
		player2.submarine.setLength(3);
		
		player2.destroyer.setName("Destroyer");
		player2.destroyer.setLength(3);
		
		player2.patrolBoat.setName("PatrolBoat");
		player2.patrolBoat.setLength(2);
				
		player2.nullShip.setName(null);


		for(int i = 0; i < board; i++)
		{
			for(int j = 0; j < board; j++)
			{
				player1.playerBoard.grid[i][j].setHasShip(inputStream.nextBoolean());
				player1.playerBoard.grid[i][j].setBeenHit(inputStream.nextBoolean());
			}
			inputStream.nextLine();

		}
		for(int i = 0; i < board; i++)
		{
			for(int j = 0; j < board; j++)
			{
				player1.enemyBoard.grid[i][j].setHasShip(inputStream.nextBoolean());
				player1.enemyBoard.grid[i][j].setBeenHit(inputStream.nextBoolean());			
			}
			inputStream.nextLine();
		}


		for(int i = 0; i < board; i++)
		{
			for(int j = 0; j < board; j++)
			{
				player2.playerBoard.grid[i][j].setHasShip(inputStream.nextBoolean());
				player2.playerBoard.grid[i][j].setBeenHit(inputStream.nextBoolean());
			}
		}
		for(int i = 0; i < board; i++)
		{
			for(int j = 0; j < board; j++)
			{
				player2.enemyBoard.grid[i][j].setHasShip(inputStream.nextBoolean());
				player2.enemyBoard.grid[i][j].setBeenHit(inputStream.nextBoolean());			
			}
		}
		player1turn = inputStream.nextBoolean();
		
		int player1ShipSize = inputStream.nextInt();
		int player2ShipSize = inputStream.nextInt();
			
		for(int i = 0; i < player1ShipSize; i++)
		{
			String s = inputStream.next();
			if (s.equals("Battleship"))
			{
				player1.battleship.setName(s);
				player1.battleship.setXPos(inputStream.nextInt());
				player1.battleship.setYPos(inputStream.nextInt());
				player1.battleship.setLife(inputStream.nextInt());
				player1.battleship.setVerticalOrientation(inputStream.nextBoolean());
				
				player1.battleship.setXPositions(player1.battleship.getXPos());
				player1.battleship.setYPositions(player1.battleship.getYPos());
				
				player1.shipsPlaced.add(player1.battleship);
			}
			if (s.equals("Destroyer"))
			{
				player1.destroyer.setName(s);
				player1.destroyer.setXPos(inputStream.nextInt());
				player1.destroyer.setYPos(inputStream.nextInt());
				player1.destroyer.setLife(inputStream.nextInt());
				player1.destroyer.setVerticalOrientation(inputStream.nextBoolean());

				player1.destroyer.setXPositions(player1.destroyer.getXPos());
				player1.destroyer.setYPositions(player1.destroyer.getYPos());
				
				player1.shipsPlaced.add(player1.destroyer);
			}
			if (s.equals("Submarine"))
			{
				player1.submarine.setName(s);
				player1.submarine.setXPos(inputStream.nextInt());
				player1.submarine.setYPos(inputStream.nextInt());
				player1.submarine.setLife(inputStream.nextInt());
				player1.submarine.setVerticalOrientation(inputStream.nextBoolean());

				player1.submarine.setXPositions(player1.submarine.getXPos());
				player1.submarine.setYPositions(player1.submarine.getYPos());
				
				player1.shipsPlaced.add(player1.submarine);
			}
			if (s.equals("PatrolBoat"))
			{
				player1.patrolBoat.setName(s);
				player1.patrolBoat.setXPos(inputStream.nextInt());
				player1.patrolBoat.setYPos(inputStream.nextInt());
				player1.patrolBoat.setLife(inputStream.nextInt());
				player1.patrolBoat.setVerticalOrientation(inputStream.nextBoolean());

				player1.patrolBoat.setXPositions(player1.patrolBoat.getXPos());
				player1.patrolBoat.setYPositions(player1.patrolBoat.getYPos());
				
				player1.shipsPlaced.add(player1.patrolBoat);
			}
		}
		for(int i = 0; i < player2ShipSize; i++)
		{
			String s = inputStream.next();
			if (s.equals("Battleship"))
			{
				player2.battleship.setName(s);
				player2.battleship.setXPos(inputStream.nextInt());
				player2.battleship.setYPos(inputStream.nextInt());
				player2.battleship.setLife(inputStream.nextInt());
				player2.battleship.setVerticalOrientation(inputStream.nextBoolean());

				player2.battleship.setXPositions(player2.battleship.getXPos());
				player2.battleship.setYPositions(player2.battleship.getYPos());

				player2.shipsPlaced.add(player2.battleship);
			}
			if (s.equals("Destroyer"))
			{
				player2.destroyer.setName(s);
				player2.destroyer.setXPos(inputStream.nextInt());
				player2.destroyer.setYPos(inputStream.nextInt());
				player2.destroyer.setLife(inputStream.nextInt());
				player2.destroyer.setVerticalOrientation(inputStream.nextBoolean());

				player2.destroyer.setXPositions(player2.destroyer.getXPos());
				player2.destroyer.setYPositions(player2.destroyer.getYPos());
								
				player2.shipsPlaced.add(player2.destroyer);
			}
			if (s.equals("Submarine"))
			{
				player2.submarine.setName(s);
				player2.submarine.setXPos(inputStream.nextInt());
				player2.submarine.setYPos(inputStream.nextInt());
				player2.submarine.setLife(inputStream.nextInt());
				player2.submarine.setVerticalOrientation(inputStream.nextBoolean());
				
				player2.submarine.setXPositions(player2.submarine.getXPos());
				player2.submarine.setYPositions(player2.submarine.getYPos());
				
				player2.shipsPlaced.add(player2.submarine);
			}
			if (s.equals("PatrolBoat"))
			{
				player2.patrolBoat.setName(s);
				player2.patrolBoat.setXPos(inputStream.nextInt());
				player2.patrolBoat.setYPos(inputStream.nextInt());
				player2.patrolBoat.setLife(inputStream.nextInt());
				player2.patrolBoat.setVerticalOrientation(inputStream.nextBoolean());

				player2.patrolBoat.setXPositions(player2.patrolBoat.getXPos());
				player2.patrolBoat.setYPositions(player2.patrolBoat.getYPos());
				
				player2.shipsPlaced.add(player2.patrolBoat);
			}
		}
		inputStream.nextLine();
		player1.setName(inputStream.nextLine());
		player2.setName(inputStream.nextLine());		
		inputStream.close();
		gameLoaded = true;
	}


	/**
	 * This method initializes player user names, both players boards
	 * and the ships sizes and names for each player.
	 */
	public void twoPlayerSetup(int gameMode)
	{
		if(gameMode == 2)//Text based Two-Player
		{
			System.out.println("Would you like to load a game? y/n");
			String stringLoadGame = input.next();
			char loadGame = stringLoadGame.charAt(0);
			if(loadGame == 'y')
			{
				load();
			}
			else
			{
				System.out.println("Please input player 1's username: ");
				player1.setName(input.next());	
				System.out.println("Please input player 2's username: ");
				player2.setName(input.next());
				System.out.println("What size would you like the board?");
				board = input.nextInt();
			}
		}
		
		choosingBoardSize(board);
		setBoardSize();
		
		player1.playerBoard.boardPopulate();	//populates boards
		player1.enemyBoard.boardPopulate();
		player2.playerBoard.boardPopulate();
		player2.enemyBoard.boardPopulate();

		//Creates all ships for both players
		player1.battleship.setName("Battleship");
		player1.battleship.setLength(4);
		player1.battleship.setLife(4);

		player1.submarine.setName("Submarine");
		player1.submarine.setLength(3);
		player1.submarine.setLife(3);

		player1.destroyer.setName("Destroyer");
		player1.destroyer.setLength(3);		
		player1.destroyer.setLife(3);

		player1.patrolBoat.setName("PatrolBoat");
		player1.patrolBoat.setLength(2);
		player1.patrolBoat.setLife(2);

		player2.battleship.setName("Battleship");
		player2.battleship.setLength(4);
		player2.battleship.setLife(4);

		player2.submarine.setName("Submarine");
		player2.submarine.setLength(3);
		player2.submarine.setLife(3);

		player2.destroyer.setName("Destroyer");
		player2.destroyer.setLength(3);
		player2.destroyer.setLife(3);

		player2.patrolBoat.setName("PatrolBoat");
		player2.patrolBoat.setLength(2);
		player2.patrolBoat.setLife(2);
	}


    /**
	 * The method goes through the process of placing player
	 * ships on the board, and choosing which ship to place.
	 */
	public void shipPlacementSelection()
	{
		boolean player1ShipPlacementNotOver = true;
		boolean player2ShipPlacementNotOver = true;
		
		do //loops through player 1's turn to place boats
		{
			player1.display(); //display boards
			char player1ShipMove = '1';
			System.out.println("Please select a ship to place \n1. Battleship \n2. Submarine \n3. Destroyer \n4. Patrol Boat \n0. Done");
			int player1ShipSelector = input.nextInt(); //ship selection
			if(player1ShipSelector == 1)
				player1.current = player1.battleship;
			if(player1ShipSelector == 2)
				player1.current = player1.submarine;
			if(player1ShipSelector == 3)
				player1.current = player1.destroyer;
			if(player1ShipSelector == 4)
				player1.current = player1.patrolBoat;
			if(player1ShipSelector == 0) //Finish placing boats
			{
				player1ShipPlacementNotOver = false;
				break;
			}
			do //Loops through ship placement
			{	
				System.out.println("WASD to move, R to rotate and E to place, I to save");
				player1.getStats(); //prints ship stats
				String player1ShipMoveString = input.next();
				player1ShipMove = player1ShipMoveString.charAt(0);
				if(player1ShipMove == 'w')
					player1.shipPlacementMarkerMoveUp();
				if(player1ShipMove == 'a')
					player1.shipPlacementMarkerMoveLeft();
				if(player1ShipMove == 's')
					player1.shipPlacementMarkerMoveDown();
				if(player1ShipMove == 'd')
					player1.shipPlacementMarkerMoveRight();
				if(player1ShipMove == 'e') //places ship
				{
					if(player1.validShipPlacement(player1.current))
					{
						player1.shipPlacement(player1.current);
						player1.setXPos(0);
						player1.setYPos(0);
						player1ShipMove = '0';
					}
				}
				if(player1ShipMove == 'i')
					save();
				if(player1ShipMove == 'r') //rotates ship
					player1.rotate();
			}while(player1ShipMove != '0');//while player not done placing ship
			System.out.println(player1.current.getName());
		}while(player1ShipPlacementNotOver);//while player not done turn


		do //loops through player 2's turn to place boats
		{
			player2.display(); //display boards
			char player2ShipMove = '1';
			System.out.println("Please select a ship to place \n1. Battleship \n2. Submarine \n3. Destroyer \n4. Patrol Boat \n0. Done");
			int player2ShipSelector = input.nextInt(); //ship selection
			if(player2ShipSelector == 1)
				player2.current = player2.battleship;
			if(player2ShipSelector == 2)
				player2.current = player2.submarine;
			if(player2ShipSelector == 3)
				player2.current = player2.destroyer;
			if(player2ShipSelector == 4)
				player2.current = player2.patrolBoat;
			if(player2ShipSelector == 0)//finish placing boats
			{
				player2ShipPlacementNotOver = false;
				break;
			}
			do //loops through ship placement
			{
				System.out.println("WASD to move, R to rotate and E to move");
				player2.getStats();//prints ship stats
				String player2ShipMoveString = input.next();
				player2ShipMove = player2ShipMoveString.charAt(0);

				if(player2ShipMove == 'w')
					player2.shipPlacementMarkerMoveUp();
				if(player2ShipMove == 'a')
					player2.shipPlacementMarkerMoveLeft();
				if(player2ShipMove == 's')
					player2.shipPlacementMarkerMoveDown();
				if(player2ShipMove == 'd')
					player2.shipPlacementMarkerMoveRight();
				if(player2ShipMove == 'e')//places ship
				{
					if(player2.validShipPlacement(player2.current))
					{
						player2.shipPlacement(player2.current);
						player2.setXPos(0);
						player2.setYPos(0);
						player2ShipMove = '0';
					}
				}
				if(player2ShipMove == 'r')//rotates ship
					player2.rotate();
			}while(player2ShipMove != '0');//while player not done placing ship
			System.out.println(player2.current.getName());

		}while(player2ShipPlacementNotOver);//while player not done turn

	}
	
	/**
	 * Sets all the boardSizes to desired size
	 * @param x desired board size
	 */
	public void choosingBoardSize(int x)
	{
		player1.playerBoard.chooseBoardSize(x);
		player1.enemyBoard.chooseBoardSize(x);
		player2.playerBoard.chooseBoardSize(x);
		player2.enemyBoard.chooseBoardSize(x);
	}
	
	/**
	 * The method links the opposing players board with their enemy
	 * board for both players, after both players has placed all their
	 * ships. 
	 * 
	 */
	public void boardLinking()
	{
		for(int i = 0; i < board; i++)//iterates through all spaces in board and links the two players boards
		{
			for(int j = 0; j < board; j++)
			{
				player1.enemyBoard.grid[i][j].setHasShip(player2.playerBoard.grid[i][j].getHasShip());
				player1.enemyBoard.grid[i][j].setShipName(player2.playerBoard.grid[i][j].getShipName());
				player2.enemyBoard.grid[i][j].setHasShip(player1.playerBoard.grid[i][j].getHasShip());
				player2.enemyBoard.grid[i][j].setShipName(player1.playerBoard.grid[i][j].getShipName());
			}
		}
	}
	
    /**
	 * The method goes through the two players turns until there is a winner.
	 * Such as, moving position, firing weapon, and ending your turn.
	 * Also displays the coordinates of the selected spot. 
	 */
	public void playerTurn()
	{
		int player1ShipsSunk = 0;
		int player2ShipsSunk = 0;
		do
		{
			System.out.println("Press c to clear screen");
			String switchTurn = input.next();
			char switchTurnChar = switchTurn.charAt(0);
			if(switchTurnChar == 'c')
			{
				if(player1turn)	//Turn switcher
					player1turn = false;
				else if(!player1turn)
					player1turn = true;
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); //Clear Screen
			}
			if(player1turn) //Player turn changers
			{
				do //player 1 turn
				{
					//Prints helpful stats
					System.out.println("Selector is on: " + player1.getXPos() + " " + player1.getYPos());
					System.out.println("Press 0 to end your turn, wasd to move and f to fire, I to save");
					player1.display();
					String movePointerString = input.next();
					char movePointer = movePointerString.charAt(0);
					if(movePointer == 'w')
						player1.moveUp();
					if(movePointer == 'a')
						player1.moveLeft();
					if(movePointer == 's')
						player1.moveDown();
					if(movePointer == 'd')
						player1.moveRight();
					if(movePointer == '0')
						playerDoneTurn = true;
					if(movePointer == 'i')
						save();
					if(movePointer == 'f')//fire weapon
					{
						if(player1.hitChecker(player1.getXPos(), player1.getYPos()))
						{
							player1.enemyBoard.grid[player1.getXPos()][player1.getYPos()].setBeenHit(true);
							player1.display();

							if(player2.shipChecker(player1.getXPos(), player1.getYPos()))
								player1ShipsSunk++;
							//playerDoneTurn = true;
							break;
						}
						else
							System.out.println("You've already hit that spot");
					}
				}while(!playerDoneTurn);//while player 1 is not done their turn
				playerDoneTurn = false;
			}
			else
			{
				do //player 2 turn (for more specific comments, see player one's code above).
				{
					System.out.println("Selector is on: " + player2.getXPos() + " " + player2.getYPos());
					System.out.println("Press 0 to end your turn, wasd to move and f to fire, I to save");	//Someone can make this is it will convert to lower case so it doesn't matter if the user inputs captial letters or not
					player2.display();
					String movePointerString = input.next();
					char movePointer = movePointerString.charAt(0);
					if(movePointer == 'w')
						player2.moveUp();
					if(movePointer == 'a')
						player2.moveLeft();
					if(movePointer == 's')
						player2.moveDown();
					if(movePointer == 'd')
						player2.moveRight();
					if(movePointer == '0')
						playerDoneTurn = true;
					if(movePointer == 'i')
						save();
					if(movePointer == 'f')
					{
						if(player2.hitChecker(player2.getXPos(), player2.getYPos()))
						{
							player2.enemyBoard.grid[player2.getXPos()][player2.getYPos()].setBeenHit(true);
							player2.display();

							if(player1.shipChecker(player2.getXPos(), player2.getYPos()))
								player2ShipsSunk++;
							break;
						}
						else
							System.out.println("You've already hit that spot");
					}
				}while(!playerDoneTurn);//while player 2 is not done their turn
				playerDoneTurn = false;
			}
			if(player1ShipsSunk == 4 || player2ShipsSunk == 4)
				winner = true;
		}while(winner == false);//while there is no winner
		System.out.println(player1ShipsSunk == 4 ? player1.getName() : player2.getName() + " has won");//prints winner

	}
}
