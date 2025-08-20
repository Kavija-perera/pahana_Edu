package com.pahanaedu.servlet;

import com.pahanaedu.model.Item;
import com.pahanaedu.model.User;
import com.pahanaedu.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/item")
public class ItemServlet extends HttpServlet {
    private final ItemService itemService;

    public ItemServlet() {
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

        // Optionally restrict to admin users
        User user = (User) session.getAttribute("user");
        if (!"admin".equalsIgnoreCase(user.getRole())) {
            request.setAttribute("error", "Access denied: Admin privileges required");
            request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "view":
                viewItem(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "list":
            default:
                listItems(request, response);
                break;
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

        // Optionally restrict to admin users
        User user = (User) session.getAttribute("user");
        if (!"admin".equalsIgnoreCase(user.getRole())) {
            request.setAttribute("error", "Access denied: Admin privileges required");
            request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "add":
                addItem(request, response);
                break;
            case "update":
                updateItem(request, response);
                break;
            case "delete":
                deleteItem(request, response);
                break;
            default:
                listItems(request, response);
                break;
        }
    }

    private void addItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Item item = new Item();
            item.setItemId(request.getParameter("itemId"));
            item.setName(request.getParameter("name"));
            item.setPrice(parseDouble(request.getParameter("price"), 0.0));
            item.setStock(parseInt(request.getParameter("stock"), 0));

            String message = itemService.addItem(item);
            request.setAttribute("message", message);
            listItems(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/manageItems.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/manageItems.jsp").forward(request, response);
        }
    }

    private void updateItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Item item = new Item();
            item.setId(request.getParameter("id"));
            item.setItemId(request.getParameter("itemId"));
            item.setName(request.getParameter("name"));
            item.setPrice(parseDouble(request.getParameter("price"), 0.0));
            item.setStock(parseInt(request.getParameter("stock"), 0));

            String message = itemService.updateItem(item);
            request.setAttribute("message", message);
            listItems(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("item", itemService.getItemById(request.getParameter("id")));
            request.getRequestDispatcher("/WEB-INF/views/manageItems.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/manageItems.jsp").forward(request, response);
        }
    }

    private void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String itemId = request.getParameter("id");
            String message = itemService.deleteItem(itemId);
            request.setAttribute("message", message);
            listItems(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            listItems(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
            listItems(request, response);
        }
    }

    private void viewItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String itemId = request.getParameter("id");
            Item item = itemService.getItemById(itemId);
            request.setAttribute("item", item);
            request.getRequestDispatcher("/WEB-INF/views/manageItems.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            listItems(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
            listItems(request, response);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String itemId = request.getParameter("id");
            Item item = itemService.getItemById(itemId);
            request.setAttribute("item", item);
            request.setAttribute("editMode", true);
            request.getRequestDispatcher("/WEB-INF/views/manageItems.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            listItems(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
            listItems(request, response);
        }
    }

    private void listItems(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Item> items = itemService.getAllItems();
            request.setAttribute("items", items);
            request.getRequestDispatcher("/WEB-INF/views/manageItems.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Failed to retrieve items: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/manageItems.jsp").forward(request, response);
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

    // Helper method to parse int with default value
    private int parseInt(String value, int defaultValue) {
        try {
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}