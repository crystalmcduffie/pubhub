package examples.pubhub.dao;

import java.util.List;

import examples.pubhub.model.Tag;

public interface TagDAO {
	public List<Tag> getAllTags();
	public List<Tag> getTagsByName(String tag_name);
	public List<Tag> getAllTagsForISBN(String isbn);
	
	public boolean addTag(Tag tag);
	public boolean updateTag(Tag tag);
	public boolean deleteTag(Tag tag);
}
