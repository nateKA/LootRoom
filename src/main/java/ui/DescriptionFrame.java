package ui;

import models.Item;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DescriptionFrame extends JFrame{

    public DescriptionFrame(Item item){
        setTitle(item.getName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 600);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel nameTag = new JLabel(item.getName());
        nameTag.setFont(new Font("Tahoma",Font.BOLD, 20));
        nameTag.setBounds(10, 42, 320, 100);
        add(nameTag);

        JTextArea desc = new JTextArea("Description - "+item.getDescription());
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setFont(new Font("Tahoma",Font.BOLD, 14));
        desc.setBounds(10, 42, 320, 400);
        JScrollPane scrollPane2 = new JScrollPane(desc,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setBounds(10,130,320,100);
        add(scrollPane2);

        JTextArea notes = new JTextArea("DM notes - "+item.getDMnotes());
        notes.setLineWrap(true);
        notes.setWrapStyleWord(true);
        notes.setFont(new Font("Tahoma",Font.BOLD, 14));
        notes.setBounds(10, 42, 320, 400);
        JScrollPane scrollPane3 = new JScrollPane(notes,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane3.setBounds(10,250,320,100);
        add(scrollPane3);
    }
}
