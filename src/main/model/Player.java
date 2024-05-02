package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a player having a name, move on status corresponding to phase of game, current question number of the
// questions answered correctly, prize barrier status, playing status, usage of skip lifeline, status indicating
// whether they can start to play, and a questionList storing game questions corresponding to that player
public class Player implements Writable {
    private String name; // the player name
    private String moveOn; // tracks player's move on status
    private int currentQuestionNumber; // player's current question number of latest question answered correctly
    private int prizeBarrier; // player's most recent prize barrier
    private boolean isPlaying; // indicates if player is still in game
    private boolean skipLifelineUsed; // usage status of Skip The Question lifeline
    private boolean canStartPlay; // indicates whether play can begin playing
    private List<Question> questionList; // player's list of game questions

    /*
     * EFFECTS: player name is set to "NEW" to indicate new game, playing status is set to true, current question
     *          number and prize barrier are 0, usage status of Skip The Question lifeline is set to false, move on
     *          status is set to "D" (default), player's canStartPlay is set to false, and questionList is set to an
     *          empty ArrayList<>;
     */
    public Player() {
        name = "NEW";
        isPlaying = true;
        currentQuestionNumber = 0;
        prizeBarrier = 0;
        skipLifelineUsed = false;
        moveOn = "D";
        canStartPlay = false;
        questionList = new ArrayList<>();
    }

    // EFFECTS: Converts player to JSON Object for file writing
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Player Name", name);
        json.put("Is Playing", isPlaying);
        json.put("Current Question Number", currentQuestionNumber);
        json.put("Prize Barrier", prizeBarrier);
        json.put("Skip Lifeline Used", skipLifelineUsed);
        json.put("Move On", moveOn);
        json.put("Can Start Play", canStartPlay);
        json.put("Question List", playerListToJson());

