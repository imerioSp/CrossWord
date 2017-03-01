package CSP;

import java.util.ArrayList;
import java.util.List;

import Model.Cell;
import Model.Word;
import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;

public class EqualConstraint implements Constraint
{

	private List<Variable>	scope;
	private Variable		var1, var2;
	private Cell			cell;

	public EqualConstraint(Variable var1, Variable var2, Cell cell)
	{
		super();
		this.var1 = var1;
		this.var2 = var2;
		this.scope = new ArrayList<Variable>(2);
		this.scope.add(var1);
		this.scope.add(var2);
		this.cell = cell;
	}

	@Override
	public List<Variable> getScope()
	{
		return this.scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment assignment)
	{
		Object value1 = assignment.getAssignment(var1);
		Object value2 = assignment.getAssignment(var2);
		CrossVariable crossV1 = (CrossVariable) var1;
		CrossVariable crossV2 = (CrossVariable) var2;
		int elementToCheckV1 = getSize(crossV1);
		int elementToCheckV2 = getSize(crossV2);
		
		if (value1 != null && value2 != null)
		{
			String v1 = value1.toString();
			String v2 = value2.toString();
			boolean res = v1.charAt(elementToCheckV1) == v2
					.charAt(elementToCheckV2);
			return res;
		}
		return true;
	}

	private int getSize(CrossVariable crossV)
	{
		Word word = crossV.getWord();
		int size = 0;
		if (word.isHorizontal())
			for (int i = word.getStartCell().getY(); i < cell.getY(); i++)
				size++;
		else
			for (int i = word.getStartCell().getX(); i < cell.getX(); i++)
				size++;

		return size;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cell == null) ? 0 : cell.hashCode());
		result = prime * result + ((scope == null) ? 0 : scope.hashCode());
		result = prime * result + ((var1 == null) ? 0 : var1.hashCode());
		result = prime * result + ((var2 == null) ? 0 : var2.hashCode());
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
		EqualConstraint other = (EqualConstraint) obj;
		if (cell == null)
		{
			if (other.cell != null)
				return false;
		} else if (!cell.equals(other.cell))
			return false;
		if (scope == null)
		{
			if (other.scope != null)
				return false;
		} else if (!scope.equals(other.scope))
			return false;
		if (var1 == null)
		{
			if (other.var1 != null)
				return false;
		} else if (!var1.equals(other.var1))
			return false;
		if (var2 == null)
		{
			if (other.var2 != null)
				return false;
		} else if (!var2.equals(other.var2))
			return false;
		return true;
	}
}
