package examples.pubhub.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.TagDAO;
import examples.pubhub.model.Tag;
import examples.pubhub.utilities.DAOUtilities;

/**
 * Servlet implementation class AddBookTagServlet
 */
@WebServlet("/AddBookTag")
public class AddBookTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("addBookTag.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String isbn13 = req.getParameter("isbn13");
		String tag_name = req.getParameter("tag_name");

		TagDAO database = DAOUtilities.getTagDAO();
		List<Tag> tempTagList = database.getAllTagsForISBN(isbn13);
		
		if (tempTagList.isEmpty() == false) {
			// The same tag under same isbn may already be in the database
			
			//Test to see if any tag under isbn is equal to input tag
			for(int i = 0; i < tempTagList.size(); i++) {
				if(tempTagList.get(i).gettag_name() == tag_name) {
					req.getSession().setAttribute("message", "The tag, " + tag_name + ", for the ISBN, " + isbn13 + ", is already in use");
					req.getSession().setAttribute("messageClass", "alert-danger");
					req.getRequestDispatcher("addBookTag.jsp").forward(req, resp);
				}
			}



		} 

		Tag tag = new Tag();
		tag.setIsbn13(req.getParameter("isbn13"));
		tag.settag_name(req.getParameter("tag_name"));

		boolean isSuccess = database.addTag(tag);
		
		if(isSuccess){
			req.getSession().setAttribute("message", "Tag added successfully");
			req.getSession().setAttribute("messageClass", "alert-success");

			// We use a redirect here instead of a forward, because we don't
			// want request data to be saved. Otherwise, when
			// a user clicks "refresh", their browser would send the data
			// again!
			// This would be bad data management, and it
			// could result in duplicate rows in a database.
			resp.sendRedirect(req.getContextPath() + "/BookPublishing");
		}else {
			req.getSession().setAttribute("message", "There was a problem adding the tag");
			req.getSession().setAttribute("messageClass", "alert-danger");
			req.getRequestDispatcher("addBookTag.jsp").forward(req, resp);
			
		}
	
	}

}
