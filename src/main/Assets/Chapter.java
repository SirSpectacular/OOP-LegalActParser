package Assets;

public class Chapter extends ActComponent {

    public Chapter(String Id, String title){
        super(Id, title);
    }

    public Hierarchy getType() { return Hierarchy.Chapter; }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Rozdział ");
        sb.append(Id);
        sb.append(" - ");
        sb.append(body);
        return sb.toString();
    }
}