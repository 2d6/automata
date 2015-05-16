package automata;

import java.util.HashSet;
import java.util.Set;

public class EvenNumberOfZeros extends CharDfa {

	public EvenNumberOfZeros() {
		super("S1", true, new TransitionFunction<Character>());
		
		Set<Character> symbols = new HashSet<>();
		symbols.add('0');
		symbols.add('1');
		transitionFunction.setSymbols(symbols);
		
		addState("S2", false);
		
		addTransition("S1", "S2", '0');
		addTransition("S2", "S1", '0');
	}

}
