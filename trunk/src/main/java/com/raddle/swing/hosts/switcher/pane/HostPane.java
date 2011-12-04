package com.raddle.swing.hosts.switcher.pane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.raddle.swing.hosts.switcher.utils.FileSelectUtils;

public class HostPane extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField        curEnvTxt;
    private JTable            table;
    private JTextField        importTxt;

    /**
     * Create the panel.
     */
    public HostPane(){
        setLayout(null);

        JLabel label = new JLabel("当前环境");
        label.setBounds(12, 12, 60, 15);
        add(label);

        curEnvTxt = new JTextField();
        curEnvTxt.setBounds(110, 8, 114, 24);
        add(curEnvTxt);
        curEnvTxt.setColumns(10);

        JLabel label_1 = new JLabel("继承自");
        label_1.setBounds(230, 12, 60, 15);
        add(label_1);

        JComboBox inheritComb = new JComboBox();
        inheritComb.setBounds(297, 7, 108, 24);
        add(inheritComb);

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
                    importTxt.setText(selectFile.getAbsolutePath());
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
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addColumn("域名");
        model.addColumn("继承");
        model.addColumn("覆盖");
        model.addColumn("最终");

    }

}
