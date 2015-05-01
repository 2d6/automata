package automata;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CharDfaTest {

	private CharDfa newBoolCharDfa(String identifier, boolean isAccepting) {
		CharAlphabet alphabet = new CharAlphabet(new Character[] { '0', '1' });
		SimpleTransitionFunction transitionFunction = new SimpleTransitionFunction(
				alphabet);
		return new CharDfa(identifier, isAccepting, transitionFunction);
	}

	@DataProvider(name = "sampleStates")
	public static Object[][] identifiersAndStates() {
		return new Object[][] { { "S1", true }, { "S2", false }, };
	}

	@DataProvider(name = "whitespaceOrEmptyIdentifiers")
	public static Object[][] whitespaceOrEmptyIdentifiers() {
		return new Object[][] { { "" }, { " " }, { "  " }, { "\0" }, { "\0\0" } };
	}

	@DataProvider(name = "nonexistantStates")
	public static Object[][] nonexistantStates() {
		return new Object[][] { { null, "secondState" },
				{ "startingState", null }, { null, null } };
	}
	
	@Test(dataProvider = "sampleStates")
	public void dfaContainsStartingState(String identifier, boolean isAccepting) {
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
	public void CharactersMayBeEvaluated() {
		CharDfa dfa = newBoolCharDfa("startingState", true);
		dfa.addState("secondState", false);
		dfa.addTransition("startingState", "secondState", '0');
		assertEquals(dfa.evaluate(new Character[] { '0' }),
				dfa.getState("secondState"));
	}

	@Test(dataProvider = "nonexistantStates", expectedExceptions = NullPointerException.class)
	public void transitionsToAndFromNonexistantStatesAreForbidden(
			String startingStateIdentifier, String secondStateIdentifier) {
		CharDfa dfa = newBoolCharDfa("startingState", true);
		dfa.addState("secondState", false);
		dfa.addTransition(startingStateIdentifier, secondStateIdentifier, '0');
	}

	@Test(dataProvider = "whitespaceOrEmptyIdentifiers", expectedExceptions = IllegalArgumentException.class)
	public void whitespaceOrEmptyIdentifiersAreNotAllowed(String identifier) {
		CharDfa dfa = newBoolCharDfa("startingState", true);
		dfa.addState(identifier, true);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void inputContainingIllegalCharactersMayNotBeParsed() {
		CharDfa dfa = newBoolCharDfa("startingState", true);
		String inputString = "102";
		dfa.evaluate(inputString.toCharArray());
	}

}
