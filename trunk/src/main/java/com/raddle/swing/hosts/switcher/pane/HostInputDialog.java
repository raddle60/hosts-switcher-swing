package com.raddle.swing.hosts.switcher.pane;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang.StringUtils;

public class HostInputDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField domainTxt;
    private JTextField ipTxt;
    private boolean ok = false;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            HostInputDialog dialog = new HostInputDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public HostInputDialog(){
        setTitle("添加Host");
        setBounds(100, 100, 337, 152);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        {
            JLabel label = new JLabel("域名");
            label.setBounds(12, 12, 70, 15);
            contentPanel.add(label);
        }
        {
            domainTxt = new JTextField();
            domainTxt.setBounds(77, 10, 226, 25);
            contentPanel.add(domainTxt);
            domainTxt.setColumns(10);
        }
        {
            JLabel lblIp = new JLabel("IP");
            lblIp.setBounds(12, 39, 70, 15);
            contentPanel.add(lblIp);
        }
        {
            ipTxt = new JTextField();
            ipTxt.setBounds(77, 39, 226, 28);
            contentPanel.add(ipTxt);
            ipTxt.setColumns(10);
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("确定");
                okButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (StringUtils.isEmpty(domainTxt.getText())) {
                            JOptionPane.showMessageDialog(null, "请输入域名");
                            return;
                        }
                        if (StringUtils.isEmpty(ipTxt.getText())) {
                            JOptionPane.showMessageDialog(null, "请输入Ip");
                            return;
                        }
                        ok = true;
                        HostInputDialog.this.dispose();
                    }
                });
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("取消");
                cancelButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        HostInputDialog.this.dispose();
                    }
                });
                buttonPane.add(cancelButton);
            }
        }
    }

    public String getDomain() {
        return domainTxt.getText();
    }

    public String getIp() {
        return ipTxt.getText();
    }

    public boolean isOk() {
        return ok;
    }
}
