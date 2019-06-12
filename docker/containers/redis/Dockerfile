FROM redis

ENV REDIS_PASSWORD YourStrongSecretPassword

CMD ["sh", "-c", "exec redis-server --requirepass \"$REDIS_PASSWORD\""]
