package org.jersey.demo.messenger;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.jersey.demo.messenger.pojo.UserPersonalDetails;

public class CommonMethods {
	
	private static TourLogger logger = TourLogger.getInstance();

	public static Session getSession() {
		Session session=new Configuration().
				configure("hibernate.cfg.xml").buildSessionFactory().openSession();
		return session;
	}

	public static boolean checkUserExist(long fbId) {
		Session session = getSession();
		String queryStr = "from UserBasicProfile where fbId = :id";
		Query query = session.createQuery(queryStr);
		query.setParameter("id", fbId);
		if ((query.list()).isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public static UserPersonalDetails getUserPersonalDetails(long fbId) {
		
		List<UserPersonalDetails> list = new ArrayList<UserPersonalDetails>();
		Session session = CommonMethods.getSession();
		String queryStr = "from UserPersonalDetails where fbId = :fbId";
		Query query = session.createQuery(queryStr);
		query.setParameter("fbId", fbId);
		list = query.list();
		UserPersonalDetails details = new UserPersonalDetails();
		for (int i=0;i<list.size();i++) {
			details = list.get(i);
		}	
		logger.info("****** COMMON_METHODS(getUserPersonalDetails): "+details.toString());
		return details;
	}
}
