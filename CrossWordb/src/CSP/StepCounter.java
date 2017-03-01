package CSP;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.CSPStateListener;

public class StepCounter implements CSPStateListener
{

	private int	assignmentCount	= 0;
	private int	domainCount		= 0;

	@Override
	public void stateChanged(CSP csp)
	{
		++domainCount;
	}

	@Override
	public void stateChanged(Assignment assignment, CSP csp)
	{
		++assignmentCount;
	}

	public void reset()
	{
		assignmentCount = 0;
		domainCount = 0;
	}

	public String getResults()
	{
		StringBuffer result = new StringBuffer();
		result.append("assignment changes: " + assignmentCount);
		if (domainCount != 0)
			result.append(", domain changes: " + domainCount);
		return result.toString();
	}
}
