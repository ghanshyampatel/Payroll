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
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class addEmployee extends HttpServlet {

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
            //out.println("hello");
            // New Employee Details
            String fname = request.getParameter("fname");
            //out.println(fname);
            String lname = request.getParameter("lname");
            String email = request.getParameter("email");
            String dob = request.getParameter("dob");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String empType = request.getParameter("empType");
            int empId = Integer.parseInt(request.getParameter("empId")); 
            int basicPay = Integer.parseInt(request.getParameter("basicPay"));                              
            out.println();
            RequestDispatcher rd;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/payroll", "root", "tiger");
                
                PreparedStatement pstmt1 = con.prepareStatement("insert into emp(emp_id, fname, lname, email, dob, basic_pay) values(?,?,?,?,?,?)");
                pstmt1.setInt(1, empId);
                pstmt1.setString(2, fname);
                pstmt1.setString(3, lname);
                pstmt1.setString(4, email);
                pstmt1.setString(5, dob);
                pstmt1.setInt(6, basicPay);
                pstmt1.executeUpdate();  
                
                out.println("hello");
                
                PreparedStatement pstmt2 = con.prepareStatement("insert into users(emp_id, username, password, emp_type) values(?,?,?,?)");
                pstmt2.setInt(1, empId);
                pstmt2.setString(2, username);
                pstmt2.setString(3, password);   
                pstmt2.setString(4, empType);
                pstmt2.executeUpdate(); 
                
                out.println("<center><h3> Employee " + fname + " added successfully <h3></center>");
                
                rd = request.getRequestDispatcher("adminHome");
                rd.include(request,response);
                con.close();
            } catch (ClassNotFoundException ex) {
                out.println("Class Not Found Exception : " + ex);
            } catch (SQLException ex) {
                out.println("<center><h3> SQL Exception : " + ex + "</center></h3>");
                rd = request.getRequestDispatcher("adminHome");
                rd.include(request,response);
            }

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet addEmployee</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addEmployee at " + request.getContextPath() + empId + fname +lname +email +dob +basicPay +username + password + empType+"</h1>");
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
