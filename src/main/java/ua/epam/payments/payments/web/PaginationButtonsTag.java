package ua.epam.payments.payments.web;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class PaginationButtonsTag extends SimpleTagSupport {
    private String length;
    private String records;


    public void setLength(String length) {
        this.length = length;
    }

    public void setRecords(String records) {
        this.records = records;
    }

    @Override
    public void doTag() throws JspException, IOException {
        double result = Double.parseDouble(length) / Double.parseDouble(records);
        result = Math.ceil(result);
        getJspContext().getOut().write(String.valueOf(result));

    }
}
