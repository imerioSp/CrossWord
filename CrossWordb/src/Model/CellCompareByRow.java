package Model;

import java.util.Comparator;

public class CellCompareByRow implements Comparator<Cell>
{

	@Override
	public int compare(Cell c1, Cell c2)
	{
		if (c1.getX() < c2.getX())
			return -1;
		else if (c1.getX() == c2.getX())
		{
			if (c1.getY() < c2.getY())
				return -1;
			else if (c1.getY() == c2.getY())
				return 0;
		}
		return 1;
	}

}
