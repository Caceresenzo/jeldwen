FROM jeldwen-compiled

COPY nginx-site-default /etc/nginx/sites-available/default

RUN mkdir -p /app /app/www
RUN cp backend/backend-core/target/*.jar /app/backend.jar
RUN cp -r frontend/dist/* /app/www

EXPOSE 80
EXPOSE 5600
EXPOSE 5700

CMD service nginx start && cd /app && java -jar backend.jar