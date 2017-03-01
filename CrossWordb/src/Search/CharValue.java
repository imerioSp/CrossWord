package Search;

public enum CharValue
{
	/*
	A('A',0.001),
	I('I',0.002),
	E('E',0.003),
	O('O',0.004),
	R('R',0.005),
	T('T',0.006),
	S('S',0.007),
	N('N',0.008),
	C('C',0.009),
	L('L',0.010),
	M('M',0.011),
	V('V',0.012),
	P('P',0.013),
	D('D',0.014),
	U('U',0.015),
	G('G',0.016),
	B('B',0.017),
	F('F',0.018),
	Z('Z',0.019),
	H('H',0.020),
	Q('Q',0.021),
	X('X',0.022),
	W('W',0.023),
	Y('Y',0.024),
	K('K',0.025),
	J('J',0.026);
	*/
	A('A',0.026),
	I('I',0.025),
	E('E',0.024),
	O('O',0.023),
	R('R',0.022),
	T('T',0.021),
	S('S',0.020),
	N('N',0.019),
	C('C',0.018),
	L('L',0.017),
	M('M',0.016),
	V('V',0.015),
	P('P',0.014),
	D('D',0.013),
	U('U',0.012),
	G('G',0.011),
	B('B',0.010),
	F('F',0.009),
	Z('Z',0.008),
	H('H',0.007),
	Q('Q',0.006),
	X('X',0.005),
	W('W',0.004),
	Y('Y',0.003),
	K('K',0.002),
	J('J',0.001);
	
	private char character;
	private double value;
	
	public char getCharacter()
	{
		return character;
	}

	public void setCharacter(char character)
	{
		this.character = character;
	}

	public double getValue()
	{
		return value;
	}

	public void setValue(double value)
	{
		this.value = value;
	}

	private CharValue(char character,double value)
	{
		this.character=character;
		this.value=value;
	}
	
}
