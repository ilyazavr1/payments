package ua.epam.payments.payments.web.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.db.DBManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;

@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        if (DBManager.getInstance() == null) {
            logger.error("Unable to get database connection");
        }

        logger.info("Database connection received");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBManager.getInstance().closeConnection();
        logger.debug("Database connection closed");
    }
}
