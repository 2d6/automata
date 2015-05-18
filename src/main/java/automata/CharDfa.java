package automata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import automata.interfaces.IDeterministicFiniteAutomaton;
import automata.interfaces.ITransitionFunction;

/**
 * Implements a deterministic finite automaton. For further information, see
 * https://en.wikipedia.org/wiki/Deterministic_finite_automaton
 *
 * @author 2d6
 */
public class CharDfa implements IDeterministicFiniteAutomaton<Character> {

	protected HashMap<String, State> states;
	protected State startingState;
	protected ITransitionFunction<Character> transitionFunction;

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
			ITransitionFunction<Character> transitionFunction) {
		states = new HashMap<>();
		startingState = new State(identifier, isAccepting);
		states.put(identifier, startingState);
		
		if (transitionFunction == null) {
			throw new IllegalArgumentException("Transition function may not be null");
		}
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
		this.transitionFunction = new TransitionFunction<>();
		Set<Character> symbols = originalDfa.transitionFunction.getSymbols();
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

	@Override
	public State getStartingState() {
		return startingState;
	}

	@Override
	public State getState(String identifier) {
		return states.get(identifier);
	}

	@Override
	public void addState(String identifier, boolean isAccepting) {
		if (states.containsKey(identifier)) {
			throw new IllegalArgumentException(
					"The automaton already contained a state with the given identifier");
		}
		states.put(identifier, new State(identifier, isAccepting));
	}

	@Override
	public void addTransition(String initialStateIdentifier,
			String targetStateIdentifier, Character symbol) {
		State initialState = getState(initialStateIdentifier);
		State targetState = getState(targetStateIdentifier);
		transitionFunction.addTransition(initialState, targetState, symbol);
	}

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

	@Override
	public boolean isStructurallyEqualTo(CharDfa otherCharDfa) {
		List<State> visitedStates = new ArrayList<>();
		List<State> visitedStatesOther = new ArrayList<>();
		return CharDfa.stateSubGraphIsEqual(this, getStartingState(),
				otherCharDfa, otherCharDfa.getStartingState(), visitedStates,
				visitedStatesOther);
	}

	private static boolean stateSubGraphIsEqual(CharDfa dfaA, State currentStateA,
			CharDfa dfaB, State currentStateB, List<State> visitedStatesA,
			List<State> visitedStatesB) {

		if (currentStateA.isAccepting() != currentStateB.isAccepting()) {
			return false;
		} 
		else if (visitedStatesA.contains(currentStateA) || visitedStatesB.contains(currentStateB)) {
			return visitedStatesA.indexOf(currentStateA) == visitedStatesB.indexOf(currentStateB);
		}

		/* 
		 * If the states have already been visited, check whether the are
		 * equal by their position in the visit history
		 */

		visitedStatesA.add(currentStateA);
		visitedStatesB.add(currentStateB);

		Set<Character> validSymbolsA = dfaA.getValidSymbols(currentStateA);
		Set<Character> validSymbolsB = dfaB.getValidSymbols(currentStateB);

		if (validSymbolsA.equals(validSymbolsB)) {

			/*
			 * Recursively check all subgraphs of the current State
			 */

			for (Character symbol : validSymbolsA) {
				State nextStateA = dfaA.getNextState(currentStateA, symbol);
				State nextStateB = dfaB.getNextState(currentStateB, symbol);
				if (!stateSubGraphIsEqual(dfaA, nextStateA, dfaB,
								nextStateB, visitedStatesA, visitedStatesB)) {
					return false;  
				}
			}
			return true;
		}
		return false;
	}
	
	/*
	 * Wrapper for the homonymous method of the {@link TransitionFunction}
	 */
	private State getNextState(State currentState, Character symbol) {
		return this.transitionFunction.getNextState(currentState, symbol);
	}

	/*
	 * Wrapper for the homonymous method of the {@link TransitionFunction}
	 */
	private Set<Character> getValidSymbols(State currentState) {
		return this.transitionFunction.getValidSymbols(currentState);
	}
	
}
