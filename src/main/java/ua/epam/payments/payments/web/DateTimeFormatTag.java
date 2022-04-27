package ua.epam.payments.payments.web;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatTag extends SimpleTagSupport {

    private LocalDateTime dateTime;
    private final DateTimeFormatter dateTimeFormatUK = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private final DateTimeFormatter dateTimeFormatUSA = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }


    @Override
    public void doTag() throws JspException, IOException {
        if (getJspContext().findAttribute("lang").equals("en")) {
            getJspContext().getOut().write(dateTime.format(dateTimeFormatUSA));
        }else  getJspContext().getOut().write(dateTime.format(dateTimeFormatUK));

    }
}
