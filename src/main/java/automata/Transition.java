package automata;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

/**
 * A transition in a @see{TransitionFunction}. Connects an initial state to a
 * target state with the triggering symbol as a condition.
 * 
 * @author 2d6
 *
 */
public class Transition<T> {

	private static Logger log = Logger.getLogger("Transition");

	private final State initialState;
	private final State targetState;
	private final T symbol;

	public State getInitialState() {
		return initialState;
	}

	public State getTargetState() {
		return targetState;
	}

	public T getSymbol() {
		return symbol;
	}

	/**
	 * Creates a Transition
	 * 
	 * @param initialState
	 *            The initial state of the transition
	 * @param targetState
	 *            The target state of the transition
	 * @param symbol
	 *            The triggering symbol of the transition
	 */
	public Transition(State initialState, State targetState, T symbol) {
		if (initialState == null) {
			throw new NullPointerException("Initial state may not be null");
		} else if (targetState == null) {
			throw new NullPointerException("Target state may not be null");
		} else if (symbol == null) {
			throw new NullPointerException("Symbol may not be null");
		}

		this.initialState = initialState;
		this.targetState = targetState;
		this.symbol = symbol;
	}

	@SuppressWarnings("unchecked")
	// This is necessary because of the cast to T when calling the clone()
	// method via reflection
	@Override
	public Object clone() {
		T newSymbol;

		/*
		 * Deep cloning may only be done if the symbol object possesses a public
		 * clone() method that returns a type which may be cast to T.
		 * 
		 * Reflection has to be used here because of the generic type T
		 */
		try {
			Class<?> cls = symbol.getClass();
			Method method = cls.getMethod("clone", new Class[0]);

			if (!(method.getReturnType().isInstance(symbol.getClass()))) {
				throw new ClassCastException(
						"Clone method could not be cast to the symbol class");
			}
			
			newSymbol = (T) method.invoke(symbol, new Object[0]);

		} catch (NoSuchMethodException | IllegalArgumentException
				| InvocationTargetException | IllegalAccessException e) {
			newSymbol = symbol;
			log.info("Could not deep-clone the transition symbol. Reverted to shallow cloning. Reason: "
					+ e.toString());
		} catch (ClassCastException e) {
			newSymbol = symbol;
			log.info("Could not deep-clone the transition symbol. Reverted to shallow cloning. Reason: "
					+ e.toString());
		}

		return new Transition<>((State) initialState.clone(),
				(State) targetState.clone(), newSymbol);
	}

}
