package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.List;

import javax.swing.JRadioButton;
import javax.swing.JCheckBox;

import aima.core.agent.Action;
import aima.core.search.csp.Assignment;
import aima.core.search.csp.ImprovedBacktrackingStrategy;
import aima.core.search.csp.MinConflictsStrategy;
import aima.core.search.csp.Variable;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.TreeSearch;
import aima.core.search.informed.AStarSearch;
import aima.core.search.local.HillClimbingSearch;
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
import CSP.CrossCSP;
import CSP.CrossVariable;
import CSP.StepCounter;
import Model.Board;
import Model.Cell;
import Model.Word;
import Persistence.Dictionary;
import Search.CrossAction;
import Search.CrossFunctionFactory;
import Search.CrossGoalTest;
import Search.CrossHeuristicFunction;
import Search.CrossWordState;
import Utility.Constant;

import java.awt.Font;

public class MainForm
{
	private static JTextField	textRow;
	private static JTextField	textCol;
	private static JCheckBox	chckbxExample;
	private static JRadioButton	radioBackTracking;
	private static JRadioButton	radioForwardChecking;
	private static JRadioButton	rdbtnMinconflict;
	private static JRadioButton	rdbtnMinconflictSimulated;
	private static JRadioButton	rdbtnAStar;
	private static JRadioButton	rdbtnDepthFirst;
	private static JRadioButton	rdbtnBreadthFirst;
	private static JRadioButton	rdbtnIterativeDeepening;
	private static JRadioButton	rdbtnHillClimbing;
	private static JRadioButton	rdbtnSimulatedAnelling;
	private static JTextArea	textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					// MainForm window = new MainForm();
					Dictionary.popola(this.getClass().getResourceAsStream(
							"/words.italian.txt"));

					createAndShowGUI();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm()
	{
	}

