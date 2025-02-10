import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameViewer extends JFrame {
    private static final int WINDOW_HEIGHT = 800;
    private static final int WINDOW_WIDTH = 1000;
    private static final int CARD_WIDTH = 100;
    private static final int CARD_HEIGHT = 150;
    private static final int CARD_SPACING = 20;
    private static final int START_X = 50;
    private static final int PLAYER_Y = 300;
    private static final int DEALER_Y = 100;
    private Game backend;
    private Image backgroundImage;


    public GameViewer(Game back) {
        this.backend = back;

        this.setTitle("Blackjack!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
        // set the background image
        backgroundImage = new ImageIcon("Resources/Background.png").getImage();
    }
    @Override
    public void paint(Graphics g) {
        // Clear the window
        g.drawImage(backgroundImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);

        // Draw the player's and dealer's hands
        // Use hideFirstCard for the players turn, but when it is the dealers turn show it
        boolean hideFirstCard = !backend.isDealerTurn();
        drawHand(g, backend.getDealerHand(), START_X, DEALER_Y, hideFirstCard);

        drawHand(g, backend.getPlayerHand(), START_X, PLAYER_Y, false);

        // Display messages
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 20));

        if (backend.isGameOver()) {
            g.drawString(backend.getGameResult(), START_X, WINDOW_HEIGHT - 20);
        } else {
            g.drawString("Enter 'hit' or 'stay' in the console to continue.", START_X, WINDOW_HEIGHT - 20);
        }
    }
    private void drawHand(Graphics g, ArrayList<Card> hand, int x, int y, boolean hideFirstCard) {
        int cardSpace = CARD_WIDTH + CARD_SPACING;
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            if (hideFirstCard && i == 0) {
                drawCard(g, "Resources/back.png", x + (i * cardSpace), y);
            } else {
                String cardFilename = card.getRank() + card.getSuit() + ".png";
                drawCard(g, "Resources/" + cardFilename, x + (i * cardSpace), y);
            }
        }
    }
    private void drawCard(Graphics g, String imagePath, int x, int y) {
        ImageIcon cardIcon = new ImageIcon(imagePath);
        Image img = cardIcon.getImage();
        g.drawImage(img, x, y, GameViewer.CARD_WIDTH, GameViewer.CARD_HEIGHT, this);

    }
    public void refresh() {
        this.repaint();
    }
}
