package Search;

import Model.Word;
import aima.core.agent.impl.DynamicAction;

public class CrossAction extends DynamicAction
{
	public static final String	ACTION_INSERT_WORD		= "word";
	public static final String	ATTRIBUTE_WORD_ENTRY	= "wordEntry";

	public static final String	ATTRIBUTE_INS_WORD		= "wordToInsert";

	public CrossAction(String name, Word wordEntry, String word)
	{
		super(name);
		setAttribute(ATTRIBUTE_WORD_ENTRY, wordEntry);
		setAttribute(ATTRIBUTE_INS_WORD, word);
	}

	public String getWord()
	{
		return (String) getAttribute(ATTRIBUTE_INS_WORD);
	}

	public Word getWordEntry()
	{
		return (Word) getAttribute(ATTRIBUTE_WORD_ENTRY);
	}

	@Override
	public boolean isNoOp()
	{
		return getWordEntry() == null || getWord() == null
				|| getWord().isEmpty() ? true : false;
	}

}
