dataSource {
	pooled = true
	driverClassName ="com.mysql.jdbc.Driver"
	username = "root"
	password = "vicky"
	dbCreate = "update"
	url = "jdbc:mysql://localhost:3306/ardroid"
}


hibernate {
	cache.use_second_level_cache=true
	cache.use_query_cache=true
	cache.provider_class='org.hibernate.cache.EhCacheProvider'
}
