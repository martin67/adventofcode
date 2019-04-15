import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class Day9MarbleMania {

    @Data
    @RequiredArgsConstructor
    class Marble {
        final int id;
        Marble left;
        Marble right;
    }

    @Data
    @RequiredArgsConstructor
    class Player {
        final int id;
        int score;
    }

    int computeScore(int players, int points) {

        List<Player> playerList = new ArrayList<>();
        for (int i = 0; i < players; i++) {
            playerList.add(new Player(i + 1));
        }

        Marble start = new Marble(0);
        start.setLeft(start);
        start.setRight(start);
        Marble current = start;
        int marblesInGame = 1;

        for (int i = 1; i < points + 1; i++) {

            Marble newMarble = new Marble(i);
            marblesInGame++;
            Player currentPlayer = playerList.get((i - 1) % playerList.size());

            if ((i % 23) == 0) {
                currentPlayer.score += i;
                // remove marble 7 steps to the left
                Marble marbleToRemove = current;
                for (int j = 0; j < 7; j++) {
                    marbleToRemove = marbleToRemove.getLeft();
                }
                marbleToRemove.getLeft().setRight(marbleToRemove.getRight());
                marbleToRemove.getRight().setLeft(marbleToRemove.getLeft());
                currentPlayer.score += marbleToRemove.getId();
                current = marbleToRemove.getRight();
                marblesInGame -= 2;
            } else {
                // insert new marble to the right
                newMarble.setLeft(current.getRight());
                newMarble.setRight(current.getRight().getRight());
                current.getRight().getRight().setLeft(newMarble);
                current.getRight().setRight(newMarble);
                current = newMarble;
            }

            /*String output = "[" + currentPlayer.getId() + "] ";
            Marble printMarble = start;
            for (int j = 0; j < marblesInGame; j++) {
                if (current == printMarble) {
                    output += "(" + printMarble.getId() + ")";
                } else {
                    output += " " + printMarble.getId() + " ";
                }
                printMarble = printMarble.getRight();
            }
            log.info(output);*/

        }

        // Find max score
        Player highScore = playerList.stream().max(Comparator.comparingInt(Player::getScore)).get();
        return highScore.getScore();
    }

}