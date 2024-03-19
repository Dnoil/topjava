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
public class JspMealController {

    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    private MealService mealService;

    @GetMapping
    public String getMeals(Model model) {
        log.info("meals");
        int userId = SecurityUtil.authUserId();
        int userCaloriesPerDay = SecurityUtil.authUserCaloriesPerDay();
        model.addAttribute("meals", MealsUtil.getTos(mealService.getAll(userId), userCaloriesPerDay));
        return "meals";
    }

    @GetMapping("/delete/{id}")
    public String deleteMeal(@PathVariable("id") int id) {
        mealService.delete(id, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String createMealForm(Model model) {
        log.info("createMealForm");
        model.addAttribute("meal", new Meal());
        return "mealForm";
    }

    @PostMapping("/create")
    public String createMeal(@ModelAttribute Meal meal) {
        if (meal.isNew()) {
            mealService.create(meal, SecurityUtil.authUserId());
        }
        return "redirect:/meals";
    }

    @GetMapping("/update/{id}")
    public String updateMealForm(Model model, @PathVariable("id") int id) {
        log.info("updateMealForm");
        int userId = SecurityUtil.authUserId();
        model.addAttribute("meal", mealService.get(id, userId));
        return "mealForm";
    }

    @PostMapping("/update/{id}")
    public String updateMeal(@ModelAttribute Meal meal) {
            mealService.update(meal, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @GetMapping("/filter")
    public String getBetweenDateTime(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
            @Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime, Model model) {
        log.info("getBetweenDateTime from {} to {} from {} to {}", startDate, endDate, startTime, endTime);
        int userId = SecurityUtil.authUserId();
        int userCaloriesPerDay = SecurityUtil.authUserCaloriesPerDay();
        List<Meal> mealsByDate = mealService.getBetweenInclusive(startDate, endDate, userId);
        List<MealTo> mealsByDateTime = MealsUtil.getFilteredTos(mealsByDate, userCaloriesPerDay, startTime, endTime);
        model.addAttribute("meals", mealsByDateTime);
        return "redirect:/meals";
    }
}
