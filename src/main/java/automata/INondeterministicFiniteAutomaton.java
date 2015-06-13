package automata;

import java.util.List;
import java.util.Set;

public interface INondeterministicFiniteAutomaton<T> {

	public abstract void addEpsilonTransition(String initialStateIdentifier,
			String targetStateIdentifier);

	public abstract Set<State> evaluate(List<T> input);

}