package ui;

import models.BaseModel;
import models.Item;
import models.LootPile;
import models.ShoppingCart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class ModelFrame extends JFrame{

    public static BaseModel currentModel;
    public static Stack<BaseModel> history = new Stack<>();
    public boolean navigable = true;


    public void forward(BaseModel bm){
        history.push(currentModel);
        currentModel = bm;
        open(currentModel);
    }

    public void open(BaseModel bm){
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ModelFrame mf = new ModelFrame(bm);
                    mf.setVisible(true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        dispose();
    }

    public ModelFrame(BaseModel bm){
        this(bm,true);
    }
    public ModelFrame(BaseModel bm, boolean navigable){

        setTitle("Loot Room");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 600);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel dungeonLabel = new JLabel(bm.getName());
        dungeonLabel.setFont(new Font("Tahoma",Font.BOLD, 26));
        dungeonLabel.setBounds(10, 42, 500, 40);
        contentPane.add(dungeonLabel);

        JPanel dungeons = new JPanel();
        dungeons.setPreferredSize(new Dimension(20,bm.getSubs().size()*30+20));
        dungeons.setLayout(new GridLayout(bm.getSubs().size(),1));
        JScrollPane scrollPane = new JScrollPane(dungeons,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(10,90,320,350);
        add(scrollPane);

        List<BaseModel> list = bm.getSubs();
        list.sort(new Comparator<BaseModel>() {
            @Override
            public int compare(BaseModel o1, BaseModel o2) {
                if(o1.getTag().equalsIgnoreCase("Item"))
                    return ((Item)o2).getCost() - ((Item)o1).getCost();
                else
                    return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        BaseModel lastBM = null;
        JButton lastBtn = null;
        int count = 1;
        for(BaseModel b: list){
            if(!b.equals(lastBM)) {
                count = 1;
                JButton bmButton = new JButton(b.getName());
                bmButton.setPreferredSize(new Dimension(300, 25));
                dungeons.add(bmButton);
                if(b.getTag().equalsIgnoreCase("item")){
                    bmButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Item item = (Item)b;
//                            JOptionPane.showMessageDialog(getParent(),
//                                    "Name - "+item.getName()+"\n\n"+
//                                "Description - "+item.getDescription()+"\n\n"+
//                                "DM Notes - "+item.getDMnotes());
                            DescriptionFrame df = new DescriptionFrame(item);
                            df.setVisible(true);
                        }
                    });
                }else {
                    bmButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            forward(b);
                        }
                    });
                }
                lastBtn = bmButton;
            }else{
                lastBtn.setText(b.getName()+" x"+(++count));
            }
            lastBM = b;
        }


        if(history.size()>0 && navigable) {
            JButton backBttn = new JButton("Back");
            backBttn.setBounds(340, 90, 70, 30);
            contentPane.add(backBttn);
            backBttn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    previous();
                }
            });
        }

        if(bm.getSubs().size() > 0 && bm.getSubs().get(0).getTag().equalsIgnoreCase("item") && navigable) {
            JButton backBttn = new JButton("Loot");
            backBttn.setBounds(340, 320, 70, 30);
            contentPane.add(backBttn);
            backBttn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JTextField pInput = new JTextField(5);
                    JTextField rInput = new JTextField(5);

                    JPanel myPanel = new JPanel();
                    myPanel.add(new JLabel("Who is looting?"));
                    myPanel.add(pInput);
                    myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                    myPanel.add(new JLabel("What did they roll?"));
                    myPanel.add(rInput);

                    int result = JOptionPane.showConfirmDialog(null, myPanel,
                            "Please Answer", JOptionPane.OK_CANCEL_OPTION);

                    int roll = 0;
                    String name = "Somebody's Loot";

                    if (result == JOptionPane.OK_OPTION) {
                        name = pInput.getText();
                        roll = Integer.parseInt(rInput.getText());
                    }


                    ShoppingCart sc = new ShoppingCart(((LootPile)currentModel));
                    sc.setName(name);
                    sc.shop(roll);
                    open(currentModel);
                    ModelFrame mf = new ModelFrame(sc,false);
                    mf.setVisible(true);

                }
            });
        }
    }

    public void previous(){
        currentModel = history.pop();
        open(currentModel);
    }
}
