package Assets;

public class Point extends ActComponent {

    public Point(String Id, String body){
        super(Id, body);
    }

    public Hierarchy getType() { return Hierarchy.Point; }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(Id);
        sb.append(") ");
        sb.append(body);
        return sb.toString();
    }
}
