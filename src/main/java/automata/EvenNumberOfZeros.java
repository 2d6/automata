package automata;

import java.util.Arrays;
import java.util.HashSet;

public class EvenNumberOfZeros extends StringDfa {

	public EvenNumberOfZeros() {
		super("S1", true, new HashSet<Character>(Arrays.asList('0', '1')));
		
		addState("S2", false);
		
		addTransition("S1", "S2", '0');
		addTransition("S1", "S1", '1');
		addTransition("S2", "S1", '0');
		addTransition("S2", "S2", '1');
	}

}
