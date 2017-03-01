package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import CSP.CrossVariable;
import Persistence.Dictionary;
import Utility.Constant;
import aima.core.search.csp.Domain;
import aima.core.search.csp.Variable;
import aima.core.util.datastructure.Pair;

public class Board implements Cloneable
{
	private Cell[][]	_cells;
	private short			cols;
	private short			rows;

	public Board(boolean fixed)
	{
		this.cols = 7;
		this.rows = 5;
		_cells = new Cell[rows][cols];
		initBoardFixedExample();
	}

	private void initBoardFixedExample()
	{
		for (short i = 0; i < rows; i++)
		{
			for (short j = 0; j < cols; j++)
			{
				_cells[i][j] = new Cell(i, j);
				char value = getFixedValue(i, j);
				_cells[i][j].setValue(value);
				_cells[i][j].setEndRow(j + 1 == cols ? true : false);
				_cells[i][j].setEndCol(i + 1 == rows ? true : false);

			}
		}
	}

	private char getFixedValue(short i, short j)
	{
		switch (i)
		{
		case 0:
			return Constant.empty;
		case 1:
			switch (j)
			{
			case 0:
				return Constant.empty;
			case 1:
				return Constant.empty;
			case 4:
				return Constant.empty;
			default:
				return Constant.nan;
			}
		case 2:
			switch (j)
			{
			case 0:
				return Constant.nan;
			case 5:
				return Constant.nan;
			default:
				return Constant.empty;
			}
		case 3:
			switch (j)
			{
			case 2:
				return Constant.empty;
			case 3:
				return Constant.empty;
			case 4:
				return Constant.empty;
			default:
				return Constant.nan;
			}
		case 4:
			switch (j)
			{
			case 0:
				return Constant.empty;
			case 3:
				return Constant.empty;
			case 5:
				return Constant.empty;
			case 6:
				return Constant.empty;
			default:
				return Constant.nan;
			}
		default:
			return Constant.empty;
		}
	}

	public Board(int rows, int cols)
	{
		this.cols = (short) cols;
		this.rows = (short) rows;
		_cells = new Cell[rows][cols];
		initBoard();
	}

	private void initBoard()
	{
		for (short i = 0; i < rows; i++)
		{
			for (short j = 0; j < cols; j++)
			{
				_cells[i][j] = new Cell(i, j);
				// 0.33 % pox che sia cella nera
				char value = new Random().nextInt(3) == 0 ? Constant.nan
						: Constant.empty;
				_cells[i][j].setValue(value);
				_cells[i][j].setEndRow(j + 1 == cols ? true : false);
				_cells[i][j].setEndCol(i + 1 == rows ? true : false);

			}
		}

	}

	public short getCols()
	{
		return this.cols;
	}

	public short getRows()
	{
		return this.rows;
	}

	public Cell[][] getCells()
	{
		return _cells;
	}

	public void setCells(Cell[][] _cells)
	{
		this._cells = _cells.clone();
	}

	public boolean IsComplete()
	{
		for (short i = 0; i < rows; i++)
			for (short j = 0; j < cols; j++)
				if (_cells[i][j].getValue() == Constant.empty)
					return false;
		return true;
	}

	public boolean checkWordsInDictionary()
	{
		// Controllo parole per riga e per colonna
		List<Word> words = getAllCompleteWords();
		for (Word w : words)
			if (!Dictionary.isInDictionary(w.getValue()))
				return false;
		return true;

	}

	@Override
	public String toString()
	{
		String result = "Board: \n";
		short k = 0;
		for (short i = 0; i < rows; i++)
		{
			for (short j = 0; j < cols; j++)
			{
				result += _cells[i][j].getValue() + "\t";
				if (k == cols - 1)
				{
					k = 0;
					result += "\n";
				} else
					k++;
			}
		}
		return result;
	}

