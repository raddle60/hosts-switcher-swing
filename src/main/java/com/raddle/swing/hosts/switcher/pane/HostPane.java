package com.raddle.swing.hosts.switcher.pane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringWriter;
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
                            refreshTable();
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
        importBtn.setBounds(417, 34, 72, 25);
        add(importBtn);

        JButton previewBtn = new JButton("预览");
        previewBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    StringWriter writer = new StringWriter();
                    hostsManager.writeHosts(hosts, writer);
                    PreviewDialog preview = new PreviewDialog();
                    preview.setLocationRelativeTo(HostPane.this);
                    preview.setTitle("预览 - " + envTxt.getText());
                    preview.getTextPane().setText(writer.getBuffer().toString());
                    preview.setModal(true);
                    preview.setVisible(true);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "预览失败," + e1.getMessage());
                }
            }
        });
        previewBtn.setBounds(12, 69, 60, 25);
        add(previewBtn);

        JButton exportBtn = new JButton("导出");
        exportBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (StringUtils.isBlank(hosts.getEnv())) {
                    JOptionPane.showMessageDialog(null, "环境名称不能为空");
                    return;
                }
                File saveFile = FileSelectUtils.selectSaveFile(importTxt.getText(), null);
                if (saveFile.getParentFile().exists()) {
                    try {
                        hostsManager.writeHosts(hosts, new FileWriter(saveFile));
                        JOptionPane.showMessageDialog(null, "导出成功\n" + saveFile.getAbsolutePath());
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, "导出失败," + e1.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "目录:" + saveFile.getParentFile().getAbsoluteFile() + ", 不存在");
                }
            }
        });
        exportBtn.setBounds(503, 34, 72, 25);
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
        saveBtn.setBounds(84, 69, 60, 25);
        add(saveBtn);

        JButton addBtn = new JButton("添加");
        addBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                HostInputDialog input = new HostInputDialog();
                input.setModal(true);
                input.setLocationRelativeTo(HostPane.this);
                input.setVisible(true);
                if (input.isOk()) {
                    if (hosts.getHost(input.getDomain()) != null) {
                        JOptionPane.showMessageDialog(null, "域名" + input.getDomain() + "已存在");
                        return;
                    }
                    hosts.setHost(new Host(input.getIp(), input.getDomain()));
                    refreshTable();
                }
            }
        });
        addBtn.setBounds(159, 69, 65, 25);
        add(addBtn);

        JButton editBtn = new JButton("编辑");
        editBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() != -1) {
                    HostWrapper wrapper = (HostWrapper) table.getValueAt(table.getSelectedRow(), 0);
                    HostEditDialog edit = new HostEditDialog();
                    edit.setDomain(wrapper.getDomain());
                    edit.setOldIp(wrapper.getIp());
                    edit.setModal(true);
                    edit.setLocationRelativeTo(HostPane.this);
                    edit.setVisible(true);
                    if (edit.isOk()) {
                        hosts.getHost(edit.getDomain()).setIp(edit.getNewIp());
                        if (edit.isEditSameIp()) {
                            // 修改所有相同的ip
                            for (Host host : hosts.getHostList()) {
                                if (StringUtils.equals(edit.getOldIp(), host.getIp())) {
                                    host.setIp(edit.getNewIp());
                                }
                            }
                        }
                        refreshTable();
                    }
                }
            }
        });
        editBtn.setBounds(236, 69, 60, 25);
        add(editBtn);

        JButton delBtn = new JButton("删除");
        delBtn.setBounds(308, 69, 60, 25);
        add(delBtn);

        JButton deactiveBtn = new JButton("禁用");
        deactiveBtn.setBounds(382, 69, 60, 25);
        add(deactiveBtn);

        JButton activeBtn = new JButton("启用");
        activeBtn.setBounds(458, 69, 60, 25);
        add(activeBtn);
    }

    private void refreshTable() {
        // 填入jtable
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        // 清除以前的
        model.getDataVector().removeAllElements();
        // 加入新的
        for (Host host : hosts.getHostList()) {
            HostWrapper wrapper = new HostWrapper(hostsManager, hosts, host);
            model.addRow(new Object[] { wrapper, wrapper.getInheritIp(), wrapper.getIp(), wrapper.getFinalIp() });
        }
    }
}
