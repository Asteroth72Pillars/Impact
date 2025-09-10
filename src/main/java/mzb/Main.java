package mzb;
import java.util.Collection;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        NumberRangeSummarizer summarizer = new NumberRangeSummarizerImpl();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Number Range Summarizer Demo");
        System.out.println("----------------------------");
        System.out.println();

        String input;
        if (args.length > 0) {
            input = String.join("", args);
            System.out.println("Using command line input: " + input);
        } else {
            input = scanner.nextLine();

            if (input.trim().isEmpty())
            {
                input = "1,3,6,7,8,12,13,14,15,21,22,23,24,31";
                System.out.println("Using default input: " + input);
            }
        }

        try {
            // Processing input
            Collection<Integer> numbers = summarizer.collect(input);
            String result = summarizer.summarizeCollection(numbers);

            // results
            System.out.println();
            System.out.println("Collected numbers: " + numbers);
            System.out.println("Summarized result: " + result);
        } catch (Exception e) {
            System.err.println("Error processing input: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}