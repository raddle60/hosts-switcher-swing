package com.raddle.swing.hosts.switcher.pane;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang.StringUtils;

public class HostEditDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private boolean ok = false;
    private JTextField ipTxt;
    private String oldIp;
    private JLabel domainLeb;
    private JLabel oldIpLeb;
    private JCheckBox allIpChk;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            HostEditDialog dialog = new HostEditDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public HostEditDialog(){
        setBounds(100, 100, 408, 247);
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
            domainLeb = new JLabel("");
            domainLeb.setBounds(94, 12, 272, 19);
            contentPanel.add(domainLeb);
        }
        {
            JLabel lblIp = new JLabel("IP");
            lblIp.setBounds(12, 70, 70, 15);
            contentPanel.add(lblIp);
        }
        {
            ipTxt = new JTextField();
            ipTxt.setBounds(94, 70, 272, 25);
            contentPanel.add(ipTxt);
            ipTxt.setColumns(10);
        }

        allIpChk = new JCheckBox("修改所有相同IP");
        allIpChk.setBounds(94, 129, 272, 23);
        contentPanel.add(allIpChk);
        {
            JLabel lblip = new JLabel("原IP");
            lblip.setBounds(12, 43, 70, 15);
            contentPanel.add(lblip);
        }
        {
            oldIpLeb = new JLabel("");
            oldIpLeb.setBounds(94, 43, 272, 19);
            contentPanel.add(oldIpLeb);
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
                        if (StringUtils.isEmpty(ipTxt.getText())) {
                            JOptionPane.showMessageDialog(null, "请输入Ip");
                            return;
                        }
                        if (StringUtils.equals(ipTxt.getText(), oldIp)) {
                            JOptionPane.showMessageDialog(null, "不能和原IP相同");
                            return;
                        }
                        ok = true;
                        HostEditDialog.this.dispose();
                    }
                });
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("取消");
                cancelButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        HostEditDialog.this.dispose();
                    }
                });
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
    }

    public boolean isOk() {
        return ok;
    }

    public boolean isEditSameIp() {
        return allIpChk.isSelected();
    }

    public String getDomain() {
        return domainLeb.getText();
    }

    public void setDomain(String domain) {
        this.domainLeb.setText(domain);
    }

    public String getOldIp() {
        return oldIp;
    }

    public void setOldIp(String oldIp) {
        this.oldIp = oldIp;
        ipTxt.setText(oldIp);
        oldIpLeb.setText(oldIp);
    }

    public String getNewIp() {
        return ipTxt.getText();
    }
}
