package com.ibm.training.awt;

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.ibm.training.jdbc.Product;
import com.ibm.training.jdbc.ProductDAO;

public class ProductFormGUIApp extends Frame implements ActionListener {
	
	//Data Access Layer Object
	ProductDAO dao = new ProductDAO();
	
	//Declare the UI components
	Label lblName = new Label("Product Name");
	Label lblPrice = new Label("Product Price");
	Label lblQoh = new Label("Qty On Hand");
	TextField txtName = new TextField(15);
	TextField txtPrice = new TextField(15);
	TextField txtQoh = new TextField(15);
	Button btnOk = new Button("Save My Data");
	Button btnCancel = new Button("Cancel");
	TextField txtStatus = new TextField(20);
	
	//set up the ui
	public ProductFormGUIApp() {
		super("Product Form AWT App!");
		this.setSize(300, 300);
		this.setLocation(300, 300);
		
		Panel p = new Panel();
		p.add(lblName);
		p.add(txtName);
		p.add(lblPrice);
		p.add(txtPrice);
		p.add(lblQoh);
		p.add(txtQoh);
		p.add(btnOk);
		p.add(btnCancel);
		p.add(txtStatus);
		txtStatus.setEditable(false);
		
		this.add(p);
		this.setVisible(true);
		
		btnCancel.addActionListener(this);
		btnOk.addActionListener(this);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		new ProductFormGUIApp();
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
