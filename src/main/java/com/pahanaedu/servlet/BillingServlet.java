package com.pahanaedu.servlet;

import com.pahanaedu.model.User;
import com.pahanaedu.service.BillingService;
import com.pahanaedu.service.CustomerService;
import com.pahanaedu.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/billing")
public class BillingServlet extends HttpServlet {
    private final BillingService billingService;
    private final CustomerService customerService;
    private final ItemService itemService;

    public BillingServlet() {
        this.billingService = new BillingService();
        this.customerService = new CustomerService();
        this.itemService = new ItemService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is authenticated
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        try {
            // Load customers and items for the billing form
            request.setAttribute("customers", customerService.getAllCustomers());
            request.setAttribute("items", itemService.getAllItems());
            request.getRequestDispatcher("/WEB-INF/views/billing.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Failed to load billing form: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/billing.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is authenticated
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        try {
            // Get form parameters
            String customerId = request.getParameter("customerId");
            String itemId = request.getParameter("itemId");
            double unitsConsumed = parseDouble(request.getParameter("unitsConsumed"), 0.0);

            // Calculate bill
            BillingService.BillDetails bill = billingService.calculateBill(customerId, itemId, unitsConsumed);

            // Set bill details and reload form data
            request.setAttribute("bill", bill);
            request.setAttribute("customers", customerService.getAllCustomers());
            request.setAttribute("items", itemService.getAllItems());
            request.setAttribute("message", "Bill calculated successfully");
            request.getRequestDispatcher("/WEB-INF/views/billing.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("customers", customerService.getAllCustomers());
            request.setAttribute("items", itemService.getAllItems());
            request.getRequestDispatcher("/WEB-INF/views/billing.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
            request.setAttribute("customers", customerService.getAllCustomers());
            request.setAttribute("items", itemService.getAllItems());
            request.getRequestDispatcher("/WEB-INF/views/billing.jsp").forward(request, response);
        }
    }

    // Helper method to parse double with default value
    private double parseDouble(String value, double defaultValue) {
        try {
            return value != null ? Double.parseDouble(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}