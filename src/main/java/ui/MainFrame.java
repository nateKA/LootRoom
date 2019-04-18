package ui;

import XMLparsing.XMLDocument;
import XMLparsing.XMLParser;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    XMLDocument doc;


    public MainFrame(String xmlPath){
        XMLParser parser = new XMLParser();
        doc = parser.parseXML(xmlPath);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 360, 500);

        DungeonFrame df = new DungeonFrame(doc);
        df.setParent(this);
        setContentPane(df);
        df.build();
    }

    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame mf = new MainFrame("C:\\Users\\Nate\\IdeaProjects\\LootRoom\\src\\main\\resources\\VestigesLootSheet.xml");
                    mf.setVisible(true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void doYaThing(){
        removeAll();
    }
}
