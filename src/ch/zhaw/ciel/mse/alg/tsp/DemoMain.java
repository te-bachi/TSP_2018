package ch.zhaw.ciel.mse.alg.tsp;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import ch.zhaw.ciel.mse.alg.tsp.metaheuristics.*;
import ch.zhaw.ciel.mse.alg.tsp.utils.Point;
import ch.zhaw.ciel.mse.alg.tsp.utils.Printer;
import ch.zhaw.ciel.mse.alg.tsp.utils.Instance;
import ch.zhaw.ciel.mse.alg.tsp.utils.Utils;

public class DemoMain {

	public static void main(String[] args) throws IOException {

	    //String[] instanceNames =  {"berlin52", "bier127", "pr1002", "pr2392", "reseau_suisse", "rl5915", "sw24978", "tsp225"};
        //String[] instanceNames =  {"berlin52", "bier127", "pr1002", "pr2392", "tsp225"};

        //runSingleTSPInstance("andreas");

		runSingleTSPInstance("berlin52");
		//runSingleTSPInstance("reseau_suisse");
        //runSingleTSPInstance("rl5915");
        //for (String instanceName : instanceNames) {
        //    runSingleTSPInstance(instanceName);
        //}
	}

	private static void runSingleTSPInstance(String instanceName) throws IOException {

		String solutionName = instanceName;

		String pathToInstances = "TSP_Instances";
		String pathToSolutions = "TSP_Solutions";

		String instanceFilenameExtension = ".tsp";
		String solutionFilenameExtension = ".html";

		String pathToInstance = pathToInstances + java.io.File.separator + instanceName + instanceFilenameExtension;
		String pathToSolution = pathToSolutions + java.io.File.separator + solutionName + solutionFilenameExtension;

		//System.out.println("Loading instance " + instanceName + "...");
		Instance instance = Instance.load(Paths.get(pathToInstance));

		//System.out.println("Instance has " + instance.getPoints().size() + " points.");

		//System.out.println("Start generating a solution...");
		List<Point> solution;
		//solution = GreedyInsertion.solve(instance);
		//solution = NearestNeighbor.solve(instance);
        //solution = BestInsertion.solve(instance);
        solution = SimulatedAnnealing.solve(instance);
        System.out.println(instanceName + " " + Utils.euclideanDistance2D(solution));


		// Generate Visualization of Result, will be stored in directory pathToSolutions
		Printer.writeToSVG(instance, solution, Paths.get(pathToSolution));
	}

}
