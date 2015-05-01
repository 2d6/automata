package automata;

public class EvenNumberOfZeros extends CharDfa {

	public EvenNumberOfZeros() {
		super("S1", true, new SimpleTransitionFunction());
		CharAlphabet alphabet = new CharAlphabet(new Character[]{'0','1'});
		transitionFunction.setAlphabet(alphabet);
		
		addState("S2", false);
		
		addTransition("S1", "S2", '0');
		addTransition("S2", "S1", '0');
	}

}
