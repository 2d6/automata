package automata.comparators;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import automata.AbstractFiniteAutomaton;
import automata.State;

/**
 * Comparator for structural equality of two automata
 * @param <T> type of the symbols of the automata to compare
 */
public class FiniteAutomatonComparator<T> {

	private AbstractFiniteAutomaton<T> automatonA;
	private List<State> visitedStatesA;
	
	private AbstractFiniteAutomaton<T> automatonB;
	private List<State> visitedStatesB;
	
	/**
	 * Determines whether two automata are structurally equal
	 * @param automatonA the first automaton
	 * @param automatonB the second automaton
	 * @return true, if the automata are structurally equal
	 */
	public boolean structurallyEqual(
			AbstractFiniteAutomaton<T> automatonA, 
			AbstractFiniteAutomaton<T> automatonB) {
		
		this.automatonA = automatonA;
		this.visitedStatesA = new ArrayList<>();

		this.automatonB = automatonB;
		this.visitedStatesB = new ArrayList<>();
		
		return this.stateSubGraphIsEqual(automatonA.getStartingState(), automatonB.getStartingState());
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

		Set<T> validSymbolsA = automatonA.getValidSymbols(currentStateA);
		Set<T> validSymbolsB = automatonB.getValidSymbols(currentStateB);

		if (validSymbolsA.equals(validSymbolsB)) {

			/*
			 * Recursively check all subgraphs of the current State; simply returns true if the 
			 * State has no valid Symbols (i.e. transitions) 
			 */

			for (T symbol : validSymbolsA) {
				State nextStateA = automatonA.getNextState(currentStateA, symbol);
				State nextStateB = automatonB.getNextState(currentStateB, symbol);
				if (!stateSubGraphIsEqual(nextStateA, nextStateB)) {
					return false;  
				}
			}
			return true;
		}
		return false;
	}

}
