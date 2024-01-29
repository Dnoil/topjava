package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
       Map<LocalDate, Boolean> checkExcess = UserMealsUtil.getExcessMap(meals, caloriesPerDay);
       List<UserMealWithExcess> resultList = new ArrayList<>();
       for (UserMeal meal : meals) {
           boolean inCorrectTimeRange = TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime);
           if (inCorrectTimeRange) {
                resultList.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), checkExcess.get(meal.getDateTime().toLocalDate())));
           }
       }
       return resultList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Boolean> checkExcess = UserMealsUtil.getExcessMap(meals, caloriesPerDay);
        List<UserMealWithExcess> resultList = meals.stream()
                .filter(e -> TimeUtil.isBetweenHalfOpen(e.getDateTime().toLocalTime(), startTime, endTime))
                .map(e -> new UserMealWithExcess(
                        e.getDateTime(),
                        e.getDescription(),
                        e.getCalories(),
                        checkExcess.get(e.getDateTime().toLocalDate())))
                .collect(Collectors.toList());
        return resultList;
    }

    public static Map<LocalDate, Boolean> getExcessMap(List<UserMeal> meals, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesMap = new HashMap<>();
        meals.forEach(meal -> caloriesMap.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), (newCalories, currentCalories) -> newCalories + currentCalories));
        Set<LocalDate> dates = caloriesMap.keySet();
        Map<LocalDate, Boolean> excessMap = new HashMap<>();
        dates.forEach(date -> excessMap.put(date, caloriesMap.get(date) > caloriesPerDay));
        return excessMap;
    }
}