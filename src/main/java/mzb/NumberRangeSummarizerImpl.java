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
            }catch(NumberFormatException)
            {
                throw new IllegalArgumentException("Invalid input: " + part);
            }
        }
        numbers.sort(Integer::compareTo);
        return numbers;
    }

}
