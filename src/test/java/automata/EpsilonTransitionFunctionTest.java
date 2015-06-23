package automata;

import java.util.HashSet;
import java.util.Set;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automata.interfaces.IState;


public class EpsilonTransitionFunctionTest {

	EpsilonTransitionFunction<Object> transitionFunction;
	
	@Mock
	State initialState;
	
	@Mock
	State targetState;
	
	@Mock
	State anotherState;
	
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
		
		Set<IState> expandedStates = transitionFunction.getExpandedStates(initialState);
		
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
	public void multipleEpsilonTransitionsMayBeAddedToSingleState() {
		expectedStates.add(initialState);
		expectedStates.add(targetState);
		expectedStates.add(anotherState);
		
		transitionFunction.addEpsilonTransition(initialState, targetState);
		transitionFunction.addEpsilonTransition(initialState, anotherState);
		
		assert(transitionFunction.getExpandedStates(initialState).containsAll(expectedStates));
	}
	
	@Test
	public void epsilonTransitionWithIdenticalStartingAndTargetStateMayBeAdded() {
		expectedStates.add(initialState);
		
		transitionFunction.addEpsilonTransition(initialState, initialState);
		
		assert(transitionFunction.getExpandedStates(initialState).containsAll(expectedStates));
	}
	
	@Test
	public void epsilonTransitionLoopMayBeAdded() {
		expectedStates.add(initialState);
		expectedStates.add(targetState);
		
		transitionFunction.addEpsilonTransition(initialState, targetState);
		transitionFunction.addEpsilonTransition(targetState, initialState);
		
		assert(transitionFunction.getExpandedStates(initialState).containsAll(expectedStates));
	}
	
	@Test
	public void epsilonTransitionChainMayBeAdded() {
		expectedStates.add(initialState);
		expectedStates.add(targetState);
		expectedStates.add(anotherState);
		
		transitionFunction.addEpsilonTransition(initialState, targetState);
		transitionFunction.addEpsilonTransition(targetState, anotherState);
		
		assert(transitionFunction.getExpandedStates(initialState).containsAll(expectedStates));
	}
}
