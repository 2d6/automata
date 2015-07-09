package automata.graphviz;

import java.util.Set;
import java.util.stream.Collectors;

import automata.NondeterministicFiniteAutomaton;
import automata.interfaces.IAbstractFiniteAutomaton;
import automata.interfaces.IDeterministicFiniteAutomaton;
import automata.interfaces.INondeterministicFiniteAutomaton;
import automata.interfaces.IState;

/**
 * Provides methods to create representations of automata as per the graphviz
 * graph vizualization software (documented on http://www.graphviz.org).
 */
public class GraphVizConverter {
	
	public static final String EPSILON = NondeterministicFiniteAutomaton.EPSILON;
	
	private static final String GRAPHVIZ_BASE_TEMPLATE = 
			"digraph automaton {\nrankdir=LR;\nsize=\"8,5\"\n"
			+ "node [shape = doublecircle];"
			+ "%s;\n"
			+ "node [shape = circle];\n"
			+ "%s"
			+ "}"; 
	
	private static final String GRAPHVIZ_TRANSITION_TEMPLATE = "%s -> %s [ label = \"%s\" ];\n";

	/**
	 * Creates a representation of an {@link IDeterministicFiniteAutomaton} in 
	 * the graphviz-Format, as documented on http://www.graphviz.org.
	 * The output of this method may be rendered using graphviz,
	 * resulting in a graphical representation 
	 * @param <T>
	 * @return A string containing the representation
	 */
	public static <T> String convert(IDeterministicFiniteAutomaton<T> dfa) {
		if (dfa == null) {
			throw new IllegalArgumentException("Automaton may not be null");
		}
		return  generateGraphViz(dfa, "");	
	}
	
	/**
	 * Creates a representation of an {@link INondeterministicFiniteAutomaton} in 
	 * the graphviz-Format, as documented on http://www.graphviz.org. 
	 * The output of this method may be rendered using graphviz,
	 * resulting in a graphical representation 
	 * @param <T>
	 * @return A string containing the representation
	 */
	public static <T> String convert(INondeterministicFiniteAutomaton<T> nfa) {
		if (nfa == null) {
			throw new IllegalArgumentException("Automaton may not be null");
		}
		
		Set<IState> states = nfa.getStates();
		
		String epsilonTransitionString = "";
		for (IState initialState : states) {
			epsilonTransitionString += getEpsilonTransitionsFromState(nfa, initialState);
		}
		
		return generateGraphViz(nfa, epsilonTransitionString);	
	}
	
	private static<T> String generateGraphViz(
			IAbstractFiniteAutomaton<T> automaton, String epsilonTransitionString) {
		Set<IState> states = automaton.getStates();
		String acceptingStates = getAcceptingStateIds(states);
		
		String transitions = "";
		for (IState initialState : states) {
			transitions += getTransitionsFromState(automaton, initialState);
		}	
		transitions += epsilonTransitionString;
		
		return  String.format(GRAPHVIZ_BASE_TEMPLATE, acceptingStates, transitions);	
	}
	
	private static String getAcceptingStateIds(Set<IState> states) {
		return states.stream()
					.filter(state -> state.isAccepting())
					.map(state -> state.getId())
					.collect(Collectors.joining(" "));	
	}
	
	private static <T> String getTransitionsFromState(
			IAbstractFiniteAutomaton<T> automaton, IState initialState) {
		return automaton.getAllSymbols().stream()
				.map(symbol -> String.format(
						GRAPHVIZ_TRANSITION_TEMPLATE, 
						initialState.getId(), 
						automaton.getNextState(initialState, symbol).getId(), 
						symbol))
				.collect(Collectors.joining());
	}
	
	private static <T> String getEpsilonTransitionsFromState(
			INondeterministicFiniteAutomaton<T> nfa, IState initialState) {
		return nfa.getExpandedStates(initialState).stream()
				.filter(state -> !state.getId().equals(initialState.getId()))
				.map(state -> String.format(
						GRAPHVIZ_TRANSITION_TEMPLATE,
						initialState.getId(),
						state.getId(),
						EPSILON))
				.collect(Collectors.joining());
	}
	
	
}
