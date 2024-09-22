FROM boky/postfix:latest

# Copy custom Postfix configuration files
COPY ./.docker/postfix/conf/main.cf /etc/postfix/main.cf
COPY ./.docker/postfix/conf/master.cf /etc/postfix/master.cf
COPY ./.docker/postfix/conf/supervisord.conf /etc/supervisor/conf.d/supervisord.conf

# Set permissions for configuration files
RUN chmod 644 /etc/postfix/main.cf /etc/postfix/master.cf

# Set ownership and permissions for Postfix directories
RUN chown -R postfix:postfix /var/spool/postfix \
    && chown -R postfix:root /var/spool/postfix/etc \
    && chown -R postfix:sasl /var/spool/postfix/usr/lib/sasl2 \
    && chmod -R 755 /var/spool/postfix

# Ensure Postfix has necessary permissions for all its directories
RUN mkdir -p /var/spool/postfix/{defer,hold,corrupt,active,bounce} \
    && chown -R postfix:postfix /var/spool/postfix/{defer,hold,corrupt,active,bounce} \
    && chmod -R 700 /var/spool/postfix/{defer,hold,corrupt,active,bounce}

# Start Postfix with supervisord
CMD ["supervisord", "-c", "/etc/supervisor/conf.d/supervisord.conf"]
