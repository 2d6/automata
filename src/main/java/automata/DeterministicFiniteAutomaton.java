package automata;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import automata.interfaces.IDeterministicFiniteAutomaton;

/**
 * Implements a deterministic finite automaton. For further information, see
 * https://en.wikipedia.org/wiki/Deterministic_finite_automaton
 *
 * @author 2d6
 */
public class DeterministicFiniteAutomaton<T> extends AbstractFiniteAutomaton<T> implements IDeterministicFiniteAutomaton<T> {

	/**
	 * Creates a new automaton with a starting state.
	 *
	 * @param identifier
	 *            The identifier of the starting state
	 * @param isAccepting
	 *            Acceptance status of the starting state. True if the starting
	 *            state is accepting.
	 * @param symbols The alphabet of the automaton
	 */
	public DeterministicFiniteAutomaton(String identifier, boolean isAccepting,
			Set<T> symbols) {
		super(identifier, isAccepting, symbols);
	}

	/**
	 * Copy-constructor; creates a new {@link DeterministicFiniteAutomaton} with semantics identical to the
	 * supplied originalDfa
	 * 
	 * @param originalDfa
	 *            CharDfa<T> to be used as a blueprint for the new CharDfa<T>
	 */
	private DeterministicFiniteAutomaton(DeterministicFiniteAutomaton<T> originalDfa) {
		super(originalDfa);
	}
	
	/**
	 * Creates a new {@link DeterministicFiniteAutomaton} semantically identical to the current one
	 * 
	 * @return The new Dfa
	 */
	public DeterministicFiniteAutomaton<T> copy() {
		return new DeterministicFiniteAutomaton<T>(this);
	}

	public boolean isStructurallyEqualTo(IDeterministicFiniteAutomaton<T> otherCharDfa) {
		return new DfaStructureComparator().structurallyEqual(this, otherCharDfa);
	}
	
	public State evaluate(List<T> input) {
		State currentState = this.startingState;
		State nextState;
		for (T symbol : input) {
			if (!transitionFunction.getSymbols().contains(symbol)) {
				throw new IllegalArgumentException("Encountered illegal symbol: " + symbol);
			}
			nextState = evaluate(currentState, symbol);
			if (!states.values().contains(nextState)) {
				return nextState;
			}
			currentState = nextState;
		}
		return currentState;
	}
	
	/**
	 * Evaluates a single symbol according to the logic of the automaton given
	 * by its states and transition function. Returns the new state the automaton
	 * is in after evaluating the symbol, or null if no transition has been defined.
	 *
	 * @param currentState
	 *            The state the automaton is in before evaluation
	 * @param symbol
	 *            The symbol under evaluation
	 * @return The state the automaton is in after evaluation, or null if no transition
	 * has been defined.
	 */
	private State evaluate(State currentState, T symbol) {
		return transitionFunction.getNextState(currentState, symbol);
	}
	
	private class DfaStructureComparator {
		private IDeterministicFiniteAutomaton<T> dfaA;
		private List<State> visitedStatesA;
		
		private IDeterministicFiniteAutomaton<T> dfaB;
		private List<State> visitedStatesB;
		
		public boolean structurallyEqual(
				IDeterministicFiniteAutomaton<T> dfaA, 
				IDeterministicFiniteAutomaton<T> dfaB) {
			
			this.dfaA = dfaA;
			this.visitedStatesA = new ArrayList<>();

			this.dfaB = dfaB;
			this.visitedStatesB = new ArrayList<>();
			
			return this.stateSubGraphIsEqual(dfaA.getStartingState(), dfaB.getStartingState());
		}
		
		private boolean stateSubGraphIsEqual(State currentStateA, State currentStateB) {

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

			Set<T> validSymbolsA = dfaA.getValidSymbols(currentStateA);
			Set<T> validSymbolsB = dfaB.getValidSymbols(currentStateB);

			if (validSymbolsA.equals(validSymbolsB)) {

				/*
				 * Recursively check all subgraphs of the current State; simply returns true if the 
				 * State has no valid Symbols (i.e. transitions) 
				 */

				for (T symbol : validSymbolsA) {
					State nextStateA = dfaA.getNextState(currentStateA, symbol);
					State nextStateB = dfaB.getNextState(currentStateB, symbol);
					if (!stateSubGraphIsEqual(nextStateA, nextStateB)) {
						return false;  
					}
				}
				return true;
			}
			return false;
		}

	}
	
}
