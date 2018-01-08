package Assets;
import java.util.ArrayList;
import java.util.List;



public abstract class ActComponent extends ParentOfComponents {
    protected String Id;
    protected String body;

    public String getId() {
        return Id;
    }
    public int getWeight() { return getType().ordinal(); }

    public abstract Hierarchy getType();

    public void extendBody(String string) {
        if(body.isEmpty())
            body = string;
        else
            body += " " + string;
    }

    public String toString(){
        return body;
    }

    public ActComponent(String Id, String body){
        this.body = body;
        this.Id = Id;
    }
}
