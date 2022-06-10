package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.CrudRepository;
import ru.javawebinar.topjava.repository.InMemoryMealsCrudRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    static final CrudRepository<Meal> repository = new InMemoryMealsCrudRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        Integer idInt = id.isEmpty() ? null : Integer.valueOf(id);
        Meal meal = new Meal(idInt,
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories")));
        log.info(meal.isNew() ? "Create: {}" : "Update: {}", meal.getId());
        repository.save(meal);
        resp.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            log.info("Get all");
            req.setAttribute("mealList", MealsUtil.getAllMeal(repository.getAll(), CALORIES_PER_DAY));
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        } else {


            switch (action) {
                case "delete":
                    int id = getId(req);
                    log.info("Delete: {}", id);
                    repository.delete(id);
                    resp.sendRedirect("meals");
                    break;
                case "create":
                case "update":
                    final Meal meal = action.equals("create") ? new Meal(LocalDateTime.now(), "", 1000) :
                            repository.getById(getId(req));
                    req.setAttribute("meal", meal);
                    req.getRequestDispatcher("mealEdit.jsp").forward(req, resp);
                    break;
                default:
            }
        }

    }

    private int getId(HttpServletRequest req) {
        String paramId = Objects.requireNonNull(req.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
