package models;

import XMLparsing.XMLDocument;
import XMLparsing.XMLElement;
import XMLparsing.XMLParser;
import com.sun.org.apache.xpath.internal.operations.Mod;
import ui.ModelFrame;

import java.util.List;

public abstract class BaseModel {
    protected String name = "Room";
    protected XMLElement xml;

    public abstract List<BaseModel> getSubs();

    public BaseModel(){}

    public BaseModel(XMLElement xml){
        this.xml = xml;
        if(xml.getAttribute("name")!=null)
            setName(xml.getAttribute("name"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription(){
        return xml.getTag()+"("+xml.getAttribute("name")+")";
    }

    public String getTag(){
        return xml.getTag();
    }
    public BaseModel getChild(String name){
        for(BaseModel bm: getSubs()){
            if(bm.getName().equalsIgnoreCase(name))
                return bm;
        }
        return null;
    }

    public boolean equals(BaseModel bm){
        if(bm == null)return false;
        if(bm.getTag().equalsIgnoreCase("item")){
            return bm.getName().equalsIgnoreCase(getName()) && bm.getDescription().equalsIgnoreCase(this.getDescription());
        }else{
            return bm.getName().equalsIgnoreCase(getName());
        }
    }

    public String getContentString(){
        String str = getName();
        String subStr = "";
        for(BaseModel bm: getSubs()){
            str+= ("\n\t"+bm.getContentString().replace("\n","\n\t"));
        }
        subStr = subStr.replace("\n","\n\t");
        return str + subStr;
    }

    public static void main(String[] args){
        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parseXML("C:\\Users\\Nate\\IdeaProjects\\LootRoom\\src\\main\\resources\\VestigesLootSheet.xml");
        XMLElement xmls = doc.getRoot();

        BaseModel ls = new LootSheet(xmls);
        ModelFrame mf = new ModelFrame(ls);
        mf.open(ls);
        ModelFrame.currentModel = ls;
    }

}
