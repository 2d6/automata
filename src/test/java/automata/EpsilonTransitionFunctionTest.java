package automata;

import java.util.HashSet;
import java.util.Set;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class EpsilonTransitionFunctionTest {

	EpsilonTransitionFunction<Object> transitionFunction;
	
	@Mock
	State initialState;
	
	@Mock
	State targetState;
	
	@Mock
	EpsilonTransition epsTransition;
	
	Set<State> expectedStates;
	
	@BeforeMethod
	public void init() {
		MockitoAnnotations.initMocks(this);
		transitionFunction = new EpsilonTransitionFunction<>();
		expectedStates = new HashSet<>();
	}
	
	@Test
	public void expandedStatesContainsInitialStateIfNoEpsilonTransitionsDefined() {
		expectedStates.add(initialState);
		
		Set<State> expandedStates = transitionFunction.getExpandedStates(initialState);
		
		assert(expandedStates.containsAll(expectedStates));
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void expandedStatesThrowsIllegalArgExceptionIfInitialStateNull() {
		transitionFunction.getExpandedStates(null);
	}
	
	@Test
	public void epsilonTransitionMayBeAdded() {
		expectedStates.add(initialState);
		expectedStates.add(targetState);
		
		transitionFunction.addEpsilonTransition(initialState, targetState);
		
		assert(transitionFunction.getExpandedStates(initialState).containsAll(expectedStates));
	}
	
	@Test
	public void multipleEpsilonTransitionsMayBeAdded() {
		
	}
}
