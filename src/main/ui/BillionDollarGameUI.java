package ui;

import model.*;
import persistence.JsonQuestionBankReader;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

// The Billion-Dollar Question User Interface
public class BillionDollarGameUI {
    private static final String JSON_STORE = "./data/player.json";
    private static final String JSON_QUESTIONS = "./data/QuestionBank.json";
    private static final int BARRIER1 = 3;
    private static final int BARRIER2 = 8;
    private static final int MAX_LEVEL = 14;
    private Scanner input;
    private Player player;
    private PrizeMoney gameMoneyLevels = new PrizeMoney();
    private QuestionOrdinal gameOrdinals = new QuestionOrdinal();
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JsonQuestionBankReader jsonQuestionBankReader;

    // EFFECTS: constructs player and runs The Billion-Dollar game
    public BillionDollarGameUI() throws FileNotFoundException {
        input = new Scanner(System.in);
        player = new Player();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        jsonQuestionBankReader = new JsonQuestionBankReader(JSON_QUESTIONS);
        runBillionDollar();
    }

    // MODIFIES: this
    // EFFECTS: processes player input depending on player's position in game, any question except last question or
    //          on last question, then controls game depending on if player enters a correct answer, an incorrect
    //          answer, wants to quit or exit game or use lifeline sequentially.
    @SuppressWarnings("methodlength")
    private void runBillionDollar() {
        boolean keepGoing = true;
        String command = null;

        System.out.println("$$$$$$$$$$ The Billion-Dollar Question $$$$$$$$$$");
        presentGameMenu();

        while (keepGoing && player.getIsPlaying()) {
            command = input.next();
            command = command.toLowerCase();

            processPlayerCommand(command);

            while (player.getCanStartPlay()) {
                for (int i = 0; i < MAX_LEVEL; i++) {
                    if (i == player.getCurrentQuestionNumber()) {
                        player.setMoveOn("D");
                        if (i == 13) {
                            boolean finalStatus = controlFinalQuestion(player.getQuestionFromPlayerList(i));
                            if (finalStatus) {
                                playerHasWonGame();
                                player.stopPlayer();
                                player = new Player();
                                resetPlayerAfterGameHasEnded();
                                keepGoing = false;
                                break;
                            } else {
                                if (player.getMoveOn().equals("D")) {
                                    terminateGameByIncorrectAnswer();
                                    player.stopPlayer();
                                    player = new Player();
                                    resetPlayerAfterGameHasEnded();
                                    keepGoing = false;
                                    break;
                                } else if (player.getMoveOn().equals("S")) {
                                    boolean status = proceedWithLifelineNotAllowed(player.getQuestionFromPlayerList(i));
                                    if (status) {
                                        playerHasWonGame();
                                        player = new Player();
                                        resetPlayerAfterGameHasEnded();
                                    } else {
                                        if (player.getMoveOn().equals("Q")) {
                                            quitGame();
                                            player = new Player();
                                            resetPlayerAfterGameHasEnded();
                                        } else if (player.getMoveOn().equals("E")) {
                                            savePlayer();
                                        } else {
                                            terminateGameByIncorrectAnswer();
                                            player = new Player();
                                            resetPlayerAfterGameHasEnded();
                                        }
                                    }
                                    player.stopPlayer();
                                    keepGoing = false;
                                    break;
                                } else if (player.getMoveOn().equals("Q")) {
                                    quitGame();
                                    player.stopPlayer();
                                    player = new Player();
                                    resetPlayerAfterGameHasEnded();
                                    keepGoing = false;
                                    break;
                                } else if (player.getMoveOn().equals("E")) {
                                    savePlayer();
                                    player.stopPlayer();
                                    keepGoing = false;
                                    break;
                                }
                            }
                            break;
                        } else if (!manageQuestion(player.getQuestionFromPlayerList(i))) {
                            if (player.getMoveOn().equals("D")) {
                                terminateGameByIncorrectAnswer();
                                player.stopPlayer();
                                player = new Player();
                                resetPlayerAfterGameHasEnded();
                                keepGoing = false;
                                break;
                            } else if (player.getMoveOn().equals("S")) {
                                if (canSkipLifelineBeUsed()) {
                                    invokeSkipQuestionLifeline(player.getQuestionFromPlayerList(i));
                                    if (player.getMoveOn().equals("E")) {
                                        savePlayer();
                                        player.stopPlayer();
                                        keepGoing = false;
                                        break;
                                    }
                                } else {
                                    if (!proceedWithoutLifeline(player.getQuestionFromPlayerList(i))) {
                                        if (player.getMoveOn().equals("Q")) {
                                            quitGame();
                                            player.stopPlayer();
                                            player = new Player();
                                            resetPlayerAfterGameHasEnded();
                                            keepGoing = false;
                                            break;
                                        } else if (player.getMoveOn().equals("E")) {
                                            savePlayer();
                                            player.stopPlayer();
                                            keepGoing = false;
                                            break;
                                        } else {
                                            terminateGameByIncorrectAnswer();
                                            player.stopPlayer();
                                            player = new Player();
                                            resetPlayerAfterGameHasEnded();
                                            keepGoing = false;
                                            break;
                                        }
                                    }
                                }
                            } else if (player.getMoveOn().equals("Q")) {
                                quitGame();
                                player.stopPlayer();
                                player = new Player();
                                resetPlayerAfterGameHasEnded();
                                keepGoing = false;
                                break;
                            } else if (player.getMoveOn().equals("E")) {
                                savePlayer();
                                player.stopPlayer();
                                keepGoing = false;
                                break;
                            }
                        } else {
                            System.out.println("\nThat is the correct answer!");
                        }

                        if (!(player.getMoveOn().equals("L"))) {
                            updatePlayerStatus();
                            nextQuestionPresentation();
                            System.out.println("");
                        }
                    }
                }
            }
        }
        System.out.println("\nThank you for playing!");
    }

