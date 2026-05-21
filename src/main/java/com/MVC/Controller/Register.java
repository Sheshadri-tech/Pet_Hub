package com.MVC.Controller;

import java.io.IOException;

import com.MVC.Model.Registration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name="register",urlPatterns= {"/register"})

public class Register extends HttpServlet {

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");

        HttpSession session = req.getSession();
        Registration reg = new Registration(session);

        try {

            if(req.getParameter("register") != null) {

                String name = req.getParameter("name");
                String phone = req.getParameter("phone");
                String email = req.getParameter("email");
                String password = req.getParameter("password");
                String confirm = req.getParameter("confirm");

                if(password.equals(confirm)) {

                    String status = reg.registration(name, phone, email, password);

                    if(status.equals("existed")) {

                        req.setAttribute("status", "Existed Record");
                        RequestDispatcher rd1 = req.getRequestDispatcher("Registration.jsp");
                        rd1.forward(req, resp);

                    }
                    else if(status.equals("success")) {

                        req.setAttribute("status", "Successfully Registered");
                        RequestDispatcher rd = req.getRequestDispatcher("Login.jsp");
                        rd.forward(req, resp);

                    }
                    else if(status.equals("failure")) {

                        req.setAttribute("status", "Registration Failed");
                        RequestDispatcher rd = req.getRequestDispatcher("Registration.jsp");
                        rd.forward(req, resp);

                    }
                }
            }

            else if(req.getParameter("Login") != null || req.getParameter("login") != null) {

                String email = req.getParameter("email");
                String password = req.getParameter("password");

                String status = reg.login(email, password);

                if(status.equals("success")) {

                    req.setAttribute("status", "Login Successfull");

                    if(session.getAttribute("uname") != null
                            && session.getAttribute("id").equals("1")) {

                        RequestDispatcher rd = req.getRequestDispatcher("Admin.jsp");
                        rd.forward(req, resp);

                    }
                    else {

                        RequestDispatcher rd = req.getRequestDispatcher("Home1.jsp");
                        rd.forward(req, resp);

                    }

                }
                else if(status.equals("failure")) {

                    req.setAttribute("status", "Login failed");
                    RequestDispatcher rd = req.getRequestDispatcher("Login.jsp");
                    rd.forward(req, resp);

                }

            }

            else if(req.getParameter("Logout") != null) {

                session.invalidate();
                resp.sendRedirect("Home1.jsp");

            }

            else if(session.getAttribute("uname") != null
                    && req.getParameter("submit") != null) {

                String name = req.getParameter("name");
                String email = req.getParameter("email");
                String phone = req.getParameter("phone");

                String status = reg.update(name, email, phone);

                if(status.equals("success")) {

                    session.setAttribute("uname", name);
                    session.setAttribute("phone", phone);
                    session.setAttribute("email", email);

                    req.setAttribute("status",
                            "Profile Updated Successfully");

                    RequestDispatcher rd =
                            req.getRequestDispatcher("Home1.jsp");

                    rd.forward(req, resp);

                }
                else if(status.equals("failure")) {

                    req.setAttribute("status",
                            "Failed to Update Profile");

                    RequestDispatcher rd =
                            req.getRequestDispatcher("Home1.jsp");

                    rd.forward(req, resp);

                }
            }

        }
        catch(Exception e) {

            e.printStackTrace();

        }
    }

    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {

        processRequest(req, resp);

    }

    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {

        processRequest(req, resp);

    }

}