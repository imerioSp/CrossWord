package Search;

import Model.Board;
import aima.core.agent.Action;
import aima.core.search.framework.StepCostFunction;

public class CrossWordState implements StepCostFunction
{

	private Board	board;

	public CrossWordState(int righe, int colonne)
	{
		board = new Board(righe, colonne);
	}

	public CrossWordState(boolean fixed)
	{
		board = new Board(fixed);
	}

	public CrossWordState()
	{
	}

	public Board getBoard()
	{
		return this.board;
	}

	public void setBoard(Board b)
	{
		this.board = (Board) b.clone();
	}

	@Override
	public double c(Object state1, Action a, Object state2)
	{
		return new Double(1);
	}

	@Override
	public String toString()
	{
		return board.toString();
	}

}
