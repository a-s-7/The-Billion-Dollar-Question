package persistence;

import model.Player;

import model.Question;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// CITATION: code modelled from JsonSerializationDemo
// Represents a reader that reads player from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads player from file and returns it; throws IOException if an error occurs reading data from file
    public Player read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayer(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses player from JSON object and returns it
    private Player parsePlayer(JSONObject jsonObject) {
        Player p = new Player();

        boolean isPlaying = jsonObject.getBoolean("Is Playing");
        p.setGameStatus(isPlaying);

        String name = jsonObject.getString("Player Name");
        p.setPlayerName(name);

        p.setMoveOn("L");

        p.setGameStatus(true);

        String moveOn =  jsonObject.getString("Move On");
        p.setMoveOn(moveOn);

        int questionNumber = jsonObject.getInt("Current Question Number");
        p.setCurrentQuestionNumber(questionNumber);

        int barrier = jsonObject.getInt("Prize Barrier");
        p.setLatestPrizeBarrier(barrier);

        boolean skipUsed = jsonObject.getBoolean("Skip Lifeline Used");
        p.setSkipLifelineHasBeenUsed(skipUsed);

        boolean canStartPlay = jsonObject.getBoolean("Can Start Play");
        p.setPlayerCanStartPlay(canStartPlay);

        addPlayerList(p, jsonObject);

        return p;
    }

    // MODIFIES: p
    // EFFECTS: parses player list from JSON object and adds it to player
    private void addPlayerList(Player p, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Question List");

        for (Object json : jsonArray) {
            JSONObject nextQuestion = (JSONObject) json;
            addQuestion(p, nextQuestion);
        }
    }

    // MODIFIES: p
    // EFFECTS: parses question from JSON object and adds it to player
    private void addQuestion(Player p, JSONObject jsonObject) {
        String question = jsonObject.getString("question");
        String opA = jsonObject.getString("option A");
        String opB = jsonObject.getString("option B");
        String opC = jsonObject.getString("option C");
        String opD = jsonObject.getString("option D");
        String answer = jsonObject.getString("correct answer");
        int questionNumber = jsonObject.getInt("question number");
        Question q = new Question(question,opA,opB,opC,opD,answer,questionNumber);
        p.addQuestionToList(q);
    }
}

