package com.raddle.swing.hosts.switcher.pane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.UUID;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang.StringUtils;

import com.raddle.swing.hosts.switcher.manager.HostsManager;
import com.raddle.swing.hosts.switcher.model.Host;
import com.raddle.swing.hosts.switcher.model.Hosts;
import com.raddle.swing.hosts.switcher.utils.FileSelectUtils;
import com.raddle.swing.layout.LayoutUtils;

public class HostPane extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField envTxt;
    private JTable table;
    private JTextField importTxt;
    private HostsManager hostsManager = new HostsManager();
    private Hosts hosts = new Hosts();

    /**
     * Create the panel.
     */
    public HostPane(){
        setLayout(null);

        JLabel label = new JLabel("环境名称");
        label.setBounds(12, 12, 60, 15);
        add(label);

        envTxt = new JTextField();
        envTxt.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent e) {
                hosts.setEnv(envTxt.getText());
            }
        });
        envTxt.setBounds(110, 8, 114, 24);
        add(envTxt);
        envTxt.setColumns(10);

        JLabel label_1 = new JLabel("继承自");
        label_1.setBounds(230, 12, 60, 15);
        add(label_1);

        JComboBox inheritComb = new JComboBox();
        inheritComb.setBounds(297, 7, 108, 24);
        add(inheritComb);
        // 初始化下拉框
        List<Hosts> allHosts = hostsManager.getAllHosts();
        DefaultComboBoxModel hostsModel = (DefaultComboBoxModel) inheritComb.getModel();
        hostsModel.removeAllElements();
        hostsModel.addElement(null);
        for (Hosts hosts : allHosts) {
            hostsModel.addElement(new HostsItem(hosts.getId(), hosts.getEnv()));
        }

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 106, 416, 249);
        add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        JLabel label_2 = new JLabel("从文件载入");
        label_2.setBounds(12, 39, 95, 15);
        add(label_2);

        importTxt = new JTextField();
        importTxt.setBounds(110, 34, 295, 25);
        add(importTxt);
        importTxt.setColumns(10);

        JButton importBtn = new JButton("导入");
        importBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                File selectFile = FileSelectUtils.selectFile(importTxt.getText(), null);
                if (selectFile != null) {
                    if (selectFile.exists()) {
                        importTxt.setText(selectFile.getAbsolutePath());
                        try {
                            Hosts importHosts = hostsManager.parseHosts(new FileReader(selectFile));
                            // 合并到已有的host
                            for (Host host : importHosts.getHostList()) {
                                hosts.setHost(host);
                            }
                            // 填入jtable
                            DefaultTableModel model = (DefaultTableModel) table.getModel();
                            // 清除以前的
                            model.getDataVector().removeAllElements();
                            // 加入新的
                            for (Host host : hosts.getHostList()) {
                                model.addRow(new Object[] { host.getDomain(), null, host.getIp(), host.getIp() });
                            }
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            JOptionPane.showMessageDialog(null, e1.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, selectFile.getAbsoluteFile() + ", 不存在");
                    }
                }

            }
        });
        importBtn.setBounds(417, 34, 117, 25);
        add(importBtn);

        JButton previewBtn = new JButton("预览");
        previewBtn.setBounds(12, 69, 117, 25);
        add(previewBtn);

        JButton exportBtn = new JButton("导出");
        exportBtn.setBounds(141, 69, 117, 25);
        add(exportBtn);
        table.getTableHeader().setReorderingAllowed(false);
        DefaultTableModel model = new HostTableModel();
        model.addColumn("域名");
        model.addColumn("继承");
        model.addColumn("覆盖");
        model.addColumn("最终");
        table.setModel(model);
        LayoutUtils.anchorFixedBorder(this, scrollPane).anchorRight(10).anchorBottom(10);

        JButton saveBtn = new JButton("保存");
        saveBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (StringUtils.isBlank(hosts.getEnv())) {
                    JOptionPane.showMessageDialog(null, "环境名称不能为空");
                    return;
                }
                if (StringUtils.isBlank(hosts.getId())) {
                    hosts.setId(UUID.randomUUID().toString());
                }
                try {
                    hostsManager.saveHosts(hosts);
                    JOptionPane.showMessageDialog(null, "保存成功");
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "保存失败," + e1.getMessage());
                }
            }
        });
        saveBtn.setBounds(270, 69, 117, 25);
        add(saveBtn);
    }
}
