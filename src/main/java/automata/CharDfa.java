package automata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Implements a deterministic finite automaton. For further information, see
 * https://en.wikipedia.org/wiki/Deterministic_finite_automaton
 *
 * @author 2d6
 */
public class CharDfa implements DeterministicFiniteAutomaton<Character> {

	private static final int NOT_IN_LIST = -1;
	protected HashMap<String, State> states;
	protected State startingState;
	protected TransitionFunction<Character> transitionFunction;

	/**
	 * Creates a new automaton with a starting state.
	 *
	 * @param identifier
	 *            The identifier of the starting state
	 * @param isAccepting
	 *            Acceptance status of the starting state. True if the starting
	 *            state is accepting.
	 */
	public CharDfa(String identifier, boolean isAccepting,
			TransitionFunction<Character> transitionFunction) {
		states = new HashMap<>();
		startingState = new State(identifier, isAccepting);
		states.put(identifier, startingState);

		this.transitionFunction = transitionFunction;
	}

	/**
	 * Copy-constructor; creates a new CharDfa with semantics identical to the
	 * supplied originalDfa
	 * 
	 * @param originalDfa
	 *            CharDfa to be used as a blueprint for the new CharDfa
	 */
	private CharDfa(CharDfa originalDfa) {
		// Copy the states
		this.states = new HashMap<>();
		Collection<State> originalStates = originalDfa.states.values();
		for (State originalState : originalStates) {
			this.addState(originalState.getIdentifier(),
					originalState.isAccepting());
		}
		this.startingState = this.getState(originalDfa.getStartingState()
				.getIdentifier());

		// Copy the transition function, using references to the original
		// symbols
		this.transitionFunction = new SimpleTransitionFunction();
		List<Character> symbols = originalDfa.transitionFunction.getSymbols();
		this.transitionFunction.setSymbols(symbols);

		// Create transitions for all State/symbol combinations which had
		// transitions in the original DFA
		for (State originalState : originalStates) {
			for (Character symbol : symbols) {
				State targetState = originalDfa.transitionFunction
						.getNextState(originalState, symbol);
				if (targetState != null) {
					this.addTransition(originalState.getIdentifier(),
							targetState.getIdentifier(), symbol);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see automata.DeterministicFiniteAutomaton#getStartingState()
	 */
	@Override
	public State getStartingState() {
		return startingState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see automata.DeterministicFiniteAutomaton#getState(java.lang.String)
	 */
	@Override
	public State getState(String identifier) {
		return states.get(identifier);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see automata.DeterministicFiniteAutomaton#addState(java.lang.String,
	 * boolean)
	 */
	@Override
	public void addState(String identifier, boolean isAccepting) {
		if (states.containsKey(identifier)) {
			throw new IllegalArgumentException(
					"The automaton already contained a state with the given identifier");
		}
		states.put(identifier, new State(identifier, isAccepting));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * automata.DeterministicFiniteAutomaton#addTransition(java.lang.String,
	 * java.lang.String, T)
	 */
	@Override
	public void addTransition(String initialStateIdentifier,
			String targetStateIdentifier, Character symbol) {
		State initialState = getState(initialStateIdentifier);
		State targetState = getState(targetStateIdentifier);
		transitionFunction.addTransition(initialState, targetState, symbol);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see automata.DeterministicFiniteAutomaton#evaluate(T[])
	 */
	@Override
	public State evaluate(Iterable<Character> input) {
		State currentState = this.startingState;
		State nextState;
		for (Character symbol : input) {
			nextState = evaluate(currentState, symbol);
			currentState = (nextState == null) ? currentState : nextState;
		}
		return currentState;
	}

	/**
	 * Evaluates the characters in a String according to the internal logic of
	 * the automaton.
	 *
	 * @param input
	 *            String of characters to be evaluated
	 * @return The state the automaton was in after evaluating the last
	 *         character in the string
	 */
	public State evaluate(String input) {
		ArrayList<Character> charList = new ArrayList<>();
		for (char ch : input.toCharArray()) {
			charList.add(ch);
		}
		return evaluate(charList);
	}

	/**
	 * Evaluates a single symbol according to the logic of the automaton given
	 * by its states and transition function. Returns the state the automaton
	 * was in after evaluating the symbol.
	 *
	 * @param currentState
	 *            The state the automaton is in before evaluation
	 * @param symbol
	 *            The symbol under evaluation
	 * @return The state the automaton is in after evaluation
	 */
	protected State evaluate(State currentState, Character symbol) {
		return transitionFunction.getNextState(currentState, symbol);
	}

	/**
	 * Creates a new CharDfa semantically identical to the current one
	 * 
	 * @return The new CharDfa
	 */
	@Override
	public CharDfa copy() {
		return new CharDfa(this);
	}

	/**
	 * Determines whether two automata are structurally identical, i.e. will
	 * evaluate identical input to identical output
	 * 
	 * @param otherCharDfa
	 *            CharDfa to compare to
	 * @return True, if the CharDfa are structurally identical
	 */
	public boolean isStructurallyEqualTo(CharDfa otherCharDfa) {
		List<State> visitedStates = new ArrayList<>();
		List<State> visitedStatesOther = new ArrayList<>();
		return CharDfa.stateSubGraphIsEqual(this, getStartingState(),
				otherCharDfa, otherCharDfa.getStartingState(), visitedStates,
				visitedStatesOther);
	}

	private static boolean stateSubGraphIsEqual(CharDfa a, State currentStateA,
			CharDfa b, State currentStateB, List<State> visitedStatesA,
			List<State> visitedStatesB) {

		if (currentStateA.isAccepting() == currentStateB.isAccepting()) {

			/* 
			 * If the states have already been visited, check whether the are
			* equal by their position in the visit history
			*/
			if (visitedStatesA.contains(currentStateA) || visitedStatesB.contains(currentStateB)) {
				return visitedStatesA.indexOf(currentStateA) == visitedStatesB.indexOf(currentStateB);
			}
			
			visitedStatesA.add(currentStateA);
			visitedStatesB.add(currentStateB);

			List<Character> validSymbolsA = a.transitionFunction.getValidSymbols(currentStateA);
			List<Character> validSymbolsB = b.transitionFunction.getValidSymbols(currentStateB);

			if (validSymbolsA.containsAll(validSymbolsB)
					&& validSymbolsB.containsAll(validSymbolsA)) {
				boolean subtreeIsIdentical = true;
				for (Character symbol : validSymbolsA) {
					State nextStateA = a.transitionFunction.getNextState(
							currentStateA, symbol);
					State nextStateB = b.transitionFunction.getNextState(
							currentStateB, symbol);
					subtreeIsIdentical = subtreeIsIdentical
							& stateSubGraphIsEqual(a, nextStateA, b,
									nextStateB, visitedStatesA, visitedStatesB);
				}
				return subtreeIsIdentical;
			}
		}
		return false;
	}
	

}
