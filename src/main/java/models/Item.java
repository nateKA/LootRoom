package models;

import XMLparsing.XMLDocument;
import XMLparsing.XMLElement;
import XMLparsing.XMLParser;

import java.util.ArrayList;
import java.util.List;

public class Item extends BaseModel{
    private int quantity=1;
    private String requires = "1+";
    private String guaranteed = null;
    private int limit=-1;
    private String name="Unnamed Item";
    private String description="A mysterious item.";
    private int cost = 1;
    private String DMnotes = null;


    public Item(XMLElement xml){
        super(xml);

        if(xml.getAttribute("DMnotes")!=null)
            setDMnotes(xml.getAttribute("DMnotes"));

        if(xml.getAttribute("quantity")!=null)
            setQuantity(Integer.parseInt(xml.getAttribute("quantity")));

        if(xml.getAttribute("requires")!=null)
            setRequires((xml.getAttribute("requires")));

        if(xml.getAttribute("guaranteed")!=null)
            setGuaranteed((xml.getAttribute("guaranteed")));

        if(xml.getAttribute("cost")!=null)
            setCost(Integer.parseInt(xml.getAttribute("cost")));

        if(xml.getAttribute("name")!=null)
            setName(xml.getAttribute("name"));

        if(xml.getAttribute("limit")!=null)
            setLimit(Integer.parseInt(xml.getAttribute("limit")));

        if(xml.getAttribute("description")!=null)
            setDescription(xml.getAttribute("description").replace("\\\\","\\"));
    }

    public static void main(String[] args){
        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parseXML("C:\\Users\\Nate\\IdeaProjects\\LootRoom\\src\\main\\resources\\VestigesLootSheet.xml");
        List<XMLElement> xmls = doc.collect("lootSheet.room.package.item");
        Item item = new Item(xmls.get(3));
        System.out.println(item.getName()+" - "+item.getDescription());
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRequires() {
        return requires;
    }

    public void setRequires(String requires) {
        this.requires = requires;
    }

    public String getGuaranteed() {
        return guaranteed;
    }

    public void setGuaranteed(String guaranteed) {
        this.guaranteed = guaranteed;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<BaseModel> getSubs(){
        return new ArrayList<>();
    }

    public String getDMnotes() {
        return DMnotes;
    }

    public void setDMnotes(String DMnotes) {
        this.DMnotes = DMnotes;
    }
}
