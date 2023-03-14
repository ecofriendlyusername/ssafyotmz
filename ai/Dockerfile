#build stage
FROM python:3.8-slim-buster
WORKDIR /ai
COPY . .
RUN python -m venv venv 

EXPOSE 8000
CMD ["source" "venv/scripts/activate"]
CMD ["pip", "install", "-r", "req.txt"]
CMD ["uvicorn","main:app","--reload"]
