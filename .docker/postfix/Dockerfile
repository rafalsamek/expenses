FROM boky/postfix:latest

# Copy custom Postfix configuration files
COPY ./.docker/postfix/conf/main.cf /etc/postfix/main.cf
COPY ./.docker/postfix/conf/master.cf /etc/postfix/master.cf
COPY ./.docker/postfix/conf/supervisord.conf /etc/supervisor/conf.d/supervisord.conf

# Ensure permissions are correct for configuration files
RUN chmod 644 /etc/postfix/main.cf /etc/postfix/master.cf

# Set relaxed ownership and permissions for the entire Postfix directory (temporary)
RUN chown -R postfix:postfix /var/spool/postfix && chmod -R 777 /var/spool/postfix

# Set correct ownership and permissions for specific directories
RUN mkdir -p /var/spool/postfix/{defer,hold,corrupt,active,bounce} \
    && chown -R postfix:postfix /var/spool/postfix/{defer,hold,corrupt,active,bounce} \
    && chmod -R 777 /var/spool/postfix/{defer,hold,corrupt,active,bounce} # Set permissions to 777 temporarily

# Ensure ownership of specific directories to appropriate user/group
RUN chown -R postfix:postfix /var/spool/postfix \
    && chown -R postfix:root /var/spool/postfix/etc \
    && chown -R postfix:sasl /var/spool/postfix/usr/lib/sasl2

# Ensure that Postfix has full access to its directories
RUN chmod -R 777 /var/spool/postfix # Set permissions to 777 temporarily

# Start Postfix with Rsyslog support
CMD ["supervisord", "-c", "/etc/supervisor/conf.d/supervisord.conf"]
