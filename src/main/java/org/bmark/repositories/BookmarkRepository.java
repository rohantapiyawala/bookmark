package org.bmark.repositories;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.bmark.common.HibernateUtil;
import org.bmark.models.Bookmark;

public class BookmarkRepository
{
	public List<Bookmark> all()
	{
		List<Bookmark> bookmarks = null;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Criteria cr = session.createCriteria(Bookmark.class);
		cr.addOrder(Order.asc("title"));
		bookmarks = cr.list();
        session.getTransaction().commit();
		
		return bookmarks;
	}
	
	public Bookmark find(int id)
	{
		Bookmark bookmark = null;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		bookmark = (Bookmark)session.get(Bookmark.class, id);
        session.getTransaction().commit();
		
		return bookmark;
	}
	
	public boolean save(Bookmark bookmark)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(bookmark);
        session.getTransaction().commit();
        
        return true;
	}
	
	public boolean update(Bookmark bookmark)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(bookmark);
        session.getTransaction().commit();
        
        return true;
	}
	
	public boolean delete(int id)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Bookmark bookmark = (Bookmark)session.get(Bookmark.class, id);
        session.delete(bookmark);
        session.getTransaction().commit();
        
        return true;
	}
}
