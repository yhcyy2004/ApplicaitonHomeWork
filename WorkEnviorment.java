import javax.swing.*;

public abstract class WorkEnviorment extends JFrame  {
    protected String environmentName;
    public WorkEnviorment(String environmentName){
        this.environmentName = environmentName;
    }
    public abstract void enterEnvironment();
}

