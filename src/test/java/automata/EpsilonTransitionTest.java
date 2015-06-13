package automata;

import static org.testng.Assert.assertEquals;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
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
	
}
