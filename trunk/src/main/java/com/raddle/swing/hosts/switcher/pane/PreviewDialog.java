package com.raddle.swing.hosts.switcher.pane;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class PreviewDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    private JTextPane textPane = new JTextPane();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    PreviewDialog dialog = new PreviewDialog();
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the dialog.
     */
    public PreviewDialog(){
        setBounds(100, 100, 645, 424);

        JScrollPane scrollPane = new JScrollPane();
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        textPane.setEditable(false);
        scrollPane.setViewportView(textPane);

    }

    public JTextPane getTextPane() {
        return textPane;
    }

}
