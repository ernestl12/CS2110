// Don't import anything else.

import java.util.ArrayList; // useful data structure
import java.util.Arrays; // only use the toString method
import java.util.Collections; // only use the sort method

/**
 * This class represents a schedule of the execution of tasks on identical processors.
 * 
 */
public class Schedule {
	private int[] tasks;
	private int m;
	
	/* Definition:
	 * An invariant of a class is a property that every object of the
	 * class has to satisfy. In particular, every method of the class
	 * should preserve the invariant.
	 * 
	 */
	
	/** 
	 * Satisfies invariant: 
	 *   for any two pairs tr1, tr2 in schedule:
	 *     if [ (tr1.p == tr2.p) and (tr1.id <= tr2.id) ] then
	 *       tr1 has to appear before tr2 in the array schedule
	 * 
	 * NOTE:
	 * Do NOT make in your code any stronger assumption for this ordering property.
	 * In particular, do not assume that pairs also occur in increasing order of pair.p.
	 * 
	 */
	private ScheduledTask[] schedule;

	/**
	 * If the arguments are not valid, throw an IllegalArgumentException.
	 * 
	 * @param tasks Duration of each task.
	 * @param m Number of processors.
	 * @param schedule The schedule for each task.
	 */
	public Schedule(int[] tasks, int m, ScheduledTask[] schedule) {
		// TODO: Implement me.
	}
	
	/**
	 * @param tasks Duration of each task.
	 * @param m Number of processors.
	 * @return True iff (tasks & m are valid).
	 */
	public static boolean areValid(int[] tasks, int m) {
		// TODO: Implement me.
		return false;
	}
	
	/**
	 * Precondition: tasks and m are valid.
	 * (You can assume that the precondition holds true.)
	 * 
	 * YOU HAVE TO IMPLEMENT THIS:
	 * Rearrange the objects in the argument "ScheduledTask[] schedule"
	 * so that the following holds after the method ends:
	 *   For any two pairs tr1, tr2 in schedule:
	 *     if [ (tr1.p == tr2.p) and (tr1.id <= tr2.id) ] then
	 *       tr1 has to appear before tr2 in the array schedule
	 * This property does not have to hold when the method starts, but it
	 * is your job to make it hold when your method ends.
	 * 
	 * @param tasks Duration of each task.
	 * @param m Number of processors.
	 * @return True iff schedule is valid.
	 */
	public static boolean isConsistent(int[] tasks, int m, ScheduledTask[] schedule) {
		// TODO: Implement me.
		return false;
	}
	
	/**
	 * Definition of makespan:
	 * The total time elapsed after the last task finishes.
	 * 
	 * @return The makespan of the schedule.
	 */
	public int getMakespan() {
		// TODO: Implement me.
		return -1;
	}
	
	/**
	 * 
	 * @return The utilization of the processors w.r.t. to this schedule.
	 */
	public double getUtilization() {
		// TODO: Implement me.
		return -1;
	}
	
	/**
	 * Creates a String representation of the schedule.
	 */
	public String toString() {
		// TODO: Implement me.
		return null;
	}
	
	
}
