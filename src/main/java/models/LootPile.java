package models;

import XMLparsing.XMLDocument;
import XMLparsing.XMLElement;
import XMLparsing.XMLParser;
import utils.Utilities;

import java.util.*;

public class LootPile  extends BaseModel{
    private List<Item> guaranteedItems = new ArrayList<>();
    private List<Item> lootPile = new ArrayList<>();
    private String shareString = "1+=1";


    public LootPile(XMLElement xml){
        super(xml);

        for(XMLElement item: xml.getSubElementsByTag("item")){
            Item i = new Item(item);
            for(int j= 0; j < i.getQuantity(); j++) {
                if (i.getGuaranteed() == null) {
                    lootPile.add(i);
                } else {
                    guaranteedItems.add(i);
                }
            }
        }

        for(XMLElement item: xml.getSubElementsByTag("Bundle")){
            //TODO: create item bundle
        }

        if(xml.getAttribute("shares")!=null)
            setShareString(xml.getAttribute("shares"));

        lootPile.sort(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o2.getCost()-o1.getCost();
            }
        });

        guaranteedItems.sort(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.getGuaranteed().compareTo( o2.getGuaranteed());
            }
        });
    }

    public static void main(String[] args){
        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parseXML("C:\\Users\\Nate\\IdeaProjects\\LootRoom\\src\\main\\resources\\VestigesLootSheet.xml");
        List<XMLElement> xmls = doc.collect("lootSheet.dungeon.room.package","item");
        LootPile charlieII = new LootPile(xmls.get(0));
        LootPile charlieIII = new LootPile(xmls.get(1));
        LootPile Rucksack = new LootPile(xmls.get(2));

        String[] names = new String[]{"John","RJ","Justin","Dan","Hailey","Will"};
        Random r = new Random();
        for(int i = 0; i < 2; i++){
            for(String n: names){
                int roll = r.nextInt(19)+8;
                LootPile pile = null;
                switch (r.nextInt(3)){
                    case 0: pile = charlieII;break;
                    case 1: pile = charlieIII;break;
                    case 2: pile = Rucksack;break;
                }
                System.out.println(n+"-"+pile.getName()+"("+roll+")");
                ShoppingCart sc = new ShoppingCart(pile);
                sc.shop(roll);
                for(Item item: sc.getCart()){
                    System.out.println("\t"+item.getName()+" - "+item.getDescription());
                }
                System.out.println("\n");
            }
        }
    }

    public List<BaseModel> getSubs(){
        List<BaseModel> list = new ArrayList<>();
        list.addAll(guaranteedItems);
        list.addAll(lootPile);
        return list;
    }

    public int getRollShares(int roll){
        return Utilities.getShares(shareString,roll);
    }

    public String getShareString() {
        return shareString;
    }

    public void setShareString(String shareString) {
        this.shareString = shareString;
    }



    public List<Item> getGuaranteedItems() {
        return guaranteedItems;
    }

    public void setGuaranteedItems(List<Item> guaranteedItems) {
        this.guaranteedItems = guaranteedItems;
    }

    public List<Item> getLootPile() {
        return lootPile;
    }

    public void setLootPile(List<Item> lootPile) {
        this.lootPile = lootPile;
    }
}
