/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Prashant
 */
public class adminLogin extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            String username = request.getParameter("adminUsername");
            String password = request.getParameter("adminPassword");
            int empId = 0;
            ResultSet rset = null;
            RequestDispatcher rd = null;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/payroll", "root", "tiger");
                PreparedStatement pstmt = con.prepareStatement("select emp_id from users where username=? and password=? and emp_type='admin' ");
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                rset = pstmt.executeQuery();

                if (!rset.next()) {
                    out.println("<center style=\"color:red\">invalid Username or Password</center>");
                    rd = request.getRequestDispatcher("./index.html");
                    rd.include(request, response);
                } else {
                    //out.println("valid Username or Password");
                    rset.beforeFirst();
                    while (rset.next()) {
                        empId = rset.getInt("emp_id");
                    }
                    HttpSession adminSession = request.getSession();
                    adminSession.setAttribute("empId", empId);
                    //userSession.setAttribute("username", username);
                    response.sendRedirect("adminHome");
                }
                con.close();
            } catch (ClassNotFoundException ex) {
                out.println("Class Not Found Exception");
            } catch (SQLException ex) {
                out.println("SQL Exception" + ex);
            }
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Payroll</title>");            
            out.println("</head>");
            out.println("<body>");
            //out.println("<h1>Servlet adminLogin at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
