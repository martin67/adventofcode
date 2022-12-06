package aoc.aoc2017;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class Day25TheHaltingProblem {

    State startState;

    private void initDemo() {
        State stateA = new State("A");
        State stateB = new State("B");
        startState = stateA;
        stateA.actions.put(0, new Action(1, 1, stateB));
        stateA.actions.put(1, new Action(0, -1, stateB));
        stateB.actions.put(0, new Action(1, -1, stateA));
        stateB.actions.put(1, new Action(1, 1, stateA));
    }

    private void init() {
        State stateA = new State("A");
        State stateB = new State("B");
        State stateC = new State("C");
        State stateD = new State("D");
        State stateE = new State("E");
        State stateF = new State("F");
        startState = stateA;
        stateA.actions.put(0, new Action(1, 1, stateB));
        stateA.actions.put(1, new Action(0, -1, stateE));
        stateB.actions.put(0, new Action(1, -1, stateC));
        stateB.actions.put(1, new Action(0, 1, stateA));
        stateC.actions.put(0, new Action(1, -1, stateD));
        stateC.actions.put(1, new Action(0, 1, stateC));
        stateD.actions.put(0, new Action(1, -1, stateE));
        stateD.actions.put(1, new Action(0, -1, stateF));
        stateE.actions.put(0, new Action(1, -1, stateA));
        stateE.actions.put(1, new Action(1, -1, stateC));
        stateF.actions.put(0, new Action(1, -1, stateE));
        stateF.actions.put(1, new Action(1, 11, stateA));
    }

    int problem1demo() {
        initDemo();
        return compute(6);
    }

    int problem1() {
        init();
        return compute(12386363);
    }

    private int compute(int steps) {
        Set<Integer> positions = new HashSet<>();
        int position = 0;
        State state = startState;
        for (int i = 0; i < steps; i++) {
            Action action;
            if (positions.contains(position)) {
                action = state.actions.get(1);
            } else {
                action = state.actions.get(0);
            }
            state = action.nextState;
            if (action.write == 1) {
                positions.add(position);
            } else {
                positions.remove(position);
            }
            position += action.move;
        }
        return positions.size();
    }

    class State {
        final String name;
        final Map<Integer, Action> actions = new HashMap<>();

        public State(String name) {
            this.name = name;
        }
    }

    class Action {
        final int write;
        final int move;
        final State nextState;

        public Action(int write, int move, State nextState) {
            this.write = write;
            this.move = move;
            this.nextState = nextState;
        }
    }
}