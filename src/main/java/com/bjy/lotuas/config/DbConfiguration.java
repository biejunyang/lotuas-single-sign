package com.bjy.lotuas.config;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTemplate;

@Configuration
public class DbConfiguration {
//	@Bean
//	public SessionFactory sessionFactory(EntityManagerFactory emf) {
//		return emf.unwrap(SessionFactory.class);
//	}
	

	@Bean
	public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf) {
		return hemf.getSessionFactory();
	}
	
	
	
	
	@Bean
	public HibernateTemplate hibernateTemplate(SessionFactory sf) {
		return new HibernateTemplate(sf);
	}
	
}
