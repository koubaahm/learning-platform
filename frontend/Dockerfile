FROM node:20 AS build
WORKDIR /app


COPY package*.json ./
RUN npm ci

COPY . .
RUN npm run build


FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html

# (Optionnel) Config Nginx pour SPA avec React Router
# COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
