package org.crazybun.codegen.ssm;

import org.crazybun.codegen.ssm.assist.ExportInfo;
import org.crazybun.codegen.ssm.db.DataBase;
import org.crazybun.codegen.ssm.db.Table;
import org.crazybun.codegen.ssm.service.IService;
import org.crazybun.codegen.ssm.service.ServiceImpl;
import org.crazybun.codegen.ssm.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@SuppressWarnings("serial")
public class ServiceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(StringUtils.UTF8);
        String dbType = StringUtils.trim(request.getParameter("dbType"));
        String ip = StringUtils.trim(request.getParameter("ip"));
        String port = StringUtils.trim(request.getParameter("port"));
        String sid = StringUtils.trim(request.getParameter("sid"));
        String user = StringUtils.trim(request.getParameter("user"));
        String password = StringUtils.trim(request.getParameter("password"));
        DataBase dataBase = new DataBase(dbType, ip, port, sid, user, password);
        IService service = new ServiceImpl();
        try {
            List<Table> tableList = service.listAllTable(dataBase);
            HttpSession session = request.getSession();
            session.setAttribute("dataBase", dataBase);
            request.setAttribute("tableList", tableList);
            request.setAttribute("exportInfo", new ExportInfo());
            request.getRequestDispatcher("/WEB-INF/jsp/tablelist.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            writeToBrowser(response, e.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(StringUtils.UTF8);
        String domain = StringUtils.trim(request.getParameter("domain"));
        String project = StringUtils.trim(request.getParameter("project"));
        String storePath = StringUtils.trim(request.getParameter("storePath"));
        String idType = StringUtils.trim(request.getParameter("idType"));
        String template = StringUtils.trim(request.getParameter("template"));
        String daoMerge = StringUtils.trim(request.getParameter("daoMerge"));
        String[] tableNames = request.getParameterValues("tableName");
        String[] moduleNames = request.getParameterValues("moduleName");
        String[] menuNames = request.getParameterValues("menuName");
        ExportInfo exportInfo = new ExportInfo(domain, project, storePath, idType, template, tableNames, moduleNames, menuNames);
        exportInfo.setAppRealPath(request.getServletContext().getRealPath("/"));
        exportInfo.setDaoMerge("true".equals(daoMerge));
        DataBase dataBase = (DataBase) request.getSession().getAttribute("dataBase");
        IService service = new ServiceImpl();
        writeToBrowser(response, service.doExport(dataBase, exportInfo).toString());
    }

    private void writeToBrowser(HttpServletResponse response, String result) throws IOException {
        response.setHeader("ContentType", "text/plain");
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        response.setCharacterEncoding(StringUtils.UTF8);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.println("<!DOCTYPE html>");
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<title>Export Result</title>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println(result);
            writer.println("<div style='text-align:center;'><button onclick='history.back();return false;'>点击返回</button></div>");
            writer.println("</body>");
            writer.println("</html>");
            writer.flush();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
