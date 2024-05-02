package gui;

import model.*;
import model.Event;
import persistence.JsonQuestionBankReader;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

// The Billion-Dollar Question GUI
public class BillionDollarGameGUI implements ActionListener {
    private static final String JSON_STORE = "./data/player.json";
    private static final String JSON_QUESTIONS = "./data/QuestionBank.json";
    private static final int BARRIER1 = 3;
    private static final int BARRIER2 = 8;
    private static final int MAX_LEVEL = 14;
    private Player player;
    private PrizeMoney gameMoneyLevels = new PrizeMoney();
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JsonQuestionBankReader jsonQuestionBankReader;
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;
    private JFrame mainFrame;
    private Container con;
    private JPanel titleNamePanel;
    private JPanel beginGamePanel;
    private JPanel knowledgePanel;
    private JPanel exitPanel;
    private JPanel welcomePanel;
    private JPanel nameChoosePanel;
    private JPanel processNamePanel;
    private JPanel greetPlayerPanel;
    private JPanel questionChoosePanel;
    private JPanel pickButtonPanel;
    private JPanel questionPanel;
    private JPanel optionPanel;
    private JPanel playerInfoPanel;
    private JPanel prizePyramidPanel;
    private JPanel leavePanel;
    private JPanel correctAnswerPanel;
    private JPanel gameNotFoundPanel;
    private JPanel gameHasEndedPanel;
    private JPanel newGamePanel;
    private JPanel wrongAnswer;
    private JPanel restartPanel;
    private JPanel moneyWonPanel;
    private JPanel terminatePanel;
    private JPanel topLeftIcon;
    private JPanel topRightIcon;
    private JPanel bottomLeftIcon;
    private JPanel bottomRightIcon;
    private JButton optionA;
    private JButton optionB;
    private JButton optionC;
    private JButton optionD;
    private JLabel currentQuestion;
    private JTextField nameField;
    private JTextArea questionLabel;
    private PrizeMoneyPopUp pop;
    private Font titleFont = new Font("Oswald", Font.PLAIN, 75);
    private Font welcomeFont = new Font("Oswald", Font.PLAIN, 60);
    private Font normalFont = new Font("Oswald", Font.PLAIN, 45);
    private Font mediumFont = new Font("Oswald", Font.PLAIN, 30);
    private Font winnerFont = new Font("Oswald", Font.PLAIN, 25);
    private Font playerInfoFont = new Font("Oswald", Font.PLAIN, 20);
    private Font noGameFont = new Font("Oswald", Font.PLAIN, 22);
    private Font welcomePanelTitle = new Font("Oswald", Font.PLAIN, 50);
    private Font smallFont = new Font("Oswald", Font.PLAIN, 28);


    // EFFECTS: instantiates player, jsonWriter, jsonReader, jsonQuestionBankReader, initializes game
    public BillionDollarGameGUI() throws FileNotFoundException {
        player = new Player();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        jsonQuestionBankReader = new JsonQuestionBankReader(JSON_QUESTIONS);
        initializeGame();
    }

    // EFFECTS: initializes game by creating mainFrame, generating mainMenu, centering the mainFrame on player's
    //          screen, setting mainFrame as visible and setting the containers bounds to mainFrame dimensions
    private void initializeGame() {
        initializeMainFrame();
        generateMainMenu();
        centerOnScreen();
        mainFrame.setVisible(true);
        con.setBounds(0, 0, WIDTH, HEIGHT);
    }

    // EFFECTS: generates main menu, by calling helpers for creating panel for title, play, knowledge, and exit, and
    //          displaying dollar images at four corners all on mainFrame
    private void generateMainMenu() {
        createTitlePanel();
        createPlayPanel();
        displayKnowledgePanel();
        displayExitPanel();
        placeDollarIconsAtFourCorners();
    }

    // EFFECTS: generates main frame, sets dimension to WIDTH and HEIGHT, adds title, sets close operation and back-
    //          ground color, sets null layout, makes mainFrame not resizable, instantiates container
    private void initializeMainFrame() {
        mainFrame = new JFrame();
        mainFrame.setSize(1200, 800);
        mainFrame.setTitle("The Billion-Dollar Question");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setBackground(Color.black);
        mainFrame.setLayout(null);
        mainFrame.setResizable(false);

        con = mainFrame.getContentPane();
    }

    // EFFECTS: creates panel to display game title, adds label with title to panel, adds panel to container
    private void createTitlePanel() {
        titleNamePanel = new JPanel();
        titleNamePanel.setBounds(0, HEIGHT / 4 + 50, WIDTH, HEIGHT / 8);
        titleNamePanel.setBackground(Color.black);

        JLabel titleNameLabel = new JLabel("THE BILLION-DOLLAR QUESTION");
        titleNameLabel.setForeground(Color.white);
        titleNameLabel.setFont(titleFont);
        titleNamePanel.add(titleNameLabel);

        con.add(titleNamePanel);
    }

    // EFFECTS: calls helper to create panel for starting game, creates buttons for starting a new game and loading a
    //          saved game, adds button to panel
    private void createPlayPanel() {
        createBeginGamePanel();

        JButton newGameButton = new JButton("NEW GAME");
        newGameButton.setBackground(Color.white);
        newGameButton.setForeground(Color.black);
        newGameButton.setFont(normalFont);
        newGameButton.setActionCommand("new game");
        newGameButton.addActionListener(this);
        beginGamePanel.add(newGameButton);

        JButton loadGameButton = new JButton("LOAD GAME");
        loadGameButton.setBackground(Color.white);
        loadGameButton.setForeground(Color.black);
        loadGameButton.setFont(normalFont);
        loadGameButton.setActionCommand("load game");
        loadGameButton.addActionListener(this);
        beginGamePanel.add(loadGameButton);
    }

