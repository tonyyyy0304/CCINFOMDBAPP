package main.java.mvc_folder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {

    private JPanel mainPanel;

    //Products Table
    private JTextField productId, productName, storeId, stockCount, description;

    private JButton productAddBtn, productRemoveBtn, userAddBtn, userRemoveBtn;

    public View() {
        // Set up the frame
        setTitle("Swing Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        init();

        // Add components to the frame
        add(mainPanel);
    }

    public void init() {
        mainPanel = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add tabs
        tabbedPane.addTab("Add Product", productAddPnl());
        tabbedPane.addTab("Remove Product", productRemovePnl());
        tabbedPane.addTab("Add User", userAddPnl());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
    }

    public JPanel productAddPnl() {
        JPanel panel = new JPanel(new GridLayout(5, 2));

        panel.add(new JLabel("Product ID:"));
        productId = new JTextField();
        panel.add(productId);

        panel.add(new JLabel("Product Name:"));
        productName = new JTextField();
        panel.add(productName);

        panel.add(new JLabel("Store ID:"));
        storeId = new JTextField();
        panel.add(storeId);

        panel.add(new JLabel("Stock Count:"));
        stockCount = new JTextField();
        panel.add(stockCount);

        panel.add(new JLabel("Description:"));
        description = new JTextField();
        panel.add(description);

        productAddBtn = new JButton("Add Product");
        productAddBtn.setActionCommand("Add Product");
        panel.add(productAddBtn);

        return panel;
    }

    public JPanel productRemovePnl() {
        JPanel panel = new JPanel(new GridLayout(2, 2));

        panel.add(new JLabel("Product ID:"));
        productId = new JTextField();
        panel.add(productId);

        productRemoveBtn = new JButton("Remove Product");
        productRemoveBtn.setActionCommand("Remove Product");
        panel.add(productRemoveBtn);

        return panel;
    }

    public JPanel userAddPnl() {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        JTextField userId = new JTextField();
        JTextField userName = new JTextField();

        panel.add(new JLabel("User ID:"));
        panel.add(userId);

        panel.add(new JLabel("User Name:"));
        panel.add(userName);

        userAddBtn = new JButton("Add User");
        userAddBtn.setActionCommand("Add User");
        panel.add(userAddBtn);

        return panel;
    }

    public JPanel userRemovePnl() {
        JPanel panel = new JPanel(new GridLayout(2, 2));

        panel.add(new JLabel("User ID:"));
        productId = new JTextField();
        panel.add(productId);

        userRemoveBtn = new JButton("Remove User");
        userRemoveBtn.setActionCommand("Remove User");
        panel.add(userRemoveBtn);

        return panel;
    }

    public void setProductRemoveBtn(ActionListener listener) {
        productRemoveBtn.addActionListener(listener);
    }

    public void setProductAddBtn(ActionListener listener) {
        productAddBtn.addActionListener(listener);
    }

    public void setUserAddBtn(ActionListener listener) {
        userAddBtn.addActionListener(listener);
    }

    public void setUserRemoveBtn(ActionListener listener) {
        userRemoveBtn.addActionListener(listener);
    }

    public String getProductId() {
        return productId.getText();
    }

    public String getProductName() {
        return productName.getText();
    }

    public String getStoreId() {
        return storeId.getText();
    }

    public String getStockCount() {
        return stockCount.getText();
    }

    public String getDescription() {
        return description.getText();
    }

    public void clearFields() {
        productId.setText("");
        productName.setText("");
        storeId.setText("");
        stockCount.setText("");
        description.setText("");
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }
}