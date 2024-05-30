package com.ibm.training.swing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.ibm.training.jdbc.Product;
import com.ibm.training.jdbc.ProductDAO;

public class SwingUIApp implements ItemListener,ActionListener{
	
	ProductDAO dao = new ProductDAO();
	//Declare all ui controls
	
	JFrame jFrame = new JFrame("My Swing App!!");
	JLabel lblName = new JLabel("Product Name");
	JLabel lblPrice = new JLabel("Product Price");
	JLabel lblQoh = new JLabel("Qty On Hand");
	JTextField txtName = new JTextField(15);
	JTextField txtPrice = new JTextField(15);
	JTextField txtQoh = new JTextField(15);
	JButton btnOk = new JButton("Save My Data");
	JButton btnCancel = new JButton("Cancel");
	JTextField txtStatus = new JTextField(20);
	
	String[] looks = new String[] {"Metal","Motif","Windows","Nimbus"};
	JComboBox<String> lookNFeel = new JComboBox<>(looks);
	//set up ui
	public SwingUIApp() {
		jFrame.setSize(300, 300);
		jFrame.setLocation(350,300);
		
		JPanel panel = new JPanel();
		panel.add(lblName);
		panel.add(txtName);
		panel.add(lblPrice);
		panel.add(txtPrice);
		panel.add(lblQoh);
		panel.add(txtQoh);
		panel.add(btnOk);
		panel.add(btnCancel);
		panel.add(txtStatus);
		txtStatus.setEditable(false);
		panel.add(lookNFeel);
		lookNFeel.addItemListener(this);
		
		jFrame.setContentPane(panel);
		jFrame.setVisible(true);
		
		btnCancel.addActionListener(this);
		btnOk.addActionListener(this);
		
		jFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		new SwingUIApp();

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		String selectedLNF = (String) lookNFeel.getSelectedItem();
		try {
			switch (selectedLNF) {
			case "Metal":
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
				break;
			case "Windows":
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				break;
			case "Nimbus":
				UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
				break;
			case "Motif":
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
				break;
			}
		} catch (Exception e2) {
			e2.printStackTrace();
			return;
		}
		
		SwingUtilities.updateComponentTreeUI(jFrame);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object srcOfEvent = e.getSource();
		
		if(srcOfEvent == btnCancel) {
			txtName.setText("");
			txtPrice.setText("");
			txtQoh.setText("");
		}else if(srcOfEvent == btnOk) {
			String nameText = txtName.getText();
			String priceText = txtPrice.getText();
			String qohText = txtQoh.getText();
			
			float price = 0;
			
			try {
				price = Float.parseFloat(priceText);
			} catch (NumberFormatException e1) {
				txtPrice.setForeground(Color.RED);
				return;
			}
			
			int qoh = 0;
			try {
				qoh = Integer.parseInt(qohText);
			} catch (NumberFormatException e2) {
				txtQoh.setForeground(Color.RED);
				return;
			}
			Product p = new Product(nameText, price, qoh);
			int id = dao.save(p);
			txtStatus.setText("Created Product With id:"+id);
			txtName.setText("");
			txtPrice.setText("");
			txtPrice.setForeground(Color.BLACK);
			txtQoh.setText("");
			txtQoh.setForeground(Color.BLACK);
		}
	}

}
