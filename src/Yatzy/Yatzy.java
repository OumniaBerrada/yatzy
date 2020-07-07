package Yatzy;

import java.util.*;
import java.util.stream.IntStream;


public class Yatzy {

    protected static int[] dice = new int[5];

    /* * Constructor Yatzy * */
    public Yatzy(int d1, int d2, int d3, int d4, int d5) {
        dice = IntStream.of(d1,d2,d3,d4,d5).toArray();
    }

    /* * Returns the sum of all dice * */
    public static int chance(int d1, int d2, int d3, int d4, int d5) {
        return IntStream.of(d1, d2, d3, d4, d5).sum();
    }

    /* * Check if all dice have the sum number *
     * Returns 50 if all dice have the sum number or 0 if not. */
    public static int yatzy(int... dice)
    {
        return Arrays.stream(dice).allMatch(die -> die == dice[0]) ? 50 : 0;
    }

    /* * Get integers as params
     * Returns the sum of the params when the params and the requested value are equal.*/
    protected static int sum(int d1, int d2, int d3, int d4, int d5, int value) {
        return IntStream.of(d1, d2, d3, d4, d5).filter(v -> v == value).sum();
    }

    /* * Get array int as params
     * Returns the sum of the params when the params of array and the requested value are equal.*/
    protected static int arraySum(int value) {
        return Arrays.stream(dice).filter(v -> v == value).sum();
    }

    /* * Returns the sum of the params when the params equal 1.*/
    public static int ones(int d1, int d2, int d3, int d4, int d5) {
       return sum(d1, d2, d3, d4, d5, 1);
    }

    /* * Returns the sum of the params when the params equal 2.*/
    public static int twos(int d1, int d2, int d3, int d4, int d5) {
        return sum(d1, d2, d3, d4, d5, 2);
    }

    /* * Returns the sum of the params when the params equal 3.*/
    public static int threes(int d1, int d2, int d3, int d4, int d5) {
        return sum(d1, d2, d3, d4, d5, 3);
    }

    /* * Returns the sum of the params when the params equal 4.*/
    public int fours()
    {
        return arraySum(4);
    }

    /* * Returns the sum of the params when the params equal 5.*/
    public int fives()
    {
        return arraySum(5);
    }

    /* * Returns the sum of the params when the params equal 6.*/
    public int sixes()
    {
        return arraySum( 6);
    }

    /* * Returns the sum of the two highest matching dice.*/
    public static int score_pair(int d1, int d2, int d3, int d4, int d5)  //If the name is composed of several words, the first letter of each word must be in uppercase : scorePair
    {
        List<Integer> diceList = Arrays.asList(d1, d2, d3, d4, d5);
        Integer maxPair = diceList.stream()
                .filter(die -> Collections.frequency(diceList, die) > 1)
                .max(Integer::compare)
                .orElse(0);
        return maxPair * 2;
    }

    /* * Returns the sum of two pairs of dice.*/
    public static int two_pair(int d1, int d2, int d3, int d4, int d5) //If the name is composed of several words, the first letter of each word must be in uppercase : twoPair
    {
        List<Integer> diceList = Arrays.asList(d1, d2, d3, d4, d5);
        Integer distinctPair = diceList.stream()
                .filter(die -> Collections.frequency(diceList, die) > 1)
                .distinct().reduce(0, Integer::sum);
        return distinctPair * 2;
    }
    /* * If params equal the number of dice that get same results, return the sum of results of these dice.*/
    protected static int numberOfAKind(int d1, int d2, int d3, int d4, int d5, int value) {
        List<Integer> diceList = Arrays.asList(d1, d2, d3, d4, d5);
        Integer maxPair = diceList.stream()
                .filter(die -> Collections.frequency(diceList, die) > value - 1)
                .findFirst()
                .orElse(0);
        return maxPair * value;
    }


    /* * Returns the sum of the dice if there are three of them with the same number.*/
    public static int three_of_a_kind(int d1, int d2, int d3, int d4, int d5) //If the name is composed of several words, the first letter of each word must be in uppercase : threeOfAKind
    {
       return numberOfAKind(d1, d2, d3, d4, d5, 3);
    }

    /* * Returns the sum of the dice if there are four of them with the same number.*/
    public static int four_of_a_kind(int d1, int d2, int d3, int d4, int d5) //If the name is composed of several words, the first letter of each word must be in uppercase : fourOfAKind
    {
        return numberOfAKind(d1, d2, d3, d4, d5, 4);
    }

    /* * Check if the dice respect the number sequence then returns the sum of the dice.*/
    public static int smallStraight(int d1, int d2, int d3, int d4, int d5) {
        int[] tallies = new int[5];
        IntStream.of(d1, d2, d3, d4, d5).forEach(value -> tallies[value - 1]++);
        return IntStream.of(tallies)
                .allMatch(value -> value == 1) ? 15 : 0;
    }

    /* * Check if the dice respect the number sequence then returns the sum of the dice.*/
    public static int largeStraight(int d1, int d2, int d3, int d4, int d5) {
      int[] tallies = new int[6];
      IntStream.of(d1, d2, d3, d4, d5).forEach(value -> tallies[value - 1]++);
      return IntStream.of(tallies)
              .skip(1)
              .allMatch(value -> value == 1) ? 20 : 0;
    }

    /* * Check if the dice are two of a kind and three of a kind then returns the sum of all dice. */
    public static int fullHouse(int d1, int d2, int d3, int d4, int d5) {
        int[] tallies = new int[6];
        IntStream.of(d1, d2, d3, d4, d5).forEach(value -> tallies[value - 1]++);
        OptionalInt b2At = IntStream.range(0, 6).filter(value -> tallies[value] == 2).reduce((first, second) -> second);
        OptionalInt b3At = IntStream.range(0, 6).filter(value -> tallies[value] == 3).reduce((first, second) -> second);

        return b2At.isPresent() && b3At.isPresent() ? (b2At.getAsInt()+1) * 2 + (b3At.getAsInt()+1) * 3 : 0;
    }
}
