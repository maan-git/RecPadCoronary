import java.io.IOException;

import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.Variable;
import jmetal.encodings.solutionType.BinaryRealSolutionType;
import jmetal.encodings.solutionType.RealSolutionType;
import jmetal.util.JMException;

@SuppressWarnings("serial")
public class ProblemMLP extends Problem{
	
	public ProblemMLP(String solutionType, Integer numberOfObjectives) {
//	    numberOfVariables_  = numberOfVariables;
	    numberOfObjectives_ = numberOfObjectives;
	    numberOfConstraints_= 0;
	    problemName_        = "ProblemMLP";
	        
	    lowerLimit_ = new double[numberOfVariables_];
	    upperLimit_ = new double[numberOfVariables_];        
//	    for (int var = 0; var < numberOfVariables; var++){
//	      lowerLimit_[var] = 0.0;
//	      upperLimit_[var] = 1.0;
//	    }
	    if (solutionType.compareTo("BinaryReal") == 0)
	    	solutionType_ = new BinaryRealSolutionType(this) ;
	    else if (solutionType.compareTo("Real") == 0)
	    	solutionType_ = new RealSolutionType(this) ;
	    else {
	    	System.out.println("Error: solution type " + solutionType + " invalid") ;
	    	System.exit(-1) ;
	    }   
	}
	
	@Override
	public void evaluate(Solution solution) throws JMException {
		
		
		// entrar com pesos, bias e numeros de neuronio e de camadas escondidas
		
		// treinar mlp
		
		// exportar os erros de classificacao
		
		
		// COMPARAR COM O QUE FOI FEITO PARA VER SE FOI A MELHOR CONFIGURACAO
		
		numberOfObjectives_ = numberOfVariables_ = 2;//O mesmo que está sendo passado no ProblemMLP 
		Variable[] gen  = solution.getDecisionVariables();
        
	    int [] x = new int[numberOfVariables_];
	    double [] f = new double[numberOfObjectives_];
	    int k = numberOfVariables_ - numberOfObjectives_ + 1;
	        
	    //for (int i = 0; i < numberOfVariables_; i++) x[i] = (int) gen[i].getValue();
	        
	    MLP mlp;
		try {
			//mlp = new MLP(x);
			mlp = new MLP(solution);
			f = mlp.evaluate(); // erros
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    // varios objetivos??
	    for (int i = 0; i < numberOfObjectives_; i++)
	      solution.setObjective(i,f[i]);
	    
	}

}
