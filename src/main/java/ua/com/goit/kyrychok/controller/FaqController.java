package ua.com.goit.kyrychok.controller;

import ua.com.goit.kyrychok.dao.FaqDao;
import ua.com.goit.kyrychok.domain.Faq;

import java.util.List;

public class FaqController {
    private FaqDao faqDao;

    public FaqController(FaqDao faqDao) {
        this.faqDao = faqDao;
    }

    public List<Faq> fetch(int projectId) {
        return faqDao.fetch(projectId);
    }

    public void addFaq(int projectId, String question) {
        Faq faq = new Faq(question);
        faqDao.add(projectId, faq);
    }
}
