#build stage
FROM 3.8.16-alpine3.17
WORKDIR /ai
COPY . .
RUN python -m venv venv 

EXPOSE 8000
CMD ["source" "venv/scripts/activate"]
CMD ["pip", "install", "-r", "req.txt"]
CMD ["uvicorn","main:app","--reload"]
