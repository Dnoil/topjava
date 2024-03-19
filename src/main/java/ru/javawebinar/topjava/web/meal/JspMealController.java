package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {

    @GetMapping
    public String getMeals(Model model) {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @GetMapping("/delete/{id}")
    public String deleteMeal(@PathVariable("id") int id) {
        super.delete(id);
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String createMealForm(Model model) {
        model.addAttribute("meal", new Meal());
        return "mealForm";
    }

    @PostMapping("/create")
    public String createMeal(@ModelAttribute Meal meal) {
        super.create(meal);
        return "redirect:/meals";
    }

    @GetMapping("/update/{id}")
    public String updateMealForm(Model model, @PathVariable("id") int id) {
        model.addAttribute("meal", super.get(id));
        return "mealForm";
    }

    @PostMapping("/update/{id}")
    public String updateMeal(@PathVariable("id") int id, @ModelAttribute Meal meal) {
        super.update(meal, id);
        return "redirect:/meals";
    }

    @GetMapping("/filter")
    public String getBetweenDateTime(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
                                     @Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime,
                                     Model model) {
        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return "redirect:/meals";
    }
}
