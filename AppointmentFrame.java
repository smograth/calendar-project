// Siena Smith; 500775103; siena.e.smith@ryerson.ca

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * @author Siena Smith; 500775103; siena.e.smith@ryerson.ca
 * Frame for viewing appointments.
 */
public class AppointmentFrame extends JFrame {
	

	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 800;
	
	private JLabel displayDate;
	private JScrollPane dsplyScroll;	
	private JTextArea displayAppointments;
	private JPanel controlPanel;
	private JPanel datePanel;
	private JPanel actionPanel;
	private JPanel descPanel;
	private JButton left;
	private JButton right;
	private JLabel d;
	private JTextField df;
	private JLabel m;
	private JTextField mf;
	private JLabel y;
	private JTextField yf;
	private JButton show;
	private JLabel h;
	private JTextField hf;
	private JLabel n;
	private JTextField nf;
	private JButton create;
	private JButton cancel;
	
	private Calendar date;
	private SimpleDateFormat SDF;
	
	private ArrayList<Appointment> appointments;


	private JTextArea desc;
	
	/**
	 * Constructs full frame, except a call to createControlPanel.
	 */
	public AppointmentFrame() {
		date = Calendar.getInstance();
		SDF = new SimpleDateFormat("EEE, MMM d, yyyy");
		displayDate = new JLabel(SDF.format(date.getTime()));
		appointments = new ArrayList<Appointment>();
		
		//these are the test appointments
		appointments.add(new Appointment(date.get(Calendar.YEAR), 
				date.get(Calendar.MONTH), 
				date.get(Calendar.DATE), 12, 0, "Lunch"));
		appointments.add(new Appointment(date.get(Calendar.YEAR), 
				date.get(Calendar.MONTH), 
				date.get(Calendar.DATE), 8, 0, "Breakfast"));
		appointments.add(new Appointment(date.get(Calendar.YEAR), 
				date.get(Calendar.MONTH), 
				date.get(Calendar.DATE), 20, 0, "Dinner"));
		
		displayAppointments = new JTextArea();
		displayAppointments.setEditable(false);
		dsplyScroll = new JScrollPane(displayAppointments);
		add(dsplyScroll, BorderLayout.CENTER);
		add(displayDate, BorderLayout.NORTH);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		controlPanel = createControlPanel();
		add(controlPanel, BorderLayout.SOUTH);
		
		printAppointments();
		
	}
	
	/**
	 * This creates the control panel, calling the subpanel creation functions.
	 * @return Control panel with functional subpanels.
	 */
	private JPanel createControlPanel() {
		JPanel controlPanel = new JPanel(new BorderLayout());
		datePanel = createDatePanel();
		controlPanel.add(datePanel, BorderLayout.NORTH);
		actionPanel = createActionPanel();
		controlPanel.add(actionPanel, BorderLayout.CENTER);
		descPanel = createDescPanel();
		controlPanel.add(descPanel, BorderLayout.SOUTH);
		return controlPanel;
	}

	/**
	 * This creates the date panel.
	 * @return Date panel with functioning components.
	 */
	private JPanel createDatePanel() {
		
		//setting up the panel itself
		datePanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		Border dBorder = BorderFactory.createTitledBorder("Date");
		datePanel.setBorder(dBorder);
		
		//initial settings
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 5, 5, 5);
		
		//left and right buttons
		left = new JButton("<");
		left.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            	date.add(Calendar.DATE, -1);
				displayDate.setText(SDF.format(date.getTime()));
				printAppointments();
				
