package ua.epam.payments.payments.web;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatTag extends SimpleTagSupport {

    private LocalDateTime dateTime;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public void doTag() throws JspException, IOException {

        getJspContext().getOut().write( dateTime.format(formatter));

    }
}
