package Entities;

import Assets.Act;
import Assets.ActComponent;
import Assets.Hierarchy;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ActParser {
    private List<String> text;
    private List<Predicate<String>> patternsToRemove;

    ActParser(List<String> text){
        this.text = text;
        this.patternsToRemove =
                Stream.of(
                        "\\d{4}-\\d{2}-\\d{2}",
                        ".*\\(uchylony\\).*$",
                        ".*pominięt[ey].*$",
                        "^.$",
                        "©Kancelaria Sejmu",
                        "^KONSTYTUCJA$",
                        "^RZECZYPOSPOLITEJ POLSKIEJ$",
                        "^z dnia 2 kwietnia 1997 r.$",
                        "^W trosce o byt i przyszłość naszej Ojczyzny,$",
                        "^odzyskawszy w 1989 roku możliwość suwerennego i demokratycznego stanowienia$",
                        "^o Jej losie,$",
                        "^my, Naród Polski - wszyscy obywatele Rzeczypospolitej,$",
                        "^zarówno wierzący w Boga$",
                        "^będącego źródłem prawdy, sprawiedliwości, dobra i piękna,$",
                        "^jak i nie podzielający tej wiary,$",
                        "^a te uniwersalne wartości wywodzący z innych źródeł,$",
                        "^równi w prawach i w powinnościach wobec dobra wspólnego - Polski,$",
                        "^wdzięczni naszym przodkom za ich pracę, za walkę o niepodległość okupioną$",
                        "^ogromnymi ofiarami, za kulturę zakorzenioną w chrześcijańskim dziedzictwie Naro-$",
                        "^du i ogólnoludzkich wartościach,$",
                        "^nawiązując do najlepszych tradycji Pierwszej i Drugiej Rzeczypospolitej,$",
                        "^zobowiązani, by przekazać przyszłym pokoleniom wszystko, co cenne$",
                        "^z ponadtysiącletniego dorobku,$",
                        "^złączeni więzami wspólnoty z naszymi rodakami rozsianymi po świecie,$",
                        "^świadomi potrzeby współpracy ze wszystkimi krajami dla dobra Rodziny Ludzkiej,$",
                        "^pomni gorzkich doświadczeń z czasów, gdy podstawowe wolności i prawa człowie-$",
                        "^ka były w naszej Ojczyźnie łamane,$",
                        "^pragnąc na zawsze zagwarantować prawa obywatelskie, a działaniu instytucji pu-$",
                        "^blicznych zapewnić rzetelność i sprawność,$",
                        "^w poczuciu odpowiedzialności przed Bogiem lub przed własnym sumieniem,$",
                        "^ustanawiamy Konstytucję Rzeczypospolitej Polskiej$",
                        "^jako prawa podstawowe dla państwa$",
                        "^oparte na poszanowaniu wolności i sprawiedliwości, współdziałaniu władz, dialogu$",
                        "^społecznym oraz na zasadzie pomocniczości umacniającej uprawnienia obywateli i$",
                        "^ich wspólnot.$",
                        "^Wszystkich, którzy dla dobra Trzeciej Rzeczypospolitej tę Konstytucję będą stoso-$",
                        "^ali,$",
                        "^wzywamy, aby czynili to, dbając o zachowanie przyrodzonej godności człowieka,$",
                        "^jego prawa do wolności i obowiązku solidarności z innymi,$",
                        "^a poszanowanie tych zasad mieli za niewzruszoną podstawę Rzeczypospolitej Pol-$",
                        "^skiej.$",
                        "^Dz.U. 2007 Nr 50 poz. 331$",
                        "^USTAWA$",
                        "^z dnia 16 lutego 2007 r.$",
                        "^o ochronie konkurencji i konsumentów$"
                )
                        .map((x) -> Pattern.compile(x).asPredicate())
                        .collect(Collectors.toList());

    }

    private void removeLines() {
        for (Predicate<String> pattern : patternsToRemove) {
            text = text.stream().
                    filter(pattern.negate()).
                    collect(Collectors.toList());
        }

    }

    private void joinLines(){
        for (int i = 0; i < text.size() - 1; i++){
            if(Pattern.matches(".*-$", text.get(i))){
                text.set(i, text.get(i).substring(0, text.get(i).length() - 1) + text.get(i+1));
                text.remove(i + 1);
            }
            else if(Pattern.matches("^Rozdział (\\d+[a-zA-Z]*|[IVXCDL]+[a-zA-Z]*)\\s*" ,text.get(i))){
                text.set(i, text.get(i) + ' ' + text.get(i+1));
                text.remove(i + 1);
            }
            else if(Pattern.matches("^DZIAŁ ([IVXCD]+[A-Z]*)\\s*", text.get(i))){
                text.set(i, text.get(i) + ' ' + text.get(i+1));
                text.remove(i + 1);
            }
        }
    }

    public  Act listToTree() {
        removeLines();
        joinLines();
        Act output = new Act();
        Stack stack = new Stack();
        for (String line : text) {
            for (Hierarchy type : Hierarchy.values()) {
                Pattern p = Pattern.compile(getPattern(type));
                Matcher m = p.matcher(line);
                if (m.find()) {
                    ActComponent component = ComponentFactory.makeComponent(type, m.group());
                    line = line.substring(m.end());
                    while (!stack.empty() && component.getWeight() <= ((ActComponent) stack.peek()).getWeight()) {
                        stack.pop();
                    }
                    if (!stack.empty()) {
                        ((ActComponent) stack.peek()).subComponents.add(component);
                    }
                    else {
                        output.subComponents.add(component);
                    }
                    stack.push(component);
                }
            }
            if(!line.isEmpty()) {
                if(!stack.empty()){
                    ((ActComponent)stack.peek()).extendBody(line);
                }
            }
        }
        return output;
    }


    private static String getPattern(Hierarchy type) {
            switch (type) {
                case Section:
                    return ("^DZIAŁ ([IVXCD]+[A-Z]?)\\s*.*");
                case Chapter:
                    return ("^Rozdział (\\d+\\p{L}?|[IVXCDL]+)\\s*.*");
                case Title:
                    return ("^[\\p{Lu} ]+\\s*$");
                case Article:
                    return ("^Art\\. (\\d+\\p{L}?)\\.\\s*");
                case Paragraph:
                    return ("^(\\d+\\p{L}?)\\.\\s.*");
                case Point:
                    return ("^(\\d+\\p{L}?)\\)\\s.*");
                case Letter:
                    return ("^(\\p{L})\\)\\s.*");
                default:
                    throw new IllegalArgumentException();
        }
    }
}