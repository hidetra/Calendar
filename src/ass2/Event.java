package ass2;

/**
 * @author Hieu Hoang
 * CS151
 * ID: 013618020
 * Assignment 2
 * 09/30/2020
 */



import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
/*
 * Event class that represent an event
 */
public class Event implements Comparable<Event> {

	String eventName;
	TimeInterval timeinterval;
	LocalDate startDay;
	LocalDate endDate;
	DayOfWeek [] days;
	


	public Event() {
		super();
		// TODO Auto-generated constructor stub
	}


	/*
	 * constructor 
	 */
	public Event(String eventName, TimeInterval timeinterval, LocalDate startDay, LocalDate endDate) {
		super();
		this.eventName = eventName;
		this.timeinterval = timeinterval;
		this.startDay = startDay;
		this.endDate = endDate;
		this.days = null;
	}
	
	/*
	 * constructor
	 */
	public Event(String eventName, TimeInterval timeinterval, LocalDate startDay, LocalDate endDate, DayOfWeek [] days) {
		super();
		this.eventName = eventName;
		this.timeinterval = timeinterval;
		this.startDay = startDay;
		this.endDate = endDate;
		this.days = days;
	}


	/*
	 * getter and setter for all fields
	 */
	public String getEventName() {
		return eventName;
	}


	public void setEventName(String eventName) {
		this.eventName = eventName;
	}


	public TimeInterval getTimeinterval() {
		return timeinterval;
	}


	public void setTimeinterval(TimeInterval timeinterval) {
		this.timeinterval = timeinterval;
	}


	public LocalDate getStartDay() {
		return startDay;
	}


	public void setStartDay(LocalDate startDay) {
		this.startDay = startDay;
	}


	public LocalDate getEndDate() {
		return endDate;
	}


	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}


	public DayOfWeek[] getDays() {
		return days;
	}


	public void setDays(DayOfWeek[] days) {
		this.days = days;
	}


	@Override
	public int compareTo(Event o) {
		return timeinterval.getStarttime().compareTo(o.getTimeinterval().getStarttime());
	}
	
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM d yyyy");
		return String.format("%s \n %s : %s",eventName, formatter.format(startDay),  timeinterval);
	}
}
