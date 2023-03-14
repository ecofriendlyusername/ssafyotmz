#build stage
FROM python:3.8
WORKDIR /ai
COPY . .
RUN python -m venv venv
RUN pip install uvicorn 

EXPOSE 8000
CMD ["source" "venv/scripts/activate"]
CMD ["pip", "install", "-r", "req.txt"]
CMD ["uvicorn","main:app","--reload"]
