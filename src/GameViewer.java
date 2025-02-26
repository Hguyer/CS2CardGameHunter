import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameViewer extends JFrame {
    // make some final variables to use for the window format
    private static final int WINDOW_HEIGHT = 800;
    private static final int WINDOW_WIDTH = 1000;
    private static final int CARD_WIDTH = 100;
    private static final int CARD_HEIGHT = 135;
    private static final int CARD_SPACING = 20;
    private static final int START_X = 300;
    private static final int PLAYER_Y = 600;
    private static final int DEALER_Y = 50;
    // make some other variables to use later
    private Game backend;
    private Image backgroundImage;
    private Image welcomeImage;
    private Image winImage;
    private Image loseImage;
    private boolean welcomeScreen = true;
    private boolean gameOverScreen = false;
    private boolean playerWon;

    // veiwer constructor
    public GameViewer(Game back) {
        this.backend = back;

        this.setTitle("Blackjack!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
        loadImages();

    }
    // set the gameover screen if the player busted
    public void setGameOver(boolean gameOver, boolean playerWon) {
        this.gameOverScreen = gameOver;
        this.playerWon = playerWon;
        repaint();
    }
    // give the image paths for the winning, losing, welcome, and background images
    private void loadImages() {
        welcomeImage = new ImageIcon("Resources/Welcome.png").getImage();
        backgroundImage = new ImageIcon("Resources/Background.png").getImage();
        winImage = new ImageIcon("Resources/Winner.png").getImage();
        loseImage = new ImageIcon("Resources/Loser.png").getImage();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // if we are on the welcome screem paint it
        if (welcomeScreen == true) {
            g.drawImage(welcomeImage, 0, 22, WINDOW_WIDTH, WINDOW_HEIGHT, this);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Type your name if on first screen. Or type anything!", 90, WINDOW_HEIGHT - 30);
        }
        // if we are at the game over screen either show the winner or loser image depending on who won
        else if (gameOverScreen == true) {
            if (playerWon) {
                g.drawImage(winImage, 0, 22, WINDOW_WIDTH, WINDOW_HEIGHT, this);
            } else {
                g.drawImage(loseImage, 0, 22, WINDOW_WIDTH, WINDOW_HEIGHT, this);
            }
            // draw the hand over the image so player knows how they won or lost and how much by
            drawHand(g, backend.getPlayerHand(), START_X, PLAYER_Y, false);
            drawHand(g, backend.getDealerHand(), START_X, DEALER_Y, false);
        }
        // otherwise draw the background image with the cards on top
        else {
                g.drawImage(backgroundImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
                drawHand(g, backend.getPlayerHand(), START_X, PLAYER_Y, false);
                drawHand(g, backend.getDealerHand(), START_X, DEALER_Y, !backend.isDealerTurn());

                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.ITALIC, 30));
                g.drawString("Enter 'hit' or 'stand' in the console to continue.", 150, WINDOW_HEIGHT - 20);
            }
    }
    // make a draw hand method, hide first card should only be true if it is the dealer's hand and your turn
    private void drawHand(Graphics g, ArrayList<Card> hand, int x, int y, boolean hideFirstCard) {
        int cardSpace = CARD_WIDTH + CARD_SPACING;
        // for their hand draw the hand using the rank and suit to build the image path
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
        // draw each card
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
    // refresh is used a lot to repain the screen
    public void refresh() {
        welcomeScreen = false;
        gameOverScreen = false;
        this.repaint();
    }
}
