package net.bachi.test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest {
    public static void main(String[] args) {
        /*
        Arrays.asList("come on!", "I", "love", "lisp", "because", "is", "cool")
                .stream()
                .filter(s -> s.startsWith("c"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);

        Arrays.asList("caaa", "aaa", "czzz", "cbbb", "qqq", "ceee", "ppp")
                .stream()
                .filter(s -> s.startsWith("c"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);
        Arrays.asList("caaa", "aaa", "czzz", "cbbb", "qqq", "ceee", "ppp")
                .stream()
                .filter(s -> "a");
        */

        /*
        List<String> list = Arrays.asList("caaa", "aaa", "czzz", "cbbb", "qqq", "ceee", "ppp");
        list.stream()
            .filter(s -> {
                System.out.println("Cuteer: " + s);
                return true;
            }).anyMatch(s -> false);

        Stream.of("h", "e", "l", "l", "o", "s", "t", "r", "e", "a", "m", "s", "!")
                .filter(s -> {
                    System.out.println("Cuteer: " + s);
                    return true;
                }).anyMatch(s -> s.startsWith("h"));
         */

        /*
        Stream.of("h", "e", "l", "l", "o", "s", "t", "r", "e", "a", "m", "s", "!")
                .filter(s -> {
                    System.out.println("Cuteer: " + s);
                    return true;
                }).forEach(System.out::println);

         */


        /*
        Stream.of("a", "b", "c", "d")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("A");
                })
                .forEach(s -> System.out.println("forEach " + s));

        */

        List<Fruit> fruits = Arrays.asList(
                new Fruit("Banana", "Yellow "),
                new Fruit("Blackberry", "Black"),
                new Fruit("Black Currant", "Black"),
                new Fruit("Blueberry", "Purple")
        );

        Map<String, List<Fruit>> fruitsByColor = fruits
                .stream()
                .collect(Collectors.groupingBy(f -> f.getColor()));

        String phrase = fruits
                .stream()
                .map(f -> f.getName())
                .collect(Collectors.joining(" and ", "In Brazil ", " are unusual fruits."));

        System.out.println(phrase);

        Collector<Fruit, StringJoiner, String> personNameCollector =
                Collector.of(
                        () -> {
                            System.out.println("collector");
                            return new StringJoiner(" | ");
                        },          // supplier
                        (j, f) -> {
                            System.out.println("accumulator");
                            j.add(f.getName().toUpperCase());
                        },  // accumulator
                        (j1, j2) -> {
                            System.out.println("combiner");
                            return j1.merge(j2);
                        },               // combiner
                        StringJoiner::toString);                // finisher

        String names = fruits
                .stream()
                .collect(personNameCollector);
        System.out.println(names);


        int value =  IntStream.rangeClosed(1, 4)
                .reduce(0, (int x, int y) -> x + y);
        System.out.println("value=" + value);
    }
}
