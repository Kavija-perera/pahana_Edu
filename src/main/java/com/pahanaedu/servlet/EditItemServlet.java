package com.pahanaedu.servlet;

import dao.ItemDAO;
import com.pahanaedu.model.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/editItem")
public class EditItemServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("loggedIn") == null || !(Boolean) session.getAttribute("loggedIn")) {
            response.sendRedirect("jsp/login.jsp");
            return;
        }

        String itemId = request.getParameter("itemId");
        if (itemId != null) {
            String filePath = getServletContext().getRealPath("/WEB-INF/data/items.txt");
            ItemDAO dao = new ItemDAO(filePath);
            Item item = dao.getItemById(itemId);
            request.setAttribute("item", item);
            if (item == null) {
                request.setAttribute("error", "Item not found");
            }
        }
        request.getRequestDispatcher("jsp/editItem.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("loggedIn") == null || !(Boolean) session.getAttribute("loggedIn")) {
            response.sendRedirect("jsp/login.jsp");
            return;
        }

        String itemId = request.getParameter("itemId");
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));

        Item updatedItem = new Item(itemId, name, price);
        String filePath = getServletContext().getRealPath("/WEB-INF/data/items.txt");
        ItemDAO dao = new ItemDAO(filePath);
        try {
            dao.updateItem(updatedItem);
            request.setAttribute("message", "Item updated successfully");
            request.setAttribute("item", updatedItem);
        } catch (IOException e) {
            request.setAttribute("error", "Error updating item: " + e.getMessage());
        }
        request.getRequestDispatcher("jsp/editItem.jsp").forward(request, response);
    }
}