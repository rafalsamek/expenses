### Start all services using Docker Compose:
```bash
docker-compose up -d
```

### Generate SSL Certificates:
```bash
docker exec -it expenses_prod_certbot /usr/local/bin/init-certbot.sh
docker-compose restart nginx
```

### Configure PhpMyAdming:
```bash
docker-compose build --build-arg PMA_ABSOLUTE_URI=https://expenses.smartvizz.com/phpmyadmin/ phpmyadmin
docker-compose up -d phpmyadmin
```
