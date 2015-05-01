package automata;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automata.clone.CloneableObject;
import automata.clone.CloneableObjectWithProtectedCloneMethod;
import automata.clone.CloneableWithWrongMethodArgumentCount;
import automata.clone.CloneableWithWrongMethodReturnType;
import automata.clone.NonCloneableObjectWithPublicCloneMethod;

public class TransitionCloningTest {

	/**
	 * This checks whether the clone() method works as per the documentation of
	 * theObject.clone() method given here:
	 * https://docs.oracle.com/javase/7/docs
	 * /api/java/lang/Object.html#clone%28%29
	 */
	@Test
	public void cloningFulfilsCloneMethodCriteria() {
		Transition<Object> transition = instantiateTransition(Object.class);
		
		@SuppressWarnings("unchecked")
		Transition<Object> clone = (Transition<Object>) transition.clone();
		
		assertTrue(transition != clone);
		assertEquals(transition.getClass(), clone.getClass());
	}

	@Test
	public void clonedTransitionAttributesAreEqual() {
		Transition<Object> transition = instantiateTransition(Object.class);
		
		@SuppressWarnings("unchecked")
		Transition<Object> clone = (Transition<Object>) transition.clone();
		
		assertEquals(clone.getInitialState().getIdentifier(), 
				transition.getInitialState().getIdentifier());
		assertEquals(clone.getTargetState().getIdentifier(), 
				transition.getTargetState().getIdentifier());
		assertEquals(clone.getSymbol(), transition.getSymbol());
	}

	@SuppressWarnings("unchecked")
	@Test(dataProvider = "unproblematicTestClasses")
	public void cloneablesAreCopiedDeeply(Class<?> cls) {
		Transition<Object> objectTransition = instantiateTransition(cls);

		Transition<Object> objectClone = (Transition<Object>) objectTransition.clone();
		
		Object clonedSymbol = (Object) objectClone.getSymbol();
		assertTrue(clonedSymbol != objectTransition.getSymbol());
	}

	@SuppressWarnings("unchecked")
	@Test(dataProvider = "problematicTestClasses")
	public void improperCloneablesAreCopiedShallowly(Class<?> cls) {
		Transition<Object> objectTransition = instantiateTransition(cls);
		
		Transition<Object> clonedTransition = (Transition<Object>) objectTransition.clone();
		
		assertTrue(clonedTransition.getSymbol() == objectTransition.getSymbol());
	}
	
	@DataProvider(name = "problematicTestClasses")
	public static Object[][] problematicTestClasses() {
		return new Object[][] {
				{ CloneableObjectWithProtectedCloneMethod.class },
				{ CloneableWithWrongMethodArgumentCount.class },
				{ CloneableWithWrongMethodReturnType.class } };
	}
	
	@DataProvider(name = "unproblematicTestClasses")
	public static Object[][] unproblematicTestClasses() {
		return new Object[][] {
				{ CloneableObject.class },
				{ NonCloneableObjectWithPublicCloneMethod.class } };
	}

	private Transition<Object> instantiateTransition(Class<?> cls) {
		State state1 = new State("S1", true);
		State state2 = new State("S2", false);
		Object symbol = new Object();
		
		try {
			symbol = cls.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			Assert.fail("Exception was raised during instantiation of test class");
		}
		
		return new Transition<>(state1, state2, symbol);
	}

}
