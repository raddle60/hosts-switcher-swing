package com.raddle.swing.hosts.switcher;

import java.awt.Rectangle;

import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class HostPane extends JDesktopPane {

    private static final long serialVersionUID = 1L;
    private JLabel jLabel = null;
    private JLabel curEnvLeb = null;
    private JLabel jLabel1 = null;
    private JComboBox inheritFromComb = null;
    private JScrollPane jScrollPane1 = null;
    private JTable jTable = null;

    /**
     * This is the default constructor
     */
    public HostPane() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     *
     * @return void
     */
    private void initialize() {
        this.setSize(452, 252);
        jLabel1 = new JLabel();
        jLabel1.setBounds(new Rectangle(195, 15, 61, 16));
        jLabel1.setText("继承自");
        curEnvLeb = new JLabel();
        curEnvLeb.setBounds(new Rectangle(105, 15, 76, 16));
        curEnvLeb.setText("");
        jLabel = new JLabel();
        jLabel.setBounds(new Rectangle(15, 15, 76, 16));
        jLabel.setText("当前环境");
        this.setBounds(new Rectangle(227, 99, 10, 10));
        this.add(jLabel, null);
        this.add(curEnvLeb, null);
        this.add(jLabel1, null);
        this.add(getInheritFromComb(), null);
//        this.add(getJScrollPane(), null);
        this.add(getJScrollPane1(), null);
    }

    /**
     * This method initializes inheritFromComb
     *
     * @return javax.swing.JComboBox
     */
    private JComboBox getInheritFromComb() {
        if (inheritFromComb == null) {
            inheritFromComb = new JComboBox();
            inheritFromComb.setBounds(new Rectangle(270, 15, 106, 16));
        }
        return inheritFromComb;
    }

    /**
     * This method initializes jScrollPane1
     *
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane1() {
        if (jScrollPane1 == null) {
            jScrollPane1 = new JScrollPane();
            jScrollPane1.setBounds(new Rectangle(15, 45, 421, 166));
            jScrollPane1.setViewportView(getJTable());
        }
        return jScrollPane1;
    }

    /**
     * This method initializes jTable
     *
     * @return javax.swing.JTable
     */
    private JTable getJTable() {
        if (jTable == null) {
            jTable = new JTable();
        }
        return jTable;
    }

}  //  @jve:decl-index=0:visual-constraint="10,10"
