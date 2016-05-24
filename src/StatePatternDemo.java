import java.util.*;

public class StatePatternDemo {
    
    public static void main(String[] args) {
        Admission admission;
        admission = new Admission();
        Scanner input = new Scanner(System.in);
        double gpa;
        System.out.print("Enter your GPA: ");
        gpa = input.nextDouble();
        admission.receiveApplication();
        admission.qualifyApplicant(gpa);
        admission.admitStudent();
        
    }

}

//interface AdmissionInterface{
//    public static final double GPA = 3.0;
//    
//    public void receiveApplication();
//    public void qualifyApplicant(double g);
//    public void admitStudent();
//    
//    public State getState();
//    public void setState(State s);
//}

class Admission{
    State state;
    public static final double GPA = 3.0;
    public Admission(){
        state = new WaitingState(this);
    }
    
    
    public void receiveApplication(){
        System.out.println(state.receiveApplication());
    }

    
    public void qualifyApplicant(double g) {
        System.out.println(state.qualifyApplicant(g));
    }

    
    public void admitStudent() {
        System.out.println(state.admitStudent());
    }

    
    public State getState() {
        return state;
    }

    public void setState(State s) {
        state = s;
    }
}

interface State{
    public String receiveApplication();
    public String qualifyApplicant(double g);
    public String admitStudent();
}

class WaitingState implements State{
    private final Admission admission;

    public WaitingState(Admission a){
        admission = a;
    }
    
    @Override
    public String receiveApplication() {
        admission.setState(new EvaluatingState(admission));
        return "Received an application";
    }

    @Override
    public String qualifyApplicant(double g) {
        return "Must receive an application first";
    }

    @Override
    public String admitStudent() {
        return "Must receive an application first";
    }
    
}

class EvaluatingState implements State{
    private final Admission admission;
    
    public EvaluatingState(Admission a){
        admission = a;
    }

    @Override
    public String receiveApplication() {
        return "Already received an application";
    }

    @Override
    public String qualifyApplicant(double g) {
        if(g >= Admission.GPA){
            admission.setState(new ProcessingState(admission));
            return "The application is accepted";
        }
        else{
            admission.setState(new WaitingState(admission));
            return "The application is denied";
        }
    }

    @Override
    public String admitStudent() {
        return "Must be evaluated first";
    }
    
    
}

class ProcessingState implements State{
    private final Admission admission;
    
    public ProcessingState(Admission a){
        admission = a;
    }

    @Override
    public String receiveApplication() {
        return "The application is finalized";
    }

    @Override
    public String qualifyApplicant(double g) {
        return "The application is finalized";
    }

    @Override
    public String admitStudent() {
        admission.setState(new WaitingState(admission));
        return "The application is finalized and an admission letter is in the mail";
    }
    
    
}