package Model;

public class Cell implements Cloneable
{
	private char	value;
	private boolean	endRow;
	private boolean	endCol;
	private short	x, y;

	public Cell(short x, short y)
	{
		this.x = x;
		this.y = y;
		endRow = false;
		endCol = false;
	}

	public char getValue()
	{
		return value;
	}

	public void setValue(char value)
	{
		this.value = value;
	}

	public boolean isEndRow()
	{
		return endRow;
	}

	public void setEndRow(boolean endRow)
	{
		this.endRow = endRow;
	}

	public boolean isEndCol()
	{
		return endCol;
	}

	public void setEndCol(boolean endCol)
	{
		this.endCol = endCol;
	}

	public Cell clone()
	{
		try
		{
			Cell c = (Cell) super.clone();
			c.endCol = endCol;
			c.endRow = endRow;
			c.x = x;
			c.y = y;
			c.value = value;
			return c;
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public int getX()
	{
		return x;
	}

	public void setX(short x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(short y)
	{
		this.y = y;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (endCol ? 1231 : 1237);
		result = prime * result + (endRow ? 1231 : 1237);
		result = prime * result + value;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (endCol != other.endCol)
			return false;
		if (endRow != other.endRow)
			return false;
		if (value != other.value)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "value=" + value + ", row=" + x + ", col=" + y + "]";
	}

}
