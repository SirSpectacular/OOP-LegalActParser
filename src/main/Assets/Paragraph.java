package Assets;

public class Paragraph extends ActComponent {

    public Paragraph(String Id, String body){
        super(Id, body);
    }

    public Hierarchy getType() { return Hierarchy.Paragraph; }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(Id);
        sb.append(". ");
        sb.append(body);
        return sb.toString();
    }
}