package Search;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import Model.Board;
import Model.Word;
import Persistence.Dictionary;
import Utility.Constant;
import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;
import aima.core.util.datastructure.Pair;

public class CrossFunctionFactory
{

	private static ActionsFunction	_actionsFunction	= null;
	private static ResultFunction	_resultFunction		= null;

	public static ActionsFunction getActionsFunction()
	{
		if (_actionsFunction == null)
			_actionsFunction = new CrossActionsFunction();
		return _actionsFunction;
	}

	public static ResultFunction getResultFunction()
	{
		if (_resultFunction == null)
			_resultFunction = new CrossResultFunction();
		return _resultFunction;
	}

	private static class CrossActionsFunction implements ActionsFunction
	{

		@Override
		public Set<Action> actions(Object state)
		{
			CrossWordState crossState = (CrossWordState) state;
			Set<Action> actions = new LinkedHashSet<Action>();
			// ottengo tutte le parole candidate ad essere inserite nel
			// cruciverba
			List<Pair<Word, Set<String>>> words = getWords(crossState);
			if (words != null)
				for (Pair<Word, Set<String>> w : words)
					if (w.getFirst() != null && !w.getSecond().isEmpty())
						for (String s : w.getSecond())
							actions.add(new CrossAction(
									CrossAction.ACTION_INSERT_WORD, w
											.getFirst(), s));

			return actions;
		}

		private List<Pair<Word, Set<String>>> getWords(CrossWordState crossState)
		{
			Board board = crossState.getBoard();
			List<Word> words = board.getAllWords(); // tutte le parole che devo
													// ancora inserire
			List<Pair<Word, Set<String>>> res = new ArrayList<Pair<Word, Set<String>>>();
			/*
			 * dallo stato attuale vedo quante parole mancano. dal dizionario
			 * prelevo tutte le parole "candidate" una parola e' candidata se ha
			 * una dimensione uguale a quella richiesta se vi e' una parola
			 * incompleta le parole candidate devono avere le lettere gia'
			 * presenti nelle rispettive posizioni
			 */
			for (Word w : words)
			{
				if ( numberOfEmptySpace(w.getValue()) == w.getSize())
					res.add(new Pair<Word, Set<String>>(w, new TreeSet<String>(
							Dictionary.getWords(w.getSize()))));
				else if (w.getValue().contains(Constant.empty + ""))
				{
					int k = 0;
					List<Pair<Integer, String>> charPosition = new ArrayList<Pair<Integer, String>>();
					while (k < w.getSize())
					{
						if (w.getValue().charAt(k) != Constant.empty)
							charPosition.add(new Pair<Integer, String>(k, w
									.getValue().charAt(k) + ""));
						k++;
					}

					res.add(new Pair<Word, Set<String>>(w, new TreeSet<String>(
							Dictionary.getWords(w.getSize(), charPosition))));
				}

			}
			return res;
		}

		private int numberOfEmptySpace(String value)
		{
			int counter = 0;
			for (int i = 0; i < value.length(); i++)
				if (value.charAt(i) == Constant.empty)
					counter++;
			return counter;
		}

	}

	private static class CrossResultFunction implements ResultFunction
	{
		@Override
		public Object result(Object s, Action a)
		{
			if (a instanceof CrossAction)
			{
				CrossAction action = (CrossAction) a;
				CrossWordState state = (CrossWordState) s;
				CrossWordState newState = new CrossWordState();
				newState.setBoard(state.getBoard());
				if (action.getName().equalsIgnoreCase(
						CrossAction.ACTION_INSERT_WORD))
				{
					String word = action.getWord();
					Word wordEntry = action.getWordEntry();
					return !word.isEmpty()
							&& newState.getBoard().modifyCell(wordEntry, word) ? newState
							: s;

				}
			}
			return s;
		}

	}
}
