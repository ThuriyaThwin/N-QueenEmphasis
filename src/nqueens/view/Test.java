package nqueens.view;

import util.StoreResult;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Test extends JFrame {
    public Test() {
        StoreResult result = new StoreResult();
        ArrayList data = result.getResult();
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setSize(900, 750);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        GridLayout grid = new GridLayout(4, 2, 10, 10);

        this.setLayout(grid);

        JTextArea[] arr = new JTextArea[data.size()];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = new JTextArea(data.get(i).toString());
            arr[i].setSize(dim.width / 2, 30);
            arr[i].setFont(new Font("Verdana", Font.PLAIN, 16));

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            arr[i].setSize(screenSize.width / 2, screenSize.height / 2);

            JScrollPane sp = new JScrollPane(arr[i]);
            this.add(sp);
        }

        this.setVisible(true);

    }
}
