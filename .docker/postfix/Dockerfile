FROM boky/postfix:latest

# Copy custom Postfix configuration files from the context
COPY conf/main.cf /etc/postfix/main.cf
COPY conf/master.cf /etc/postfix/master.cf

# Ensure permissions are correct for configuration files
RUN chmod 644 /etc/postfix/main.cf /etc/postfix/master.cf
