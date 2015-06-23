package automata.states;

import automata.interfaces.IState;

/**
 * Implements a State representing the default state, i.e. the State automata are in after
 * receiving a symbol for which no transition was defined in the previous state. The NullState
 * is a Singleton.
 */
public class NullState implements IState {

	private static final NullState INSTANCE = new NullState();
	private static final String NULL_STATE_ID = "NullState";
	
	private NullState() {
	}

	public static NullState getInstance() {
		return INSTANCE;
	}
	
	@Override
	public boolean isAccepting() {
		return false;
	}

	@Override
	public String getId() {
		return NULL_STATE_ID;
	}


}
