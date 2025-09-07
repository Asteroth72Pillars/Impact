package mzb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NumberRangeSummarizerImpl implements NumberRangeSummarizer
{
    @Override
    public Collection<Integer> collect(String input)
    {
        if (input == null || input.isEmpty())
        {
            return new ArrayList<>();
        }

        String[] parts = input.split(",");
        List<Integer> numbers = new ArrayList<>();

        for (String part : parts)
        {
            try{
                numbers.add(Integer.parseInt(part.trim()));
            }catch(NumberFormatException e)
            {
                throw new IllegalArgumentException("Invalid input: " + part, e);
            }
        }
        numbers.sort(Integer::compareTo);
        return numbers;
    }

    @Override
    public String summarizeCollection(Collection<Integer> input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        List<Integer> numbers = new ArrayList<>(input);
        StringBuilder result = new StringBuilder();
        int start = numbers.get(0);
        int prev = start;

        for (int i = 1; i < numbers.size(); i++) {
            int current = numbers.get(i);
            if (current == prev + 1) {
                prev = current;
            } else {
                appendRange(result, start, prev);
                start = current;
                prev = current;
            }
        }
        appendRange(result, start, prev);
        return result.toString();
    }

    private void appendRange(StringBuilder result, int start, int end) {
        if (result.length() > 0) {
            result.append(", ");
        }
        if (start == end) {
            result.append(start);
        } else if (end == start + 1) {
            result.append(start).append(", ").append(end);
        } else {
            result.append(start).append("-").append(end);
        }
    }

}
