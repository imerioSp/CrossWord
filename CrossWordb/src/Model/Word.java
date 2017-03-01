package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Word
{
	private int			size;
	private String		value;
	private List<Cell>	cells;
	private boolean		horizontal;

	public Word(String value)
	{
		this.value = value;
		this.size = value.length();
		cells = new ArrayList<Cell>();
	}

	public Word()
	{
		this.value = "";
		cells = new ArrayList<Cell>();
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}

	public String getValue()
	{
		return value != null ? value : "";
	}

	public void setValue(String value)
	{
		this.value = value;
		this.size = value.length();
	}

	public boolean isHorizontal()
	{
		return horizontal;
	}

	public void setHorizontal(boolean horizontal)
	{
		this.horizontal = horizontal;
	}

	public List<Cell> getCells()
	{
		return cells;
	}

	public void setCells(List<Cell> cells)
	{
		this.cells = cells;
	}

	@Override
	public String toString()
	{
		return "size=" + size + ", occupy cells =" + printcells()
				+ ", horizontal=" + horizontal;
	}

	private String printcells()
	{
		String result = "";
		for (Cell c : cells)
		{
			result += " [" + c.getX() + "," + c.getY() + " ]";
		}
		return result;
	}

	public Cell getStartCell()
	{
		if (this.isHorizontal())
		{
			Collections.sort(cells, new CellCompareByRow());
			return cells.get(0);
		} else
		{
			Collections.sort(cells, new CellCompareByCols());
			return cells.get(0);
		}
	}

	public Cell getEndCell()
	{
		if (this.isHorizontal())
		{
			Collections.sort(cells, new CellCompareByRow());
			return cells.get(cells.size());
		} else
		{
			Collections.sort(cells, new CellCompareByCols());
			return cells.get(cells.size());
		}
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (horizontal ? 1231 : 1237);
		result = prime * result + ((cells == null) ? 0 : cells.hashCode());
		result = prime * result + size;
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
		Word other = (Word) obj;
		if (horizontal != other.horizontal)
			return false;
		if (cells == null)
		{
			if (other.cells != null)
				return false;
		} else if (!cells.equals(other.cells))
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	public boolean isPresentinList(List<Word> words)
	{
		for (Word w : words)
			if (w.getValue().equalsIgnoreCase(this.getValue()))
				return true;
		return false;
	}

}
