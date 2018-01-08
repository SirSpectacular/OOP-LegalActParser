package Entities;

import Assets.Hierarchy;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.util.List;

public class OptionParser {
    OptionParser(String[] args){
        JCommander.newBuilder()
                .addObject(this)
                .build()
                .parse(args);
    }

    @Parameter(description = "Files", required = true)
    private String path;

    @Parameter(names = {"-m", "--mode"}, required = true)
    private String mode;

    @Parameter(names = {"-s", "--section"})
    private String section;

    @Parameter(names = {"-c", "--chapter"})
    private String chapter;

    @Parameter(names = {"-t", "--title"})
    private String title;

    @Parameter(names = {"-a", "--article"})
    private String article;

    @Parameter(names = {"-P", "--paragraph"})
    private String paragraph;

    @Parameter(names = {"-p", "--point"})
    private String point;

    @Parameter(names = {"-l", "--letter"})
    private String letter;

    @Parameter(names = {"-r", "--rangeOfArticles"}, arity = 2)
    private List<String> range;


    public String getSection() {
        return section;
    }

    public String getChapter() {
        return chapter;
    }

    public String getTitle() {
        return title;
    }

    public String getArticle() {
        return article;
    }

    public String getParagraph() {
        return paragraph;
    }

    public String getPoint() {
        return point;
    }

    public String getLetter() {
        return letter;
    }

    public List<String> getRange() {
        return range;
    }

    public String getMode() {
        return mode;
    }

    public String getPath() {
        return path;
    }
}
