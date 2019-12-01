package ch.zhaw.ciel.mse.alg.tsp;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import ch.zhaw.ciel.mse.alg.tsp.metaheuristics.*;
import ch.zhaw.ciel.mse.alg.tsp.utils.Point;
import ch.zhaw.ciel.mse.alg.tsp.utils.Printer;
import ch.zhaw.ciel.mse.alg.tsp.utils.Instance;
import ch.zhaw.ciel.mse.alg.tsp.utils.Utils;

public class DemoMain {

	enum SolverOption {
		RANDOM_SAMPLING,
		GREEDY_INSERTION,
		NEAREST_NEIGHBOR,
        ALL_DISTANCES,
		BEST_INSERTION,
		PILOT_METHOD,
		SIMULATED_ANNEALING,
		ANT_COLONY_OPTIMIZATION
	}

	public static void main(String[] args) throws IOException {

		SolverOption option = SolverOption.SIMULATED_ANNEALING;

		InstanceName[] instanceNames = new InstanceName[] {
				new InstanceName(false, "andreas8"),
				new InstanceName(false, "andreas12"),
				new InstanceName(true, "berlin52"),
				new InstanceName(false, "bier127"),
				new InstanceName(false, "pr1002"),
				new InstanceName(false, "pr2392"),
				new InstanceName(false, "reseau_suisse"),
				new InstanceName(false, "rl5915"),
				new InstanceName(false, "sw24978"),
				new InstanceName(false, "tsp225"),
		};

		Solver solver = null;
		switch (option) {
			case RANDOM_SAMPLING:			solver = new RandomSampling();			break;
			case GREEDY_INSERTION:			solver = new GreedyInsertion();			break;
            case NEAREST_NEIGHBOR:			solver = new NearestNeighbor();			break;
            case ALL_DISTANCES:			    solver = new AllDistances();			break;
			case BEST_INSERTION:			solver = new BestInsertion();			break;
			case PILOT_METHOD:			    solver = new PilotMethod(2, new NearestNeighbor());			break;
			case SIMULATED_ANNEALING:		solver = new SimulatedAnnealing();		break;
			case ANT_COLONY_OPTIMIZATION:	solver = new AntColonyOptimization();	break;
			default:
				throw new IllegalStateException("Unexpected value: " + option);
		}

		for (InstanceName instanceName : instanceNames) {
			if (instanceName.isEnabled()) {
				runSingleTSPInstance(instanceName.getName(), solver);
			}
        }
	}

	private static void runSingleTSPInstance(String instanceName, Solver solver) throws IOException {

		String solutionName = instanceName;

		String pathToInstances = "TSP_Instances";
		String pathToSolutions = "TSP_Solutions";

		String instanceFilenameExtension = ".tsp";
		String solutionFilenameExtension = ".html";

		String pathToInstance = pathToInstances + java.io.File.separator + instanceName + instanceFilenameExtension;
		String pathToSolution = pathToSolutions + java.io.File.separator + solutionName + solutionFilenameExtension;

		System.out.println("Loading instance " + instanceName + "...");
		Instance instance = Instance.load(Paths.get(pathToInstance));

		System.out.println("Instance has " + instance.getPoints().size() + " points.");

		System.out.println("Start generating a solution...");
		List<Point> solution;
		solution = solver.solve(instance);

        System.out.println(instanceName + " " + Utils.euclideanDistance2D(solution));

		// Generate Visualization of Result, will be stored in directory pathToSolutions
		Printer.writeToSVG(instance, solution, Paths.get(pathToSolution));
	}

}
