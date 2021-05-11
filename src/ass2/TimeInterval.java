package ass2;

/**
 * @author Hieu Hoang
 * CS151
 * ID: 013618020
 * Assignment 2
 * 09/30/2020
 */



import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/*
 * TimeInterval class that represent an interval of time
 */
public class TimeInterval {
	private static LocalTime starttime;
	private static LocalTime endtime;
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
	
	/*
	 * constructor
	 */
	public TimeInterval(LocalTime starttime, LocalTime endtime) {
		super();
		TimeInterval.starttime = starttime;
		TimeInterval.endtime = endtime;
	}
	
	/*
	 * use to check conflict time between to event
	 */
	public static boolean CheckOverlap(TimeInterval timeinterval) {
		boolean overlap = false;
		if (timeinterval.starttime.isAfter(endtime) || 
				timeinterval.endtime.isBefore(starttime)) 
			overlap = true;
		
		return overlap;
		
	}

	/*
	 * getters and setters
	 */
	public static LocalTime getStarttime() {
		return starttime;
	}

	public static void setStarttime(LocalTime starttime) {
		TimeInterval.starttime = starttime;
	}

	public static LocalTime getEndtime() {
		return endtime;
	}

	public static void setEndtime(LocalTime endtime) {
		TimeInterval.endtime = endtime;
	}
	
	@Override
	public String toString() {
		return String.format("%s - %s", starttime.format(formatter), endtime.format(formatter));
	}

}