	private static void createAndShowGUI()
	{
		JFrame f = new JFrame();
		f.setBounds(100, 100, 450, 300);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.getContentPane().setLayout(new BorderLayout());

		JPanel container = new JPanel(new BorderLayout());

		JPanel panelChoose = new JPanel(new FlowLayout());
		container.add(panelChoose, BorderLayout.LINE_START);

		chckbxExample = new JCheckBox("Example");
		chckbxExample.setActionCommand("Example");
		chckbxExample.setSelected(true);
		panelChoose.add(chckbxExample);

		textRow = new JTextField();
		textRow.setEditable(false);
		panelChoose.add(textRow);
		textRow.setColumns(1);

		textCol = new JTextField();
		textCol.setEditable(false);
		panelChoose.add(textCol);
		textCol.setColumns(1);

		chckbxExample.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent e)
			{
				if (e.getStateChange() == 1)
				{
					textCol.setEditable(false);
					textRow.setEditable(false);
				} else
				{
					textCol.setEditable(true);
					textRow.setEditable(true);
				}

			}
		});

		JPanel panelSearch = new JPanel(new GridLayout(10, 1));
		container.add(panelSearch, BorderLayout.LINE_END);

		radioBackTracking = new JRadioButton("BackTracking + AC3 + MRV + LCV");
		radioBackTracking.setActionCommand("BackTracking + AC3 + MRV + LCV");
		radioBackTracking.setSelected(true);
		panelSearch.add(radioBackTracking);

		radioForwardChecking = new JRadioButton("FC + MRV + LCV");
		radioForwardChecking.setActionCommand("FC + MRV + LCV");
		radioForwardChecking.setSelected(true);
		panelSearch.add(radioForwardChecking);

		rdbtnMinconflict = new JRadioButton("Min-Conflict");
		panelSearch.add(rdbtnMinconflict);

		rdbtnMinconflictSimulated = new JRadioButton(
				"Min-Conflict + Simulated Anelling");
		panelSearch.add(rdbtnMinconflictSimulated);

		rdbtnAStar = new JRadioButton("A Star");
		panelSearch.add(rdbtnAStar);

		rdbtnDepthFirst = new JRadioButton("Depth First");
		panelSearch.add(rdbtnDepthFirst);

		rdbtnBreadthFirst = new JRadioButton("Breadth First");
		panelSearch.add(rdbtnBreadthFirst);

		rdbtnIterativeDeepening = new JRadioButton("Iterative deepening");
		panelSearch.add(rdbtnIterativeDeepening);

		rdbtnHillClimbing = new JRadioButton("Hill Climbing");
		panelSearch.add(rdbtnHillClimbing);

		rdbtnSimulatedAnelling = new JRadioButton("Simulated Anelling");
		panelSearch.add(rdbtnSimulatedAnelling);

		ButtonGroup bg = new ButtonGroup();
		bg.add(radioBackTracking);
		bg.add(radioForwardChecking);
		bg.add(rdbtnSimulatedAnelling);
		bg.add(rdbtnAStar);
		bg.add(rdbtnBreadthFirst);
		bg.add(rdbtnDepthFirst);
		bg.add(rdbtnHillClimbing);
		bg.add(rdbtnIterativeDeepening);
		bg.add(rdbtnMinconflict);
		bg.add(rdbtnMinconflictSimulated);

		final CrosswordPanel panel = new CrosswordPanel();
		container.add(panel, BorderLayout.CENTER);
		f.getContentPane().add(container, BorderLayout.CENTER);

		JPanel panelSouth = new JPanel(new FlowLayout());
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{

				search(panel);
			}
		});
		textArea = new JTextArea();
		textArea.setDragEnabled(true);
		textArea.setFont(new Font("MS Gothic", Font.PLAIN, 18));

		panelSouth.add(textArea);
		panelSouth.add(searchButton);
		JScrollPane scroll = new JScrollPane(panelSouth,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		f.getContentPane().add(scroll, BorderLayout.SOUTH);
		f.setSize(900, 400);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.validate();

	}

	private static void search(CrosswordPanel panel)
	{
		Board result;
		if (chckbxExample.isSelected())
		{
			// Esempio 5x7
			result = resolve(5, 7, true);

		} else
		{
			if (!checkTextBox())
				return;
			int righe = Integer.parseInt(textRow.getText());
			int colonne = Integer.parseInt(textCol.getText());

			result = resolve(righe, colonne, false);

		}

		if (result != null)
			panel.setCrossword(result);
	}

	private static boolean checkTextBox()
	{
		if (textCol.getText().isEmpty() && textRow.getText().isEmpty())
		{
			JOptionPane
					.showMessageDialog(null,
							" Devi inserire un valore numero per indicare le righe e le colonne!!");
			return false;
		}
		String r = textRow.getText();
		String c = textCol.getText();
		int k = 0;
		while (k < r.length())
		{
			if (!(r.charAt(k) >= '0' && r.charAt(k) <= '9'))
			{
				JOptionPane
						.showMessageDialog(null,
								" Devi inserire un valore numero per indicare le righe e le colonne!!");
				return false;
			}
			k++;
		}
		k = 0;
		while (k < c.length())
		{
			if (!(c.charAt(k) >= '0' && c.charAt(k) <= '9'))
			{
				JOptionPane
						.showMessageDialog(null,
								" Devi inserire un valore numero per indicare le righe e le colonne!!");
				return false;
			}
			k++;
		}
		return true;
	}

	private static Board resolve(int righe, int colonne, boolean fixed)
	{
		Problem problem;
		CrossWordState state;
		if (!fixed)
			state = new CrossWordState(righe, colonne);
		else
			state = new CrossWordState(fixed);

		problem = new Problem(state, CrossFunctionFactory.getActionsFunction(),
				CrossFunctionFactory.getResultFunction(), new CrossGoalTest());

		CrossCSP crossCsp;
		if (!fixed)
			crossCsp = new CrossCSP(righe, colonne);
		else
			crossCsp = new CrossCSP(fixed);

		StepCounter stepCounter = new StepCounter();

		if (radioForwardChecking.isSelected())
		{
			long time = new Date().getTime();
			ImprovedBacktrackingStrategy s = new ImprovedBacktrackingStrategy(
					true, true, false, true);
			s.setInference(ImprovedBacktrackingStrategy.Inference.FORWARD_CHECKING);
			s.addCSPStateListener(stepCounter);

			stepCounter.reset();
			String text = "CrossCSP (FC + MRV + LCV)\n";
			Assignment ass = s.solve(crossCsp.copyDomains());

			text += "Assignment: \n" + ass + "\n";
			text += stepCounter.getResults() + "\n";
			text += ("Total time to search the final solution: "
					+ (new Date().getTime() - time) + " ms");
			textArea.setText(text);

			return getBoardFromFinalAssignment(ass, crossCsp);

		} else if (radioBackTracking.isSelected())
		{

			long time = new Date().getTime();
			ImprovedBacktrackingStrategy s = new ImprovedBacktrackingStrategy(
					true, true, true, true);
			s.setInference(ImprovedBacktrackingStrategy.Inference.AC3);
			s.addCSPStateListener(stepCounter);

			stepCounter.reset();
			String text = "CrossCSP (Backtracking + AC3 + MRV + LCV)\n";
			Assignment ass = s.solve(crossCsp.copyDomains());

			text += "Assignment: \n" + ass + "\n";
			text += stepCounter.getResults() + "\n";
			text += ("Total time to search the final solution: "
					+ (new Date().getTime() - time) + " ms");
			textArea.setText(text);

			return getBoardFromFinalAssignment(ass, crossCsp);

		} else if (rdbtnAStar.isSelected())
		{

			String text = "\nCrossWordState A STAR SEARCH \n";
			try
			{
				long time = new Date().getTime();
				Search search = new AStarSearch(new TreeSearch(),
						new CrossHeuristicFunction());
				SearchAgent agent = new SearchAgent(problem, search);
				text += agent.getActions() + "\n";
				text += agent.getInstrumentation() + "\n";
				text += "Total time to search the solution: "
						+ (new Date().getTime() - time) + " ms";
				textArea.setText(text);

				return getBoardFromActions(agent.getActions(), state.getBoard());

			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else if (rdbtnBreadthFirst.isSelected())
		{
			try
			{
				long time = new Date().getTime();

				String text = "\nCrossWordState BFS \n";

				Search search = new BreadthFirstSearch(new TreeSearch());
				SearchAgent agent = new SearchAgent(problem, search);
				text += agent.getActions() + "\n";
				text += agent.getInstrumentation() + "\n";
				text += "Total time to search the solution: "
						+ (new Date().getTime() - time) + " ms";
				textArea.setText(text);

				return getBoardFromActions(agent.getActions(), state.getBoard());

			} catch (Exception e1)
			{

				e1.printStackTrace();
			}

		} else if (rdbtnDepthFirst.isSelected())
		{
			try
			{
				String text = "\nCrossWordState DFS \n";
				long time = new Date().getTime();

				System.out.println(problem.getInitialState().toString());

				Search search = new DepthFirstSearch(new TreeSearch());
				SearchAgent agent = new SearchAgent(problem, search);
				text += agent.getActions() + "\n";
				text += agent.getInstrumentation() + "\n";
				text += "Total time to search the solution: "
						+ (new Date().getTime() - time) + " ms";
				textArea.setText(text);

				return getBoardFromActions(agent.getActions(), state.getBoard());
			} catch (Exception e1)
			{

				e1.printStackTrace();
			}

		} else if (rdbtnHillClimbing.isSelected())
		{
			try
			{
				long time = new Date().getTime();

				String text = "CrossWordState Hill Climbing \n";

				HillClimbingSearch search = new HillClimbingSearch(
						new CrossHeuristicFunction());
				SearchAgent agent = new SearchAgent(problem, search);

				text += "Search Outcome=" + search.getOutcome() + "\n";
				text += agent.getActions() + "\n";
				text += agent.getInstrumentation() + "\n";
				text += "Total time to search the solution: "
						+ (new Date().getTime() - time) + " ms";

				textArea.setText(text);

				CrossWordState finalStateSearch = (CrossWordState) search
						.getLastSearchState();

				return finalStateSearch.getBoard();
			} catch (Exception e1)
			{

				e1.printStackTrace();
			}
		} else if (rdbtnIterativeDeepening.isSelected())
		{

			String text = "CrossWordState Iteradive Deepening Search IDS \n";
			try
			{
				long time = new Date().getTime();

				Search search = new IterativeDeepeningSearch();
				SearchAgent agent = new SearchAgent(problem, search);
				text += agent.getActions() + "\n";
				text += agent.getInstrumentation() + "\n";
				text += "Total time to search the solution: "
						+ (new Date().getTime() - time) + " ms";
				textArea.setText(text);

				return getBoardFromActions(agent.getActions(), state.getBoard());

			} catch (Exception e)
			{
				e.printStackTrace();
			}

		} else if (rdbtnMinconflict.isSelected())
		{
			long time = new Date().getTime();
			int k = 1;
			String text = "CrossCSP (Minimum Conflicts)\n";
			while (k < 9)
			{
				MinConflictsStrategy solver = new MinConflictsStrategy(400);
				solver.addCSPStateListener(stepCounter);
				stepCounter.reset();
				Assignment ass = solver.solve(crossCsp.copyDomains());
				if (ass != null)
				{
					text += ass + "\n";
					text += stepCounter.getResults() + " in " + k + " times \n";
					text += "Total time to search the final solution: "
							+ (new Date().getTime() - time) + " ms";
					textArea.setText(text);

					return getBoardFromFinalAssignment(ass, crossCsp);
				} else
				{
					k++;
				}
			}
			text += "FAILURE\n No solution by the " + k + "  times\n";
			textArea.setText(text);

			return null;

		} else if (rdbtnMinconflictSimulated.isSelected())
		{
			try
			{
				long time = new Date().getTime();
				SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(
						new CrossHeuristicFunction());
				int k = 1;
				String text = "Resolve with min-conflicts strategy + Simulated Annealing for the start state\n";
				while (k < 9)
				{
					@SuppressWarnings("unused")
					SearchAgent agent2 = new SearchAgent(problem, search);

					CrossWordState crossState = (CrossWordState) search
							.getLastSearchState();
					CrossCSP crossCsp2 = new CrossCSP();

					crossCsp2.setBoard(crossState.getBoard());

					MinConflictsStrategy solver = new MinConflictsStrategy(400);
					solver.addCSPStateListener(stepCounter);
					stepCounter.reset();
					Assignment ass = solver.solve(crossCsp2.copyDomains());
					if (ass != null)
					{
						text += ass + "\n";
						text += stepCounter.getResults() + "\n";
						text += "Total time to search the final solution: "
								+ (new Date().getTime() - time) + " ms";
						textArea.setText(text);

						return getBoardFromFinalAssignment(ass, crossCsp2);
					} else
					{
						k++;

					}
				}
				text += "FAILURE\n No solution by the 8 times\n";
				textArea.setText(text);

				return null;

			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else if (rdbtnSimulatedAnelling.isSelected())
		{
			String text = "CrossWordState Simulated Annealing  \n";
			long time = new Date().getTime();

			try
			{

				SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(
						new CrossHeuristicFunction());
				SearchAgent agent = new SearchAgent(problem, search);

				text = "Search Outcome=" + search.getOutcome() + "\n";
				text += agent.getActions() + "\n";
				text += agent.getInstrumentation() + "\n";
				text += "Total time to search the solution: "
						+ (new Date().getTime() - time) + " ms";
				textArea.setText(text);
				CrossWordState finalStateSearch = (CrossWordState) search
						.getLastSearchState();
				return finalStateSearch.getBoard();

			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	private static Board getBoardFromFinalAssignment(Assignment ass,
			CrossCSP csp)
	{
		CrossCSP cspNew = new CrossCSP();
		cspNew = csp.clone();
		if (ass != null)
		{
			for (Variable v : csp.getVariables())
			{
				String value = ass.getAssignment(v).toString();
				CrossVariable cv = (CrossVariable) v;
				cspNew.getBoard().modifyCell(cv.getWord(), value);
			}
			return cspNew.getBoard();
		}
		return null;
	}

	private static Board getBoardFromActions(List<Action> actions, Board b)
	{
		Board board = b;
		for (Action a : actions)
		{
			CrossAction ca = (CrossAction) a;
			Word wordEntry = ca.getWordEntry();
			String word = ca.getWord();
			int k = 0;
			for (Cell c : wordEntry.getCells())
			{
				board.getCells()[c.getX()][c.getY()].setValue(word.charAt(k));
				k++;
			}
		}

		return board;

	}

}

class CrosswordPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private JTextField			textFields[][];

	void setCrossword(Board board)
	{
		removeAll();
		int row = board.getRows();
		int col = board.getCols();
		setLayout(new GridLayout(row, col));
		textFields = new JTextField[row][col];

		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < col; j++)
			{
				char c = board.getCells()[i][j].getValue();
				if (c != Constant.nan)
				{
					textFields[i][j] = new JTextField(String.valueOf(c));
					textFields[i][j].setFont(textFields[i][j].getFont()
							.deriveFont(20.0f));
					add(textFields[i][j]);
				} else
				{
					JLabel l = new JLabel();
					l.setBackground(Color.black);
					l.setOpaque(true);
					add(l);
				}
			}
		}
		getParent().validate();
		repaint();
	}

}
