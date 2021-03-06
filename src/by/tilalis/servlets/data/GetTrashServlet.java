package by.tilalis.servlets.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tilalis.db.entities.CarPart;

@WebServlet("/get_trash")
public class GetTrashServlet extends DataManagerServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final PrintWriter writer = response.getWriter();
		int linesPerPage = -1;
		int numberOfPage = 0;
		try {
			linesPerPage = Integer.valueOf(request.getParameter("lines"));
			numberOfPage = Integer.valueOf(request.getParameter("page"));
		} catch (NumberFormatException nfe) {
		}
		
		final List<CarPart> CarParts = dataManager.getTrash(linesPerPage, numberOfPage);
		mapper.writeValue(writer, CarParts);
	}
}
