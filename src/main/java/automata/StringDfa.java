package automata;

import java.util.ArrayList;
import java.util.Set;

public class StringDfa extends DeterministicFiniteAutomaton<Character> {

	public StringDfa(String identifier, boolean isAccepting,
			Set<Character> symbols) {
		super(identifier, isAccepting, symbols);
	}
	
	/**
	 * Evaluates the characters in a String according to the internal logic of
	 * the automaton.
	 *
	 * @param input
	 *            String of characters to be evaluated
	 * @return The state the automaton was in after evaluating the last
	 *         character in the string
	 */
	public State evaluate(String input) {
		ArrayList<Character> charList = new ArrayList<>();
		for (char ch : input.toCharArray()) {
			charList.add(ch);
		}
		return evaluate(charList);
	}

}
