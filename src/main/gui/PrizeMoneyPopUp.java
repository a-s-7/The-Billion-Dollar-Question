package gui;

import javax.swing.*;
import java.awt.*;

// Represents PrizeMoney Pop-Up for BillionDollarGUI
public class PrizeMoneyPopUp {
    private JFrame popup;
    private Container con;
    private Font font = new Font("Oswald", Font.PLAIN, 25);

    // REQUIRES: currentQuestion & buttonNumber
    // EFFECTS: creates a new prize-money pop-up given currentQuestion and buttonNumber
    public PrizeMoneyPopUp(int currentQuestion, int buttonNumber) {
        createPopUp(currentQuestion,buttonNumber);
    }

    // REQUIRES: currentQuestion & buttonNumber
    // EFFECTS: creates a new prize-money pop-up by instantiating a new frame, adding a container, creating an info
    //          panel, gets question status, creates label with status and adds it to panel, adds panel to container.
    public void createPopUp(int currentQuestion, int buttonNumber) {
        String title = "Question - " + buttonNumber;

        popup = new JFrame();
        popup.setSize(320, 75);
        popup.setTitle(title);
        popup.getContentPane().setBackground(Color.black);
        popup.setLayout(null);
        popup.setResizable(false);
        popup.setVisible(true);

        con = popup.getContentPane();

        JPanel info = new JPanel();
        info.setBounds(0,0,320,75);
        info.setBackground(Color.red);

        String status = getQuestionStatus(currentQuestion, buttonNumber);
        JLabel infoLabel = new JLabel("STATUS: " + status);
        infoLabel.setForeground(Color.white);
        infoLabel.setFont(font);
        info.add(infoLabel);

        con.add(info);
    }

    // REQUIRES: currentQuestion & buttonNumber
    // EFFECTS: returns status of question, given current question (c), and button number (b).
    //              If current question is less than button number, returns "LOCKED".
    //              If current question is greater than button number, returns "UNLOCKED".
    //              Else returns "CURRENTLY ON".
    public String getQuestionStatus(int c, int b) {
        if (c < b) {
            return "LOCKED";
        } else if (c > b) {
            return "UNLOCKED";
        } else {
            return "CURRENTLY ON";
        }
    }
}
