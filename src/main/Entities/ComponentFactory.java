package Entities;


import Assets.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComponentFactory {
    public static ActComponent makeComponent(Hierarchy type, String entryLine){
        switch(type){
            case Section:
                String[] sectionEntrySplit = entryLine.split(" ", 3);
                return new Section(sectionEntrySplit[1], sectionEntrySplit[2]);

            case Chapter:
                String[] chapterEntrySplit = entryLine.split(" ", 3);
                return new Chapter(chapterEntrySplit[1], chapterEntrySplit[2]);

            case Title:
                return new Title(entryLine);

            case Article:
                Matcher articleMatcher = Pattern.compile("\\d+[a-z]?").matcher(entryLine);
                articleMatcher.find();
                return new Article(articleMatcher.group(), "");

            case Paragraph:
                String[] paragraphEntrySplit = Pattern.compile("\\.").split(entryLine, 2);
                return new Paragraph(paragraphEntrySplit[0], paragraphEntrySplit[1].substring(1));

            case Point:
                String[] pointEntrySplit = Pattern.compile("\\)").split(entryLine, 2);
                return new Point(pointEntrySplit[0] ,pointEntrySplit[1].substring(1));

            case Letter:
                String[] letterEntrySplit = Pattern.compile("\\)").split(entryLine, 2);
                return new Letter(letterEntrySplit[0], letterEntrySplit[1].substring(1));

            default:
                throw new InternalError("Invalid Enum state!");
        }
    }
}

