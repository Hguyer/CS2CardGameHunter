import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameViewer extends JFrame {
    private static final int WINDOW_HEIGHT = 500;
    private static final int WINDOW_WIDTH = 800;
    private static final int CARD_WIDTH = 100;
    private static final int CARD_HEIGHT = 150;
    private static final int CARD_SPACING = 20;
    private static final int START_X = 50;
    private static final int PLAYER_Y = 300;
    private static final int DEALER_Y = 100;
    private Game backend;

    public GameViewer(Game back) {
        this.backend = back;

        this.setTitle("Blackjack!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
    }

    private void drawHand(Graphics g, ArrayList<Card> hand, int x, int y, boolean hideFirstCard) {
        for (int i = 0; i < hand.size(); i++) {
            String imagePath;
            if (i == 0 && hideFirstCard) {
                imagePath = "Resources/back.png";
            } else {
                imagePath = "Resources/" + i + ".png";
            }
            drawCard(g, imagePath, x + i * (CARD_WIDTH + CARD_SPACING), y);
        }
    }
    private void drawCard(Graphics g, String imagePath, int x, int y) {
        ImageIcon cardIcon = new ImageIcon(imagePath);
        g.drawImage(cardIcon.getImage(), x, y, CARD_WIDTH, CARD_HEIGHT, this);
    }
}
