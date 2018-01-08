package Assets;

import com.beust.jcommander.ParameterException;

import java.util.ArrayList;
import java.util.List;

public class    Article extends ActComponent {

    public Article(String Id, String body) {
        super(Id, body);
    }

    public Hierarchy getType() {
        return Hierarchy.Article;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Art. ");
        sb.append(Id);
        sb.append('.');
        if (!body.equals("")) {
            sb.append('\n');
            sb.append(body);
        }
        return sb.toString();
    }
}




