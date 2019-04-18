package models;

import XMLparsing.XMLElement;

import java.util.ArrayList;
import java.util.List;

public class Dungeon extends BaseModel{
    private List<Room> rooms = new ArrayList<>();

    public Dungeon(XMLElement xml){
        super(xml);

        for(XMLElement x: xml.getSubElements()){
            Room lp = new Room(x);
            rooms.add(lp);
        }
    }

    @Override
    public List<BaseModel> getSubs() {
        return new ArrayList<>(rooms);
    }
}
