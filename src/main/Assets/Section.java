package Assets;

public class Section extends ActComponent {

    public Section(String Id, String body) {
        super(Id, body);
    }

    public Hierarchy getType() { return Hierarchy.Section; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Dział ");
        sb.append(Id);
        sb.append(" - ");
        sb.append(body);
        return sb.toString();
    }
}
