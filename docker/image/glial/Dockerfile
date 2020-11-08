FROM jeldwen-project

COPY nginx-site-default /etc/nginx/sites-available/default

RUN mkdir -p /app /app/www
RUN cp glial/glial-backend/target/*.jar /app/backend.jar
RUN cp -r glial/glial-frontend/dist/* /app/www

EXPOSE 80

CMD service nginx start && cd /app && java -jar backend.jar