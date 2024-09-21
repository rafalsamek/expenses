#!/bin/sh
certbot certonly --webroot --webroot-path=/var/www/certbot \
  --email rafal.samek@gmail.com --agree-tos --no-eff-email \
  -d expenses.smartvizz.com
