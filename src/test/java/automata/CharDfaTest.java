package automata;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CharDfaTest {
	
	@Test(dataProvider = "sampleStates")
	public void startingStateExists(String identifier, boolean isAccepting) {
		CharDfa dfa = newBoolCharDfa(identifier, isAccepting);
		State startingState = dfa.getStartingState();
		assertEquals(identifier, startingState.getIdentifier());
		assertEquals(isAccepting, startingState.isAccepting());
	}

	@Test(dataProvider = "sampleStates")
	public void statesMayBeQueriedByIdentifier(String identifier,
			boolean isAccepting) {
		CharDfa dfa = newBoolCharDfa(identifier, isAccepting);
		State startingState = dfa.getState(identifier);
		assertEquals(identifier, startingState.getIdentifier());
		assertEquals(isAccepting, startingState.isAccepting());
	}

	@Test(dataProvider = "sampleStates")
	public void statesMayBeAdded(String identifier, boolean isAccepting) {
		CharDfa dfa = newBoolCharDfa("startingState", true);
		dfa.addState(identifier, isAccepting);
		State state = dfa.getState(identifier);
		assertEquals(identifier, state.getIdentifier());
		assertEquals(isAccepting, state.isAccepting());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void duplicateStatesMayNotBeAdded() {
		CharDfa dfa = newBoolCharDfa("startingState", true);
		dfa.addState("startingState", false);
	}

	@Test
	public void charactersMayBeEvaluated() {
		CharDfa dfa = newBoolCharDfa("startingState", true);
		dfa.addState("secondState", false);
		dfa.addTransition("startingState", "secondState", '0');
		ArrayList<Character> symbolList = new ArrayList<>();
		symbolList.add('0');
		assertEquals(dfa.evaluate(symbolList),
				dfa.getState("secondState"));
	}
	
	@Test
	public void stringsMayBeEvaluated() {
		CharDfa dfa = newBoolCharDfa("startingState", true);
		dfa.addState("secondState", false);
		dfa.addTransition("startingState", "secondState", '0');
		assertEquals(dfa.evaluate("110"),
				dfa.getState("secondState"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void inputContainingIllegalCharactersMayNotBeEvaluated() {
		CharDfa dfa = newBoolCharDfa("startingState", true);
		String inputString = "102";
		dfa.evaluate(inputString);
	}

	@Test(dataProvider = "nonexistantStates", expectedExceptions = NullPointerException.class)
	public void transitionsContainingNonexistantStatesAreForbidden(
			String startingStateIdentifier, String secondStateIdentifier) {
		CharDfa dfa = newBoolCharDfa("startingState", true);
		dfa.addState("secondState", false);
		dfa.addTransition(startingStateIdentifier, secondStateIdentifier, '0');
	}
	
	private CharDfa newBoolCharDfa(String identifier, boolean isAccepting) {
		CharAlphabet alphabet = new CharAlphabet();
		alphabet.add('0');
		alphabet.add('1');
		SimpleTransitionFunction transitionFunction = new SimpleTransitionFunction(
				alphabet);
		return new CharDfa(identifier, isAccepting, transitionFunction);
	}
	
	@DataProvider(name = "sampleStates")
	public static Object[][] identifiersAndStates() {
		return new Object[][] { { "S1", true }, { "S2", false }, };
	}

	@DataProvider(name = "nonexistantStates")
	public static Object[][] nonexistantStates() {
		return new Object[][] { { null, "secondState" },
				{ "startingState", null }, { null, null } };
	}

}
