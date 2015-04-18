package de._2d6.dfa;

/**
 * Represents a state, as e.g. in a DeterministicFiniteAutomaton. A state is
 * discernible from other states solely by its identifier, i.e. states with
 * equal identifiers count as equal even if their remaining attributes differ.
 * 
 * @author 2d6
 * @see DeterministicFiniteAutomaton
 */
public class State {

	private boolean isAccepting;
	private String identifier;

	public State(boolean isAccepting) {
		this.isAccepting = isAccepting;
		this.identifier = "";
	}

	public State(String identifier, boolean isAccepting) {
		this(isAccepting);
		this.identifier = identifier;
	}

	public boolean isAccepting() {
		return this.isAccepting;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((identifier == null) ? 0 : identifier.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;
	}
	
	

}
