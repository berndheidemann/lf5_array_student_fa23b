package gameOfLife;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameOfLifeUI extends JFrame {
    private GameOfLife game;
    private JPanel gameField;

    public GameOfLifeUI() {
        game = new GameOfLife();
        inputForInitialzingBoard();
        setVisible(true);
        setTitle("Game of Life");
        setSize(710, 788);
        setLayout(null);
        this.gameField = new JPanel();
        this.gameField.setLayout(new GridLayout(10, 10));
        this.gameField.setBounds(0, 50, 700, 700);
        getContentPane().add(this.gameField);
        Border border = LineBorder.createBlackLineBorder();
        JButton btn = new JButton("neue Generation");
        btn.setBorder(border);
        btn.setFont(new Font("Arial", Font.BOLD, 25));
        btn.setBounds(0, 0, 700, 50);
        btn.addActionListener((e) -> {
            if (game.simulationOver()) {
                JOptionPane.showMessageDialog(this, "Game over!");
                System.exit(0);
            } else {

                game.simulate();
                for (int y = 0; y < 10; y++) {
                    for (int x = 0; x < 10; x++) {
                        ((JLabel) this.gameField.getComponent(y * 10 + x)).setText(game.getCellStatus(y, x) + "");
                    }
                }
                SwingUtilities.updateComponentTreeUI(this);
            }
        });
        getContentPane().add(btn);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                GameFieldJLabel label = new GameFieldJLabel(y, x);
                label.setText(game.getCellStatus(y, x) + "");
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        GameFieldJLabel clicked = (GameFieldJLabel) e.getSource();
                        if (game.getCellStatus(clicked.getyPosi(), clicked.getxPosi()) != '-') {
                            game.setAliveCell(clicked.getyPosi(), clicked.getxPosi());
                            clicked.setText("O");
                        }
                    }
                });
                this.gameField.add(label);
            }
        }
        SwingUtilities.updateComponentTreeUI(this);
    }

    private void inputForInitialzingBoard() {
        try {
            String number = JOptionPane.showInputDialog("Gib die Anzahl der lebenden Zellen der ersten Generation an: ");
            game.initializeBoard(Integer.parseInt(number));
        } catch (RuntimeException e) {
            JOptionPane.showConfirmDialog(this, e.getMessage(), "Falsche Eingabe", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            inputForInitialzingBoard();
        }
    }

    public static void main(String[] args) {
        new GameOfLifeUI();
    }
}

class GameFieldJLabel extends JLabel {

    private int xPosi;
    private int yPosi;

    public int getxPosi() {
        return xPosi;
    }

    public int getyPosi() {
        return yPosi;
    }

    public GameFieldJLabel(int yPosi, int xPosi) {
        super();
        this.xPosi = xPosi;
        this.yPosi = yPosi;
        setSize(50, 50);
        Border border = LineBorder.createBlackLineBorder();
        this.setBorder(border);
        this.setVerticalAlignment(JLabel.CENTER);
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setFont(new Font("Arial", Font.BOLD, 20));
        setOpaque(true);
    }

    public void setText(String text) {
        if (text.equals("O")) {
            setBackground(Color.green);
            super.setText("O");
        } else {
            super.setText(" ");
            setBackground(Color.WHITE);

            GameOfLife gameFoo = new GameOfLife();
            gameFoo.initializeBoard(10);
            gameFoo.setAliveCell(0, 0);
        }
    }
}


