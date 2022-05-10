package ua.epam.payments.payments.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


@WebServlet("/download")
public class PaymentPdfServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String fileName = "test.pdf";
        FileInputStream fileInputStream = null;
        OutputStream responseOutputStream = null;
        try
        {
          //  String filePath = req.getServletContext().getRealPath("C:\\Users\\Illia\\IdeaProjects\\payments\\src\\main\\webapp\\WEB-INF\\pdf\\")+ fileName;
            File file = new File("C:\\Users\\Illia\\IdeaProjects\\payments\\src\\main\\webapp\\WEB-INF\\pdf\\test.pdf");

            String mimeType = req.getServletContext().getMimeType("C:\\Users\\Illia\\IdeaProjects\\payments\\src\\main\\webapp\\WEB-INF\\pdf\\test.pdf");
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            resp.setContentType(mimeType);
            resp.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            resp.setContentLength((int) file.length());

            fileInputStream = new FileInputStream(file);
            responseOutputStream = resp.getOutputStream();
            int bytes;
            while ((bytes = fileInputStream.read()) != -1) {
                responseOutputStream.write(bytes);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            fileInputStream.close();
            responseOutputStream.close();
        }

    }
}
