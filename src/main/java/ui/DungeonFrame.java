package ui;

import XMLparsing.XMLDocument;
import XMLparsing.XMLElement;
import sun.applet.Main;
import utils.Utilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;

public class DungeonFrame extends JPanel {

    private XMLDocument xmlDocument;
    private MainFrame parent;

    public DungeonFrame(XMLDocument xmlDoc){
        xmlDocument = xmlDoc;
    }

    public void setParent(MainFrame jf){
        parent = jf;
    }

    public void build(){
        setBorder(new EmptyBorder(5,5,5,5));
        setLayout(null);


        JLabel dungeonLabel = new JLabel("Dungeons");
        dungeonLabel.setFont(new Font("Tahoma",Font.BOLD, 26));
        dungeonLabel.setBounds(10, 42, 300, 40);
        add(dungeonLabel);

        JPanel dungeons = new JPanel();
        dungeons.setPreferredSize(new Dimension(20,1000));
        JScrollPane scrollPane = new JScrollPane(dungeons,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(10,90,300,350);
        add(scrollPane);


        List<XMLElement> dungeonList = xmlDocument.collect("lootSheet.dungeon","room");
        dungeonList.sort(new Comparator<XMLElement>() {
            @Override
            public int compare(XMLElement o1, XMLElement o2) {
                return o1.getAttribute("name").compareTo(o2.getAttribute("name"));
            }
        });
        for(XMLElement xml: dungeonList) {
            String name = Utilities.righPadding(xml.getAttribute("name"),50,' ');
            JButton backButton = new JButton(name);
            dungeons.add(backButton);
            backButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
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
            });
        }


    }

//    contentPane = new JPanel();
//        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//    setContentPane(contentPane);
//        contentPane.setLayout(null);
//    //setIconImage(new ImageIcon(GameUtil.sumoIcon).getImage());
//
//
//
//    JLabel lblNewLabel = new JLabel(Properties.APP_NAME);
//        lblNewLabel.setFont(new Font("Tahoma",Font.BOLD, 26));
//        lblNewLabel.setBounds(10, 42, 300, 40);
//        contentPane.add(lblNewLabel);
//
//    JLabel accLabel = new JLabel(account.getName()+" $"+account.getBalanceString());
//        accLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
//        accLabel.setBounds(10, 75, 300, 40);
//        contentPane.add(accLabel);
//
//    //Labels for categories
//    int x = 10, y = 110, diff = 28;
//        for(Category c: account.getCategories()){
//        JLabel lblEnterYourName = new JLabel(c.getName());
//        lblEnterYourName.setFont(new Font("Tahoma", Font.PLAIN, 14));
//        lblEnterYourName.setBounds(x+99, y, 117, 17);
//        contentPane.add(lblEnterYourName);
//
//        JButton backButton = new JButton("$"+c.getBalanceString());
//        backButton.setBounds(x, y, 89, 23);
//        contentPane.add(backButton);
//        //when the button is pressed, send the name to the client application and connect to the server
//        backButton.addActionListener(new ActionListener()
//        {
//            @Override
//            public void actionPerformed(ActionEvent e)
//            {
//                Printer p = new Printer();
//                JOptionPane.showMessageDialog(app,
//                        p.printCategory(c),
//                        account.getName(),
//                        JOptionPane.PLAIN_MESSAGE);
//            }
//        });
//
//        y+= diff;
//    }
}
