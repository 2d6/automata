package automata.comparators;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import automata.State;
import automata.interfaces.IDeterministicFiniteAutomaton;

public class FiniteAutomatonComparator<T> {

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
