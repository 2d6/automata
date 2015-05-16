package automata;

/**
 * A state in a deterministic finite automaton
 * @author 2d6
 */
public class State {
	private final boolean isAccepting;
	private final String identifier;
	
	/**
	 * Creates a new State
	 * @param identifier Identifier of the new state
	 * @param isAccepting Acceptance status of the state. True if the state is accepting.
	 */
	public State(String identifier, boolean isAccepting) {
		this.isAccepting = isAccepting;
		this.identifier = identifier;
	}
	
	public boolean isAccepting() {
		return this.isAccepting;
	}
	
	public String getIdentifier() {
		return this.identifier;
	}	
	
}