    // MODIFIES: this
    // EFFECTS: displays message warning player that lifeline cannot be used on final question, processes answer.
    //          if player wants to quit or exit sets respective moveOn and returns false; otherwise checks answer and
    //          returns true, else returns false
    private boolean proceedWithLifelineNotAllowed(Question q) {
        System.out.println("\nYou cannot use a lifeline on the final question!");
        System.out.println("\nPlease enter your answer:");
        System.out.println("--------------------------");
        String userAnswer = getPlayerAnswerAfterLifeline();

        if (userAnswer.equals("Q")) {
            player.setMoveOn("Q");
            return false;
        } else if (userAnswer.equals("E")) {
            player.setMoveOn("E");
            return false;
        } else if (checkUserAnswer(userAnswer, q)) {
            System.out.println("\nThat is the correct answer!");
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: processes player answer after lifeline has been used until they enter valid choice, if player wants to
    //          quit, or exit returns respective strings otherwise returns player answer by converting input
    private String getPlayerAnswerAfterLifeline() {
        String userAnswer;
        userAnswer = input.next();
        userAnswer = userAnswer.toUpperCase();

        while (userAnswer.equals("S")) {
            System.out.println("Please enter a valid choice:");
            System.out.println("-----------------------------");
            userAnswer = input.next();
            userAnswer = userAnswer.toUpperCase();
        }

        if (userAnswer.equals("Q")) {
            return "Q";
        } else if (userAnswer.equals("E")) {
            return "E";
        } else {
            return convertPlayerInputToOption(userAnswer);
        }
    }

    // EFFECTS: returns true if Skip The Question Lifeline can be used, false otherwise
    private boolean canSkipLifelineBeUsed() {
        if (!player.isSkipLifelineUsed()) {
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: asks for player to enter their presumed answer, indicates player cannot exit game without answering and
    //          keeps on checking until valid option is inputted, checks player answer and marks lifeline has been used,
    //          then displays message and invokes exiting process if player wants to exit, otherwise continues playing
    //          process
    private void invokeSkipQuestionLifeline(Question q) {
        System.out.println("\nBefore skipping the question, please enter your answer:");
        System.out.println("--------------------------------------------------------");
        String answerToCheck = getPlayerAnswer();

        if (answerToCheck.equals("E")) {
            while (answerToCheck.equals("E")) {
                System.out.println("\nYou must enter your answer before exiting the game:");
                System.out.println("--------------------------------------------------------");
                answerToCheck = getPlayerAnswer();
            }
            checkUserAnswerForSkip(answerToCheck, q);
            player.skipLifelineHasBeenUsed();
            player.setMoveOn("E");
        } else {
            checkUserAnswerForSkip(answerToCheck, q);
            player.skipLifelineHasBeenUsed();
            player.setMoveOn("D");
        }
    }

    // MODIFIES: this
    // EFFECTS: checks player answer after lifeline has been invoked and informs player whether their answer was correct
    private void checkUserAnswerForSkip(String answerToCheck, Question q) {
        if (checkUserAnswer(answerToCheck, q)) {
            System.out.println("\nThat was the correct answer!");
        } else {
            System.out.println("\nThat was an incorrect answer!");
            displayCorrectAnswer();
            player.increaseCurrentQuestionNumber();
        }
    }

    // MODIFIES: this
    // EFFECTS: informs player when they have already used their lifeline, asks them to choose again and
    //          then checks answer
    private boolean proceedWithoutLifeline(Question q) {
        System.out.println("\nYou have already used Skip The Question!");
        System.out.println("\nPlease enter your answer:");
        System.out.println("--------------------------");
        String userAnswer = getPlayerAnswerAfterLifeline();

        if (checkUserAnswer(userAnswer, q)) {
            System.out.println("\nThat is the correct answer!");
            return true;
        } else if (userAnswer.equals("Q") || userAnswer.equals("E")) {
            player.setMoveOn(userAnswer);
            return false;
        } else {
            return false;
        }

    }

    //EFFECTS: displays message indicating which question the player is advancing to
    private void nextQuestionPresentation() {
        int number;

        if (player.getCurrentQuestionNumber() == 0) {
            number = 1;
        } else if (player.isSkipLifelineUsed()) {
            number = player.getCurrentQuestionNumber();
        } else {
            number = player.getCurrentQuestionNumber();
        }

        int questionIndex = number;

        if (number - 1 < 13) {
            String ordinal = gameOrdinals.getOrdinal(questionIndex);
            System.out.println("\nMoving on...");
            System.out.println("\nHere is the " + ordinal + " question:");
        }
    }

    // EFFECTS: displays menu of options to player
    private void presentGameMenu() {
        System.out.println("\nMAIN MENU");
        System.out.println("Please choose from the following:");
        System.out.println("\tp --> Play a new game");
        System.out.println("\tr --> View the game rules");
        System.out.println("\te --> Save game to file");
        System.out.println("\tl --> Load game from file");
        System.out.println("\tq --> Quit game");
        System.out.println("-------------------------------------------------");
    }

    // MODIFIES: this
    // EFFECTS: processes player command to corresponding options in main menu and enters respective part of game,
    //          otherwise informs of invalid selection
    private void processPlayerCommand(String command) {
        if (command.equals("p")) {
            initializePlayer();
        } else if (command.equals("r")) {
            presentGameRules();
        } else if (command.equals("e")) {
            savePlayer();
        } else if (command.equals("l")) {
            loadGame();
            player.setMoveOn("L");
        } else if (command.equals("q")) {
            player.setGameStatus(false);
        } else {
            System.out.println("\nSelection not valid...");
            System.out.println("\nPlease select again:");
            System.out.println("---------------------");
        }
    }

    // MODIFIES: this
    // EFFECTS: prints welcome message, processes player name and greets player, asks for permission to add
    //          game questions to player's questionList until permission is given. Once questions are added, prints
    //          message informing player questions are ready, starts the game by allowing player into question stage
    private void initializePlayer() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");

        System.out.println("\n$$$$$$$$$$ Welcome to The Billion-Dollar Question $$$$$$$$$$");
        System.out.println("\nPlease enter your name:");
        System.out.println("------------------------");

        String playerName = input.next();
        player.setPlayerName(playerName);

        System.out.println("\nHello, " + player.getPlayerName() + "!");

        System.out.println("\nBefore playing, are you ready to choose your 14 game questions (Enter Y or N):");
        System.out.println("-------------------------------------------------------------------------------");

        String canAdd = input.next();
        canAdd = canAdd.toUpperCase();

        while (!canAdd.equals("Y")) {
            System.out.println("\nPlease choose again, you must give permission to add the questions:");
            System.out.println("--------------------------------------------------------------------");
            canAdd = input.next();
            canAdd = canAdd.toUpperCase();
        }

        createPlayerQuestionList();
        System.out.println("\nQUESTIONS ARE READY!");
        playANewGame();
        player.playerCanStartPlay();
    }

    // MODIFIES: this
    // EFFECTS: creates QuestionBank containing 70 questions (5 for each question), generates random number and picks
    //          the question from bank with that index and adds it to playerList, carries out process for all 14
    //          questions
    private void createPlayerQuestionList() {
        try {
            QuestionBank bank = jsonQuestionBankReader.read();

            for (int i = 0; i < 14; i++) {
                int num = randomQuestionNumberGenerator(1, 5);
                Question q = bank.getQuestionFromQuestionBank(i + 1, num);
                player.addQuestionToPlayerList(q);
            }

        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }

    }

    // EFFECTS: displays all rules of game
    private void presentGameRules() {
        presentGeneralRules();
        presentMoneyRules();
        presentLifelineRules();
        presentStoppageRules();
        presentControlsGuide();
    }

    // EFFECTS: displays basic rules of game
    private void presentGeneralRules() {
        System.out.println("\nGAME RULES:");
        System.out.println("\n- This game consists of 14 multiple-choice questions");
        System.out.println("- As the game progresses, the questions increase in terms of difficulty");
        System.out.println("- You must correctly answer each question to win the ultimate prize of $1,000,000,000");
        System.out.println("\nBelow is a list of the prize money levels in the game:");
        presentGameLevels();
    }

    // EFFECTS: displays all prize levels in game
    private void presentGameLevels() {
        for (int i = 0; i < MAX_LEVEL; i++) {
            int number = i + 1;

            if (number == 3 || number == 8) {
                System.out.println(" " + number + ". " + "*" + "$" + gameMoneyLevels.getPrizeMoneyForRules(i) + "*");
            } else {
                System.out.println(" " + number + ". " + "$" + gameMoneyLevels.getPrizeMoneyForRules(i));
            }
        }
    }

    // EFFECTS: displays rules of prize system in game
    private void presentMoneyRules() {
        System.out.println("\nBARRIERS");
        System.out.println("- TWO *prize barriers* can be unlocked by correctly answering the associated question");
        System.out.println("- The barriers ensure a base falling point, if you are to enter a incorrect answer later");
        System.out.println("- Failing to reach first barrier will result in no prize");
        System.out.println("- Reaching the first barrier ensures a prize of $10,000");
        System.out.println("- Reaching the second barrier ensures a prize of $1,000,000");
    }

    // EFFECTS: displays rules of using a lifeline in the game
    private void presentLifelineRules() {
        System.out.println("\nLIFELINES");
        System.out.println("- You have one lifeline, Skip The Question, that can be used once in the game");
        System.out.println("- If you would like to use Skip The Question, please enter S");
        System.out.println("- Skip The Question allows you to move on to the next question");
        System.out.println("- In order to move on, you must answer the question regardless of the outcome");
        System.out.println("- Note that Skip The Question CANNOT be used on the final question");
    }

    // EFFECTS: displays rules of stopping the game by quitting or exiting
    private void presentStoppageRules() {
        System.out.println("\nQUITTING");
        System.out.println("- If at any given question you wish to quit the game, please enter Q");
        System.out.println("- By quitting, you will win the prize of the most recently answered correct question");
        System.out.println("\nEXITING");
        System.out.println("- If at any given question, you wish to exit and save the game, please enter E");
        System.out.println("- By exiting, your current game progress will be saved");
    }

    // EFFECTS: displays in-game controls guide
    private void presentControlsGuide() {
        System.out.println("\nIN-GAME CONTROL GUIDE:");
        System.out.println("-> A: Option A");
        System.out.println("-> B: Option A");
        System.out.println("-> C: Option A");
        System.out.println("-> D: Option A");
        System.out.println("-> S: Skip-The-Question");
        System.out.println("-> Q: Quit Game");
        System.out.println("-> E: Exit Game");
        System.out.println("\nOnce you have the read the game rules, please select an option from the MAIN MENU:");
    }

    // EFFECTS: displays game starting message
    private void playANewGame() {
        System.out.println("\nGet ready, the game is starting in...");
        System.out.println("3");
        System.out.println("2");
        System.out.println("1");
        System.out.println("\nGood Luck!");
        System.out.println("\nHere is the first question:");
        System.out.println("");
    }

    // EFFECTS: manages question by presenting question, options, if player wants to skip, quit, or exit game sets
    //          moveOn of player to input and returns false, otherwise returns the checked player answer
    private boolean manageQuestion(Question q) {
        System.out.println(q.getQuestion());
        scrambleQuestionOptions(q);
        presentQuestionOptions(q);

        System.out.println("\nPlease enter your answer:");
        System.out.println("--------------------------");
        String answerToCheck = getPlayerAnswer();

        if (answerToCheck.equals("S") || answerToCheck.equals("Q") || answerToCheck.equals("E")) {
            player.setMoveOn(answerToCheck);
            return false;
        }
        return checkUserAnswer(answerToCheck, q);
    }

    // EFFECTS: displays message alerting player that they are on the final question, presents options for question,
    //          scans player input to check if player wants to skip, quit, or exit and sets moveOn to input and returns
    //          false, otherwise returns the checked player answer
    private boolean controlFinalQuestion(Question q) {
        System.out.println(q.getQuestion() + "?");
        scrambleQuestionOptions(q);
        presentQuestionOptions(q);

        System.out.println("\nPlease enter your answer:");
        System.out.println("--------------------------");
        String answerToCheck = getPlayerAnswer();

        if (answerToCheck.equals("S") || answerToCheck.equals("Q") || answerToCheck.equals("E")) {
            player.setMoveOn(answerToCheck);
            return false;
        } else {
            return checkUserAnswer(answerToCheck, q);
        }
    }

    // MODIFIES: this
    // EFFECTS: stores original options of question q in list, generates shuffled option order, scrambles original
    //          question options with respect to new shuffled option order
    private void scrambleQuestionOptions(Question q) {
        String orgA = q.getOption(1);
        String orgB = q.getOption(2);
        String orgC = q.getOption(3);
        String orgD = q.getOption(4);
        ArrayList<String> options = new ArrayList<>();

        options.add(orgA);
        options.add(orgB);
        options.add(orgC);
        options.add(orgD);

        ArrayList<Integer> opOrder = generateShuffledOptionOrder();

        for (int i = 0; i < 4; i++) {
            String chosenOp = options.get(opOrder.get(i));
            q.setOption(i + 1, chosenOp);
        }
    }

    // EFFECTS: creates a list containing the original order of question indexes (1 -> 2 -> 3 -> 4), generates random
    //          number, seed, and shuffles original order by seed then returns it (0 implies option A, 1 implies option
    //          B, 2 implies option C, 3 implies option D)
    private ArrayList<Integer> generateShuffledOptionOrder() {
        ArrayList<Integer> newOrder = new ArrayList<>();
        int o1 = 0;
        int o2 = 1;
        int o3 = 2;
        int o4 = 3;

        newOrder.add(o1);
        newOrder.add(o2);
        newOrder.add(o3);
        newOrder.add(o4);

        int seed = randomQuestionNumberGenerator(4,12);

        Collections.shuffle(newOrder, new Random(seed));

        return newOrder;
    }

    // EFFECTS: generates a random number the range of min and max, inclusive
    private int randomQuestionNumberGenerator(int min, int max) {
        int randomNum = ThreadLocalRandom.current().nextInt(min, max);
        return randomNum;
    }

    // EFFECTS: displays the four options for a given question q
    private void presentQuestionOptions(Question q) {
        for (int i = 1; i <= 4; i++) {
            String option;

            if (i == 1) {
                option = "A";
            } else if (i == 2) {
                option = "B";
            } else if (i == 3) {
                option = "C";
            } else {
                option = "D";
            }

            System.out.println(option + ". " + q.getOption(i));
        }
    }

    // MODIFIES: this
    // EFFECTS: checks player answer with the correct answer of a question, returns true if correct, otherwise false
    private boolean checkUserAnswer(String ans, Question q) {
        if (ans.equals(q.getCorrectAnswer())) {
            player.increaseCurrentQuestionNumber();
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: gets player's input, if player wants to skip, quit, or exit, returns answer, otherwise returns
    //          converted input to answer
    private String getPlayerAnswer() {
        String userAnswer;
        userAnswer = input.next();
        userAnswer = userAnswer.toUpperCase();

        if (userAnswer.equals("S") || userAnswer.equals("Q") || userAnswer.equals("E")) {
            return userAnswer;
        } else {
            return convertPlayerInputToOption(userAnswer);
        }
    }

    // EFFECTS: converts player input (A/B/C/D) to the corresponding answer from that option
    private String convertPlayerInputToOption(String a) {
        Question currentQuestion = player.getQuestionFromPlayerList(player.getCurrentQuestionNumber());

        if (a.equals("A")) {
            return currentQuestion.getOption(1);
        } else if (a.equals("B")) {
            return currentQuestion.getOption(2);
        } else if (a.equals("C")) {
            return currentQuestion.getOption(3);
        } else if (a.equals("D")) {
            return currentQuestion.getOption(4);
        } else {
            return "";
        }
    }

    // EFFECTS: displays message indicating prize won by player based on their prize barrier after entering an
    //          incorrect answer
    private void terminateGameByIncorrectAnswer() {
        int playerBarrier = player.getLatestPrizeBarrier();
        String prize1 = gameMoneyLevels.getPrizeMoneyForGameTermination(BARRIER1);
        String prize2 = gameMoneyLevels.getPrizeMoneyForGameTermination(BARRIER2);

        System.out.println("\nThat is the incorrect answer!");

        displayCorrectAnswer();

        System.out.println("\nThe game ends here...");

        if (playerBarrier == 0) {
            System.out.println("\nUnfortunately, you have not won any money!");
        } else if (playerBarrier == 1) {
            System.out.println("\nCongratulations, you have won " + "$" + prize1 + "!");
        } else {
            System.out.println("\nCongratulations, you have won " + "$" + prize2 + "!");
        }
    }

    // MODIFIES: this
    // EFFECTS: checks player's current question number and updates prize barrier based on currentQuestionNumber
    //          if player has passed the third question, barrier increases to one, barrier increases to 2
    //          after passing the eighth question
    private void updatePlayerStatus() {
        if (player.getCurrentQuestionNumber() == 3 || player.getCurrentQuestionNumber() == 8) {
            player.increasePrizeBarrier();
        }
    }

    // EFFECTS: displays message upon winning the game, indicating ultimate prize has been won
    private void playerHasWonGame() {
        String grandPrize = gameMoneyLevels.getPrizeMoneyForGameTermination(MAX_LEVEL);
        String name = player.getPlayerName();
        System.out.println("\nWINNNNNNNNEEEEERRRRRRRRRRRRRR!!!!!!!!!!!!!!!!");
        System.out.println("\nCongratulations " + name + ", you have won the ultimate prize of " + "$" + grandPrize);
    }

    // EFFECTS: displays message upon quitting game, indicating prize money won
    private void quitGame() {
        String name = player.getPlayerName();
        int previousQuestion = player.getCurrentQuestionNumber();
        String walkAwayPrize = gameMoneyLevels.getPrizeMoneyForGameTermination(previousQuestion);

        System.out.println("\nYou have decided to quit the game");
        displayCorrectAnswer();

        if (previousQuestion == 0) {
            System.out.println("\nUnfortunately, you have not won any money!");
        } else {
            System.out.println("\nCongratulations " + name + ", you have won " + "$" + walkAwayPrize + "!");
        }
    }

    // EFFECTS: saves the current player to file
    private void savePlayer() {
        try {
            jsonWriter.open();
            jsonWriter.write(player);
            jsonWriter.close();
            System.out.println("\nThe game has been saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: overwrites the current player with a new player after the game has finished
    private void resetPlayerAfterGameHasEnded() {
        try {
            jsonWriter.open();
            jsonWriter.write(player);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads player from file, if playerName is "NEW', indicates that the previous game has finished,
    //          asking the player to continue by selecting a valid option from the main menu (excluding e and l),
    //          otherwise loads player who was previously playing game from file
    private void loadGame() {
        String name;

        try {
            player = jsonReader.read();
            name = player.getPlayerName();

            if (name.equals("NEW")) {
                System.out.println("\nThe previous game has finished!");
                System.out.println("\nPlease select any option from the main menu, excluding e and l:");
                System.out.println("----------------------------------------------------------------");
            } else {
                int num = player.getCurrentQuestionNumber();

                System.out.println("\nThe game for " + name + " has been loaded from " + JSON_STORE);
                System.out.println("\nWelcome back, " + name + "!");
                System.out.println("\nYou are currently on the " + gameOrdinals.getOrdinal(num) + " question:");
                System.out.println("");
            }
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: displays a message indicating the correct option and answer for a given question q
    private void displayCorrectAnswer() {
        Question question = player.getQuestionFromPlayerList(player.getCurrentQuestionNumber());
        String option = findCorrectOption(question);
        System.out.println("\nThe correct answer was option " + option + ", " + question.getCorrectAnswer() + "!");
    }

    // EFFECTS: given a question, q, finds and returns the option corresponding to the correct answer
    private String findCorrectOption(Question q) {
        String correctOption = null;

        for (int i = 1; i <= 4; i++) {
            if (q.getOption(i).equals(q.getCorrectAnswer())) {
                if (i == 1) {
                    correctOption = "A";
                } else if (i == 2) {
                    correctOption = "B";
                } else if (i == 3) {
                    correctOption = "C";
                } else {
                    correctOption = "D";
                }
            }
        }
        return correctOption;
    }

}
