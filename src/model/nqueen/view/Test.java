package model.nqueen.view;

import util.StoreResult;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Test extends JFrame{
    public Test()
    {
        StoreResult result=new StoreResult();
        ArrayList data=result.getResult();
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setSize(700,750);
        Dimension dim= Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2,dim.height/2-this.getSize().height/2);

        GridLayout grid=new GridLayout(5,3,10,10);
        this.setLayout(grid);

        JTextArea[] arr=new JTextArea[data.size()];

        for(int i=0;i<arr.length;i++)
        {
            arr[i]=new JTextArea(data.get(i).toString());
            arr[i].setSize(30,30);
            arr[i].setFont(new Font("Verdana",Font.PLAIN, 16));
            this.add(arr[i]);
        }

        this.setVisible(true);

    }
}
