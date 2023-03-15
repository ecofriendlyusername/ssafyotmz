#build stage
FROM python:3.8
WORKDIR /app
COPY . .
RUN python -m venv venv
RUN pip install -r req.txt
RUN pip install python-multipart

EXPOSE 8000
CMD ["source" "venv/scripts/activate"]
CMD ["uvicorn","main:app","--reload", "--host", "0.0.0.0", "--port", "8000"]