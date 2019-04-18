package models;

import XMLparsing.XMLElement;

import java.util.ArrayList;
import java.util.List;

public class Room extends BaseModel{
    private List<LootPile> lootPiles = new ArrayList<>();

    public Room(XMLElement xml){
        super(xml);

        for(XMLElement x: xml.getSubElements()){
            LootPile lp = new LootPile(x);
            lootPiles.add(lp);
        }
    }

    @Override
    public List<BaseModel> getSubs() {
        return new ArrayList<>(lootPiles);
    }
}
