package ua.com.goit.kyrychok.dao;

import ua.com.goit.kyrychok.domain.ProjectEvent;

import java.util.List;

public interface ProjectEventDao {

    List<ProjectEvent> fetch(int projectId);
}
