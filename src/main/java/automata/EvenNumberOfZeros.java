package automata;

import java.util.ArrayList;
import java.util.List;

public class EvenNumberOfZeros extends CharDfa {

	public EvenNumberOfZeros() {
		super("S1", true, new SimpleTransitionFunction());
		
		List<Character> symbols = new ArrayList<>();
		symbols.add('0');
		symbols.add('1');
		transitionFunction.setSymbols(symbols);
		
		addState("S2", false);
		
		addTransition("S1", "S2", '0');
		addTransition("S2", "S1", '0');
	}

}
