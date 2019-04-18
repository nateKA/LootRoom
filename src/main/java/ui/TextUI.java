package ui;

import XMLparsing.XMLDocument;
import XMLparsing.XMLElement;
import XMLparsing.XMLParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUI {
    private Stack<XMLElement> history = new Stack<>();
    private XMLElement currentPage = null;

    public void forward(XMLElement nextPage){
        currentPage = nextPage;
        history.push(nextPage);
    }

    public void backward(){
        currentPage = history.pop();
    }

    public void execute(String command){
        if(command.matches("ls")){

            printSubs();
        }else if(command.matches("open\\s+.*")){
            Matcher m = Pattern.compile("open\\s+(.*)").matcher(command);
            if(m.find()){
                MatchResult res = m.toMatchResult();
                open(res.group(1));
            }
        }else {

            print("Unrecognized Command - "+command);
        }
    }

    private void open(String next){
        for(XMLElement xml: currentPage.getSubElements()){
            if(next.equalsIgnoreCase(xml.getAttribute("name"))){
                forward(xml);
                print("Opened "+next);
                return;
            }
        }

        print("Could not find page - "+next);
    }

    private void printSubs(){
        String str = "@ - "+currentPage.getAttribute("name")+"\n";
        for(XMLElement xml: currentPage.getSubElements()){
            str += "\t"+xml.getAttribute("name")+"\n";
        }
        print(str);
    }

    private void print(String str){
        try {
            System.out.println((""+str));
            System.out.println("---------------\n\n");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        XMLParser parser = new XMLParser();
        XMLDocument doc = parser.parseXML("C:\\Users\\Nate\\IdeaProjects\\LootRoom\\src\\main\\resources\\VestigesLootSheet.xml");
        TextUI tui = new TextUI();
        tui.forward(doc.getRoot());

        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.print("exec>");
            String line = scanner.nextLine();
            if(line.equals("quit"))
                break;
            tui.execute(line);
        }

    }

}
