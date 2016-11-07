import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

import jmetal.core.Algorithm;
import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.Variable;
import jmetal.encodings.solutionType.BinaryRealSolutionType;
import jmetal.encodings.solutionType.RealSolutionType;
import jmetal.util.JMException;

@SuppressWarnings("serial")
public class ProblemMLP extends Problem {

	public ProblemMLP(String solutionType, Integer numberOfObjectives) {
		// numberOfVariables_ = numberOfVariables;
		numberOfObjectives_ = numberOfObjectives;
		numberOfConstraints_ = 0;
		problemName_ = "ProblemMLP";

		lowerLimit_ = new double[numberOfVariables_];
		upperLimit_ = new double[numberOfVariables_];
		// for (int var = 0; var < numberOfVariables; var++){
		// lowerLimit_[var] = 0.0;
		// upperLimit_[var] = 1.0;
		// }
		if (solutionType.compareTo("BinaryReal") == 0)
			solutionType_ = new BinaryRealSolutionType(this);
		else if (solutionType.compareTo("Real") == 0)
			solutionType_ = new RealSolutionType(this);
		else {
			System.out.println("Error: solution type " + solutionType + " invalid");
			System.exit(-1);
		}
	}

	
	public void evaluate1(Solution solution) throws JMException {
		Variable[] decisionVariables = solution.getDecisionVariables();//Tá 0
		//System.out.println("Variáveis: " + decisionVariables.length);
		
		// entrar com pesos, bias e numeros de neuronio e de camadas escondidas

		// treinar mlp

		// exportar os erros de classificacao

		// COMPARAR COM O QUE FOI FEITO PARA VER SE FOI A MELHOR CONFIGURACAO

		numberOfObjectives_ = numberOfVariables_ = 2;// O mesmo que está sendo
														// passado no ProblemMLP
		Variable[] gen = solution.getDecisionVariables();

		int[] x = new int[numberOfVariables_];
		double[] f = new double[numberOfObjectives_];
		int k = numberOfVariables_ - numberOfObjectives_ + 1;

		// for (int i = 0; i < numberOfVariables_; i++) x[i] = (int)
		// gen[i].getValue();

		MLP mlp;
		try {
			// mlp = new MLP(x);
			mlp = new MLP(solution);
			f = mlp.evaluate(); // erros
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// varios objetivos??
		for (int i = 0; i < numberOfObjectives_; i++)
			solution.setObjective(i, f[i]);

	}

	@Override
	public void evaluate(Solution solution) throws JMException {
		Variable[] decisionVariables = solution.getDecisionVariables();
		
		solution.getNumberOfObjectives();
		
		//População
		int qtdPopulacao = 2;
		
		//Variáveis
		int variaveis = 6;
		
		//Valor do somatório de xmn e ρm
		double vlXmnPm = 0.0;
		
		//Cálculo do Z		
		double pm = 0.3;
		double[][] objetivos = {{0.386724, 0.613276, 0.871085, 0.128915, 0.511723, 0.488277}, 
								{0.878893, 0.121107, 0.441413, 0.558587, 0.277349, 0.722651}}; 
		
		
		Random rIndividuo = new Random();
		// Caracteristicas do individuo
		DecimalFormat format = new DecimalFormat("#.######");

		
		// Individuos com as variáveis
		double[][] x = new double[qtdPopulacao][variaveis];
		for (int linhas = 0; linhas < qtdPopulacao; linhas++)
			for (int colunas = 0; colunas < variaveis; colunas++) {
				//x[linhas][colunas] = decisionVariables[colunas].getValue();
				
				x[linhas][colunas] = Double.valueOf(format.format(rIndividuo.nextDouble()));
				vlXmnPm += Math.pow((x[linhas][colunas] - pm), 2);
			}
		
		
		/*
		 * Equacao 3
		 */
		//Denominador do Zmn		
		vlXmnPm /= qtdPopulacao;
		vlXmnPm = Math.sqrt(vlXmnPm);
		
		//Zmn
		double[][] zmn = new double[qtdPopulacao][variaveis];
				
		//Somatório de Zmn
		double sumZmn = 0.0;
		
		for (int linhas = 0; linhas < qtdPopulacao; linhas++)
			for (int colunas = 0; colunas < variaveis; colunas++) {
				zmn[linhas][colunas] = (x[linhas][colunas] - pm) / vlXmnPm;
				sumZmn += zmn[linhas][colunas];
		}
		
		
		
		
		/*
		 * Vn = Mm=1 zmn/M
		 * Equacao 4
		 */
		sumZmn /= variaveis;
		double[][] vn = new double[qtdPopulacao][variaveis];
		//Calcula os objetivos
		for (int linhas = 0; linhas < qtdPopulacao; linhas++)
			for (int colunas = 0; colunas < variaveis; colunas++) {
				
			}
		
		/*	

		int count1, count2;
		double prod1, prod2;
		double sum1, sum2, yj, hj, pj;
		sum1 = sum2 = 0.0;
		count1 = count2 = 0;
		prod1 = prod2 = 1.0;

		for (int j = 2; j <= numberOfVariables_; j++) {
			yj = x[j - 1] - Math.sin(6.0 * Math.PI * x[0] + j * Math.PI / numberOfVariables_);
			pj = Math.cos(20.0 * yj * Math.PI / Math.sqrt(j));
			if (j % 2 == 0) {
				sum2 += yj * yj;
				prod2 *= pj;
				count2++;
			} else {
				sum1 += yj * yj;
				prod1 *= pj;
				count1++;
			}
		}
		hj = 2.0 * (0.5 / N_ + epsilon_) * Math.sin(2.0 * N_ * Math.PI * x[0]);
		if (hj < 0.0)
			hj = 0.0;

		solution.setObjective(0, x[0] + hj + 2.0 * (4.0 * sum1 - 2.0 * prod1 + 2.0) / (double) count1);
		solution.setObjective(1, 1.0 - x[0] + hj + 2.0 * (4.0 * sum2 - 2.0 * prod2 + 2.0) / (double) count2);
		*/

	}

}
