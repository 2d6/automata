package automata;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import automata.comparators.FiniteAutomatonComparator;
import automata.interfaces.IState;
import automata.interfaces.ITransitionFunction;
import automata.states.NullState;
import automata.states.State;

public abstract class AbstractFiniteAutomaton<T> {

	protected HashMap<String, IState> states;
	protected IState startingState;
	protected ITransitionFunction<T> transitionFunction;
	
	public AbstractFiniteAutomaton() {
		super();
	}
	
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
	public AbstractFiniteAutomaton(String identifier, boolean isAccepting,
			Set<T> symbols) {
		states = new HashMap<>();
		startingState = new State(identifier, isAccepting);
		states.put(identifier, startingState);
		
		if (symbols == null) {
			throw new IllegalArgumentException("Set of symbols may not be null");
		}
		this.transitionFunction = new TransitionFunction<>(symbols);
	}
	
	public AbstractFiniteAutomaton(AbstractFiniteAutomaton<T> otherAutomaton) {
		// Copy the states
		this.states = new HashMap<>();
		Collection<IState> originalStates = otherAutomaton.states.values();
		originalStates.stream().forEach(
				state -> this.addState(state.getId(),
						state.isAccepting()));	
		
		this.startingState = this.getState(otherAutomaton.getStartingState()
				.getId());

		// Copy the transition function, using references to the original
		// symbols
		this.transitionFunction = new TransitionFunction<>();
		Set<T> symbols = otherAutomaton.transitionFunction.getSymbols();
		this.transitionFunction.setSymbols(symbols);

		// Create transitions for all State/symbol combinations which had
		// transitions in the original DFA
		for (IState originalState : originalStates) {
			for (T symbol : symbols) {
				IState targetState = otherAutomaton.transitionFunction
						.getNextState(originalState, symbol);
				if (!NullState.isNullState(targetState)) {
					this.addTransition(originalState.getId(),
							targetState.getId(), symbol);
				}
			}
		}
	}

	public IState getStartingState() {
		return startingState;
	}

	public IState getState(String identifier) {
		return states.get(identifier);
	}

	public void addState(String identifier, boolean isAccepting) {
		if (states.containsKey(identifier)) {
			throw new IllegalArgumentException(
					"The automaton already contained a state with the given identifier");
		}
		states.put(identifier, new State(identifier, isAccepting));
	}

	public void addTransition(String initialStateIdentifier, String targetStateIdentifier, T symbol) {
		IState initialState = getState(initialStateIdentifier);
		IState targetState = getState(targetStateIdentifier);
		transitionFunction.addTransition(initialState, targetState, symbol);
	}

	public Set<T> getValidSymbols(IState currentState) {
		return this.transitionFunction.getValidSymbols(currentState);
	}
	

	public IState getNextState(IState currentState, T symbol) {
		return this.transitionFunction.getNextState(currentState, symbol);
	}
	
	public boolean isStructurallyEqualTo(AbstractFiniteAutomaton<T> otherDfa) {
		return new FiniteAutomatonComparator<T>().structurallyEqual(this, otherDfa);
	}

	/**
	 * Creates a representation of the automaton in the graphviz-Format, as documented on 
	 * http://www.graphviz.org. The output of this method may be rendered using graphviz,
	 * resulting in a graphical representation 
	 * @return A string containing the representation
	 */
	public String toGraphViz() {
		String graphVizTemplate = getGraphVizBaseTemplate();
		
		String acceptingStates = states.values().stream()
					.filter(state -> state.isAccepting())
					.map(state -> state.getId())
					.collect(Collectors.joining(" "));
		
		
		String transitionTemplate = getGraphVizTransitionTemplate();
		String transitions = "";
		for (IState initialState : states.values()) {
			transitions += transitionFunction.getSymbols().stream()
					.map(symbol -> String.format(
							transitionTemplate, 
							initialState.getId(), 
							transitionFunction.getNextState(initialState, symbol).getId(), 
							symbol))
					.collect(Collectors.joining());
		}	
		
		return  String.format(graphVizTemplate, acceptingStates, transitions);
	}
	
	/**
	 * Create a template for a valid graphviz file, with %s placeholders for accepting states and
	 * transitions, respectively. The template may be e.g. parsed with String.format(). 
	 * @return The template
	 */
	protected String getGraphVizBaseTemplate() {
		return "digraph automaton {\nrankdir=LR;\nsize=\"8,5\"\n"
				+ "node [shape = doublecircle];"
				+ "%s;\n"
				+ "node [shape = circle];\n"
				+ "%s"
				+ "}";
	}
	
	/**
	 * Creates a template for the transition portion of a graphViz file, with the string placeholders
	 * for initial state, target state and symbol from left to right. The template may be e.g. parsed with
	 * String.format()
	 * @return The template
	 */
	protected String getGraphVizTransitionTemplate() {
		return "%s -> %s [ label = \"%s\" ];\n";
	}

}