package Persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import aima.core.util.datastructure.Pair;

public final class Dictionary
{

	private static TreeMap<Integer, List<String>>	dic	= new TreeMap<Integer, List<String>>();

	public static void popola(InputStream in)
	{
		try
		{

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String word;
			List<String> words = new ArrayList<String>();
			int x = 1;
			while ((word = reader.readLine()) != null)
			{
				if (word.trim().length() == x)
					words.add(word);
				else
				{
					dic.put(x, words);
					x++;
					words = new ArrayList<String>();
				}
			}

			reader.close();

		} catch (IOException e)
		{
			e.printStackTrace();

		}

	}

	public static boolean isInDictionary(String word)
	{
		List<String> result = dic.get(word.length());
		boolean res = result.contains(word);
		return res;
	}

	public static List<String> getWords(int size,
			List<Pair<Integer, String>> charPosition)
	{
		List<String> result = new ArrayList<String>();

		for (String s : dic.get(size))
		{
			if (!result.contains(s))
			{
				boolean ok = true;
				for (Pair<Integer, String> cP : charPosition)
					if (s.charAt(cP.getFirst()) != cP.getSecond().charAt(0))
						ok = false;
				if (ok)
					result.add(s);
			}
		}
		return result;
	}

	public static List<String> getWords(int size)
	{

		return dic.get(size);
	}

	public static boolean existWords(int size,
			List<Pair<Integer, String>> charPosition)
	{
		for (String s : dic.get(size))
		{
			boolean ok = true;
			for (Pair<Integer, String> cP : charPosition)
				if (s.charAt(cP.getFirst()) != cP.getSecond().charAt(0))
					ok = false;
			if (ok)
				return true;
		}
		return false;
	}

}
