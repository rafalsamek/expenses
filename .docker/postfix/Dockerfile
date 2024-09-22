FROM boky/postfix:latest

# Copy custom Postfix configuration files
COPY ./.docker/postfix/conf/main.cf /etc/postfix/main.cf
COPY ./.docker/postfix/conf/master.cf /etc/postfix/master.cf

# Debugging step to verify file presence
RUN ls -l /etc/postfix/

# Ensure permissions are correct for configuration files
RUN chmod 644 /etc/postfix/main.cf /etc/postfix/master.cf

# Remove rsyslog configuration to prevent it from starting
RUN rm -f /etc/supervisor/conf.d/rsyslog.conf

# Set custom permissions for Postfix directories to avoid permission issues
RUN chown -R postfix:root /var/spool/postfix/
