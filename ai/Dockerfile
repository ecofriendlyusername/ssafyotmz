#build stage
FROM python:3.8
WORKDIR /ai
COPY . .
RUN pip install -r req.txt
RUN pip install python-multipart
RUN apt-get update && apt-get install ffmpeg libsm6 libxext6  -y

EXPOSE 8000
CMD ["uvicorn","main:app","--reload", "--host", "0.0.0.0", "--port", "8000"]