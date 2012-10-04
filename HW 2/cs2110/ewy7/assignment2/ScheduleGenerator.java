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
	private int optimalMakespan = 9999;
	private ScheduledTask[] optimalST;
	private int count = 0;

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
		
		int[] mValues = new int[m];
		
		//tempTasks is a replica of tasks
		//used to keep track of which tasks have been exhausted
		//true = available, false = used
		boolean[] tempTasks = new boolean[tasks.length];
		for(int index = 0; index < tasks.length; index++) {
			tempTasks[index] = true;
		}
		
		//first task will always be assigned to processor 0
		ScheduledTask[] st = new ScheduledTask[tasks.length];
		st[0] = new ScheduledTask(0 ,0);
		tempTasks[0] = false;
		mValues[0] += tasks[0];
		
		Schedule best = new Schedule(tasks, m, optimize(tempTasks, mValues, st, 1));
		System.out.println("Trials: " + count);
		return best;
	}

	//recursive method parameters: remaining keeps track of which tasks have been used up
	//current keeps track of current values of each processor
	//temp keeps track of the current ScheduledTask[] array
	//NOTE: The first task is the control value. It doesn't change with recursion to reduce repetition.
	private ScheduledTask[] optimize(boolean[] taskList, int[] values, ScheduledTask[] schedtask, int index) {
		int[] current = values;
		boolean[] remaining = taskList;
		ScheduledTask[] temp = schedtask;
		
		//if all tasks are used, return the full schedule of tasks		
		if(used(remaining)) {
			return temp;
		}
		
		if(remaining[index]) {
			for(int processor = 0; processor < m; processor++) {
				current[processor] += tasks[index];

				System.out.println("temp index: " + index);
				System.out.println(temp[index]);
				
				if(temp[index] == null) {
					temp[index] = new ScheduledTask(index, processor);
				}
				else {
					temp[index].p = processor;
				}
				System.out.println("temp index: " + index);
				System.out.println(temp[index]);
				remaining[index] = false;
					
				System.out.println("JUMPING");
				temp = optimize(remaining, current, temp, index + 1);
				System.out.println("RETURNED");

				int makespan = obtainMakespan(temp);
				
				if(used(remaining) && makespan < optimalMakespan) {
					optimalMakespan = makespan;
					optimalST = temp;
					System.out.println("New Optimal: " + optimalMakespan);
				}
				else {
					current[processor] -= tasks[index];
					remaining[index] = true;
				}
			}
		}
		return optimalST;
	}

	//checks to see whether all tasks are used
	private boolean used(boolean[] checkList) {
		for(int p = 0; p < checkList.length; p++)
		{
			if(checkList[p]) {
				System.out.println("Checking task usage...false!");
				return false;
			}
		}
		System.out.println("Checking task usage...true!");
		return true;
	}
	
	//self explanatory
	private int obtainMakespan(ScheduledTask[] st) {
		for(int i = 0; i < st.length; i++) {
			System.out.println(st[i]);
		}
		System.out.println("Schedule creation");
		Schedule sched = new Schedule(tasks, m, st);
		for(int i = 0; i < st.length; i++) {
			System.out.println(st[i]);
		}
		System.out.println(sched);
		count++;
		return sched.getMakespan();
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