    // EFFECTS: helper for creating a panel that houses buttons to begin game
    private void createBeginGamePanel() {
        beginGamePanel = new JPanel();
        beginGamePanel.setBounds(WIDTH / 4, HEIGHT / 2, WIDTH / 2, HEIGHT / 8 - 25);
        beginGamePanel.setBackground(Color.black);
        con.add(beginGamePanel);
    }

    // EFFECTS: gets moneyIcon picture from helper, creates 4 separate labels of the icon, receives panels
    //          housing the moneyIcon label for all four corners of the screen, adds panels containing image labels
    //          to container
    private void placeDollarIconsAtFourCorners() {
        int dimY = HEIGHT - 190;
        int dimX = WIDTH - 175;

        BufferedImage moneyIcon = getMainMenuMoneyIcon();

        JLabel imageTopLeft = getMoneyIcon(moneyIcon);
        JLabel imageTopRight = getMoneyIcon(moneyIcon);
        JLabel imageBottomLeft = getMoneyIcon(moneyIcon);
        JLabel imageBottomRight  = getMoneyIcon(moneyIcon);

        topLeftIcon = displayIconOnScreen(imageTopLeft, 0, 0,175,175);
        topRightIcon = displayIconOnScreen(imageTopRight, dimX, 0, 175, 175);
        bottomLeftIcon = displayIconOnScreen(imageBottomLeft, 0, dimY, 175, 175);
        bottomRightIcon = displayIconOnScreen(imageBottomRight, dimX, dimY, 175, 175);

        con.add(topLeftIcon);
        con.add(topRightIcon);
        con.add(bottomLeftIcon);
        con.add(bottomRightIcon);
    }

