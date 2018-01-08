package Assets;

public class Title extends ActComponent {
    public Hierarchy getType() { return Hierarchy.Title; }

    public Title(String Id){
        super(Id, "");
    }
}
