package Assets;

import com.beust.jcommander.ParameterException;

import java.util.ArrayList;
import java.util.List;

public abstract class ParentOfComponents {
    public ArrayList<ActComponent> subComponents = new ArrayList<>();

    public ParentOfComponents findComponent(List<String> pathOfIDs, List<Hierarchy> pathOfTypes){
        if(pathOfIDs.isEmpty()){
            return this;
        }
        for(ActComponent actComponent : this.subComponents){
            if(actComponent.getType() == pathOfTypes.get(0) && actComponent.getId().equals(pathOfIDs.get(0))){
                return actComponent.findComponent(pathOfIDs.subList(1,pathOfIDs.size()), pathOfTypes.subList(1,pathOfIDs.size()));
            }
        }
        throw new NullPointerException("Couldn't find given component");
    }

    public Article findArticle(String Id) {
        if (this instanceof Article) {
            if (((Article) this).Id.equals(Id))
                return (Article) this;
            return null;
        }
        Article article = null;
        for (ActComponent actComponent : this.subComponents) {
            if (article == null) {
                article = actComponent.findArticle(Id);
            }
        }
        return article;
    }

    public List<Article> findRangeOfArticles(Article start, Article end){
        List<Article> list = new ArrayList<>();
        list = this.findRangeOfArticles(start, end, list);
        if(list.contains(start) && list.contains(end)) {
            return list;
        }
        else
            throw new ParameterException("Unexisting range of articles");
    }
    private List<Article> findRangeOfArticles(Article start, Article end, List<Article> list){
        for(ParentOfComponents component : this.subComponents){
            if(component instanceof Article) {
                if (component == start || (list.contains(start) && !list.contains(end)))
                    list.add((Article) component);
            }
            else {
                list = component.findRangeOfArticles(start, end, list);
            }
        }
        return list;
    }
}

