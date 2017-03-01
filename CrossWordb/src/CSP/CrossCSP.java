package CSP;

import java.util.ArrayList;
import java.util.List;

import Model.Board;
import Model.Cell;
import aima.core.search.csp.CSP;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Domain;
import aima.core.search.csp.Variable;
import aima.core.util.datastructure.Pair;

public class CrossCSP extends CSP implements Cloneable
{
	private Board	board;

	public CrossCSP(int m, int n)
	{
		this.board = new Board(m, n);
		generateCSP();

	}

	public CrossCSP(boolean fixed)
	{
		this.board = new Board(fixed);
		generateCSP();
	}

	public CrossCSP()
	{
	}

	private void generateCSP()
	{
		List<Pair<Variable, Domain>> varDom = board.getVariable();

		for (Pair<Variable, Domain> v : varDom)
		{
			addVariable(v.getFirst());
			setDomain(v.getFirst(), v.getSecond());
		}

		List<Constraint> constraints = getAllConstraints();
		for (Constraint c : constraints)
			addConstraint(c);
	}

	private List<Constraint> getAllConstraints()
	{
		List<Constraint> result = new ArrayList<Constraint>();
		/*
		 * Per ogni variabile, imposto il vincolo di ugualianza di ogni lettera
		 * incrociata Pk[j]=Px[i]
		 */
		getEqualsConstraint(result, getVariables());

		// Tutte le variabili devono essere diverse tranne quelle che occupano 1
		// sola posizione
		getDifferentConstraint(result, getVariables());
		return result;
	}

	private void getDifferentConstraint(List<Constraint> result,
			List<Variable> variables)
	{

		// Devono essere diverse tutte le variabili di egual dimensione 
		for (int i = 0; i < variables.size() - 1; i++)
		{
			CrossVariable vari = (CrossVariable) variables.get(i);
			for (Variable varj : getVariables())
			{
				CrossVariable var2 = (CrossVariable) varj;
				if (!vari.equals(var2)
						&& vari.getWord().getSize() == var2.getWord().getSize()
						&& !result.contains(new NotEqualsConstraintV2(varj,vari)))
				{
					result.add(new NotEqualsConstraintV2(vari, varj));
				}
			}

		}
	}

	private void getEqualsConstraint(List<Constraint> result,
			List<Variable> variables)
	{
		// Per ogni variabile trovo le variabili che hanno almeno una cella in
		// comune
		// Ed aggiungo il vincolo di ugualianza di tutte le celle che han in
		// comune
		for (int i = 0; i < variables.size() - 1; i++)
		{
			CrossVariable vari = (CrossVariable) variables.get(i);
			for (Variable varj : getVariables())
			{
				CrossVariable var2 = (CrossVariable) varj;
				if (!vari.equals(var2)
						&& !vari.getWord().equals(var2.getWord()))
				{
					for (Cell c : vari.getWord().getCells())
					{
						if (var2.getWord().getCells().contains(c)
								&& !result.contains(new EqualConstraint(varj,
										vari, c)))
						{
							result.add(new EqualConstraint(vari, varj, c));
							break;//poichè al max 1 cella in comune hanno due variabili
						}
					}
				}
			}

		}

	}

	public Board getBoard()
	{
		return board;
	}

	public void setBoard(Board board)
	{
		this.board = board;
		generateCSP();// cambiano i vincoli se cambia la board

	}

	@Override
	public String toString()
	{
		String result = "CrossCSP \n";
		for (Variable v : getVariables())
		{
			result += v.getName() + " = [ " + getDomain(v).toString() + " ] \n";
		}

		result += board;
		return result;
	}

	public CrossCSP clone()
	{
		try
		{
			CrossCSP c = (CrossCSP) super.clone();
			c.board = this.board.clone();
			return c;
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			return null;

		}
	}
}
