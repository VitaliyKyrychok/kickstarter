package ua.com.goit.kyrychok.controller;

import ua.com.goit.kyrychok.dao.RewardDao;
import ua.com.goit.kyrychok.domain.Reward;

import java.util.List;

public class RewardController {
    private RewardDao rewardDao;

    public RewardController(RewardDao rewardDao) {
        this.rewardDao = rewardDao;
    }

    public List<Reward> fetch(int projectId) {
        return rewardDao.fetch(projectId);
    }

    public Reward get(int id) {
        return rewardDao.load(id);
    }
}