    // EFFECTS: reads and returns MainMenuMoneyIcon as buffered image
    private BufferedImage getMainMenuMoneyIcon() {
        BufferedImage myPicture = null;

        try {
            myPicture = ImageIO.read(new File("./data/MainMenuMoneyIcon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myPicture;
    }

    // REQUIRES: !moneyIcon == null
    // EFFECTS: receives image of moneyIcon, creates and returns a JLabel with a scaled ImageIcon of moneyIcon
    private JLabel getMoneyIcon(BufferedImage moneyIcon) {
        JLabel imageLabel;
        ImageIcon myPic = new ImageIcon(moneyIcon.getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        imageLabel = new JLabel(myPic);
        return imageLabel;
    }

    // REQUIRES: imageLabel, x, y, width, height
    // EFFECTS: creates and returns JPanel with given dimensions, containing given imageLabel
    private JPanel displayIconOnScreen(JLabel imageLabel, int x, int y, int width, int height) {
        JPanel imagePanel = new JPanel();
        imagePanel.setBounds(x, y, width, height);
        imagePanel.setBackground(Color.black);
        imagePanel.add(imageLabel);

        return imagePanel;
    }

    // EFFECTS: creates knowledge panel, creates and adds buttons for viewing rules and options to knowledge panel
    private void displayKnowledgePanel() {
        createKnowledgePanel();

        JButton rulesButton = new JButton("RULES");
        rulesButton.setBackground(Color.white);
        rulesButton.setForeground(Color.black);
        rulesButton.setFont(normalFont);
        rulesButton.setActionCommand("rules");
        rulesButton.addActionListener(this);
        knowledgePanel.add(rulesButton);

        JButton optionsButton = new JButton("OPTIONS");
        optionsButton.setBackground(Color.white);
        optionsButton.setForeground(Color.black);
        optionsButton.setFont(normalFont);
        optionsButton.setActionCommand("options");
        optionsButton.addActionListener(this);
        knowledgePanel.add(optionsButton);
    }

    // EFFECTS: instantiates knowledgePanel and adds it to container
    private void createKnowledgePanel() {
        knowledgePanel = new JPanel();
        knowledgePanel.setBounds(WIDTH / 4 + 17, 475, WIDTH / 2, HEIGHT / 8 - 25);
        knowledgePanel.setBackground(Color.black);
        con.add(knowledgePanel);
    }

    // EFFECTS: creates exit panel, creates and adds button for leaving game to panel
    private void displayExitPanel() {
        createExitPanel();

        JButton exitButton = new JButton("LEAVE GAME");
        exitButton.setBackground(Color.white);
        exitButton.setForeground(Color.black);
        exitButton.setFont(normalFont);
        exitButton.setActionCommand("leave game");
        exitButton.addActionListener(this);
        exitPanel.add(exitButton);
    }

    // EFFECTS: instantiates exit panel to size, sets background colour and adds it to container
    private void createExitPanel() {
        exitPanel = new JPanel();
        exitPanel.setBounds(WIDTH / 4, 550, WIDTH / 2, HEIGHT / 8 - 25);
        exitPanel.setBackground(Color.black);
        con.add(exitPanel);
    }

    // EFFECTS: centers mainFrame on player's screen
    private void centerOnScreen() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension size = toolkit.getScreenSize();
        mainFrame.setLocation(size.width / 2 - mainFrame.getWidth() / 2, size.height / 2 - mainFrame.getHeight() / 2);
    }

    // EFFECTS: exits system
    public void exitSystem() {
        printLog(EventLog.getInstance());
        System.exit(0);
    }

    // EFFECTS: hides all components (panels) of the main menu screen by setting their visibilities to false
    public void hideMainMenu() {
        titleNamePanel.setVisible(false);
        beginGamePanel.setVisible(false);
        knowledgePanel.setVisible(false);
        exitPanel.setVisible(false);
    }

    // EFFECTS: hides all panels of the welcome screen by setting their visibilities to false
    public void hideWelcomeMenu() {
        welcomePanel.setVisible(false);
        nameChoosePanel.setVisible(false);
        processNamePanel.setVisible(false);
        nameField.setVisible(false);
    }

    // EFFECTS: hides all panels of the greet screen by setting their visibilities to false
    public void hideGreetMenu() {
        greetPlayerPanel.setVisible(false);
        questionChoosePanel.setVisible(false);
        pickButtonPanel.setVisible(false);
    }

    // EFFECTS: hides all components of the question area by setting their visibilities to false
    private void hideQuestionArea() {
        questionPanel.setVisible(false);
        optionPanel.setVisible(false);
        playerInfoPanel.setVisible(false);
        prizePyramidPanel.setVisible(false);
        leavePanel.setVisible(false);
    }

    // EFFECTS: hides all panels of the money icons by setting their visibilities to false
    private void hideDollarIcons() {
        topLeftIcon.setVisible(false);
        topRightIcon.setVisible(false);
        bottomLeftIcon.setVisible(false);
        bottomRightIcon.setVisible(false);
    }

    // EFFECTS: hides all panels of the no game found screen by setting their visibilities to false
    private void hideNoGameFoundPanel() {
        gameNotFoundPanel.setVisible(false);
        gameHasEndedPanel.setVisible(false);
        newGamePanel.setVisible(false);
    }

    // EFFECTS: hides all panels of the end of game screen by setting their visibilities to false
    private void hideGameEndPanel() {
        wrongAnswer.setVisible(false);
        restartPanel.setVisible(false);
        correctAnswerPanel.setVisible(false);
        moneyWonPanel.setVisible(false);
        terminatePanel.setVisible(false);
    }

    // EFFECTS: creates welcome, name choose, and handle name panels for welcoming player
    public void welcomePlayerToGame() {
        createWelcomePanel();
        createNameChoosePanel();
        handlePlayerName();
    }

    // EFFECTS: instantiates welcome panel by setting bounds and background color, adds welcome label to welcome panel
    //          , adds welcome panel to container
    private void createWelcomePanel() {
        welcomePanel = new JPanel();
        welcomePanel.setBounds(0, 250, WIDTH, HEIGHT / 8);
        welcomePanel.setBackground(Color.black);

        JLabel welcomeLabel = new JLabel("WELCOME TO THE BILLION-DOLLAR QUESTION");
        welcomeLabel.setForeground(Color.white);
        welcomeLabel.setFont(welcomePanelTitle);
        welcomePanel.add(welcomeLabel);

        con.add(welcomePanel);
    }

    // EFFECTS: instantiates name choose panel to size and background color, adds enterName label to name choose panel,
    //          adds nameChoosePanel to container
    private void createNameChoosePanel() {
        nameChoosePanel = new JPanel();
        nameChoosePanel.setBounds(0, 350, WIDTH, HEIGHT / 8 - 25);
        nameChoosePanel.setBackground(Color.black);

        JLabel enterName = new JLabel("PLEASE ENTER YOUR NAME:");
        enterName.setForeground(Color.white);
        enterName.setFont(normalFont);
        nameChoosePanel.add(enterName);

        con.add(nameChoosePanel);
    }

    // EFFECTS: creates name field and process name panel, creates and adds buttons to confirm name to process name
    //          panel
    private void handlePlayerName() {
        createNameField();
        createProcessNamePanel();

        JButton confirmNameButton = new JButton("CONFIRM");
        confirmNameButton.setBackground(Color.white);
        confirmNameButton.setForeground(Color.white);
        confirmNameButton.setFont(normalFont);
        confirmNameButton.setActionCommand("confirmed name");
        confirmNameButton.addActionListener(this);
        processNamePanel.add(confirmNameButton);
    }

    // EFFECTS: instantiates name field, and adds it to container
    private void createNameField() {
        nameField = new JTextField();
        nameField.setFont(normalFont);
        nameField.setForeground(Color.black);
        nameField.setBackground(Color.white);
        nameField.setBounds(450, 425, 300, HEIGHT / 8 - 10);
        nameField.setHorizontalAlignment(JTextField.CENTER);
        con.add(nameField);
    }

    // EFFECTS: instantiates panel for processing name, and adds it to container
    private void createProcessNamePanel() {
        processNamePanel = new JPanel();
        processNamePanel.setBounds(450, 515, 300, HEIGHT / 8 - 10);
        processNamePanel.setBackground(Color.black);
        con.add(processNamePanel);
    }

    // EFFECTS: gets player name and current question, creates player info panel, creates and adds labels with player
    //          name and current question number to player info panel
    private void displayPlayerInfo() {
        String name = player.getPlayerName();
        int num = player.getCurrentQuestionNumber();

        createPlayerInfoPanel();

        JLabel playerName = new JLabel(" PLAYER: " + name.toUpperCase());
        playerName.setFont(playerInfoFont);
        playerName.setForeground(Color.white);
        playerInfoPanel.add(playerName);

        currentQuestion = new JLabel(" QUESTION NUMBER: " + (num + 1));
        currentQuestion.setFont(playerInfoFont);
        currentQuestion.setForeground(Color.white);
        playerInfoPanel.add(currentQuestion);
    }

    // EFFECTS: instantiates player info panel with border, layout, bounds, and background color
    //          and adds it to container
    private void createPlayerInfoPanel() {
        playerInfoPanel = new JPanel();
        playerInfoPanel.setBorder(BorderFactory.createMatteBorder(2, 10, 2, 2, Color.orange));
        playerInfoPanel.setLayout(new GridLayout(2, 1));
        playerInfoPanel.setBounds(0, 0, 275, 75);
        playerInfoPanel.setBackground(Color.black);
        con.add(playerInfoPanel);
    }

    // EFFECTS: creates leave panel, creates and adds buttons for saving and leaving game, and quitting game to leave
    //          panel
    private void displayLeaveMenu() {
        createLeavePanel();

        JButton exitButton = new JButton("SAVE & LEAVE GAME");
        exitButton.setForeground(Color.black);
        exitButton.setActionCommand("exit game");
        exitButton.addActionListener(this);

        JButton quitButton = new JButton("QUIT GAME");
        quitButton.setForeground(Color.black);
        quitButton.setActionCommand("quit game");
        quitButton.addActionListener(this);

        leavePanel.add(exitButton);
        leavePanel.add(quitButton);
    }

    // EFFECTS: instantiates leave panel with bounds, layout and background color, adds it to container
    private void createLeavePanel() {
        leavePanel = new JPanel();
        leavePanel.setBounds(275, 0, 175, 75);
        leavePanel.setLayout(new GridLayout(2, 1));
        leavePanel.setBackground(Color.orange);
        con.add(leavePanel);
    }

    // MODIFIES: this
    // EFFECTS: sets player move on status to Q (Quit), hides the question area, displays correct answer, gets and
    //          displays player prize message, gives user option to terminate, and resets player
    private void quitGame() {
        player.setMoveOn("Q");
        hideQuestionArea();
        displayCorrectAnswer();
        String message = getPlayerPrizeMessage();
        displayPlayerPrizeWon(message);
        giveOptionToTerminate(Color.black);
        resetPlayerAfterGameHasEnded();
    }

    // MODIFIES: this
    // EFFECTS: handles all buttons in game by triggering behaviour corresponding to button
    @Override
    @SuppressWarnings("methodlength")
    public void actionPerformed(ActionEvent e) {
        int question = player.getCurrentQuestionNumber() + 1;

        if (e.getActionCommand().equals("new game")) {
            // For playing a new game
            hideMainMenu();
            welcomePlayerToGame();
        } else if (e.getActionCommand().equals("load game")) {
            // For loading a game
            hideMainMenu();
            loadGame();

            if (player.getPlayerName().equals("NEW")) {
                commenceNonLoadedGame();
            } else {
                commenceLoadedGame();
            }
        } else if (e.getActionCommand().equals("rules")) {
            // For viewing game rules
            hideMainMenu();
            mainFrame.setBackground(Color.red);
        } else if (e.getActionCommand().equals("options")) {
            // For viewing game options
            hideMainMenu();
            mainFrame.getContentPane().setBackground(Color.yellow);
        } else if (e.getActionCommand().equals("leave game")) {
            // For leaving the game
            exitSystem();
        } else if (e.getActionCommand().equals("go back to title screen")) {
            // For going back to main menu after no game has been found
            hideNoGameFoundPanel();
            generateMainMenu();
        } else if (e.getActionCommand().equals("restart game")) {
            // For going back to main menu after user quits game
            hideGameEndPanel();
            generateMainMenu();
        } else if (e.getActionCommand().equals("confirmed name")) {
            // For choosing questions after entering name
            player.setPlayerName(nameField.getText());
            chooseQuestionsBeforeEntering();
        } else if (e.getActionCommand().equals("chosen questions")) {
            // For starting the game after choosing the questions
            createPlayerQuestionList();
            hideDollarIcons();
            player.playerCanStartPlay();
            commenceGame();
        } else if (e.getActionCommand().equals("exit game")) {
            // For saving and leaving the game
            savePlayer();
            exitSystem();
        } else if (e.getActionCommand().equals("new save game")) {
            // For starting a new game after no game found
            hideNoGameFoundPanel();
            welcomePlayerToGame();
        } else if (e.getActionCommand().equals("quit game")) {
            // For quitting the game
            quitGame();
        } else if (e.getActionCommand().equals("picked A")) {
            // For checking option A
            checkUserAnswer("A");
        } else if (e.getActionCommand().equals("picked B")) {
            // For checking option B
            checkUserAnswer("B");
        } else if (e.getActionCommand().equals("picked C")) {
            // For checking option C
            checkUserAnswer("C");
        } else if (e.getActionCommand().equals("picked D")) {
            // For checking option D
            checkUserAnswer("D");
        } else if (e.getActionCommand().equals("popup1")) {
            pop = new PrizeMoneyPopUp(question, 1);
        } else if (e.getActionCommand().equals("popup2")) {
            pop = new PrizeMoneyPopUp(question, 2);
        } else if (e.getActionCommand().equals("popup3")) {
            pop = new PrizeMoneyPopUp(question, 3);
        } else if (e.getActionCommand().equals("popup4")) {
            pop = new PrizeMoneyPopUp(question, 4);
        } else if (e.getActionCommand().equals("popup5")) {
            pop = new PrizeMoneyPopUp(question, 5);
        } else if (e.getActionCommand().equals("popup6")) {
            pop = new PrizeMoneyPopUp(question, 6);
        } else if (e.getActionCommand().equals("popup7")) {
            pop = new PrizeMoneyPopUp(question, 7);
        } else if (e.getActionCommand().equals("popup8")) {
            pop = new PrizeMoneyPopUp(question, 8);
        } else if (e.getActionCommand().equals("popup9")) {
            pop = new PrizeMoneyPopUp(question, 9);
        } else if (e.getActionCommand().equals("popup10")) {
            pop = new PrizeMoneyPopUp(question, 10);
        } else if (e.getActionCommand().equals("popup11")) {
            pop = new PrizeMoneyPopUp(question, 11);
        } else if (e.getActionCommand().equals("popup12")) {
            pop = new PrizeMoneyPopUp(question, 12);
        } else if (e.getActionCommand().equals("popup13")) {
            pop = new PrizeMoneyPopUp(question, 13);
        } else if (e.getActionCommand().equals("popup14")) {
            pop = new PrizeMoneyPopUp(question, 14);
        }
    }

    // EFFECTS: prints out event log on console
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString() + "\n");
        }
    }

