package bearmaps.proj2c;
import java.util.LinkedList;
import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.HashMap;
import java.util.List;


public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    // Graph vals
    private DoubleMapPQ<Vertex> fringe;
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;

    //inputs
    private Vertex start;
    private Vertex goal;
    private AStarGraph<Vertex> input;
    private Stopwatch sw;
    private double timeout;

    //Shortest Path solver
    private LinkedList<Vertex> solution;
    private double getTimeSpent;
    private int exploredStates;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        //Initializing
        this.distTo = new HashMap<>();
        this.edgeTo = new HashMap<>();
        this.fringe = new DoubleMapPQ<>();
        this.input = input;
        this.start = start;
        this.goal = end;
        this.timeout = timeout;

        this.distTo.put(start, 0.0);
        this.fringe.add(start, 0);

        //runs and times A*
        this.sw = new Stopwatch();
        AstarAlg();
        this.getTimeSpent = sw.elapsedTime();
    }

    /**
     * AStar Algorithm
     *
     * @source: The algorithm is based off psuedo code in Project spec
     */

    private void AstarAlg() {
        while (fringe.size() != 0 && !fringe.getSmallest().equals(goal)
                && this.timeout > sw.elapsedTime()) {
            //Look at the next neighbors of smallest vertex in PQ
            Vertex temp = fringe.removeSmallest();
            for (WeightedEdge<Vertex> edge : input.neighbors(temp)) {
                relax(edge);
            }
            //inc explored states
            this.exploredStates += 1;
        }
    }

    /**
     * @source: The algorithm is based off the psuedo-code in Project spec
     * Relaxes the edges
     */

    private void relax(WeightedEdge<Vertex> edge) {
        Vertex to = edge.to();
        Vertex from = edge.from();
        double weight = edge.weight();

        if (!distTo.containsKey(to) || distTo.get(from) + weight < distTo.get(to)) {
            distTo.put(to, distTo.get(from) + weight);
            edgeTo.put(to, from);
            if (fringe.contains(to)) {
                fringe.changePriority(to, distTo.get(to) + input.estimatedDistanceToGoal(to, goal));
            } else {
                fringe.add(to, distTo.get(to) + input.estimatedDistanceToGoal(to, goal));
            }
        }
    }

    @Override
    public SolverOutcome outcome() {
        if (fringe.size() != 0 && fringe.getSmallest().equals(goal)) {
            return SolverOutcome.SOLVED;
        } else if (fringe.size() == 0) {
            return SolverOutcome.UNSOLVABLE;
        } else {
            return SolverOutcome.TIMEOUT;
        }
    }


    @Override
    public List<Vertex> solution() {
        this.solution = new LinkedList<>();
        if (fringe.size() != 0 && fringe.getSmallest().equals(goal)) {
            Vertex temp = goal;
            while (!temp.equals(start)) {
                solution.addFirst(temp);
                temp = edgeTo.get(temp);
            }
            solution.addFirst(start);
        }
        return solution;
    }


    @Override
    public double solutionWeight() {
        return this.distTo.get(this.goal);
    }

    @Override
    public int numStatesExplored() {
        return this.exploredStates;
    }

    @Override
    public double explorationTime() {
        return getTimeSpent;
    }


}



