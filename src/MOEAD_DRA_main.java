

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import jmetal.core.Algorithm;
import jmetal.core.Operator;
import jmetal.core.Problem;
import jmetal.core.SolutionSet;
import jmetal.operators.crossover.CrossoverFactory;
import jmetal.operators.mutation.MutationFactory;
import jmetal.problems.Kursawe;
import jmetal.problems.ProblemFactory;
import jmetal.problems.DTLZ.DTLZ1;
import jmetal.qualityIndicator.QualityIndicator;
import jmetal.util.Configuration;
import jmetal.util.JMException;

public class MOEAD_DRA_main {
	public static Logger      logger_ ;      // Logger object
	public static FileHandler fileHandler_ ; // FileHandler object
	public static void main(String[] args) throws SecurityException, IOException, JMException, ClassNotFoundException {
		Problem   problem   ;         // The problem to solve
	    Algorithm algorithm ;         // The algorithm to use
	    Operator  crossover ;         // Crossover operator
	    Operator  mutation  ;         // Mutation operator
	     
	    QualityIndicator indicators ; // Object to get quality indicators

	    HashMap  parameters ; // Operator parameters

	    // Logger object and file to store log messages
	    logger_      = Configuration.logger_ ;
	    fileHandler_ = new FileHandler("MOEAD_DRA.log"); 
	    logger_.addHandler(fileHandler_) ;
	    
	    indicators = null ;
	    if (args.length == 1) {
	      Object [] params = {"Real"};
	      problem = (new ProblemFactory()).getProblem(args[0],params);
	    } // if
	    else if (args.length == 2) {
	      Object [] params = {"Real"};
	      problem = (new ProblemFactory()).getProblem(args[0],params);
	      indicators = new QualityIndicator(problem, args[1]) ;
	    } // if
	    else { // Default problem
	    	problem = new ProblemMLP("Real",2);
//	      problem = new Kursawe("Real", 3); 
	      //problem = new Kursawe("BinaryReal", 3);
	      //problem = new Water("Real");
	      //problem = new ZDT1("ArrayReal", 100);
	      //problem = new ConstrEx("Real");
//	      problem = new DTLZ1("Real");
	      //problem = new OKA2("Real") ;
	    } // else

	    
	    algorithm = new MOEAD_DRA(problem);
	    
	    // Algorithm parameters
	    algorithm.setInputParameter("populationSize",800);
	    algorithm.setInputParameter("maxEvaluations",150000);
	    
	    // Directory with the files containing the weight vectors used in 
	    // Q. Zhang,  W. Liu,  and H Li, The Performance of a New Version of MOEA/D 
	    // on CEC09 Unconstrained MOP Test Instances Working Report CES-491, School 
	    // of CS & EE, University of Essex, 02/2009.
	    // http://dces.essex.ac.uk/staff/qzhang/MOEAcompetition/CEC09final/code/ZhangMOEADcode/moead0305.rar
	    algorithm.setInputParameter("dataDirectory","/xampp/htdocs/java/RecPadCoronary/weight");

	    algorithm.setInputParameter("finalSize", 800) ; // used by MOEAD_DRA

	    algorithm.setInputParameter("T", 20) ;
	    algorithm.setInputParameter("delta", 0.9) ;
	    algorithm.setInputParameter("nr", 2) ;

	    // Crossover operator 
	    parameters = new HashMap() ;
	    parameters.put("CR", 1.0) ;
	    parameters.put("F", 0.5) ;
	    crossover = CrossoverFactory.getCrossoverOperator("DifferentialEvolutionCrossover", parameters);                   
	    
	    // Mutation operator
	    parameters = new HashMap() ;
	    parameters.put("probability", 1.0/problem.getNumberOfVariables()) ;
	    parameters.put("distributionIndex", 20.0) ;
	    mutation = MutationFactory.getMutationOperator("PolynomialMutation", parameters);                    
	    
	    algorithm.addOperator("crossover",crossover);
	    algorithm.addOperator("mutation",mutation);
	    
	    // Execute the Algorithm
	    long initTime = System.currentTimeMillis();
	    SolutionSet population = algorithm.execute();
	    long estimatedTime = System.currentTimeMillis() - initTime;
	    
	    // Result messages 
	    logger_.info("Total execution time: "+estimatedTime + "ms");
	    logger_.info("Objectives values have been writen to file FUN");
	    population.printObjectivesToFile("FUN");
	    logger_.info("Variables values have been writen to file VAR");
	    population.printVariablesToFile("VAR");      
	    
	    if (indicators != null) {
	      logger_.info("Quality indicators") ;
	      logger_.info("Hypervolume: " + indicators.getHypervolume(population)) ;
	      logger_.info("EPSILON    : " + indicators.getEpsilon(population)) ;
	      logger_.info("GD         : " + indicators.getGD(population)) ;
	      logger_.info("IGD        : " + indicators.getIGD(population)) ;
	      logger_.info("Spread     : " + indicators.getSpread(population)) ;
	    } // if          

	}

}