    // REQUIRES: pickedOption is "A" | "B" | "C" | "D"
    // MODIFIES: this
    // EFFECTS: gets players current question, finds correct option for that question, matches it to pickedOption,
    //          if answer is incorrect terminates game by incorrect answer, else increases the player's question
    //          number, updates player status and game
    private void checkUserAnswer(String pickedOption) {
        Question currentQuestion = player.getQuestionFromPlayerList(player.getCurrentQuestionNumber());
        String correctOption = findCorrectOption(currentQuestion);

        if (!pickedOption.equals(correctOption)) {
            terminateGameByIncorrectAnswer();
        } else {
            player.increaseCurrentQuestionNumber();
            updatePlayerStatus();
            updateGame();
        }
    }

    // EFFECTS: displays player info, leave menu, gets player's question corresponding to current question number,
    //          creates question panel, prize pyramid, adds question to panel, scrambles question options, and displays
    //          options
    private void manageQuestion() {
        for (int i = 0; i < MAX_LEVEL; i++) {
            if (i == player.getCurrentQuestionNumber()) {
                displayPlayerInfo();
                displayLeaveMenu();
                Question currentQuestion = player.getQuestionFromPlayerList(i);
                createQuestionPanel();
                createPrizePyramid();
                addQuestionToPanel(currentQuestion);
                scrambleQuestionOptions(currentQuestion);
                displaysOptions(currentQuestion);
            }
        }
    }

