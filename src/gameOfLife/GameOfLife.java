package gameOfLife;

import java.util.Random;

public class GameOfLife {

    private char[][] petridish;
    private final char DEAD_CELL = ' ';
    private final char ALIVE_CELL = 'O';
    private boolean generationHadChanges = true;

    /*
    public void initializeBoard(int number) initialisiert die Petrischale mit der ihr übergebenen Anzahl an
    lebenden Zellen, die zufallsbestimmt in die Petrischale gesetzt werden. Sie wirft eine RuntimeException,
    wenn die übergebene Anzahl nicht im Intervall von 1 bis 100 liegt. Die Petrischale ist zwar nur 10 x 10
    Felder groß, für die späteren Überprüfungen ist es allerdings sehr nützlich, die Außenränder um jeweils
    eine Reihe oben und unten und um jeweils eine weitere Spalte rechts und links zu erweitern, so dass die
    Petrischale eine Größe von 12x12 Feldern hat. Die eigentliche Petrischale befindet sich dann auf den
    Koordianten 1, 1 bis 10, 10.
     */
    public void initializeBoard(int number) {
        if (number < 0 || number > 100) {
            throw new RuntimeException("Die Anzahl der Zellen muss im Intervall 1 bis 100 liegen!");
        }
        this.petridish = new char[12][12];
        for (int row = 0; row <= 11; row++) {
            for (int col = 0; col <= 11; col++) {
                this.petridish[row][col] = DEAD_CELL;
            }
        }
        Random rnd = new Random();
        while (number > 0) {
            int row = rnd.nextInt(10) + 1; // 1,2,3,4,5,6,7,8,9,10
            int col = rnd.nextInt(10) + 1;
            if (getCellStatus(row, col) == DEAD_CELL) {
                setCellStatus(row, col, ALIVE_CELL);
                number--;
            }
        }
    }


    /*
    public char[][] getBoard() gibt die Petrischale im Format 10 x 10 Felder zurück.
     */
    public char[][] getBoard() {
        char field[][] = new char[10][10];
        // 1,2,3,4,5,6,7,8,9,10
        for (int row = 1; row <= 10; row++) {
            // 1,2,3,4,5,6,7,8,9,10
            for (int col = 1; col <= 10; col++) {
                field[row - 1][col - 1] = petridish[row][col];
            }
        }
        return field;
    }

    /*
    public boolean setAliveCell(int row, int col) setzt an die Stelle row, col
    eine lebende Zelle in die Petrischale. Die Methode gibt true zurück, wenn das
    Setzen erfolgreich war, d.h. die Zelle an den Koordinaten row, col tot war
    und durch den Aufruf auf lebend gesetzt werden konnte. Lebt die Zelle an den
    Koordinaten, wird false zurückgegeben.
     */
    public boolean setAliveCell(int row, int col) {
        // prüfen ob Zelle lebendig
        if (getCellStatus(row, col) == ALIVE_CELL) {
            // falls ja --> return false
            return false;
        } else {
            // false nein --> Zustand auf lebendig setzen und return true
            setCellStatus(row, col, ALIVE_CELL);
            return true;
        }
    }

    private void setCellStatus(int row, int col, char cellStatus) {
        // Zugriff auf das entsprechende "Schubfach" von petridish
        this.petridish[row + 1][col + 1] = cellStatus;
    }

    /*
    public char getCellStatus(int row, int col) gibt den Status der Zelle an der
    Stelle row, col in der Petrischale zurück, also 'O' für eine lebende Zelle
    oder ' ' für eine tote Zelle.
     */
    public char getCellStatus(int row, int col) {
        return this.petridish[row + 1][col + 1];
    }

    public int getAliveCells() {
        int counter = 0;
        for (int row = 0; row <= 10; row++) {
            for (int col = 0; col <= 10; col++) {
                if (getCellStatus(row, col) == ALIVE_CELL) {
                    counter++;
                }
            }
        }
        return counter;
    }

    private int countNeigbours(int row, int col) {
        /*
                    ' '      'O'     ' '
                    ' '       X      ' '
                    'O'      'O'     ' '

                    return --> 3
         */
        int count = 0;
        // oben links
        if (getCellStatus(row - 1, col - 1) == ALIVE_CELL) {
            count++;
        }
        // oben
        if (getCellStatus(row - 1, col) == ALIVE_CELL) {
            count++;
        }
        // oben rechts
        if (getCellStatus(row - 1, col + 1) == ALIVE_CELL) {
            count++;
        }
        // rechts
        if (getCellStatus(row, col + 1) == ALIVE_CELL) {
            count++;
        }
        // unten rechts
        if (getCellStatus(row + 1, col + 1) == ALIVE_CELL) {
            count++;
        }
        // unten
        if (getCellStatus(row + 1, col) == ALIVE_CELL) {
            count++;
        }
        // unten links
        if (getCellStatus(row + 1, col - 1) == ALIVE_CELL) {
            count++;
        }
        // links
        if (getCellStatus(row, col - 1) == ALIVE_CELL) {
            count++;
        }
        return count;
    }

    public void simulate() {
        /*
        Eine Zelle wird (unabhängig von ihrem derzeitigen Zustand) in der nächsten
        Generation tot sein, wenn sie in der jetzigen Generation weniger als zwei
         oder mehr als drei lebende Nachbarn besitzt.

        Eine Zelle mit genau zwei lebenden Nachbarn ändert ihren Zustand nicht.

        Eine Zelle mit genau drei lebenden Nachbarn wird sich in der nächsten
        Generation im Zustand lebendig befinden.
         */

        char[][] newGeneration = new char[10][10];

        generationHadChanges = false;
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                // Anzahl der Nachbarn der Zelle ermitteln
                int neighbourCount = countNeigbours(row, col);
                // Regeln nacheinander prüfen
                if (neighbourCount < 2 || neighbourCount > 3) {
                    // Zelle stirbt bzw. bleibt tot
                    if (getCellStatus(row, col) == ALIVE_CELL) {
                        generationHadChanges = true;
                    }
                    newGeneration[row][col] = DEAD_CELL;
                } else if (neighbourCount == 2) {
                    newGeneration[row][col] = getCellStatus(row, col);
                } else if (neighbourCount == 3) {
                    if (getCellStatus(row, col) == DEAD_CELL) {
                        generationHadChanges = true;
                    }
                    newGeneration[row][col] = ALIVE_CELL;
                }
            }
        }
        // Spielfeld verändern
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                setCellStatus(row, col, newGeneration[row][col]);
            }
        }
        int i = 1;
    }

    public boolean simulationOver() {
        if (getAliveCells() == 0) {
            return true;
        }
        if (!generationHadChanges) {
            return true;
        }
        return false;
    }
}