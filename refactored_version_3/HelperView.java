import java.awt.*;
import javax.swing.*;

public class HelperView {

    public static void setWindowAlignment(JFrame win) {
        Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
        win.setLocation(
                ((screenSize.width) / 2) - ((win.getSize().width) / 2),
                ((screenSize.height) / 2) - ((win.getSize().height) / 2));
        win.show();
    }

    public static JButton makNewTab(String name,JPanel controlname) {
        JButton button = new JButton(name);
        JPanel panelName = new JPanel();
        panelName.setLayout(new FlowLayout());
        //button.addActionListener(this);
        panelName.add(button);
        controlname.add(panelName);
        return button;
    }
    public static JTextField makeNewPatronPanel(String s,JPanel patronPanel) {
        JPanel tempObject= new JPanel();
        tempObject.setLayout(new FlowLayout());
        JLabel temp = new JLabel(s);
        tempObject.add(temp);
        int x=15;
        JTextField value = new JTextField("",x);
        tempObject.add(value);
        patronPanel.add(tempObject);
        return value;

    }


}
