
import mzb.NumberRangeSummarizer;
import mzb.NumberRangeSummarizerImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Collection;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NumberRangeSummarizerImplTest {

    private NumberRangeSummarizer summarizer;

    @BeforeEach
    void setUp() {
        summarizer = new NumberRangeSummarizerImpl();
    }

    // Test collect method

    @Test
    @DisplayName("Collect with normal input")
    void collectNormalInput() {
        String input = "1,3,6,7,8,12,13,14,15,21,22,23,24,31";
        Collection<Integer> result = summarizer.collect(input);

        List<Integer> expected = Arrays.asList(1, 3, 6, 7, 8, 12, 13, 14, 15, 21, 22, 23, 24, 31);
        assertIterableEquals(expected, result, "Collected numbers should match input");
    }

    @Test
    @DisplayName("Collect with single number")
    void collectSingleNumber() {
        String input = "5";
        Collection<Integer> result = summarizer.collect(input);

        List<Integer> expected = Arrays.asList(5);
        assertIterableEquals(expected, result);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    @DisplayName("Collect with empty or null input")
    void collectEmptyOrNullInput(String input) {
        Collection<Integer> result = summarizer.collect(input);
        assertTrue(result.isEmpty(), "Result should be empty for null or empty input");
    }

    @Test
    @DisplayName("Collect with whitespace around numbers")
    void collectWithWhitespace() {
        String input = " 1, 3 , 5 ,7 ";
        Collection<Integer> result = summarizer.collect(input);

        List<Integer> expected = Arrays.asList(1, 3, 5, 7);
        assertIterableEquals(expected, result, "Should handle whitespace correctly");
    }

    @Test
    @DisplayName("Collect with negative numbers")
    void collectNegativeNumbers() {
        String input = "-5,-3,0,2,4";
        Collection<Integer> result = summarizer.collect(input);

        List<Integer> expected = Arrays.asList(-5, -3, 0, 2, 4);
        assertIterableEquals(expected, result, "Should handle negative numbers correctly");
    }

    @Test
    @DisplayName("Collect with unsorted input")
    void collectUnsortedInput() {
        String input = "10,1,5,3,8";
        Collection<Integer> result = summarizer.collect(input);

        List<Integer> expected = Arrays.asList(1, 3, 5, 8, 10);
        assertIterableEquals(expected, result, "Should sort numbers in ascending order");
    }

    @Test
    @DisplayName("Collect with duplicate numbers")
    void collectDuplicateNumbers() {
        String input = "1,2,2,3,4,4,4,5";
        Collection<Integer> result = summarizer.collect(input);

        List<Integer> expected = Arrays.asList(1, 2, 2, 3, 4, 4, 4, 5);
        assertIterableEquals(expected, result, "Should preserve duplicate numbers");
    }

    @Test
    @DisplayName("Collect should throw exception for invalid input")
    void collectInvalidInput() {
        String input = "1,abc,3";
        assertThrows(IllegalArgumentException.class, () -> summarizer.collect(input),
                "Should throw IllegalArgumentException for non-numeric input");
    }

    // Test summarizeCollection method

    @Test
    @DisplayName("Summarize with sample input")
    void summarizeSampleInput() {
        Collection<Integer> input = Arrays.asList(1, 3, 6, 7, 8, 12, 13, 14, 15, 21, 22, 23, 24, 31);
        String result = summarizer.summarizeCollection(input);

        String expected = "1, 3, 6-8, 12-15, 21-24, 31";
        assertEquals(expected, result, "Should correctly summarize the sample input");
    }

    @Test
    @DisplayName("Summarize with consecutive numbers")
    void summarizeConsecutiveNumbers() {
        Collection<Integer> input = Arrays.asList(1, 2, 3, 5, 6, 7, 9, 10, 11);
        String result = summarizer.summarizeCollection(input);

        String expected = "1-3, 5-7, 9-11";
        assertEquals(expected, result, "Should group consecutive numbers into ranges");
    }

    @Test
    @DisplayName("Summarize with single number")
    void summarizeSingleNumber() {
        Collection<Integer> input = Arrays.asList(5);
        String result = summarizer.summarizeCollection(input);

        String expected = "5";
        assertEquals(expected, result, "Should handle single number correctly");
    }

    @Test
    @DisplayName("Summarize with negative numbers")
    void summarizeNegativeNumbers() {
        Collection<Integer> input = Arrays.asList(-5, -4, -3, 0, 1, 2, 4);
        String result = summarizer.summarizeCollection(input);

        String expected = "-5--3, 0-2, 4";
        assertEquals(expected, result, "Should handle negative numbers correctly");
    }

    @Test
    @DisplayName("Summarize with duplicate numbers")
    void summarizeDuplicateNumbers() {
        Collection<Integer> input = Arrays.asList(1, 2, 2, 3, 4, 4, 4, 5);
        String result = summarizer.summarizeCollection(input);

        String expected = "1-5";
        assertEquals(expected, result, "Should handle duplicate numbers by treating them as part of the range");
    }

    @Test
    @DisplayName("Summarize with unsorted collection")
    void summarizeUnsortedCollection() {
        Collection<Integer> input = Arrays.asList(5, 1, 3, 2, 4, 7, 6);
        String result = summarizer.summarizeCollection(input);

        String expected = "1-7";
        assertEquals(expected, result, "Should handle unsorted input by sorting it first");
    }

    @ParameterizedTest
    @CsvSource({
            "'1,2,3', '1-3'",
            "'1,3,5', '1, 3, 5'",
            "'1,2,4,5', '1-2, 4-5'",
            "'1,2,3,5,6,7', '1-3, 5-7'",
            "'-2,-1,0,1,2', '-2-2'"
    })
    @DisplayName("Parameterized summarize tests")
    void parameterizedSummarizeTests(String inputStr, String expected) {
        Collection<Integer> input = summarizer.collect(inputStr);
        String result = summarizer.summarizeCollection(input);

        assertEquals(expected, result, "Parameterized test failed for input: " + inputStr);
    }

}