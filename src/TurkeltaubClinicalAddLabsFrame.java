import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import javax.swing.JFormattedTextField;
import java.awt.Font;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;


public class TurkeltaubClinicalAddLabsFrame extends JFrame {

	private TurkeltaubClinicalFrame mainFrame;
	private String hospital;
	private JPanel contentPane;
	private final JPanel panelForm = new JPanel();
	private final JPanel panelTable = new JPanel();
	private final JLabel lblHospitalLabs = new JLabel("Hospital Labs");
	private final JLabel lblLabName = new JLabel("Lab Name:");
	private final JLabel lblWeeks = new JLabel("Weeks:");
	private final JLabel lblLength = new JLabel("Length:");
	private final JComboBox comboBoxLabName = new JComboBox();
	private final JScrollPane scrollPane = new JScrollPane();
	private final JFormattedTextField formattedTextFieldLength = new JFormattedTextField();
	private final JButton btnSave = new JButton("Save");
	private final JList listWeeks = new JList();

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					TurkeltaubClinicalAddLabsFrame frame = new TurkeltaubClinicalAddLabsFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public TurkeltaubClinicalAddLabsFrame(TurkeltaubClinicalFrame main, String h) {
		mainFrame = main;
		hospital = h;
	
		jbInit();
	}
	private void jbInit() {
		setBounds(100, 100, 984, 762);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		panelForm.setBounds(21, 21, 464, 573);
		
		contentPane.add(panelForm);
		panelForm.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		lblHospitalLabs.setFont(new Font("Tahoma", Font.BOLD, 21));
		
		panelForm.add(lblHospitalLabs, "2, 2");
		
		panelForm.add(lblLabName, "2, 4, right, default");
		comboBoxLabName.setModel(new DefaultComboBoxModel(new String[] {"Hematology", "Blood Bank", "Clinical Chemistry", "Clinical Microbiology", "Urinalysis", "Outpatient Phlebotomy", "Inpatient Phlebotomy"}));
		
		panelForm.add(comboBoxLabName, "4, 4, fill, default");
		
		panelForm.add(lblWeeks, "2, 6");
		
		panelForm.add(scrollPane, "4, 6, fill, fill");
		listWeeks.setModel(new AbstractListModel() {
			String[] values = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		scrollPane.setViewportView(listWeeks);
		
		panelForm.add(lblLength, "2, 8, right, default");
		
		panelForm.add(formattedTextFieldLength, "4, 8, fill, default");
		
		panelForm.add(btnSave, "4, 10");
		panelTable.setBounds(485, 21, 452, 573);
		
		contentPane.add(panelTable);
		
		lblHospitalLabs.setText(hospital + ": Labs");
	}
}