	public Board clone()
	{
		try
		{
			Board b = (Board) super.clone();
			b._cells = new Cell[rows][cols];
			for (short i = 0; i < rows; i++)
				for (short j = 0; j < cols; j++)
					b._cells[i][j] = this._cells[i][j].clone();

			b.cols = this.cols;
			b.rows = this.rows;
			return b;
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(_cells);
		result = prime * result + cols;
		result = prime * result + rows;
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
		Board other = (Board) obj;
		if (!Arrays.deepEquals(_cells, other._cells))
			return false;
		if (cols != other.cols)
			return false;
		if (rows != other.rows)
			return false;
		return true;
	}

	public boolean wordAllDifferent()
	{
		List<Word> words = new ArrayList<Word>();
		if (!getHorizontalCompleteWords(words))
			return false;
		boolean res = getVerticalCompleteWords(words);
		return res;

	}

	public List<Pair<Variable, Domain>> getVariable()
	{
		List<Pair<Variable, Domain>> result = new ArrayList<Pair<Variable, Domain>>();
		List<Word> words = new ArrayList<Word>();
		// Ottieni tutte le parole orizzontali
		getHorizontalWords(words);

		// Ottieni tutte le parole verticali
		getVerticalWords(words);

		// Le parole composte da una sola lettera sono ammesse solo se quella
		// cella non è occupata da altre parole

		// Crea le variabili ed associa i rispettivi domini
		short k = 0;
		for (Word w : words)
		{
			if (w.getSize() > 0)
			{
				CrossVariable v = new CrossVariable(w, "P" + k);
				Domain domain = new Domain(Dictionary.getWords(w.getSize()));
				result.add(new Pair<Variable, Domain>(v, domain));
				k++;
			}
		}

		return result;
	}

	public boolean modifyCell(Word word, String value)
	{
		short k = 0;
		for (Cell c : word.getCells())
		{
			try
			{
				_cells[c.getX()][c.getY()].setValue(value.charAt(k));
				k++;
			} catch (StringIndexOutOfBoundsException s)
			{
				s.printStackTrace();
			}
		}
		return true;

	}

	public List<Word> getAllWords()
	{
		List<Word> words = new ArrayList<Word>();

		getHorizontalWords(words);

		getVerticalWords(words);

		return words;
	}

	public List<Word> getAllCompleteWords()
	{
		List<Word> words = new ArrayList<Word>();

		getHorizontalCompleteWords(words);

		getVerticalCompleteWords(words);

		return words;
	}

	private boolean getVerticalCompleteWords(List<Word> words)
	{
		boolean notDuplicate = true;
		for (short j = 0; j < cols; j++)
		{
			Word w = new Word();
			w.setHorizontal(false);
			short size = 0;
			for (short i = 0; i < rows; i++)
			{
				if (_cells[i][j].getValue() != Constant.nan)
				{
					size++;
					w.getCells().add(_cells[i][j]);
					if (_cells[i][j].getValue() != Constant.empty)
						w.setValue(w.getValue() + _cells[i][j].getValue());
				} else if (_cells[i][j].getValue() == Constant.nan)
				{
					if ((size == 1 && !cellIsInWord(_cells[i - 1][j], words))
							|| size > 1)
					{
						w.setSize(size);
						if (words.contains(w) || w.isPresentinList(words))
							notDuplicate = false;
						if (size == w.getValue().length() && !w.isHorizontal())
							words.add(w);
					}
					size = 0;

					w = new Word();
					w.setHorizontal(false);
				}
				if (_cells[i][j].isEndCol())
				{
					if ((size == 1 && !cellIsInWord(_cells[i][j], words))
							|| size > 1)
					{
						w.setSize(size);
						if (words.contains(w) || w.isPresentinList(words))
							notDuplicate = false;

						if (size == w.getValue().length() && !w.isHorizontal())
							words.add(w);
					}
					size = 0;
					w = new Word();
					w.setHorizontal(false);
				}
			}
		}
		return notDuplicate;

	}

	public boolean getHorizontalCompleteWords(List<Word> words)
	{
		boolean notDuplicate = true;
		for (short i = 0; i < rows; i++)
		{
			Word w = new Word();
			w.setHorizontal(true);
			short size = 0;
			for (short j = 0; j < cols; j++)
			{
				if (_cells[i][j].getValue() != Constant.nan)
				{
					size++;
					w.getCells().add(_cells[i][j]);
					if (_cells[i][j].getValue() != Constant.empty)
						w.setValue(w.getValue() + _cells[i][j].getValue());
				} else if (_cells[i][j].getValue() == Constant.nan)
				{
					if (size > 1)
					{
						w.setSize(size);
						if (words.contains(w) || w.isPresentinList(words))
							notDuplicate = false;
						if (size == w.getValue().length())
							words.add(w);
						size = 0;

						w = new Word();
						w.setHorizontal(true);
					} else if (size == 1)
					{
						size = 0;
						w = new Word();
						w.setHorizontal(true);
					}
				}
				if (_cells[i][j].isEndRow())
				{
					if (size > 1)
					{
						w.setSize(size);
						if (words.contains(w) || w.isPresentinList(words))
							notDuplicate = false;

						if (size == w.getValue().length())
							words.add(w);
					}
					size = 0;

					w = new Word();
					w.setHorizontal(true);
				}

			}
		}
		return notDuplicate;
	}

	public void getHorizontalWords(List<Word> words)
	{
		for (short i = 0; i < rows; i++)
		{
			Word w = new Word();
			w.setHorizontal(true);
			short size = 0;
			for (short j = 0; j < cols; j++)
			{
				if (_cells[i][j].getValue() != Constant.nan)
				{
					size++;
					w.getCells().add(_cells[i][j]);
					w.setValue(w.getValue() + _cells[i][j].getValue());
				} else if (_cells[i][j].getValue() == Constant.nan)
				{
					if (size > 1)
					{
						w.setSize(size);
						if (size == w.getValue().length())
						{
							words.add(w);
						}
					}
					size = 0;
					w = new Word();
					w.setHorizontal(true);
				}
				if (_cells[i][j].isEndRow() && size > 1)
				{
					w.setSize(size);
					if (size == w.getValue().length())
					{
						words.add(w);
					}
					size = 0;

				}

			}
		}
	}

	private void getVerticalWords(List<Word> words)
	{
		for (short j = 0; j < cols; j++)
		{
			Word w = new Word();
			w.setHorizontal(false);
			short size = 0;
			for (short i = 0; i < rows; i++)
			{
				if (_cells[i][j].getValue() != Constant.nan)
				{
					size++;
					w.getCells().add(_cells[i][j]);
					w.setValue(w.getValue() + _cells[i][j].getValue());
				} else if (_cells[i][j].getValue() == Constant.nan)
				{
					if ((size == 1 && !cellIsInWord(_cells[i - 1][j], words))
							|| size > 1)
					{
						w.setSize(size);
						if ((size == w.getValue().length()))
						{
							words.add(w);
						}
					}
					size = 0;
					w = new Word();
					w.setHorizontal(false);
				}
				if (_cells[i][j].isEndCol())
				{
					if ((size == 1 && !cellIsInWord(_cells[i][j], words))
							|| size > 1)
					{
						w.setSize(size);
						if ((size == w.getValue().length()))
						{
							words.add(w);
						}
					}
					size = 0;
					w = new Word();
					w.setHorizontal(false);
				}
			}
		}
	}

	private boolean cellIsInWord(Cell c, List<Word> words)
	{
		for (Word w : words)
		{
			if (w.getCells().contains(c))
				return true;
		}
		return false;
	}
}
