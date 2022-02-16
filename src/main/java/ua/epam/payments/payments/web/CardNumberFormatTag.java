package ua.epam.payments.payments.web;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class CardNumberFormatTag extends SimpleTagSupport {
    private String number;


    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public void doTag() throws JspException, IOException {

        getJspContext().getOut().write( number.replaceAll("(.{4})(?!$)", "$1-"));

    }
}
