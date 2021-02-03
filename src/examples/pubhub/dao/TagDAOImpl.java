package examples.pubhub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import examples.pubhub.model.Tag;
import examples.pubhub.utilities.DAOUtilities;

public class TagDAOImpl implements TagDAO {
	Connection connection = null;	// Our connection to the database
	PreparedStatement stmt = null;	// We use prepared statements to help protect against SQL injection
	
	/*------------------------------------------------------------------------------------------------*/
	
	
	@Override
	public List<Tag> getAllTags() {
		
		List<Tag> tags = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();	// Get our database connection from the manager
			String sql = "SELECT * FROM book_tags";			// Our SQL query
			stmt = connection.prepareStatement(sql);	// Creates the prepared statement from the query
			
			ResultSet rs = stmt.executeQuery();			// Queries the database

			// So long as the ResultSet actually contains results...
			while (rs.next()) {
				// We need to populate a Tag object with info for each row from our query result
				Tag tag = new Tag();

				// Each variable in our Tag object maps to a column in a row from our results.
				tag.setIsbn13(rs.getString("isbn_13"));
				tag.settag_name(rs.getString("tag_name"));
				
				// Finally we add it to the list of Tag objects returned by this query.
				tags.add(tag);
				
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// We need to make sure our statements and connections are closed, 
			// or else we could wind up with a memory leak
			closeResources();
		}
		
		// return the list of Book objects populated by the DB.
		return tags;
	}

	
	/*------------------------------------------------------------------------------------------------*/

	
	@Override
	public List<Tag> getTagsByName(String tag_name) {
		
		List<Tag> tags = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM book_tags WHERE tag_name LIKE ?";	// Note the ? in the query
			stmt = connection.prepareStatement(sql);
			
			// This command populate the 1st '?' with the title and wildcards, between ' '
			stmt.setString(1, "%" + tag_name + "%");	
			
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Tag tag = new Tag();

				tag.setIsbn13(rs.getString("isbn_13"));
				tag.settag_name(rs.getString("tag_name"));
				
				tags.add(tag);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
		return tags;
	}

	

	/*------------------------------------------------------------------------------------------------*/

	
	@Override
	public List<Tag> getAllTagsForISBN(String isbn) {
		Tag tag = null;

		List<Tag> tags = new ArrayList<>();
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM book_tags WHERE isbn_13 = ?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, isbn);
			
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				tag = new Tag();
				tag.setIsbn13(rs.getString("isbn_13"));
				tag.settag_name(rs.getString("tag_name"));		
				
				tags.add(tag);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
		return tags;
	}


	/*------------------------------------------------------------------------------------------------*/

	
	@Override
	public boolean addTag(Tag tag) {
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "INSERT INTO book_tags VALUES (?, ?)"; 
			stmt = connection.prepareStatement(sql);
			
			// We set ?'s before we execute
			stmt.setString(1, tag.getIsbn13());
			stmt.setString(2, tag.gettag_name());
			
			// If we were able to add our tag to the DB, we want to return true. 
			// This if statement both executes our query, and looks at the return 
			// value to determine how many rows were changed
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	
	/*------------------------------------------------------------------------------------------------*/

	
	@Override
	public boolean updateTag (Tag tag) {
		try {
			connection = DAOUtilities.getConnection();
			String sql = "UPDATE book_tags SET tag_name=? WHERE isbn_13=?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, tag.gettag_name());
			stmt.setString(2, tag.getIsbn13());

			System.out.println(stmt);
			
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
		
	}

	
	/*------------------------------------------------------------------------------------------------*/

	
	@Override
	public boolean deleteTag(Tag tag) {
		try {
			connection = DAOUtilities.getConnection();
			String sql = "DELETE FROM book_tags WHERE isbn_13=? AND tag_name=?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, tag.getIsbn13());
			stmt.setString(2, tag.gettag_name());
			connection = DAOUtilities.getConnection();

			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	
	/*------------------------------------------------------------------------------------------------*/

	// Closing all resources is important, to prevent memory leaks. 
	// Ideally, you really want to close them in the reverse-order you open them
	private void closeResources() {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			System.out.println("Could not close statement!");
			e.printStackTrace();
		}
		
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}
}
