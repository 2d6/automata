package automata.clone;

/**
 * A class that implements the Cloneable interface and possesses a public
 * clone() function of type String with no parameters.
 */
public class CloneableWithWrongMethodReturnType implements Cloneable {

	@Override
	public String clone() {
		return new String();
	}
}
