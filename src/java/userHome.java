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
public class userHome extends HttpServlet {

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
            HttpSession userSession = request.getSession();
            int empId = Integer.parseInt(userSession.getAttribute("empId").toString());
            //String username = userSession.getAttribute("username").toString();
            String fname = "", lname = "", email = "", dob = "";
            int basicPay = 0, da = 0, hra = 0, grossPay = 0, tax = 0, netPay = 0;

            ResultSet rset;
            RequestDispatcher rd;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/payroll", "root", "tiger");
                PreparedStatement pstmt = con.prepareStatement("select * from emp where emp_id=?");
                pstmt.setInt(1, empId);
                rset = pstmt.executeQuery();

                if (!rset.next()) {
                    out.println("<center style=\"color:red\">Employee details not found !</center>");
                } else {
                    //out.println("valid Username or Password");
                    rset.beforeFirst();
                    while (rset.next()) {
                        fname = rset.getString("fname");
                        lname = rset.getString("lname");
                        email = rset.getString("email");
                        dob = rset.getString("dob");
                        basicPay = rset.getInt("basic_pay");
                    }

                    da = (int) (0.5 * basicPay);
                    hra = (int) (0.2 * basicPay);
                    grossPay = da + hra + basicPay;
                    //out.println(da + " - " + hra + " - " + grossPay);

                    if (grossPay > 300000) {
                        tax = (int)(0.4 * grossPay);
                    } else if (grossPay > 200000) {
                        tax = (int)(0.3 * grossPay);
                    } else if (grossPay > 100000) {
                        tax = (int)(0.2 * grossPay);
                    } else {
                        tax = (int)(0.1 * grossPay);
                    }
                    netPay = grossPay - tax;
                    
                    rset.beforeFirst();
                }
                con.close();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(userHome.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(userHome.class.getName()).log(Level.SEVERE, null, ex);
            }

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Payroll</title>");
            out.println("<meta charset=\"utf-8\">\n"
                    + "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
            out.println("<link href=\"./Bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\">\n"
                    + "        <link href=\"./CustomStyles.css\" rel=\"stylesheet\" type=\"text/css\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<nav class=\"navbar navbar-default navbar-fixed-top navbar-inverse\">\n"
                    + "            <div class=\"container-fluid\">\n"
                    + "                <!-- Brand and toggle get grouped for better mobile display -->\n"
                    + "\n"
                    + "                <div class=\"navbar-header\">\n"
                    + "                    <button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#bs-payroll-navbar-collapse-1\" aria-expanded=\"false\">\n"
                    + "                        <span class=\"sr-only\">Toggle navigation</span>\n"
                    + "                        <span class=\"icon-bar\"></span>\n"
                    + "                        <span class=\"icon-bar\"></span>\n"
                    + "                        <span class=\"icon-bar\"></span>\n"
                    + "                    </button>  \n"
                    + "                    <a class=\"navbar-brand\" href=\"./index.html\">Payroll</a>\n"
                    + "                </div>\n"
                    + "\n"
                    + "                <!-- Collect the nav links, forms, and other content for toggling -->\n"
                    + "                <div class=\"collapse navbar-collapse\" id=\"bs-payroll-navbar-collapse-1\">\n"
                    + "\n"
                    + "          <ul class=\"nav navbar-nav navbar-right\">\n"
                    + "             <li class=\"dropdown\">\n"
                    + "             <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">Welcome " + fname + " <span class=\"caret\"></span></a>\n"
                    + "             <ul class=\"dropdown-menu\">\n"
                    //+ "             <li><a href=\"./index.html\">Logout</a></li>\n"
                    + "               <li> <form action=\"killSession\" method=\"get\">\n"
                    + "                 <center><input type=\"submit\" class=\"btn btn-sm btn-info col-lg-8 col-lg-offset-2\" value=\"Logout\" \n"
                    + "                 name=\"logout\" />\n"
                    + "                 </form> </center> </li>  \n"
                    + "             </ul>\n"
                    + "             </li>\n"
                    + "         </ul>\n"
                    + "                </div><!-- /.navbar-collapse -->\n"
                    + "            </div><!-- /.container-fluid -->\n"
                    + "        </nav>");
            //out.println("<h1>Servlet userHome at " + empId + username + fname + "</h1>");
            out.println("<br><br>");

            out.println("<div class=\"container col-lg-4 col-lg-offset-4 \">");
            out.println("<table class=\"table table-bordered table-hover\">");
            out.println("<tr>");
            out.println("<th> Employee ID </th>");
            out.println("<td>" + empId + "</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<th> First Name </th>");
            out.println("<td>" + fname + "</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<th> Last Name </th>");
            out.println("<td>" + lname + "</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<th> Email </th>");
            out.println("<td>" + email + "</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<th> Date of Birth </th>");
            out.println("<td>" + dob + "</td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</div>");
            
            out.println("<div class=\"container col-lg-4 col-lg-offset-4 \">");
            out.println("<table class=\"table table-bordered table-hover\">");
            out.println("<tr>");
            out.println("<th> Basic Pay </th>");
            out.println("<td>" + basicPay + "</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<th> DA </th>");
            out.println("<td>" + da + "</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<th> HRA </th>");
            out.println("<td>" + hra + "</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<th> Gross Pay </th>");
            out.println("<td>" + grossPay + "</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<th> Taxable Amount </th>");
            out.println("<td>" + tax + "</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<th> Net Pay </th>");
            out.println("<th>" + netPay + "</td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("<div>");

            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js\"></script>");
            out.println("<script src=\"./Bootstrap/js/bootstrap.min.js\"></script>");
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
