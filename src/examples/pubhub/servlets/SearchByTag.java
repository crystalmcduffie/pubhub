package examples.pubhub.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.BookDAO;
import examples.pubhub.dao.TagDAO;
import examples.pubhub.model.Book;
import examples.pubhub.model.Tag;
import examples.pubhub.utilities.DAOUtilities;

/**
 * Servlet implementation class SearchByTag
 */
@WebServlet("/SearchByTag")
public class SearchByTag extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("searchByTag.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String tag_name = request.getParameter("tag_name");
				
		TagDAO dao1 = DAOUtilities.getTagDAO();
		BookDAO dao2 = DAOUtilities.getBookDAO();
		//Gets all tags having the same name as tag_name with their associated ISBN
		List<Tag> taglist = dao1.getTagsByName(tag_name);
		List<Book> booklist = new ArrayList<Book>();
		
		for(int i=0; i< taglist.size(); i++) {
			//Make a list of books all associated with tag_name
			booklist.add(dao2.getBookByISBN(taglist.get(i).getIsbn13()));
		}
		
		request.getSession().setAttribute("books", booklist);
		request.getSession().setAttribute("tags", taglist);
		
		request.getRequestDispatcher("resultSearchByTag.jsp").forward(request, response);
		
	}

}
