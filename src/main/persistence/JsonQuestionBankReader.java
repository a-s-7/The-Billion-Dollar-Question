package persistence;

import model.Question;
import model.QuestionBank;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// CITATION: code modelled from JsonSerializationDemo
// Represents a reader that reads question bank from JSON data stored in file
public class JsonQuestionBankReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonQuestionBankReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads question bank from file and returns it; throws IOException if an error occurs reading data from
    //          file
    public QuestionBank read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);

        return parseQuestionBank(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses question bank from JSON object and returns it
    private QuestionBank parseQuestionBank(JSONObject jsonObject) {
        QuestionBank gameBank = new QuestionBank();

        addQuestionList(gameBank, jsonObject, "Q1", 1);
        addQuestionList(gameBank, jsonObject, "Q2", 2);
        addQuestionList(gameBank, jsonObject, "Q3", 3);
        addQuestionList(gameBank, jsonObject, "Q4", 4);
        addQuestionList(gameBank, jsonObject, "Q5", 5);
        addQuestionList(gameBank, jsonObject, "Q6", 6);
        addQuestionList(gameBank, jsonObject, "Q7", 7);
        addQuestionList(gameBank, jsonObject, "Q8", 8);
        addQuestionList(gameBank, jsonObject, "Q9", 9);
        addQuestionList(gameBank, jsonObject, "Q10", 10);
        addQuestionList(gameBank, jsonObject, "Q11", 11);
        addQuestionList(gameBank, jsonObject, "Q12", 12);
        addQuestionList(gameBank, jsonObject, "Q13", 13);
        addQuestionList(gameBank, jsonObject, "Q14", 14);

        return gameBank;
    }

    // REQUIRES: key, l
    // MODIFIES: gameBank
    // EFFECTS: parses question list with given key and list number from JSON object and adds it to question bank
    private void addQuestionList(QuestionBank b, JSONObject jsonObject, String key, int l) {
        JSONArray jsonArray = jsonObject.getJSONArray(key);

        for (Object json : jsonArray) {
            JSONObject nextQuestion = (JSONObject) json;
            addQuestion(b, nextQuestion, l);
        }
    }

    // REQUIRES: l
    // MODIFIES: gameBank
    // EFFECTS: parses question from JSON object and adds it to question bank with given list number
    private void addQuestion(QuestionBank b, JSONObject jsonObject, int l) {
        String question = jsonObject.getString("Question");
        String opA = jsonObject.getString("Option A");
        String opB = jsonObject.getString("Option B");
        String opC = jsonObject.getString("Option C");
        String opD = jsonObject.getString("Option D");
        String answer = jsonObject.getString("Correct Answer");
        int questionNumber = jsonObject.getInt("Question Number ");

        Question q = new Question(question, opA, opB, opC, opD, answer, questionNumber);

        b.addQuestionToQuestionBank(l, q);
    }


}
