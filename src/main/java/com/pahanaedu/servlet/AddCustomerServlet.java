package com.pahanaedu.servlet;

import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/addCustomer")
public class AddCustomerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("loggedIn") == null || !(Boolean) session.getAttribute("loggedIn")) {
            response.sendRedirect("jsp/login.jsp");
            return;
        }

        String accountNumber = request.getParameter("accountNumber");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        int units = Integer.parseInt(request.getParameter("units"));

        Customer customer = new Customer(accountNumber, name, address, phone, units);
        String filePath = getServletContext().getRealPath("/WEB-INF/data/customers.txt");
        CustomerDAO dao = new CustomerDAO(filePath);
        try {
            dao.addCustomer(customer);
            request.setAttribute("message", "Customer added successfully");
        } catch (IOException e) {
            request.setAttribute("error", "Error adding customer: " + e.getMessage());
        }
        request.getRequestDispatcher("jsp/addCustomer.jsp").forward(request, response);
    }
}
