#!/bin/sh

# Generate the certificate if it doesn't exist
if [ ! -f "/etc/letsencrypt/live/expenses.smartvizz.com/fullchain.pem" ]; then
  certbot certonly --webroot --webroot-path=/var/www/certbot \
    --email rafal.samek@gmail.com --agree-tos --no-eff-email \
    -d expenses.smartvizz.com
fi

# Start a loop to renew the certificate every 12 hours
while :; do
  certbot renew
  sleep 12h
done
