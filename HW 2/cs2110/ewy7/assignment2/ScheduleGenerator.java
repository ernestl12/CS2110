package release;

//Don't import anything else.
//Known errors: No exceptions thrown yet

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
	private int optimalMakespan = 9999;
	private ScheduledTask[] optimalST;
	private ScheduledTask[] currentST;
	/**
	 * If the arguments are not valid, throw an IllegalArgumentException.
	 * 
	 * @param tasks Duration of each task. 
	 * @param m Number of processors.
	 */
	public ScheduleGenerator(int[] tasks, int m) {
		this.tasks = tasks;
		this.m = m;
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
		int[] mValues = new int[m];
		
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
		
		int[] mValues = new int[m];
		
		//tempTasks is a replica of tasks
		//used to keep track of which tasks have been exhausted
		//true = available, false = used
		boolean[] tempTasks = new boolean[tasks.length];
		for(int index = 0; index < tasks.length; index++) {
			tempTasks[index] = true;
		}
		currentST = new ScheduledTask[tasks.length];
		optimalST = new ScheduledTask[tasks.length];
		
		//first task will always be assigned to processor 0		
		currentST[0] = new ScheduledTask(0 ,0);
		tempTasks[0] = false;
		mValues[0] += tasks[0];
		
		for(int task = 1; task < currentST.length; task++) {
			currentST[task] = new ScheduledTask(task, 0);
		}
		
		Schedule best = new Schedule(tasks, m, optimize(tempTasks, currentST, mValues, 1));
		
		return best;
	}

	//recursive method parameters: remaining keeps track of which tasks have been used up
	//current keeps track of current values of each processor
	//temp keeps track of the current ScheduledTask[] array
	//NOTE: The first task is the control value. It doesn't change with recursion to reduce repetition.
	private ScheduledTask[] optimize(boolean[] taskList, ScheduledTask[] schedtask, int[] values, int index) {
		boolean[] remaining = taskList;
		int[] curVals = values;
		
		//if all tasks are used, return the full schedule of tasks		
		if(used(remaining)) {
			return currentST;
		}		
		for(int processor = 0; processor < m; processor++) {
				
				currentST[index].p = processor;
				curVals[processor] += tasks[index];
				remaining[index] = false;
					
				currentST = optimize(remaining, currentST, curVals, index + 1);
				
				int makespan = obtainMakespanTwo(curVals);
				
				if(used(remaining) && (makespan < optimalMakespan)) {
					optimalMakespan = makespan;
					registerOptimal(currentST);
				}
				curVals[processor] -= tasks[index];
				remaining[index] = true;
		}
		//System.out.println("This is stack # " + index);
		//printOpt();
		if(index > 1)
			return currentST;
		else
			return optimalST;
	}
	
	//checks to see whether all tasks are used
	private boolean used(boolean[] checkList) {
		for(int p = 0; p < checkList.length; p++) {
			if(checkList[p])
				return false;
		}
		return true;
	}
	
	private int obtainMakespanTwo(int[] values) {
		int max = 0;
		for(int i = 0; i < values.length; i++) {
			if(values[i] > max)
				max = values[i];
		}
		return max;
	}
	
	//registers a new optimal 
	private void registerOptimal(ScheduledTask[] st){
		for(int task = 0; task < optimalST.length; task++) {
			optimalST[task] = new ScheduledTask(st[task].id, st[task].p);
		}							
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
		public String toString() { return Integer.toString(v); }
	}
	
}