
import java.util.ArrayList;
import java.util.Iterator;

public class ObserverDemo {
    public static void main(String[] args) {
        Investor t = new Investor("Tung");
        Investor l = new Investor("Lance");
        
        IBM ibm = new IBM("IBM", 120.00);
        
        ibm.addObserver(t);
        ibm.addObserver(l);
        
        ibm.setPrice(120.10);
        ibm.setPrice(121.00);
        ibm.setPrice(120.50);
        ibm.setPrice(120.75);
        
        ibm.setSymbol("IBMTEST");
        
    }

}

abstract class Stock{
    protected String symbol;
    protected double price;
    
    private ArrayList investors = new ArrayList();
    
    public Stock(){}
    
    public void addObserver(IInvestor iinvestor){
        investors.add(iinvestor);
    }
    
    public void removeObserver(IInvestor iinvestor){
        investors.remove(iinvestor);
    }
    
    public void notifyObservers(Object args){
        Iterator i = investors.iterator();
        
        while(i.hasNext()){
            IInvestor investor = (IInvestor)i.next();
            investor.update(this, args);
        }
    }
    
    public String getSymbol(){
        return symbol;
    }
    
    public Double getPrice(){
        return price;
    }
}

class IBM extends Stock{
    private String _symbol;
    private Double _price;
    
    public IBM(String symbol, Double price){
        _symbol = symbol;
        _price = price;
    }
    
    @Override
    public Double getPrice(){
        return _price;
    }
    
    @Override
    public String getSymbol(){
        return _symbol;
    }
    
    public void setPrice(Double price){
        _price = price;
        notifyObservers(price);
    }
    
    public void setSymbol(String symbol){
        _symbol = symbol;
        notifyObservers(symbol);
    }
}

interface IInvestor{
    void update(Stock stock, Object args);
}

class Investor implements IInvestor{
    private String name;
    private String observerState;
    private Stock stock;
    
    public Investor(String name){
        this.name = name;
    }
    
    @Override
    public void update(Stock stock, Object args) {
        System.out.println("Notified Observer " + name);
        
        if(args instanceof String){
            System.out.println("The symbol of " + stock.getSymbol() + " changed to " + args);
        }
        else if (args instanceof Double){
            System.out.println("The price of " + stock.getSymbol() + " changed to " + args);
        }
    }
    
}