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
public class adminHome extends HttpServlet {

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

            HttpSession adminSession = request.getSession();
            int empId = Integer.parseInt(adminSession.getAttribute("empId").toString()); // Admin Emp ID
            String fname = "", lname = "", email = "", dob = "";
            int basicPay = 0;
            ResultSet rset;
            RequestDispatcher rd;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/payroll", "root", "");
                PreparedStatement pstmt = con.prepareStatement("select * from emp where emp_id=?");
                pstmt.setInt(1, empId);
                rset = pstmt.executeQuery();

                if (!rset.next()) {
                    out.println("<center style=\"color:red\">Admin details not found !</center>");
                } else {
                    //out.println("valid Username or Password");
                    rset.beforeFirst();
                    while (rset.next()) {
                        fname = rset.getString("fname");
                        /*lname = rset.getString("lname");
                        email = rset.getString("email");
                        dob = rset.getString("dob");
                        basicPay = rset.getInt("basic_pay");*/
                    }
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

            out.println("<script>\n"
                    + "		function validateAddEmpForm()\n"
                    + "            {\n"
                    + "                var fname = document.forms[\"addEmpForm\"][\"fname\"].value;\n"
                    + "                if (fname == \"\")\n"
                    + "                {\n"
                    + "                    alert(\"Please Enter First Name !!!\");\n"
                    + "                    return false;\n"
                    + "                }\n"
                    + "				var lname = document.forms[\"addEmpForm\"][\"lname\"].value;\n"
                    + "                if (lname == \"\")\n"
                    + "                {\n"
                    + "                    alert(\"Please Enter Last Name !!!\");\n"
                    + "                    return false;\n"
                    + "                }\n"
                    + "				var email = document.forms[\"addEmpForm\"][\"email\"].value;\n"
                    + "                if (email == \"\")\n"
                    + "                {\n"
                    + "                    alert(\"Please Enter Email !!!\");\n"
                    + "                    return false;\n"
                    + "                }\n"
                    + "				var uname = document.forms[\"addEmpForm\"][\"username\"].value;\n"
                    + "                if (uname == \"\")\n"
                    + "                {\n"
                    + "                    alert(\"Please Enter Username !!!\");\n"
                    + "                    return false;\n"
                    + "                }\n"
                    + "				var password = document.forms[\"addEmpForm\"][\"password\"].value;\n"
                    + "                if (password == \"\")\n"
                    + "                {\n"
                    + "                    alert(\"Please Enter Password !!!\");\n"
                    + "                    return false;\n"
                    + "                }\n"
                    + "				var dob = document.forms[\"addEmpForm\"][\"dob\"].value;\n"
                    + "                if (dob == \"\")\n"
                    + "                {\n"
                    + "                    alert(\"Please Enter DOB !!!\");\n"
                    + "                    return false;\n"
                    + "                }\n"
                    + "				var empId = document.forms[\"addEmpForm\"][\"empId\"].value;\n"
                    + "                if (empId == \"\" || empId<=0)\n"
                    + "                {\n"
                    + "                    alert(\"Please Enter valid Employee ID !!!\");\n"
                    + "                    return false;\n"
                    + "                }\n"
                    + "				var empType = document.forms[\"addEmpForm\"][\"empType\"].value;\n"
                    + "                if (empType == \"\")\n"
                    + "                {\n"
                    + "                    alert(\"Please Enter Employee Type !!!\");\n"
                    + "                    return false;\n"
                    + "                }\n"
                    + "				var basicPay = document.forms[\"addEmpForm\"][\"basicPay\"].value;\n"
                    + "                if (basicPay == \"\" || basicPay<0)\n"
                    + "                {\n"
                    + "                    alert(\"Please Enter valid Basic Pay !!!\");\n"
                    + "                    return false;\n"
                    + "                }\n"
                    + "            }\n"
                    + "			\n"
                    + "			function validateRemoveEmpForm()\n"
                    + "            {\n"
                    + "                var empId = document.forms[\"removeEmpForm\"][\"empId\"].value;\n"
                    + "                if (empId == \"\" || empId<=0)\n"
                    + "                {\n"
                    + "                    alert(\"Please Enter valid Employee ID !!!\");\n"
                    + "                    return false;\n"
                    + "                }				\n"
                    + "            }\n"
                    + "			\n"
                    + "	</script>");

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
                    + "             <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">Admin " + fname + " <span class=\"caret\"></span></a>\n"
                    + "             <ul class=\"dropdown-menu \" >\n"
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

            //out.println("<h1>Servlet adminHome at " + request.getContextPath() + "</h1>");
            out.println("<br><br><br>");

            out.println("<div class=\"container\">\n"
                    + "        <div class=\"row\">\n"
                    + "            <div class=\"col-xs-6 col-lg-offset-3\">\n"
                    + "\n"
                    + "                <div id=\"accordion\" class=\"panel-group\">\n"
                    + "                    <div class=\"panel panel-primary\">\n"
                    + "                        <div class=\"panel-heading\">\n"
                    + "                            <h4 class=\"panel-title\">\n"
                    + "                                <a href=\"#panelBodyOne\" data-toggle=\"collapse\"\n"
                    + "                                   data-parent=\"#accordion\">\n"
                    + "                                    <span class=\"glyphicon glyphicon-menu-down\"></span>\n"
                    + "                                    Add Employee\n"
                    + "                                </a>\n"
                    + "                            </h4>\n"
                    + "                        </div>\n"
                    + "                        <div id=\"panelBodyOne\" class=\"panel-collapse collapse\">\n"
                    + "                            <div class=\"panel-body\">                             \n"
                    + "								<form method=\"get\" action=\"addEmployee\" name=\"addEmpForm\" onsubmit=\"return validateAddEmpForm()\"> 								\n"
                    + "									<div class=\"form-group\">\n"
                    + "										<label for=\"fname\">First Name</label>\n"
                    + "										<input type=\"text\" class=\"form-control\" placeholder=\"Enter First Name\" name=\"fname\">\n"
                    + "									</div>\n"
                    + "									<div class=\"form-group\">\n"
                    + "										<label for=\"lname\">Last Name</label>\n"
                    + "										<input type=\"text\" class=\"form-control\" placeholder=\"Enter Last Name\" name=\"lname\">\n"
                    + "									</div>\n"
                    + "									<div class=\"form-group\">\n"
                    + "										<label for=\"email\">Email</label>\n"
                    + "										<input type=\"text\" class=\"form-control\" placeholder=\"Enter Email\" name=\"email\">\n"
                    + "									</div>\n"
                    + "									<div class=\"form-group\">\n"
                    + "										<label for=\"username\">Username</label>\n"
                    + "										<input type=\"text\" class=\"form-control\" placeholder=\"Enter Username\" name=\"username\">\n"
                    + "									</div>\n"
                    + "									<div class=\"form-group\">\n"
                    + "										<label for=\"password\">Password</label>\n"
                    + "										<input type=\"password\" class=\"form-control\" placeholder=\"Enter Password\" name=\"password\">\n"
                    + "									</div>\n"
                    + "									<div class=\"form-group\">\n"
                    + "										<label for=\"DOB\">DOB</label>\n"
                    + "										<input type=\"date\" class=\"form-control\" placeholder=\"Enter DOB\" name=\"dob\">\n"
                    + "									</div>\n"
                    + "									<div class=\"form-group\">\n"
                    + "										<label for=\"empId\">Employee ID</label>\n"
                    + "										<input type=\"number\" class=\"form-control\" placeholder=\"Enter Employee ID\" name=\"empId\">\n"
                    + "									</div>\n"
                    + "									<div class=\"form-group\">\n"
                    + "										<label for=\"empType\">Employee Type</label>\n"
                    + "										<input type=\"text\" class=\"form-control\" placeholder=\"Enter Employee Type\" name=\"empType\">\n"
                    + "									</div>\n"
                    + "									<div class=\"form-group\">\n"
                    + "										<label for=\"basicPay\">Basic Pay</label>\n"
                    + "										<input type=\"number\" class=\"form-control\" placeholder=\"Enter Basic pay\" name=\"basicPay\">\n"
                    + "									</div>\n"
                    + "									<input type=\"submit\" class=\"btn btn-primary\" value=\"Add Employee\">							\n"
                    + "								</form>					\n"
                    + "                            </div>\n"
                    + "                        </div>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"panel panel-primary\">\n"
                    + "                        <div class=\"panel-heading\">\n"
                    + "                            <h4 class=\"panel-title\">\n"
                    + "                                <a href=\"#panelBodyTwo\" data-toggle=\"collapse\"\n"
                    + "                                   data-parent=\"#accordion\">\n"
                    + "                                    <span class=\"glyphicon glyphicon-menu-down\"></span>\n"
                    + "                                    Remove Employee\n"
                    + "                                </a>\n"
                    + "                            </h4>\n"
                    + "                        </div>\n"
                    + "                        <div id=\"panelBodyTwo\" class=\"panel-collapse collapse\">\n"
                    + "                            <div class=\"panel-body\">\n"
                    + "                                <form method=\"get\" action=\"removeEmployee\" name=\"removeEmpForm\" onsubmit=\"return validateRemoveEmpForm()\">\n"
                    + "									<div class=\"form-group\">\n"
                    + "										<label for=\"empId\">Employee ID</label>\n"
                    + "										<input type=\"number\" class=\"form-control\" placeholder=\"Enter Employee ID\" name=\"empId\">\n"
                    + "									</div>\n"
                    + "									<input type=\"submit\" class=\"btn btn-primary\" value=\"Remove Employee\">\n"
                    + "								</form>\n"
                    + "                            </div>\n"
                    + "                        </div>\n"
                    + "                    </div>\n"
                    + "                    \n"
                    + "                </div>\n"
                    + "\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "    </div>");

            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js\"></script>");
            out.println("<script src=\"./Bootstrap/js/bootstrap.min.js\"></script>");

            out.println("<script type=\"text/javascript\">\n"
                    + "        $(document).ready(function () {\n"
                    + "            $('.collapse').on('shown.bs.collapse', function () {\n"
                    + "                $(this).parent().find('.glyphicon-menu-down')\n"
                    + "                                .removeClass('glyphicon-menu-down')\n"
                    + "                                .addClass('glyphicon-menu-up');\n"
                    + "            }).on('hidden.bs.collapse', function () {\n"
                    + "                $(this).parent().find(\".glyphicon-menu-up\")\n"
                    + "                                .removeClass(\"glyphicon-menu-up\")\n"
                    + "                                .addClass(\"glyphicon-menu-down\");\n"
                    + "            });\n"
                    + "        });\n"
                    + "    </script>");

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
