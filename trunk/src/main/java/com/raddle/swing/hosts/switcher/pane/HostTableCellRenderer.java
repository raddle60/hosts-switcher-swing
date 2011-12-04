package com.raddle.swing.hosts.switcher.pane;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class HostTableCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        HostWrapper wrapper = (HostWrapper) table.getValueAt(row, 0);
        if (wrapper.isActive()) {
            rendererComponent.setForeground(Color.BLUE);
        } else {
            rendererComponent.setForeground(Color.GRAY);
        }
        return rendererComponent;
    }

}
