package Search;

import Model.Board;
import aima.core.search.framework.GoalTest;

public class CrossGoalTest implements GoalTest
{

	@Override
	public boolean isGoalState(Object state)
	{
		if (state instanceof CrossWordState)
		{
			Board board = ((CrossWordState) state).getBoard();

			return board.IsComplete() && board.wordAllDifferent()
					&& board.checkWordsInDictionary();

		}
		return false;
	}

}
