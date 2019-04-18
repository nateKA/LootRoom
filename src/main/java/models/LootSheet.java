package models;

import XMLparsing.XMLElement;

import java.util.ArrayList;
import java.util.List;

public class LootSheet extends BaseModel {
    private List<Dungeon> dungeons = new ArrayList<>();

    public LootSheet(XMLElement xml){
        super(xml);

        for(XMLElement x: xml.getSubElements()){
            Dungeon lp = new Dungeon(x);
            dungeons.add(lp);
        }
    }

    @Override
    public List<BaseModel> getSubs() {
        return new ArrayList<>(dungeons);
    }
}
