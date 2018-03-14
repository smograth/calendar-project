// Siena Smith; 500775103; siena.e.smith@ryerson.ca

import java.util.*;

/**
 * @author Siena Smith; 500775103; siena.e.smith@ryerson.ca
 */

public class Appointment implements Comparable<Appointment> {
	/**
	 * Date of the appointment.
	 */
	private Calendar date;
	/**
	 * Description of the appointment.
	 */
    private String description;
    
    /**
     * Default constructor.                          
     */
    public Appointment() {
        date = new GregorianCalendar();
        description = "";
    }
    /**
     * Constructor with parameters for description and calendar values.
     * @param yr Year in which appointment takes place.
     * @param mnth Month in which appointment takes place.
     * @param dom Day of the month on which appointment takes place.
     * @param hod Hour of the day at which appointment takes place.
     * @param mnt Minute of the day at which appointment takes place.
     * @param desc Description of the appointment.
     */
    public Appointment(int yr, int mnth, int dom, int hod, int mnt, String desc) {
        date = new GregorianCalendar(yr, mnth, dom , hod, mnt);
        description = desc;
    }
    /**
     * Gets description of the appointment.
     * @return Description of the appointment. 
     */
    public String getDescription() {
        return description;
    }
    /**
     * Sets description of the appointment.
     * @param desc Description to set.
     */
    public void setDescription(String desc) {
        description = desc;
    }
    /**
     * Gets date value of the appointment.
     * @return Date value, as a Calendar.
     */
    public Calendar getDate() {
        return date;
    }
    /**
     * Sets date value of the appointment.
     * @param yr Year in which appointment takes place.
     * @param mnth Month in which appointment takes place.
     * @param dom Day of the month on which appointment takes place.
     * @param hod Hour of the day at which appointment takes place.
     * @param mnt Minute of the day at which appointment takes place.
     */
    public void setDate(int yr, int mnth, int dom, int hod, int mnt) {
        date.set(yr, mnth, dom, hod, mnt);
    }
    
    /**
     * Describes the appointment in a string.
     * @return String describing the appointment in format "16:20: Dentist".
     */
    public String print() {
    	String minute = "" + date.get(Calendar.MINUTE);
    	if (minute.length() == 1) minute = "0" + minute;
        return date.get(Calendar.HOUR_OF_DAY) + ":" + minute + ": " + description;
    }
    /**
     * Checks if an appointment takes place at a certain time.
     * @param yr Year to check.
     * @param mnth Month to check.
     * @param dom Day to check.
     * @param hod Hour to check. If this or mnt is negative, it will merely check the date.
     * @param mnt Minute to check. If this or hod is negative, it will merely check the date.
     * @return true if appointment occurs at specified time; false if it does not.
     */
    public boolean occursOn(int yr, int mnth, int dom, int hod, int mnt) {
    	if (hod < 0 || mnt < 0) {
    		return (date.get(Calendar.YEAR) == yr && 
    				date.get(Calendar.MONTH) == mnth &&
    				date.get(Calendar.DATE) == dom);
    	}
    	GregorianCalendar occurs = new GregorianCalendar(yr, mnth, dom, hod, mnt);
	    if (date.compareTo(occurs) == 0) return true;
	    else return false;
    }
    /**
     * Compares one appointment with another.
     * @param a Appointment to compare with this.
     * @return A negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Appointment a) {
    	if (date.compareTo(a.getDate()) == 0) {
    		return description.compareTo(a.getDescription());
    	}
    	else return date.compareTo(a.getDate());
    }
}
