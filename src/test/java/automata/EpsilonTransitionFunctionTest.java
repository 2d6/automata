package automata;

import java.util.HashSet;
import java.util.Set;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automata.interfaces.IState;
import automata.states.NullState;


public class EpsilonTransitionFunctionTest {

	private EpsilonTransitionFunction<Object> transitionFunction;
	
	@Mock
	private IState initialState;
	
	@Mock
	private IState targetState;
	
	@Mock
	private IState anotherState;
	
	@Mock
	private EpsilonTransition epsTransition;
	
	private Set<IState> expectedStates;
	
	private Set<IState> initialStates;
	
	@BeforeMethod
	public void init() {
		MockitoAnnotations.initMocks(this);
		transitionFunction = new EpsilonTransitionFunction<>();
		expectedStates = new HashSet<>();
		initialStates = new HashSet<>();
	}
	
	@Test
	public void expandedStatesContainsInitialStateIfNoEpsilonTransitionsDefined() {
		expectedStates.add(initialState);
		
		Set<IState> expandedStates = transitionFunction.getExpandedStates(initialState);
		
		assert(expandedStates.containsAll(expectedStates));
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void expandedStatesThrowsIllegalArgExceptionIfInitialStateNull() {
		transitionFunction.getExpandedStates((IState) null);
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
	
	@Test
	public void expandedStatesOfSetMayBeDetermined() {
		expectedStates.add(initialState);
		expectedStates.add(targetState);
		expectedStates.add(anotherState);
		initialStates.add(initialState);
		initialStates.add(anotherState);
		
		transitionFunction.addEpsilonTransition(initialState, targetState);
		transitionFunction.addEpsilonTransition(anotherState, targetState);
		
		assert(transitionFunction.getExpandedStates(initialStates).containsAll(expectedStates));
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void expandedStatesThrowsIllegalArgExceptionIfSetNull() {
		transitionFunction.getExpandedStates((Set<IState>) null);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void expandedStatesThrowsIllegalArgExceptionIfSetEmpty() {
		transitionFunction.getExpandedStates(new HashSet<IState>());
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void transitionFunctionThrowsIllegalArgExceptionOnTransitionFromNullState() {
		transitionFunction.addEpsilonTransition(NullState.getInstance(), targetState);
	}
}
