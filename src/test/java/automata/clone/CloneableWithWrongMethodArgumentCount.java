package automata.clone;

/**
 * A class that implements the Cloneable interface and possesses a public
 * clone() function of type Object with a non-zero number of parameters
 */
public class CloneableWithWrongMethodArgumentCount {

	public Object clone(int arg) {
		return new Object();
	}

}
