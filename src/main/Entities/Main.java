package Entities;

import Assets.*;
import com.beust.jcommander.ParameterException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            OptionParser arguments = new OptionParser(args);

            ActParser parser = new ActParser(
                    Files.readAllLines(
                            new File(arguments.getPath()).toPath(),
                            Charset.forName("windows-1250")));


            Act act = parser.listToTree();

            List<String> IDs = new ArrayList<>();
            List<Hierarchy> Types = new ArrayList<>();

            switch(arguments.getMode()){
                case "show": {
                    if (arguments.getRange() != null &&
                            arguments.getSection() == null &&
                            arguments.getChapter() == null &&
                            arguments.getTitle() == null &&
                            arguments.getArticle() == null &&
                            arguments.getParagraph() == null &&
                            arguments.getPoint() == null &&
                            arguments.getLetter() == null) {

                        List<Article> articlesToPrint = act.findRangeOfArticles(
                                act.findArticle(arguments.getRange().get(0)),
                                act.findArticle(arguments.getRange().get(1)));

                        for (Article articleToPrint : articlesToPrint) {
                            new Viewer(articleToPrint).printComponent(0);
                        }
                    }
                    else if (arguments.getRange() == null /*&& (
                                   arguments.getSection() != null ||
                                    arguments.getChapter() != null ||
                                    arguments.getTitle() != null ||
                                    arguments.getArticle() != null ||
                                    arguments.getParagraph() != null ||
                                    arguments.getPoint() != null ||
                                    arguments.getLetter() != null)*/) {

                        if (arguments.getSection() != null) {
                            IDs.add(arguments.getSection());
                            Types.add(Hierarchy.Section);
                        }
                        if (arguments.getChapter() != null) {
                            IDs.add(arguments.getChapter());
                            Types.add(Hierarchy.Chapter);
                        }
                        if (arguments.getTitle() != null) {
                            IDs.add(arguments.getTitle());
                            Types.add(Hierarchy.Title);
                        }
                        if (arguments.getArticle() != null) {
                            IDs.add(arguments.getArticle());
                            Types.add(Hierarchy.Article);
                        }
                        if (arguments.getParagraph() != null) {
                            IDs.add(arguments.getParagraph());
                            Types.add(Hierarchy.Paragraph);
                        }
                        if (arguments.getPoint() != null) {
                            IDs.add(arguments.getPoint());
                            Types.add(Hierarchy.Point);
                        }
                        if (arguments.getLetter() != null) {
                            IDs.add(arguments.getLetter());
                            Types.add(Hierarchy.Letter);
                        }

                        if (arguments.getSection() != null ||
                                arguments.getChapter() != null ||
                                arguments.getTitle() != null) {
                            new Viewer(act.findComponent(IDs, Types)).printComponent(0);
                        }
                        else if (arguments.getArticle() != null) {
                            new Viewer(act.findArticle(arguments.getArticle()).findComponent(
                                    IDs.subList(1, IDs.size()),
                                    Types.subList(1, Types.size())))
                                    .printComponent(0);
                        }
                        else if (arguments.getParagraph() == null &&
                                arguments.getPoint() == null &&
                                arguments.getLetter() == null) {
                            new Viewer(act).printComponent(0);
                        }
                        else
                            throw new ParameterException("Incorrect path");
                    }
                    else
                        throw new ParameterException("");
                } break;
                case "tableOfContents": {
                    if (arguments.getSection() != null) {
                        IDs.add(arguments.getSection());
                        Types.add(Hierarchy.Section);
                    }
                    if (arguments.getRange() == null &&
                            arguments.getChapter() == null &&
                            arguments.getTitle() == null &&
                            arguments.getArticle() == null &&
                            arguments.getParagraph() == null &&
                            arguments.getPoint() == null &&
                            arguments.getLetter() == null)
                        new Viewer(act.findComponent(IDs, Types)).printComponent(2);
                } break;
            default:
                throw new ParameterException("Incorrect mode");
            }

        } catch (ParameterException p) {
            System.out.println("Wrong syntax of argments parsed from cmd line");
            System.out.println(p.getMessage());
        } catch (IOException n) {
            System.out.println("Couldn't parse from file");
        } catch (NullPointerException g) {
            System.out.println(g.getMessage());
        }
    }
}
