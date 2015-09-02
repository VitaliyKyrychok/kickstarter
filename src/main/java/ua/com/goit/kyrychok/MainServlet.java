package ua.com.goit.kyrychok;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ua.com.goit.kyrychok.controller.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class MainServlet extends HttpServlet {
    private CategoryController categoryController;
    private ProjectController projectController;
    private RewardController rewardController;
    private ProjectEventController projectEventController;
    private FaqController faqController;

    private String getAction(HttpServletRequest req) {
        String result;
        String reqURI = req.getRequestURI();
        result = reqURI.substring(req.getContextPath().length(), reqURI.length());
        return result;
    }

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(
                this.getServletContext());
        categoryController = (CategoryController) context.getBean("categoryController");
        projectController = (ProjectController) context.getBean("projectController");
        rewardController = (RewardController) context.getBean("rewardController");
        projectEventController = (ProjectEventController) context.getBean("projectEventController");
        faqController = (FaqController) context.getBean("faqController");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("URI=" + req.getRequestURI());
        String action = getAction(req);
        System.out.println(new Date() + "action=" + action);
        if (action.startsWith("/kickstarter")) {
            req.setAttribute("welcomeMessage", categoryController.getWelcomeMessage());
            req.setAttribute("activeHome", "active");
            req.setAttribute("container", "parts/kickstarter.jsp");
        } else if (action.startsWith("/categories")) {
            req.setAttribute("categories", categoryController.getCategories());
            req.setAttribute("activeCategories", "active");
            req.setAttribute("container", "parts/categories.jsp");
        } else if (action.startsWith("/category")) {
            int categoryId = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("activeCategories", "active");
            req.setAttribute("category", categoryController.get(categoryId));
            req.setAttribute("projects", projectController.fetch(categoryId));
            req.setAttribute("container", "parts/category.jsp");
        } else if (action.startsWith("/projects")) {
            req.setAttribute("activeProjects", "active");
            req.setAttribute("projects", projectController.fetchAll());
            req.setAttribute("container", "parts/projects.jsp");
        } else if (action.startsWith("/project")) {
            int projectId = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("activeProjects", "active");
            req.setAttribute("category", categoryController.getByProjectId(projectId));
            req.setAttribute("project", projectController.get(projectId));
            req.setAttribute("rewards", rewardController.fetch(projectId));
            String viewMode = req.getParameter("viewMode");
            if ("history".equals(viewMode)) {
                req.setAttribute("projectEvents", projectEventController.fetch(projectId));
            } else if ("comments".equals(viewMode)) {
                req.setAttribute("faqs", faqController.fetch(projectId));
            }
            req.setAttribute("container", "parts/project.jsp");
        } else if (action.startsWith("/donate")) {
            int projectId = Integer.parseInt(req.getParameter("projectId"));
            req.setAttribute("activeProjects", "active");
            req.setAttribute("category", categoryController.getByProjectId(projectId));
            req.setAttribute("project", projectController.get(projectId));
            String rewardParametr = req.getParameter("rewardId");
            if (rewardParametr != null) {
                int rewardId = Integer.parseInt(rewardParametr);
                req.setAttribute("reward", rewardController.get(rewardId));
            }
            req.setAttribute("container", "parts/donate.jsp");
        }
        req.getRequestDispatcher("main.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);
        System.out.println(action);
        String additionalParams = "";
        if (action.startsWith("/donate")) {
            int projectId = Integer.parseInt(req.getParameter("projectId"));
            projectController.doDonate(projectId, req.getParameter("rewardId"), req.getParameter("userName"),
                    req.getParameter("cardNumber"), req.getParameter("pledge"));
        } else if (action.startsWith("/faq")) {
            int projectId = Integer.parseInt(req.getParameter("projectId"));
            faqController.addFaq(projectId, req.getParameter("question"));
            additionalParams = "&viewMode=comments";
        }
        resp.sendRedirect("project?id=" + req.getParameter("projectId") + additionalParams);
    }
}
