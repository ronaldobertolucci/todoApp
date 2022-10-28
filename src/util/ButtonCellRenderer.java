package util;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ButtonCellRenderer extends DefaultTableCellRenderer {
    private String buttonType;

    public ButtonCellRenderer(String buttonType) {
        this.buttonType = buttonType;
    }

    public String getButtonType() {
        return buttonType;
    }

    public void setButtonType(String buttonType) {
        this.buttonType = buttonType;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
        label.setHorizontalAlignment(CENTER);
        label.setIcon(new ImageIcon(getClass().getResource("/main/resources/" + buttonType + ".png")));
        return label;
    }
}
