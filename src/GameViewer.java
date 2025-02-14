import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameViewer extends JFrame {
    private static final int WINDOW_HEIGHT = 800;
    private static final int WINDOW_WIDTH = 1000;
    private static final int CARD_WIDTH = 100;
    private static final int CARD_HEIGHT = 150;
    private static final int CARD_SPACING = 20;
    private static final int START_X = 300;
    private static final int PLAYER_Y = 500;
    private static final int DEALER_Y = 100;
    private Game backend;
    private Image backgroundImage;
    private Image welcomeImage;
    private Image winImage;
    private Image loseImage;
    private boolean welcomeScreen = true;
    private boolean gameOverScreen = false;
    private boolean playerWon;


    public GameViewer(Game back) {
        this.backend = back;

        this.setTitle("Blackjack!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
        loadImages();

    }
    public void showGameScreen() {
        welcomeScreen = false;
        gameOverScreen = false;
        repaint();
    }
    public void setGameOver(boolean gameOver, boolean playerWon) {
        this.gameOverScreen = gameOver;
        this.playerWon = playerWon;
        repaint();
    }

    private void loadImages() {
        welcomeImage = new ImageIcon("Resources/Welcome.png").getImage();
        backgroundImage = new ImageIcon("Resources/Background.png").getImage();
        winImage = new ImageIcon("Resources/Winner.png").getImage();
        loseImage = new ImageIcon("Resources/Loser.png").getImage();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (welcomeScreen == true) {
            g.drawImage(welcomeImage, 0, 22, WINDOW_WIDTH, WINDOW_HEIGHT, this);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Type your name if on first screen. Else type anything!", 90, WINDOW_HEIGHT - 100);
        }
        else if (gameOverScreen == true) {
            if (playerWon) {
                g.drawImage(winImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
            } else {
                g.drawImage(loseImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
            }
            drawHand(g, backend.getPlayerHand(), START_X, PLAYER_Y, false);
            drawHand(g, backend.getDealerHand(), START_X, DEALER_Y, false);
        }
        else {
                g.drawImage(backgroundImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
                drawHand(g, backend.getPlayerHand(), START_X, PLAYER_Y, false);
                drawHand(g, backend.getDealerHand(), START_X, DEALER_Y, !backend.isDealerTurn());

                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.ITALIC, 30));
                g.drawString("Enter 'hit' or 'stand' in the console to continue.", 100, WINDOW_HEIGHT - 20);
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

    } public void showWelcomeScreen() {
        welcomeScreen = true;
        gameOverScreen = false;
        this.repaint();
    }

    public void showWinScreen() {
        welcomeScreen = false;
        gameOverScreen = true;
        this.repaint();
    }

    public void showLoseScreen() {
        welcomeScreen = false;
        gameOverScreen = true;
        this.repaint();
    }

    public void refresh() {
        welcomeScreen = false;
        gameOverScreen = false;
        this.repaint();
    }
}
