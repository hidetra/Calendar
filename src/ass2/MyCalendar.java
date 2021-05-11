package ass2;

/**
 * @author Hieu Hoang
 * CS151
 * ID: 013618020
 * Assignment 2
 * 09/30/2020
 */



import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
/*
 * MyCalendar class use ArrayList to hold all events
 */
public class MyCalendar {

	ArrayList <Event> eventList = new ArrayList<Event>();

	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("[M/d/uuuu");
	DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");

	/*
	 * create one time event
	 * 
	 * @param name name of the event
	 * @param date date of the event
	 * @param start starting time of the event
	 * @param end ending time of the event
	 */
	public void CreateOneTimeEvent(String name, String date, String start, String end) {
		Event event = new Event(name, new TimeInterval(LocalTime.parse(start, timeFormatter), LocalTime.parse(end, timeFormatter)),
				LocalDate.parse(date, dateFormatter), LocalDate.parse(date, dateFormatter));
		ArrayList <Event> events = new ArrayList<Event>();
		events = getEvent(LocalDate.parse(date, dateFormatter));
		boolean overlap = false;
		if(events != null)
			for (Event eve : events) 
				overlap = eve.timeinterval.CheckOverlap(event.getTimeinterval());
		if (!overlap) {
			eventList.add(event);
			System.out.println("Create event successful");
		} else {
			System.out.println("Failto create. Time overlap");
		}
	}


	/*
	 * create recurring event
	 * 
	 * @param name name of the event
	 * @data[] time of the event including dayofweek, staring day, ending day, starting time and ending time
	 */
	public void CreateRecurringEvent(String name, String[] data) {
		String [] arr = data[0].split("(?!^)");
		DayOfWeek [] days = null;
		for (int i = 0; i < arr.length; i++) {
			try
	        {  
				days[i].valueOf(arr[i]);
	        } 
	        catch(NullPointerException e) 
	        { 
	            System.out.print("NullPointerException Caught"); 
	        }
				
		}
		Event event = new Event(name, new TimeInterval(LocalTime.parse(data[1], timeFormatter), LocalTime.parse(data[2], timeFormatter)),
				LocalDate.parse(data[3], dateFormatter), LocalDate.parse(data[4], dateFormatter),days);
		eventList.add(event);
		
	}


	/*
	 * use to display a month
	 * 
	 * @param date month we need to display
	 */
	public void PrinttheMonth(LocalDate date) {
		
		LocalDate now = LocalDate.now();
		System.out.println("\t" + date.format(DateTimeFormatter.ofPattern("MMM yyyy")));
		int firstDay = date.withDayOfMonth(1).getDayOfWeek().getValue();
		int today = date.getDayOfMonth();
		LocalDate dateCount = date.withDayOfMonth(1);
		String []arr = {"Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"};
		
		for (int i = 1; i <= 7; i++ ) {
			System.out.print(arr[i-1]+ " ");
		}
		System.out.println();
		int dayofMonth = date.lengthOfMonth();
		int day = 1;
		for (int i=1 ; i < firstDay; i++) 
			System.out.print("   ");
		for (int i=1; i <= dayofMonth; i++) {
			ArrayList <Event> events = new ArrayList<Event>();
			events = getEvent(dateCount); 
			if (i<10)
				System.out.print(" ");
			
			if (i == today && date.equals(now))
				System.out.print("[" + i +"]" +" ");
			else if(events.size() != 0) {
				System.out.print("{" + i +"}" +" ");
			}
			else 
				System.out.print(i +" ");
			dateCount = dateCount.plusDays(1);
			if ((i+firstDay-1)%7 == 0 )
				System.out.println();
		}
		System.out.println();
	}


	/*
	 * use to display a day
	 * 
	 * @param date date need to display
	 */
	public void DayView(LocalDate date) {
		System.out.println(date.format(DateTimeFormatter.ofPattern("E, MMM d yyyy")));
		ArrayList <Event> events = new ArrayList<Event>();
		events = getEvent(date);
		for (Event event : events) {
			System.out.println(event);
		}
		
	}
	
