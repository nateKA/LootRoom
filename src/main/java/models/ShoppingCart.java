package models;

import utils.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShoppingCart extends BaseModel{
    private List<Item> cart = new ArrayList<>();
    private int budget = 1;
    private int roll = 1;
    private LootPile lootPile;

    public ShoppingCart(LootPile lp){

        lootPile = lp;
    }

    public List<BaseModel> getSubs(){
        return new ArrayList<>(cart);
    }

    public void shop(int roll){
        this.roll = roll;
        this.budget = lootPile.getRollShares(roll);

        List<Item> guaranteed = lootPile.getGuaranteedItems();
        List<Item> gBuffer = new ArrayList<>(guaranteed);
        while(gBuffer.size() > 0 && budget > 0){
            Item item = gBuffer.get(0);
            gBuffer.remove(item);
            if(Utilities.rollFitsDesc(item.getRequires(),roll) && Utilities.rollFitsDesc(item.getGuaranteed(),roll))
            purchaseItem(item,guaranteed);
        }

        List<Item> pile = lootPile.getLootPile();
        List<Item> bufferPile = new ArrayList<>(pile);

        for(int i = 0; i < bufferPile.size(); i++){
            if(bufferPile.get(i).getCost()>budget)
                bufferPile.remove(i);
        }
        Random r = new Random();
        while(budget >= 0 && bufferPile.size() > 0){
            double mod = (Math.max(1.0,bufferPile.get(0).getCost()-budget))/bufferPile.get(0).getCost();
            mod = Math.min(mod,1);
            int index = (int)((double)r.nextInt(bufferPile.size())*mod);
            Item item = bufferPile.get(index);
            bufferPile.remove(item);
            if(isAffordable(item)){
                purchaseItem(item,pile);
            }
        }
    }

    public void emptyCart(){
        cart = new ArrayList<>();
    }

    private boolean isAffordable(Item item){
        boolean affordable = item.getCost() <= budget;
        boolean meets = Utilities.rollFitsDesc(item.getRequires(),roll);
        return  affordable && meets;
    }

    private void purchaseItem(Item item, List<Item> pile){
        pile.remove(item);
        cart.add(item);
        budget -= item.getCost();
    }

    public List<Item> getCart() {
        return cart;
    }

    public void setCart(List<Item> cart) {
        this.cart = cart;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }
}
