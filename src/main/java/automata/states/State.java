package automata.states;

import automata.interfaces.IState;

/**
 * A state in a deterministic finite automaton
 * @author 2d6
 */
public class State implements IState {
	private final boolean isAccepting;
	private final String id;
	
	/**
	 * Creates a new State
	 * @param id Identifier of the new state
	 * @param isAccepting Acceptance status of the state. True if the state is accepting.
	 */
	public State(String id, boolean isAccepting) {
		this.isAccepting = isAccepting;
		this.id = id;
	}
	
	/* (non-Javadoc)
	 * @see automata.IState#isAccepting()
	 */
	@Override
	public boolean isAccepting() {
		return this.isAccepting;
	}
	
	/* (non-Javadoc)
	 * @see automata.IState#getId()
	 */
	@Override
	public String getId() {
		return this.id;
	}	
	
	@Override
	public String toString() {
		return this.id + " (" + this.isAccepting + ")";
	}
	
}
