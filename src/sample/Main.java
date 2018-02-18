package sample;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main
{

    public static void main(String[] args)
    {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8,9,10,11,12,14,15);

        List<Integer> first = numbers.stream().filter(e->e%3==0||e%7==0).collect(Collectors.toList());
        System.out.println(first);
        List<Integer > second = first.stream().map(e->e-1).collect(Collectors.toList());
        System.out.println(second );
        Integer third = second.stream().reduce((a, b)->(a+b)%5).orElse(0);
        System.out.println("the nr is "+third);
        System.out.println(Arrays.asList(third));

//        System.out.println(numbers);
//        List<Integer> first = numbers.stream().filter(p->((p%3==0)||(p%7==0))).collect(Collectors.toList());
//        System.out.println(first);
//        List<Integer> second = first.stream().map(p->p-1).collect(Collectors.toList());
//        System.out.println(second);
//        Integer third = second.stream().reduce((a,b)->((a+b)%5)).orElse(0); // similar to .get(); returns a default value if no values are left after the reduce
//        System.out.println(third);
//        List<Integer> fourth = Collections.singletonList(third); // same as Arrays.asList(third)
//        System.out.println(fourth);
//        List<Integer> everything =Collections.singletonList(numbers.stream().filter(p->((p%3==0)||(p%7==0))).map(p->p-1).
//                reduce((a,b)->((a+b)%5)).orElse(0));
//        System.out.println(everything);
        class Author{
            public String name; public int age;
            public Author (String n, int a){
                name = n; age =a;

            }
            public String toString(){
                return name + " "+age;
            }

            public String getName() {
                return name;
            }
        }

        ArrayList<Author> authors = new ArrayList<>();
        Author a1 = new Author("Bernard", 32);
        Author a2 = new Author("Pedro", 23);
        Author a3 = new Author("Norman", 65);
        Author a4 = new Author("felix", 45);
        Author a5 = new Author("rob", 34);
        Author a6 = new Author("bobby", 87);
        Author a7= new Author("peter", 76);
        Author a8 = new Author("louis", 65);
        Author a9 = new Author("penny", 45);
        Author a10 = new Author("stephen", 56);
        Author a11 = new Author("roman", 57);
        Author a12 = new Author("a12", 58);
        Author a13 = new Author("a13", 54);
        Author a14 = new Author("a14", 35);
        Author a15 = new Author("Ricardo", 87);
        Author a16 = new Author("peter", 65);
        Author a17 = new Author("trump", 54);
        Author a18 = new Author("luiza", 87);
        Author a19 = new Author("pesos", 43);
        Author a20 = new Author("zahid", 23);

        authors.add(a1);
        authors.add(a2);
        authors.add(a3);
        authors.add(a4);
        authors.add(a5);
        authors.add(a6);
        authors.add(a7);
        authors.add(a8);
        authors.add(a9);
        authors.add(a10);
        authors.add(a13);
        authors.add(a14);
        authors.add(a15);
        authors.add(a11);
        authors.add(a12);
        authors.add(a16);
        authors.add(a17);
        authors.add(a18);
        authors.add(a19);
        authors.add(a20);

        //Exercise 1: Get the unique surnames in
        // uppercase of the first 15 book authors that are 50 years old or older.

        List<String> finalList = authors.stream().filter(e->e.age>=50).
                map(Author::getName).map(String::toUpperCase).distinct().
                limit(15).collect(Collectors.toList());
        System.out.println(finalList);

        //Exercise 2: Print out the sum of ages of all female authors younger than 25.

        int age =authors.stream().filter(e->e.age<=25).map(e->e.age).reduce(Integer::sum).orElse(0);//reduce(0,Integer::sum);
        System.out.println(age);

        List<String> words = Arrays.asList("hi", "hello", "rome", "threads",
                "threading", "parallel", "programming", "zahid",
                "waking", "dear", "white", "people");

        words.stream().forEach(System.out::println);
        words.stream().forEach(e->System.out.println(" "+e));

        System.out.println(words.stream().map(String::toUpperCase).reduce(String::concat).orElse(" "));
        words.stream().map(e->e+"!").collect(Collectors.toList()).stream().forEach(System.out::println);
        Stream.of("a", "b", "c", "", "e").takeWhile(s -> !s.isEmpty()).forEach(System.out::print);
        Stream.of("a", "b", "c", "de", "f").dropWhile(s -> s.length() <= 1).forEach(System.out::print);
        Stream.iterate(1, i -> i <= 10, i -> 2 * i).forEach(System.out::println);

        List<Integer> nr = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8,9,10,11,12,14,15);
        System.out.println(nr.stream().filter(e->e%2==0||e%5==0).map(e->"N"+e+"R").reduce(String::concat).orElse(""));


    }
}