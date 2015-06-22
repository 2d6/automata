package automata;

/**
 * A state in a deterministic finite automaton
 * @author 2d6
 */
public class State {
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
	
	public boolean isAccepting() {
		return this.isAccepting;
	}
	
	public String getIdentifier() {
		return this.id;
	}	
	
	@Override
	public String toString() {
		return this.id + " (" + this.isAccepting + ")";
	}
	
}
