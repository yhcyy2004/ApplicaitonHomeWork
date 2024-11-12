import javax.swing.*;

public abstract class WorkEnvironment extends JFrame  {
    protected String environmentName;
    public WorkEnvironment(String environmentName){
        this.environmentName = environmentName;
    }
    public abstract void enterEnvironment();
}

