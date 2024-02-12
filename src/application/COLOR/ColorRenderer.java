package application.COLOR;

import java.awt.Color;
import java.awt.Component;
import java.util.Arrays;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ColorRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;
    private final Color backgroundColor;
    private final Color foregroundColor;
    private final int[] targetColumns;

    public ColorRenderer(Color backgroundColor, Color foregroundColor, int... targetColumns) {
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
        this.targetColumns = targetColumns;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Check if the cell is in the specified columns
        if (Arrays.stream(targetColumns).anyMatch(targetColumn -> targetColumn == column)) {
            cellComponent.setBackground(backgroundColor);
            cellComponent.setForeground(foregroundColor);
        } else {
            // Reset background and foreground colors for other columns
            cellComponent.setBackground(table.getBackground());
            cellComponent.setForeground(table.getForeground());
        }

        return cellComponent;
    }
}
