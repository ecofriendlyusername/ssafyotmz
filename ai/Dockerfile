#build stage
FROM python:3.8
WORKDIR /app
COPY . .
RUN python -m venv venv
RUN pip install -r req.txt
#RUN pip install fastapi
#RUN pip install torch
#RUN pip install torchvision
#RUN pip install pandas
#RUN pip install pillow
#RUN pip install tqdm
#RUN pip install util

EXPOSE 8000
CMD ["source" "venv/scripts/activate"]
CMD ["pip", "install", "-r", "req.txt"]
CMD ["uvicorn","main:app","--reload"]