    // EFFECTS: If player is not on final question, updates questionLabel with new question, updates options and
    //          player's current question number displayed on player info panel. Otherwise, updates game by
    //          informing player they have won.
    private void updateGame() {
        if (player.getCurrentQuestionNumber() < 14) {
            Question q = player.getQuestionFromPlayerList(player.getCurrentQuestionNumber());
            questionLabel.setText(q.getQuestion());
            updateOptions(q);

            int num = player.getCurrentQuestionNumber();
            currentQuestion.setText(" QUESTION NUMBER: " + (num + 1));
        } else {
            playerHasWon();
        }
    }

    // REQUIRES: q
    // EFFECTS: updates options with new options corresponding to given question, q
    private void updateOptions(Question q) {
        scrambleQuestionOptions(q);

        optionA.setText("A. " + q.getOption(1));
        optionB.setText("B. " + q.getOption(2));
        optionC.setText("C. " + q.getOption(3));
        optionD.setText("D. " + q.getOption(4));
    }

    // EFFECTS: hides question area, sets background to white and displays winner message
    private void playerHasWon() {
        hideQuestionArea();
        displayWinnerMessage();
        resetPlayerAfterGameHasEnded();
    }

    // EFFECTS: creates winner panel and adds winnerLabel to indicate player has won, creates congratsPanel, gets
    //          grandPrize message and adds it to congratsLabel, adds congratsLabel to congratsPanel, gives user
    //          option to terminate and adds both panels to container
    private void displayWinnerMessage() {
        JPanel winnerPanel = new JPanel();
        winnerPanel.setBounds(0, 200, WIDTH, HEIGHT / 8);
        winnerPanel.setBackground(Color.black);

        JLabel winnerLabel = new JLabel("WINNNNNNNNEEEEERRRRRRRRRRRRRR!!!!!!!!!!!!!!!!");
        winnerLabel.setFont(normalFont);
        winnerLabel.setForeground(Color.white);
        winnerPanel.add(winnerLabel);

        JPanel congratsPanel = new JPanel();
        congratsPanel.setBounds(0, 300, WIDTH, HEIGHT / 8);
        congratsPanel.setBackground(Color.black);

        JLabel congratsLabel = new JLabel(getGrandPrizeMessage());
        congratsLabel.setFont(winnerFont);
        congratsLabel.setForeground(Color.white);
        congratsPanel.add(congratsLabel);

        giveOptionToTerminate(Color.black);

        con.add(winnerPanel);
        con.add(congratsPanel);
    }

    // EFFECTS: gets grand prize amount corresponding to max level and player name, creates customized message for
    //          player to indicate they have won the ultimate prize
    private String getGrandPrizeMessage() {
        String grandPrize = gameMoneyLevels.getPrizeMoneyForGameTermination(MAX_LEVEL);
        String name = player.getPlayerName();
        return "Congratulations " + name + ", you have won the ultimate prize of " + "$" + grandPrize;
    }

    // EFFECTS: hides question area, alerts user of game status and resets player to terminate game
    private void terminateGameByIncorrectAnswer() {
        hideQuestionArea();
        String prizeMessage = getPlayerPrizeMessage();
        alertUser(prizeMessage);
        player.setMoveOn("I");
        resetPlayerAfterGameHasEnded();
    }

    // EFFECTS: gets player's prize money won, converts and returns it as custom message
    private String getPlayerPrizeMessage() {
        String moneyWon = getPrizeMoneyWon();

        return convertPrizeMoneyWon(moneyWon);
    }

    // EFFECTS: If player has quit game, gets previous question number and returns amount associated with that question,
    //          "0" if user quits on first question. Otherwise, gets player's latest prize barrier and returns amount
    //          corresponding to prize barrier, "0" if no barrier has been crossed
    private String getPrizeMoneyWon() {

        if (player.getMoveOn().equals("Q")) {
            int previousQuestion = player.getCurrentQuestionNumber();

            if (previousQuestion == 0) {
                return "0";
            } else {
                return gameMoneyLevels.getPrizeMoneyForGameTermination(previousQuestion);
            }
        } else {
            int playerBarrier = player.getLatestPrizeBarrier();
            String prize1 = gameMoneyLevels.getPrizeMoneyForGameTermination(BARRIER1);
            String prize2 = gameMoneyLevels.getPrizeMoneyForGameTermination(BARRIER2);

            if (playerBarrier == 0) {
                return "0";
            } else if (playerBarrier == 1) {
                return prize1;
            } else {
                return prize2;
            }
        }
    }

    // REQUIRES: m
    // EFFECTS: gets player name and creates custom message indicating prize money won, if any has been won in the
    //          first place
    private String convertPrizeMoneyWon(String m) {
        String name = player.getPlayerName();

        if (m.equals("0")) {
            return "Unfortunately " + name + ", you have not won any money!";
        } else {
            return "Congratulations " + name + ", you have won " + "$" + m + "!";
        }
    }

