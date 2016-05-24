
import java.util.ArrayList;

public class StateAndObserverDemo implements NotificationObserver {
    private static CarpoolManagement cm;
    public static void main(String[] args) {
        cm = new CarpoolManagement();
        //case 1
        cm.accept();
        cm.track();
        
        //case 2
        cm.accept();
        cm.decline();
    }

    @Override
    public void update(Alert a) {
        System.out.println(a.getAlert());
    }

}

class CarpoolManagement{
    CarpoolState state;
    public CarpoolManagement(){
        state = new PendingState(this);
    }
    
    public void accept(){
        System.out.println(state.accept());
    }
    
    public void decline(){
        System.out.println(state.decline());
    }
    
    public void track(){
        System.out.println(state.track());
    }
    
    public void setState(CarpoolState s){
        state = s;
    }
    
    public CarpoolState getState(){
        return state;
    }
}

interface CarpoolState{
    public String accept();
    public String decline();
    public String track();
}

class PendingState implements CarpoolState{

    private final CarpoolManagement cm;
    public PendingState(CarpoolManagement cm){
        this.cm = cm;
    }
    
    @Override
    public String accept() {
        cm.setState(new ConfirmedState(cm));
        return "Carpool Ride Accepted!";
    }

    @Override
    public String decline() {
        cm.setState(new CancelledState(cm));
        return "Carpool Ride Declined";
    }
    
    @Override
    public String track(){
        return "Must Accept A Ride First";
    }
}

class ConfirmedState implements CarpoolState{
    private final CarpoolManagement cm;
    public ConfirmedState(CarpoolManagement cm){
        this.cm = cm;
    }
    
    @Override
    public String accept(){
        return "";
    }
    
    @Override
    public String decline(){
        cm.setState(new CancelledState(cm));
        return "Carpool Ride Is Already Declined";
    }
    
    @Override
    public String track(){
        cm.setState(new TrackingState(cm));
        return "Carpool Ride Is Already Accepted and Being Tracked";
    }
}

class CancelledState implements CarpoolState{
    private final CarpoolManagement cm;
    
    public CancelledState(CarpoolManagement cm){
        this.cm = cm;
    }
    
    
    @Override
    public String accept() {
        cm.setState(new PendingState(cm));
        return "Carpool Ride Is Already Declined. New Ride Pending.";
    }

    @Override
    public String decline() {
        return "Carpool Ride IS Cancelled";
    }

    @Override
    public String track() {
        return "";
    }
}

class TrackingState implements CarpoolState{
    private final CarpoolManagement cm;
    public TrackingState(CarpoolManagement cm){
        this.cm = cm;
    }
    
    @Override
    public String accept() {
        cm.setState(new PendingState(cm));
        return "Carpool Ride Was Tracked.";
    }

    @Override
    public String decline() {
        return "";
    }

    @Override
    public String track() {
        return "Carpool Ride Is Being Tracked";
    }
}

interface NotificationObserver{
    void update(Alert a);
}

abstract class Notification{
    private ArrayList<NotificationObserver> observers = new ArrayList<>();

    public void add(NotificationObserver no){
        observers.add(no);
    }

    public void notifyObservers(Alert a) {
        for (NotificationObserver o: observers) {
            o.update(a);
        }
    }
}

class Alert extends Notification{
    private String alert;
    
    public void setAlert(String a){
        alert = a;
        notifyObservers(this);
    }

    public String getAlert(){
        return alert;
    }
}

