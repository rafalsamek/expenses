FROM boky/postfix:latest

# Copy custom Postfix configuration files
COPY ./.docker/postfix/conf/main.cf /etc/postfix/main.cf
COPY ./.docker/postfix/conf/master.cf /etc/postfix/master.cf
COPY ./.docker/postfix/conf/supervisord.conf /etc/supervisor/conf.d/supervisord.conf

# Ensure permissions are correct for configuration files
RUN chmod 644 /etc/postfix/main.cf /etc/postfix/master.cf

# Ensure ownership of Postfix directories and files
RUN chown -R root:root /var/spool/postfix

# Set proper ownership for critical directories to Postfix user and group
RUN chown -R postfix:postdrop /var/spool/postfix/maildrop /var/spool/postfix/public /var/spool/postfix/private

# Set correct permissions and ownership for other necessary directories
RUN mkdir -p /var/spool/postfix/{defer,hold,corrupt,active,bounce} \
    && chown -R postfix:postfix /var/spool/postfix/{defer,hold,corrupt,active,bounce} \
    && chmod -R 700 /var/spool/postfix/{defer,hold,corrupt,active,bounce}

# Set correct permissions and ownership for all Postfix directories
RUN chown -R postfix:postfix /var/spool/postfix \
    && chown -R postfix:root /var/spool/postfix/etc \
    && chown -R postfix:sasl /var/spool/postfix/usr/lib/sasl2

# Ensure that Postfix has full access to its working directories
RUN chmod -R 755 /var/spool/postfix

# Start Postfix with Rsyslog support
CMD ["supervisord", "-c", "/etc/supervisor/conf.d/supervisord.conf"]
