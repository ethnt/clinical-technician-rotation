import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JTabbedPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JFormattedTextField;
import javax.swing.AbstractListModel;
import javax.swing.SwingConstants;


public class TurkeltaubClinicalFrame extends JFrame {

	private JPanel contentPane;
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu mnFile = new JMenu("File");
	private final JMenuItem mntmExit = new JMenuItem("Exit");
	private final JMenu mnHelp = new JMenu("Help");
	private final JMenuItem mntmAbout = new JMenuItem("About");
	private final JMenu mnNew = new JMenu("New");
	private final JMenuItem mntmHospital = new JMenuItem("Hospital...");
	private final JTable currentLabsTable = new JTable();
	private final JScrollPane scrollPane = new JScrollPane();
	private final JLabel lblLabsFound = new JLabel("0 Labs Found");
	private final JPanel panel = new JPanel();
	private final JLabel lblHospital = new JLabel("Hospital:");
	private final JLabel lblLabName = new JLabel("Lab Name:");
	private final JLabel lblWeek = new JLabel("Week:");
	private final JLabel lblLength = new JLabel("Length:");
	private final JComboBox comboBoxHospital = new JComboBox();
	private final JComboBox comboBoxLabName = new JComboBox();
	private final JFormattedTextField formattedTextFieldLength = new JFormattedTextField();
	private final JList listWeek = new JList();
	private final JScrollPane scrollPane_1 = new JScrollPane();
	private final JButton btnSave = new JButton("Save");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TurkeltaubClinicalFrame frame = new TurkeltaubClinicalFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TurkeltaubClinicalFrame() {
		jbInit();
	}
	
	/**
	 * Initialize the frame.
	 */
	private void jbInit() {
		setTitle("Clinical Technician Rotation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1180, 868);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		menuBar.setBounds(0, 0, 1154, 40);
		
		contentPane.add(menuBar);
		
		menuBar.add(mnFile);
		
		mnFile.add(mnNew);
		mntmHospital.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_mntmHospital_actionPerformed(arg0);
			}
		});
		
		mnNew.add(mntmHospital);
		
		mnFile.add(mntmExit);
		
		menuBar.add(mnHelp);
		
		mnHelp.add(mntmAbout);
		
		populateCurrentHospital();
		
		scrollPane.setBounds(456, 111, 677, 631);
		
		contentPane.add(scrollPane);
		scrollPane.setViewportView(currentLabsTable);
		currentLabsTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"Hospital", "Week", "Lab Name", "Length"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		currentLabsTable.getColumnModel().getColumn(0).setPreferredWidth(215);
		currentLabsTable.getColumnModel().getColumn(2).setPreferredWidth(325);
		lblLabsFound.setBounds(456, 77, 146, 26);
		
		contentPane.add(lblLabsFound);
		panel.setBounds(21, 111, 414, 631);
		
		contentPane.add(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("98dlu"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		panel.add(lblHospital, "2, 2, right, default");
		comboBoxHospital.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_comboBoxHospital_actionPerformed(e);
			}
		});
		
		panel.add(comboBoxHospital, "4, 2, fill, default");
		
		panel.add(lblLabName, "2, 4, right, default");
		comboBoxLabName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_comboBoxLabName_actionPerformed(e);
			}
		});
		comboBoxLabName.setModel(new DefaultComboBoxModel(new String[] {"All", "Hematology", "Blood Bank", "Clinical Chemistry", "Clinical Microbiology", "Urinalysis", "Outpatient Phlebotomy", "Inpatient Phlebotomy"}));
		
		panel.add(comboBoxLabName, "4, 4, fill, default");
		lblWeek.setHorizontalAlignment(SwingConstants.RIGHT);
		
		panel.add(lblWeek, "2, 6");
		
		panel.add(scrollPane_1, "4, 6, fill, fill");
		listWeek.setEnabled(false);
		listWeek.setModel(new AbstractListModel() {
			String[] values = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane_1.setViewportView(listWeek);
		
		panel.add(lblLength, "2, 8, right, default");
		formattedTextFieldLength.setEnabled(false);
		
		panel.add(formattedTextFieldLength, "4, 8, fill, default");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_btnSave_actionPerformed(e);
			}
		});
		btnSave.setEnabled(false);
		
		panel.add(btnSave, "4, 10");
		
		populateCurrentLabsTable();
	}
	
	public void populateCurrentHospital() {
		List<Hospital> hospitals = Hospital.all();
		
		List<String> strList = new ArrayList<String>();
		
		strList.add("All");
		
		for (Hospital hospital : hospitals) {
			strList.add(hospital.getHospitalName());
		}
		
		comboBoxHospital.setModel(new DefaultComboBoxModel(strList.toArray()));
	}
	
	public void populateCurrentLabsTable() {
		String hospital = comboBoxHospital.getSelectedItem().toString();
		String lab_name = comboBoxLabName.getSelectedItem().toString();
		
		List<Lab> labs = null;
		
//		if (hospital == "All") {
//			// Get a list of labs for the given hospital.
//			labs = Lab.all();
//		} else {
//			labs = Lab.forHospital(hospital);
//		}
		
		labs = Lab.forTable(hospital, lab_name);
		
		// Clear the table of existing records.
		while (currentLabsTable.getRowCount() > 0) {
			((DefaultTableModel) currentLabsTable.getModel()).removeRow(0);
		}

		// For every lab...
		for (int i = 0; i < labs.size(); i++) {

			// Get the array of columns in the record...
			Object[] row = labs.get(i).asArray();
			
			// And insert it as a row
			((DefaultTableModel) currentLabsTable.getModel()).insertRow(i, row);
		}
		
		lblLabsFound.setText(labs.size() + " Labs Found");
	}
	
	public void enableFields() {
		if (comboBoxHospital.getSelectedItem().toString() != "All" && comboBoxLabName.getSelectedItem().toString() != "All") {
			listWeek.setEnabled(true);
			formattedTextFieldLength.setEnabled(true);
			btnSave.setEnabled(true);
		}
	}
	
	protected void do_mntmHospital_actionPerformed(ActionEvent arg0) {
		TurkeltaubClinicalAddHospitalFrame frame = new TurkeltaubClinicalAddHospitalFrame(this);
		
		frame.setVisible(true);
	}
	
	protected void do_comboBoxHospital_actionPerformed(ActionEvent e) {
		populateCurrentLabsTable();
		enableFields();
	}
	protected void do_comboBoxLabName_actionPerformed(ActionEvent e) {
		populateCurrentLabsTable();
		enableFields();
	}
	
	protected void do_btnSave_actionPerformed(ActionEvent e) {
		// Error handling here!
		
		List<Integer> weeks = new ArrayList<Integer>();
		
		for (Object week : listWeek.getSelectedValues()) {
			weeks.add(Integer.parseInt(week.toString()));
		}

		Lab.fromForm(comboBoxHospital.getSelectedItem().toString(), comboBoxLabName.getSelectedItem().toString(), weeks, formattedTextFieldLength.getText());
	}
}
