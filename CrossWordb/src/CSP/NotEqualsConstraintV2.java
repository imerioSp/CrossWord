package CSP;

import java.util.List;

import aima.core.search.csp.NotEqualConstraint;
import aima.core.search.csp.Variable;

public class NotEqualsConstraintV2 extends NotEqualConstraint
{
	private Variable		v1, v2;
	private List<Variable>	scope;

	public NotEqualsConstraintV2(Variable var1, Variable var2)
	{
		super(var1, var2);
		v1 = var1;
		v2 = var2;
		scope = super.getScope();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((scope == null) ? 0 : scope.hashCode());
		result = prime * result + ((v1 == null) ? 0 : v1.hashCode());
		result = prime * result + ((v2 == null) ? 0 : v2.hashCode());
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
		NotEqualsConstraintV2 other = (NotEqualsConstraintV2) obj;
		if (scope == null)
		{
			if (other.scope != null)
				return false;
		} else if (!scope.equals(other.scope))
			return false;
		if (v1 == null)
		{
			if (other.v1 != null)
				return false;
		} else if (!v1.equals(other.v1))
			return false;
		if (v2 == null)
		{
			if (other.v2 != null)
				return false;
		} else if (!v2.equals(other.v2))
			return false;
		return true;
	}

}
