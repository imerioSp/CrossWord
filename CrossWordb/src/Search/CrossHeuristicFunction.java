package Search;

import java.util.ArrayList;
import java.util.List;

import Model.Board;
import Model.Word;
import Persistence.Dictionary;
import Utility.Constant;
import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.Pair;

public class CrossHeuristicFunction implements HeuristicFunction
{

	@Override
	public double h(Object state)
	{
		CrossWordState crossWordState = (CrossWordState) state;
		Board board = crossWordState.getBoard();

		/*
		 * Espando prima i nodi con minor numero di parole mancanti (valore
		 * euristica = numero di parole mancanti) Se vi è più di una parola
		 * inserita effettuo un controllo preventivo se esistono parole
		 * verticali o orizzontali che si possono incastrare, se non esistono
		 * assegno una priorità elevata poichè è un nodo che porterà ad un
		 * sicuro fallimento.
		 * 
		 * Se le parole mancanti e' > 1 allora espando prima i nodi in cui vi
		 * sono parole composte con un numero maggiore di lettere piu' usate
		 * (esempio = e,0.003+ s, 0.007+ e, 0.003+ m, 0.011+ p, 0.013+ i, 0.002+
		 * o, 0.004 = 0.43)
		 */

		// double result = words.size() - wordsCompl.size();
		double result = board.getAllWords().size()
				- board.getAllCompleteWords().size();

		// Se vi è piu' di una parola mancante allora assegno una priorita'
		// anche in base alle lettere che compongono le singole parole
		if (result > 0)
			result -= getHeuristicByChar(board.getAllCompleteWords());

		// per ogni parola incompleta (non tutte le proprie celle sono state
		// riempite)
		// controllo se esiste almeno 1 parola nel dizionario che inizi con
		// quelle lettere.
		// Se non esiste assegno valore euristico molto alto poiche' e' un
		// nodo che portera' ad
		// un sicuro fallimento

		result += checkCorrectWord(board.getAllWords());

		// controllo se le parole complete inserite siano corrette
		// ovvero presenti nel dizionario e non sono ripetute.
		result += checkRepeatedWord(board);

		return result > 0 ? result : 0;

	}

	private double checkRepeatedWord(Board board)
	{
		double result = 0;
		List<Word> actualWords = new ArrayList<Word>();
		for (Word word : board.getAllCompleteWords())
		{
			if (!Dictionary.isInDictionary(word.getValue()))
				result += 100;
			if (word.isPresentinList(actualWords))
				result += 100;
			else
				actualWords.add(word);
		}
		return result;
	}

	private double checkCorrectWord(List<Word> words)
	{
		double result = 0;
		for (Word w : words)
		{

			if (w.getValue().contains(Constant.empty + ""))
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
				if (!charPosition.isEmpty())
					result = Dictionary.existWords(w.getSize(), charPosition) ? result
							: result + 100;
			}
		}
		return result;
	}

	private double getHeuristicByChar(List<Word> words)
	{
		double result = 0;
		for (Word w : words)
		{
			int k = 0;
			;
			while (k < w.getSize())
			{
				result += CharValue.valueOf("" + w.getValue().charAt(k))
						.getValue();
				k++;
			}
		}
		return result;
	}

}
