package ua.epam.payments.payments.web.listener;

import ua.epam.payments.payments.db.DBManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
       DBManager.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBManager.getInstance().closeConnection();
    }
}
