package com.raddle.swing.hosts.switcher.pane;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.commons.lang.StringUtils;

public class HostTableCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(column == 0){
            ((JLabel)rendererComponent).setHorizontalAlignment(SwingConstants.RIGHT);
        } else {
            ((JLabel)rendererComponent).setHorizontalAlignment(SwingConstants.LEFT);
        }
        if (isSelected) {
            rendererComponent.setForeground(Color.WHITE);
        } else {
            HostWrapper wrapper = (HostWrapper) table.getValueAt(row, 0);
            if (wrapper.isExist()) {
                if (wrapper.isActive()) {
                    rendererComponent.setForeground(Color.BLACK);
                    if (!StringUtils.equals(wrapper.getIp(), wrapper.getInheritIp())) {
                        rendererComponent.setForeground(Color.BLUE);
                    }
                } else {
                    rendererComponent.setForeground(Color.RED);
                }
            } else {
                rendererComponent.setForeground(Color.GRAY);
            }
        }
        return rendererComponent;
    }

}
