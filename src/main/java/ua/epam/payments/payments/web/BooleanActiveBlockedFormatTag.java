package ua.epam.payments.payments.web;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class BooleanActiveBlockedFormatTag extends SimpleTagSupport {
    private boolean status;


    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", Locale.getDefault());

        String result;

        if (status) result = "blocked";
        else result = "active";
       //

        //System.out.println(pageContext.getSession().getAttribute("lang"));

        getJspContext().getOut().write(result);

    }
}
