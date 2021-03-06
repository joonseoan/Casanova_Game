# [Casanova_Game]
  - This game is based on Sokoban, a type of puzzle game.
  - For more fun, the worker and the boxes are replaced with a casanova and ladies respectively.
  - The game is over once the casanova complets delivering a bunch of roeses to 5 ladies. 

# [Programming Language: JAVA]
  ## - Canvas
  ## - JavaFx
  ## - ArrayList
  ## - TreeSet
  ## - Java io
  ## - Multidimensional Array

# [Programming Summary]
### 1. Create a simple user list text file to store user IDs by using File and FileWriter and FileReader.
##### ![Login Page](/images/c1.PNG)

### 2. Memorize the userId in the main page. 
#####    - Blocks and playground are made by the multidimensional array.
#####    - Mesure time conumption to complete the game by using the Timer().
#####    - Record the winner who finishes the game in the the shortest time. The data is pulled out of the user list text file.
#####    - The timer data is stored on the user text file together with userIDs. 
#####    - TreeSet is used to match user IDs with time and to sort out the list in ascending order of time data.
##### ![Main game page](/images/c2.PNG)

### 3. Undo, Reset, Stop/Restart, and Exit the game
#####    - Undo simply by using checkup() and another multidimensional array
#####    - Reset simply by using another multidimensional array
#####    - Stop and restart by inheriting TimerTask
#####    - Exit in the mid of playing game and time user ID and time information is dismissed.
##### ![Buttons](/images/c3.PNG)

### 4. Show completion of the game
#####    - Show timer information if the lady elements in the array are replaced with the rose elements.
##### ![Message](/images/c4.PNG)

### 5. Record the best player
#####    - Record the player who consumes the minimum amount of time to finish the game by using TreeSet and Javaio.
##### ![Record](/images/c5.PNG)




  
  
 
