package automata;

import static org.testng.Assert.assertEquals;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class EpsilonTransitionTest {

	@Mock
	State initialState;
	
	@Mock
	State targetState;
	
	@BeforeMethod
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void statesMayBeSet() {
		EpsilonTransition transition = new EpsilonTransition(initialState, targetState);
		
		assertEquals(transition.getInitialState(), initialState);
		assertEquals(transition.getTargetState(), targetState);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class, dataProvider = "nullStates")
	public void transitionThrowsIllegalArgumentExceptionIfStatesNull(State initialState, State targetState) {
		new EpsilonTransition(initialState, targetState);
	}
	
	@DataProvider(name = "nullStates")
	private Object[][] getNullStates() {
		return new Object[][] {
				{null, null},
				{initialState, null},
				{null, targetState}
		};
	}
	
}
