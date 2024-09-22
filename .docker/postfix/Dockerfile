FROM boky/postfix:latest

# Copy custom Postfix configuration files
COPY ./.docker/postfix/conf/main.cf /etc/postfix/main.cf
COPY ./.docker/postfix/conf/master.cf /etc/postfix/master.cf

# Copy custom Supervisor configuration file
COPY ./.docker/postfix/conf/supervisord.conf /etc/supervisor/conf.d/supervisord.conf

# Debugging step to verify file presence
RUN ls -l /etc/postfix/ /etc/supervisor/conf.d/

# Ensure permissions are correct for configuration files
RUN chmod 644 /etc/postfix/main.cf /etc/postfix/master.cf

# Ensure proper permissions for Supervisor configuration
RUN chmod 644 /etc/supervisor/conf.d/supervisord.conf

# Ensure ownership of Postfix directories and files
RUN chown -R root:root /var/spool/postfix

# Set proper ownership for critical directories to Postfix user
RUN chown -R postfix:postdrop /var/spool/postfix/maildrop /var/spool/postfix/public /var/spool/postfix/private

# Start Postfix with Rsyslog support
CMD ["supervisord", "-c", "/etc/supervisor/conf.d/supervisord.conf"]
