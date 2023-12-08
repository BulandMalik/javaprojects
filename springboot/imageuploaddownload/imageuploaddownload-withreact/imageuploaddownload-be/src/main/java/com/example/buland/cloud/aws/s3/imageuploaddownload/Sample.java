package com.example.buland.cloud.aws.s3.imageuploaddownload;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Sample {

    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<Integer>();
        numbers.add(1);numbers.add(2);numbers.add(3);numbers.add(4);numbers.add(5);

        System.out.println(numbers);
        List<Integer> li = numbers.stream().filter( num -> num != 3).collect(Collectors.toList());
        System.out.println("____________________________");
        System.out.println(li);

        System.out.println("\n\n\n**********************");
        main();

        System.out.println("\n\n\n**********************");
        main2();
    }

    public static void main() {
        List<String> strings = Arrays.asList("apple", "banana", "orange", "grape", "kiwi");

        System.out.println(strings);
        List<String> filteredList = strings.stream()
                .filter(s -> s.length() > 5)
                .collect(Collectors.toList());
        System.out.println("____________________________");
        System.out.println(filteredList); // Output: [banana, orange]
    }

    public static void main2() {
        List<String> strings = Arrays.asList("apple", "banana", "orange", "grape", "kiwi");

        System.out.println(strings);
        List<String> filteredList = strings.stream()
                .filter(s -> s.equals("apple"))
                .collect(Collectors.toList());
        System.out.println("____________________________");
        System.out.println(filteredList); // Output: [apple]
    }
}
