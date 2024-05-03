## *The Billion-Dollar Question*

The Billion-Dollar question is a knowledge-based game that allows players to test their knowledge by asking them a 
series of questions, giving them a chance to win a fictional cash prize. The game consists of **14 multiple-choice 
questions** that originate from various domains in general knowledge, such as sports, world politics, geography, 
entertainment, etc. A player must answer each question correctly in order to walk away with the **ultimate prize** of 
**1 billion dollars**. Below is a list of the prize money levels in the game:

1. $1000
2. $2000
3. **$10,000**
4. $20,000
5. $40,000
6. $100,000
7. $200,000
8. **$1,000,000**
9. $5,000,000
10. $10,000,000
11. $20,000,000
12. $40,000,000
13. $200,000,000
14. $1,000,000,000

Starting from $1000, the total winnable prize money associated with each question incrementally increases 
to 1 billion dollars. However, as the game progresses towards the ultimate prize, the questions increase in terms of
difficulty. If a player gets a question wrong, then the game will end, and the player will drop to the latest prize 
barrier that has been unlocked. 

While playing this game, two prize barriers can be unlocked, one at the **third question**, $10,000, and another at 
the **eighth question**, $1,000,000. Once each question is answered correctly, the barriers will be unlocked, ensuring 
a base falling point for the player if they were to enter an incorrect answer to one of the subsequent questions. If a 
player enters a wrong answer and does not reach the first barrier of $10,000, then they will **not win** a cash prize. 
Similarly, reaching the $10,000 barrier will **guarantee** the player a cash prize of $10,000 and $1,000,000 for the 
second barrier. 

It is to be noted that a player can **quit** the game at any time. In the entirety of this game, a player will have 
**one lifeline** that can be used in any order at any point of the game, except for the final question of 1 billion 
dollars, to advance to the subsequent question:

- *Skip The Question: allows the player to move to the next question by skipping the current question.*

In its entirety, The Billion-Dollar Question is a gaming-oriented application that is aimed at all people. 
Whether it be children, teenagers, adults, or elders, this game is intended for everyone to play. It allows the 
everyday person to test their depth of knowledge in multiple domains while having a fun game experience. In addition, 
it provides a sense of motivation and satisfaction with a chance to win a cash prize, fictionally.

This project is of particular interest to me for multiple reasons. Firstly, it has always been my dream to create 
a proper gaming application that everyone can enjoy, not just a specific demographic. When I think about games, I 
believe that providing players an extraordinary experience allows them to connect with themselves. 
"The Billion-Dollar Question" provides everyone with an opportunity to connect and test themselves while having fun at
the same time. Since childhood, I have always been an avid fan of game shows. The inspiration for this game, 
"Who Wants To Be A Millionaire," has been my all-time favourite gaming show, one that I have always had a strong 
attachment to, with a desire that one day I could design my own version of it.

## User Stories
- As a user, I want to be able to view the rules of the game before playing
- As a user, I want to be able to play a new game by choosing randomized questions and being able to add all of them to 
a list that is stored in Player.
- As a user, I want to be able to advance in the game by entering my answer for each of the questions in the game.
- As a user, I want to be able to use Skip The Question if I do not know the answer to a question, if I haven't used it.
- As a user, I want to be able to quit a game whenever I want and walk away an appropriate cash prize. 

- As a user, when I choose to exit the game, I want to be able to save my game progress to file if and only if I do 
  not want to quit the game.
- As a user, when I start the game I want to be given the option to load my game from file, given that the game was not 
  finished and was saved. 

## *Phase 4: Task 2*

Here is a sample of the events that occur when The Billion-Dollar Question runs, more specifically in the scenario 
when a player successfully completes the game:

Thu Nov 25 20:25:27 PST 2021
<br>Player entered name: Ayush

Thu Nov 25 20:25:29 PST 2021
<br>Ayush has chosen 14 questions

Thu Nov 25 20:25:31 PST 2021
<br>Ayush passed Question #1

Thu Nov 25 20:25:37 PST 2021
<br>Ayush passed Question #2

Thu Nov 25 20:25:38 PST 2021
<br>Ayush passed Question #3

Thu Nov 25 20:25:38 PST 2021
<br>Ayush has crossed Prize Barrier #1

Thu Nov 25 20:25:46 PST 2021
<br>Ayush passed Question #4

Thu Nov 25 20:25:52 PST 2021
<br>Ayush passed Question #5

Thu Nov 25 20:25:53 PST 2021
<br>Ayush passed Question #6

Thu Nov 25 20:25:54 PST 2021
<br>Ayush passed Question #7

Thu Nov 25 20:25:56 PST 2021
<br>Ayush passed Question #8

Thu Nov 25 20:25:56 PST 2021
<br>Ayush has crossed Prize Barrier #2

Thu Nov 25 20:25:59 PST 2021
<br>Ayush passed Question #9

Thu Nov 25 20:25:59 PST 2021
<br>Ayush passed Question #10

Thu Nov 25 20:26:02 PST 2021
<br>Ayush passed Question #11

Thu Nov 25 20:26:03 PST 2021
<br>Ayush passed Question #12

Thu Nov 25 20:26:05 PST 2021
<br>Ayush passed Question #13

Thu Nov 25 20:26:10 PST 2021
<br>Ayush passed Question #14

Thu Nov 25 20:26:10 PST 2021
<br>Ayush successfully completed the game

Thu Nov 25 20:26:10 PST 2021
<br>The current game has ended


## *Phase 4: Task 3*

If given more time to work on the project, I would implement the following refactoring to improve the structure of my 
gaming application:

- Abstract methods in Player class that are used to set, get, and modify corresponding fields of player to reduce 
repetition.
- Where applicable, replace the old Player methods with the newly abstracted methods.


- Restructure BillionDollarGameGUI to make it more modular by breaking up components of different phases of the game in 
the GUI into separate classes to improve cohesion.
- Create separate classes and possible subclasses applicable for different parts of the game such as the main menu, 
buttons, prize pyramid, question area, question options, information panel, quitting screen, winning screen to 
improve cohesion, readability, coupling.
- Abstract the classes as mentioned above, more specifically those that involve the creation of multiple objects
such as buttons for playing the game, to make them less repetitive.


- Transfer methods from BillionDollarGUI correspond to question randomization and analysis to the QuestionClass, such 
as findCorrectOption and scrambleQuestionOptions.


- Simplify QuestionBank constructor by constructing each object using a list of lists instead of 14 different fields 
to reduce repetitiveness and decrease coupling by allowing the game to be easily expandable for more questions.
- Condense QuestionBank getters and setters to reduce duplication per the new constructor as mentioned above.
- Align QuestionBank reader methods with the new constructor as described above.








