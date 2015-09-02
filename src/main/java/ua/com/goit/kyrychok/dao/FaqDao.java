package ua.com.goit.kyrychok.dao;

import ua.com.goit.kyrychok.domain.Faq;

import java.util.List;

public interface FaqDao {

    void add(int projectId, Faq faq);

    List<Faq> fetch(int projectId);
}
