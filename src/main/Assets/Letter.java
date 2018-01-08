package Assets;

public class Letter extends ActComponent {

    public Letter(String Id, String body){
        super(Id, body);
    }

    public Hierarchy getType() { return Hierarchy.Letter; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Id);
        sb.append(") ");
        sb.append(body);
        return sb.toString();
    }
}
