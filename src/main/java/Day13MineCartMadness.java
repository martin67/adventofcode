import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
class Track {
    Position position;
    char track;
}

enum Turn {Left, Straight, Right}

@Data
@AllArgsConstructor
@Slf4j
class Cart implements Comparable<Cart> {
    int serial;
    Position position;
    char direction;
    Turn nextTurn;

    public int compareTo(Cart c) {
        if (position.getX() < c.getPosition().getX()) {
            return -1;
        } else if (position.getX() > c.getPosition().getX()) {
            return 1;
        } else if (position.getY() < c.getPosition().getY()) {
            return -1;
        } else if (position.getY() > c.getPosition().getY()) {
            return 1;
        } else {
            log.error("Comparison on same position: " + c);
            return 0;
        }
    }
}

@Slf4j
class Day13MineCartMadness {
    private final List<Track> tracks = new ArrayList<>();
    private final List<Cart> carts = new ArrayList<>();

    public Day13MineCartMadness(String input) {
        readMap(input);
    }

    private void readMap(String input) {
        List<String> inputStrings = Arrays.stream(input.split("\\n+"))
                .collect(Collectors.toList());

        int xSize = 0;
        int ySize = inputStrings.size();
        int x;
        int y = 0;
        int serial = 0;

        for (String row : inputStrings) {
            if (row.length() > xSize) {
                xSize = row.length();
            }
            x = 0;
            for (char c : row.toCharArray()) {
                if (c == 'v' || c == '^' || c == '<' || c == '>') {
                    carts.add(new Cart(serial++, new Position(x, y), c, Turn.Left));
                    // add underlying track
                    if (c == 'v' || c == '^') {
                        tracks.add(new Track(new Position(x, y), '|'));
                    } else {
                        tracks.add(new Track(new Position(x, y), '-'));
                    }
                } else if (c == '|' || c == '/' || c == '\\' || c == '-' || c == '+') {
                    tracks.add(new Track(new Position(x, y), c));
                }
                x++;
            }
            y++;
        }
        log.info("Reading map, " + xSize + "x" + ySize);
        log.info("Added " + tracks.size() + " tracks");
        log.info("Added " + carts.size() + " carts");
    }

    private void moveCart(Cart cart) {

        // First move the cart one step forward
        //log.info("Moving cart: " + cart);
        switch (cart.getDirection()) {
            case '^':
                cart.getPosition().setY(cart.getPosition().getY() - 1);
                break;
            case 'v':
                cart.getPosition().setY(cart.getPosition().getY() + 1);
                break;
            case '<':
                cart.getPosition().setX(cart.getPosition().getX() - 1);
                break;
            case '>':
                cart.getPosition().setX(cart.getPosition().getX() + 1);
                break;
        }
        //log.info("Moved cart: " + cart);

        // Check if we need to turn
        Track newTrack = tracks.stream().filter(t -> t.getPosition().equals(cart.getPosition())).findFirst().orElse(null);
        if (newTrack == null) {
            log.error("Could not find track for cart: " + cart);
        }
        switch (newTrack.getTrack()) {
            case '+':
                if (cart.getNextTurn() == Turn.Left && cart.getDirection() == '<') {
                    cart.setDirection('v');
                    cart.setNextTurn(Turn.Straight);
                } else if (cart.getNextTurn() == Turn.Left && cart.getDirection() == '>') {
                    cart.setDirection('^');
                    cart.setNextTurn(Turn.Straight);
                } else if (cart.getNextTurn() == Turn.Left && cart.getDirection() == '^') {
                    cart.setDirection('<');
                    cart.setNextTurn(Turn.Straight);
                } else if (cart.getNextTurn() == Turn.Left && cart.getDirection() == 'v') {
                    cart.setDirection('>');
                    cart.setNextTurn(Turn.Straight);
                } else if (cart.getNextTurn() == Turn.Right && cart.getDirection() == '<') {
                    cart.setDirection('^');
                    cart.setNextTurn(Turn.Left);
                } else if (cart.getNextTurn() == Turn.Right && cart.getDirection() == '>') {
                    cart.setDirection('v');
                    cart.setNextTurn(Turn.Left);
                } else if (cart.getNextTurn() == Turn.Right && cart.getDirection() == '^') {
                    cart.setDirection('>');
                    cart.setNextTurn(Turn.Left);
                } else if (cart.getNextTurn() == Turn.Right && cart.getDirection() == 'v') {
                    cart.setDirection('<');
                    cart.setNextTurn(Turn.Left);
                } else if (cart.getNextTurn() == Turn.Straight) {
                    cart.setNextTurn(Turn.Right);
                }
                break;
            case '/':
                switch (cart.getDirection()) {
                    case '<':
                        cart.setDirection('v');
                        break;
                    case '>':
                        cart.setDirection('^');
                        break;
                    case '^':
                        cart.setDirection('>');
                        break;
                    case 'v':
                        cart.setDirection('<');
                        break;
                    default:
                        log.error("Illegal track for cart " + cart);
                        break;
                }
                break;
            case '\\':
                switch (cart.getDirection()) {
                    case '<':
                        cart.setDirection('^');
                        break;
                    case '>':
                        cart.setDirection('v');
                        break;
                    case '^':
                        cart.setDirection('<');
                        break;
                    case 'v':
                        cart.setDirection('>');
                        break;
                    default:
                        log.error("Illegal track for cart " + cart);
                        break;
                }
                break;

        }

    }

    private Position checkForCollision(Cart cart) {
        for (Cart c : carts) {
            if (c.getPosition().equals(cart.getPosition()) && c.getDirection() != cart.getDirection()) {
                return cart.getPosition();
            }
        }
        return null;
    }

    private Position moveAllCarts() {
        Position collision = null;
        int ticks = 0;
        while (collision == null) {
            //log.info("Moving carts, tick: " + ticks);

            for (Cart cart : carts.stream().sorted().collect(Collectors.toList())) {
                //log.info("Moving cart: " + cart);
                moveCart(cart);
                collision = checkForCollision(cart);
                if (collision != null) {
                    log.info("Collision after " + ticks + " ticks for cart: " + cart);
                    return collision;
                }
            }
            ticks++;
        }
        return collision;
    }

    Position getFirstCollision() {
        return moveAllCarts();
    }

    Position lastCart() {
        Position collision = null;
        int ticks = 0;
        while (carts.size() > 1) {
            log.info("Moving " + carts.size() + " carts, tick: " + ticks);

            for (Cart cart : carts.stream().sorted().collect(Collectors.toList())) {
                //log.info("Moving cart: " + cart);
                moveCart(cart);
                collision = checkForCollision(cart);
                if (collision != null) {
                    log.info("Collision after " + ticks + " ticks for cart: " + cart);
                    carts.removeIf(c -> c.getPosition().equals(cart.getPosition()));
                }
            }
            ticks++;
        }
        return carts.get(0).getPosition();
    }
}
