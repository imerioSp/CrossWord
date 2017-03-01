package CSP;

import Model.Word;
import aima.core.search.csp.Variable;

public class CrossVariable extends Variable
{
	private Word	word;

	public CrossVariable(Word word, String name)
	{
		super(name);
		this.setWord(word);
	}

	public Word getWord()
	{
		return word;
	}

	public void setWord(Word word)
	{
		this.word = word;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CrossVariable other = (CrossVariable) obj;
		if (word == null)
		{
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;

		return super.equals(obj);
	}

}
