package com.raddle.swing.hosts.switcher.pane;

import javax.swing.table.DefaultTableModel;


public class HostTableModel extends DefaultTableModel {

    private static final long serialVersionUID = 1L;

    @Override
    public boolean isCellEditable(int row, int column) {
        if(column == 2){
            return true;
        }
        return false;
    }

}
