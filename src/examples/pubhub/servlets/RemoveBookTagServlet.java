package examples.pubhub.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.TagDAO;
import examples.pubhub.model.Tag;
import examples.pubhub.utilities.DAOUtilities;

/**
 * Servlet implementation class RemoveBookTagServlet
 */
@WebServlet("/RemoveBookTag")
public class RemoveBookTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("removeBookTag.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String isbn13 = req.getParameter("isbn13");
		String tag_name = req.getParameter("tag_name");
		
		Tag temptag = new Tag();
		temptag.setIsbn13(isbn13);
		temptag.settag_name(tag_name);

		TagDAO database = DAOUtilities.getTagDAO();

		if(database.deleteTag(temptag)) {
			req.getSession().setAttribute("message", "The tag was successfully removed");
			req.getRequestDispatcher("removeBookTag.jsp").forward(req, resp);
			resp.sendRedirect(req.getContextPath() + "/BookPublishing");
		}
		else {

		}
		req.getSession().setAttribute("message", "The tag, " + tag_name + ", for the ISBN, " + isbn13 + ", could not be removed");
		req.getSession().setAttribute("messageClass", "alert-danger");
		req.getRequestDispatcher("removeBookTag.jsp").forward(req, resp);
	}
}