        return json;
    }

    // EFFECTS: Converts player's questionList to JSON Object for file writing
    private JSONArray playerListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Question q : questionList) {
            jsonArray.put(q.toJson());
        }
        return jsonArray;
    }

    // MODIFIES: this, EventLog
    // EFFECTS: Adds question to question list of player
    public void addQuestionToPlayerList(Question q) {
        questionList.add(q);

        if (questionList.size() == 14) {
            EventLog.getInstance().logEvent(new Event(this.getPlayerName() + " has chosen 14 questions"));
        }
    }

    // MODIFIES: this, EventLog
    // EFFECTS: sets name of Player to s, if game is not in saved state, and player name is not new, then logs
    //          that the player has entered their name
    public void setPlayerName(String s) {
        name = s;

        if (!this.getMoveOn().equals("S")) {
            if (!this.getPlayerName().equals("NEW")) {
                if (this.getIsPlaying()) {
                    EventLog.getInstance().logEvent(new Event("Player entered name: " + s));
                }
            }
        }
    }

    //MODIFIES: this, EventLog
    //EFFECTS: sets player's moveOn status to string s
    public void setMoveOn(String s) {
        moveOn = s;

        if (moveOn.equals("Q")) {
            logQuitGame();
        } else if (moveOn.equals("I")) {
            logGameEndByIncorrectAnswer();
        } else if (this.getMoveOn().equals("S") && !this.getIsPlaying()) {
            logSaveGame();
        } else if (this.getMoveOn().equals("L")) {
            logLoadedGame();
        }
    }

    // MODIFIES: EventLog
    // EFFECTS: logs that game for player has been loaded in event log
    private void logLoadedGame() {
        EventLog.getInstance().logEvent(new Event("Loaded game for Player: " + this.getPlayerName()));
    }

    // MODIFIES: EventLog
    // EFFECTS: logs that player has saved and exited game in event log
    private void logSaveGame() {
        EventLog.getInstance().logEvent(new Event(this.getPlayerName() + " saved and exited"
                + " the game"));
    }

    // MODIFIES: EventLog
    // EFFECTS: logs that player has chosen the wrong answer and the current game has ended
    private void logGameEndByIncorrectAnswer() {
        EventLog.getInstance().logEvent(new Event(this.getPlayerName() + " chose one of the wrong answers "
                + "for" + " Question #" + (this.getCurrentQuestionNumber() + 1)));
        logCurrentGameHasEnded();
    }

    // MODIFIES: EventLog
    // EFFECTS: logs that player has chosen to quit and the current game has ended
    private void logQuitGame() {
        EventLog.getInstance().logEvent(new Event(this.getPlayerName() + " has chosen to quit game"));
        logCurrentGameHasEnded();
    }

    // MODIFIES: EventLog
    // EFFECTS: logs that the current game has ended
    private void logCurrentGameHasEnded() {
        EventLog.getInstance().logEvent(new Event("The current game has ended"));
    }

    //MODIFIES: this, EventLog
    //EFFECTS: increases currentQuestionNumber to represent the question player has passed, logs player has
    //         passed that question, if the player passes the last (14th) question, logs player has completed game
    //         and game has ended
    public void increaseCurrentQuestionNumber() {
        currentQuestionNumber++;
        EventLog.getInstance().logEvent(new Event(this.getPlayerName() + " passed Question #"
                + this.getCurrentQuestionNumber()));

        if (this.getCurrentQuestionNumber() == 14) {
            EventLog.getInstance().logEvent(new Event(this.getPlayerName() + " successfully completed the "
                    + "game"));
            logCurrentGameHasEnded();
        }
    }

    // MODIFIES: EventLog
    // EFFECTS: logs that player has crossed the latest prize barrier
    private void logCrossedPrizeBarrier() {
        EventLog.getInstance().logEvent(new Event(this.getPlayerName()
                + " has crossed Prize Barrier #"
                + this.getLatestPrizeBarrier()));
    }

    //Getter
    //EFFECTS: gets question list of player
    public List<Question> getQuestionList() {
        return questionList;
    }

    //Getter
    //EFFECTS: Gets question to question list of player
    public Question getQuestionFromPlayerList(int i) {
        return questionList.get(i);
    }

    //Getter
    //EFFECTS: gets name of Player
    public String getPlayerName() {
        return name;
    }


    //Getter
    //EFFECTS: gets canStartPlay
    public Boolean getCanStartPlay() {
        return canStartPlay;
    }

    //MODIFIES: this
    //EFFECTS: sets Player's status to p
    public void setPlayerCanStartPlay(boolean p) {
        canStartPlay = p;
    }

    //MODIFIES: this
    //EFFECTS: sets Player's status to true
    public void playerCanStartPlay() {
        canStartPlay = true;
    }

    //MODIFIES: this
    //EFFECTS: sets Player's status to false
    public void stopPlayer() {
        canStartPlay = false;
    }

    //Getter
    //EFFECTS: gets getMoveOn
    public String getMoveOn() {
        return moveOn;
    }

    //Getter
    //EFFECTS: gets isPlaying
    public Boolean getIsPlaying() {
        return isPlaying;
    }

    //MODIFIES: this
    //EFFECTS: sets isPlaying to s
    public void setGameStatus(boolean s) {
        isPlaying = s;
    }

    //MODIFIES: this
    //EFFECTS: gets currentQuestionNumber
    public void setCurrentQuestionNumber(int i) {
        currentQuestionNumber = i;
    }

    //Getter
    //EFFECTS: gets currentQuestionNumber
    public int getCurrentQuestionNumber() {
        return currentQuestionNumber;
    }

    //Getter
    //EFFECTS: gets prizeBarrier
    public int getLatestPrizeBarrier() {
        return prizeBarrier;
    }

    //MODIFIES: this
    //EFFECTS: gets prizeBarrier
    public void setLatestPrizeBarrier(int b) {
        prizeBarrier = b;
    }

    //MODIFIES: this
    //EFFECTS: increases prizeBarrier by 1 (0 for none, 1 for question 3, and 2 for question 8)
    public void increasePrizeBarrier() {
        prizeBarrier++;
        logCrossedPrizeBarrier();
    }

    //Getter
    //EFFECTS: gets skipLifelineUsed
    public boolean isSkipLifelineUsed() {
        return skipLifelineUsed;
    }

    //MODIFIES: this
    //EFFECTS: sets skipLifelineUsed to true, indicating lifeline has been used
    public void skipLifelineHasBeenUsed() {
        skipLifelineUsed = true;
    }

    //MODIFIES: this
    //EFFECTS: sets skipLifelineUsed to true, indicating lifeline has been used
    public void setSkipLifelineHasBeenUsed(boolean s) {
        skipLifelineUsed = s;
    }

    // MODIFIES: this
    // EFFECTS: adds question q to player's questionList, for file writing and reading
    public void addQuestionToList(Question q) {
        questionList.add(q);
    }

    // EFFECTS: returns size of questionList
    public int numQuestionsInList() {
        return questionList.size();
    }
}
