package edu.wpi.cs3733.D21.teamB.entities.chatbot;

import java.util.ArrayList;
import java.util.List;

public class StateManager {

    // case insensitive
    private static final String[] COVID_KEYWORDS = {"covid", "survey"};
    private static final String[] REGISTER_KEYWORDS = {"register", "make", "user", "new", "account"};
    private static final String[] LOGIN_KEYWORDS = {"login", "log", "in"};
    private static final String[] PATHFINDING_KEYWORDS = {"where", "hospital", "pathfinding", "directions", "map", "path"};
    private static final String[] GOOGLE_MAPS_KEYWORDS = {"google", "maps", "drive", "navigation", "Boston"};

    private IState previousState;
    private IState currentState;

    /**
     * Based on the message, determines into what state
     * the
     *
     * @param input the message to check
     */
    public List<String> respond(String input) {
        // If switching tracks, reset everything back to the beginning
        if (containsAny(input, COVID_KEYWORDS) && !(currentState instanceof CovidState)) {
            previousState = currentState;
            currentState = new CovidState();
        } else if (containsAny(input, REGISTER_KEYWORDS) && !(currentState instanceof RegisterState)) {
            previousState = currentState;
            currentState = new RegisterState();
        } else if (containsAny(input, LOGIN_KEYWORDS) && !(currentState instanceof LoginState)) {
            previousState = currentState;
            currentState = new LoginState();
        } else if (containsAny(input, PATHFINDING_KEYWORDS) && !(currentState instanceof PathfindingState)) {
            previousState = currentState;
            currentState = new PathfindingState();
        } else if (containsAny(input, GOOGLE_MAPS_KEYWORDS) && !(currentState instanceof GoogleMapsState)) {
            previousState = currentState;
            currentState = new GoogleMapsState();
        } else if (currentState == null) {
            return new ArrayList<>();
        }

        // Let the current state deal with the response, and if null is returned, try with the previous state
        List<String> response = currentState.respond(input);
        if (response.isEmpty()) {
            setCurrentToPrev();

            if (currentState != null)
                response = currentState.respond(input);
        }
        return response;
    }

    public void setCurrentToPrev() {
        currentState = previousState;
        previousState = null;
    }

    public void reset() {
        currentState = null;
        previousState = null;
    }

    /**
     * Given n amount of words and a starting message,
     * checks whether the message contains any of them
     *
     * @param message the message to check
     * @param toCheck list of things to check
     * @return true if message has one of those in it
     */
    public static boolean containsAny(String message, String[] toCheck) {
        String[] words = message.split("\\W+");
        boolean contains = false;
        for (String word : words) {
            for (String s : toCheck) {
                if (word.equals(s)) {
                    contains = true;
                    break;
                }
            }
        }
        return contains;
    }
}