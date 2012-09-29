//Don't import anything else.

package cs2110.ewy7.assignment2;


import java.util.Arrays; // you can only use toString & sort

/**
 *
 * The purpose of this class is to compute an optimal schedule for the
 * execution of tasks on identical processors.
 *
 */
public class ScheduleGenerator {
	private int[] tasks;
	private int m;
	private int[] _mValues;

	/**
	 * If the arguments are not valid, throw an IllegalArgumentException.
	 * 
	 * @param tasks Duration of each task. 
	 * @param m Number of processors.
	 */
	public ScheduleGenerator(int[] tasks, int m) {
		this.tasks = tasks;
		this.m = m;
		_mValues = new int[m];
	}
	
	/**
	 * Since the classical scheduling problem is hard to compute
	 * (it seems to require unavoidably super-polynomial time, because it is NP-complete)
	 * we ask you to implement a suboptimal fast heuristic algorithm.
	 * 
	 * Greedy heuristic:
	 * Schedule the tasks one by one, assigning at every stage a task to a
	 * processor that has minimum load (total processing to do).
	 * 
	 * @return The schedule given by a suboptimal heuristic algorithm.
	 */
	public Schedule heuristicScheduling() {
		ScheduledTask[] st = new ScheduledTask[tasks.length];
		int current = 0;
		
		for(int i = 0; i < tasks.length; i++) {	
			
			//keep track of processor with min load
			for(int p = 0; p < m; p++) {
				if(mValues[p] < mValues[current])
					current = p;
			}
			//assign a new task to current processor
			st[i] = new ScheduledTask(i, current);
			//add duration of task onto current processor
			mValues[current] = mValues[current] + tasks[i];		
		}
		
		Schedule sch = new Schedule(tasks, m, st);
		return sch;
	}
	
	/**
	 * Find an optimal schedule, that is one with minimum makespan.
	 */
	public Schedule getOptSchedule() {
		// TODO: Implement me.
		return null;
	}
	
	/**
	 * NOTE: You do NOT have to use this class if you don't want to or
	 * you can't see why it's useful.
	 * 
	 * The purpose of this class is to provide a basic implementation of a "mutable integer". 
	 */
	class MyInt {
		public int v;
		public MyInt(int v) { this.v = v; }
		@Override
    public String toString() { return Integer.toString(v); }
	}
	
}
