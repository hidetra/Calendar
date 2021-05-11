package ass2;

/**
 * @author Hieu Hoang
 * CS151
 * ID: 013618020
 * Assignment 2
 * 09/30/2020
 */



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Scanner;
/*
 * MyCalendarTester is the main class
 * use to display menu
 */
public class MyCalendarTester {
	
	static MyCalendar mycalendar = new MyCalendar();
	static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("[M/d/uuuu");
	static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
	
	/**
	 * The main method.
	 *
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {
		
		//Print the current Month
		LocalDate currentDate = LocalDate.now();
		mycalendar.PrinttheMonth(currentDate);
		
		//Read input file
		String fileName = "input.txt";
			
		ReadFromFile(fileName);
		boolean exit = true;
		while(exit) {
			Scanner input = new Scanner(System.in);
			System.out.println("Select one of the following main menu options:\r\n" + 
					"[V]iew by  [C]reate, [G]o to [E]vent list [D]elete  [Q]uit");
			String option = input.nextLine();  
		    switch(option) {
		    case "V": 
		    	ViewEvent(currentDate);
		    	break;
		    case "C":
		    	CreateEvent();
		    	break;
		    case "G":
		    	GoToCalendar();
		    	break;
		    case "E":
		    	mycalendar.PrintEventList();
		    	break;
		    case "D":
		    	DeleteEvent();
		    	break;
		    case "Q":
		    	WriteIntoFile();
		    	exit=false;
		    	break;
		    default:
		    	System.out.print("Error\n");
		    	break;
		    }
		}
		
	}

	/*
	 * Save all the events into a file named output.txt
	 */
	private static void WriteIntoFile() {
		try {
		      File myObj = new File("output.txt");
		      myObj.createNewFile();
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		mycalendar.WriteinFile("output.txt");
		System.out.println("GOOD BYE");
		
	}

	/*
	 * Delete event when user choose delete
	 */
	private static void DeleteEvent() {
		Scanner input = new Scanner(System.in);
		System.out.println("[S]elected  [A]ll   [DR]");
		String data = input.nextLine();
		switch(data) {
	    case "S":
	    	System.out.println("Enter the date [mm/dd/yyyy]");
			String dateStr = input.nextLine();
			LocalDate date = LocalDate.parse(dateStr,dateFormatter);
			mycalendar.DayView(date);
			System.out.println("Enter the name of the event to delete:");
			String name = input.nextLine();
			mycalendar.DeleteSelected(date, name);
			break;
	    case "A":
	    	System.out.println("Enter the date [mm/dd/yyyy]");
			String dateA = input.nextLine();
	    	mycalendar.DeleteAll(LocalDate.parse(dateA,dateFormatter));
	    	break;
	    case "DR":
	    	System.out.println("Enter the name of the event to delete:");
			String nameDR = input.nextLine();
			mycalendar.DeleteDR(nameDR);
	    	break;
	    default:
	    	break;
		}
	}

	/*
	 * Go to a specific day when user input G
	 */
	private static void GoToCalendar() {
		Scanner input = new Scanner(System.in);
		System.out.println("Date: MM/DD/YYYY ");
		String date = input.nextLine();
		mycalendar.DayView(LocalDate.parse(date, dateFormatter));
	}

	/*
	 * Create a single event when user input C
	 */
	private static void CreateEvent() {
		Scanner input = new Scanner(System.in);
		System.out.println("Name: ");
		String name = input.nextLine();
		System.out.println("Date: MM/DD/YYYY ");
		String date = input.nextLine();
		System.out.println("Starting time: 24 hour clock such as 06:00 for 6 AM and 15:30 for 3:30 PM ");
		String start = input.nextLine();
		System.out.println("Ending time: 24 hour clock such as 06:00 for 6 AM and 15:30 for 3:30 PM ");
		String end = input.nextLine();
		mycalendar.CreateOneTimeEvent(name, date, start, end);
	}

	/*
	 * use to view Day or Month
	 * 
	 * @param currentDate
	 */
	private static void ViewEvent(LocalDate currentDate) {
		Scanner input = new Scanner(System.in);
		System.out.println("[D]ay view or [M]view ? ");
		String option = input.nextLine();  
	    
		switch(option) {
	    case "D":
	    	boolean ed = false;
	    	
		    	mycalendar.DayView(currentDate);
		    	LocalDate day = currentDate;
		    while (!ed) {
		    	Scanner scanner = new Scanner(System.in);
				System.out.println("[P]revious or [N]ext or [G]o back to the main menu ?");
				String choice = input.nextLine(); 
				
				switch(choice) {
				case "P":
					day = day.minusDays(1);
					mycalendar.DayView(day);
					break;
				case "N":
					day = day.plusDays(1);
					mycalendar.DayView(day);
					break;
				case "G":
					ed = true;
					break;
				default:
					System.out.print("Error\n");
				    break;
				}
	    	}
	    	break;
	    
	    case "M":
	    	boolean em = false;
	    	
		    	mycalendar.PrinttheMonth(currentDate);
		    	LocalDate month = currentDate;
		    while (!em) {
		    	Scanner scanner = new Scanner(System.in);
				System.out.println("[P]revious or [N]ext or [G]o back to the main menu ?");
				String choice = input.nextLine(); 
				
				switch(choice) {
				case "P":
					month = month.minusMonths(1);
					mycalendar.PrinttheMonth(month);
					break;
				case "N":
					month = month.plusMonths(1);
					mycalendar.PrinttheMonth(month);
					break;
				case "G":
					em = true;
					break;
				default:
					System.out.print("Error\n");
				    break;
				}
	    	}
		    break;
	    
	    default:
	    	System.out.print("Error\n");
	    	break;
	    }
	}

	/*
	 * use to read file input.txt and create events from that file 
	 * 
	 * @param fileName file contain all events
	 * @throws IOException
	 */
	private static void ReadFromFile(String fileName) throws IOException {
		
		try {
			File myObj = new File(fileName);
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		    	  String name = myReader.nextLine();
		    	  String [] data = myReader.nextLine().split("\\s+");
		    	  if (data.length == 3)
		    		  mycalendar.CreateOneTimeEvent(name, data[0], data[1], data [2]);
		    	  else if (data.length ==5)
		    		  mycalendar.CreateRecurringEvent(name, data);
		      }
		      System.out.println("Loading is done!");
		      myReader.close();
		    } catch (FileNotFoundException e) {
		    	File myReader = new File(fileName);
		    	myReader.createNewFile();
		    }
		
	}

}