				//updates the dmy fields
				yf.setText("" + date.get(Calendar.YEAR));
				mf.setText("" + (date.get(Calendar.MONTH) + 1));
				df.setText("" + date.get(Calendar.DATE));
            }
        });
		datePanel.add(left, c);
		
		right = new JButton(">");
		right.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            	date.add(Calendar.DATE, 1);
				displayDate.setText(SDF.format(date.getTime()));
				
				//updates the dmy fields
				yf.setText("" + date.get(Calendar.YEAR));
				mf.setText("" + (date.get(Calendar.MONTH) + 1));
				df.setText("" + date.get(Calendar.DATE));
				printAppointments();
            }
        });
		
		
		//dmy labels and fields added here
		c.gridx = 3;
		datePanel.add(right, c);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		
		d = new JLabel("Day");
		datePanel.add(d, c);
		c.gridx = 1;
		df = new JTextField("" + date.get(Calendar.DATE), 4); // these autofill with current time/date at the beginning
		datePanel.add(df, c);
		
		m = new JLabel("Month");
		c.gridx = 2;
		datePanel.add(m, c);
		c.gridx = 3;
		mf = new JTextField("" + (date.get(Calendar.MONTH) + 1), 4);
		datePanel.add(mf, c);
		
		y = new JLabel("Year");
		c.gridx = 4;
		datePanel.add(y, c);
		c.gridx = 5;
		yf = new JTextField("" + date.get(Calendar.YEAR), 4);
		datePanel.add(yf, c);
		
		//show button
		show = new JButton("Show");
		show.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//this try-catch structuring is so it won't let errors stop it from interpreting any valid fields. 
				//it's also used in the create and cancel buttons
				try {
					if (Integer.valueOf(yf.getText()) > 0) date.set(Calendar.YEAR, Integer.valueOf(yf.getText()));
					else yf.setText("" + date.get(Calendar.YEAR)); 
					//on error, these fields reset to current date. this is also used in the create and cancel buttons
				} catch (Exception ee) {
					yf.setText("" + date.get(Calendar.YEAR));
				}
				
				try {
					if (Integer.valueOf(mf.getText()) > 0 && 
							Integer.valueOf(mf.getText()) < 13) date.set(Calendar.MONTH, (Integer.valueOf(mf.getText()) - 1));
					else mf.setText("" + (date.get(Calendar.MONTH) + 1));
				} catch (Exception ee) {
					mf.setText("" + (date.get(Calendar.MONTH) + 1));
				}
				
				try {
					if (Integer.valueOf(df.getText()) > 0 &&
							Integer.valueOf(df.getText()) < date.getActualMaximum(Calendar.DATE)) date.set(Calendar.DATE, Integer.valueOf(df.getText()));
					else df.setText("" + date.get(Calendar.DATE));
				} catch (Exception ee) {
					df.setText("" + date.get(Calendar.DATE));
				}
				displayDate.setText(SDF.format(date.getTime()));
				printAppointments();
			}
			
		});
		
		c.gridy = 3;
		c.gridx = 0;
		c.gridwidth = 6;
		datePanel.add(show, c);
		
		return datePanel;
	}
	
	/**
	 * Creates the action panel.
	 * @return The action panel, with working components.
	 */
	private JPanel createActionPanel() {
		
		//init. setup
		actionPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		Border aBorder = BorderFactory.createTitledBorder("Action");
		actionPanel.setBorder(aBorder);
		c.insets = new Insets(5, 5, 5, 5);
		
		//hour and minute labels and fields added here
		h = new JLabel("Hour");
		actionPanel.add(h, c);
		c.gridx = 1;
		hf = new JTextField("" + date.get(Calendar.HOUR_OF_DAY), 4); //these also autofill at start
		actionPanel.add(hf, c);
		
		n = new JLabel("Minute");
		c.gridx = 2;
		actionPanel.add(n, c);
		c.gridx = 3;
		nf = new JTextField("" + date.get(Calendar.MINUTE), 4);
		actionPanel.add(nf, c);
		
		
		//create button
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		create = new JButton("Create");
		create.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				try {
					if (Integer.valueOf(hf.getText()) >= 0 &&
							Integer.valueOf(hf.getText()) < 24) date.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hf.getText()));
					else hf.setText("" + date.get(Calendar.HOUR_OF_DAY));
				} catch (Exception e) {
					hf.setText("" + date.get(Calendar.HOUR_OF_DAY));
				}
				try {
					if (Integer.valueOf(nf.getText()) >= 0 &&
							Integer.valueOf(nf.getText()) < 60) date.set(Calendar.MINUTE, Integer.valueOf(nf.getText()));
					else nf.setText("" + date.get(Calendar.MINUTE));
				} catch (Exception e) {
					nf.setText("" + date.get(Calendar.MINUTE));
				}
				Appointment alreadyExists = findAppointment(date.get(Calendar.YEAR),
						date.get(Calendar.MONTH), 
						date.get(Calendar.DATE), 
						date.get(Calendar.HOUR_OF_DAY),
						date.get(Calendar.MINUTE));
				if (alreadyExists == null ) {
					String dsc;
					//if user hasn't entered a description, it uses a dummy one
					if (desc.getText().equals("")) dsc = "No description";
					else dsc = desc.getText();
					Appointment toCreate = new Appointment(date.get(Calendar.YEAR), 
							date.get(Calendar.MONTH), 
							date.get(Calendar.DATE), 
							date.get(Calendar.HOUR_OF_DAY), 
							date.get(Calendar.MINUTE), dsc);
					appointments.add(toCreate);
					Collections.sort(appointments);
					printAppointments();
				} else desc.setText("CONFLICT!!");
			}
			
		});
		actionPanel.add(create, c);
		
		c.gridx = 2;
		cancel = new JButton("Cancel"); //Cancel is structured very similarly to Create
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				try {
					if (Integer.valueOf(hf.getText()) >= 0 &&
							Integer.valueOf(hf.getText()) < 24) date.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hf.getText()));
				} catch (Exception e) {
					date.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hf.getText()));
				}
				
				try {
					if (Integer.valueOf(nf.getText()) >= 0 &&
							Integer.valueOf(nf.getText()) < 60) date.set(Calendar.MINUTE, Integer.valueOf(nf.getText()));
				} catch (Exception e) {
					date.set(Calendar.MINUTE, Integer.valueOf(nf.getText()));
				}
				Appointment toCancel = findAppointment(date.get(Calendar.YEAR), 
						date.get(Calendar.MONTH), 
						date.get(Calendar.DATE), 
						date.get(Calendar.HOUR_OF_DAY), 
						date.get(Calendar.MINUTE));
				if (toCancel != null ) {
					appointments.remove(appointments.indexOf(toCancel));
					Collections.sort(appointments);
					printAppointments();
				}
			}
			
		});
		actionPanel.add(cancel, c);
		
		return actionPanel;
	}
	
	/**
	 * Creates description panel.
	 * @return Description panel with functioning JTextArea.
	 */
	private JPanel createDescPanel() {
		descPanel = new JPanel(new BorderLayout());
		Border dscBorder = BorderFactory.createTitledBorder("Description");
		descPanel.setBorder(dscBorder);
		desc = new JTextArea(3, 8);
		descPanel.add(desc, BorderLayout.CENTER);
		return descPanel;
	}

	/**
	 * Prints appointments to the display.
	 */
	private void printAppointments() {
		Collections.sort(appointments);
		displayAppointments.setText(null);
		for (Appointment ap : appointments) {
			if (ap.occursOn(date.get(Calendar.YEAR), 
					date.get(Calendar.MONTH),
					date.get(Calendar.DATE), -1, -1)) { // the negative variables indicate not to check hour/minute
				displayAppointments.append(ap.print() + System.getProperty("line.separator"));
			}
		}
		
	}
	
	
	/**
	 * 
	 * @param y Year to check.
	 * @param m Month to check.
	 * @param d Day to check.
	 * @param h Hour to check.
	 * @param n Minute to check.
	 * @return Null if no appointment occurs at this time; otherwise, the appointment that occurs at that time.
	 */
	private Appointment findAppointment(int y, int m, int d, int h, int n) {
		for (Appointment ap : appointments) {
			if (ap.occursOn(y, m, d, h, n)) return ap;
		}
		return null;
	}
}