	/*
	 * use to get all event in a day
	 * 
	 * @param date the date need to get all events from
	 * @return arraylist of all event
	 */
	public ArrayList<Event> getEvent (LocalDate date){
		ArrayList <Event> events = new ArrayList<Event>();
		for (Event event : eventList) {
			DayOfWeek [] days;
			if (event.getStartDay().isEqual(event.getEndDate())) {
				if (event.getStartDay().isEqual(date)) {
					events.add(event);
				}
			}else {
			for(int i = 0; i < event.getDays().length; i++) {
				if (date.isAfter(event.getStartDay()) &&
					date.isBefore(event.getEndDate()) && 
					(date.getDayOfWeek().getValue() == event.getDays()[i].getValue())){
				events.add(event);
			}}}
		}
		Collections.sort(events);
		return events;
		
	}


	/*
	 * use to display all event we have
	 */
	public void PrintEventList() {
		Collections.sort(eventList);
		System.out.println("ONE TIME EVENTS");
		for (Event event : eventList) {
			if (event.getDays() == null) {
					System.out.println(event);
			}
		}		
		System.out.println("RECURRING EVENTS");
		for (Event event : eventList) {
			if (event.getDays() != null) {
				System.out.print(event);
				System.out.println(event.getEndDate() + "\n");	
			}
		}
	}


	/*
	 * use to create file  and save all events into that file
	 * 
	 * @param filename name of the file need to write
	 */
	public void WriteinFile(String filename) {
		try {
		      FileWriter myWriter = new FileWriter(filename);
		      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM d yyyy");
		      for (Event event : eventList) {
					if (event.getDays() == null) {
						myWriter.write(event.getEventName() + "\n");
						myWriter.write(formatter.format(event.getStartDay()) + " ");
						myWriter.write(timeFormatter.format(event.timeinterval.getStarttime()) + " ");
						myWriter.write(timeFormatter.format(event.timeinterval.getEndtime()) + "\n");
					} else if (event.getDays() != null) {
						myWriter.write(event.getEventName() + "\n");
						DayOfWeek[] days = event.getDays();
						for (int i = 0; i < days.length; i++) {
							DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("E");
							myWriter.write(formatter1.format(days[i]));
						}
						myWriter.write(formatter.format(event.getStartDay()) + " ");
						myWriter.write(formatter.format(event.getEndDate()) + " ");
						myWriter.write(timeFormatter.format(event.timeinterval.getStarttime()) + " ");
						myWriter.write(timeFormatter.format(event.timeinterval.getEndtime()) + "\n");
					}	
				}
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}


	/*
	 * use to delete a selected event
	 * 
	 * @param date date of the event need to be deleted
	 * @param name name of the event need to be deleted
	 */
	public void DeleteSelected(LocalDate date, String name) {
		ArrayList <Event> events = new ArrayList<Event>();
		events = getEvent(date);
		for (Event event : events) {
			if (event.getDays() == null && event.getEventName().toUpperCase().equals(name.toUpperCase())) {
				eventList.remove(event);
				System.out.println("Delete successful");
			} else
				System.out.println("Delete error");
		}
		
	}


	/*
	 * use to delete all events in a day
	 * 
	 * @param date date need to delete all events
	 */
	public void DeleteAll(LocalDate date) {
		ArrayList <Event> events = new ArrayList<Event>();
		events = getEvent(date);
		for (Event event : events) {
			if (event.getDays() == null) {
				eventList.remove(event);
				System.out.println("Delete successful");
			} 
		}
		if (events == null)
			System.out.println("Delete error");
	}


	/*
	 * use to delete a recurring event
	 * 
	 *@param nameDr name of the recurring event need to be deleted
	 */
	public void DeleteDR(String nameDR) {
		for (Event event : eventList) {
			if (event.getDays() != null && event.getEventName().toUpperCase().equals(nameDR.toUpperCase())) {
				eventList.remove(event);
			}
		}
		
	}
}
