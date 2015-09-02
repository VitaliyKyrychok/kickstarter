package ua.com.goit.kyrychok.controller;

import ua.com.goit.kyrychok.dao.ProjectEventDao;
import ua.com.goit.kyrychok.domain.ProjectEvent;

import java.util.List;

public class ProjectEventController {
    private ProjectEventDao projectEventDao;

    public ProjectEventController(ProjectEventDao projectEventDao) {
        this.projectEventDao = projectEventDao;
    }

    public List<ProjectEvent> fetch(int projectId) {
        return projectEventDao.fetch(projectId);
    }
}