    // MODIFIES: this
    // EFFECTS: instantiates new player and rewrites (saves) current player as new player after game has ended
    private void resetPlayerAfterGameHasEnded() {
        try {
            player = new Player();
            jsonWriter.open();
            jsonWriter.write(player);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // REQUIRES: p
    // EFFECTS: displays user has entered incorrect answer, correct answer, prize won, gives user option to return to
    //          main menu or quit
    private void alertUser(String p) {
        displayEnteredIncorrectAnswer();
        displayCorrectAnswer();
        displayPlayerPrizeWon(p);
        giveOptionToGoBackToMainMenu();
        giveOptionToTerminate(Color.BLACK);
    }

    // EFFECTS: creates wrong answer panel, adds enteredWrong label to indicate user has entered the wrong answer to
    //          panel, adds panel to container
    private void displayEnteredIncorrectAnswer() {
        wrongAnswer = new JPanel();
        wrongAnswer.setBounds(0, 200, 1200, HEIGHT / 8);
        wrongAnswer.setBackground(Color.black);

        JLabel enteredWrong = new JLabel("That is the incorrect answer");
        enteredWrong.setForeground(Color.white);
        enteredWrong.setFont(normalFont);
        wrongAnswer.add(enteredWrong);
        con.add(wrongAnswer);
    }

    // EFFECTS: creates correct answer panel, adds rightAnswer label to indicate the correct answer to panel, adds
    //          panel to container
    private void displayCorrectAnswer() {
        correctAnswerPanel = new JPanel();
        correctAnswerPanel.setBounds(0, 300, 1200, HEIGHT / 8);
        correctAnswerPanel.setBackground(Color.black);

        JLabel rightAnswer = new JLabel(getCorrectAnswerMessage());
        rightAnswer.setForeground(Color.white);
        rightAnswer.setFont(normalFont);
        correctAnswerPanel.add(rightAnswer);

        con.add(correctAnswerPanel);
    }

    // EFFECTS: creates money won panel, adds prizeAmount label to indicate the money won to panel, adds
    //          panel to container
    private void displayPlayerPrizeWon(String message) {
        moneyWonPanel = new JPanel();
        moneyWonPanel.setBounds(0, 400, 1200, HEIGHT / 8);
        moneyWonPanel.setBackground(Color.black);

        JLabel prizeAmount = new JLabel(message);
        prizeAmount.setForeground(Color.white);
        prizeAmount.setFont(smallFont);
        moneyWonPanel.add(prizeAmount);

        con.add(moneyWonPanel);
    }

    // EFFECTS: creates and formats restart panel, adds restartButton to panel, adds panel to container
    private void giveOptionToGoBackToMainMenu() {
        restartPanel = new JPanel();
        restartPanel.setBounds(0, 500, WIDTH, HEIGHT / 8);
        restartPanel.setBackground(Color.black);

        JButton restartButton = new JButton("MAIN MENU");
        restartButton.setFont(normalFont);
        restartButton.setForeground(Color.white);
        restartButton.setActionCommand("restart game");
        restartButton.addActionListener(this);
        restartPanel.add(restartButton);
        con.add(restartPanel);
    }

    // REQUIRES: c
    // EFFECTS: creates terminate panel, adds terminateButton to panel, adds panel to container
    private void giveOptionToTerminate(Color c) {
        terminatePanel = new JPanel();
        terminatePanel.setBounds(0, 600, 1200, HEIGHT / 8);
        terminatePanel.setBackground(c);

        JButton terminateButton = new JButton("LEAVE GAME");
        terminateButton.setForeground(Color.white);
        terminateButton.setFont(normalFont);
        terminateButton.setActionCommand("leave game");
        terminateButton.addActionListener(this);
        terminatePanel.add(terminateButton);

        con.add(terminatePanel);
    }

    // EFFECTS: creates and returns message indicating correct answer to question
    private String getCorrectAnswerMessage() {
        String correctAnswer;
        Question question = player.getQuestionFromPlayerList(player.getCurrentQuestionNumber());
        String option = findCorrectOption(question);
        correctAnswer = "The correct answer was option " + option + ", " + question.getCorrectAnswer() + "!";

        return correctAnswer;
    }

    // EFFECTS: hides welcome menu, greets player, alerts player to choose questions, and gets user to choose questions
    public void chooseQuestionsBeforeEntering() {
        hideWelcomeMenu();
        greetPlayer();
        alertUserToChoose();
        getUserToChoose();
    }

    // EFFECTS: creates greet player panel, adds label welcoming player to game to panel, adds panel to container
    private void greetPlayer() {
        greetPlayerPanel = new JPanel();
        greetPlayerPanel.setBounds(0, 275, WIDTH, HEIGHT / 8);
        greetPlayerPanel.setBackground(Color.black);

        JLabel helloPlayer = new JLabel("HELLO " + (player.getPlayerName()).toUpperCase() + " !");
        helloPlayer.setFont(welcomeFont);
        helloPlayer.setForeground(Color.white);
        greetPlayerPanel.add(helloPlayer);

        con.add(greetPlayerPanel);
    }

    // EFFECTS: creates questionChoosePanel, creates label indicating player must choose questions before playing to
    //          panel, adds panel to container
    private void alertUserToChoose() {
        questionChoosePanel = new JPanel();
        questionChoosePanel.setBounds(10, 375, WIDTH - 10, HEIGHT / 8);
        questionChoosePanel.setBackground(Color.black);

        JLabel beforeChoosing = new JLabel("BEFORE ENTERING THE GAME, ARE YOU READY TO CHOOSE YOUR 14 QUESTIONS?");
        beforeChoosing.setFont(mediumFont);
        beforeChoosing.setForeground(Color.white);
        questionChoosePanel.add(beforeChoosing);

        con.add(questionChoosePanel);
    }

    // EFFECTS: creates pickButtonPanel and creates and adds button for picking question to panel, adds panel to
    //          container
    private void getUserToChoose() {
        pickButtonPanel = new JPanel();
        pickButtonPanel.setBounds(0, 475, WIDTH, HEIGHT / 8);
        pickButtonPanel.setBackground(Color.black);

        JButton chooseQuestions = new JButton("PICK");
        chooseQuestions.setBackground(Color.white);
        chooseQuestions.setForeground(Color.white);
        chooseQuestions.setFont(welcomeFont);
        chooseQuestions.setActionCommand("chosen questions");
        chooseQuestions.addActionListener(this);
        pickButtonPanel.add(chooseQuestions);

        con.add(pickButtonPanel);
    }

    // EFFECTS: begins game by hiding greet menu and managing each question
    private void commenceGame() {
        hideGreetMenu();
        manageQuestion();
    }

    // EFFECTS: begins loaded game, by loading game, hiding main menu, and managing question
    private void commenceLoadedGame() {
        hideMainMenu();
        hideDollarIcons();
        manageQuestion();
    }

    // EFFECTS: begins non-loaded game by hiding main menu and alerting user no game exists
    private void commenceNonLoadedGame() {
        hideMainMenu();
        alertUserNoGameExists();
    }

    // MODIFIES: this
    // EFFECTS: loads player from file, if playerName is "NEW', indicates that the previous game has finished,
    //          asking the player to continue by selecting a valid option from the main menu (excluding e and l),
    //          otherwise loads player who was previously playing game from file
    private void loadGame() {
        try {
            player = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: alerts user there is no saved game by creating not found alert, displaying no game found message, and
    //          checking if they want to continue
    private void alertUserNoGameExists() {
        createGameNotFoundAlert();
        displayNoGameFoundMessage();
        checkIfUserWantsToContinue();
    }

    // EFFECTS: creates new game panel, creates and adds button for going back to title screen to pane, adds panel to
    //          container
    private void checkIfUserWantsToContinue() {
        newGamePanel = new JPanel();
        newGamePanel.setBounds(300, 525, 600, 100);
        newGamePanel.setLayout(new GridLayout(2, 1));
        newGamePanel.setBackground(Color.black);

        JButton newGameButton = createNewSaveGameButton();
        newGamePanel.add(newGameButton);

        JButton returnToMainMenuButton = new JButton("NO");
        returnToMainMenuButton.setFont(normalFont);
        returnToMainMenuButton.setForeground(Color.white);
        returnToMainMenuButton.setActionCommand("go back to title screen");
        returnToMainMenuButton.addActionListener(this);

        newGamePanel.add(returnToMainMenuButton);

        con.add(newGamePanel);
    }

    // EFFECTS: creates game ended panel, creates and displays labels with message alerting user no game is saved
    //          asking for next move, adds panel to container
    private void displayNoGameFoundMessage() {
        gameHasEndedPanel = new JPanel();
        gameHasEndedPanel.setBounds(0, 425, 1200, 100);
        gameHasEndedPanel.setBackground(Color.black);

        String message = "You'll need to set up a new save game in order to play. Do you want to set one up now?";
        JLabel gameHasEndedLabel = new JLabel();
        gameHasEndedLabel.setText(message.toUpperCase());
        gameHasEndedLabel.setFont(noGameFont);
        gameHasEndedLabel.setForeground(Color.white);
        gameHasEndedPanel.add(gameHasEndedLabel);


        String titleScreenMessage = "SELECTING 'NO' WILL RETURN YOU TO THE TITLE SCREEN";
        JLabel returnToTitleLabel = new JLabel();
        returnToTitleLabel.setText(titleScreenMessage);
        returnToTitleLabel.setFont(noGameFont);
        returnToTitleLabel.setForeground(Color.white);
        gameHasEndedPanel.add(returnToTitleLabel);

        con.add(gameHasEndedPanel);
    }

    // EFFECTS: creates and formats game not found panel with game not found label, and adds it to container
    private void createGameNotFoundAlert() {
        gameNotFoundPanel = new JPanel();
        gameNotFoundPanel.setBackground(Color.black);
        gameNotFoundPanel.setBounds(0, 300, 1200, 100);

        JLabel gameNotFoundLabel = new JLabel("GAME NOT FOUND");
        gameNotFoundLabel.setFont(titleFont);
        gameNotFoundLabel.setForeground(Color.white);
        gameNotFoundPanel.add(gameNotFoundLabel);
        con.add(gameNotFoundPanel);
    }

    // EFFECTS: creates, formats, and returns new game button
    private JButton createNewSaveGameButton() {
        JButton newSaveGameButton = new JButton("NEW GAME");
        newSaveGameButton.setFont(normalFont);
        newSaveGameButton.setForeground(Color.white);

        newSaveGameButton.setActionCommand("new save game");
        newSaveGameButton.addActionListener(this);

        return newSaveGameButton;
    }

    // EFFECTS: creates pyramid panel, creates ands prize buttons to pyramid panel
    private void createPrizePyramid() {
        createPrizePyramidPanel();

        JButton q14 = createPyramidButton("14     $1,000,000,000", "popup14");
        JButton q13 = createPyramidButton("13       $200,000,000", "popup13");
        JButton q12 = createPyramidButton("12        $40,000,000", "popup12");
        JButton q11 = createPyramidButton("11        $20,000,000", "popup11");
        JButton q10 = createPyramidButton("10        $10,000,000", "popup10");
        JButton q9 = createPyramidButton(" 9         $5,000,000", "popup9");
        JButton q8 = createPyramidButton(" 8         $1,000,000", "popup8");
        JButton q7 = createPyramidButton(" 7           $200,000", "popup7");
        JButton q6 = createPyramidButton(" 6           $100,000", "popup6");
        JButton q5 = createPyramidButton(" 5            $40,000", "popup5");
        JButton q4 = createPyramidButton(" 4            $20,000", "popup4");
        JButton q3 = createPyramidButton(" 3            $10,000", "popup3");
        JButton q2 = createPyramidButton(" 2             $2,000", "popup2");
        JButton q1 = createPyramidButton(" 1             $1,000", "popup1");

        addPrizeButtonToPyramidPanel(q14, q13);
        addPrizeButtonToPyramidPanel(q12, q11);
        addPrizeButtonToPyramidPanel(q10, q9);
        addPrizeButtonToPyramidPanel(q8, q7);
        addPrizeButtonToPyramidPanel(q6, q5);
        addPrizeButtonToPyramidPanel(q4, q3);
        addPrizeButtonToPyramidPanel(q2, q1);
    }

    // EFFECTS: creates, formats pyramid panel, adds it to container
    private void createPrizePyramidPanel() {
        prizePyramidPanel = new JPanel();
        prizePyramidPanel.setLayout(new GridLayout(14, 1));
        prizePyramidPanel.setBounds(825, 0, 375, 775);
        prizePyramidPanel.setBackground(Color.orange);
        con.add(prizePyramidPanel);
    }

    // REQUIRES: buttonText, actionCommand
    // EFFECTS: creates button with given buttonText and actionCommand, adds actionListener, returns button
    private JButton createPyramidButton(String buttonText, String actionCommand) {
        JButton button = new JButton(buttonText);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);

        return button;
    }

    // REQUIRES: b1 and b2
    // EFFECTS: adds two prize money buttons to pyramid panel
    private void addPrizeButtonToPyramidPanel(JButton b1, JButton b2) {
        prizePyramidPanel.add(b1);
        prizePyramidPanel.add(b2);
    }

    // EFFECTS: creates, formats question panel, adds it to container
    private void createQuestionPanel() {
        questionPanel = new JPanel();
        questionPanel.setBounds(5, 300, 800 - 5, HEIGHT / 8);
        questionPanel.setLayout(new GridLayout(1, 1));
        con.add(questionPanel);
    }

    // REQUIRES: q
    // EFFECTS: creates and formats question label with question of given q, adds label to questionPanel
    private void addQuestionToPanel(Question q) {
        String question = q.getQuestion();
        questionLabel = new JTextArea(question);
        questionLabel.setBackground(Color.orange);
        questionLabel.setLineWrap(true);
        questionLabel.setFont(smallFont);
        questionLabel.setForeground(Color.black);
        questionPanel.add(questionLabel);
    }

    // REQUIRES: q
    // EFFECTS: creates and formats option panel, adds it to container, creates four option buttons
    private void displaysOptions(Question q) {
        optionPanel = new JPanel();
        optionPanel.setBounds(0, 400, 800, 375);
        optionPanel.setBackground(Color.black);
        optionPanel.setLayout(new GridLayout(4, 1));
        con.add(optionPanel);

        createOptionAButton(q);
        createOptionBButton(q);
        createOptionCButton(q);
        createOptionDButton(q);
    }

    // REQUIRES: q
    // EFFECTS: given question, creates formatted button with option D of question, and adds it to optionPanel
    private void createOptionDButton(Question q) {
        optionD = new JButton("D. " + q.getOption(4));
        optionD.setFont(normalFont);
        optionD.setForeground(Color.white);
        optionD.setHorizontalAlignment(SwingConstants.LEFT);
        optionD.setActionCommand("picked D");
        optionD.addActionListener(this);
        optionPanel.add(optionD);
    }

    // REQUIRES: q
    // EFFECTS: given question, creates formatted button with option C of question, and adds it to optionPanel
    private void createOptionCButton(Question q) {
        optionC = new JButton("C. " + q.getOption(3));
        optionC.setFont(normalFont);
        optionC.setForeground(Color.white);
        optionC.setHorizontalAlignment(SwingConstants.LEFT);
        optionC.setActionCommand("picked C");
        optionC.addActionListener(this);
        optionPanel.add(optionC);
    }

    // REQUIRES: q
    // EFFECTS: given question, creates formatted button with option B of question, and adds it to optionPanel
    private void createOptionBButton(Question q) {
        optionB = new JButton("B. " + q.getOption(2));
        optionB.setFont(normalFont);
        optionB.setForeground(Color.white);
        optionB.setHorizontalAlignment(SwingConstants.LEFT);
        optionB.setActionCommand("picked B");
        optionB.addActionListener(this);
        optionPanel.add(optionB);
    }

    // REQUIRES: q
    // EFFECTS: given question, creates formatted button with option A of question, and adds it to optionPanel
    private void createOptionAButton(Question q) {
        optionA = new JButton("A. " + q.getOption(1));
        optionA.setFont(normalFont);
        optionA.setForeground(Color.white);
        optionA.setHorizontalAlignment(SwingConstants.LEFT);
        optionA.setActionCommand("picked A");
        optionA.addActionListener(this);
        optionPanel.add(optionA);
    }

    // MODIFIES: this
    // EFFECTS: creates QuestionBank containing 70 questions (5 for each question), generates random number and picks
    //          the question from bank with that index and adds it to playerList, carries out process for all 14
    //          questions
    private void createPlayerQuestionList() {
        try {
            QuestionBank bank = jsonQuestionBankReader.read();

            for (int i = 0; i < 14; i++) {
                int num = randomQuestionNumberGenerator(1, 6);
                Question q = bank.getQuestionFromQuestionBank(i + 1, num);
                player.addQuestionToPlayerList(q);
            }

        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
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

        int seed = randomQuestionNumberGenerator(4, 12);

        Collections.shuffle(newOrder, new Random(seed));

        return newOrder;
    }

    // EFFECTS: generates a random number the range of min and max, inclusive
    private int randomQuestionNumberGenerator(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
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

    // EFFECTS: saves the current player to file
    private void savePlayer() {
        try {
            player.setGameStatus(false);
            player.setMoveOn("S");
            jsonWriter.open();
            jsonWriter.write(player);
            jsonWriter.close();
            System.out.println("\nThe game has been saved to " + JSON_STORE + "\n");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // REQUIRES: q
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
