package ru.kirillvenediktov.springbootuniversity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kirillvenediktov.springbootuniversity.dao.DatabaseStartupFiller;

import javax.annotation.PostConstruct;

@Service
public class StartupFillingService {

    private final DatabaseStartupFiller filler;

    @Autowired
    public StartupFillingService(DatabaseStartupFiller filler) {
        this.filler = filler;
    }

    @PostConstruct
    public void prepareDataForWork() {
        filler.fillTables();
    }

}
