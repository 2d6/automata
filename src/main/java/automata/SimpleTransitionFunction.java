package automata;

import java.util.ArrayList;
import java.util.List;

public class SimpleTransitionFunction implements TransitionFunction {
	
	private List<Transition> transitions;
	
	public SimpleTransitionFunction() {
		this.transitions = new ArrayList<Transition>();
	}

	/* (non-Javadoc)
	 * @see automata.TransitionFunction#add(automata.State, automata.State, char)
	 */
	public void add(State initialState, State targetState, char symbol) {
		Transition transition = new Transition(initialState, targetState, symbol);
		if (!transitions.contains(transition)) {
			transitions.add(transition);
		}
		else {
			throw new IllegalArgumentException("Transition was already defined within the transition function");
		}
	}

	/* (non-Javadoc)
	 * @see automata.TransitionFunction#get(automata.State, char)
	 */
	public State get(State currentState, char symbol) {
		State state = null;
		
		for (Transition transition : transitions) {
			if (transition.getInitialState() == currentState
					&& transition.getSymbol() == symbol) {
				state = transition.getTargetState();
			}
		}
		
		return state;
	}

}
