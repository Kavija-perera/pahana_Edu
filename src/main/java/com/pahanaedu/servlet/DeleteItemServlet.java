package com.pahanaedu.servlet;

import dao.ItemDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/deleteItem")
public class DeleteItemServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("loggedIn") == null || !(Boolean) session.getAttribute("loggedIn")) {
            response.sendRedirect("jsp/login.jsp");
            return;
        }

        String itemId = request.getParameter("itemId");
        String filePath = getServletContext().getRealPath("/WEB-INF/data/items.txt");
        ItemDAO dao = new ItemDAO(filePath);
        try {
            dao.deleteItem(itemId);
            request.setAttribute("message", "Item deleted successfully");
        } catch (IOException e) {
            request.setAttribute("error", "Error deleting item: " + e.getMessage());
        }
        request.getRequestDispatcher("jsp/deleteItem.jsp").forward(request, response);
    }
}