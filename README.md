# AndroidCardWar
A simple android application of card war ("Milhama" in Hebrew)

**Note**:

You need to enter your Google map Key to the /values/google_maps_api.xml file
in order to save your location and see the map.

Game logic: 

1. Menu View:

    The game title appers on the top with a button under to start the game when pressed.
			
2. Game View:

    Both players icon, scors and cards appear, at the start of the game each
    player gets half of the amount of cards from the overall deck.   
    The game is automatic, every 3 seconds both players drow a card
     (progress bar shows on the top view
    ).  
    In case of a draw the game will continue and each player will get 5
     cards until there is a winner.  
    When there is a winner the game will swich to the winner view.	
		
3. Winner View:

    Shows the image of the winner and it's score.   
    Under the players icon there's an EditText object so the player can
     save their name and score to enter to the winners list.      
     In the bottom of the view there are 3 buttons: save - to save score
     , start a new game and back to menu.

4. Score View:
   
   Contains a list view of the 10 top winners (from highest to lowest).
Contains a Google Map view.
When clicking on a selcted winner, their location will appear in the map
 view with a marker and a title that contains: the winnerr's name and their
  score.  
       

Please Notice the following:

- We couldn't make the music to continue when rotating the screen, so when you roatat the screen the music stops and starts again.
	
