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
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class TurkeltaubClinicalAddHospitalFrame extends JFrame {

	private TurkeltaubClinicalFrame mainFrame;
	private JPanel contentPane;
	private final JLabel lblHospitalName = new JLabel("Hospital Name:");
	private final JLabel lblStreetAddress = new JLabel("Street Address:");
	private final JLabel lblCity = new JLabel("City:");
	private final JLabel lblState = new JLabel("State:");
	private final JLabel lblZip = new JLabel("Zip:");
	private final JTextField textFieldName = new JTextField();
	private final JTextField textFieldAddress = new JTextField();
	private final JTextField textFieldCity = new JTextField();
	private final JTextField textFieldState = new JTextField();
	private final JTextField textFieldZip = new JTextField();
	private final JButton btnSave = new JButton("Save");
	private final JLabel lblNewHospital = new JLabel("New Hospital");
	
	/**
	 * Create the frame.
	 */
	public TurkeltaubClinicalAddHospitalFrame(TurkeltaubClinicalFrame main) {
		mainFrame = main;
		
		textFieldZip.setColumns(10);
		textFieldState.setColumns(10);
		textFieldCity.setColumns(10);
		textFieldAddress.setColumns(10);
		textFieldName.setColumns(10);
		jbInit();
	}
	private void jbInit() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 826, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		lblNewHospital.setFont(new Font("Tahoma", Font.BOLD, 21));
		
		contentPane.add(lblNewHospital, "2, 2");
		
		contentPane.add(lblHospitalName, "2, 4, right, default");
		
		contentPane.add(textFieldName, "4, 4, fill, default");
		
		contentPane.add(lblStreetAddress, "2, 6, right, default");
		
		contentPane.add(textFieldAddress, "4, 6, fill, default");
		
		contentPane.add(lblCity, "2, 8, right, default");
		
		contentPane.add(textFieldCity, "4, 8, fill, default");
		
		contentPane.add(lblState, "2, 10, right, default");
		
		contentPane.add(textFieldState, "4, 10, fill, default");
		
		contentPane.add(lblZip, "2, 12, right, default");
		
		contentPane.add(textFieldZip, "4, 12, fill, default");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_btnSave_actionPerformed(e);
			}
		});
		
		contentPane.add(btnSave, "4, 14");
	}

	protected void do_btnSave_actionPerformed(ActionEvent e) {
		Hospital hospital = new Hospital(
			textFieldName.getText(),
			textFieldAddress.getText(),
			textFieldCity.getText(),
			textFieldState.getText(),
			textFieldZip.getText()
		);
		
		hospital.save();
		
		mainFrame.populateCurrentHospital();
		
		this.dispose();
	}
}
