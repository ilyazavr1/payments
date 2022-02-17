package ua.epam.payments.payments.web;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class CardBooleanFormatTag extends SimpleTagSupport {
    private boolean status;


    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public void doTag() throws JspException, IOException {
        String result;
        if (status) result = "Blocked";
        else result = "Active";

        getJspContext().getOut().write(result);

    }
}
