package ch.zhaw.ciel.mse.alg.tsp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author Marek Arnold (arnd@zhaw.ch)
 *
 * A Traveling Salesman Problem instance.
 */
public class Instance {
	private final static int NAME_INDEX = 0, COMMENT_INDEX = 1;

	private final String name;
	private final String comment;

	private final List<Point> points;

	/**
	 * Load a TSP instance from a given file.
	 * 
	 * @param filePath
	 *            The path to the file.
	 * @return The loaded TSP instance.
	 * @throws IOException
	 *             If an exception occurs while reading the file.
	 */
	public static Instance load(Path filePath) throws IOException {
		try (BufferedReader tspFileReader = Files.newBufferedReader(filePath, Charset.forName("UTF-8"))) {
			List<String> lines = Files.readAllLines(filePath);
			List<String> header = lines.subList(0, 2);

			List<Point> points = lines.subList(2, lines.size()).stream().map(line -> line.split("\t"))
					.map(splits -> new Point(Integer.parseInt(splits[0]), Double.parseDouble(splits[1]), Double.parseDouble(splits[2])))
					.collect(Collectors.toList());

			String name = header.get(NAME_INDEX).split("\t")[1];
			String comment = header.get(COMMENT_INDEX).split("\t")[1];

			return new Instance(name, comment, points);
		}
	}

	/**
	 * Create a new TSP instance.
	 * 
	 * @param name
	 *            The name of this instance.
	 * @param comment
	 *            A comment about this instance.
	 * @param points
	 *            The points.
	 */
	public Instance(String name, String comment, List<Point> points) {
		super();
		this.name = name;
		this.comment = comment;
		this.points = points;
	}

	public String getName() {
		return name;
	}

	public String getComment() {
		return comment;
	}

	public List<Point> getPoints() {
		return points;
	}

	public List<Point> clonePointList() {
		ArrayList<Point> clonedPoints = new ArrayList<Point>(getPoints().size());

		for (Point p : getPoints()) {
			clonedPoints.add(p);
		}
		return clonedPoints;	
	}
}